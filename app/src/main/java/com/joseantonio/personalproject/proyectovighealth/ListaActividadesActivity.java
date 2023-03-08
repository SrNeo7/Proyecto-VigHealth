package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joseantonio.personalproject.proyectovighealth.adaptadores.ActividadAdapter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasActividadImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityListaActividadesBinding;

public class ListaActividadesActivity extends DrawerBaseActivity {

    RecyclerView listaActividades;

    ActivityListaActividadesBinding listaActividadesBinding;

    String activityTitle,nombreActividad;

    FloatingActionButton fbtnNuevaActividad,fbtnAndar,fbtnCorrer,fbtnCiclismo;

    TextView tvAndar,tvCorrer,tvCiclismo;

    boolean nuevaActividad = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaActividadesBinding = ActivityListaActividadesBinding.inflate(getLayoutInflater());
        setContentView(listaActividadesBinding.getRoot());
        activityTitle = getString(R.string.at_lista_actividad);
        allocateActivityTitle(activityTitle);

        listaActividades = findViewById(R.id.rvListaActividades);
        fbtnNuevaActividad = findViewById(R.id.fabtnNuevaActividad);
        fbtnAndar = findViewById(R.id.fabtnAndar);
        fbtnCorrer = findViewById(R.id.fabtnCorrer);
        fbtnCiclismo = findViewById(R.id.fabtnCiclismo);
        tvAndar = findViewById(R.id.tvAndarFAB);
        tvCorrer = findViewById(R.id.tvCorrerFAB);
        tvCiclismo = findViewById(R.id.tvCiclismorFAB);

        ConsultasActividadImpl consultasActividad = new ConsultasActividadImpl(ListaActividadesActivity.this);
        ActividadAdapter adapter = new ActividadAdapter(consultasActividad.mostrarActividadTotal());
        listaActividades.setAdapter(adapter);
        listaActividades.setLayoutManager(new LinearLayoutManager(this));

        fbtnNuevaActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!nuevaActividad){
                fbtnAndar.setEnabled(true);
                fbtnAndar.setVisibility(View.VISIBLE);
                tvAndar.setVisibility(View.VISIBLE);
                fbtnCorrer.setEnabled(true);
                fbtnCorrer.setVisibility(View.VISIBLE);
                tvCorrer.setVisibility(View.VISIBLE);
                fbtnCiclismo.setEnabled(true);
                fbtnCiclismo.setVisibility(View.VISIBLE);
                tvCiclismo.setVisibility(View.VISIBLE);
                nuevaActividad=true;

                }else{
                    fbtnAndar.setEnabled(false);
                    fbtnAndar.setVisibility(View.GONE);
                    tvAndar.setVisibility(View.GONE);
                    fbtnCorrer.setEnabled(false);
                    fbtnCorrer.setVisibility(View.GONE);
                    tvCorrer.setVisibility(View.GONE);
                    fbtnCiclismo.setEnabled(false);
                    fbtnCiclismo.setVisibility(View.GONE);
                    tvCiclismo.setVisibility(View.GONE);
                    nuevaActividad = false;
                }
            }
        });

        fbtnAndar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreActividad = "Andar";
                comenzarActividad(nombreActividad);
            }
        });

        fbtnCorrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreActividad = "Correr";
                comenzarActividad(nombreActividad);
            }
        });

        fbtnCiclismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreActividad = "Ciclismo";
                comenzarActividad(nombreActividad);
            }
        });

    }

    public void comenzarActividad(String actividad){
        Intent intent = new Intent(ListaActividadesActivity.this,NuevaActividadActivity.class);
        intent.putExtra("Actividad",actividad);
        startActivity(intent);
        finish();
    }

}