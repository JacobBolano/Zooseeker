package com.example.cse110_team45;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.GraphWalk;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkipButtonTest {

    static Intent emptyIntent;
    static {
        emptyIntent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<>();
        emptyIntent.putStringArrayListExtra("destinationList", destinationList);
    }

    @Rule
    public ActivityScenarioRule<plan> skipTestRule = new ActivityScenarioRule<>(emptyIntent);

    @Test
    public void skipTest() {
        ActivityScenario scenario = skipTestRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            ArrayList<String> visits = new ArrayList<>();
            visits.add("koi");
            visits.add("flamingo");
            visits.add("capuchin");
            // 1. Load the graph...
            Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", activity);
            System.out.println(visits.toString());
            // 2. Load the information about our nodes and edges...
            Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", activity);
            Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", activity);

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("zoo_graph.json",);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("trail_info.json", this);

        planData PlanData = new planData(g, vInfo, eInfo, visits);
        PlanData.pathFinding();
        PlanData.pathComputation();
        PlanData.orderedPathWithComp();

        DirectionData directionData = new DirectionData(PlanData.orderedPathEdgeList, PlanData.orderedPathExhibitNames);

        String preSkipExhibit = directionData.getCurrentExhibit().id;
        directionData.skipExhibit();
        String postSkipExhibit = directionData.getCurrentExhibit().id;

        assertNotEquals(preSkipExhibit, postSkipExhibit);
    }
}
