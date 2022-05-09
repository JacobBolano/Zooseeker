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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DirectionDetailsActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public TextView textView;
    List<GraphPath> orderedEdgeList;
    List<String> orderedExhibitNames;
    String prevNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_details);

        Intent intent = getIntent();
        orderedExhibitNames = intent.getStringArrayListExtra("orderedExhibitNames");
        orderedEdgeList = (List<GraphPath>) intent.getSerializableExtra("orderedEdgeList");

        prevNode = intent.getStringExtra("prevNode");

        // 1. Load the graph...
        Graph<String, IdentifiedWeightedEdge> g = ZooData.loadZooGraphJSON("sample_zoo_graph.json", this);

        // 2. Load the information about our nodes and edges...
        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", this);
        Map<String, ZooData.EdgeInfo> eInfo = ZooData.loadEdgeInfoJSON("sample_edge_info.json", this);

        DirectionAdapter adapter = new DirectionAdapter();

        textView = findViewById(R.id.directionDetails);
        textView.setText(orderedExhibitNames.get(0));

        System.out.println("Direction details: " + orderedEdgeList.size());

        /*for (GraphPath currPath : orderedEdgeList) {
            System.out.println(currPath.getEdgeList());
            for (Object edge : currPath.getEdgeList()) {
                System.out.println(g.getEdgeWeight((IdentifiedWeightedEdge) edge));
            }
        }*/
        List<MockIndividualEdge> myList = new ArrayList<MockIndividualEdge>();
        List<IdentifiedWeightedEdge> edgePath = orderedEdgeList.get(0).getEdgeList();
        //List<String> nodePath = orderedEdgeList.get(0).getVertexList();
        for (int i = 0; i < edgePath.size(); i++) {
            String target =  vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name;
            if(target.equals(prevNode)){
                myList.add(new MockIndividualEdge(eInfo.get(edgePath.get(i).getId()).street, vInfo.get(g.getEdgeSource(edgePath.get(i)).toString()).name, g.getEdgeWeight(edgePath.get(i))));
            } else {
                myList.add(new MockIndividualEdge(eInfo.get(edgePath.get(i).getId()).street, vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name, g.getEdgeWeight(edgePath.get(i))));
            }
            if (i == edgePath.size() - 1) {
                if(prevNode != vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name){
                    textView.setText(vInfo.get(g.getEdgeTarget(edgePath.get(i)).toString()).name);
                } else{
                    textView.setText(vInfo.get(g.getEdgeSource(edgePath.get(i)).toString()).name);
                }
            }
            prevNode = myList.get(i).getNodeTo();
        }
        orderedExhibitNames.remove(0);
        orderedEdgeList.remove(0);

        recyclerView = findViewById(R.id.direction_lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.setIndividualDirectionListItems(myList);

    }

    public void onNextClicked(View view) {
        // Call this activity again with shortened version of list (not including current exhibit/path)
        //  pass shortened list
        Intent intent = new Intent(this, DirectionDetailsActivity.class);
        intent.putExtra("orderedEdgeList", (Serializable) orderedEdgeList);
        intent.putStringArrayListExtra("orderedExhibitNames", (ArrayList<String>) orderedExhibitNames);
        intent.putExtra("prevNode", prevNode);
        startActivity(intent);
    }
}