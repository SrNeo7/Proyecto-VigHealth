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
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityNuevaTensionBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NuevaTensionActivity extends DrawerBaseActivity {

    ActivityNuevaTensionBinding nuevaTensionBinding;

    EditText sistolica, diastolica;
    Button guardar;
    RecyclerView listaTension;

    String fecha, categoria;
    float sist, diast;

    ArrayList<Tension>listaRegistrosTension;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevaTensionBinding = ActivityNuevaTensionBinding.inflate(getLayoutInflater());
        setContentView(nuevaTensionBinding.getRoot());
        allocateActivityTitle("Nuevo registro de tension");

        sistolica = findViewById(R.id.et_sistolica);
        diastolica = findViewById(R.id.et_diastolica);
        guardar = findViewById(R.id.btn_añadirTension);
        listaTension = findViewById(R.id.rvRegistrosTension);
        listaTension.setLayoutManager(new LinearLayoutManager(this));

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sist = Float.parseFloat(sistolica.getText().toString());
                diast = Float.parseFloat(diastolica.getText().toString());
                fecha = obtenerFechaHoy();
                categoria = obtenerCategoria(sist,diast);

                ConsultasTensionImpl consultasTension =
                        new ConsultasTensionImpl(NuevaTensionActivity.this);

                long id = consultasTension.nuevoRegistroTension(fecha,sist,diast,categoria);

                if (id>0){
                    Toast.makeText(NuevaTensionActivity.this, "Registro guardado",Toast.LENGTH_LONG).show();
                    TensionAdapter adapter = new TensionAdapter(consultasTension.mostrarRegistrosTension());
                    listaTension.setAdapter(adapter);
                    limpiar();
                }else{
                    Toast.makeText(NuevaTensionActivity.this, "ERROR AL GUARDAR EL REGISTRO",Toast.LENGTH_LONG).show();
                }

            }
        });

        ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(NuevaTensionActivity.this);
        listaRegistrosTension = new ArrayList<>();
        TensionAdapter adapter = new TensionAdapter(consultasTension.mostrarRegistrosTension());
        listaTension.setAdapter(adapter);


    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_tension, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mnHistorialTension:
                historialTension();
                return true;
            case R.id.mnGrafica:
                graficaTension();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

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

    public String obtenerFechaHoy(){

        String fechaActual;

        fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

        return fechaActual;
    }

    /**
     * obtenerCategoria: Funcion que asigna una categoria a la medicion de tension introducida
     * @param sistolica Float con la tension sistolica introducida por el usuario
     * @param diastolica Float con la tension diastolica introducida por el usuario
     * @return Un string con el resultado de la evaluacion de la medicion recibida como parametro
     */
    public String obtenerCategoria(float sistolica , float diastolica){

        String categoriaAsignada;

        //Limites de los valores de tension para establecer la comparacion con los parametros
        final float limiteHipoSis = 8;
        final float limiteHipoDias = 6;
        final float limiteHiperSis = 12;
        final float limiteHiperDias = 8;

        //Comparacion de los valores recibidos por parametro con los limites establecidos
        if (sistolica<limiteHipoSis || diastolica<limiteHipoDias){
            categoriaAsignada = "Baja";
        }else if (sistolica>limiteHiperSis || diastolica>limiteHiperDias){
            categoriaAsignada = "Alta";
        }else{
            categoriaAsignada = "Normal";
        }

        return categoriaAsignada;
    }
}