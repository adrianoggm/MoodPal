/**
 * @author Alejandro
 */

package com.example.etsiitgo.sensores.orientation;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class OrientationUpdater implements SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;
    UpdateOrientationCallback callback;

    public OrientationUpdater(Context context, UpdateOrientationCallback callback) {
        this.callback = callback;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    }

    public void start() {
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        this.sensorManager.unregisterListener(this, sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int degree = Math.round(event.values[0]);
        callback.onOrientationUpdated(degree);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
