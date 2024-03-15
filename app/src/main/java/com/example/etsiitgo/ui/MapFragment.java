/**
 * @authors Alejandro e Iván
 */

package com.example.etsiitgo.ui;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.etsiitgo.GusiAssistant;
import com.example.etsiitgo.R;
import com.example.etsiitgo.data.MapaData;
import com.example.etsiitgo.data.RouteManager;
import com.example.etsiitgo.navigation.CheckpointDataBase;
import com.example.etsiitgo.navigation.FileUtils;
import com.example.etsiitgo.navigation.LocationTypeAdapter;
import com.example.etsiitgo.navigation.RoutesDataBase;
import com.example.etsiitgo.sensores.gps.LocationUpdater;
import com.example.etsiitgo.sensores.gps.UpdateLocationCallback;
import com.example.etsiitgo.sensores.orientation.OrientationUpdater;
import com.example.etsiitgo.sensores.orientation.UpdateOrientationCallback;
import com.example.etsiitgo.sensores.pressure.PressureUpdater;
import com.example.etsiitgo.sensores.pressure.UpdatePressureCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MapFragment extends Fragment implements UpdateLocationCallback, UpdateOrientationCallback, UpdatePressureCallback {
    static private final String TAG = "MapFragmentDEBUG";
    private static final DecimalFormat df = new DecimalFormat("0.00");

    static private final String CHECKPOINT_DATA_FILE = "checkpoint_data.json";
    static private final String ROUTES_DATA_FILE = "routes_data.json";
    static private final String DEBUG_TAG = "MapFragmentDEBUG";
    static private final String DEBUG_TAG_ROUTE_MANAGER = "RouteManagerDEBUG";

    // interfaz
    private Spinner spinner;
    private Button buttonIr;
    private TextView planta, instruccion, distancia;
    private MapaData mapa;
    private String seleccion;

    private Location goalLocation;
    private Location currentLocation;
    private ImageView arrowImage;

    private LocationUpdater locationUpdater;
    private OrientationUpdater orientationUpdater;
    private PressureUpdater pressureUpdater;


    // Asistente de Guia
    private final RouteManager routeManager;

    private FusedLocationProviderClient locationProviderClient;
    private ScheduledExecutorService locationScheduledExecutor;
    private ScheduledExecutorService gusiScheduledExecutor; // this may be useful

    public MapFragment() {
        routeManager = new RouteManager();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        locationUpdater = new LocationUpdater(requireActivity(), this);
        locationUpdater.start();

        orientationUpdater = new OrientationUpdater(requireContext(), this);
        pressureUpdater = new PressureUpdater(requireContext(), this);

        goalLocation = new Location("");
        currentLocation = new Location("");

        arrowImage = view.findViewById(R.id.arrowImage);
        spinner = view.findViewById(R.id.spinner);
        buttonIr = view.findViewById(R.id.button_ir);
        planta = view.findViewById(R.id.texto_planta);
        instruccion = view.findViewById(R.id.instruccion);
        distancia = view.findViewById(R.id.texto_distancia);
        distancia.setVisibility(View.INVISIBLE);

        mapa = new MapaData();

        // checkpoints and routes data setup

        String checkpointDataJSON = FileUtils.readFileFromAssets(requireActivity(),CHECKPOINT_DATA_FILE);
        String routesDataJSON = FileUtils.readFileFromAssets(requireActivity(),ROUTES_DATA_FILE);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Location.class, new LocationTypeAdapter())
                .create();

        // Read JSON data for checkpoints and routes
        CheckpointDataBase checkpointsData = gson.fromJson(checkpointDataJSON, CheckpointDataBase.class);
        RoutesDataBase routesDataBase = gson.fromJson(routesDataJSON, RoutesDataBase.class);
        assert checkpointsData != null && routesDataBase != null;

        routeManager.setCheckpointDatabase(checkpointsData);
        routeManager.setRoutesDatabase(routesDataBase);

        Log.d(DEBUG_TAG_ROUTE_MANAGER,routeManager.getCheckpointsDatabase().toString());
        Log.d(DEBUG_TAG_ROUTE_MANAGER,routeManager.getRoutesDatabase().toString());

        List<String> opciones = new ArrayList<>(routeManager.getRoutesDatabase().keySet()); // añadir todas las rutas de la base de datos al spinner

        // Crear un ArrayAdapter utilizando la lista de opciones y un diseño simple
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, opciones);

        // Especificar el diseño que se usará cuando se despliegue el Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Establecer el adaptador en el Spinner
        spinner.setAdapter(adapter);

        // Manejar la selección del Spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Acción a realizar cuando se selecciona un elemento en el Spinner
                seleccion = opciones.get(position);
                //showToast("Seleccionaste " + seleccion);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Acción a realizar cuando no se selecciona nada
            }
        });

        // Manejar el clic en el botón
        buttonIr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // instruccion.setText(Objects.requireNonNull(mapa.getMapa().get(seleccion)).get(0).getFirst());

                // 1. Elegir la ruta a la que ir

                // 2. Seleccionar el primer punto de ruta al que ir
                //goalLocation.setLatitude(...)
                //goalLocation.setLongitude(...)

                // Para probar le pongo las coordenadas de la Etsiit ( lo quito para poner como objetivo el siguiente checkpoint )
                // goalLocation.setLatitude(37.196948);
                // goalLocation.setLongitude(-3.624617);

                // 3. Activar el actualizador de la localización
                locationUpdater.setUpdateLocation(true);

                // 4. Hacer la imágen de la flecha visible
                arrowImage.setVisibility(View.VISIBLE);
                distancia.setVisibility(View.VISIBLE);

                if ( routeManager.isActiveRoute() ) { // stop route if pressed again while not finished
                    routeManager.endRoute();
                    locationScheduledExecutor.shutdown();
                    //showToast("Route Terminated");
                    buttonIr.setText("Ir");
                }
                else { // start new route
                    buttonIr.setText("Parar");

                    // Create a scheduled executor service with a single thread for position update
                    locationScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

                    // for testing
                    /*
                    testLocations.clear();
                    testLocations.add(data.getData().get("Home"));
                    testLocations.add(data.getData().get("Home1"));
                    testLocations.add(data.getData().get("Home2"));
                    testLocations.add(data.getData().get("Home3"));
                     */

                    String selectedRoute = (String)spinner.getSelectedItem();

                    // showToast("Start Route: " + selectedRoute);

                    Log.d(DEBUG_TAG_ROUTE_MANAGER,routeManager.getRoutesDatabase().toString());

                    routeManager.startRoute(selectedRoute);

                    if ( !routeManager.isActiveRoute() ) { // check if route has been activated

                        showToast("Route not found");
                        return;

                    }

                    Runnable locationUpdater = () -> updateLocation();

                    // update position every 5 seconds
                    locationScheduledExecutor.scheduleAtFixedRate(
                            locationUpdater,
                            0,  // Initial delay
                            5,  // Periodic interval
                            TimeUnit.SECONDS
                    );

                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        locationUpdater.setUpdateLocation(true);
        orientationUpdater.start();
        pressureUpdater.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        locationUpdater.setUpdateLocation(false);
        orientationUpdater.stop();
        pressureUpdater.stop();
    }

    private void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCurrentLocationUpdated(Location location) {
        this.currentLocation = new Location(location);
    }

    @Override
    public void onOrientationUpdated(int orientationDegree) {
        // Actualiza la dirección a la que tiene que apuntar la flecha
        if (currentLocation != null && goalLocation != null && arrowImage != null && arrowImage.getVisibility() == View.VISIBLE) {
            int goalDegree = Math.round(currentLocation.bearingTo(goalLocation));
            int finalDegree = goalDegree - orientationDegree;

            arrowImage.setRotation(finalDegree);

            if ( routeManager.isActiveRoute() ) {
                float d = currentLocation.distanceTo(goalLocation);
                distancia.setText("Estás a " + df.format(d) + " m del siguiente objetivo");
            }
            else{
                distancia.setText("");
            }

        }
    }

    private void updateLocation() {
        try {
            Task<Location> task = locationProviderClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null);

            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    boolean locationChanged = routeManager.updateRoute(location);
                    // testLocations.remove(0);


                    //requireActivity().runOnUiThread(() -> showToast("Location Updated"));

                    if (routeManager.isRouteCompleted()) {
                        //requireActivity().runOnUiThread(() -> showToast("Route Completed"));

                        GusiAssistant.getInstance(requireActivity()).speak("Has llegado a tu destino");

                        routeManager.endRoute();
                        locationScheduledExecutor.shutdown();
                    } else {
                        String currentNode = routeManager.getCurrentLocation();
                        String nextNode = routeManager.getNextLocation();

                        if ( locationChanged ) {
                            goalLocation.set(Objects.requireNonNull(routeManager.getCheckpointsDatabase().get(routeManager.getNextLocation())));
                        }

                        if ( locationChanged || GusiAssistant.REPEAT_INSTRUCTIONS_THRESHOLD <= routeManager.getAccumulatedStaticPosition() ) {

                            routeManager.resetAccumulatedStaticPosition();
                            GusiAssistant.getInstance(requireActivity()).speak(routeManager.getCurrentAnnotation());

                        }
                    }
                }
            });
        }
        catch (SecurityException e) {
            Log.e(DEBUG_TAG, "Error en getCurrentLocation: " + e.getLocalizedMessage());
        }
    }

    @Override
    public void onPressureUpdated(float pressureBars){
        planta.setText(pressureBars + " mbars");
    }
}