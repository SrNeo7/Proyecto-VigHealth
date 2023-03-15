package com.joseantonio.personalproject.proyectovighealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasActividadImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NuevaActividadActivity extends AppCompatActivity
implements OnMapReadyCallback {

    GoogleMap mMap;
    ImageButton btnEmpezar,btnPausar,btnParar;

    double distacia,ritmo,velocidad;

    int distanciaActulizarMapa,idUsuario;

    String nombreActividad,distanciaValue,ritmoValue,fechaActividad,duracion;

    TextView tvDistancia,tvRitmo,tvNombreActividad;

    Chronometer chronometer;


    long pausa,tiempoTranscurrido;

    boolean chronoIsRunning = false;

    final double MINKM = 16.66667;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actividad);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapActividad);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        getLocalizacion();
        btnEmpezar = findViewById(R.id.btnStart);
        btnPausar = findViewById(R.id.btnPause);
        btnParar = findViewById(R.id.btnStop);
        tvDistancia = findViewById(R.id.tvDistancia);
        tvRitmo = findViewById(R.id.tvRitmo);
        tvNombreActividad = findViewById(R.id.tvNombreActividad);
        chronometer = findViewById(R.id.cmCrono);


        //Recupera el nombre de la actividad elegida por el usuario en el Activity previo
       if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                nombreActividad = null;
            }else{
                nombreActividad = extras.getString("Actividad");
            }
        }else{
            nombreActividad = (String)savedInstanceState.getSerializable("Actividad");
        }

        tvNombreActividad.setText(nombreActividad);
        distanciaActulizarMapa = distanciaActualizacionMapa(nombreActividad);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pausa = 0;
                chronometer.start();
                chronoIsRunning = true;
                btnEmpezar.setEnabled(false);
                btnEmpezar.setVisibility(View.INVISIBLE);
                btnPausar.setEnabled(true);
                btnPausar.setVisibility(View.VISIBLE);
                btnParar.setEnabled(true);
                btnParar.setVisibility(View.VISIBLE);

            }
        });

        btnPausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chronoIsRunning){
                    pausa = SystemClock.elapsedRealtime()-chronometer.getBase();
                    chronometer.stop();
                    btnPausar.setImageResource(R.drawable.play);
                    chronoIsRunning = false;

                }else{
                    chronometer.setBase(SystemClock.elapsedRealtime()-pausa);
                    chronometer.start();
                    btnPausar.setImageResource(R.drawable.pause);
                    chronoIsRunning=true;
                }
            }
        });

        btnParar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Obtenemos el tiempo transcurrido en el cronómetro
                pausa = SystemClock.elapsedRealtime()-chronometer.getBase();
                chronometer.stop();
                chronoIsRunning = false;
                ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(NuevaActividadActivity.this);
                idUsuario = consultasUsuario.obtenerIdUsuario();
                ConsultasActividadImpl consultasActividad = new ConsultasActividadImpl(NuevaActividadActivity.this);
                fechaActividad = Utilidades.obtenerFechaActual();
                duracion = obtenerDuracion(pausa);
                double distanciaTotal = Double.parseDouble(distanciaValue.replace(",","."));
                capturarMapa(fechaActividad);

                long id = consultasActividad
                        .nuevaActividad(idUsuario,nombreActividad,duracion,
                                distanciaTotal,ritmoValue,fechaActividad);

                if(id>0){
                    Toast.makeText(NuevaActividadActivity.this,
                            "Actividad finalizada con exito.BUEN TRABAJO",Toast.LENGTH_LONG).show();
                    Intent intent =
                            new Intent(NuevaActividadActivity.this,
                                    ListaActividadesActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(NuevaActividadActivity.this,
                            "Se ha producido un error al finalizar la actividad. " +
                                    "Disculpe las molestias",Toast.LENGTH_LONG).show();
                    finish();
                }


            }
        });


    }

    /**
     * getLocalizacion: Comprueba los permisos acceder a la ubicación del usuario
     */
    private void getLocalizacion(){
        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if(permiso == PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){

            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

       if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
       PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)
       != PackageManager.PERMISSION_GRANTED){
           return;
       }

       mMap.setMyLocationEnabled(true);
       mMap.getUiSettings().setMyLocationButtonEnabled(false);

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .width(14)
                .color(Color.BLUE));

       LocationManager locationManager = (
               LocationManager)NuevaActividadActivity.this.getSystemService(Context.LOCATION_SERVICE);



        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                LatLng miUbicacion = new LatLng(location.getLatitude(),location.getLongitude());
                if (chronoIsRunning) {
                    /*Se obtienen los valores de los datos que se muestran mientras el usuario lleva
                     a cabo la actividad y se dibuja el itinerario seguido */
                    List<LatLng> points = line.getPoints();
                    if (points.size() > 0) {
                        LatLng ubicacionPrevia = points.get(points.size() - 1);
                        distacia += calculoDistancia(ubicacionPrevia, miUbicacion);
                        tiempoTranscurrido = (SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000;
                        velocidad = velocidadUsuario(distacia, tiempoTranscurrido);
                        ritmo = ritmoUsuario(velocidad);
                        double distanciaKM = distacia / 1000;
                        int minutos = (int) ritmo;
                        double segundos = ritmo - minutos;
                        int segundosEnteros = formateoSegundos(segundos);
                        String segundosDefinitivos = Utilidades.dosDigitos(segundosEnteros);
                        distanciaValue = String.format("%.2f", distanciaKM);
                        tvDistancia.setText(distanciaValue + " KM");
                        ritmoValue = minutos + "'" + segundosDefinitivos + "\"";
                        tvRitmo.setText(ritmoValue);
                    }
                    points.add(miUbicacion);
                    line.setPoints(points);
                }
                //Configura la vista del mapa
                mMap.moveCamera(CameraUpdateFactory.newLatLng(miUbicacion));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(miUbicacion)
                        .zoom(13)
                        .bearing(90)
                        .tilt(0)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            @Override
            public void onStatusChanged(String provider,int status,Bundle extras){

            }

            @Override
            public void onProviderEnabled(String provider){

            }

            @Override
            public void onProviderDisabled(String provider){

            }
        };
        //Actualiza el mapa periodicamente
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,distanciaActulizarMapa,
                locationListener);
    }

    /**
     * calculoDistancia: calcula la distancia que esta recorriendo el usuario midiendo entre los dos
     * puntos pasado como parametro
     * @param origen
     * @param destino
     * @return
     */
    private float calculoDistancia(LatLng origen, LatLng destino){

        Location locOrigen = new Location("origen");
        locOrigen.setLatitude(origen.latitude);
        locOrigen.setLongitude(origen.longitude);
        Location locDestino = new Location("destino");
        locDestino.setLatitude(destino.latitude);
        locDestino.setLongitude(destino.longitude);

        return locOrigen.distanceTo(locDestino);

    }

    /**
     * distanciaActualizacionMapa: destermina la cada cuantos metros se actualiza el mapa en funcion
     * de la actividad fisica seleccionada por el usuario
     * @param actividad
     * @return
     */
    private int distanciaActualizacionMapa (String actividad){
        int distancia;

        switch (actividad){
            case "Andar":
                distancia = 5;
                break;
            case "Correr":
                distancia = 10;
                break;
            case "Bicicleta":
                distancia = 20;
                break;
            default:
                distancia = 0;
                break;

        }
        return distancia;
    }

    /**
     * velocidadUsuario: calcula la velocidad a la que avanaza el usuario
     * @param distancia
     * @param tiempo
     * @return
     */
    private double velocidadUsuario(double distancia, long tiempo){
        double velocidad;

        velocidad = distancia/tiempo;

        return velocidad;
    }

    /**
     * ritmoUsuario: calcula el ritmo al que avanza en el usuario en minutos/kilometro
     * @param velocidad
     * @return
     */
    private double ritmoUsuario(double velocidad){

        double ritmo;

        ritmo = MINKM / velocidad;

        return ritmo;
    }

    private int formateoSegundos(double segundos){
        int segundosFormateados;

        double segundosReales = segundos*60;

        segundosFormateados = (int) segundosReales;

        return segundosFormateados;
    }


    /**
     * obtenerDuracion: formatea la duracion del ejercicio de milisegundos al formato horas, minutos,segundos
     * @param milisegundos
     * @return
     */
    private String obtenerDuracion (long milisegundos){
        String duracion;

        long segundos = (long)(milisegundos/1000)%60;
        long minutos = (long)((milisegundos/(1000*60))%60);
        long horas = (long)((milisegundos/(1000*60*60))%24);

        duracion = String.format("%02d:%02d:%02d",horas,minutos,segundos);

        return duracion;

    }


    /**
     * capturaMapa: hace una captura del mapa con el itineratio hecho por el usuario cuando
     * finaliza el ejercicio
     * @param fechaCaptura
     */
    private void capturarMapa (String fechaCaptura){
        mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(@Nullable Bitmap bitmap) {
                String fileName = "map_capture_" + fechaCaptura + ".png";
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput(fileName,MODE_PRIVATE);
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

}