package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasHidratacion;

public class ConsultasHidratacionImpl extends DbHelper implements ConsultasHidratacion {

    Context context;


    public ConsultasHidratacionImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public long nuevoRecordatorio(int idUsuario, int estado, int frecuencia) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("estado",estado );
            values.put("frecuencia",frecuencia);

            id = db.insert(TABLE_WATER, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public boolean editarRecordatorio(int idHidratacion, int estado, int frecuencia) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("UPDATE " + TABLE_WATER + " SET estado = '" + estado + "'" +
                    ", frecuencia = '" + frecuencia + "'" +
                    "WHERE idHidratacion = '" + idHidratacion +"' ");
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
    public boolean eliminarRecordatorio(int idHidratacion) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM " + TABLE_WATER + " WHERE idHidratacion = '" + idHidratacion +"' LIMIT 1");
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
