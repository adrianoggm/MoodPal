package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.R;
import com.example.etsiitgo.data.HorarioData;
import com.example.etsiitgo.parsers.HorarioParser;
import com.example.etsiitgo.sensores.bluetooth.BluetoothState;

import java.nio.charset.StandardCharsets;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HorarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HorarioFragment extends InteractableFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ContenidoHorarioFragment contenidoHorarioFragment;
    private IndicacionGestoDosDedos indicacionGestoDosDedos;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HorarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HorarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HorarioFragment newInstance(String param1, String param2) {
        HorarioFragment fragment = new HorarioFragment();
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
        View root = inflater.inflate(R.layout.fragment_horario, container, false);

        //boton temporal simula el paquete
        /*
        root.findViewById(R.id.boton_descarga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paquete = "horario\r\r\r\rPL\n2.1\rVC\n1.1\r\rSS\n3.8\rSS\n1.1\rTIC\n1.5\rPL\n2.1\rVC\n1.1\r\rSS\n3.8\rSS\n1.1\rTIC\n1.5\rNPI\n1.1\rNPI\n3.1\r\r\rTIC\n2.2\r\rNPI\n1.1\rNPI\n3.1\r\r\rTIC\n2.2\r\rVC\n2.1\rPL\n1.1\r\r\r\r\rVC\n2.1\rPL\n1.1\r";
                HorarioParser parser = new HorarioParser();
                HorarioData data = parser.parse(paquete);
                Log.i("a", data.gethoras()[0][4]);

                if (data != null) {
                    contenidoHorarioFragment = new ContenidoHorarioFragment();
                    contenidoHorarioFragment.setHorarioData(data);

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.horario_contenido_fragment, contenidoHorarioFragment, null)
                            .commit();
                }
            }
        });
        */
        // Creación de variables
        //contenidoHorarioFragment = new ContenidoHorarioFragment();
        contenidoHorarioFragment = ContenidoHorarioFragment.newInstance("", "");
        indicacionGestoDosDedos = new IndicacionGestoDosDedos();

        // Hooks

        // TODO: Usar el fragmento de indicación del gesto cuando ya tengamos la conexión bluetooth
        // Inicializamos el fragment del contenido principal con el mensaje
        // de como obtener el menu desde el tótem.
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.horario_contenido_fragment, indicacionGestoDosDedos, null)
                .commit();

        return root;
    }


    @Override
    public void onTwoFingerGesture() {
        // Pide el menú al totem si esta conectado
        if (bluetoothService.getState() == BluetoothState.CONNECTED) {
            bluetoothService.write("horario".getBytes(StandardCharsets.UTF_8));
        }
        else {
            Toast.makeText(
                    getActivity(),
                    "Necesitas estar conectado con el totem para realizar esta acción",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    @Override
    public void onSwipeRight() {
        contenidoHorarioFragment.onSwipeRight();
    }

    @Override
    public void onSwipeLeft() {
        contenidoHorarioFragment.onSwipeLeft();
    }

    @Override
    public void onPacket(String packet) {
        if (!packet.startsWith("horario"))
            return;

        HorarioParser parser = new HorarioParser();
        HorarioData data = parser.parse(packet);

        if (data != null) {
            contenidoHorarioFragment = new ContenidoHorarioFragment();
            contenidoHorarioFragment.setHorarioData(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.horario_contenido_fragment, contenidoHorarioFragment, null)
                    .commit();
        }
    }
}