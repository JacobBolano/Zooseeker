package com.example.cse110_team45;
public class MockIndividualEdge implements MockIndividualDirection{
    String streetName;
    String nodeTo;
    double distance;

    public MockIndividualEdge(String name, String end, double dist){
        streetName = name;
        distance = dist;
        nodeTo = end;
    }

    public String getNodeTo(){return nodeTo;}


    @Override
    public String getDetails() {

        return "Proceed along " + streetName + " for " + distance + "m to " + nodeTo;
    }
}
