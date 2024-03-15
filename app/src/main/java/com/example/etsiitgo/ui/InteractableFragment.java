/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo.ui;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.sensores.bluetooth.BluetoothService;

public abstract class InteractableFragment extends Fragment {
    protected BluetoothService bluetoothService;

    public abstract void onTwoFingerGesture();
    public abstract void onSwipeRight();
    public abstract void onSwipeLeft();

    public abstract void onPacket(String packet);

    public void setBluetoothService(BluetoothService bluetoothService) {
        this.bluetoothService = bluetoothService;
    }
}
