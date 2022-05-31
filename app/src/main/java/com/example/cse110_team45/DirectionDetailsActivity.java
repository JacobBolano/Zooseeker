package com.example.cse110_team45;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    List<String> orderedExhibitNames;
    List<GraphPath> orderedEdgeList;
    List<String> orderedExhibitID;
    String prevNode;
    String nextNode;
    Map <String, LatLng> exhibitLatLng;
    private final permissionChecker PermissionChecker = new permissionChecker(this);

    @Override
    @SuppressLint("MissingPermission")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);

        Intent intent = getIntent();
        exhibitLatLng = new HashMap<String, LatLng>();

        directionData = new DirectionData((List<GraphPath>) intent.getSerializableExtra("orderedEdgeList"), intent.getStringArrayListExtra("orderedExhibitNames"));

        // MM we store the destination list so that we can call the plan backend if we restore the directions from storage
        orderedExhibitID = new ArrayList<String>();
        directionData.setDestinationList(intent.getStringArrayListExtra("destinationList"));
        // MM read in currentExhibitIndex so that directions can be restored from file
        directionData.setCurrentExhibitIndex(intent.getIntExtra("currentExhibitIndex",0));
        //directionData.setCurrentExhibitIndex(intent.getIntExtra("currentExhibitIndex"));

        orderedExhibitNames = directionData.orderedExhibitNames;
        orderedEdgeList = directionData.orderedEdgeList;
        prevNode = directionData.prevNode;
        //nextNode = orderedExhibitNames.get(1);


        Log.d("Exhibit List",directionData.orderedExhibitNames.toString());
        Log.d("Edge List", directionData.orderedEdgeList.toString());

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("trail_info.json", this);

        directionData.addGraphs(g, vInfo, eInfo);

        adapter = new DirectionAdapter();

        textView = findViewById(R.id.directionDetails);

        Log.d("direction details", "Direction details: " + directionData.orderedEdgeList.size());


        recyclerView = findViewById(R.id.direction_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
        textView.setText(directionData.getTitleText());


        // NEW STUFF

        if (PermissionChecker.ensurePermission()) return;

        for(int i = 1; i < orderedExhibitNames.size() - 1; i++){
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
                        // they want to replan
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // they do not want to replan
                    }
                });
        AlertDialog dialog = builder.create();

        var provider = LocationManager.GPS_PROVIDER;
        var locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Map<String, LatLng> finalExhibitLatLng = exhibitLatLng;
        var locationListener = new LocationListener(){
            @Override
            public void onLocationChanged(@NonNull Location location){
                Log.d("Replan Route", String.format("Location changed %s", location));
                LatLng currLocation = new LatLng(
                        location.getLatitude(), location.getLongitude());
                double currDistance = distanceBetween(currLocation, finalExhibitLatLng.get(nextNode));
                Log.d("curr_distance", ""+currDistance);
                for(String key : finalExhibitLatLng.keySet()){
                    if(!key.equals(nextNode)){
                        Log.d("current exhibit", key+ " " + nextNode);
                        Log.d("distance between", ""+distanceBetween(finalExhibitLatLng.get(key), currLocation));
                        if(distanceBetween(finalExhibitLatLng.get(key), currLocation) < currDistance){
                            //showalert
                            Log.d("closer exhibit", key);
                            Log.d("distance between", ""+distanceBetween(finalExhibitLatLng.get(key), currLocation));

                            dialog.show();
                        }
                    }
                }
            }
        };
        locationManager.requestLocationUpdates(provider,0,0f,locationListener);

    }

    //TODO move this
    @NonNull
    public static double distanceBetween(LatLng l1, LatLng l2) {
        return Math.sqrt(
                Math.pow(l1.latitude - l2.latitude, 2) + Math.pow(l1.longitude - l2.longitude, 2));
    }


    public void onNextClicked(View view) {

        if(directionData.currentExhibitIndex < directionData.orderedEdgeList.size()){

            Log.d("Next", "Here");
            adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
            textView.setText(directionData.getTitleText());
            nextNode = orderedExhibitID.get(directionData.currentExhibitIndex);
        }
        else{
            finish();
        }

    }

    public void onPreviousClicked(View view) {
        Log.d("Previous Clicked!", "True");
        adapter.setIndividualDirectionListItems(directionData.getPreviousDirections());
        textView.setText(directionData.getTitleText());

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "DIRECTIONS");
        Gson gson = new Gson();
        String destinationListJSON = gson.toJson(directionData.getDestinationList());
        editor.putString("destinationListJSON",destinationListJSON);
        editor.putInt("currentExhibitIndex",directionData.getCurrentExhibitIndex()-1);
        editor.apply();
    }

    public void onSkipClick(View view) {
        if(directionData.currentExhibitIndex < directionData.orderedEdgeList.size() && directionData.currentExhibitIndex > 0){
            adapter.setIndividualDirectionListItems(directionData.skipExhibit());
            textView.setText(directionData.getTitleText());
        }
        else {
            finish();
        }
    }
}