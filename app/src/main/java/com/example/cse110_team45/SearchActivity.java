package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class SearchActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    private SearchListAdapter adapter;
    private Set<String> keySet;
    private List<String> destinationList;
    Map<String, ZooData.VertexInfo> nodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("assets/sample_node_info.json");

        this.keySet = vInfo.keySet();

        this.destinationList = new ArrayList<>();

        adapter = new SearchListAdapter();
        adapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    public void onSearchClick(View view) {

        // TODO set all recycler colors to gray

        EditText searchBar = findViewById(R.id.search_bar);
        String searchText = searchBar.getText().toString();

        Set<String> returnSet = searchAlgo(searchText, this.keySet);

        List<String> recyclerList = new ArrayList<>(returnSet);


        adapter.setSearchListItems(recyclerList);
        //adapter.notifyDataSetChanged();






    }

    public void onItemClick(View view){
        TextView textView = (TextView) view;
        String destination = textView.getText().toString();
        System.out.println(destination);
        if(! destinationList.contains(destination)){
            destinationList.add(destination);
        }
        view.setBackgroundColor(0xFF73b4cd);

    }

    public Set<String> searchAlgo(String searchText, Set<String> keySet){

        Set<String> returnSet = new HashSet<>();
        searchText = searchText.toLowerCase(Locale.ROOT);
        for(String key : keySet){
            if(key.contains(searchText)){
                returnSet.add(key);
            }
        }
        return returnSet;
    }

    public void onPlanClick(View view) {
        Intent intent = new Intent(this, plan.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("destinationList", (ArrayList<String>) destinationList);

        System.out.println(destinationList);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}