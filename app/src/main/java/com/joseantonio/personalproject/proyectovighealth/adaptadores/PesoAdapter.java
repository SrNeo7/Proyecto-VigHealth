package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;

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
        String fechaFormateadaEU = fechaEuropea(listaPeso.get(position).getFechaPeso());

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


        }
    }

    /**
     * fechaEuropea: Funcion para convertir la fecha recuperada en formato SQLite a formato europeo
     * @param fecha
     * @return fechaFormateada: String que contiene la fecha convertida a formato europeo.
     */
    private String fechaEuropea (String fecha){

        String fechaFormateada;
        String dia, mes, anno ,hora;

        anno = fecha.substring(0,4);
        mes = fecha.substring(5,7);
        dia = fecha.substring(8,10);
        hora= fecha.substring(11,16);

        fechaFormateada = dia + "/" + mes + "/" + anno + " " + hora;

        return fechaFormateada;
    }

}
