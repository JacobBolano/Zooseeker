package com.example.cse110_team45;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class SearchData {


    private Set<String> exhibitSet;
    private List<String> destinationList;


    SearchData(Map<String, ZooData.VertexInfo> vInfo){
        this.exhibitSet = new HashSet<String>();

        for(ZooData.VertexInfo vertex : vInfo.values()) {
            if(vertex.kind.equals(ZooData.VertexInfo.Kind.EXHIBIT)) {
                this.exhibitSet.add(vertex.name);
            }
        }
        this.destinationList = new ArrayList<>();
    }

    SearchData(){}

    public List<String> getDestinationList() {
        return destinationList;
    }

    public Set<String> getExhibitSet() {
        return exhibitSet;
    }

    public int getExhibitCount(){
        if(destinationList == null){
            return 0;
        }
        return this.destinationList.size();
    }


    public void setDestinationList(List<String> destinationList){
        this.destinationList = new ArrayList<>(destinationList);
    }

    public void setExhibitSet(Set<String> exhibitSet){
        this.exhibitSet = new HashSet<>(exhibitSet);
    }


    public boolean updateDestinationList(String destination){
        if(! destinationList.contains(destination)){
            destinationList.add(destination);
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

            for(String exhibitName : this.exhibitSet){
                System.out.println(exhibitName);
                if(exhibitName.toLowerCase(Locale.ROOT).contains(searchText)){
                    returnSet.add(exhibitName);
                }
            }
            return returnSet;
        }
    }

}
