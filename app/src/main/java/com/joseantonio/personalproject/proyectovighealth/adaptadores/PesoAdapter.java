package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.EditarPesoActivity;
import com.joseantonio.personalproject.proyectovighealth.EditarTensionActivity;
import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.util.ArrayList;

public class PesoAdapter extends RecyclerView.Adapter<PesoAdapter.PesoViewHolder> {

    ArrayList<Peso>listaPeso;

    public PesoAdapter(ArrayList<Peso> listaPeso) {
        this.listaPeso = listaPeso;
    }

    @NonNull
    @Override
    public PesoAdapter.PesoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peso,parent,false);

        return new PesoAdapter.PesoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesoAdapter.PesoViewHolder holder, int position) {

        String peso = Double.toString(listaPeso.get(position).getPeso())+"Kg";
        String diferencia = "(" + Double.toString(listaPeso.get(position).getDiferenciaPeso()) + "Kg" + ")";
        String imc = Double.toString(listaPeso.get(position).getImc());
        String fechaFormateadaEU = Utilidades.fechaEuropea(listaPeso.get(position).getFechaPeso());

        holder.viewPeso.setText(peso);
        holder.viewFecha.setText(fechaFormateadaEU);
        holder.viewDiferencia.setText(diferencia);
        holder.viewImc.setText(imc);


    }

    @Override
    public int getItemCount() {
        return listaPeso.size();
    }


    public class PesoViewHolder extends RecyclerView.ViewHolder{

        TextView viewPeso,viewFecha,viewDiferencia,viewImc;

        public PesoViewHolder(@NonNull View itemView){
            super(itemView);

            viewPeso = itemView.findViewById(R.id.tv_Peso);
            viewFecha = itemView.findViewById(R.id.tv_fechaPeso);
            viewDiferencia = itemView.findViewById(R.id.tv_diferenciaPeso);
            viewImc = itemView.findViewById(R.id.tv_imc);

            //Esto hace que los items del recycler view sean clickeables
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EditarPesoActivity.class);
                    intent.putExtra("ID",listaPeso.get(getAdapterPosition()).getIdPeso());
                    context.startActivity(intent);
                }
            });


        }
    }
}
