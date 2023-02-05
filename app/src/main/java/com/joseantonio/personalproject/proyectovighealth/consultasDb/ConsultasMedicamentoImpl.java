package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasMedicamento;
import com.joseantonio.personalproject.proyectovighealth.objetos.Medicamento;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;

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
                                 String medidaDosis, int periodicidad, String comentario) {
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
            values.put("comentario", comentario);

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

        cursorMedicamentos = db.rawQuery("SELECT idMedicamento,nombreMedicamento, dosis, medidaDosis, " +
                "periodicidad, comentario FROM " +
                TABLE_MEDICINE + " ORDER BY idMedicamento ASC", null);

        if(cursorMedicamentos.moveToFirst()){
            do{
                medicamento = new Medicamento();
                medicamento.setIdMedicamento(cursorMedicamentos.getInt(0));
                medicamento.setNombreMedicamento(cursorMedicamentos.getString(1));
                medicamento.setDosis(cursorMedicamentos.getInt(2));
                medicamento.setMedidaDosis(cursorMedicamentos.getString(3));
                medicamento.setPeriodicidad(cursorMedicamentos.getInt(4));
                medicamento.setComentarios(cursorMedicamentos.getString(5));
                listaMedicamentos.add(medicamento);
            }while(cursorMedicamentos.moveToNext());
        }

        cursorMedicamentos.close();
        return listaMedicamentos;
    }

    @Override
    public boolean editarMedicamento(int idMedicamento, String nombreMedicamento, int dosis,
                                     String medidaDosis, int periodicidad, String comentario) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLE_MEDICINE + " SET nombreMedicamento = '" + nombreMedicamento + "'," +
                    "dosis = '" + dosis + "',medidaDosis = '" + medidaDosis + "'," +
                    "periodicidad = '" + periodicidad + "',comentario = '" + comentario + "'" +
                    "WHERE idMedicamento = '" + idMedicamento +"' ");
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
    public boolean eliminarMedicamento(int idMedicamento) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM " + TABLE_MEDICINE + " WHERE idMedicamento = " + idMedicamento);
            correcto = true;
        }catch (Exception ex){
            ex.printStackTrace();
            correcto = false;
        }finally {
            db.close();
        }

        return correcto;
    }

    public Medicamento verMedicamento(int id) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Medicamento medicamento = null;
        Cursor cursorMedicamentos = null;

        cursorMedicamentos = db.rawQuery("SELECT idMedicamento,nombreMedicamento, dosis, medidaDosis, " +
                "periodicidad, comentario FROM " +
                TABLE_MEDICINE + " WHERE idMedicamento = " + id + " LIMIT 1", null);

        if(cursorMedicamentos.moveToFirst()){
                medicamento = new Medicamento();
                medicamento.setIdMedicamento(cursorMedicamentos.getInt(0));
                medicamento.setNombreMedicamento(cursorMedicamentos.getString(1));
                medicamento.setDosis(cursorMedicamentos.getInt(2));
                medicamento.setMedidaDosis(cursorMedicamentos.getString(3));
                medicamento.setPeriodicidad(cursorMedicamentos.getInt(4));
                medicamento.setComentarios(cursorMedicamentos.getString(5));
        }

        cursorMedicamentos.close();

        return medicamento;
    }

    public Medicamento ultimoMedicamento() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Medicamento medicamento = null;
        Cursor cursorMedicamento = null;

        cursorMedicamento = db.rawQuery("SELECT nombreMedicamento, periodicidad FROM " +
                TABLE_MEDICINE + " ORDER BY idMedicamento DESC LIMIT 1", null);

        if(cursorMedicamento.moveToFirst()){
            do{
                medicamento= new Medicamento(cursorMedicamento.getString(0), cursorMedicamento.getInt(1));

            }while(cursorMedicamento.moveToNext());
        }

        cursorMedicamento.close();
        return medicamento;

    }
}
