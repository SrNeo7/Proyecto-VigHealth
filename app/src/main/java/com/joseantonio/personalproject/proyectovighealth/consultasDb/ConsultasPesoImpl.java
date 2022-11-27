package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasPeso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;

import java.util.ArrayList;

public class ConsultasPesoImpl extends DbHelper implements ConsultasPeso {

    Context context;

    public ConsultasPesoImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public long nuevoPeso(int idUsuario, double peso, String fechaPeso, double imc, double diferenciaPeso) {
        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("idUsuario",idUsuario);
            values.put("peso",peso );
            values.put("fechaPeso", fechaPeso);
            values.put("imc", imc);
            values.put("diferenciaPeso", diferenciaPeso);

            id = db.insert(TABLE_WEIGHT, null, values);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public ArrayList<Peso> mostrarRegistrosPeso() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Peso>listaPeso = new ArrayList<>();
        Peso peso = null;
        Cursor cursorPeso = null;

        cursorPeso = db.rawQuery("SELECT peso, fechaPeso, imc, " +
                "diferenciaPeso FROM " +
                TABLE_WEIGHT + " ORDER BY fechaPeso DESC", null);

        if(cursorPeso.moveToFirst()){
            do{
                peso = new Peso();
                peso.setPeso(cursorPeso.getDouble(0));
                peso.setFechaPeso(cursorPeso.getString(1));
                peso.setImc(cursorPeso.getDouble(2));
                peso.setDiferenciaPeso(cursorPeso.getDouble(3));
                listaPeso.add(peso);
            }while(cursorPeso.moveToNext());
        }

        cursorPeso.close();
        return listaPeso;

    }

    @Override
    public ArrayList<Peso> mostrarPorFecha(String fechaInicialPeso, String fechaFinalPeso) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Peso>listaPesoHistorico = new ArrayList<>();
        Peso pesoHistorico = null;
        Cursor cursorPesoHistorico = null;

        cursorPesoHistorico = db.rawQuery("SELECT peso, fechaPeso, imc, diferenciaPeso FROM " +
                        TABLE_WEIGHT + " WHERE fechaPeso BETWEEN '" + fechaInicialPeso + "' AND '" + fechaFinalPeso +"'",
                null);

        if (cursorPesoHistorico.moveToFirst()){
            do{

               pesoHistorico = new Peso();
                pesoHistorico.setPeso(cursorPesoHistorico.getDouble(0));
                pesoHistorico.setFechaPeso(cursorPesoHistorico.getString(1));
                pesoHistorico.setImc(cursorPesoHistorico.getDouble(2));
                pesoHistorico.setDiferenciaPeso(cursorPesoHistorico.getDouble(3));
                listaPesoHistorico.add(pesoHistorico);

            }while(cursorPesoHistorico.moveToNext());
        }

        cursorPesoHistorico.close();

        return listaPesoHistorico;
    }
}
