package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.List;
import java.util.Map;

public class DirectionDetailsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public TextView textView;
    DirectionAdapter adapter;

    DirectionData directionData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);

        Intent intent = getIntent();

        directionData = new DirectionData((List<GraphPath>) intent.getSerializableExtra("orderedEdgeList"), intent.getStringArrayListExtra("orderedExhibitNames"));

        // MM we store the destination list so that we can call the plan backend if we restore the directions from storage
        directionData.setDestinationList(intent.getStringArrayListExtra("destinationList"));
        // MM read in currentExhibitIndex so that directions can be restored from file
        directionData.setCurrentExhibitIndex(intent.getIntExtra("currentExhibitIndex",0));
        //directionData.setCurrentExhibitIndex(intent.getIntExtra("currentExhibitIndex"));

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

        System.out.println("Direction details: " + directionData.orderedEdgeList.size());


        recyclerView = findViewById(R.id.direction_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
        textView.setText(directionData.getTitleText());
    }

    public void onNextClicked(View view) {

        if(directionData.currentExhibitIndex < directionData.orderedEdgeList.size()){

            Log.d("Next", "Here");
            adapter.setIndividualDirectionListItems(directionData.getCurrentExhibitDirections());
            textView.setText(directionData.getTitleText());
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