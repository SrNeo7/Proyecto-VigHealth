package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasMedicamentoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;

public class NuevoMedicamentoActivity extends AppCompatActivity {

    EditText nombreMed, dosisMed, periodicMed,comentMed;
    Spinner medidaDosis;
    Button aceptarBtn, cancelarBtn;

    String nombreCad,medidaDosisCad,comentCad;

    int periodicNum,dosisMedNum,idUsuario;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_medicamento);

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
}