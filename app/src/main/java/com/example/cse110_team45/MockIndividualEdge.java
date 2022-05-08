package com.example.cse110_team45;
public class MockIndividualEdge implements MockIndividualDirection{
    String streetName;
    String nodeTo;
    int distance;

    public MockIndividualEdge(String name, String end, int dist){
        streetName = name;
        distance = dist;
        nodeTo = end;
    }

    @Override
    public String getDetails() {
        return streetName + distance;
    }
}
