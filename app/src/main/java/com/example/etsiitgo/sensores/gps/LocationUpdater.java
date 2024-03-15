/**
 * @author Alejandro
 */

package com.example.etsiitgo.sensores.gps;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class LocationUpdater extends Thread {
    private static final String TAG = "LocationUpdater";
    private static final int UPDATE_INTERVAL_MS = 1000;
    private FusedLocationProviderClient locationProviderClient;
    private UpdateLocationCallback callback;
    private boolean updateLocation;

    public LocationUpdater(Activity activity, UpdateLocationCallback callback) {
        this.locationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        this.callback = callback;
        this.updateLocation = false;
    }

    public void run() {
        try {
            while (true) {
                getCurrentLocation();
                sleep(UPDATE_INTERVAL_MS);
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "LocationUpdater::run error: "+ e.getLocalizedMessage());
        }
    }

    public void setUpdateLocation(boolean updateLocation) {
        this.updateLocation = updateLocation;
    }

    private void getCurrentLocation() {
        if (!updateLocation)
            return;

        try {
            Task<Location> task = locationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null);
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        callback.onCurrentLocationUpdated(location);
                    }
                }
            });
        }
        catch (SecurityException e) {
            Log.e(TAG, "Error en LocationUpdater::getCurrentLocation: " + e.getLocalizedMessage());
        }
    }
}
