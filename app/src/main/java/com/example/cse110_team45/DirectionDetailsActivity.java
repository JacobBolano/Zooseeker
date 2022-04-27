package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DirectionDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);
        TextView animalTitle = (TextView) findViewById(R.id.animalTitle);
        animalTitle.setText("To the Bears:");
        TextView detailedDirectionText = (TextView) findViewById(R.id.detailedDirectionText);
        detailedDirectionText.setText("Proceed from Front Street down Treetops Way, 200 ft on your left.\n\nTurn Right on Bear Blvd, 100 ft ahead.\n\nThe Exhibit will be directly on your left.");
    }
}