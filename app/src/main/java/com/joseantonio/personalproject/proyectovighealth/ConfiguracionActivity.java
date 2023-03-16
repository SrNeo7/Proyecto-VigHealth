package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import android.app.ActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityConfiguracionBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Usuario;

public class ConfiguracionActivity extends DrawerBaseActivity {

    ActivityConfiguracionBinding configuracionBinding;

    MaterialTextView tvDatosPersonales, tvBorrarDatos;

    String activityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configuracionBinding = ActivityConfiguracionBinding.inflate(getLayoutInflater());
        setContentView(configuracionBinding.getRoot());
        activityTitle = getString(R.string.at_configuracion);
        allocateActivityTitle(activityTitle);

        tvDatosPersonales = findViewById(R.id.tvClkDatos);
        tvBorrarDatos = findViewById(R.id.tvClkBorrarDatos);


        tvDatosPersonales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfiguracionActivity.this,EditarUsuarioActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tvBorrarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConfiguracionActivity.this);
                builder.setMessage("Se van a borrar todos sus datos en la aplicación. ¿Desea eliminar sus datos?");
                builder.setTitle("Borrar datos de la aplicacion");

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //WorkManager.getInstance(ConfiguracionActivity.this).cancelAllWork();
                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        am.clearApplicationUserData();
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
}