package com.example.cse110_team45;

import static org.junit.Assert.assertEquals;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.jgrapht.Graph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class RoutePlanSummaryTest {

    static Intent nonEmptyIntent;
    static {
        nonEmptyIntent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<String>();
        destinationList.add("gorilla");
        destinationList.add("motmot");
        destinationList.add("fern_canyon");
        nonEmptyIntent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);
    }

    static Intent emptyIntent;
    static {
        emptyIntent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<String>();
        emptyIntent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);
    }






    @Rule
    public ActivityScenarioRule<plan> nonEmptyPlanTestRule = new ActivityScenarioRule<>(nonEmptyIntent);

    @Rule
    public ActivityScenarioRule<plan> emptyPlanTestRule = new ActivityScenarioRule<>(emptyIntent);


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void nonEmptyListPlanTest() {
        ActivityScenario<plan> scenario = nonEmptyPlanTestRule.getScenario();

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            Log.d("list", activity.PlanData.orderedPathNamesAndDist.toString());
            Log.d("item", ((TextView)firstVH.itemView.findViewById(R.id.plan_item_text)).getText().toString());
            assertEquals(activity.PlanData.orderedPathNamesAndDist.get(0).toString(), ((TextView)firstVH.itemView.findViewById(R.id.plan_item_text)).getText().toString());
            assertEquals(((planListAdapter)recyclerView.getAdapter()).getPlanItems(), activity.PlanData.orderedPathNamesAndDist);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Test
    public void emptyListPlanTest() {
        ActivityScenario<plan> scenario = emptyPlanTestRule.getScenario();

        Intent nonEmptyIntent = new Intent(ApplicationProvider.getApplicationContext(), plan.class);
        ArrayList<String> destinationList = new ArrayList<String>();

        nonEmptyIntent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);

        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.STARTED);
        scenario.moveToState(Lifecycle.State.RESUMED);

        scenario.onActivity(activity -> {
            RecyclerView recyclerView = activity.recyclerView;
            RecyclerView.ViewHolder firstVH = recyclerView.findViewHolderForAdapterPosition(0);
            RecyclerView.ViewHolder secondVH = recyclerView.findViewHolderForAdapterPosition(1);
            RecyclerView.ViewHolder thirdVH = recyclerView.findViewHolderForAdapterPosition(2);
            Log.d("list", activity.PlanData.orderedPathNamesAndDist.toString());
            Log.d("item", ((TextView)firstVH.itemView.findViewById(R.id.plan_item_text)).getText().toString());
            assertEquals(activity.PlanData.orderedPathNamesAndDist.get(0).toString(), ((TextView)firstVH.itemView.findViewById(R.id.plan_item_text)).getText().toString());
            assertEquals(((planListAdapter)recyclerView.getAdapter()).getPlanItems(), activity.PlanData.orderedPathNamesAndDist);

        });
    }

}
