package com.example.moodpal.ui;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moodpal.R;

public class PanicFragment extends InteractableFragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int indice_consejo=0; //indice del dia de la semana debe estar en %5 siempre !!!
    private TextView textotitulopanico,textopanico,postre,day; //Textos del layout horario el Dia y sus franjas horarias
    private ImageView imagenespanicImageView;
    private Button siguiente_paso;


    public static PanicFragment newInstance(String param1, String param2) {
        PanicFragment fragment = new PanicFragment();
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
        View view = inflater.inflate(R.layout.fragment_panic, container, false);

        textotitulopanico=view.findViewById(R.id.textotitulopanic);
        textopanico = view.findViewById(R.id.textopanic);
        siguiente_paso=view.findViewById(R.id.botonpasarpanic);

        imagenespanicImageView =view.findViewById(R.id.imagenpanic);



        actualizarInterfaz();
        return view;
    }


    public void actualizarInterfaz(){
        String []titulo = new String[2];
        String []texto= new String[2];
        String []imagenes= new String[2];
        titulo[0]=" Respira ";
        titulo[1]="Ayúdate";
        texto[0]="";
        texto[1]=" ";
        imagenes[0]=String.valueOf(R.drawable.respira1);
        imagenes[1] = String.valueOf(R.drawable.respira1);

        textotitulopanico.setText(titulo[indice_consejo]);
        textopanico.setText(texto[indice_consejo]);

        // imagenespanicImageView.setImageResource(Integer.parseInt(imagenes[indice_consejo]));
        Glide.with(this)
                .asGif()
                .load("https://media1.tenor.com/m/q1a1tucKnB0AAAAd/inspire-inhale.gif")
                .into(imagenespanicImageView);

        siguiente_paso.setOnClickListener(new View.OnClickListener(){
        @Override public  void onClick(View v){
            indice_consejo=(indice_consejo+1)%2;
            textotitulopanico.setText(titulo[indice_consejo]);
            textopanico.setText(texto[indice_consejo]);
            imagenespanicImageView.setImageResource(Integer.parseInt(imagenes[indice_consejo]));
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
