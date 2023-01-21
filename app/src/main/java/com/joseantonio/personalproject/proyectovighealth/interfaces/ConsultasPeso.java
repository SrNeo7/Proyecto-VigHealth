package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;

import java.util.ArrayList;

public interface ConsultasPeso {

    public long nuevoPeso(int idUsuario,double peso, String fechaPeso, double imc,
                          double diferenciaPeso);

    public ArrayList<Peso> mostrarRegistrosPeso();

    public ArrayList<Peso>mostrarPorFecha(String fechaInicialPeso, String fechaFinalPeso);


}
