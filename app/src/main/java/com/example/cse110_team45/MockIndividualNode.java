package com.example.cse110_team45;
public class MockIndividualNode implements MockIndividualDirection {
    String name;

    public MockIndividualNode(String name){
        this.name = name;
    }

    @Override
    public String getDetails() {
        return name;
    }
}
