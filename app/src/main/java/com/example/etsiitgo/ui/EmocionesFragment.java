package com.example.etsiitgo.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.etsiitgo.R;

public class EmocionesFragment extends InteractableFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button felicidad,miedo,amor,sorpresa,tristeza,repugnancia,enojo;


    public static EmocionesFragment newInstance(String param1, String param2) {
        EmocionesFragment fragment = new EmocionesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emociones, container, false);


        felicidad=view.findViewById(R.id.boton_felicidad);
        enojo=view.findViewById(R.id.boton_enojo);
        amor = view.findViewById(R.id.boton_amor);
        miedo =view.findViewById(R.id.boton_miedo);
        repugnancia=view.findViewById(R.id.boton_repugnancia);
        tristeza=view.findViewById(R.id.boton_tristeza);
        enojo=view.findViewById(R.id.boton_enojo);




        actualizarInterfaz();
        return view;
    }


    public void actualizarInterfaz(){
        /**DEBE INICIALIZAR LA NUEVA INTERFAZ Y CONSTREUIRLA  DEPENDIENDO DEL BOTON PULSADO
         *
         *
         *
         * * **/
        felicidad.setOnClickListener(new View.OnClickListener(){
            @Override public  void onClick(View v){
                //PASA AL FRAGMENT 2
            }
        });
    }

    @Override
    public void onTwoFingerGesture() {

    }

    @Override
    public void onSwipeRight() {

    }

    @Override
    public void onSwipeLeft() {

    }

    @Override
    public void onPacket(String packet) {

    }
}