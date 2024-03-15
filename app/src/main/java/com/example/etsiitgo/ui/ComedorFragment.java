package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.R;
import com.example.etsiitgo.data.ComedorData;
import com.example.etsiitgo.parsers.ComedorParser;
import com.example.etsiitgo.sensores.bluetooth.BluetoothState;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComedorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComedorFragment extends InteractableFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ContenidoComedorFragment contenidoComedorFragment;
    private IndicacionGestoDosDedos indicacionGestoDosDedos;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Map<String, String> img_id = new HashMap<>();

    public ComedorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComedorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComedorFragment newInstance(String param1, String param2) {
        ComedorFragment fragment = new ComedorFragment();
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
        int i=R.drawable.primero1;
        img_id.put("primero1", String.valueOf(i));
         i=R.drawable.segundo1;
        img_id.put("segundo1", String.valueOf(i));
         i=R.drawable.postre1;
        img_id.put("postre1", String.valueOf(i));
        i=R.drawable.primero2;
        img_id.put("primero2", String.valueOf(i));
        i=R.drawable.segundo2;
        img_id.put("segundo2", String.valueOf(i));
        i=R.drawable.postre2;
        img_id.put("postre2", String.valueOf(i));
        i=R.drawable.primero3;
        img_id.put("primero3", String.valueOf(i));
        i=R.drawable.segundo3;
        img_id.put("segundo3", String.valueOf(i));
        i=R.drawable.postre3;
        img_id.put("postre3", String.valueOf(i));
        i=R.drawable.primero4;
        img_id.put("primero4", String.valueOf(i));
        i=R.drawable.segundo4;
        img_id.put("segundo4", String.valueOf(i));
        i=R.drawable.postre4;
        img_id.put("postre4", String.valueOf(i));
        i=R.drawable.primero5;
        img_id.put("primero5", String.valueOf(i));
        i=R.drawable.segundo5;
        img_id.put("segundo5", String.valueOf(i));
        i=R.drawable.postre5;
        img_id.put("postre5", String.valueOf(i));

    }

    private ComedorData fixImg(ComedorData data){
        String imagenes[][] = data.getImagenes();

        for (int i = 0; i < 5; i++){
            for (int j = 0; j < imagenes[i].length; j++){
                String plato = imagenes[i][j];
                if(plato.contains("primero"))
                    imagenes[i][j] = img_id.get("primero" + (i+1));
                else if (plato.contains("segundo"))
                    imagenes[i][j] = img_id.get("segundo" + (i+1));
                else
                    imagenes[i][j] = img_id.get("postre" + (i+1));
            }
        }

        data.setImagenes(imagenes);
        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_comedor, container, false);

        //botón temporal simula el envío del paquete por bluetooth.
        /*
        root.findViewById(R.id.boton_descarga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paquete = "comedor\rPrimero: Ensalada de quinoa y aguacate\n\rprimero1\rSegundo: Salmón a la parrilla con salsa de limón y puré de papas con ajo\n\rsegundo1\rPostre: Tarta de frutas frescas\n\rpostre1\rPrimero: Crema de champiñones\n\rprimero2\rSegundo: Pollo al curry con arroz basmati y brócoli al vapor con almendras\n\rsegundo2\rPostre: Mousse de chocolate blanco\n\rpostre2\rPrimero: Gazpacho andaluz\n\rprimero3\rSegundo: Lasaña de espinacas y ricotta con espárragos a la parrilla\n\rsegundo3\rPostre: Sorbete de limón\n\rpostre3\rPrimero: Sopa de tomate asado\n\rprimero4\rSegundo: Risotto de champiñones con zanahorias glaseadas\n\rsegundo4\rPostre: Cheesecake de fresas\n\rpostre4\rPrimero: Ceviche de camarones\n\rprimero5\rSegundo: Tacos de pescado con salsa de mango y arroz con frijoles negros\n\rsegundo5\rPostre: Flan de coco\n\rpostre5\rPrimero: Espárragos gratinados\n\rprimero6\rSegundo: Solomillo de ternera con salsa de vino tinto y puré de batatas\n\rsegundo6\rPostre: Tiramisú\n\rpostre6";
                ComedorParser parser = new ComedorParser();
                ComedorData data = parser.parse(paquete);
                data = fixImg(data);
                if (data != null) {
                    contenidoComedorFragment = new ContenidoComedorFragment();
                    contenidoComedorFragment.setComedorData(data);

                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.comedor_contenido_fragment, contenidoComedorFragment, null)
                            .commit();
                }
            }
        });
*/
        // Creación de variables
        //contenidoComedorFragment = new ContenidoComedorFragment();
        contenidoComedorFragment = ContenidoComedorFragment.newInstance("", "");
        indicacionGestoDosDedos = new IndicacionGestoDosDedos();

        // Hooks

        // TODO: Usar el fragmento de indicación del gesto cuando ya tengamos la conexión bluetooth
        // Inicializamos el fragment del contenido principal con el mensaje
        // de como obtener el menu desde el tótem.
        getParentFragmentManager()
                .beginTransaction()
                .add(R.id.comedor_contenido_fragment, indicacionGestoDosDedos, null)
                .commit();

        return root;
    }


    @Override
    public void onTwoFingerGesture() {
        // Pide el menú al totem si esta conectado
        if (bluetoothService.getState() == BluetoothState.CONNECTED) {
            bluetoothService.write("comedor".getBytes(StandardCharsets.UTF_8));
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
        contenidoComedorFragment.onSwipeRight();
    }

    @Override
    public void onSwipeLeft() {
        contenidoComedorFragment.onSwipeLeft();
    }

    @Override
    public void onPacket(String packet) {
        if (!packet.startsWith("comedor"))
            return;

        ComedorParser parser = new ComedorParser();
        ComedorData data = parser.parse(packet);
        data = fixImg(data);

        if (data != null) {
            contenidoComedorFragment = new ContenidoComedorFragment();
            contenidoComedorFragment.setComedorData(data);

            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.comedor_contenido_fragment, contenidoComedorFragment, null)
                    .commit();
        }
    }
}