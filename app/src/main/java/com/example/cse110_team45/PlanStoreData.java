package com.example.cse110_team45;

import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.List;

public class PlanStoreData extends StoreData {

    private List<String> visits;

    public PlanStoreData(List<String> visits) {
        setLastActivity(ActivityType.PLAN);
        this.visits = visits;
    }

    public void setVisits(List<String> visits) {
        this.visits = visits;
    }


    public List<String> getVisits() {
        return visits;
    }

}