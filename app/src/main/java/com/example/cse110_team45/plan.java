package com.example.cse110_team45;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.jgrapht.Graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class plan extends AppCompatActivity {

  public planData PlanData;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String start = "entrance_exit_gate";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Intent intent = getIntent();
        List<String> visits = intent.getStringArrayListExtra("destinationList");

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", this);

        PlanData = new planData(g, vInfo, eInfo, visits);
        this.PlanData.pathFinding();
        this.PlanData.pathComputation();

        //testing
        TextView orderedVisitsView = this.findViewById(R.id.orderedVisits);
        orderedVisitsView.setText(PlanData.orderedPathExhibitNames.get(0));
        //testing
        directionDetailsSend();
    }


    public void directionDetailsSend() {
        Intent intent = new Intent(plan.this, DirectionDetailsActivity.class);

        intent.putStringArrayListExtra("orderedExhibitNames", (ArrayList<String>) this.PlanData.orderedPathExhibitNames);
        intent.putExtra("orderedEdgeList", (Serializable) this.PlanData.orderedPathEdgeList);
        startActivity(intent);
    }

    public planData getPlanData() {
        return PlanData;
    }
}