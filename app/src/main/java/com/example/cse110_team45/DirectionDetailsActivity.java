package com.example.cse110_team45;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.location.LocationManager;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cse110_team45.location.Coord;
import com.example.cse110_team45.location.LocationModel;
import com.example.cse110_team45.location.LocationPermissionChecker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectionDetailsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public TextView textView;
    DirectionAdapter adapter;

    DirectionData directionData;
    String currExhibit;
    List<String> orderedExhibitNames;
    List<GraphPath> orderedEdgeList;
    String closestExhibit;
    Map<String, ZooData.VertexInfo> vInfo;
    List<String> orderedExhibitID;
    String prevNode;
    String nextNode;
    AlertDialog dialog;
    Map <String, LatLng> exhibitLatLng;
    double prevDistance = Double.MAX_VALUE;
    boolean wantToReplan;
    private final permissionChecker PermissionChecker = new permissionChecker(this);
    boolean wantToUseLocation;

    boolean buttonMostRecentlyPressed=true;

    public static final String EXTRA_USE_LOCATION_SERVICE = "use_location_updated";
    private boolean useLocationService;


    @Override
    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);

        wantToUseLocation = false;
        useLocationService = getIntent().getBooleanExtra(EXTRA_USE_LOCATION_SERVICE, wantToUseLocation);


        Intent intent = getIntent();

        exhibitLatLng = new HashMap<String, LatLng>();
        orderedExhibitID = new ArrayList<String>();
        wantToReplan = true;


        // get data sent over by plan

        directionData = new DirectionData((List<GraphPath>) intent.getSerializableExtra("orderedEdgeList"), intent.getStringArrayListExtra("orderedExhibitNames"));

        // MM we store the destination list so that we can call the plan backend if we restore the directions from storage
        //directionData.setDestinationList(intent.getStringArrayListExtra("destinationList"));
        // MM read in currentExhibitIndex so that directions can be restored from file
        directionData.setCurrentExhibitIndex(intent.getIntExtra("currentExhibitIndex",0));
        //directionData.setCurrentExhibitIndex(intent.getIntExtra("currentExhibitIndex"));

        orderedExhibitNames = directionData.orderedExhibitNames;
        orderedEdgeList = directionData.orderedEdgeList;

        Log.d("YO orderedEdgeList: ", orderedEdgeList.toString());

        prevNode = directionData.prevNode;
        //nextNode = orderedExhibitNames.get(1);


        Log.d("Exhibit List",directionData.orderedExhibitNames.toString());
        Log.d("Edge List", directionData.orderedEdgeList.toString());

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("trail_info.json", this);

        // add this information to the directionData so that it can be used
        directionData.addGraphs(g, vInfo, eInfo);

        adapter = new DirectionAdapter();

        textView = findViewById(R.id.directionDetails);

        Log.d("direction details", "Direction details: " + directionData.orderedEdgeList.size());

        //populate the screen with directions to the first exhibit
        recyclerView = findViewById(R.id.direction_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
        textView.setText(directionData.getTitleText());

        if (PermissionChecker.ensurePermission()) return;

        for(int i = 1; i < orderedExhibitNames.size(); i++){
            String categoryToGetLocation = vInfo.get(orderedExhibitNames.get(i)).group_id;
            if (categoryToGetLocation == null) {
                // if its null just use its exhibit name
                categoryToGetLocation = orderedExhibitNames.get(i);
            }
            orderedExhibitID.add(categoryToGetLocation);
            Log.d("CategoryToGetLocation", categoryToGetLocation);
            exhibitLatLng.put(categoryToGetLocation, new LatLng(
                    vInfo.get(categoryToGetLocation).lat,
                    vInfo.get(categoryToGetLocation).lng)
            );
        }

        nextNode = orderedExhibitID.get(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to replan your exhibits?")
                .setTitle("You are off track")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ArrayList<String>  newVisits = new ArrayList<String>();
                        for(int i = directionData.currentExhibitIndex; i < directionData.orderedExhibitNames.size(); i++){
                            newVisits.add(directionData.orderedExhibitNames.get(i));
                        }
                        Log.d("new visit", newVisits.toString());
                        planData PlanData = new planData(g, vInfo, eInfo, newVisits);
                        PlanData.makeSet();
                        if (vInfo.get(closestExhibit).group_id != null) {
                            closestExhibit = vInfo.get(closestExhibit).group_id;
                        }
                        Log.d("PlanData exhibit names", PlanData.orderedPathExhibitNames.toString());
                        PlanData.pathFinding(closestExhibit);
                        Log.d("PlanData exhibit names", PlanData.orderedPathExhibitNames.toString());

                        DirectionData tempdData = new DirectionData(PlanData.orderedPathEdgeList, PlanData.orderedPathExhibitNames);
                        Log.d("closest", closestExhibit);
                        Log.d("direction orderedEdgeList", directionData.orderedEdgeList.toString());
                        Log.d("direction orderedExhibitNames", directionData.orderedExhibitNames.toString());
                        Log.d("Temp orderedEdgeList", tempdData.orderedEdgeList.toString());
                        Log.d("Temp orderedExhibitNames", tempdData.orderedExhibitNames.toString());
                        for(int i = directionData.currentExhibitIndex; i < directionData.orderedExhibitNames.size()-1; i++){
                            directionData.orderedEdgeList.set(i-1, tempdData.orderedEdgeList.get(i-directionData.currentExhibitIndex));
                            directionData.orderedExhibitNames.set(i, tempdData.orderedExhibitNames.get(i-directionData.currentExhibitIndex+1));
                        }
                        Log.d("orderedEdgeList", directionData.orderedEdgeList.toString());
                        Log.d("orderedExhibitNames", directionData.orderedExhibitNames.toString());
//                        directionData.changeCurrentDirection(closestExhibit);
                        directionData.currentExhibitIndex--;
                        adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
                        textView.setText(directionData.getTitleText());
                        setNextNode();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // they do not want to replan
                        wantToReplan = false;
                    }
                });
        dialog = builder.create();

        var provider = LocationManager.GPS_PROVIDER;
        var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Map<String, LatLng> finalExhibitLatLng = exhibitLatLng;
        currExhibit = directionData.orderedExhibitNames.get(directionData.currentExhibitIndex);
        if (vInfo.get(directionData.orderedExhibitNames.get(directionData.currentExhibitIndex)).group_id != null) {
            currExhibit = vInfo.get(directionData.orderedExhibitNames.get(directionData.currentExhibitIndex)).group_id;
        }
        String finalCurrExhibit = currExhibit;

        if(useLocationService) {
            var locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    LatLng currLocation = new LatLng(
                            location.getLatitude(), location.getLongitude());
                    Log.d("Current Location", currLocation.toString());
                    if (prevDistance < distanceBetween(currLocation, exhibitLatLng.get(finalCurrExhibit))) {
                        directionData.changeCurrentDirection(getNearestExhibit(currLocation));
                        adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
                        textView.setText(directionData.getTitleText());
                    }
                    if (wantToReplan) {
                        Log.d("Replan Route", String.format("Location changed %s", location));
                        double currDistance = distanceBetween(currLocation, finalExhibitLatLng.get(nextNode));
                        Log.d("directionData.orderedExhibitNames", directionData.orderedExhibitNames.toString());
                        Log.d("index", "" + directionData.currentExhibitIndex);
                        Log.d("current distance", "" + currDistance);
                        for (int i = directionData.currentExhibitIndex + 1; i < directionData.orderedExhibitNames.size(); i++) {
                            String key = directionData.orderedExhibitNames.get(i);
                            if (vInfo.get(key).group_id != null) {
                                key = vInfo.get(key).group_id;
                            }
                            if (!key.equals(nextNode)) {
                                Log.d("current exhibit", key + " " + nextNode);
                                Log.d("distance between", "" + distanceBetween(finalExhibitLatLng.get(key), currLocation));
                                if (distanceBetween(finalExhibitLatLng.get(key), currLocation) < currDistance && !key.equals(directionData.orderedExhibitNames.get(0))) {
                                    //showalert
                                    Log.d("closer exhibit", key);
                                    Log.d("distance between", "" + distanceBetween(finalExhibitLatLng.get(key), currLocation));
                                    closestExhibit = getNearestExhibit(currLocation);
                                    dialog.show();
                                }
                            }
                        }
                    }
                }
            };
            locationManager.requestLocationUpdates(provider,0,0f,locationListener);
        }


    }

    //TODO move this
    @NonNull
    public static double distanceBetween(LatLng l1, LatLng l2) {
        return Math.sqrt(
                Math.pow(l1.latitude - l2.latitude, 2) + Math.pow(l1.longitude - l2.longitude, 2));
    }



    public void onNextClicked(View view) {

        if(directionData.getCurrentExhibitIndex() < directionData.orderedEdgeList.size()){
            wantToReplan = true;
            Log.d("Next", "Here");
            adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
            textView.setText(directionData.getTitleText());
            setNextNode();

            buttonMostRecentlyPressed=true;

        }
        else{ //if there is nothing left you go to you have completed your zoo journey
            finish();
        }

    }

    public void onPreviousClicked(View view) {
        //update screen with directions to previous exhibit
        Log.d("Previous Clicked!", "True");
        adapter.setIndividualDirectionListItems(directionData.getPreviousDirections());
        textView.setText(directionData.getTitleText());

        wantToReplan = true;
        setNextNode();

        buttonMostRecentlyPressed=false;

    }

    public void onSettingsClicked(View view) {
        //flip the boolean value that represents direction type
        directionData.setDirectionType(!directionData.getDirectionType());
        if(buttonMostRecentlyPressed){
            //if next was most recently pressed refresh the screen by calling previous and then next
            //this makes it so that the prevNode inside directionData is properly set
            onPreviousClicked(view);
            onNextClicked(view);
        } else {
            //if previous was most recently pressed refresh the screen by calling next and then previous
            //this makes it so that the prevNode inside directionData is properly set to give directions backwards
            onNextClicked(view);
            onPreviousClicked(view);
        }

    }



    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "DIRECTIONS");
        Gson gson = new Gson();
        String destinationListJSON = gson.toJson(directionData.orderedExhibitNames.subList(1,directionData.orderedExhibitNames.size() -1));
        //String destinationListJSON = gson.toJson(directionData.getDestinationList());
        editor.putString("destinationListJSON",destinationListJSON);
        editor.putInt("currentExhibitIndex",directionData.getCurrentExhibitIndex()-1);
        editor.apply();
    }

    public void onSkipClick(View view) {

        if(directionData.getCurrentExhibitIndex() < directionData.orderedEdgeList.size() && directionData.currentExhibitIndex > 0){

            //use the .skipExhibit() to remove the current exhibit from the list, this also returns the new directions

            adapter.setIndividualDirectionListItems(directionData.skipExhibit());
            textView.setText(directionData.getTitleText());
            wantToReplan = true;
            setNextNode();
        }
        else {
            finish();
        }
    }

    public String getNearestExhibit(LatLng currLoc){
        double nearestExhibitDist = Double.MAX_VALUE;
        String nearestExhibit = "";
        for(String key : vInfo.keySet()){
            double distBetweenCurr = distanceBetween(new LatLng(vInfo.get(key).lat,vInfo.get(key).lng), currLoc);
            if(distBetweenCurr < nearestExhibitDist){
                nearestExhibitDist = distBetweenCurr;
                nearestExhibit = key;
            }
        }
        Log.d("Nearest Exhibit: ", nearestExhibit);
        return nearestExhibit;
    }

    public void setNextNode() {
        String currentExhibitCategory = directionData.getCurrentExhibit().id;
        if (directionData.getCurrentExhibit().group_id != null) {
            currentExhibitCategory = directionData.getCurrentExhibit().group_id;
        }
        Log.d("Current Exhibit Category: ", currentExhibitCategory);
        int orderedExhibitIDIndex = orderedExhibitID.indexOf(currentExhibitCategory);
        if (orderedExhibitIDIndex == orderedExhibitID.size() - 1) {
            wantToReplan = false;
        }
        nextNode = orderedExhibitID.get(orderedExhibitIDIndex);
    }

    public void inputLocButton(View view) {

        EditText inputLongEdit = (EditText) findViewById(R.id.inputLong);
        EditText inputLatEdit = (EditText) findViewById(R.id.inputLat);

        Log.d("Input Long", inputLongEdit.getText().toString());
        Log.d("Input Lat", inputLatEdit.getText().toString());

        Double inputLongDouble = Double.parseDouble(inputLongEdit.getText().toString());
        Double inputLatDouble = Double.parseDouble(inputLatEdit.getText().toString());

        LatLng currLocation = new LatLng(inputLatDouble, inputLongDouble);
        Log.d("Current Location", currLocation.toString());
        onLocationChanging(currLocation);
    }

