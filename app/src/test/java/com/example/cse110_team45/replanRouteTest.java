package com.example.cse110_team45;


import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
importx androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)

public class replanRouteTest {


    static Intent nonEmptyIntent;
    static {

        String zooGraph = "zoo_graph.json";
        String vertexData = "exhibit_info.json";
        String edgeData = "trail_info.json";



        List<String> OrderedPathExhibitNames = new ArrayList<String>();
        List<GraphPath> orderedPathEdgeList = new ArrayList<GraphPath>();
        List<String> destinationList = new ArrayList<String>();

        destinationList.add("capuchin");
        destinationList.add("motmot");
        destinationList.add("gorilla");
        OrderedPathExhibitNames.add("entrance_exit_gate");
        OrderedPathExhibitNames.add("motmot");
        OrderedPathExhibitNames.add("gorilla");
        OrderedPathExhibitNames.add("capuchin");
        OrderedPathExhibitNames.add("entrance_exit_gate");


        nonEmptyIntent = new Intent(ApplicationProvider.getApplicationContext(), DirectionDetailsActivity.class);
        nonEmptyIntent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);
        nonEmptyIntent.putExtra("currentExhibitIndex",0);
        nonEmptyIntent.putStringArrayListExtra("orderedExhibitNames", (ArrayList<String>) OrderedPathExhibitNames);
        nonEmptyIntent.putExtra("orderedEdgeList", (Serializable) orderedPathEdgeList);
    }

    @Rule
    public ActivityScenarioRule<DirectionDetailsActivity> nonEmptyPlanTestRule = new ActivityScenarioRule<>(nonEmptyIntent);



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void replanRouteTest() {

        ActivityScenario scenario = nonEmptyPlanTestRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {

        });
    }

}
