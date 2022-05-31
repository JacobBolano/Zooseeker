package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jgrapht.Graph;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class RestoreQueryActivity extends AppCompatActivity {

    private planData PlanData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_query);
    }


    public void onRestartClick(View view) {
        // Open search
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putStringArrayListExtra("destinationIdList", new ArrayList<>());
        startActivity(intent);
    }

    public void onRestoreClick(View view) {
        // store the page on close. Ideally we would have an option for every page, defaulting
        // unimportant pages to their calling pages (so if you open settings from Plan, we save the
        // state of plan when you close). In practice we'll probably only save on certain pages
        // (search, plan, and directions).
        // so store the page
        // for search, store destinationIDList
        // for plan, store visits (intent already has support for passing data)
        // for directions, store orderedEdgeLists and orderedExhibitNames, store currentExhibitIndex
        // Open search
        Intent intent;
        Gson gson = new Gson();
        ArrayList<String> destinationList;
        Type arrayListStringType = new TypeToken<ArrayList<String>>() {
        }.getType();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String lastActivity = prefs.getString("lastActivity", "NONE"); // check activity before close
        // StoreData data = StoreData.loadJSON(this,"exit_state.json");
        switch (lastActivity) {
            case "SEARCH":
                intent = new Intent(this, SearchActivity.class);
                destinationList = gson.fromJson(prefs.getString("destinationIdListJSON", ""), arrayListStringType);
                intent.putStringArrayListExtra("destinationIdList", destinationList);
                break;
            case "PLAN":
                intent = new Intent(this, plan.class);
                destinationList = gson.fromJson(prefs.getString("destinationListJSON", ""), arrayListStringType);
                intent.putStringArrayListExtra("destinationList", destinationList);
                break;
            case "DIRECTIONS":
                intent = new Intent(this, DirectionDetailsActivity.class);
                // ArrayList<String> orderedExhibitNames = gson.fromJson(prefs.getString("orderedExhibitNamesJSON", ""), arrayListStringType);
                // intent.putStringArrayListExtra("orderedExhibitNames", orderedExhibitNames);
                // intent.putExtra("orderedEdgeListJSON", prefs.getString("orderedEdgeListJSON", ""));
                int currentExhibitIndex = prefs.getInt("currentExhibitIndex", 0);
                destinationList = gson.fromJson(prefs.getString("destinationListJSON",""),arrayListStringType);

                // run plan backend

                // 1. Load the graph...
                Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("zoo_graph.json", this);

                // 2. Load the information about our nodes and edges...
                Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);
                Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("trail_info.json", this);


                PlanData = new planData(g, vInfo, eInfo, destinationList);
                this.PlanData.pathFinding("start");
                this.PlanData.pathComputation();
                this.PlanData.orderedPathWithComp();

                intent.putExtra("currentExhibitIndex", currentExhibitIndex);
                intent.putStringArrayListExtra("orderedExhibitNames", (ArrayList<String>) this.PlanData.orderedPathExhibitNames);
                intent.putExtra("orderedEdgeList", (Serializable) this.PlanData.orderedPathEdgeList);
                intent.putStringArrayListExtra("destinationList", (ArrayList<String>) this.PlanData.destinationList);
                break;
            default:
                intent = new Intent(this, SearchActivity.class);
                intent.putStringArrayListExtra("destinationIdList", new ArrayList<>());
                startActivity(intent);
                break;
        }
        startActivity(intent);
    }
}