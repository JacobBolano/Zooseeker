package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DirectionDetailsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);

        Intent intent = getIntent();
        List<String> orderedExhibitNames = intent.getStringArrayListExtra("orderedExhibitNames");
        List<GraphPath> orderedEdgeList = intent.getExtras().getParcelable("orderedEdgeList");

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", this);

        DirectionAdapter adapter = new DirectionAdapter();

        textView = findViewById(R.id.directionDetails);
        textView.setText(orderedExhibitNames.get(0));

        List<MockIndividualEdge> mylist = new ArrayList<MockIndividualEdge>();


        recyclerView = findViewById(R.id.direction_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        List<MockIndividualDirection> testingList = new ArrayList<MockIndividualDirection>();
        //testingList.add(new MockIndividualEdge("test street 1", 100));
        //testingList.add(new MockIndividualNode("test street intersection"));
        //testingList.add(new MockIndividualEdge("test street 2", 200));
        //adapter.setIndividualDirectionListItems(testingList);

    }

    public void onNextClicked(View view) {
        // Call this activity again with shortened version of list (not including current exhibit/path)
        // TODO: pass shortened list
        Intent intent = new Intent(this, DirectionDetailsActivity.class);
        startActivity(intent);
    }
}