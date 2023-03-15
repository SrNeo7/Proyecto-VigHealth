package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.DetallesActividadActivity;
import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Actividad;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.util.ArrayList;

public class ActividadAdapter extends RecyclerView.Adapter<ActividadAdapter.ActividadViewHolder> {

    ArrayList<Actividad>listaActividades;

    public ActividadAdapter(ArrayList<Actividad>listaActividades){
        this.listaActividades = listaActividades;

    }

    @NonNull
    @Override
    public ActividadAdapter.ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_actividad,parent,false);

        return new ActividadAdapter.ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadAdapter.ActividadViewHolder holder, int position) {
        String tipoActividad = listaActividades.get(position).getTipo();
        String distanciaActividad =String.valueOf(listaActividades.get(position).getDistancia()) + " KM";
        String fechaActividad = Utilidades.fechaEuropea(listaActividades.get(position).getFechaActividad());

        holder.viewTipoActividad.setText(tipoActividad);
        holder.viewDistanciaActividad.setText(distanciaActividad);
        holder.viewFechaActividad.setText(fechaActividad);

    }

    @Override
    public int getItemCount() {
        return listaActividades.size();
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder{

        TextView viewTipoActividad,viewDistanciaActividad,viewFechaActividad;

        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);

            viewTipoActividad = itemView.findViewById(R.id.tv_tipoActividad);
            viewDistanciaActividad = itemView.findViewById(R.id.tv_distanciaActividad);
            viewFechaActividad = itemView.findViewById(R.id.tv_fechaActividad);

           //Esto hace que los items del recycler view sean clickeables
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, DetallesActividadActivity.class);
                    intent.putExtra("ID",listaActividades.get(getAdapterPosition()).getIdActividad());
                    context.startActivity(intent);
                }
            });
        }
    }
}
