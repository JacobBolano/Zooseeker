package com.example.cse110_team45;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(AndroidJUnit4.class)

public class SearchCapabilitiesTest {



    @Test
    public void SubstringWordTest(){

        SearchData searchData = new SearchData();

        Map<String, String> tester = new HashMap<>();
        tester.put("Apples", "apples_id");
        tester.put("Great Ape", "great_ape_id");
        tester.put("Gorilla", "gorilla_id");

        String key = "ape";

        searchData.setExhibitMap(tester);
        Set<String> results = searchData.searchAlgo(key);

        Set<String> expected = new HashSet<>();
        expected.add("Great Ape");
        assertEquals(expected, results);
    }

    @Test
    public void MixedCaseSubstringTest() {
        SearchData searchData = new SearchData();
        Map<String, String> tester = new HashMap<>();
        tester.put("Crows", "crows_id");
        tester.put("Chickens", "chickens_id");
        tester.put("Software Engineers", "software_engineers_id");

        String searchString = "enGIneErS";
        searchData.setExhibitMap(tester);
        Set<String> results = searchData.searchAlgo(searchString);
        HashSet<String> expectedSet = new HashSet<>();
        expectedSet.add("Software Engineers");
        assertEquals(expectedSet,expectedSet);
    }

    @Test
    public void EmptySearchTest() {
        SearchData searchData = new SearchData();
        Map<String, String> tester = new HashMap<>();
        tester.put("Monkeys", "monkeys_id");
        tester.put("Cows", "cows_id");
        tester.put("Giraffes", "giraffes_id");

        String emptyString = "";
        searchData.setExhibitMap(tester);
        Set<String> results = searchData.searchAlgo(emptyString);
        assertEquals(0, results.size());
    }

    @Test
    public void noMatchesSearchTest(){
        SearchData searchData = new SearchData();

        Map<String, String> tester = new HashMap<>();
        tester.put("Apples", "apples_id");
        tester.put("great ape", "great ape");
        tester.put("Gorilla", "gorilla");

        String key = "hawaii";

        searchData.setExhibitMap(tester);

        Set<String> results = searchData.searchAlgo(key);
        assertEquals(0, results.size());


    }

}
