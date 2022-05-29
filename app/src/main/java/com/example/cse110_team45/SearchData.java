package com.example.cse110_team45;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SearchData {


    // Key: Name
    // Value: ID
    private Map<String, String> exhibitMap;
    private Map<String, List<String>> tagMap;
    private List<String> destinationIdList;


    SearchData(Map<String, ZooData.VertexInfo> vInfo){
        this.exhibitMap = new HashMap<>();
        this.tagMap = new HashMap<>();

        for(Map.Entry<String, ZooData.VertexInfo> entry: vInfo.entrySet()) {

            if (entry.getKey().equals("fern_canyon")){
                ZooData.VertexInfo value = entry.getValue();
                Log.d("kind", value.kind.toString());
                System.out.println(value.group_id);
                Log.d("name", value.name);
                Log.d("tags", value.tags.toString());
                Log.d("lat", Float.toString(value.lat));
                Log.d("lng", Float.toString(value.lng));
            }
            if(entry.getValue().kind == null){
                continue;
            }
            if(entry.getValue().kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) {
                this.exhibitMap.put(entry.getValue().name, entry.getKey());
                this.tagMap.put(entry.getValue().name, entry.getValue().tags);
            }
        }
        this.destinationIdList = new ArrayList<>();
    }

    SearchData(){}

    public List<String> getDestinationIdList() {
        System.out.println(destinationIdList);
        return destinationIdList;
    }

    public Map<String, String> getExhibitMap() {
        return exhibitMap;
    }

    public int getExhibitCount(){
        if(destinationIdList == null){
            return 0;
        }
        return this.destinationIdList.size();
    }


    public void setDestinationIdList(List<String> destinationIdList){
        this.destinationIdList = new ArrayList<>(destinationIdList);
    }

    public void setSearchMaps(Map<String, String> exhibitMap, Map<String, List<String>> tagMap){
        this.exhibitMap = new HashMap<>(exhibitMap);
        this.tagMap = new HashMap<>(tagMap);
    }


    public boolean updateDestinationList(String destination){

        String destination_id = exhibitMap.get(destination);
        Log.d("destination_id",destination_id);
        if(! destinationIdList.contains(destination_id)){
            destinationIdList.add(destination_id);
            return true;
        }
        return false;

    }

    public Set<String> searchAlgo(String searchText){

        Set<String> returnSet = new HashSet<>();
        if(searchText.equals("")) {
            return returnSet;
        }
        else {
            searchText = searchText.toLowerCase(Locale.ROOT);

            for(Map.Entry<String, String> exhibitEntry : this.exhibitMap.entrySet()){
                if(exhibitEntry.getKey().toLowerCase(Locale.ROOT).contains(searchText)){
                    returnSet.add(exhibitEntry.getKey());
                }
            }
            for(Map.Entry<String, List<String>> tagEntry: this.tagMap.entrySet()){
                for(String tag : tagEntry.getValue()){
                    if(tag.toLowerCase(Locale.ROOT).equals(searchText)){
                        returnSet.add(tagEntry.getKey());
                    }
                }
            }
            return returnSet;
        }
    }

}
