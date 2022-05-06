package com.example.cse110_team45;


import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.Set;

@RunWith(AndroidJUnit4.class)

public class SearchCapabilitiesTest {



    @Test
    public void SubstringWordTest(){

        SearchData searchData = new SearchData();

        Set<String> tester = new HashSet<>();
        tester.add("apples");
        tester.add("great ape");
        tester.add("gorilla");

        String key = "ape";

        searchData.setExhibitSet(tester);
        Set<String> results = searchData.searchAlgo(key);

        Set<String> expected = new HashSet<>();
        expected.add("great ape");
        assertEquals(expected, results);
    }

    @Test
    public void MixedCaseSubstringTest() {
        SearchData searchData = new SearchData();
        Set<String> tester = new HashSet<>();
        tester.add("crows");
        tester.add("Chickens");
        tester.add("Software Engineers");

        String searchString = "enGIneErS";
        searchData.setExhibitSet(tester);
        Set<String> results = searchData.searchAlgo(searchString);
        HashSet<String> expectedSet = new HashSet<>();
        expectedSet.add("Software Engineers");
        assertEquals(expectedSet,expectedSet);
    }

    @Test
    public void EmptySearchTest() {
        SearchData searchData = new SearchData();
        Set<String> tester = new HashSet<>();
        tester.add("monkeys");
        tester.add("cows");
        tester.add("giraffes");

        String emptyString = "";
        searchData.setExhibitSet(tester);
        Set<String> results = searchData.searchAlgo(emptyString);
        assertEquals(0, results.size());
    }

    @Test
    public void noMatchesSearchTest(){
        SearchData searchData = new SearchData();

        Set<String> tester = new HashSet<>();
        tester.add("apples");
        tester.add("great ape");
        tester.add("gorilla");

        String key = "hawaii";

        searchData.setExhibitSet(tester);

        Set<String> results = searchData.searchAlgo(key);
        assertEquals(0, results.size());


    }

}
