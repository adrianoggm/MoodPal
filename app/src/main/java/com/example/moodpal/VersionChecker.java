
package com.example.moodpal;

import android.os.Build;

public class VersionChecker {
    static public boolean isNewAndroidVersion() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S;
    }
}
