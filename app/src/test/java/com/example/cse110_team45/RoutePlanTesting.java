package com.example.cse110_team45;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.room.Index;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jgrapht.Graph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RunWith(AndroidJUnit4.class)
public class RoutePlanTesting {
    static Intent nonEmptyIntent;
    static {
        nonEmptyIntent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<String>();

        nonEmptyIntent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);
    }

    static Intent emptyIntent;
    static {
        emptyIntent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<String>();
        emptyIntent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);

    }



    planData PlanData;

    @Rule
    public ActivityScenarioRule<plan> nonEmptyPlanTestRule = new ActivityScenarioRule<>(nonEmptyIntent);

    @Rule
    public ActivityScenarioRule<plan> emptyPlanTestRule = new ActivityScenarioRule<>(emptyIntent);


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void nonEmptyPlanTest() {

        ActivityScenario scenario = nonEmptyPlanTestRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            List<String> visits = new ArrayList<String>();
            visits.add("gorillas");
            visits.add("lions");
            visits.add("gators");
            // 1. Load the graph...
            Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", activity);
            System.out.println(visits.toString());
            // 2. Load the information about our nodes and edges...
            Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", activity);
            Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", activity);

            PlanData = new planData(g, vInfo, eInfo, visits);
            PlanData.pathFinding();
            assertEquals(5, PlanData.orderedPathExhibitNames.size());
            assertEquals("entrance_exit_gate", PlanData.orderedPathExhibitNames.get(0));
            assertEquals("entrance_exit_gate", PlanData.orderedPathExhibitNames.get(PlanData.orderedPathExhibitNames.size()-1));
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void emptyPlanTest() {

        ActivityScenario scenario = emptyPlanTestRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            List<String> visits = nonEmptyIntent.getStringArrayListExtra("destinationList");

            // 1. Load the graph...
            Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", activity);

            // 2. Load the information about our nodes and edges...
            Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", activity);
            Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", activity);

            PlanData = new planData(g, vInfo, eInfo, visits);
            PlanData.pathFinding();
            assertEquals(2, PlanData.orderedPathExhibitNames.size());
            assertEquals("entrance_exit_gate", PlanData.orderedPathExhibitNames.get(0));
            assertEquals("entrance_exit_gate", PlanData.orderedPathExhibitNames.get(PlanData.orderedPathExhibitNames.size()-1));
        });
    }
}
