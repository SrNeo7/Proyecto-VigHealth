package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasAlergias;
import com.joseantonio.personalproject.proyectovighealth.objetos.Alergia;

import java.util.ArrayList;

public class ConsultasAlergiasImpl extends DbHelper implements ConsultasAlergias{

    Context context;

    public ConsultasAlergiasImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public long nuevoRegistroAlergia(int idUsuario, String nombreAlergia) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("nombreAlergia",nombreAlergia );

            id = db.insert(TABLE_ALERGY, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public long recogidaDatosAlergia(int idUsuario, String nombreAlergia, String fechaDatos,
                                     String concentracionAtm,
                                     String valoracion) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("nombreAlergia",nombreAlergia );
            values.put("fechaDatos", fechaDatos);
            values.put("concentracionAtm", concentracionAtm);
            values.put("valoracion", valoracion);

            id = db.insert(TABLE_ALERGY, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public ArrayList<Alergia> mostrarRegistrosAlergia() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Alergia>listaRegistrosAlergia = new ArrayList<>();
        Alergia registrosAlergia = null;
        Cursor cursorRegistrosAlergia = null;

        cursorRegistrosAlergia = db.rawQuery("SELECT nombreAlergia, fechaDatos, " +
                "concentracionAtm, valoracion FROM " + TABLE_ALERGY +
                " ORDER BY fehcaDatos DESC",null);

        if(cursorRegistrosAlergia.moveToFirst()){
            do{
                registrosAlergia = new Alergia();
                registrosAlergia.setNombreAlergia(cursorRegistrosAlergia.getString(0));
                registrosAlergia.setFechaDatos(cursorRegistrosAlergia.getString(1));
                registrosAlergia.setConcentracionAtm(cursorRegistrosAlergia.getString(2));
                registrosAlergia.setValoracion(cursorRegistrosAlergia.getString(3));
                listaRegistrosAlergia.add(registrosAlergia);

            }while(cursorRegistrosAlergia.moveToNext());
        }

        cursorRegistrosAlergia.close();

        return listaRegistrosAlergia;
    }
}
