package com.example.cse110_team45;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

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

        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("sample_node_info.json", this);

        searchData = new SearchData(vInfo);

        // added MM 05/26
        Intent intent = getIntent();
        if(!intent.getStringArrayListExtra("destinationIdList").isEmpty()) {
            searchData.setDestinationIdList(intent.getStringArrayListExtra("destinationIdList"));
            TextView exhibitCountView = findViewById(R.id.exhibitCount);
            exhibitCountView.setText(String.format("%d", this.searchData.getExhibitCount()));
        }

        adapter = new SearchListAdapter();
        adapter.setHasStableIds(true);

        recyclerView = findViewById(R.id.recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onPause() {
        // put a new scenario here for debug
        super.onPause();
        Log.i("test", "onDestroy is called");
        //SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "SEARCH");
        Gson gson = new Gson();
        String destinationIdListJSON = gson.toJson(searchData.getDestinationIdList());
        editor.putString("destinationIdListJSON", destinationIdListJSON);
        editor.apply();

        //SearchStoreData storeData = new SearchStoreData(searchData.getDestinationIdList());
        Log.i("test3", "problem in writeJSON?");
        //storeData.writeJSON(this, "exit_state.json");
        Log.i("test2", "is this called?");
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


}