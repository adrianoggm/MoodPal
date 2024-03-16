

package com.example.moodpal.ui;

import androidx.fragment.app.Fragment;


public abstract class InteractableFragment extends Fragment {

    public abstract void onTwoFingerGesture();
    public abstract void onSwipeRight();
    public abstract void onSwipeLeft();

    public abstract void onPacket(String packet);
}
