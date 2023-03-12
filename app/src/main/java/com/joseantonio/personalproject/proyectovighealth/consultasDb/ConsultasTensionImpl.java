package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasTension;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

import java.util.ArrayList;

public class ConsultasTensionImpl extends DbHelper implements ConsultasTension {

    Context context;

    //Constructor de la clase
    public ConsultasTensionImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public long nuevoRegistroTension(int idUsuario,String fechaTension, double sistolica,
                                     double diastolica, String valoracion){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("sistolica", sistolica);
            values.put("diastolica", diastolica);
            values.put("fechaTension", fechaTension);
            values.put("valoracion", valoracion);

            id = db.insert(TABLE_TENSION, null, values);
            db.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;


    }

    @Override
    public ArrayList<Tension> mostrarRegistrosTension(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Tension>listaRegistrosTension = new ArrayList<>();
        Tension registroTension = null;
        Cursor cursorRegistrosTension = null;

        cursorRegistrosTension = db.rawQuery("SELECT idTension, sistolica, diastolica, fechaTension, valoracion FROM " +
                TABLE_TENSION + " ORDER BY fechaTension DESC", null);

        if(cursorRegistrosTension.moveToFirst()){
            do{
                registroTension = new Tension();
                registroTension.setIdTension(cursorRegistrosTension.getInt(0));
                registroTension.setSistolica(cursorRegistrosTension.getDouble(1));
                registroTension.setDiastolica(cursorRegistrosTension.getDouble(2));
                registroTension.setFechaTension(cursorRegistrosTension.getString(3));
                registroTension.setValoracion(cursorRegistrosTension.getString(4));
                listaRegistrosTension.add(registroTension);
            }while (cursorRegistrosTension.moveToNext());
        }
        cursorRegistrosTension.close();
        db.close();

        return listaRegistrosTension;
    }

    @Override
    public ArrayList<Tension>mostrarPorFechas(String fechaInicialTension, String fechaFinalTension){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Tension>listaRegistrosHist = new ArrayList<>();
        Tension registro = null;
        Cursor cursorHistorico = null;

        cursorHistorico = db.rawQuery("SELECT idTension, sistolica, diastolica, fechaTension, valoracion FROM " +
                        TABLE_TENSION + " WHERE fechaTension BETWEEN '" + fechaInicialTension + "' AND '" + fechaFinalTension +"'",
                null);

        if (cursorHistorico.moveToFirst()){
            do{
                registro = new Tension();
                registro.setIdTension(cursorHistorico.getInt(0));
                registro.setSistolica(cursorHistorico.getDouble(1));
                registro.setDiastolica(cursorHistorico.getDouble(2));
                registro.setFechaTension(cursorHistorico.getString(3));
                registro.setValoracion(cursorHistorico.getString(4));
                listaRegistrosHist.add(registro);
            }while (cursorHistorico.moveToNext());
        }

        cursorHistorico.close();
        db.close();


        return listaRegistrosHist;
    }
    @Override
    public boolean editarTension(int idTension, double sistolica, double diastolica,String valoracion) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_TENSION + " SET sistolica = " + sistolica + "," +
                    "diastolica = " + diastolica + "," + "valoracion = '" + valoracion + "'" +
                    "WHERE idTension = '" + idTension + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        }


        return correcto;
    }
    @Override
    public boolean eliminarTension(int idTension) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("DELETE FROM " + TABLE_TENSION + " WHERE idTension = " + idTension);
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        }

        return correcto;
    }

    public Tension ultimaTension() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Tension tension = null;
        Cursor cursorTension = null;

        cursorTension = db.rawQuery("SELECT sistolica, diastolica, valoracion FROM " +
                TABLE_TENSION + " ORDER BY fechaTension DESC LIMIT 1", null);

        if(cursorTension.moveToFirst()){
            do{
                tension = new Tension(cursorTension.getDouble(0), cursorTension.getDouble(1),cursorTension.getString(2));

            }while(cursorTension.moveToNext());
        }

        cursorTension.close();
        db.close();

        return tension;

    }

    public Tension verTension(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Tension tension = null;
        Cursor cursorTension = null;

        cursorTension = db.rawQuery("SELECT idTension, sistolica, diastolica, fechaTension, " +
                "valoracion FROM " +
                TABLE_TENSION + " WHERE idTension = " + id + " LIMIT 1",null);

        if(cursorTension.moveToFirst()){
            tension = new Tension();
            tension.setIdTension(cursorTension.getInt(0));
            tension.setSistolica(cursorTension.getDouble(1));
            tension.setDiastolica(cursorTension.getDouble(2));
            tension.setFechaTension(cursorTension.getString(3));
            tension.setValoracion(cursorTension.getString(4));
        }
        cursorTension.close();
        db.close();

        return tension;
    }

}
