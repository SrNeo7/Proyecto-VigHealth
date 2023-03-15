package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Hidratacion;

public interface ConsultasHidratacion {

    public long nuevoRecordatorio(int idUsuario, int estado, int frecuencia);

    public Hidratacion obtenerRecordatorio();

    public boolean editarRecordatorio(int idHidratacion, int estado, int frecuencia);

    public boolean eliminarRecordatorio(int idHidratacion);

    public int obtenerIdRecordatorio();

}
