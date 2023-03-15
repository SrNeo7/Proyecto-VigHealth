package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasHidratacion;
import com.joseantonio.personalproject.proyectovighealth.objetos.Hidratacion;

public class ConsultasHidratacionImpl extends DbHelper implements ConsultasHidratacion {

    Context context;


    public ConsultasHidratacionImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    /**
     * nuevoRecordatorio: crea un nuevo registro en la tabla Hidratacion
     * @param idUsuario
     * @param estado
     * @param frecuencia
     * @return si la operacion ha tenido exito devuelve el id del registro
     */
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
            db.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    /**
     * obtenerRecordatorio: recupera la informacion de un registro de la tabla Hidratacion
     * @return un objeto Hidratacion con la informacion recuperada
     */
    @Override
    public Hidratacion obtenerRecordatorio(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Hidratacion hidratacion = null;
        Cursor cursorHidratacion = null;

        cursorHidratacion = db.rawQuery("SELECT estado, frecuencia FROM " + TABLE_WATER,null);

        if(cursorHidratacion.moveToFirst()){
            do{
                hidratacion = new Hidratacion(cursorHidratacion.getInt(0),cursorHidratacion.getInt(1));

            }while(cursorHidratacion.moveToNext());
        }
        cursorHidratacion.close();
        db.close();
        return hidratacion;

    }

    /**
     * editarRecordatorio: modifica la informacion de un registro de la tabla Hidratacion
     * @param idHidratacion
     * @param estado
     * @param frecuencia
     * @return true si la operacion se lleva a cabo con exito, false si no
     */
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

    /**
     * eliminarRecordatorio: elimina el registro de la tabla Hidratacion cuyo id
     * se corresponda con el parametro idHidratacion
     * @param idHidratacion
     * @return true si la operacion se lleva a cabo con exito, false si no
     */
    @Override
    public boolean eliminarRecordatorio(int idHidratacion) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL("DELETE FROM " + TABLE_WATER + " WHERE idHidratacion = " + idHidratacion);
            correcto = true;
        }catch (Exception ex){
            ex.printStackTrace();
            correcto = false;
        }finally {
            db.close();
        }

        return correcto;
    }

    /**
     * obtenerIdRecordatorio: recupera el id de un registro de la tabla Hidratacion
     * @return un int con el id recuperado
     */
    @Override
    public int obtenerIdRecordatorio(){
        int idHidratacion = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorHidracion = null;

        cursorHidracion = db.rawQuery("SELECT idHidratacion FROM " + TABLE_WATER,null);
        if (cursorHidracion.moveToFirst()){
            do{
                idHidratacion = cursorHidracion.getInt(0);
            }while (cursorHidracion.moveToNext());
        }
        cursorHidracion.close();
        db.close();

        return idHidratacion;
    }
}
