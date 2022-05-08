package com.example.cse110_team45;

import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

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
