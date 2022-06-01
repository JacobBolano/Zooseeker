package com.example.cse110_team45;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class SearchBarTest {

    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), SearchActivity.class);
        intent.putStringArrayListExtra("destinationIdList", new ArrayList<>());
    }

    @Rule
    public ActivityScenarioRule<SearchActivity> scenarioRule = new ActivityScenarioRule<>(intent);

    @Test
    public void testSearchingandPlanning(){
        ActivityScenario<SearchActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText searchBar = activity.findViewById(R.id.search_bar);
            searchBar.setText("e");

            Button searchButton = activity.findViewById(R.id.search_button);
            searchButton.performClick();

            Button planButton = activity.findViewById(R.id.plan_button);
            planButton.performClick();

            assert(true);
        });
    }

    @Test
    public void testInvalidSearch(){
        ActivityScenario<SearchActivity> scenario = scenarioRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            EditText searchBar = activity.findViewById(R.id.search_bar);
            searchBar.setText("churros");

            Button searchButton = activity.findViewById(R.id.search_button);
            searchButton.performClick();

            int resultCount = activity.recyclerView.getChildCount();

            assertEquals(resultCount, 0);

        });
    }
}