package com.joseantonio.personalproject.proyectovighealth.objetos;

public class Tension {

    private int idTension;
    private int idUsuario;
    private String fechaTension;
    private double sistolica;
    private double diastolica;
    private String valoracion;

    public Tension(){}

    public Tension(int idTension, int idUsuario, String fechaTension, double sistolica, double diastolica, String valoracion) {
        this.idTension = idTension;
        this.idUsuario = idUsuario;
        this.fechaTension = fechaTension;
        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.valoracion = valoracion;
    }

    //Constructor para recuperar los datos  que se muestran en la pantalla Principal
    public Tension(double sistolica,double diastolica,String valoracion){
        this.sistolica = sistolica;
        this.diastolica = diastolica;
        this.valoracion = valoracion;
    }

    //Getters y Setters
    public int getIdTension() {
        return idTension;
    }

    public void setIdTension(int idTension) {
        this.idTension = idTension;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFechaTension() {
        return fechaTension;
    }

    public void setFechaTension(String fechaTension) {
        this.fechaTension = fechaTension;
    }

    public double getSistolica() {
        return sistolica;
    }

    public void setSistolica(double sistolica) {
        this.sistolica = sistolica;
    }

    public double getDiastolica() {
        return diastolica;
    }

    public void setDiastolica(double diastolica) {
        this.diastolica = diastolica;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
