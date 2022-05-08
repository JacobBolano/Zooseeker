package com.example.cse110_team45;
public class MockIndividualEdge implements MockIndividualDirection{
    String streetName;
    int distance;

    public MockIndividualEdge(String name, int dist){
        streetName = name;
        distance = dist;
    }

    @Override
    public String getDetails() {
        return streetName + distance;
    }
}
