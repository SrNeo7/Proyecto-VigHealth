package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.adaptadores.TensionAdapter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasTensionImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityNuevaTensionBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NuevaTensionActivity extends DrawerBaseActivity {

    ActivityNuevaTensionBinding nuevaTensionBinding;

    EditText sistolica, diastolica;
    Button guardar;

    String fecha, categoria,activityTitle;
    float sist, diast;
    int idUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevaTensionBinding = ActivityNuevaTensionBinding.inflate(getLayoutInflater());
        setContentView(nuevaTensionBinding.getRoot());
        activityTitle = getString(R.string.at_nuevo_registro_tension);
        allocateActivityTitle(activityTitle);

        sistolica = findViewById(R.id.et_sistolica);
        diastolica = findViewById(R.id.et_diastolica);
        guardar = findViewById(R.id.btn_añadirTension);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sist = Float.parseFloat(sistolica.getText().toString());
                diast = Float.parseFloat(diastolica.getText().toString());
                fecha = Utilidades.obtenerFechaActual();
                categoria = Utilidades.obtenerCategoria(sist,diast);
                ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(NuevaTensionActivity.this);
                idUsuario = consultasUsuario.obtenerIdUsuario();

                ConsultasTensionImpl consultasTension =
                        new ConsultasTensionImpl(NuevaTensionActivity.this);

                long id = consultasTension.nuevoRegistroTension(idUsuario,fecha,sist,diast,categoria);

                if (id>0){
                    Toast.makeText(NuevaTensionActivity.this, "Registro guardado",Toast.LENGTH_LONG).show();
                    limpiar();
                }else{
                    Toast.makeText(NuevaTensionActivity.this, "ERROR AL GUARDAR EL REGISTRO",Toast.LENGTH_LONG).show();
                }

            }
        });

        nuevaTensionBinding.btNavNuevaTension.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.mnHistorialTension:
                    historialTension();
                    break;
                case R.id.mnGrafica:
                    graficaTension();
                    break;
            }

            return true;
        });

    }

    private void historialTension(){
        Intent intent = new Intent(this,HistoricoTensionActivity.class);
        startActivity(intent);
    }

    private void graficaTension(){
        Intent intent = new Intent(this,GraficaTensionActivity.class);
        startActivity(intent);
    }


    private void limpiar(){
        sistolica.setText("");
        diastolica.setText("");
    }

}