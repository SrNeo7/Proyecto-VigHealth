package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Actividad;

import java.util.ArrayList;

public interface ConsultasActividad {

    public long nuevaActividad(int idUsuario, String tipo, String duracion, double distancia,
                               String ritmo, String fechaActividad);

    public ArrayList<Actividad>mostrarActividadTotal();

    public ArrayList<Actividad>mostrarActividadPasiva();


}
