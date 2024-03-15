package com.example.etsiitgo.navigation;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;

public class CheckpointDataBase {
    private Map<String, Location> data;

    public CheckpointDataBase() {
        data = new HashMap<>();
    }
    public Map<String, Location> getData() {
        return data;
    }
    public void addLocation(String name, Location location) {
        data.put(name,location);
    }

}