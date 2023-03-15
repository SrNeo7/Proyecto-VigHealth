package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityNuevoUsuarioBinding;

public class NuevoUsuarioActivity extends DrawerBaseActivity {

    ActivityNuevoUsuarioBinding nuevoUsuarioBinding;

    EditText nombreUsuario, apellidosUsuario, alturaUsuario, edadUsuario, pesoUsuario;
    Spinner spGenero;
    Button btnNuevoUsuario;
    String nombre,apellidos,genero;

    String activityTitle;

    int edad;
    float altura, peso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nuevoUsuarioBinding = ActivityNuevoUsuarioBinding.inflate(getLayoutInflater());
        setContentView(nuevoUsuarioBinding.getRoot());
        activityTitle = getString(R.string.at_nuevo_usuario);
        allocateActivityTitle(activityTitle);

        nombreUsuario = findViewById(R.id.etNombreUsuario);
        apellidosUsuario = findViewById(R.id.etApellidosUsuario);
        alturaUsuario = findViewById(R.id.etAlturaUsuario);
        edadUsuario = findViewById(R.id.etEdadUsuario);
        pesoUsuario = findViewById(R.id.etPesoUsuario);
        spGenero = findViewById(R.id.spGenero);
        btnNuevoUsuario = findViewById(R.id.btbGuardarUsuario);

        btnNuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = nombreUsuario.getText().toString();
                apellidos = apellidosUsuario.getText().toString();
                edad = Integer.parseInt(edadUsuario.getText().toString());
                peso = Float.parseFloat(pesoUsuario.getText().toString());
                altura = Float.parseFloat((alturaUsuario.getText().toString()));
                genero = spGenero.getSelectedItem().toString();

                ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(NuevoUsuarioActivity.this);
                long id = consultasUsuario.nuevoUsuario(nombre,apellidos,edad,genero,peso,altura);

                if(id>0){
                    Toast.makeText(NuevoUsuarioActivity.this,
                            "Datos guardados correctamente",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NuevoUsuarioActivity.this,PrincipalActivity.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(NuevoUsuarioActivity.this,
                            "Error al guardar los datos. Reviselos y vuelva a intentarlo",
                            Toast.LENGTH_LONG).show();
                }



            }
        });


    }
}