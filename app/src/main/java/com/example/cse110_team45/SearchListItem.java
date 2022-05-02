package com.example.cse110_team45;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "search_list_item")
public class SearchListItem {

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    @NonNull
    public String text;

    SearchListItem(String text){
        this.text = text;
    }
}
