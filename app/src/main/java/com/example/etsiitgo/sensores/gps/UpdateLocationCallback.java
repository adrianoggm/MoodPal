/**
 * @author Alejandro
 */
package com.example.etsiitgo.sensores.gps;

import android.location.Location;

public interface UpdateLocationCallback {
    void onCurrentLocationUpdated(Location location);
}
