package com.example.cse110_team45;

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
    private List<String> destinationIdList;


    SearchData(Map<String, ZooData.VertexInfo> vInfo){
        this.exhibitMap = new HashMap<>();

        for(Map.Entry<String, ZooData.VertexInfo> entry: vInfo.entrySet()) {
            if(entry.getValue().kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) {
                this.exhibitMap.put(entry.getValue().name, entry.getKey());
            }
        }
        this.destinationIdList = new ArrayList<>();
    }

    SearchData(){}

    public List<String> getDestinationIdList() {
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

    public void setExhibitMap(Map<String, String> exhibitMap){
        this.exhibitMap = new HashMap<>(exhibitMap);
    }


    public boolean updateDestinationList(String destination){
        String destination_id = exhibitMap.get(destination);
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
            return returnSet;
        }
    }

}
