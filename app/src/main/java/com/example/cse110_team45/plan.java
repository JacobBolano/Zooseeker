package com.example.cse110_team45;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.jgrapht.Graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class plan extends AppCompatActivity {

    public planData PlanData;
    public RecyclerView recyclerView;
    private planListAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String start = "entrance_exit_gate";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Intent intent = getIntent();
        List<String> visits = intent.getStringArrayListExtra("destinationList");

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("trail_info.json", this);

        PlanData = new planData(g, vInfo, eInfo, visits);
        this.PlanData.pathFinding();
        this.PlanData.pathComputation();
        this.PlanData.orderedPathWithComp();

        adapter = new planListAdapter();
        adapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.orderedVisits);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<String> recyclerList = this.PlanData.orderedPathNamesAndDist;

        adapter.setTodoListItems(recyclerList);

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "PLAN");
        Gson gson = new Gson();
        String destinationListJSON = gson.toJson(PlanData.destinationList);
        editor.putString("destinationListJSON",destinationListJSON);
        editor.apply();
    }

    /*
    send the paths and exhibits to the directionDetails activity
     */
    public void directionDetailsSend(View view) {
        Intent intent = new Intent(plan.this, DirectionDetailsActivity.class);

        intent.putStringArrayListExtra("orderedExhibitNames", (ArrayList<String>) this.PlanData.orderedPathExhibitNames);
        intent.putExtra("orderedEdgeList", (Serializable) this.PlanData.orderedPathEdgeList);
        // MM for compatibility with store/restore
        intent.putStringArrayListExtra("destinationList", (ArrayList<String>) this.PlanData.destinationList);
        // MM for compatibility with store/restore
        intent.putExtra("currentExhibitIndex",0);
        startActivity(intent);
    }
}