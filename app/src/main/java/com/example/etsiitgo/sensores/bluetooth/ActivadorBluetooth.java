/**
 * @author Alejandro Torres Rodríguez
 */
package com.example.etsiitgo.sensores.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.etsiitgo.ComprobadorPermisos;
import com.example.etsiitgo.R;

public class ActivadorBluetooth {
    public static final int REQUEST_ENABLE_BT = 123;
    public static final int REQUEST_PERMS_BT = 124;
    public static final String[] PERMISOS_BT = new String[] {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN
    };

    public static boolean estaActivado(Context context) {
        return BluetoothAdapter.getDefaultAdapter() != null &&
                BluetoothAdapter.getDefaultAdapter().isEnabled() &&
                ComprobadorPermisos.permisosConcedidos(context, PERMISOS_BT);
    }

    public static void activar(Activity activity) {
        if (bluetoothNotCompatible()) {
            Toast.makeText(activity, R.string.bluetooth_no_compatible, Toast.LENGTH_SHORT).show();
        }
        else if (ComprobadorPermisos.noPermisosConcedidos(activity, PERMISOS_BT)){
            activity.requestPermissions(PERMISOS_BT, REQUEST_PERMS_BT);
        }
        else if (bluetoothNotEnabled()) {
            Intent enableBlueetoth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBlueetoth, REQUEST_ENABLE_BT);
        }
    }

    private static boolean bluetoothNotEnabled() {
        return !BluetoothAdapter.getDefaultAdapter().isEnabled();
    }

    private static boolean bluetoothNotCompatible() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return bluetoothAdapter == null;
    }
}
