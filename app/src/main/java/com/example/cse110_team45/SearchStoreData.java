package com.example.cse110_team45;

import java.util.List;

public class SearchStoreData extends StoreData {

    private List<String> destinationIdList;

    //store the exhibits we have already added
    public SearchStoreData(List<String> destinationIdList) {
        setLastActivity(ActivityType.SEARCH);
        this.destinationIdList = destinationIdList;
    }

    public void setDestinationIdList(List<String> destinationIdList) {
        this.destinationIdList = destinationIdList;
    }

    public List<String> getDestinationIdList() {
        return destinationIdList;
    }
}
