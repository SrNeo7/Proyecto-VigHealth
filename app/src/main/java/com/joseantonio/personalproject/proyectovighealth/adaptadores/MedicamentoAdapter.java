package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.EditarMedicamentoActivity;
import com.joseantonio.personalproject.proyectovighealth.ListaMedicamentosActivity;
import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;

import java.util.ArrayList;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {

    ArrayList<Medicamento>listaMedicamentos;

    public MedicamentoAdapter (ArrayList<Medicamento>listaMedicamentos){
        this.listaMedicamentos = listaMedicamentos;
    }

    @NonNull
    @Override
    public MedicamentoAdapter.MedicamentoViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento,parent,false);

        return new MedicamentoAdapter.MedicamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoAdapter.MedicamentoViewHolder holder, int position){
        String nombreMedicamento = listaMedicamentos.get(position).getNombreMedicamento();
        String dosisMed = String.valueOf(listaMedicamentos.get(position).getDosis());
        String medidaDosis = listaMedicamentos.get(position).getMedidaDosis();
        String periodicidad = String.valueOf(listaMedicamentos.get(position).getPeriodicidad()) +" h";

        holder.viewNombreMed.setText(nombreMedicamento);
        holder.viewDosisMed.setText(dosisMed);
        holder.viewMedidaDosisMed.setText(medidaDosis);
        holder.viewPeriodicidadMed.setText(periodicidad);


    }

    @Override
    public int getItemCount(){
        return listaMedicamentos.size();
    }

    public class MedicamentoViewHolder extends RecyclerView.ViewHolder{

        TextView viewNombreMed, viewDosisMed,viewMedidaDosisMed,viewPeriodicidadMed;

        public MedicamentoViewHolder(@NonNull View itemView){
            super(itemView);

            viewNombreMed = itemView.findViewById(R.id.tv_nombreMedicamento);
            viewDosisMed = itemView.findViewById(R.id.tv_dosisMedicamento);
            viewMedidaDosisMed = itemView.findViewById(R.id.tv_medidaMedicamento);
            viewPeriodicidadMed = itemView.findViewById(R.id.tv_periodicidadMedicamento);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EditarMedicamentoActivity.class);
                    intent.putExtra("ID",listaMedicamentos.get(getAdapterPosition()).getIdMedicamento());
                    context.startActivity(intent);
                }
            });

        }

    }


}
