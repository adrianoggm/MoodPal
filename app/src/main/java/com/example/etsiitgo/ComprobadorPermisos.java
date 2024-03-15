/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class ComprobadorPermisos {
    /**
     * Devuelve si la aplicación tiene el permiso o no
     * @param context Contexto de la aplicación
     * @param permiso Permiso
     * @return Booleano
     */
    static public boolean permisoConcedido(Context context, String permiso) {
        int perm = ContextCompat.checkSelfPermission(context, permiso);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    static public boolean noPermisoConcedido(Context context, String permiso) {
        return !permisoConcedido(context, permiso);
    }

    static public boolean permisosConcedidos(Context context, String[] permisos) {
        boolean concedidos = true;

        for (String permiso : permisos) {
            concedidos &= permisoConcedido(context, permiso);
        }

        return concedidos;
    }

    static public boolean noPermisosConcedidos(Context context, String[] permisos) {
        return !permisosConcedidos(context, permisos);
    }

    static public boolean permisosConcedidos(int[] grantResults) {
        boolean concedidos = true;

        for (int result : grantResults) {
            concedidos &= (result == PackageManager.PERMISSION_GRANTED);
        }

        return concedidos;
    }

    static public boolean noPermisosConcedidos(int[] grantResults) {
        return !permisosConcedidos(grantResults);
    }
}
