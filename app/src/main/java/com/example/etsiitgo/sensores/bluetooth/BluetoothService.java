/**
 * @author Alejandro Torres Rodríguez
 */
package com.example.etsiitgo.sensores.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.etsiitgo.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {
    private static final String TAG = "BluetoothService";
    private static final UUID TOTEM_UUID = UUID.fromString("500eed0a-b705-4713-bc31-4452f660c814");

    private BluetoothAdapter bluetoothAdapter;
    private Handler handler;
    private ConnectedThread connectedThread;
    private ConnectThread connectThread;
    private BluetoothState state;

    public BluetoothService(Handler handler) {
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.state = BluetoothState.NONE;
        this.handler = handler;
        this.connectedThread = null;
        this.connectThread = null;
    }

    public synchronized BluetoothState getState() {
        return state;
    }

    public synchronized void connect(BluetoothDevice device) {
        finishThreads();

        connectThread = new ConnectThread(device);
        connectThread.start();

        // Actualizar interfaz?
        updateInterfaceTitle();
    }

    public synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        finishThreads();

        connectedThread = new ConnectedThread(socket);
        connectedThread.start();

        updateInterfaceTitle();
    }

    public synchronized void stop() {
        finishThreads();

        state = BluetoothState.NONE;

        updateInterfaceTitle();
    }

    public void write(byte[] out) {
        ConnectedThread r;

        // Crea una copia del thread conectado
        // de forma síncrona para evitar condiciones de carrera
        synchronized (this) {
            if (state != BluetoothState.CONNECTED)
                return;

            r = connectedThread;
        }

        r.write(out);
    }

    private synchronized void finishThreads() {
        if (connectThread != null) {
            connectThread.cancel();
            connectThread = null;
        }

        if (connectedThread != null) {
            connectedThread.cancel();
            connectedThread = null;
        }
    }

    private synchronized void updateInterfaceTitle() {
        handler.obtainMessage(Constants.MESSAGE_STATE_CHANGE, getState().ordinal(), -1).sendToTarget();
    }

    private void connectionFailed() {
        Message msg = handler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "No se pudo establecer conexión con el totem");
        msg.setData(bundle);
        //handler.sendMessage(msg);

        state = BluetoothState.NONE;

        updateInterfaceTitle();
    }

    private void connectionLost() {
        Message msg = handler.obtainMessage(Constants.MESSAGE_TOAST);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TOAST, "Conexión perdida con el totem");
        msg.setData(bundle);
        handler.sendMessage(msg);

        state = BluetoothState.NONE;

        updateInterfaceTitle();
    }

    private class ConnectedThread extends Thread {
        private BluetoothSocket socket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;

            try {
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
                state = BluetoothState.CONNECTED;
            }
            catch (IOException e) {
                Log.e(TAG, "Error al obtener los flujos de entrada y salida de bluetooth", e);
            }
        }

        public void run() {
            byte[] buffer = new byte[8192];
            int bytes;

            while (state == BluetoothState.CONNECTED) {
                try {
                    bytes = inputStream.read(buffer);

                    handler.obtainMessage(Constants.MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                }
                catch (IOException e) {
                    Log.e(TAG, "Socket bluetooth desconectado", e);
                    connectionLost();
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                outputStream.write(buffer);
            }
            catch (IOException e) {
                Log.e(TAG, "Error al escribir en el socket bluetooth", e);
            }
        }

        public void cancel() {
            try {
                socket.close();
            }
            catch (IOException e) {
                Log.e(TAG, "Error al cerrar socket bluetooth", e);
            }
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket socket;
        private BluetoothDevice device;

        private void initSocket() {
            socket = null;

            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(TOTEM_UUID);
                state = BluetoothState.CONNECTING;
            }
            catch (IOException e) {
                Log.e(TAG, "Error en creación de socket bluetooth", e);
            }
        }

        public ConnectThread(BluetoothDevice device) {
            this.device = device;
            this.socket = null;

            initSocket();
        }

        public void run() {
            bluetoothAdapter.cancelDiscovery();

            try {
                socket.connect();
            }
            catch (IOException connectException) {
                Log.e(TAG, "Error al conectar socket bluetooth", connectException);

                try {
                    socket.close();
                }
                catch (IOException closeException) {
                    Log.e(TAG, "Error al cerrar socket bluetooth", closeException);
                }

                connectionFailed();
                return;
            }

            // Reseteamos el thread de conectarse porque ya hemos terminado la conexion
            synchronized (BluetoothService.this) {
                connectThread = null;
            }

            connected(socket, device);
        }

        public void cancel() {
            try {
                socket.close();
            }
            catch (IOException e) {
                Log.e(TAG, "Error al cerrar el socket del hilo de conexion", e);
            }
        }
    }

}
