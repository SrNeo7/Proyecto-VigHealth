package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Alergia;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.util.ArrayList;

public class AlergiaAdapter extends RecyclerView.Adapter<AlergiaAdapter.AlergiaViewHolder> {

    ArrayList<Alergia>listaAlergia;

    public AlergiaAdapter(ArrayList<Alergia>listaAlergia){
        this.listaAlergia = listaAlergia;
    }


    @NonNull
    @Override
    public AlergiaAdapter.AlergiaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alergia,parent,false);

        return new AlergiaAdapter.AlergiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlergiaAdapter.AlergiaViewHolder holder, int position) {
        String nombreAlergeno = listaAlergia.get(position).getNombreAlergia();
        String fechaAlergia = Utilidades.fechaEuropea(listaAlergia.get(position).getFechaDatos());
        String concentracionPolen = listaAlergia.get(position).getConcentracionAtm();
        String valoracion = listaAlergia.get(position).getValoracion();

        holder.viewNombreAlergeno.setText(nombreAlergeno);
        holder.viewFechaAlergia.setText(fechaAlergia);
        holder.viewConcentracionPolen.setText(concentracionPolen);
        holder.viewValoracion.setText(valoracion);

    }

    @Override
    public int getItemCount() {
        return listaAlergia.size();
    }

    public class AlergiaViewHolder extends RecyclerView.ViewHolder{

        TextView viewNombreAlergeno, viewFechaAlergia,viewConcentracionPolen,viewValoracion;
        public AlergiaViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombreAlergeno = itemView.findViewById(R.id.tv_alergeno);
            viewFechaAlergia = itemView.findViewById(R.id.tv_fechaAlergia);
            viewConcentracionPolen = itemView.findViewById(R.id.tv_itemConcentracion);
            viewValoracion = itemView.findViewById(R.id.tv_valoracionAlergia);
        }


    }
}
