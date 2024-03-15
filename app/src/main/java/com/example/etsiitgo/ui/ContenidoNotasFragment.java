/**
 * @author Alejandro Torres Rodríguez
 */

package com.example.etsiitgo.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.R;
import com.example.etsiitgo.data.Nota;
import com.example.etsiitgo.data.NotasData;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContenidoNotasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContenidoNotasFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int DEFAULT_FONT_SIZE = 16;
    private static final int DEFAULT_PADDING = 10;
    private static final float DEFAULT_WEIGHT = 1.0f;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView nombre;
    private TextView dni;
    private TextView mencion;
    private TextView curso;
    private TableLayout tablaNotas;
    private NotasData notasData;

    public void setNotasData(NotasData notasData) {
        this.notasData = notasData;
    }

    public ContenidoNotasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContenidoNotasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContenidoNotasFragment newInstance(String param1, String param2) {
        ContenidoNotasFragment fragment = new ContenidoNotasFragment();
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
        View root = inflater.inflate(R.layout.fragment_contenido_notas, container, false);

        // Hooks
        nombre = root.findViewById(R.id.notas_nombre_alumno);
        dni = root.findViewById(R.id.notas_dni_alumno);
        mencion = root.findViewById(R.id.notas_mencion);
        curso = root.findViewById(R.id.notas_curso);
        tablaNotas = root.findViewById(R.id.tabla_notas);

        updateNotasData(notasData);

        return root;
    }

    public void updateNotasData(NotasData notasData) {
        if (notasData == null)
            return;

        // Actualizamos primero la información personal
        try {
            nombre.setText(notasData.getNombre());
            dni.setText(notasData.getDni());
            mencion.setText(notasData.getMencion());
            curso.setText(notasData.getCurso());
        }
        catch (Exception e) {
            Log.d("test", "error", e);
        }


        // Iteramos por las notas y añadimos las nuevas filas a la tabla
        for (Nota nota : notasData.getNotas()) {
            // Creamos la fila
            TableRow row = new TableRow(getContext());
            row.setLayoutParams(new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            row.setGravity(Gravity.CENTER);

            // Calculamos el padding en medidas "dp"
            float scale = getResources().getDisplayMetrics().density;
            int padding = (int)(DEFAULT_PADDING * scale + 0.5f);

            // Creamos el textview para la asignatura
            TextView asignaturaTextView = new TextView(getContext());
            asignaturaTextView.setText(nota.getAsignatura());
            asignaturaTextView.setTextSize(DEFAULT_FONT_SIZE);
            asignaturaTextView.setPadding(padding, padding, padding, padding);
            asignaturaTextView.setLayoutParams(new TableRow.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, DEFAULT_WEIGHT
            ));

            // Creamos el textview para la nota
            TextView notaTextView = new TextView(getContext());
            notaTextView.setText(nota.getNota());
            notaTextView.setTextSize(DEFAULT_FONT_SIZE);
            notaTextView.setGravity(Gravity.CENTER);
            notaTextView.setPadding(padding, padding, padding, padding);
            notaTextView.setLayoutParams(new TableRow.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, DEFAULT_WEIGHT
            ));

            // Añadimos los textos a la fila
            row.addView(asignaturaTextView);
            row.addView(notaTextView);

            // Añadimos la fila a la tabla
            tablaNotas.addView(row);
        }
    }
}