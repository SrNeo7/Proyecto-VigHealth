package com.joseantonio.personalproject.proyectovighealth;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joseantonio.personalproject.proyectovighealth.adaptadores.MedicamentoAdapter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasMedicamentoImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityListaMedicamentosBinding;

public class ListaMedicamentosActivity extends DrawerBaseActivity {

    RecyclerView listaMedicamentos;
    ActivityListaMedicamentosBinding listaMedicamentosBinding;

    String activityTitle;
    FloatingActionButton fabNuevoMedicamento;

    public static Activity listaMed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaMedicamentosBinding = ActivityListaMedicamentosBinding.inflate(getLayoutInflater());
        setContentView(listaMedicamentosBinding.getRoot());
        activityTitle = getString(R.string.at_lista_medicamentos);
        allocateActivityTitle(activityTitle);
        listaMed = this;


        listaMedicamentos = findViewById(R.id.rvListaMeds);
        fabNuevoMedicamento = findViewById(R.id.fabNuevoMedicamento);

        ConsultasMedicamentoImpl consultasMedicamento = new ConsultasMedicamentoImpl(ListaMedicamentosActivity.this);
        MedicamentoAdapter adapter = new MedicamentoAdapter(consultasMedicamento.mostrarMedicamentos());
        listaMedicamentos.setAdapter(adapter);
        listaMedicamentos.setLayoutManager(new LinearLayoutManager(this));

        fabNuevoMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoMedicamento();
            }
        });
    }

    public void nuevoMedicamento(){
        Intent intent = new Intent(ListaMedicamentosActivity.this,NuevoMedicamentoActivity.class);
        startActivity(intent);
        finish();
    }



}