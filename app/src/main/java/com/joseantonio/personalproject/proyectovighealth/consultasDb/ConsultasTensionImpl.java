package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasTension;
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
    public long nuevoRegistroTension(String fechaTension, double sistolica,
                                     double diastolica, String valoracion){

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("sistolica", sistolica);
            values.put("diastolica", diastolica);
            values.put("fechaTension", fechaTension);
            values.put("valoracion", valoracion);

            id = db.insert(TABLE_TENSION, null, values);
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

        cursorRegistrosTension = db.rawQuery("SELECT sistolica, diastolica, fechaTension, valoracion FROM " +
                TABLE_TENSION + " ORDER BY fechaTension DESC", null);

        if(cursorRegistrosTension.moveToFirst()){
            do{
                registroTension = new Tension();
                registroTension.setSistolica(cursorRegistrosTension.getDouble(0));
                registroTension.setDiastolica(cursorRegistrosTension.getDouble(1));
                registroTension.setFechaTension(cursorRegistrosTension.getString(2));
                registroTension.setValoracion(cursorRegistrosTension.getString(3));
                listaRegistrosTension.add(registroTension);
            }while (cursorRegistrosTension.moveToNext());
        }
        cursorRegistrosTension.close();

        return listaRegistrosTension;
    }

    @Override
    public ArrayList<Tension>mostrarPorFechas(String fechaInicialTension, String fechaFinalTension){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Tension>listaRegistrosHist = new ArrayList<>();
        Tension registro = null;
        Cursor cursorHistorico = null;

        cursorHistorico = db.rawQuery("SELECT sistolica, diastolica, fechaTension, valoracion FROM " +
                        TABLE_TENSION + " WHERE fechaTension BETWEEN '" + fechaInicialTension + "' AND '" + fechaFinalTension +"'",
                null);

        if (cursorHistorico.moveToFirst()){
            do{
                registro = new Tension();
                registro.setSistolica(cursorHistorico.getDouble(0));
                registro.setDiastolica(cursorHistorico.getDouble(1));
                registro.setFechaTension(cursorHistorico.getString(2));
                registro.setValoracion(cursorHistorico.getString(3));
                listaRegistrosHist.add(registro);
            }while (cursorHistorico.moveToNext());
        }

        cursorHistorico.close();


        return listaRegistrosHist;
    }
}
