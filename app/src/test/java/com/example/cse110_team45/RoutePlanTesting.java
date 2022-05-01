package com.example.cse110_team45;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import android.content.Context;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.json.JSONImporter;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class RoutePlanTesting {

    @Test
    public void nonEmptyMapTest(){
        Context context = ApplicationProvider.getApplicationContext();

        Graph<String, DefaultWeightedEdge> g = null;
        // Reads in graph from JSON file
        try {
            // Reads in graph from JSON file
            g = createGraphFromJSON(context, "res/sample_graph.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(g.edgeSet());
    }

    public static Graph<String, DefaultWeightedEdge> createGraphFromJSON(Context context, String path)
            throws IOException {
        Graph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        JSONImporter<String, DefaultWeightedEdge> jsonImporter = new JSONImporter<>();
        jsonImporter.setVertexFactory(label -> label);

        InputStream input = context.getAssets().open(path);
        Reader reader = new InputStreamReader(input);
        jsonImporter.importGraph(g, reader);

        return g;
    }
}
