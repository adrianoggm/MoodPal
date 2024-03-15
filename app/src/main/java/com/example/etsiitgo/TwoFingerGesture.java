/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo;

import android.graphics.PointF;

public class TwoFingerGesture {
    private static final float NEARBY_VERTICAL_THRESHOLD = 200f;
    private static final float GESTURE_VERTICAL_MIN_DISTANCE = 500f;
    private PointF firstFingerInitialPos;
    private PointF secondFingerInitialPos;
    private boolean isGestureHappening;

    public TwoFingerGesture() {
        this.firstFingerInitialPos = null;
        this.secondFingerInitialPos = null;
        this.isGestureHappening = false;
    }

    public void setFirstFingerInitialPos(float x, float y) {
        firstFingerInitialPos = new PointF(x, y);
    }

    public void setSecondFingerInitialPos(float x, float y) {
        secondFingerInitialPos = new PointF(x, y);
    }

    public void onGestureStart() {
        // Comprobamos si los dos dedos están a la misma altura
        isGestureHappening = nearbyVertically(firstFingerInitialPos, secondFingerInitialPos, NEARBY_VERTICAL_THRESHOLD);
    }

    public void onGestureEnd() {
        isGestureHappening = false;
    }

    public boolean detected(PointF firstFingerEndPos, PointF secondFingerEndPos) {
        // Los dos puntos finales tienen que estar a la misma altura y separados cierta
        // distancia de los puntos iniciales
        return isGestureHappening &&
                nearbyVertically(firstFingerEndPos, secondFingerEndPos, NEARBY_VERTICAL_THRESHOLD) &&
                !nearbyVertically(firstFingerInitialPos, firstFingerEndPos, GESTURE_VERTICAL_MIN_DISTANCE);

    }

    private boolean nearbyVertically(PointF firstFinger, PointF secondFinger, float threshold) {
        return Math.abs(firstFinger.y - secondFinger.y) < threshold;
    }
}
