package com.joseantonio.personalproject.proyectovighealth.interfaces;

import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;

import java.util.ArrayList;

public interface consultasMedicamento {

    public long nuevoMedicamento(int idUsuario, String nombreMedicamento, int dosis,
                                    String medidaDosis, int periodicidad, String comentarios);

    public ArrayList<Medicamento> mostrarMedicamentos();

    public boolean editarMedicamento(int id, int idUsuario, String nombreMedicamento, int dosis,
                                  String medidaDosis, int periodicidad, String comentarios);

    public boolean eliminarMedicamento(int id);


}
