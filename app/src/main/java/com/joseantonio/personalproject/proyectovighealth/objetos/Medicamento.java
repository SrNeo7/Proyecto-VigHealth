package com.joseantonio.personalproject.proyectovighealth.objetos;

public class Medicamento {

    private int idMedicamento;
    private int idUsuario;
    private String nombreMedicamento;
    private int dosis;
    private String medidaDosis;
    private int periodicidad;
    private String comentarios;

    public Medicamento(){}

    public Medicamento(int idMedicamento, int idUsuario, String nombreMedicamento, int dosis, String medidaDosis, int periodicidad, String comentarios) {
        this.idMedicamento = idMedicamento;
        this.idUsuario = idUsuario;
        this.nombreMedicamento = nombreMedicamento;
        this.dosis = dosis;
        this.medidaDosis = medidaDosis;
        this.periodicidad = periodicidad;
        this.comentarios = comentarios;
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreMedicamento() {
        return nombreMedicamento;
    }

    public void setNombreMedicamento(String nombreMedicamento) {
        this.nombreMedicamento = nombreMedicamento;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getMedidaDosis() {
        return medidaDosis;
    }

    public void setMedidaDosis(String medidaDosis) {
        this.medidaDosis = medidaDosis;
    }

    public int getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(int periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}
