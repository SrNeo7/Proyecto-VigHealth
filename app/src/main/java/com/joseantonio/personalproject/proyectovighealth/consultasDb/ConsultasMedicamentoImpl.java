package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasMedicamento;
import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;

import java.util.ArrayList;

public class ConsultasMedicamentoImpl extends DbHelper implements ConsultasMedicamento {

    Context context;

    //Constructor de la clase
    public ConsultasMedicamentoImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public long nuevoMedicamento(int idUsuario, String nombreMedicamento, int dosis,
                                 String medidaDosis, int periodicidad, String comentarios) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("nombreMedicamento",nombreMedicamento );
            values.put("dosis", dosis);
            values.put("medidaDosis", medidaDosis);
            values.put("periodicidad", periodicidad);
            values.put("comentarios", comentarios);

            id = db.insert(TABLE_MEDICINE, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public ArrayList<Medicamento> mostrarMedicamentos() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Medicamento>listaMedicamentos = new ArrayList<>();
        Medicamento medicamento = null;
        Cursor cursorMedicamentos = null;

        cursorMedicamentos = db.rawQuery("SELECT nombreMedicamento, dosis, medidaDosis, " +
                "periodicidad, comentarios FROM " +
                TABLE_MEDICINE + " ORDER BY fecha DESC", null);

        if(cursorMedicamentos.moveToFirst()){
            do{
                medicamento = new Medicamento();
                medicamento.setNombreMedicamento(cursorMedicamentos.getString(0));
                medicamento.setDosis(cursorMedicamentos.getInt(1));
                medicamento.setMedidaDosis(cursorMedicamentos.getString(2));
                medicamento.setPeriodicidad(cursorMedicamentos.getInt(3));
                medicamento.setComentarios(cursorMedicamentos.getString(4));
                listaMedicamentos.add(medicamento);
            }while(cursorMedicamentos.moveToNext());
        }

        cursorMedicamentos.close();
        return listaMedicamentos;
    }

    @Override
    public boolean editarMedicamento(int id, String nombreMedicamento, int dosis,
                                     String medidaDosis, int periodicidad, String comentarios) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLE_MEDICINE + " SET nombreMedicamento = '" + nombreMedicamento + "'," +
                    "dosis = '" + dosis + "',medidaDosis = '" + medidaDosis + "'," +
                    "periodicidad = '" + periodicidad + "',comentarios = '" + comentarios + "'," +
                    "WHERE id= '" + id +"' ");
            correcto = true;
        }catch (Exception ex){
            ex.printStackTrace();
            correcto = false;
        }finally {
            db.close();
        }


        return correcto;
    }

    @Override
    public boolean eliminarMedicamento(int id) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETRE FROM " + TABLE_MEDICINE + " WHERE id= '" + id +"' LIMIT 1");
            correcto = true;
        }catch (Exception ex){
            ex.printStackTrace();
            correcto = false;
        }finally {
            db.close();
        }

        return correcto;
    }
}
