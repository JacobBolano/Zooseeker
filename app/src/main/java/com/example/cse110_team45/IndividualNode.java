package com.example.cse110_team45;
public class IndividualNode implements IndividualDirection {
    String name;

    public IndividualNode(String name){
        this.name = name;
    }

    @Override
    public String getDetails() {
        return name;
    }
}
