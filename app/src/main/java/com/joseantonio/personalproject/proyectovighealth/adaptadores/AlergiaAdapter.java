package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Alergia;

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
        String fechaAlergia = fechaEuropea(listaAlergia.get(position).getFechaDatos());
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
