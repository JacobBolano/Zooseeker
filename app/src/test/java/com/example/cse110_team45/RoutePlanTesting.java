package com.example.cse110_team45;

import org.junit.Test;

import static org.junit.Assert.*;
import plan;

public class RoutePlanTesting {
    @Test
    public void nonEmptyMapTest(){
        Graph<String, DefaultWeightedEdge> g = null;
        // Reads in graph from JSON file
        g = createGraphFromJSON(this, "res/sample_graph.json");
        AssertEquals(g.getAllEdges(), 0);
    }
}
