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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(AndroidJUnit4.class)

public class ExhibitNumberTest {

    // Test thatInitial Count is Zero

    @Test
    public void testInitialCountIsZero(){
        SearchActivity searchActivity = new SearchActivity();

        assertEquals(searchActivity.getExhibitCount(), 0);
    }


    //Test that exhibit count increases by 1

    @Test
    public void testIncrementingExhibitCount(){
        SearchActivity searchActivity = new SearchActivity();

        List<String> testerDestinationList = new ArrayList<>(Arrays.asList("hawaii", "zoo", "Kansas"));

        searchActivity.setDestinationList(testerDestinationList);

        String testDestination = "alaska";
        searchActivity.updateDestinationList(testDestination);

        assertEquals(searchActivity.getExhibitCount(), testerDestinationList.size() + 1);
    }


    //Test taht exhibit count does not change if duplicate added
    @Test
    public void testDuplicateExhibit(){
        SearchActivity searchActivity = new SearchActivity();

        List<String> testerDestinationList = new ArrayList<>(Arrays.asList("hawaii", "zoo", "Kansas"));

        searchActivity.setDestinationList(testerDestinationList);

        String testDestination = "zoo";
        searchActivity.updateDestinationList(testDestination);

        assertEquals(searchActivity.getExhibitCount(), testerDestinationList.size());
    }

    @Test
    public void testFirstExhibitEntry(){
        SearchActivity searchActivity = new SearchActivity();

        List<String> testerDestinationList = new ArrayList<>();

        searchActivity.setDestinationList(testerDestinationList);

        String testDestination = "zoo";
        searchActivity.updateDestinationList(testDestination);

        assertEquals(searchActivity.getExhibitCount(), 1);
    }
}
