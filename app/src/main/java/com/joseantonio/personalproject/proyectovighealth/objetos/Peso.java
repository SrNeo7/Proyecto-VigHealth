package com.joseantonio.personalproject.proyectovighealth.objetos;

public class Peso {

    private int idPeso;
    private int idUsuario;
    private double peso;
    private String fechaPeso;
    private double imc;
    private double diferenciaPeso;

    public Peso(){}

    public Peso(int idPeso, int idUsuario, double peso, String fechaPeso, double imc, double diferenciaPeso) {
        this.idPeso = idPeso;
        this.idUsuario = idUsuario;
        this.peso = peso;
        this.fechaPeso = fechaPeso;
        this.imc = imc;
        this.diferenciaPeso = diferenciaPeso;
    }

    //Constructor para recuperar los datos  que se muestran en la pantalla Principal
    public Peso (double peso, double diferenciaPeso){
        this.peso = peso;
        this.diferenciaPeso = diferenciaPeso;
    }

    //Getters y Setters
    public int getIdPeso() {
        return idPeso;
    }

    public void setIdPeso(int idPeso) {
        this.idPeso = idPeso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getFechaPeso() {
        return fechaPeso;
    }

    public void setFechaPeso(String fechaPeso) {
        this.fechaPeso = fechaPeso;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getDiferenciaPeso() {
        return diferenciaPeso;
    }

    public void setDiferenciaPeso(double diferenciaPeso) {
        this.diferenciaPeso = diferenciaPeso;
    }
}
