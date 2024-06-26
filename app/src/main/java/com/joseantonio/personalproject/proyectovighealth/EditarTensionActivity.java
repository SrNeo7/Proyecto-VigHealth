package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasTensionImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityEditarMedicamentoBinding;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityEditarTensionBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

public class EditarTensionActivity extends DrawerBaseActivity {

    ActivityEditarTensionBinding editarTensionBinding;

    TextView tvFechaRegistro;

    EditText etEdSistolica, etEdDiastolica;

    Button btnModificarTension, btnEliminarTension;

    String etiquetaFecha = "Fecha de registro: ";

    String valoracion;
    int id = 0;

    float sistolica, diastolica;

    Tension tension;

    String activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editarTensionBinding = ActivityEditarTensionBinding.inflate(getLayoutInflater());
        setContentView(editarTensionBinding.getRoot());
        activityTitle = getString(R.string.at_editar_tension);
        allocateActivityTitle(activityTitle);
        HistoricoTensionActivity.historicoTension.finish();

        tvFechaRegistro = findViewById(R.id.tv_fechaRegistroTension);
        etEdSistolica = findViewById(R.id.etEd_sistolica);
        etEdDiastolica = findViewById(R.id.etEd_diastolica);
        btnModificarTension = findViewById(R.id.btn_editarTension);
        btnEliminarTension = findViewById(R.id.btn_eliminarTension);

        //Recupera el id enviado desde el item del recycler view seleccionado
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


        ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(EditarTensionActivity.this);
        tension = consultasTension.verTension(id);


        if(tension!=null){
            etEdSistolica.setText(String.valueOf(tension.getSistolica()));
            etEdDiastolica.setText(String.valueOf(tension.getDiastolica()));
            String fechaEuropea = Utilidades.fechaEuropea(tension.getFechaTension());
            tvFechaRegistro.setText(etiquetaFecha + fechaEuropea);
            valoracion = tension.getValoracion();
        }

        btnModificarTension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarTensionActivity.this);
                builder.setMessage("¿Desea modificar el registro de tensión?");
                builder.setTitle("Confirmar edición");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editarTension();
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

            }
        });

        btnEliminarTension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarTensionActivity.this);
                builder.setMessage("¿Desea eliminar el registro de tensión?");
                builder.setTitle("Confirmar eliminación");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarTension();
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

            }
        });


    }


    /**
     * editarTension: lleva a cabo la operacion de edicion del registro de Tension seleccionado por el usuario
     */
    private void editarTension(){
        sistolica = Float.parseFloat(etEdSistolica.getText().toString());
        diastolica = Float.parseFloat(etEdDiastolica.getText().toString());
        valoracion = Utilidades.obtenerCategoria(sistolica,diastolica);

        ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(EditarTensionActivity.this);
        boolean verificacionEdicion = consultasTension.editarTension(id,sistolica,diastolica,valoracion);

        if(verificacionEdicion){
            Toast.makeText(EditarTensionActivity.this,
                    "Registro de tension actualizado correctamente",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarTensionActivity.this,HistoricoTensionActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(EditarTensionActivity.this,
                    "ERROR.Actualizacion fallida.Formato de datos incorrecto",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * eliminarTension: lleva a cabo la operacion de eliminación del registro de Tension seleccionado por el usuario
     */
    private void eliminarTension(){
        ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(EditarTensionActivity.this);
        boolean verificacionEliminacion = consultasTension.eliminarTension(id);

        if(verificacionEliminacion){
            Toast.makeText(EditarTensionActivity.this,
                    "Registro de tension eliminado correctamente",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarTensionActivity.this,HistoricoTensionActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(EditarTensionActivity.this,
                    "ERROR.No se ha podido eliminar el registro.",Toast.LENGTH_LONG).show();
        }
    }
}