public void onLocationChanging(LatLng currLocation){
    if(prevDistance < distanceBetween(currLocation, exhibitLatLng.get(currExhibit))){
        directionData.changeCurrentDirection(getNearestExhibit(currLocation));
        adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
        textView.setText(directionData.getTitleText());
    }
    if (wantToReplan) {
        double currDistance = distanceBetween(currLocation, exhibitLatLng.get(nextNode));
        Log.d("directionData.orderedExhibitNames", directionData.orderedExhibitNames.toString());
        Log.d("index", ""+directionData.currentExhibitIndex);
        Log.d("current distance", ""+currDistance);
        for(int i = directionData.currentExhibitIndex+1; i < directionData.orderedExhibitNames.size(); i++){
            String key = directionData.orderedExhibitNames.get(i);
            if (vInfo.get(key).group_id != null) {
                key = vInfo.get(key).group_id;
            }

            if(!key.equals(nextNode)){
                Log.d("current exhibit", key+ " " + nextNode);
                Log.d("distance between", ""+distanceBetween(exhibitLatLng.get(key), currLocation));
                if(distanceBetween(exhibitLatLng.get(key), currLocation) < currDistance && !key.equals(directionData.orderedExhibitNames.get(0))){
                    //showalert
                    Log.d("closer exhibit", key);
                    Log.d("distance between", ""+distanceBetween(exhibitLatLng.get(key), currLocation));
                    closestExhibit = getNearestExhibit(currLocation);
                    dialog.show();
                }
            }
        }
    }

}



}