package com.joseantonio.personalproject.proyectovighealth.objetos;

public class Hidratacion {

    private int idHidratacion;
    private int idUsuario;
    private int estado;
    private int frecuencia;

    public Hidratacion(){}

    //Constructor para recuperar los datos  que se muestran en la pantalla Principal
    public Hidratacion(int estado,int frecuencia){
        this.estado = estado;
        this.frecuencia = frecuencia;
    }

    public Hidratacion(int idHidratacion, int idUsuario, int estado, int frecuencia) {
        this.idHidratacion = idHidratacion;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.frecuencia = frecuencia;
    }
    //Getters y Setters
    public int getIdHidratacion() {
        return idHidratacion;
    }

    public void setIdHidratacion(int idHidratacion) {
        this.idHidratacion = idHidratacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
