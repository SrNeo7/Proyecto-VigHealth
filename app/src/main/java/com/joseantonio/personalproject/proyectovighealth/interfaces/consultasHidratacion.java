package com.joseantonio.personalproject.proyectovighealth.interfaces;

public interface consultasHidratacion {

    public long nuevoRecordatorio(int idUsuario, int estado, int frecuencia);

    public boolean editarRecordatorio(int idUsuario, int estado, int frecuencia);

    public boolean eliminarRecordatorio(int id);

}
