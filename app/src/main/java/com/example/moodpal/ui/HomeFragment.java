package com.example.moodpal.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.moodpal.MainActivity;
import com.example.moodpal.R;

public class HomeFragment extends Fragment  {

    private static final String DEBUG_TAG = "HomeFragmentDEBUG";
    public HomeFragment() { }

    private boolean userLoged = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null) {

            view.findViewById(R.id.panic_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.PANIC_BUTTON);
                }
            });
            view.findViewById(R.id.boton_emociones).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.EMOCIONES);
                }
            });

            view.findViewById(R.id.botonexperto).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.EXPERTO);
                }
            });

            view.findViewById(R.id.boton_entender).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.ENTENDER);
                }
            });


        }

        return view;
    }


    public boolean isUserLoged() {
        return userLoged;
    }

    public void setUserLoged(boolean value) {
        userLoged = value;
    }

}