/**
 * @authors Alejandro Torres Rodríguez
 */

package com.example.etsiitgo;

import static com.example.etsiitgo.R.id.nav_horario;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.preference.PreferenceManager;

import com.applozic.mobicomkit.uiwidgets.kommunicate.KmPrefSettings;
import com.example.etsiitgo.sensores.bluetooth.ActivadorBluetooth;
import com.example.etsiitgo.sensores.bluetooth.BluetoothScanner;
import com.example.etsiitgo.sensores.bluetooth.BluetoothService;
import com.example.etsiitgo.sensores.bluetooth.BluetoothState;
import com.example.etsiitgo.sensores.bluetooth.DeviceFoundCallback;
import com.example.etsiitgo.ui.ComedorFragment;
import com.example.etsiitgo.ui.HomeFragment;
import com.example.etsiitgo.ui.HorarioFragment;
import com.example.etsiitgo.ui.InteractableFragment;
import com.example.etsiitgo.ui.LoginScreenFragment;
import com.example.etsiitgo.ui.MapFragment;
import com.example.etsiitgo.ui.NotasFragment;
import com.example.etsiitgo.ui.PanicFragment;
import com.example.etsiitgo.ui.QrFragment;
import com.example.etsiitgo.ui.SettingsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import io.kommunicate.Kommunicate;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final int LOCATION_PERM_REQUEST = 666;
    private static final String[] locationPerms = new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION };

    private static final String GUSI_APP_ID= "48d233093e54127c75ac8b69c3397f10";
    private static final String DEBUG_TAG = "MainActivity";
    private static final String INIT_FRAGMENT_TAG = "init_fragment_main_activity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    public enum Fragmentos { HOME, MAP, HORARIO, COMEDOR, NOTAS, LOGIN, SETTINGS, QR,PANIC_BUTTON };

    private HomeFragment homeFragment;
    private MapFragment mapFragment;
    private HorarioFragment horarioFragment;
    private NotasFragment notasFragment;
    private ComedorFragment comedorFragment;
    private LoginScreenFragment loginScreenFragment;
    private QrFragment qrFragment;
    private SettingsFragment settingsFragment;
    private FragmentContainerView fragmentContainerView;

    private PanicFragment panicFragment;
    // Test
    private BluetoothScanner scanner;
    private BluetoothService bluetoothService;
    private GestureDetectorCompat gestureDetector;
    private TwoFingerGesture twoFingerGesture;
    private ArrayList<InteractableFragment> interactableFragmentViews;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivity thisActivity = this;

        // permisos de gusi
        GusiAssistant.getInstance(this).requestPermissions(this);

        bluetoothService = new BluetoothService(handler);
        Kommunicate.init(this,GUSI_APP_ID);
        Kommunicate.loginAsVisitor(this, null);

        // Creamos la demás vistas
        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        horarioFragment = new HorarioFragment();
        notasFragment = new NotasFragment();
        comedorFragment = new ComedorFragment();
        loginScreenFragment = new LoginScreenFragment();
        qrFragment = new QrFragment();
        settingsFragment = new SettingsFragment();
        panicFragment=new PanicFragment();

        horarioFragment.setBluetoothService(bluetoothService);
        notasFragment.setBluetoothService(bluetoothService);
        comedorFragment.setBluetoothService(bluetoothService);

        interactableFragmentViews = new ArrayList<>();
        interactableFragmentViews.add(horarioFragment);
        interactableFragmentViews.add(notasFragment);
        interactableFragmentViews.add(comedorFragment);
        interactableFragmentViews.add(panicFragment);

        // Hooks (asignar a cada variable su puntero)
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        fragmentContainerView = findViewById(R.id.main_fragment_viewer);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        initNavMenu();

        gestureDetector = new GestureDetectorCompat(this, this);
        gestureDetector.setOnDoubleTapListener(this);

        twoFingerGesture = new TwoFingerGesture();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(thisActivity);
                boolean enable_gusi_voice = sharedPreferences.getBoolean("enable_gusi_voice",false);
                String gusi_language = sharedPreferences.getString("gusi_language","es-ES");
                KmPrefSettings.getInstance(thisActivity)
                        .enableSpeechToText(true) // if true, enables speech to text feature in the SDK.
                        .enableTextToSpeech(enable_gusi_voice) // if true, enables text to speech feature. All the messages received will be spoken when the chat screen is open.
                        .setSendMessageOnSpeechEnd(true) //if true, the speech will automatically be sent as a message without clicking the send message button.
                        .setSpeechToTextLanguage(gusi_language) // set the laguage code for speech recognition. Default is en-US.
                        .setTextToSpeechLanguage(gusi_language); // set the language code for text to speech. Default is en-US
                Kommunicate.openConversation(thisActivity);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        scanner = new BluetoothScanner(new DeviceFoundCallback() {
            @Override
            public void onDeviceFound(String deviceName, String deviceAddress) {

                if (deviceAddress.equals(Constants.MAC_TOTEM)) {
                    BluetoothDevice device = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);
                    bluetoothService.connect(device);
                }
            }
        });

        scanner.startScan(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GusiAssistant.getInstance(this).cleanup();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivadorBluetooth.REQUEST_ENABLE_BT && resultCode == RESULT_CANCELED) {
            Toast.makeText(this, R.string.bluetooth_apagado, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ComprobadorPermisos.noPermisosConcedidos(grantResults)) {
            Toast.makeText(this, R.string.perms_denied, Toast.LENGTH_LONG).show();
            return;
        }

        // En este punto sabemos que todos los permisos han sido concedidos
        if (requestCode == LOCATION_PERM_REQUEST) {
            // Abrir el menú del mapa
            abrirMenuMapa();
        }
    }

    /**
     * Código de inicialización del menú lateral
     */
    private void initNavMenu() {
        // Establece la toolbar que hemos creado como action bar
        setSupportActionBar(toolbar);

        // Escondemos el menú de cerrar sesión porque de primeras si el usuario
        // no esta logeado no debería de estar ahí
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_qr).setVisible(false);

        // Permite abrir y cerrar el menú lateral
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Añade el slot para cuando se hace click en algun elemento del menú
        navigationView.setNavigationItemSelectedListener(this);

        // Por defecto está seleccionada la pestaña home
        navigationView.setCheckedItem(R.id.nav_home);

        // Añadimos el callback para cuando se va hacia atrás en la aplicación
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                    return;
                }

                finish();
            }
        });

        // Ponemos de primera pantalla el menu de inicio
        if (getSupportFragmentManager().getFragments().size() == 0) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_fragment_viewer, homeFragment, INIT_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        // Por desgracia esto no se puede hacer con un switch-case
        if (itemId == R.id.nav_home) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, homeFragment)
                    .commit();
        }
        else if (itemId == R.id.panic_button) {
            abrirMenuMapa();
        }
        else if (itemId == nav_horario) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, horarioFragment)
                    .commit();
        }
        else if (itemId == R.id.nav_notas) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, notasFragment)
                    .commit();
        }
        else if (itemId == R.id.nav_comedor) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, comedorFragment)
                    .commit();
        }
        else if(itemId == R.id.nav_login) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, loginScreenFragment)
                    .commit();
        }
        else if (itemId == R.id.nav_qr) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, qrFragment)
                    .commit();
        }
        else if (itemId == R.id.nav_settings) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, settingsFragment)
                    .commit();
        }
        else if (itemId == R.id.nav_logout) {
            onLogout();
        }
        else if (itemId == R.id.nav_exit) {
            finish();
            System.exit(0);
        }

        // Cerramos el menú cuando se selecciona algo
        drawerLayout.closeDrawer(GravityCompat.START);

        // Si lo ponemos a true los items del menú de navegación
        // se quedan seleccionados
        return true;
    }

    public void navigateToFragment(Fragmentos frag_name) {

        Fragment fragment = null;

        switch (frag_name) {
            case HOME:
                fragment = homeFragment;
                navigationView.setCheckedItem(R.id.nav_home);
                break;
            case MAP:
                abrirMenuMapa();
                return;
            case HORARIO:
                fragment = horarioFragment;
                navigationView.setCheckedItem(nav_horario);
                break;
            case COMEDOR:
                fragment = comedorFragment;
                navigationView.setCheckedItem(R.id.nav_comedor);
                break;
            case NOTAS:
                fragment = notasFragment;
                navigationView.setCheckedItem(R.id.nav_notas);
                break;
            case SETTINGS:
                fragment = settingsFragment;
                navigationView.setCheckedItem(R.id.nav_settings);
                break;
            case LOGIN:
                fragment = loginScreenFragment;
                navigationView.setCheckedItem(R.id.nav_login);
                break;
            case QR:
                fragment = qrFragment;
                navigationView.setCheckedItem(R.id.nav_qr);
                break;
            case PANIC_BUTTON:
                fragment = panicFragment;
                navigationView.setCheckedItem(R.id.nav_qr);
                break;

        }

        if ( fragment == null ) return;

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_viewer, fragment)
                .commit();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (this.gestureDetector.onTouchEvent(event)) {
            return true;
        }

        int action = event.getActionMasked();

        switch(action) {
            case MotionEvent.ACTION_POINTER_DOWN:

                if (event.getPointerCount() == 2) {
                    twoFingerGesture.setFirstFingerInitialPos(event.getX(0), event.getY(0));
                    twoFingerGesture.setSecondFingerInitialPos(event.getX(1), event.getY(1));
                    twoFingerGesture.onGestureStart();
                }
                else {
                    twoFingerGesture.onGestureEnd();
                }

                return true;
            case MotionEvent.ACTION_POINTER_UP:
                if (event.getPointerCount() == 2) {
                    PointF firstFingerPos = new PointF(event.getX(0), event.getY(0));
                    PointF secondFingerPos = new PointF(event.getX(1), event.getY(1));

                    if (twoFingerGesture.detected(firstFingerPos, secondFingerPos)) {
                        for (InteractableFragment interactableFragment : interactableFragmentViews) {
                            if (interactableFragment.isVisible())
                                interactableFragment.onTwoFingerGesture();
                        }
                    }
                }

                twoFingerGesture.onGestureEnd();
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }


    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[])msg.obj;
                    String packet = new String(readBuf, 0, msg.arg1);

                    for (InteractableFragment fragment : interactableFragmentViews) {
                        fragment.onPacket(packet);
                    }

                    break;
                case Constants.MESSAGE_STATE_CHANGE:
                    BluetoothState state = BluetoothState.values()[msg.arg1];
                    switch (state) {
                        case NONE:
                            getSupportActionBar().setSubtitle(R.string.totem_no_conectado);
                            break;
                        case CONNECTED:
                            getSupportActionBar().setSubtitle(R.string.totem_conectado);
                            break;
                        case CONNECTING:
                            getSupportActionBar().setSubtitle(R.string.totem_connecting);
                            break;
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(Constants.TOAST),
                            Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent e) {

    }

    @Override
    public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
        final float gestureVelocity = 1000f;
        final float maxVerticalVelocity = 1000f;

        Log.d(DEBUG_TAG, "VelocityX: " + velocityX + " VelocityY: " + velocityY);

        if (velocityY > -maxVerticalVelocity && velocityY < maxVerticalVelocity) {
            if (velocityX > gestureVelocity) {
                for (InteractableFragment interactableFragment : interactableFragmentViews) {
                    if (interactableFragment.isVisible())
                        interactableFragment.onSwipeRight();
                }

                Log.d(DEBUG_TAG, "Swipe right");
            }
            else if (velocityX < -gestureVelocity) {
                for (InteractableFragment interactableFragment : interactableFragmentViews) {
                    if (interactableFragment.isVisible())
                        interactableFragment.onSwipeLeft();
                }

                Log.d(DEBUG_TAG, "Swipe left");
            }
        }

        return false;
    }

    public void onCorrectLogin() {
        // Cambiamos el nombre del menú lateral
        TextView menuName = findViewById(R.id.menu_name);
        menuName.setText("Adriano García-Giralda MilenaE");

        //ImageView img = findViewById(R.id.nav_profile_picture);
        //img.setImageResource(R.drawable.login_icon2);

        homeFragment.setUserLoged(true);

        // Activamos el menú de cerrar sesión y del perfil
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(true);
        menu.findItem(R.id.nav_qr).setVisible(true);


        // Desactivamos el menú de iniciar sesión
        menu.findItem(R.id.nav_login).setVisible(false);

        // Cambiamos al menu de inicio
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_viewer, homeFragment)
                .commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    public void onLogout() {
        // Cambiamos el nombre del menú lateral
        TextView menuName = findViewById(R.id.menu_name);
        menuName.setText(getString(R.string.not_logged_in));

        // Cambiamos la imágen
        ImageView img = findViewById(R.id.nav_profile_picture);
        img.setImageResource(R.drawable.login_icon);

        homeFragment.setUserLoged(false);

        // Desactivamos los menus de cerrar sesion y qr
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_qr).setVisible(false);

        // Activamos el menú de iniciar sesión
        menu.findItem(R.id.nav_login).setVisible(true);

        // Cambiamos al menu de inicio de sesión
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_viewer, loginScreenFragment)
                .commit();
        navigationView.setCheckedItem(R.id.nav_login);
    }

    private void askLocationPerm() {
        ActivityCompat.requestPermissions(this, locationPerms, LOCATION_PERM_REQUEST);
    }

    private void abrirMenuMapa() {
        if (ComprobadorPermisos.permisosConcedidos(this, locationPerms)) {
            // abrir
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_viewer, mapFragment)
                    .commit();

            navigationView.setCheckedItem(R.id.nav_mapa);
        }
        else {
            // pedir permisos
            askLocationPerm();
        }
    }
}