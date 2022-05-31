package com.example.cse110_team45;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jgrapht.GraphPath;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DirectionsFormatTest {

    List<GraphPath> testList;
    List<String> testList2;
    DirectionData directionData;

    @Before
    public void setUp() {
        testList = new ArrayList<>();
        testList2 = new ArrayList<>();
        directionData = new DirectionData(testList, testList2);
    }

    @Test
    public void defaultFormatTest() {
        assertFalse(directionData.getDirectionType());
    }

    @Test
    public void changeFormatTest() {
        directionData.setDirectionType(true);
        assertTrue(directionData.getDirectionType());
    }
}
