package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

        List<IndividualDirection> testingList = new ArrayList<IndividualDirection>();
        testingList.add(new IndividualEdge("test street 1", 100));
        testingList.add(new IndividualNode("test street intersection"));
        testingList.add(new IndividualEdge("test street 2", 200));
        adapter.setIndividualDirectionListItems(testingList);

    }
}