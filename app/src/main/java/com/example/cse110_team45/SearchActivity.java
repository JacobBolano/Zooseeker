package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SearchActivity extends AppCompatActivity {

    public RecyclerView recyclerView;

    private SearchListAdapter adapter;
    private Set<String> keySet;
    private List<String> destinationList;
    private int exhibitCount;
    Map<String, ZooData.VertexInfo> nodeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", this);

        this.exhibitCount = 0;
        this.keySet = new HashSet<String>();
        //this.keySet = vInfo.keySet();

        for(ZooData.VertexInfo vertex : vInfo.values()) {
            if(vertex.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) {
                this.keySet.add(vertex.name);
            }
        }
        this.destinationList = new ArrayList<>();

        adapter = new SearchListAdapter();
        adapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }


    public void setDestinationList(List<String> destinationList){
        this.destinationList = destinationList;
    }

    public void setKeySet(Set<String> keySet){
        this.keySet = keySet;
    }

    public void onSearchClick(View view) {

        // TODO set all recycler colors to gray

        EditText searchBar = findViewById(R.id.search_bar);
        String searchText = searchBar.getText().toString();

        Set<String> returnSet = searchAlgo(searchText);

        List<String> recyclerList = new ArrayList<>(returnSet);


        adapter.setSearchListItems(recyclerList);
        //adapter.notifyDataSetChanged();






    }

    public void onItemClick(View view){
        TextView textView = (TextView) view;
        TextView exhibitCountView = findViewById(R.id.exhibitCount);


        String destination = textView.getText().toString();
        System.out.println(destination);


        if(updateDestinationList(destination)){
            exhibitCountView.setText(String.format("%d", this.exhibitCount));
        }
        view.setBackgroundColor(0xFF73b4cd);

    }

    public boolean updateDestinationList(String destination){
        if(! destinationList.contains(destination)){
            destinationList.add(destination);
            this.exhibitCount++;
            return true;
        }
        return false;

    }

    public Set<String> searchAlgo(String searchText){

        Set<String> returnSet = new HashSet<>();
        searchText = searchText.toLowerCase(Locale.ROOT);

        for(String key : this.keySet){
            System.out.println(key);
            if(key.toLowerCase(Locale.ROOT).contains(searchText)){
                returnSet.add(key);
            }
        }
        return returnSet;
    }

    public void onPlanClick(View view) {
        Intent intent = new Intent(SearchActivity.this, plan.class);

        intent.putStringArrayListExtra("destinationList", (ArrayList<String>) destinationList);

        System.out.println(destinationList);
        startActivity(intent);
    }

    public int getExhibitCount(){
        return this.exhibitCount;
    }
}