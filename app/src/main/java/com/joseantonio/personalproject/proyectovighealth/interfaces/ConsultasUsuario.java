package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Usuario;

public interface ConsultasUsuario {


    public long nuevoUsuario(String nombre, String apellidos, int edad, String genero,
                             double peso, double altura);

    public Usuario mostarDatosUsuario();

    public boolean modificarDatosUsuario(int idUsuario, String nombre, String apellidos, int edad,
                                         String genero, double peso, double altura);

}
