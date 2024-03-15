package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.R;
import com.example.etsiitgo.data.ComedorData;
import com.ortiz.touchview.TouchImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContenidoComedorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContenidoComedorFragment extends InteractableFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //DIAS
    private String []dia={"Lunes","Martes","Miércoles","Jueves","Viernes"};
    //DESCRIPCION DE CADA PLATO
    private String Descripcionp1,Descripcionp2,Descripcionp3;
    private String mParam2;
    private String mParam1;
    private ComedorData menus;
    public ContenidoComedorFragment() {
        // Required empty public constructor
    }

    private int indice_dia=0; //indice del dia de la semana debe estar en %5 siempre !!!
    private TextView primero,segundo,postre,day; //Textos del layout horario el Dia y sus franjas horarias
    private TouchImageView primeroImageView,segundoImageView,postreImageView;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContenidoComedorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContenidoComedorFragment newInstance(String param1, String param2) {
        ContenidoComedorFragment fragment = new ContenidoComedorFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contenido_comedor, container, false);
        day=view.findViewById(R.id.textodiamenu);
        primero = view.findViewById(R.id.textoprimero);
        segundo = view.findViewById(R.id.textosegundo);
        postre = view.findViewById(R.id.textotercero);

        primeroImageView =view.findViewById(R.id.viewPlato1);
        segundoImageView=view.findViewById(R.id.viewPlato2);
        postreImageView=view.findViewById(R.id.viewPlato3);
       // menus=new ComedorData();

      actualizarInterfaz(this.indice_dia);
        return view;
    }

    public void setComedorData(ComedorData comedorData) {this.menus = comedorData;}

    public void actualizarInterfaz(int indice_dia){
        String [][]menu=menus.getMenu();
        String [][]imagenes=menus.getImagenes();

        day.setText(dia[indice_dia]);
        primero.setText(menu[indice_dia][0]);
        segundo.setText(menu[indice_dia][1]);
        postre.setText(menu[indice_dia][2]);
        //MODIFICAR A UNA MATRIZ DE IMAGENES
        primeroImageView.setImageResource(Integer.parseInt(imagenes[indice_dia][0]));
//        primero2ImageView.setImageResource(Integer.parseInt(imagenes[indice_dia][0]));
        segundoImageView.setImageResource(Integer.parseInt(imagenes[indice_dia][1]));
        postreImageView.setImageResource(Integer.parseInt(imagenes[indice_dia][2]));
    }

    @Override
    public void onTwoFingerGesture() {

    }

    @Override
    public void onSwipeRight() {
        indice_dia = (indice_dia - 1) % 5;
        if (indice_dia < 0) {
            indice_dia = 4;
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