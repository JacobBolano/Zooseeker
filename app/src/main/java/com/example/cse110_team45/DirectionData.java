package com.example.cse110_team45;

import android.util.Log;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectionData {

    List<GraphPath> orderedEdgeList;
    List<String> orderedExhibitNames;


    List<String> destinationList; // added for store/restore
    //prevNode is used so that we know what node we are walking towards and what node we are walking away from
    String prevNode;

    // false indicates brief styled directions while true is for detailed styled directions
    boolean directionType;

    //these are used to retrieve the streets, node names, and distances
    Graph<String, IdentifiedWeightedEdge> g;
    Map<String, ZooData.VertexInfo> vInfo;
    Map<String, ZooData.EdgeInfo> eInfo;

    String titleText;

    //this is used to keep track of where in our trip we are
    public int currentExhibitIndex = 0;

    public DirectionData(List<GraphPath> orderedEdgeList, List<String> orderedExhibitNames) {
        this.orderedEdgeList = orderedEdgeList;
        this.orderedExhibitNames = orderedExhibitNames;
        //initialize the direction type to brief
        this.directionType = false;

    }

    public boolean getDirectionType(){
        return directionType;
    }

    public void setDirectionType(boolean newDirectionType){
        directionType = newDirectionType;

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

    public int getCurrentExhibitIndex() {
        return currentExhibitIndex;
    }

    public void setDestinationList(List<String> destinationList) {
        this.destinationList = destinationList;
    }

    public List<String> getDestinationList() {
        return destinationList;
    }

    public void addGraphs(Graph<String, IdentifiedWeightedEdge> g,
                          Map<String, ZooData.VertexInfo> vInfo,
                          Map<String, ZooData.EdgeInfo> eInfo){
        this.g = g;
        this.vInfo = vInfo;
        this.eInfo = eInfo;

    }

    public List<MockIndividualEdge> getCurrentExhibitDirections() {
        List<MockIndividualEdge> output;
        if(directionType){ //call the proper directions based on if it is brief or false
            output =  getCurrentExhibitDirectionsDetailed();
        } else {
            output =   getCurrentExhibitDirectionsBrief();
        }
        //store that we have moved forward on our trip
        currentExhibitIndex++;
        return output;
    }

    public List<MockIndividualEdge> getCurrentExhibitDirectionsDetailed() {
        List<MockIndividualEdge> detailEdgeList = new ArrayList<MockIndividualEdge>();
        List<IdentifiedWeightedEdge> edgePath = orderedEdgeList.get(currentExhibitIndex).getEdgeList();
        Log.d("Which Exhibit", orderedExhibitNames.get(currentExhibitIndex));
        String localPrevNode= prevNode;

        for (int i = 0; i < edgePath.size(); i++) {
            String target =  vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name;
            //add the MockIndividualEdge that is to the node that does not match prevNode
            if(target.equals(localPrevNode)){
                detailEdgeList.add(new MockIndividualEdge(eInfo.get(edgePath.get(i).getId()).street, vInfo.get(g.getEdgeSource(edgePath.get(i)).toString()).name, g.getEdgeWeight(edgePath.get(i))));
            } else {
                detailEdgeList.add(new MockIndividualEdge(eInfo.get(edgePath.get(i).getId()).street, vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name, g.getEdgeWeight(edgePath.get(i))));
            }
            if (i == edgePath.size() - 1) {
                // get the name of the exhibit that we are going to to display at top of screen
                if(!localPrevNode.equals(vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name)){
                    titleText = vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name;
                }
                else{
                    titleText = vInfo.get(g.getEdgeSource(edgePath.get(i)).toString()).name;
                }
            }
            localPrevNode = detailEdgeList.get(i).getNodeTo();
        }
        prevNode = localPrevNode;
        return detailEdgeList;
    }

    public List<MockIndividualEdge> getCurrentExhibitDirectionsBrief() {
        List<MockIndividualEdge> detailEdgeList = new ArrayList<MockIndividualEdge>();
        List<IdentifiedWeightedEdge> edgePath = orderedEdgeList.get(currentExhibitIndex).getEdgeList();
        Log.d("Which Exhibit", orderedExhibitNames.get(currentExhibitIndex));

        String localPrevNode = prevNode;

        int j = 0;
        while(j< edgePath.size()){
            double currDistanceValue = g.getEdgeWeight(edgePath.get(j));
            String target = vInfo.get(g.getEdgeTarget(edgePath.get(j)).toString()).name;
            if(target.equals(localPrevNode)){
                target = vInfo.get(g.getEdgeSource(edgePath.get(j)).toString()).name;
            }
            //if the street of the next part of the directions is the same as the current street then combine them into one longer street
            while (j< (edgePath.size()-1) && eInfo.get(edgePath.get(j).getId()).street.equals(eInfo.get(edgePath.get(j+1).getId()).street)){
                localPrevNode = target;
                target = vInfo.get(g.getEdgeTarget(edgePath.get(j+1)).toString()).name;
                if(target.equals(localPrevNode)){
                    target = vInfo.get(g.getEdgeSource(edgePath.get(j+1)).toString()).name;
                }
                currDistanceValue += g.getEdgeWeight(edgePath.get(j+1));
                localPrevNode = target;
                j++;
            }
            detailEdgeList.add(new MockIndividualEdge(eInfo.get(edgePath.get(j).getId()).street, target ,currDistanceValue));
            localPrevNode = target;
            j++;
        }
        prevNode = localPrevNode;
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


    public List<MockIndividualEdge> skipExhibit(){
        Log.d("Skipping This Exhibit", orderedExhibitNames.get(currentExhibitIndex));
        //the actual removal of the current exhibit
        orderedEdgeList.remove(currentExhibitIndex - 1);
        orderedExhibitNames.remove(currentExhibitIndex);

        //decrease index since the current is removed shrinking the size
        currentExhibitIndex--;
        ZooData.VertexInfo sourceNode = getCurrentExhibit();
        String source;
        if(sourceNode.group_id != null){
            source = sourceNode.group_id;
        }
        else {
            source = sourceNode.id;
        }
        Log.d("Source", source);

        currentExhibitIndex++;
        ZooData.VertexInfo goalNode = getCurrentExhibit();
        String goal;
        if(goalNode.group_id != null){
            goal = goalNode.group_id;
        }
        else{
            goal = goalNode.id;
        }
        Log.d("Goal", goal);
        currentExhibitIndex--;

        //now that a exhibit was removed we need to recalculate the path to the new next exhibit
        GraphPath<String, IdentifiedWeightedEdge> pathBetween = DijkstraShortestPath.findPathBetween(g, source, goal);
        orderedEdgeList.set(currentExhibitIndex, pathBetween);
        System.out.println(orderedEdgeList);

        //return the MockIndividualEdge list that is the path to the new next exhibit
        return getCurrentExhibitDirections();
    }
    public void changeCurrentDirection(String exhibit){

        ZooData.VertexInfo sourceNode = vInfo.get(exhibit);
        String source;
        if(sourceNode.group_id != null){
            source = sourceNode.group_id;
        }
        else {
            source = sourceNode.id;
        }
        String dest = orderedExhibitNames.get(currentExhibitIndex);
        if(vInfo.get(dest).group_id != null){
            dest = vInfo.get(dest).group_id;
        }
        Log.d("Source", source);
        Log.d("dest", dest);

        GraphPath<String, IdentifiedWeightedEdge> pathBetween = DijkstraShortestPath.findPathBetween(g, source, dest);
        orderedEdgeList.set(currentExhibitIndex-1, pathBetween);
        Log.d("currIndex", ""+(currentExhibitIndex-1));
        System.out.println(orderedEdgeList);
    }



}
