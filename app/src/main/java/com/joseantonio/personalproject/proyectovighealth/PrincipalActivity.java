package com.joseantonio.personalproject.proyectovighealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasMedicamentoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasPesoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasTensionImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityPrincipalBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

public class PrincipalActivity extends DrawerBaseActivity {

    ActivityPrincipalBinding activityPrincipalBinding;

    MaterialCardView tension, peso, medicamento;

    Peso statusPeso = null;
    Tension statusTension = null;

    Medicamento statusMedicamento = null;

    TextView pesoTv, difPesoTv,tensionTv,valTenTv,nombreMedTv,periodicidadMedTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPrincipalBinding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(activityPrincipalBinding.getRoot());
        allocateActivityTitle("Principal");

        tension = findViewById(R.id.cvTension);
        peso = findViewById(R.id.cvPeso);
        medicamento = findViewById(R.id.cvMedicamento);
        pesoTv=findViewById(R.id.tvPplPeso);
        difPesoTv = findViewById(R.id.tvPplPesoDif);
        tensionTv = findViewById(R.id.tvPplTension);
        valTenTv = findViewById(R.id.tvPplTenVal);
        nombreMedTv = findViewById(R.id.tvPplMedNombre);
        periodicidadMedTv = findViewById(R.id.tvPplMedPeriodo);

       /* ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(PrincipalActivity.this);
        ultimoPeso = new Peso();
        ultimoPeso = consultasPeso.ultimoPeso();

        pesoTv.setText(String.valueOf(ultimoPeso.getPeso()) + " Kg.");
        difPesoTv.setText(String.valueOf(ultimoPeso.getDiferenciaPeso()) + " Kg.");*/

        datosPanelTension();
        datosPanelPeso();
        datosPanelMedicamento();



        tension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,NuevaTensionActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        peso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,PesoSeguimientoActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        medicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,ListaMedicamentosActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });



    }

    private void datosPanelPeso(){

        ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(PrincipalActivity.this);
        statusPeso = new Peso();
        statusPeso = consultasPeso.ultimoPeso();

        pesoTv.setText(String.valueOf(statusPeso.getPeso()) + " Kg.");
        difPesoTv.setText(String.valueOf(statusPeso.getDiferenciaPeso()) + " Kg.");

    }

    private void datosPanelTension(){
        ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(PrincipalActivity.this);
        statusTension = new Tension();
        statusTension = consultasTension.ultimaTension();

        double sistolicaTemp = statusTension.getSistolica();
        double diastolicaTemp = statusTension.getDiastolica();
        String ultimaMedicion = String.valueOf(sistolicaTemp+"/"+diastolicaTemp);

        tensionTv.setText(ultimaMedicion);
        valTenTv.setText(statusTension.getValoracion());
    }

    private void datosPanelMedicamento(){
        ConsultasMedicamentoImpl consultasMedicamento=new ConsultasMedicamentoImpl(PrincipalActivity.this);
        statusMedicamento = new Medicamento();
        statusMedicamento = consultasMedicamento.ultimoMedicamento();

        int horas = statusMedicamento.getPeriodicidad();
        String periodicidad = String.valueOf(horas + " horas");
        
        nombreMedTv.setText(statusMedicamento.getNombreMedicamento());
        periodicidadMedTv.setText(periodicidad);



    }
}