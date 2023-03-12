package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasHidratacionImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityHidratacionBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Hidratacion;
import com.joseantonio.personalproject.proyectovighealth.workers.NotificacionesHidWorker;
import com.joseantonio.personalproject.proyectovighealth.workers.NotificacionesMedWorker;

import java.sql.Array;
import java.util.concurrent.TimeUnit;

public class HidratacionActivity extends DrawerBaseActivity {

    ActivityHidratacionBinding hidratacionBinding;
    SwitchMaterial swtEstado;
    Spinner spnFrecuencia;
    MaterialButton btnGuardar,btnModificar,btnEliminar;

    String activityTitle;

    int estado,frecuencia,idUsuario;

    Hidratacion comprobacionHid = new Hidratacion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hidratacionBinding = ActivityHidratacionBinding.inflate(getLayoutInflater());
        setContentView(hidratacionBinding.getRoot());
        activityTitle = getString(R.string.at_hidratacion);
        allocateActivityTitle(activityTitle);

        swtEstado = findViewById(R.id.swtRecorOn);
        spnFrecuencia = findViewById(R.id.spnFrecuencia);
        btnGuardar = findViewById(R.id.btnRecHidra);
        btnModificar = findViewById(R.id.btnModHidra);
        btnEliminar = findViewById(R.id.btnElimHidra);

        comprobacionHid = comprobarRecordatorio();

        if(comprobacionHid !=null){
            btnGuardar.setVisibility(View.INVISIBLE);
            btnGuardar.setEnabled(false);
            btnModificar.setVisibility(View.VISIBLE);
            btnModificar.setEnabled(true);
            btnEliminar.setVisibility(View.VISIBLE);
            btnEliminar.setEnabled(true);
            swtEstado.setChecked(true);
            spnFrecuencia.setSelection(comprobacionHid.getFrecuencia()-1);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swtEstado.isChecked()){
                    estado = 1;
                    String frecS = spnFrecuencia.getSelectedItem().toString();
                    String numFrec = frecS.substring(0,1);
                    frecuencia = Integer.parseInt(numFrec);

                    ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(HidratacionActivity.this);
                    idUsuario = consultasUsuario.obtenerIdUsuario();

                    ConsultasHidratacionImpl consultasHidratacion = new ConsultasHidratacionImpl(HidratacionActivity.this);
                    long id = consultasHidratacion.nuevoRecordatorio(idUsuario,estado,frecuencia);

                    if(id>0){
                        Toast.makeText(HidratacionActivity.this,
                                "Recordatorio guardado correctamente",Toast.LENGTH_LONG).show();
                        String idTag = id + "hid";
                        Data data = notificacionDatos("¡Hidratate!","Bebe un poco de agua para estar bien hidratado",(int)id);
                        guardarRecordatorioNotif(frecuencia,data,idTag);
                    }else{
                        Toast.makeText(HidratacionActivity.this,
                                "Error al guardar los datos. Reviselos y vuelva a intentarlo",Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(HidratacionActivity.this,
                            "El interruptor para activar el recordatorio no esta activado.",Toast.LENGTH_LONG).show();
                }
            }
        });


        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estado = 1;
                String frecS = spnFrecuencia.getSelectedItem().toString();
                String numFrec = frecS.substring(0,1);
                frecuencia = Integer.parseInt(numFrec);
                 ConsultasHidratacionImpl consultasHidratacion = new ConsultasHidratacionImpl(HidratacionActivity.this);
                 int idHidratacion = consultasHidratacion.obtenerIdRecordatorio();
                 boolean verificacionModif = consultasHidratacion.editarRecordatorio(idHidratacion,estado,frecuencia);

                String idTag = idHidratacion + "hid";

                 if (verificacionModif){
                     Toast.makeText(HidratacionActivity.this,
                             "Recordatorio actualizado correctamente",Toast.LENGTH_LONG).show();
                     eliminarRecordatorioNotif(idTag);
                     Data data = notificacionDatos("¡Hidratate!","Bebe un poco de agua para estar bien hidratado",idHidratacion);
                     guardarRecordatorioNotif(frecuencia,data,idTag);
                 }else{
                     Toast.makeText(HidratacionActivity.this,
                             "ERROR.Actualizacion fallida. Comprueba que los datos son correctos",
                             Toast.LENGTH_LONG).show();
                 }


            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConsultasHidratacionImpl consultasHidratacion = new ConsultasHidratacionImpl(HidratacionActivity.this);
                int idHidratacion = consultasHidratacion.obtenerIdRecordatorio();
                boolean verificacioinElim = consultasHidratacion.eliminarRecordatorio(idHidratacion);
                String idTag = idHidratacion + "hid";
                if (verificacioinElim){
                    Toast.makeText(HidratacionActivity.this,
                            "Recordatorio eliminado correctamente",Toast.LENGTH_LONG).show();

                    eliminarRecordatorioNotif(idTag);

                    btnGuardar.setVisibility(View.VISIBLE);
                    btnGuardar.setEnabled(true);
                    btnModificar.setVisibility(View.INVISIBLE);
                    btnModificar.setEnabled(false);
                    btnEliminar.setVisibility(View.INVISIBLE);
                    btnEliminar.setEnabled(false);
                }else{
                    Toast.makeText(HidratacionActivity.this,
                            "ERROR. No se ha podido eliminar el recordatorio",
                            Toast.LENGTH_LONG).show();
                }


            }
        });


    }

    public Hidratacion comprobarRecordatorio(){


        Hidratacion hidratacion = null;

        ConsultasHidratacionImpl consultasHidratacion = new ConsultasHidratacionImpl(HidratacionActivity.this);
        hidratacion = consultasHidratacion.obtenerRecordatorio();


        return hidratacion;
    }

    void eliminarRecordatorioNotif(String tag){
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
    }

    private Data notificacionDatos(String titulo, String descripcion, int idRecordatorio){

        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("descripcion",descripcion)
                .putInt("idRecordatorio",idRecordatorio).build();

    }

    public void guardarRecordatorioNotif(int duracion, Data data, String tag){
        PeriodicWorkRequest recordatorio = new PeriodicWorkRequest.Builder
                (NotificacionesHidWorker.class,duracion, TimeUnit.HOURS)
                .setInitialDelay(15,TimeUnit.MINUTES)
                .setBackoffCriteria(BackoffPolicy.LINEAR,15,TimeUnit.MINUTES)
                .addTag(tag)
                .setInputData(data)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(recordatorio);
    }


}
