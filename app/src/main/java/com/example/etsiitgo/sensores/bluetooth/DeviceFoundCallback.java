/**
 * @author Alejandro Torres Rodríguez
 */
package com.example.etsiitgo.sensores.bluetooth;

public interface DeviceFoundCallback {
    void onDeviceFound(String deviceName, String deviceAddress);
}
