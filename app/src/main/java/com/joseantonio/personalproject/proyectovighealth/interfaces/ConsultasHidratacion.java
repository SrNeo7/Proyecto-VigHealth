package com.joseantonio.personalproject.proyectovighealth.interfaces;

public interface ConsultasHidratacion {

    public long nuevoRecordatorio(int idUsuario, int estado, int frecuencia);

    public boolean editarRecordatorio(int idHidratacion, int estado, int frecuencia);

    public boolean eliminarRecordatorio(int idHidratacion);

}
