package com.example.cse110_team45;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class StoreTest2 {
    Context context;

    static Intent emptyIntent;
    static {
        emptyIntent = new Intent(ApplicationProvider.getApplicationContext(), RestoreQueryActivity.class);
    }

    @Before
    public void setUp() {
        context = getInstrumentation().getTargetContext().getApplicationContext();
    }

    @Rule
    public ActivityScenarioRule<RestoreQueryActivity> storeTestRule = new ActivityScenarioRule<>(emptyIntent);

    @Test
    public void restoreTest() {
        ArrayList<String> visits = new ArrayList<>();
        visits.add("koi");
        visits.add("flamingo");
        visits.add("capuchin");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "PLAN");
        Gson gson = new Gson();
        String destinationListJSON = gson.toJson(visits);
        editor.putString("destinationListJSON",destinationListJSON);
        editor.apply();

        ActivityScenario<RestoreQueryActivity> scenario = storeTestRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
                    Intent testIntent = activity.getRestoreIntent(prefs);
                    ArrayList<String> testDestinations = testIntent.getStringArrayListExtra("destinationList");
                    assertEquals(visits,testDestinations);
        });
    }
}
