package com.example.cse110_team45;
public class IndividualEdge implements IndividualDirection{
    String streetName;
    int distance;

    public IndividualEdge(String Name, int dist){
        streetName=Name;
        distance=dist;
    }

    @Override
    public String getDetails() {
        return streetName + distance;
    }
}
