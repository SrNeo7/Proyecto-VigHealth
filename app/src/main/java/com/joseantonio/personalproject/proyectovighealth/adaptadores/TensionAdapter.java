package com.joseantonio.personalproject.proyectovighealth.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.joseantonio.personalproject.proyectovighealth.EditarTensionActivity;
import com.joseantonio.personalproject.proyectovighealth.R;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

import java.util.ArrayList;

public class TensionAdapter extends RecyclerView.Adapter<TensionAdapter.TensionViewHolder> {

    ArrayList<Tension> listaTension;
    private TensionViewHolder tensionViewHolder;

    public TensionAdapter(ArrayList<Tension>listaTension){
        this.listaTension = listaTension;
    }

    @NonNull
    @Override
    public TensionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tension,parent,false);

        return new TensionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TensionViewHolder holder, int position) {

        String diastolica = Double.toString(listaTension.get(position).getDiastolica());
        String sistolica = Double.toString(listaTension.get(position).getSistolica());
        String medicion = sistolica + "/" + diastolica;
        String fechaFormateadaEU = fechaEuropea(listaTension.get(position).getFechaTension());

        holder.viewMedicion.setText(medicion);
        holder.viewValoracion.setText(listaTension.get(position).getValoracion());
        holder.viewFecha.setText(fechaFormateadaEU);


    }

    @Override
    public int getItemCount() {
        return listaTension.size();
    }


    public class TensionViewHolder extends RecyclerView.ViewHolder{

        TextView viewMedicion,viewFecha,viewValoracion;

        public TensionViewHolder(@NonNull View itemView){
            super(itemView);

            viewMedicion = itemView.findViewById(R.id.tv_medicionTension);
            viewFecha = itemView.findViewById(R.id.tv_fechaTension);
            viewValoracion = itemView.findViewById(R.id.tv_valoracionTension);

            //Esto hace que los items del recycler view sean clickeables
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, EditarTensionActivity.class);
                    intent.putExtra("ID",listaTension.get(getAdapterPosition()).getIdTension());
                    context.startActivity(intent);
                }
            });


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
