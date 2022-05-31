package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SettingsActivity extends AppCompatActivity {
    boolean directionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();

        directionType = intent.getBooleanExtra("directionType", false);
    }

    public void onBriefClicked(View view) {
        directionType = false;

    }

    public void onDetailedClicked(View view) {
        directionType = true;


    }

    public void onExitSettingsClicked(View view) {
        Intent intent = new Intent();
        intent.putExtra("newDirectionType", directionType);
        setResult(RESULT_OK, intent);
        finish();

    }
}