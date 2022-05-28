package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RestoreQueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_query);
    }


    public void onRestartClick(View view) {
        // Open search
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putStringArrayListExtra("destinationIdList", new ArrayList<>());
        startActivity(intent);
    }

    public void onRestoreClick(View view) {
        // store the page on close. Ideally we would have an option for every page, defaulting
        // unimportant pages to their calling pages (so if you open settings from Plan, we save the
        // state of plan when you close). In practice we'll probably only save on certain pages
        // (search, plan, and directions).
        // so store the page
        // for search, store destinationIDList
        // for plan, store visits (intent already has support for passing data)
        // for directions, store orderedEdgeLists and orderedExhibitNames, store currentExhibitIndex
        // Open search
        Intent intent;
        Gson gson = new Gson();
        Type arrayListStringType = new TypeToken<ArrayList<String>>() {
        }.getType();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String lastActivity = prefs.getString("lastActivity", "NONE"); // check activity before close
        // StoreData data = StoreData.loadJSON(this,"exit_state.json");
        switch (lastActivity) {
            case "SEARCH":
                intent = new Intent(this, SearchActivity.class);
                ArrayList<String> destinationIdList = gson.fromJson(prefs.getString("destinationIdListJSON", ""), arrayListStringType);
                intent.putStringArrayListExtra("destinationIdList", destinationIdList);
                break;
            case "PLAN":
                intent = new Intent(this, plan.class);
                ArrayList<String> visits = gson.fromJson(prefs.getString("visitsJSON", ""), arrayListStringType);
                intent.putStringArrayListExtra("visits", visits);
                break;
            case "DIRECTIONS":
                intent = new Intent(this, DirectionDetailsActivity.class);
                ArrayList<String> orderedExhibitNames = gson.fromJson(prefs.getString("orderedExhibitNamesJSON", ""), arrayListStringType);
                intent.putStringArrayListExtra("orderedExhibitNames", orderedExhibitNames);
                int currentExhibitIndex = prefs.getInt("currentExhibitIndex", 0);
                intent.putExtra("currentExhibitIndex", currentExhibitIndex);
                intent.putExtra("orderedEdgeListJSON", prefs.getString("orderedEdgeListJSON", ""));
                break;
            default:
                intent = new Intent(this, SearchActivity.class);
                intent.putStringArrayListExtra("destinationIdList", new ArrayList<>());
                startActivity(intent);
                break;
        }
        startActivity(intent);
    }
}