package com.example.cse110_team45;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class plan extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("assets/sample_zoo_graph.json");

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("assets/sample_node_info.json");
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("assets/sample_edge_info.json");

        //will be imported from search bar
        List<String> visits = new ArrayList<String>();
        List<String> orderedPath = new ArrayList<String>();
        List<String> orderedPathStreets = new ArrayList<String>();
        List<Integer> orderedPathDistances = new ArrayList<Integer>();


        String start = "entrance_exit_gate";
        orderedPath.add(start);
        String streetName = "";
        List<String> visitsTemp = new ArrayList<String>();
        for(int i = 0; i < visits.size(); i++) {
            visitsTemp.set(i, visits.get(i));
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

    }

}