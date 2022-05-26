package com.example.cse110_team45;

import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectionData {

    List<GraphPath> orderedEdgeList;
    List<String> orderedExhibitNames;
    String prevNode;

    Graph<String, IdentifiedWeightedEdge> g;
    Map<String, ZooData.VertexInfo> vInfo;
    Map<String, ZooData.EdgeInfo> eInfo;

    String titleText;

    public int currentExhibitIndex = 0;

    public DirectionData(List<GraphPath> orderedEdgeList, List<String> orderedExhibitNames) {
        this.orderedEdgeList = orderedEdgeList;
        this.orderedExhibitNames = orderedExhibitNames;
    }

    public void setOrderedEdgeList(List<GraphPath> orderedEdgeList) {
        this.orderedEdgeList = orderedEdgeList;
    }

    public void setOrderedExhibitNames(List<String> orderedExhibitNames) {
        this.orderedExhibitNames = orderedExhibitNames;
    }

    public void setCurrentExhibitIndex(int currentExhibitIndex) {
        this.currentExhibitIndex = currentExhibitIndex;
    }

    public void addGraphs(Graph<String, IdentifiedWeightedEdge> g,
                          Map<String, ZooData.VertexInfo> vInfo,
                          Map<String, ZooData.EdgeInfo> eInfo){
        this.g = g;
        this.vInfo = vInfo;
        this.eInfo = eInfo;

    }

    public List<MockIndividualEdge> getCurrentExhibitDirections(){
        List<MockIndividualEdge> detailEdgeList = new ArrayList<MockIndividualEdge>();
        List<IdentifiedWeightedEdge> edgePath = orderedEdgeList.get(currentExhibitIndex).getEdgeList();
        Log.d("Which Exhibit", orderedExhibitNames.get(currentExhibitIndex + 1));

        for (int i = 0; i < edgePath.size(); i++) {
            String target =  vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name;
            if(target.equals(prevNode)){
                detailEdgeList.add(new MockIndividualEdge(eInfo.get(edgePath.get(i).getId()).street, vInfo.get(g.getEdgeSource(edgePath.get(i)).toString()).name, g.getEdgeWeight(edgePath.get(i))));
            } else {
                detailEdgeList.add(new MockIndividualEdge(eInfo.get(edgePath.get(i).getId()).street, vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name, g.getEdgeWeight(edgePath.get(i))));
            }
            if (i == edgePath.size() - 1) {
                if(prevNode != vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name){
                    titleText = vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name;
                }
                else{
                    titleText = vInfo.get(g.getEdgeSource(edgePath.get(i)).toString()).name;
                }
            }
            prevNode = detailEdgeList.get(i).getNodeTo();
        }
        currentExhibitIndex++;
        return detailEdgeList;
    }

    public String getTitleText(){
        titleText =vInfo.get(orderedExhibitNames.get(currentExhibitIndex)).name;
        return titleText;
    }

    public ZooData.VertexInfo getCurrentExhibit(){
        return vInfo.get(orderedExhibitNames.get(currentExhibitIndex));
    }


    public List<MockIndividualEdge> getPreviousDirections(){
        currentExhibitIndex--;

        //FIXME what if group_id ??

        ZooData.VertexInfo currentExhibit = getCurrentExhibit();
        String exhibitDestinationName;

        if(currentExhibit.group_id !=null){
            exhibitDestinationName = vInfo.get(currentExhibit.group_id).name;
        }
        else{
            exhibitDestinationName = currentExhibit.name;
        }


        prevNode = exhibitDestinationName;

        List<MockIndividualEdge> detailEdgeList = getCurrentExhibitDirections();

        List<MockIndividualEdge> reverseEdgeList = new ArrayList<>();



        String nodeTo = exhibitDestinationName;

        for(MockIndividualEdge edge : detailEdgeList){
            reverseEdgeList.add(new MockIndividualEdge(edge.streetName, nodeTo, edge.distance));
            nodeTo = edge.nodeTo;
            Log.d("New nodeTo", nodeTo);
        }

        currentExhibitIndex--;
        prevNode = exhibitDestinationName;

        Collections.reverse(reverseEdgeList);
        return reverseEdgeList;
    }




}
