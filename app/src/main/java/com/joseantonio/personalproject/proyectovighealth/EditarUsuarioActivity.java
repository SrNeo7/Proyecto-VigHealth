package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.objetos.Usuario;

public class EditarUsuarioActivity extends AppCompatActivity {

    TextView tvNombreUsuario,tvApellidosUsuario;

    EditText etEdPeso, etEdAltura,etEdEdad;

    Spinner spEdGenero;

    Button btnGuardarCambios,btnCancelarCambios;

    Usuario usuario;

    String edGenero;

    int edEdad;

    float edPeso,edAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        tvApellidosUsuario = findViewById(R.id.tvApellidosUsuario);
        etEdPeso = findViewById(R.id.etEdPesoUsuario);
        etEdAltura = findViewById(R.id.etEdAlturaUsuario);
        etEdEdad = findViewById(R.id.etEdEdadUsuario);
        spEdGenero = findViewById(R.id.spEdGenero);
        btnGuardarCambios = findViewById(R.id.btnEditarUsuario);
        btnCancelarCambios = findViewById(R.id.btnCanecelarEditar);

        ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(EditarUsuarioActivity.this);
        usuario = consultasUsuario.mostarDatosUsuario();

        if(usuario!=null){
            tvNombreUsuario.setText(usuario.getNombre());
            tvApellidosUsuario.setText(usuario.getApellidos());
            etEdPeso.setText(String.valueOf(usuario.getPeso()));
            etEdAltura.setText(String.valueOf(usuario.getAltura()));
            etEdEdad.setText(String.valueOf(usuario.getEdad()));
            spEdGenero.setSelection(0);
        }

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edAltura = Float.parseFloat(etEdAltura.getText().toString());
                edPeso = Float.parseFloat(etEdPeso.getText().toString());
                edGenero = spEdGenero.getSelectedItem().toString();
                edEdad = Integer.parseInt(etEdEdad.getText().toString());

                boolean verificacionEditUsuario = consultasUsuario
                        .modificarDatosUsuario(1,edEdad,edGenero,edPeso,edAltura);

                if(verificacionEditUsuario){
                    Toast.makeText(EditarUsuarioActivity.this,
                            "Datos de usuario actualizados correctamente.",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditarUsuarioActivity.this,ConfiguracionActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(EditarUsuarioActivity.this,
                            "Fallo de actualizacion de datos.Revise los datos introducidos.",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCancelarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}