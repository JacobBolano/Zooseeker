package com.example.cse110_team45;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class planData {
    List<String> orderedPathExhibitNames;
    List<String> orderedPathStreets;
    List<GraphPath> orderedPathEdgeList;
    List<Integer> orderedPathDistances;
    Graph<String, IdentifiedWeightedEdge> g;
    Map<String, ZooData.VertexInfo>vInfo;
    Map<String, ZooData.EdgeInfo> eInfo;
    List<String> visits;

    PrintWriter out;

    planData(Graph<String, IdentifiedWeightedEdge> g, Map<String, ZooData.VertexInfo> vInfo,
             Map<String, ZooData.EdgeInfo> eInfo, List<String> visits){
        this.g = g;
        this.vInfo = vInfo;
        this.eInfo = eInfo;
        this.visits = visits;

        try
        {
            out = new PrintWriter(new BufferedWriter(new FileWriter("task.out")));
        }
        catch(IOException e){

        }

        orderedPathExhibitNames = new ArrayList<String>(); //send to direction details
        orderedPathStreets = new ArrayList<String>(); //use in route plan screen
        orderedPathEdgeList = new ArrayList<GraphPath>(); //send to direction details
        orderedPathDistances = new ArrayList<Integer>(); //use in route plan screen

    }

    public void pathFinding(){
        //Pathfinding
        String start = "entrance_exit_gate";
        orderedPathExhibitNames.add(start);
        GraphPath path2 = null;
        String source = start;
        String lastStreet = "";
        while(!visits.isEmpty()) {
            int minDist = Integer.MAX_VALUE;
            String dest = null;
            for(int i = 0; i < visits.size(); i++){
                String goal = visits.get(i);
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
                    path2 = path;
                    lastStreet = tempStreet;
                }
            }
            source = dest;
            orderedPathExhibitNames.add(dest);
            orderedPathDistances.add(minDist);
            orderedPathEdgeList.add(path2);
            orderedPathStreets.add(lastStreet);
            visits.remove(dest);
        }
        int dist = 0;
        String tempStreet = "";
        GraphPath<String, IdentifiedWeightedEdge> path3 = DijkstraShortestPath.findPathBetween(g, source, start);
        for(IdentifiedWeightedEdge e : path3.getEdgeList()){
            dist += g.getEdgeWeight(e);
            tempStreet = eInfo.get(e.getId()).street;
        }
        orderedPathExhibitNames.add(start);
        orderedPathDistances.add(dist);
        orderedPathEdgeList.add(path3);
        orderedPathStreets.add(tempStreet);




    }

    public void pathComputation() {
        List<Integer> totalRoutePaths = new ArrayList<>();
        totalRoutePaths.add(orderedPathDistances.get(0));
        for(int i = 1; i < orderedPathDistances.size(); i++){
            totalRoutePaths.add(i, totalRoutePaths.get(i-1) + orderedPathDistances.get(i));
        }
    }
}

