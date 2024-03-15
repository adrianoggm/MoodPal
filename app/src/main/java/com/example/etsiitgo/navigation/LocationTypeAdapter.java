package com.example.etsiitgo.navigation;

import android.location.Location;
import android.os.Build;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class LocationTypeAdapter extends TypeAdapter<Location> {

    @Override
    public void write(JsonWriter out, Location location) throws IOException {
        out.beginObject();
        out.name("latitude").value(location.getLatitude());
        out.name("longitude").value(location.getLongitude());
        out.name("accuracy").value(location.getAccuracy());
        out.name("altitude").value(location.getAltitude());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            out.name("verticalAccuracyMeters").value(location.getVerticalAccuracyMeters());
        }
        // Add more properties if needed
        out.endObject();
    }

    @Override
    public Location read(JsonReader in) throws IOException {
        Location location = new Location("");

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "latitude":
                    location.setLatitude(in.nextDouble());
                    break;
                case "longitude":
                    location.setLongitude(in.nextDouble());
                    break;
                case "accuracy":
                    location.setAccuracy((float) in.nextDouble());
                    break;
                case "altitude":
                    location.setAltitude(in.nextDouble());
                    break;
                case "verticalAccuracyMeters":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        location.setVerticalAccuracyMeters((float) in.nextDouble());
                    }
                    break;
                // Add more cases if needed
                default:
                    in.skipValue(); // skip values of other properties
            }
        }
        in.endObject();

        return location;
    }
}
