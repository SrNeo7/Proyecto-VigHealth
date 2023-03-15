package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;

import java.util.ArrayList;

public interface ConsultasPeso {

    public long nuevoPeso(int idUsuario,double peso, String fechaPeso, double imc,
                          double diferenciaPeso);

    public ArrayList<Peso> mostrarRegistrosPeso();

    public ArrayList<Peso>mostrarPorFecha(String fechaInicialPeso, String fechaFinalPeso);

    public boolean editarPeso(int idPeso, double peso, double imc, double diferenciaPeso);

    public boolean eliminarPeso(int idPeso);

    public Peso ultimoPeso();

    public Peso verPeso(int id);


}
