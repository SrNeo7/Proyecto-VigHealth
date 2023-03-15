package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasMedicamentoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityNuevoMedicamentoBinding;
import com.joseantonio.personalproject.proyectovighealth.workers.NotificacionesMedWorker;

import java.util.concurrent.TimeUnit;

public class NuevoMedicamentoActivity extends DrawerBaseActivity {

    ActivityNuevoMedicamentoBinding nuevoMedicamentoBinding;
    EditText nombreMed, dosisMed, periodicMed,comentMed;
    Spinner medidaDosis;
    Button aceptarBtn, cancelarBtn;

    String nombreCad,medidaDosisCad,comentCad;

    int periodicNum,dosisMedNum,idUsuario;

    String activityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevoMedicamentoBinding = ActivityNuevoMedicamentoBinding.inflate(getLayoutInflater());
        setContentView(nuevoMedicamentoBinding.getRoot());
        activityTitle = getString(R.string.at_nuevo_medicamento);
        allocateActivityTitle(activityTitle);

        nombreMed = findViewById(R.id.etNombreMedicamento);
        dosisMed = findViewById(R.id.etDosis);
        medidaDosis = findViewById(R.id.spMedida);
        periodicMed = findViewById(R.id.etPeriodicidad);
        comentMed = findViewById(R.id.etComentarios);
        aceptarBtn = findViewById(R.id.btnMedAceptar);
        cancelarBtn = findViewById(R.id.btnMedCancelar);

        aceptarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nombreCad = nombreMed.getText().toString();
                medidaDosisCad = medidaDosis.getSelectedItem().toString();
                comentCad = comentMed.getText().toString();
                periodicNum = Integer.parseInt(periodicMed.getText().toString());
                dosisMedNum = Integer.parseInt(dosisMed.getText().toString());

                ConsultasUsuarioImpl consultasUsuario =
                        new ConsultasUsuarioImpl(NuevoMedicamentoActivity.this);
                idUsuario = consultasUsuario.obtenerIdUsuario();

                ConsultasMedicamentoImpl consultasMedicamento =
                        new ConsultasMedicamentoImpl(NuevoMedicamentoActivity.this);

                long id =
                        consultasMedicamento
                                .nuevoMedicamento(idUsuario,nombreCad,dosisMedNum,
                                        medidaDosisCad,periodicNum,comentCad);


                if(id>0){
                    Toast.makeText(NuevoMedicamentoActivity.this,
                            "Medicamento guardado correctamente",Toast.LENGTH_LONG).show();
                    String idTag = id + "med";
                    Data data = notificacionDatos(nombreCad,"Es hora de tomar tu medicación",(int)id);
                    guardarRecordatorio(periodicNum,data,idTag);
                    limpiarcampos();
                }else{
                    Toast.makeText(NuevoMedicamentoActivity.this,
                            "Error al guardar los datos. Reviselos y vuelva a intentarlo",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        cancelarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void limpiarcampos(){

        nombreMed.setText("");
        dosisMed.setText("");
        medidaDosis.setSelection(0);
        periodicMed.setText("");
        comentMed.setText("");

    }

    /**
     * notifiacionDatos: coloca en la notificacion la informacion que se mostrara en la notificacion
     * del recordatorio
     * @param titulo
     * @param descripcion
     * @param idRecordatorio
     * @return
     */
    private Data notificacionDatos(String titulo, String descripcion, int idRecordatorio){

        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("descripcion",descripcion)
                .putInt("idRecordatorio",idRecordatorio).build();

    }

    /**
     * guardarRecordatorio: Crea y configura el WorkRequest que se encargara de mostrar las
     * notificaciones de recordatorio
     * @param duracion
     * @param data
     * @param tag
     */
    public void guardarRecordatorio(int duracion, Data data, String tag){
        PeriodicWorkRequest recordatorio = new PeriodicWorkRequest.Builder
                (NotificacionesMedWorker.class,duracion,TimeUnit.HOURS)
                .setInitialDelay(15,TimeUnit.MINUTES)
                .setBackoffCriteria(BackoffPolicy.LINEAR,15,TimeUnit.MINUTES)
                .addTag(tag)
                .setInputData(data)
                .build();

        WorkManager.getInstance(getApplicationContext()).enqueue(recordatorio);
    }



}