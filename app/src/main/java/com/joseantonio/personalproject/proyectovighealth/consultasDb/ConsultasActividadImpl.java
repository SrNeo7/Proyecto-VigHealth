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

    public ConsultasActividadImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }


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

    @Override
    public ArrayList<Actividad> mostrarActividadPasiva() {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Actividad>listaActividadPasiva = new ArrayList<>();
        Actividad actividadPasiva= null;
        Cursor cursorActividadPasiva = null;

        cursorActividadPasiva = db.rawQuery("SELECT tipo, distancia, ritmo, " +
                " fechaActividad FROM " + TABLE_ACTIVITY + " WHERE tipo = 'Pasiva'" +
                " ORDER BY fechaActividad DESC", null);

        if(cursorActividadPasiva.moveToFirst()){
            do{
                actividadPasiva = new Actividad();
                actividadPasiva.setTipo(cursorActividadPasiva.getString(0));
                actividadPasiva.setDistancia(cursorActividadPasiva.getDouble(1));
                actividadPasiva.setRitmo(cursorActividadPasiva.getString(2));
                actividadPasiva.setFechaActividad(cursorActividadPasiva.getString(3));
                listaActividadPasiva.add(actividadPasiva);

            }while(cursorActividadPasiva.moveToNext());
        }

        cursorActividadPasiva.close();
        db.close();

        return listaActividadPasiva;
    }

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
