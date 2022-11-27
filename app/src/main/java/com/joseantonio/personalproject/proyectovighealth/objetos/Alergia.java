package com.joseantonio.personalproject.proyectovighealth.objetos;

public class Alergia {

    private int idAlergia;
    private int idUsuario;
    private String nombreAlergia;
    private String fechaDatos;
    private String concentracionAtm;
    private String valoracion;

    public Alergia(){}

    public Alergia(int idAlergia, int idUsuario, String nombreAlergia, String fechaDatos, String concentracionAtm, String valoracion) {
        this.idAlergia = idAlergia;
        this.idUsuario = idUsuario;
        this.nombreAlergia = nombreAlergia;
        this.fechaDatos = fechaDatos;
        this.concentracionAtm = concentracionAtm;
        this.valoracion = valoracion;
    }

    public int getIdAlergia() {
        return idAlergia;
    }

    public void setIdAlergia(int idAlergia) {
        this.idAlergia = idAlergia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreAlergia() {
        return nombreAlergia;
    }

    public void setNombreAlergia(String nombreAlergia) {
        this.nombreAlergia = nombreAlergia;
    }

    public String getFechaDatos() {
        return fechaDatos;
    }

    public void setFechaDatos(String fechaDatos) {
        this.fechaDatos = fechaDatos;
    }

    public String getConcentracionAtm() {
        return concentracionAtm;
    }

    public void setConcentracionAtm(String concentracionAtm) {
        this.concentracionAtm = concentracionAtm;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
