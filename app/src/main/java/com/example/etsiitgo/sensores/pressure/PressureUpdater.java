package com.example.etsiitgo.sensores.pressure;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class PressureUpdater implements SensorEventListener {
    SensorManager sensorManager;
    Sensor sensor;
    UpdatePressureCallback callback;

    public PressureUpdater(Context context, UpdatePressureCallback callback) {
        this.callback = callback;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);

    }

    public void start() {
        this.sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
        this.sensorManager.unregisterListener(this, sensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float bars = event.values[0];
        callback.onPressureUpdated(bars);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
