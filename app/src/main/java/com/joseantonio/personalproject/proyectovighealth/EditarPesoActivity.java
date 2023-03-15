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

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasPesoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityEditarPesoBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class EditarPesoActivity extends DrawerBaseActivity {

    ActivityEditarPesoBinding editarPesoBinding;

    TextView tvFechaRegistro;

    EditText etEditarPeso;

    Button btnEditarPeso, btnEliminarPeso;

    String etiquetaFecha = "Fecha de registro: ";


    double pesoRecuperado, diferenciaPeso,imc;

    int id = 0;

    String activityTitle;

    DecimalFormatSymbols symbols = new DecimalFormatSymbols();

    Peso peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editarPesoBinding = ActivityEditarPesoBinding.inflate(getLayoutInflater());
        setContentView(editarPesoBinding.getRoot());
        activityTitle = getString(R.string.at_editar_peso);
        allocateActivityTitle(activityTitle);
        HistoricoPesoActivity.historicoPeso.finish();

        //Formatea decimales
        symbols.setDecimalSeparator('.');
        DecimalFormat formatoDos = new DecimalFormat("##.##",symbols);

        tvFechaRegistro = findViewById(R.id.tv_fechaRegistroPeso);
        etEditarPeso = findViewById(R.id.etEdPeso);
        btnEditarPeso = findViewById(R.id.btnEditarPeso);
        btnEliminarPeso = findViewById(R.id.btnEliminarPeso);

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

        ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(EditarPesoActivity.this);
        peso = consultasPeso.verPeso(id);

        if(peso!=null){
            etEditarPeso.setText(String.valueOf(peso.getPeso()));
            String fechaEuropea = Utilidades.fechaEuropea(peso.getFechaPeso());
            tvFechaRegistro.setText(etiquetaFecha + fechaEuropea);
        }

        btnEditarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPesoActivity.this);
                builder.setMessage("¿Desea modificar el registro de peso?");
                builder.setTitle("Confirmar edición");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        editarPeso();
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

        btnEliminarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditarPesoActivity.this);
                builder.setMessage("¿Desea eliminar el registro de peso?");
                builder.setTitle("Confirmar eliminación");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminarPeso();
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
     * editarPeso: lleva a cabo la operacion de edicion del registro de Peso seleccionado por el usuario
     */
    private void editarPeso(){
        pesoRecuperado = Float.parseFloat(etEditarPeso.getText().toString());

        //Para formatear decimales
        symbols.setDecimalSeparator('.');
        DecimalFormat formatoDos = new DecimalFormat("##.##",symbols);

        ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(EditarPesoActivity.this);
        double pesoAnterior = consultasUsuario.obtenerPesoUsuario();
        diferenciaPeso = Double.parseDouble(formatoDos.format(-1*(pesoAnterior - pesoRecuperado)));
        double alturaUsuario = consultasUsuario.obtenerAlturaUsuario();
        imc = Double.parseDouble(formatoDos.format(pesoRecuperado/(alturaUsuario*alturaUsuario)));
        int idUsuario = consultasUsuario.obtenerIdUsuario();
        boolean pesoActualizado = consultasUsuario.modificarPesoUsuario(idUsuario,pesoRecuperado);

        if(pesoActualizado){
            Toast.makeText(EditarPesoActivity.this, "Peso actualizado",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(EditarPesoActivity.this, "Fallo en actualizacion de peso",Toast.LENGTH_LONG).show();
        }

        ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(EditarPesoActivity.this);
        boolean pesoEditado = consultasPeso.editarPeso(id,pesoRecuperado,imc,diferenciaPeso);

        if(pesoEditado){
            Toast.makeText(EditarPesoActivity.this, "Registro de peso modificado correctamente.",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarPesoActivity.this,HistoricoPesoActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(EditarPesoActivity.this, "ERROR.Fallo en la modificación del registro.",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * eliminarPeso: lleva a cabo la operacion de eliminación del registro de Peso seleccionado por el usuario
     */
    private void eliminarPeso(){

        ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(EditarPesoActivity.this);
        boolean registroEliminado = consultasPeso.eliminarPeso(id);

        if(registroEliminado){
            Toast.makeText(EditarPesoActivity.this,
                    "Registro de peso eliminado correctamente",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(EditarPesoActivity.this,HistoricoPesoActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(EditarPesoActivity.this,
                    "ERROR.No se ha podido eliminar el registro.",Toast.LENGTH_LONG).show();
        }
    }
}