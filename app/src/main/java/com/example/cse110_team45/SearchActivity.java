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
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    private SearchListAdapter adapter;
    private SearchData searchData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);

        searchData = new SearchData(vInfo);
        
        adapter = new SearchListAdapter();
        adapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }


    

    public void onSearchClick(View view) {

        EditText searchBar = findViewById(R.id.search_bar);
        String searchText = searchBar.getText().toString();

        List<String> recyclerList = new ArrayList<>(this.searchData.searchAlgo(searchText));


        adapter.setSearchListItems(recyclerList);
    }

    public void onItemClick(View view){
        TextView textView = (TextView) view;
        TextView exhibitCountView = findViewById(R.id.exhibitCount);


        String destination = textView.getText().toString();


        if(this.searchData.updateDestinationList(destination)){
            exhibitCountView.setText(String.format("%d", this.searchData.getExhibitCount()));
        }
        view.setBackgroundColor(0xFF73b4cd);

    }





    public void onPlanClick(View view) {
        Intent intent = new Intent(SearchActivity.this, plan.class);

        intent.putStringArrayListExtra("destinationList", (ArrayList<String>) this.searchData.getDestinationIdList());

        startActivity(intent);
    }


    public void onShowExhibitsClicked(View view) {
        List<String> recyclerList = new ArrayList<>(this.searchData.getSelectedExhibits());
        adapter.setSearchListItems(recyclerList);
    }
}