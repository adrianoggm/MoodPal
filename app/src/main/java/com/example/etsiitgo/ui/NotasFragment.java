/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.R;
import com.example.etsiitgo.data.NotasData;
import com.example.etsiitgo.parsers.NotasParser;
import com.example.etsiitgo.sensores.bluetooth.BluetoothState;

import java.nio.charset.StandardCharsets;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotasFragment extends InteractableFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ContenidoNotasFragment contenidoNotasFragment;
    private IndicacionGestoDosDedos indicacionGestoDosDedos;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotasFragment newInstance(String param1, String param2) {
        NotasFragment fragment = new NotasFragment();
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
        View root = inflater.inflate(R.layout.fragment_notas, container, false);

        // Creación de variables
        //contenidoNotasFragment = new ContenidoNotasFragment();
        contenidoNotasFragment = ContenidoNotasFragment.newInstance("", "");
        indicacionGestoDosDedos = new IndicacionGestoDosDedos();

        // Hooks

        // TODO: Usar el fragmento de indicación del gesto cuando ya tengamos la conexión bluetooth
        // Inicializamos el fragment del contenido principal con el mensaje
        // de como obtener las notas desde el tótem.
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.notas_contenido_fragment, indicacionGestoDosDedos, null)
                .commit();



        return root;
    }


    @Override
    public void onTwoFingerGesture() {
        // Pide las notas al totem si esta conectado
        if (bluetoothService.getState() == BluetoothState.CONNECTED) {
            bluetoothService.write("notas".getBytes(StandardCharsets.UTF_8));
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

    }

    @Override
    public void onSwipeLeft() {

    }

    @Override
    public void onPacket(String packet) {
        if (!packet.startsWith("notas"))
            return;

        NotasParser parser = new NotasParser();
        NotasData data = parser.parse(packet);

        if (data != null) {
            contenidoNotasFragment = new ContenidoNotasFragment();
            contenidoNotasFragment.setNotasData(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.notas_contenido_fragment, contenidoNotasFragment, null)
                    .commit();
        }
    }
}