package com.joseantonio.personalproject.proyectovighealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class NuevaActividadActivity extends AppCompatActivity
implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actividad);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}