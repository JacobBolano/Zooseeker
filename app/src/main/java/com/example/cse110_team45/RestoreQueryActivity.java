package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RestoreQueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_query);
    }


    public void onRestartClick(View view) {
        // Open search
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void onRestoreClick(View view) {
    }
}