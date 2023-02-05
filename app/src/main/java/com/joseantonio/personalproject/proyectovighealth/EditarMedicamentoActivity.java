package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasMedicamentoImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityEditarMedicamentoBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;

public class EditarMedicamentoActivity extends DrawerBaseActivity {

    ActivityEditarMedicamentoBinding editarMedicamentoBinding;
    EditText etNombreMed, etDosisMed, etPeriodicidadMed, etComentariosMed;
    Spinner spMedidaMed;

    Medicamento medicamento;
    int id = 0;

    String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editarMedicamentoBinding = ActivityEditarMedicamentoBinding.inflate(getLayoutInflater());
        setContentView(editarMedicamentoBinding.getRoot());
        activityTitle = getString(R.string.at_mod_medicamento);
        allocateActivityTitle(activityTitle);

        etNombreMed = findViewById(R.id.etEdNombreMedicamento);
        etDosisMed = findViewById(R.id.etEdDosis);
        etPeriodicidadMed = findViewById(R.id.etEdPeriodicidad);
        etComentariosMed = findViewById(R.id.etEdComentarios);
        spMedidaMed = findViewById(R.id.spEdMedida);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            }else {
                id = extras.getInt("ID");
            }
        }else {
            id = (int)savedInstanceState.getSerializable("ID");
        }

        ConsultasMedicamentoImpl consultasMedicamento =
                new ConsultasMedicamentoImpl(EditarMedicamentoActivity.this);
        medicamento = consultasMedicamento.verMedicamento(id);


        if (medicamento != null){
            etNombreMed.setText(medicamento.getNombreMedicamento());
            etDosisMed.setText(String.valueOf(medicamento.getDosis()));
            etPeriodicidadMed.setText(String.valueOf(medicamento.getPeriodicidad()));
            etComentariosMed.setText(medicamento.getComentarios());
            spMedidaMed.setSelection(0);
        }

        editarMedicamentoBinding.btNavEdMed.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.btmenu_home:
                    finish();
                    break;
                case R.id.btmenu_edit:
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditarMedicamentoActivity.this);
                    builder.setMessage("¿Desea guardar los cambios del medicamento?");
                    builder.setTitle("Editar medicamento");
                    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            modificarMed();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    break;
                case R.id.btmenu_delete:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(EditarMedicamentoActivity.this);
                    builder2.setMessage("¿Desea eliminar el  medicamento?");
                    builder2.setTitle("Eliminar medicamento");
                    builder2.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            eliminiarMed();
                        }
                    });
                    builder2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog dialog2 = builder2.create();
                    dialog2.show();

                    break;
            }
            return true;
        });


    }

    void modificarMed(){
        String nombreMedEdit = etNombreMed.getText().toString();
        String medidaDosisEdit = spMedidaMed.getSelectedItem().toString();
        String comentariosMedEdit = etComentariosMed.getText().toString();
        int dosisMedEdit = Integer.parseInt(etDosisMed.getText().toString());
        int periodicidadMedEdit = Integer.parseInt(etPeriodicidadMed.getText().toString());

        ConsultasMedicamentoImpl consultasMedicamento = new ConsultasMedicamentoImpl(EditarMedicamentoActivity.this);
        boolean verificacionEdit = consultasMedicamento
                .editarMedicamento(id,nombreMedEdit,dosisMedEdit,medidaDosisEdit,
                        periodicidadMedEdit,comentariosMedEdit);

        if (verificacionEdit){
            Toast.makeText(EditarMedicamentoActivity.this,
                    "Medicamento actualizado correctamente",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarMedicamentoActivity.this,ListaMedicamentosActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(EditarMedicamentoActivity.this,
                    "ERROR.Actualizacion del medicamento fallida. Datos incorrectos.",Toast.LENGTH_LONG).show();
        }
    }

    void eliminiarMed(){

        ConsultasMedicamentoImpl consultasMedicamento = new ConsultasMedicamentoImpl(EditarMedicamentoActivity.this);
        boolean eliminado = consultasMedicamento.eliminarMedicamento(id);

        if(eliminado){
            Toast.makeText(EditarMedicamentoActivity.this,
                    "Medicamento eliminado",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarMedicamentoActivity.this,ListaMedicamentosActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(EditarMedicamentoActivity.this,
                    "ERROR.No se ha podido eliminar el medicamento.",Toast.LENGTH_LONG).show();
        }
    }
}