package com.joseantonio.personalproject.proyectovighealth.objetos;

public class Actividad {

    private int idActividad;
    private int idUsuario;
    private String tipo;
    private String duracion;
    private double distancia;
    private String ritmo;
    private String fechaActividad;

    public Actividad(){}

    public Actividad(int idActividad, int idUsuario, String tipo, String duracion, double distancia, String ritmo, String fechaActividad) {
        this.idActividad = idActividad;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.duracion = duracion;
        this.distancia = distancia;
        this.ritmo = ritmo;
        this.fechaActividad = fechaActividad;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getRitmo() {
        return ritmo;
    }

    public void setRitmo(String ritmo) {
        this.ritmo = ritmo;
    }

    public String getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(String fechaActividad) {
        this.fechaActividad = fechaActividad;
    }
}
