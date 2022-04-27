package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent directionsTestIntent = new Intent(this,DirectionDetailsActivity.class);
        startActivity(directionsTestIntent);
    }
}