package com.example.cse110_team45;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.junit.Rule;
import org.junit.Test;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import android.content.Context;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.test.core.app.ApplicationProvider;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class RoutePlanTesting {
    static Intent intent;
    static {
        intent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<String>();
        destinationList.add("gorillas");
        destinationList.add("lions");
        destinationList.add("gators");
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("destinationList", (ArrayList<String>) destinationList);
        intent.putExtras(bundle);
    }


    @Rule
    public ActivityScenarioRule<plan> planTestRule = new ActivityScenarioRule<>(intent);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void basicTest(){

        ActivityScenario scenario = planTestRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            TextView OrderedVisitsView = activity.findViewById(R.id.orderedVisits);
            assertNotNull(OrderedVisitsView.getText());
        });



    }

}
