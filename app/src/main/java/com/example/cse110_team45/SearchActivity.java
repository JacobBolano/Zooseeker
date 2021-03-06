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

        Map<String, ZooData.VertexInfo> vInfo = ZooData.loadVertexInfoJSON("exhibit_info.json", this);

        searchData = new SearchData(vInfo);

        // added MM 05/26
        //if called from restore populates the saved exhibits
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
        Log.i("test", "onPause is called");
        //SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lastActivity", "SEARCH");
        Gson gson = new Gson();
        String destinationIdListJSON = gson.toJson(searchData.getDestinationIdList());
        editor.putString("destinationIdListJSON", destinationIdListJSON);
        editor.apply();
    }
    

    /*
    after clicking search update the recycle view with all things that match the search bar
     */
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


        // tries to add the clicked one to the list and updates the number accordingly
        if(this.searchData.updateDestinationList(destination)){
            exhibitCountView.setText(String.format("%d", this.searchData.getExhibitCount()));
        }
        view.setBackgroundColor(0xFF73b4cd);

    }



    /*
    send the destination list to the next activity
     */
    public void onPlanClick(View view) {
        Intent intent = new Intent(SearchActivity.this, plan.class);

        intent.putStringArrayListExtra("destinationList", (ArrayList<String>) this.searchData.getDestinationIdList());

        startActivity(intent);
    }


    public void onShowExhibitsClicked(View view) {
        List<String> recyclerList = new ArrayList<>(this.searchData.getSelectedExhibits());
        adapter.setSearchListItems(recyclerList);
    }
  
    public void onClearClick(View view) {
        searchData.clearDestinationList();
        TextView exhibitCountView = findViewById(R.id.exhibitCount);
        exhibitCountView.setText(String.format("%d", this.searchData.getExhibitCount()));

    }
}