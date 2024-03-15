package com.example.etsiitgo.ui;


import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.GusiAssistant;
import com.example.etsiitgo.R;
import com.example.etsiitgo.data.ComedorData;
import com.example.etsiitgo.data.HorarioData;
import com.example.etsiitgo.data.MapaData;
import com.example.etsiitgo.data.RouteManager;
import com.example.etsiitgo.navigation.CheckpointDataBase;
import com.example.etsiitgo.navigation.FileUtils;
import com.example.etsiitgo.navigation.LocationTypeAdapter;
import com.example.etsiitgo.navigation.RoutesDataBase;
import com.example.etsiitgo.sensores.bluetooth.BluetoothState;
import com.example.etsiitgo.sensores.gps.LocationUpdater;
import com.example.etsiitgo.sensores.orientation.OrientationUpdater;
import com.example.etsiitgo.sensores.pressure.PressureUpdater;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ortiz.touchview.TouchImageView;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        titulo[0]="Respira profundamente";
        titulo[1]="Aqui no se que poner";
        texto[0]="Todo va a salir bien";
        texto[1]="Solo un poco más ";
        imagenes[0]=String.valueOf(R.drawable.respira1);
        imagenes[1] = String.valueOf(R.drawable.respira1);

        textotitulopanico.setText(titulo[indice_consejo]);
        textopanico.setText(texto[indice_consejo]);

        imagenespanicImageView.setImageResource(Integer.parseInt(imagenes[indice_consejo]));
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
