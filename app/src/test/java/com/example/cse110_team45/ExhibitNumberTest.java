package com.example.cse110_team45;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(AndroidJUnit4.class)

public class ExhibitNumberTest {

    // Test thatInitial Count is Zero

    @Test
    public void testInitialCountIsZero(){
        SearchData searchData = new SearchData();


        assertEquals(searchData.getExhibitCount(), 0);
    }


    //Test that exhibit count increases by 1

    @Test
    public void testIncrementingExhibitCount(){
        SearchData searchData = new SearchData();

        List<String> testerDestinationList = new ArrayList<>(Arrays.asList("hawaii", "zoo", "Kansas"));

        Map<String, String> testExhibitMap = new HashMap<>();
        testExhibitMap.put("alaska", "alaska_id");

        searchData.setExhibitMap(testExhibitMap);
        searchData.setDestinationIdList(testerDestinationList);

        String testDestination = "alaska";
        searchData.updateDestinationList(testDestination);

        assertEquals(searchData.getExhibitCount(), testerDestinationList.size() + 1);
    }


    //Test taht exhibit count does not change if duplicate added
    @Test
    public void testDuplicateExhibit(){
        SearchData searchData = new SearchData();

        List<String> testerDestinationList = new ArrayList<>(Arrays.asList("hawaii_id", "zoo_id", "Kansas_id"));

        Map<String, String> testExhibitMap = new HashMap<>();
        testExhibitMap.put("zoo", "zoo_id");

        searchData.setExhibitMap(testExhibitMap);

        searchData.setDestinationIdList(testerDestinationList);

        String testDestination = "zoo";
        searchData.updateDestinationList(testDestination);

        assertEquals(searchData.getExhibitCount(), testerDestinationList.size());
    }

    @Test
    public void testFirstExhibitEntry(){
        SearchData searchData = new SearchData();

        List<String> testerDestinationList = new ArrayList<>();
        Map<String, String> testExhibitMap = new HashMap<>();
        testExhibitMap.put("zoo", "zoo_id");

        searchData.setExhibitMap(testExhibitMap);
        searchData.setDestinationIdList(testerDestinationList);

        String testDestination = "zoo";
        searchData.updateDestinationList(testDestination);

        assertEquals(searchData.getExhibitCount(), 1);
    }
}
