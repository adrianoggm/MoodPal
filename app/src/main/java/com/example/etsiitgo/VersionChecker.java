/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo;

import android.os.Build;

public class VersionChecker {
    static public boolean isNewAndroidVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }
}
