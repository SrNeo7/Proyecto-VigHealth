package com.joseantonio.personalproject.proyectovighealth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasActividadImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasAlergiasImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasHidratacionImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasMedicamentoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasPesoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasTensionImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityPrincipalBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Actividad;
import com.joseantonio.personalproject.proyectovighealth.objetos.Alergia;
import com.joseantonio.personalproject.proyectovighealth.objetos.Hidratacion;
import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

public class PrincipalActivity extends DrawerBaseActivity {

    ActivityPrincipalBinding activityPrincipalBinding;

    MaterialCardView tension, peso, medicamento, hidratacion, actividad, alergia;

    Peso statusPeso = null;
    Tension statusTension = null;

    Medicamento statusMedicamento = null;

    Hidratacion statusHidratacion = null;

    Actividad statusActividad = null;

    Alergia statusAlergia = null;

    TextView pesoTv, difPesoTv,tensionTv,valTenTv,nombreMedTv,periodicidadMedTv,
            estadoHidTv,frecuenciaHidTv,tipoActividadTv,distanciaTv,nombreAlergiaTv,valAlergiaTv;


    final String SIN_DATOS = "Sin datos";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPrincipalBinding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(activityPrincipalBinding.getRoot());
        allocateActivityTitle("Principal");

        tension = findViewById(R.id.cvTension);
        peso = findViewById(R.id.cvPeso);
        medicamento = findViewById(R.id.cvMedicamento);
        hidratacion = findViewById(R.id.cvHidratacion);
        actividad = findViewById(R.id.cvActividad);
        alergia = findViewById(R.id.cvAlergia);
        pesoTv=findViewById(R.id.tvPplPeso);
        difPesoTv = findViewById(R.id.tvPplPesoDif);
        tensionTv = findViewById(R.id.tvPplTension);
        valTenTv = findViewById(R.id.tvPplTenVal);
        nombreMedTv = findViewById(R.id.tvPplMedNombre);
        periodicidadMedTv = findViewById(R.id.tvPplMedPeriodo);
        estadoHidTv = findViewById(R.id.tvPplHiEstado);
        frecuenciaHidTv = findViewById(R.id.tvPplHiFrec);
        tipoActividadTv = findViewById(R.id.tvPplActividadTipo);
        distanciaTv = findViewById(R.id.tvPplActividadDistancia);
        nombreAlergiaTv = findViewById(R.id.tvPplAlergeno);
        valAlergiaTv = findViewById(R.id.tvPplConVal);


        datosPanelTension();
        datosPanelPeso();
        datosPanelMedicamento();
        datosPanelHidratacion();
        datosPanelActividad();
        datosPanelAlergia();



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

        hidratacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,HidratacionActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,ListaActividadesActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        alergia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrincipalActivity.this,AlergiaActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();

        datosPanelTension();
        datosPanelPeso();
        datosPanelMedicamento();
        datosPanelHidratacion();
        datosPanelActividad();
        datosPanelAlergia();

    }

    //Funciones para obtener los datos que se muestran en los paneles del dashboard

    private void datosPanelPeso(){

        ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(PrincipalActivity.this);
        statusPeso = new Peso();
        statusPeso = consultasPeso.ultimoPeso();

        if(statusPeso!=null){

        pesoTv.setText(String.valueOf(statusPeso.getPeso()) + " Kg.");
        difPesoTv.setText(String.valueOf(statusPeso.getDiferenciaPeso()) + " Kg.");
        }else{
            pesoTv.setText(SIN_DATOS);
            difPesoTv.setText("");
        }

    }

    private void datosPanelTension(){
        ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(PrincipalActivity.this);
        statusTension = new Tension();
        statusTension = consultasTension.ultimaTension();

        if(statusTension!=null){
        double sistolicaTemp = statusTension.getSistolica();
        double diastolicaTemp = statusTension.getDiastolica();
        String ultimaMedicion = String.valueOf(sistolicaTemp+"/"+diastolicaTemp);

        tensionTv.setText(ultimaMedicion);
        valTenTv.setText(statusTension.getValoracion());
        }else{
            tensionTv.setText(SIN_DATOS);
            valTenTv.setText("");
        }
    }

    private void datosPanelMedicamento(){
        ConsultasMedicamentoImpl consultasMedicamento=new ConsultasMedicamentoImpl(PrincipalActivity.this);
        statusMedicamento = new Medicamento();
        statusMedicamento = consultasMedicamento.ultimoMedicamento();


        if ( statusMedicamento != null) {
            String unidadHoras = pluralSingular(statusMedicamento.getPeriodicidad());
            int horas = statusMedicamento.getPeriodicidad();
            String periodicidad = String.valueOf(horas + unidadHoras);

            nombreMedTv.setText(statusMedicamento.getNombreMedicamento());
            periodicidadMedTv.setText(periodicidad);
        }else {
            nombreMedTv.setText(SIN_DATOS);
            periodicidadMedTv.setText("");
        }
    }

    private void datosPanelHidratacion(){
        ConsultasHidratacionImpl consultasHidratacion = new ConsultasHidratacionImpl(PrincipalActivity.this);
        statusHidratacion = new Hidratacion();
        statusHidratacion = consultasHidratacion.obtenerRecordatorio();
        String estadoStr = "Inactivo";

        if(statusHidratacion != null ){
            String unidadHoras = pluralSingular(statusHidratacion.getFrecuencia());
            int estado = statusHidratacion.getEstado();
            int frecuencia = statusHidratacion.getFrecuencia();
            String frecucuenciaStr = frecuencia + unidadHoras;

            if(estado == 1){

                estadoStr = "Activado";
            }

            estadoHidTv.setText(estadoStr);
            frecuenciaHidTv.setText(frecucuenciaStr);
        }else{
            estadoHidTv.setText(estadoStr);
            frecuenciaHidTv.setText("-");
        }

    }

    private void datosPanelActividad(){
        ConsultasActividadImpl consultasActividad = new ConsultasActividadImpl(PrincipalActivity.this);
        statusActividad = new Actividad();
        statusActividad = consultasActividad.ultimaActividad();

        if (statusActividad!= null){
            String tipoActividad = statusActividad.getTipo();
            double distancia = statusActividad.getDistancia();

            String distanciaStr = distancia + " KM";

            tipoActividadTv.setText(tipoActividad);
            distanciaTv.setText(distanciaStr);
        }else{
            tipoActividadTv.setText(SIN_DATOS);
            distanciaTv.setText("");
        }
    }

    private void datosPanelAlergia(){
        ConsultasAlergiasImpl consultasAlergias = new ConsultasAlergiasImpl(PrincipalActivity.this);
        statusAlergia = new Alergia();
        statusAlergia = consultasAlergias.ultimaAlergia();

        if(statusAlergia!= null){
            nombreAlergiaTv.setText(statusAlergia.getNombreAlergia());
            valAlergiaTv.setText(statusAlergia.getValoracion());
        }else{
            nombreAlergiaTv.setText(SIN_DATOS);
            valAlergiaTv.setText("");
        }
    }

    private String pluralSingular(int horas){
        String hora = "";

        if (horas>1){
            hora = " horas";
        }else if (horas == 1){
            hora = " hora";
        }

        return hora;
    }
}