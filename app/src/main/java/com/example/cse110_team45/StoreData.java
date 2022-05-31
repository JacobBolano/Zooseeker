package com.example.cse110_team45;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

public class StoreData {
    private ActivityType lastActivity;

    public static StoreData loadJSON(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            //GsonBuilder gb = new GsonBuilder().serializeNulls();
            //Gson gson = gb.create();
            Type type = new TypeToken<StoreData>() {
            }.getType();
            return gson.fromJson(reader, type);
        } catch(IOException e) {
            e.printStackTrace();
            return new StoreData();
        }
    }

    public void writeJSON(Context context, String path) {
        try {
            OutputStream output = context.openFileOutput(path,Context.MODE_PRIVATE);
            Writer writer = new OutputStreamWriter(output);
            Log.i("writing","JSON test");
            Gson gson = new Gson();
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastActivity(ActivityType lastActivity) {
        this.lastActivity = lastActivity;
    }

    public ActivityType getLastActivity() {
        return lastActivity;
    }
}
