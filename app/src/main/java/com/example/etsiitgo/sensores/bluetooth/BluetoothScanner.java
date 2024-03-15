/**
 * @author Alejandro Torres Rodríguez
 */
package com.example.etsiitgo.sensores.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class BluetoothScanner {
    private BluetoothAdapter bluetoothAdapter;
    private DeviceFoundCallback deviceFoundCallback;

    public BluetoothScanner(DeviceFoundCallback deviceFoundCallback) {
        this.deviceFoundCallback = deviceFoundCallback;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void startScan(Activity activity) {
        IntentFilter discoveryFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(receiver, discoveryFilter);

        if (ActivadorBluetooth.estaActivado(activity)) {
            bluetoothAdapter.startDiscovery();
        }
        else {
            ActivadorBluetooth.activar(activity);
        }
    }

    public void stopScan(Activity activity) {
        bluetoothAdapter.cancelDiscovery();
        activity.unregisterReceiver(receiver);
    }

    public void stopScan() {
        bluetoothAdapter.cancelDiscovery();
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                try {
                    if (deviceFoundCallback != null) {
                        deviceFoundCallback.onDeviceFound(device.getName(), device.getAddress());
                    }
                }
                catch (SecurityException exception) {
                    exception.printStackTrace();
                }
            }
        }
    };
}
