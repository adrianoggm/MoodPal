package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.MainActivity;
import com.example.etsiitgo.R;

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

            view.findViewById(R.id.account_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.LOGIN);
                }
            });
            view.findViewById(R.id.map_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.MAP);
                }
            });

            view.findViewById(R.id.comedor_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.COMEDOR);
                }
            });

            view.findViewById(R.id.horario_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.HORARIO);
                }
            });

            view.findViewById(R.id.notas_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.NOTAS);
                }
            });

            view.findViewById(R.id.settings_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.navigateToFragment(MainActivity.Fragmentos.SETTINGS);
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if ( !userLoged ) {

            View view = requireView();
            ImageButton accountButton = view.findViewById(R.id.account_button);
            accountButton.setImageResource(R.drawable.login_icon);
            view.findViewById(R.id.account_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)requireActivity()).navigateToFragment(MainActivity.Fragmentos.LOGIN);
                }
            });

        }
        else { // user loged in

            View view = requireView();
            ImageButton accountButton = view.findViewById(R.id.account_button);
            accountButton.setImageResource(R.drawable.qr_code);

            view.findViewById(R.id.account_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)requireActivity()).navigateToFragment(MainActivity.Fragmentos.QR);
                }
            });

        }

    }

    public boolean isUserLoged() {
        return userLoged;
    }

    public void setUserLoged(boolean value) {
        userLoged = value;
    }

}