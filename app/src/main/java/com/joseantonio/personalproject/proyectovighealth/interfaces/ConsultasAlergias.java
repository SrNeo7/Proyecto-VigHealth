package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Alergia;

import java.util.ArrayList;

public interface ConsultasAlergias {

    public long nuevoRegistroAlergia(int idUsuario,String nombreAlergia);

    public long recogidaDatosAlergia(int idUsuario, String nombreAlergia, String fechaDatos,
                                     String concentracionAtm, String valoracion);

    public ArrayList<Alergia>mostrarRegistrosAlergia(String alergeno);
}
