package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DirectionDetailsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);

        DirectionAdapter adapter = new DirectionAdapter();

        recyclerView = findViewById(R.id.direction_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<MockIndividualDirection> testingList = new ArrayList<MockIndividualDirection>();
        testingList.add(new MockIndividualEdge("test street 1", 100));
        testingList.add(new MockIndividualNode("test street intersection"));
        testingList.add(new MockIndividualEdge("test street 2", 200));
        adapter.setIndividualDirectionListItems(testingList);

    }

    public void onNextClicked(View view) {
        // Call this activity again with shortened version of list (not including current exhibit/path)
        // TODO: pass shortened list
        Intent intent = new Intent(this, DirectionDetailsActivity.class);
        startActivity(intent);
    }
}