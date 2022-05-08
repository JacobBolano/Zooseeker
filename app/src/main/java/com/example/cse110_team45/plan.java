package com.example.cse110_team45;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import org.jgrapht.nio.json.JSONImporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import android.os.Bundle;
import android.widget.TextView;

public class plan extends AppCompatActivity {

    List<String> orderedPath;
    List<String> orderedPathStreets;
    List<Integer> orderedPathDistances;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        Intent intent = getIntent();
        List<String> visits = intent.getStringArrayListExtra("destinationList");
        System.out.println("this is the shit " + visits);

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", this);

        //will be imported from search bar
        orderedPath = new ArrayList<String>(); //send to direction details
        orderedPathStreets = new ArrayList<String>(); //send to direction details
        // actual path of graph elements
        // each graph element
        // each graph element can get getDetails()
        // filled where each node has name of exhibit and then the path
        //
        orderedPathDistances = new ArrayList<Integer>(); //
        //

        //Pathfinding
        String start = "entrance_exit_gate";
        orderedPath.add(start);
        String streetName = "";
        List<String> visitsTemp = new ArrayList<String>();
        for(int i = 0; i < visits.size(); i++) {
            visitsTemp.add(visits.get(i));
        }
        String source = start;
        while(!visitsTemp.isEmpty()) {
            int minDist = Integer.MAX_VALUE;
            String dest = null;
            for(int i = 0; i < visitsTemp.size(); i++){
                String goal = visitsTemp.get(i);
                GraphPath<String, IdentifiedWeightedEdge> path = DijkstraShortestPath.findPathBetween(g, source, goal);
                int pathDist = 0;
                String tempStreet = "";
                for(IdentifiedWeightedEdge e : path.getEdgeList()){
                    pathDist += g.getEdgeWeight(e);
                    tempStreet = eInfo.get(e.getId()).street;
                }
                if (pathDist < minDist) {
                    minDist = pathDist;
                    dest = goal;
                    streetName = tempStreet;
                }
            }
            source = dest;
            orderedPath.add(dest);
            orderedPathDistances.add(minDist);
            orderedPathStreets.add(streetName);
            visitsTemp.remove(dest);
        }
        //to exit
        int lastDistance = 0;
        String lastStreet = "";
        GraphPath<String, IdentifiedWeightedEdge> path =
                DijkstraShortestPath.findPathBetween(g, orderedPath.get(orderedPath.size()-1), start);
        for(IdentifiedWeightedEdge e : path.getEdgeList()){
            lastDistance += g.getEdgeWeight(e);
            lastStreet = eInfo.get(e.getId()).street;
        }
        orderedPath.add(start);
        orderedPathDistances.add(lastDistance);
        orderedPathStreets.add(lastStreet);

        //computation of total route paths
        List<Integer> totalRoutePaths = new ArrayList<>();
        totalRoutePaths.add(orderedPathDistances.get(0));
        for(int i = 1; i < orderedPathDistances.size(); i++){
            totalRoutePaths.add(i, totalRoutePaths.get(i-1) + orderedPathDistances.get(i));
        }
        TextView orderedVisitsView = this.findViewById(R.id.orderedVisits);
        orderedVisitsView.setText(orderedPath.get(0));

    }


}