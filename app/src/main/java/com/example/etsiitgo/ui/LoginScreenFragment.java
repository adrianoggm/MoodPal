/**
 * @authors Alejandro y Ariel
 */

package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.MainActivity;
import com.example.etsiitgo.R;

public class LoginScreenFragment extends Fragment {
    static private String USERNAME = "user";
    static private String PASSWORD = "password";
    private Button loginButton;
    private EditText usernameInput;
    private EditText passwodInput;
    private MainActivity mainActivity;
    public LoginScreenFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.login_screen, container, false);

        loginButton = root.findViewById(R.id.login_btn);
        usernameInput = root.findViewById(R.id.username_input);
        passwodInput = root.findViewById(R.id.password_input);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("LOGIN", "Usuario: " + usernameInput.getText().toString().equals(USERNAME));
                Log.d("LOGIN", "Contraseña: " + passwodInput.getText().toString().equals(PASSWORD));



                if (usernameInput.getText().toString().equals(USERNAME) &&
                        passwodInput.getText().toString().equals(PASSWORD)) {
                    MainActivity activity = (MainActivity) getActivity();

                    if (activity != null)
                        activity.onCorrectLogin();
                }
                else {
                    Toast.makeText(getActivity(), "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }


}
