package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.R;
import com.example.etsiitgo.data.HorarioData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContenidoHorarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContenidoHorarioFragment extends InteractableFragment {

    // TODO: Crear una función como la de get notas para actualizar las notas mediante el gesto.

    private EditText editText;

    private String []dia={"Lunes","Martes","Miércoles","Jueves","Viernes"};

    private int indice_dia=0; //indice del dia de la semana debe estar en %7 siempre !!!

    private TextView h1,h2,h3,h4,h5,h6,day; //Textos del layout horario el Dia y sus franjas horarias

    private HorarioData horario;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContenidoHorarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContenidoHorarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContenidoHorarioFragment newInstance(String param1, String param2) {
        ContenidoHorarioFragment fragment = new ContenidoHorarioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contenido_horario, container, false);
        day=view.findViewById(R.id.day);
        h1 = view.findViewById(R.id.h1);
        h2 = view.findViewById(R.id.h2);
        h3 = view.findViewById(R.id.h3);
        h4 = view.findViewById(R.id.h4);
        h5 = view.findViewById(R.id.h5);
        h6 = view.findViewById(R.id.h6);
        //horario=new HorarioData();

        actualizarInterfaz(this.indice_dia);

        return view;
    }

    public void setHorarioData(HorarioData horarioData) {
        this.horario = horarioData;
    }


    public void actualizarInterfaz(int indice_dia){
        String [][] horas= horario.gethoras();

        // Modifica el texto del TextView
        day.setText(dia[indice_dia]);
        h1.setText(horas[0][indice_dia]);
        h2.setText(horas[1][indice_dia]);
        h3.setText(horas[2][indice_dia]);
        h4.setText(horas[3][indice_dia]);
        h5.setText(horas[4][indice_dia]);
        h6.setText(horas[5][indice_dia]);
    }


    @Override
    public void onTwoFingerGesture() {

    }

    @Override
    public void onSwipeRight() {
        indice_dia=(indice_dia-1)%5;
        if(indice_dia<0){
            indice_dia=4;
        }

        actualizarInterfaz(indice_dia);
    }

    @Override
    public void onSwipeLeft() {
        indice_dia=(indice_dia+1)%5;

        actualizarInterfaz(indice_dia);
    }

    @Override
    public void onPacket(String packet) {

    }
}