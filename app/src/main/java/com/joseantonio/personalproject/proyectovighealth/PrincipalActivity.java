package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityPrincipalBinding;

public class PrincipalActivity extends DrawerBaseActivity {

    ActivityPrincipalBinding activityPrincipalBinding;

    MaterialCardView tension;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPrincipalBinding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(activityPrincipalBinding.getRoot());
        allocateActivityTitle("Principal");

        tension = findViewById(R.id.cvTension);

        tension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,NuevaTensionActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}