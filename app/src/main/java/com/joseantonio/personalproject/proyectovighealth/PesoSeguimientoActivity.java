package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasPesoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityPesoSeguimientoBinding;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PesoSeguimientoActivity extends DrawerBaseActivity {

    ActivityPesoSeguimientoBinding pesoSeguimientoBinding;

    EditText pesoEt;
    Button nuevoPeso;

    double peso, diferenciaPeso,imc;
    String fecha,activityTitle;
    int idUsuario;
    boolean pesoActualizado;
    DecimalFormatSymbols symbols = new DecimalFormatSymbols();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pesoSeguimientoBinding = ActivityPesoSeguimientoBinding.inflate(getLayoutInflater());
        setContentView(pesoSeguimientoBinding.getRoot());
        activityTitle = getString(R.string.at_peso_seguimiento);
        allocateActivityTitle(activityTitle);

        symbols.setDecimalSeparator('.');
        DecimalFormat formatoDos = new DecimalFormat("##.##",symbols);

        pesoEt = findViewById(R.id.etPeso);
        nuevoPeso = findViewById(R.id.btnNuevoPeso);

        nuevoPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peso = Float.parseFloat(pesoEt.getText().toString());
                fecha = Utilidades.obtenerFechaActual();

                ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(PesoSeguimientoActivity.this);
                double pesoAnterior = consultasUsuario.obtenerPesoUsuario();
                diferenciaPeso = Double.parseDouble(formatoDos.format(-1*(pesoAnterior - peso)));
                double alturaUsuario = consultasUsuario.obtenerAlturaUsuario();
                imc = Double.parseDouble(formatoDos.format(peso/(alturaUsuario*alturaUsuario)));
                idUsuario = consultasUsuario.obtenerIdUsuario();
                pesoActualizado = consultasUsuario.modificarPesoUsuario(idUsuario,peso);

                if(pesoActualizado){
                    Toast.makeText(PesoSeguimientoActivity.this, "Peso del usuario actualizado",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(PesoSeguimientoActivity.this, "Fallo en actualizacion de peso del usuario",Toast.LENGTH_LONG).show();
                }

                    ConsultasPesoImpl consultasPesoRegistrado = new ConsultasPesoImpl(PesoSeguimientoActivity.this);
                    long id = consultasPesoRegistrado.nuevoPeso(idUsuario, peso, fecha, imc, diferenciaPeso);

                if (id>0){
                    Toast.makeText(PesoSeguimientoActivity.this, "Registro guardado",Toast.LENGTH_LONG).show();
                    limpiar();
                }else{
                    Toast.makeText(PesoSeguimientoActivity.this, "ERROR AL GUARDAR EL REGISTRO",Toast.LENGTH_LONG).show();
                }

            }
        });

        pesoSeguimientoBinding.btNavSeguimientoPeso.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.mnHistorialPeso:
                    historialPeso();
                    break;
                case R.id.mnGraficaPeso:
                    graficaPeso();
                    break;
            }
            return true;
        });


    }


    private void historialPeso(){
        Intent intent = new Intent(this,HistoricoPesoActivity.class);
        startActivity(intent);
    }

    private void graficaPeso(){
        Intent intent = new Intent(this,GraficaPesoActivity.class);
        startActivity(intent);
    }


    private void limpiar(){
        pesoEt.setText("");
    }

}