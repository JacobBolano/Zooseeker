package com.example.cse110_team45;


import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import android.content.Context;

import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.nio.json.JSONImporter;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;

@RunWith(AndroidJUnit4.class)

public class SearchCapabilitiesTest {



    @Test
    public void SubstringWordTest(){

        SearchActivity myActivity = new SearchActivity();

        Set<String> tester = new HashSet<>();
        tester.add("apples");
        tester.add("great ape");
        tester.add("gorilla");

        String key = "ape";

        myActivity.setKeySet(tester);
        Set<String> results = myActivity.searchAlgo(key);

        Set<String> expected = new HashSet<>();
        expected.add("great ape");
        assertEquals(expected, results);
    }

    @Test
    public void MixedCaseSubstringTest() {
        SearchActivity myActivity = new SearchActivity();
        Set<String> tester = new HashSet<>();
        tester.add("crows");
        tester.add("Chickens");
        tester.add("Software Engineers");

        String searchString = "enGIneErS";
        myActivity.setKeySet(tester);
        Set<String> results = myActivity.searchAlgo(searchString);
        HashSet<String> expectedSet = new HashSet<>();
        expectedSet.add("Software Engineers");
        assertEquals(expectedSet,expectedSet);
    }

    @Test
    public void EmptySearchTest() {
        SearchActivity myActivity = new SearchActivity();
        Set<String> tester = new HashSet<>();
        tester.add("monkeys");
        tester.add("cows");
        tester.add("giraffes");

        String emptyString = "";
        myActivity.setKeySet(tester);
        Set<String> results = myActivity.searchAlgo(emptyString);
        assertEquals(0, results.size());
    }

    @Test
    public void noMatchesSearchTest(){
        SearchActivity myActivity = new SearchActivity();

        Set<String> tester = new HashSet<>();
        tester.add("apples");
        tester.add("great ape");
        tester.add("gorilla");

        String key = "hawaii";

        myActivity.setKeySet(tester);

        Set<String> results = myActivity.searchAlgo(key);
        assertEquals(0, results.size());


    }

}
