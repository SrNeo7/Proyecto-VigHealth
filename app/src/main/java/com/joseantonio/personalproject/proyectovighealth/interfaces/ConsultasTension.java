package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

import java.util.ArrayList;

public interface ConsultasTension {

    public long nuevoRegistroTension(int idUsuario, String fechaTension, double sistolica,
                                     double diastolica, String valoracion);

    public ArrayList<Tension>mostrarRegistrosTension();

    public ArrayList<Tension>mostrarPorFechas(String fechaInicialTension, String fechaFinalTension);
}
