package com.example.cse110_team45;

import org.jgrapht.GraphPath;

import java.util.List;

public class DirectionsStoreData extends StoreData{
    public List<GraphPath> getOrderedEdgeList() {
        return orderedEdgeList;
    }

    public void setOrderedEdgeList(List<GraphPath> orderedEdgeList) {
        this.orderedEdgeList = orderedEdgeList;
    }

    public List<String> getOrderedExhibitNames() {
        return orderedExhibitNames;
    }

    public void setOrderedExhibitNames(List<String> orderedExhibitNames) {
        this.orderedExhibitNames = orderedExhibitNames;
    }

    public int getCurrentExhibitIndex() {
        return currentExhibitIndex;
    }

    public void setCurrentExhibitIndex(int currentExhibitIndex) {
        this.currentExhibitIndex = currentExhibitIndex;
    }

    private List<GraphPath> orderedEdgeList;
    private List<String> orderedExhibitNames;
    private int currentExhibitIndex;

    public DirectionsStoreData(List<GraphPath> orderedEdgeList, List<String> orderedExhibitNames, int currentExhibitIndex) {
        this.orderedEdgeList = orderedEdgeList;
        this.orderedExhibitNames = orderedExhibitNames;
        this.currentExhibitIndex = currentExhibitIndex;
    }
}
