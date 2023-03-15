package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasActividad;
import com.joseantonio.personalproject.proyectovighealth.objetos.Actividad;

import java.util.ArrayList;

public class ConsultasActividadImpl extends DbHelper implements ConsultasActividad {

    Context context;

    //Constructor de la clase
    public ConsultasActividadImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * nueva actividad: Añade un nuevo registro a la tabla Actividad de la base de datos
     * @param idUsuario
     * @param tipo
     * @param duracion
     * @param distancia
     * @param ritmo
     * @param fechaActividad
     * @return si la operacion ha tenido exito devuelve el id del registro
     */
    @Override
    public long nuevaActividad(int idUsuario, String tipo, String duracion, double distancia,
                               String ritmo, String fechaActividad) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("tipo",tipo );
            values.put("duracion", duracion);
            values.put("distancia", distancia);
            values.put("ritmo", ritmo);
            values.put("fechaActividad",fechaActividad);

            id = db.insert(TABLE_ACTIVITY, null, values);
            db.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    /**
     * mostrarActividad:Recupera todos los registros de la tabla Actividad
     * @return Devuelve un ArrayList con el que poblar un recycler view
     */
    @Override
    public ArrayList<Actividad> mostrarActividadTotal() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Actividad>listaActividad = new ArrayList<>();
        Actividad actividad= null;
        Cursor cursorActividad = null;

        cursorActividad = db.rawQuery("SELECT idActividad,tipo, duracion, distancia, ritmo, " +
                " fechaActividad FROM " + TABLE_ACTIVITY + " ORDER BY fechaActividad DESC", null);

        if(cursorActividad.moveToFirst()){
            do{
                actividad = new Actividad();
                actividad.setIdActividad(cursorActividad.getInt(0));
                actividad.setTipo(cursorActividad.getString(1));
                actividad.setDuracion(cursorActividad.getString(2));
                actividad.setDistancia(cursorActividad.getDouble(3));
                actividad.setRitmo(cursorActividad.getString(4));
                actividad.setFechaActividad(cursorActividad.getString(5));
                listaActividad.add(actividad);

            }while(cursorActividad.moveToNext());
        }

        cursorActividad.close();
        db.close();

        return listaActividad;
    }


    /**
     * mostrarActividadPorId: Recupera la fila de la tabla Actividad cuyo id coincida con el parametro id
     * @param id
     * @return un objeto Actividad con la informacion recuperada
     */
    public Actividad mostrarActividadPorId(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Actividad actividad = null;
        Cursor cursorActividad = null;

        cursorActividad = db.rawQuery("SELECT idActividad,tipo, duracion, distancia, ritmo, " +
                " fechaActividad FROM " + TABLE_ACTIVITY + " WHERE idActividad = " + id + " LIMIT 1", null);

        if(cursorActividad.moveToFirst()){
            actividad = new Actividad();
            actividad.setIdActividad(cursorActividad.getInt(0));
            actividad.setTipo(cursorActividad.getString(1));
            actividad.setDuracion(cursorActividad.getString(2));
            actividad.setDistancia(cursorActividad.getDouble(3));
            actividad.setRitmo(cursorActividad.getString(4));
            actividad.setFechaActividad(cursorActividad.getString(5));
        }
        cursorActividad.close();
        db.close();

        return actividad;
    }

    /**
     * ultimaActividad: Recupera la informacion del ultimo registro introducido en la tabla Actividad
     * @return un objeto Actividad con la informacion recuperada
     */
    @Override
    public Actividad ultimaActividad(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Actividad actividad = null;
        Cursor cursorActividad = null;

        cursorActividad = db.rawQuery("SELECT tipo, distancia FROM " +
                TABLE_ACTIVITY + " ORDER BY idActividad DESC LIMIT 1", null);

        if(cursorActividad.moveToFirst()){
            do{
                actividad = new Actividad(cursorActividad.getString(0),cursorActividad.getDouble(1));
            }while(cursorActividad.moveToNext());
        }

        cursorActividad.close();
        db.close();

        return actividad;
    }
}
