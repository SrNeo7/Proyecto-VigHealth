package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasPeso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

import java.util.ArrayList;

public class ConsultasPesoImpl extends DbHelper implements ConsultasPeso {

    Context context;

    public ConsultasPesoImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    /**
     * nuevoPeso: crea un nuevo registro en la tabla Peso con la informacion de los parametros
     * @param idUsuario
     * @param peso
     * @param fechaPeso
     * @param imc
     * @param diferenciaPeso
     * @return el id del registro creado si la transaccion se ha llevado a cabo
     */
    @Override
    public long nuevoPeso(int idUsuario,double peso, String fechaPeso, double imc, double diferenciaPeso) {
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
            db.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return id;
    }

    /**
     * mostrarRegistrosPeso: recupera la informaicon de la tabla Peso
     * @return un ArrayListo con la informacion recuperada
     */
    @Override
    public ArrayList<Peso> mostrarRegistrosPeso() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Peso>listaPeso = new ArrayList<>();
        Peso peso = null;
        Cursor cursorPeso = null;

        cursorPeso = db.rawQuery("SELECT idPeso,peso, fechaPeso, imc, diferenciaPeso FROM " +
                TABLE_WEIGHT + " ORDER BY fechaPeso DESC", null);

        if(cursorPeso.moveToFirst()){
            do{
                peso = new Peso();
                peso.setIdPeso(cursorPeso.getInt(0));
                peso.setPeso(cursorPeso.getDouble(1));
                peso.setFechaPeso(cursorPeso.getString(2));
                peso.setImc(cursorPeso.getDouble(3));
                peso.setDiferenciaPeso(cursorPeso.getDouble(4));
                listaPeso.add(peso);
            }while(cursorPeso.moveToNext());
        }

        cursorPeso.close();
        db.close();
        return listaPeso;

    }

    /**
     * mostrarPorFecha: recupera los registros de la tabla Peso entre dos fechas recibidas por parametro
     * @param fechaInicialPeso
     * @param fechaFinalPeso
     * @return un ArrayListo con la informacion recuperada
     */
    @Override
    public ArrayList<Peso> mostrarPorFecha(String fechaInicialPeso, String fechaFinalPeso) {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Peso>listaPesoHistorico = new ArrayList<>();
        Peso pesoHistorico = null;
        Cursor cursorPesoHistorico = null;

        cursorPesoHistorico = db.rawQuery("SELECT idPeso, peso, fechaPeso, imc, diferenciaPeso FROM " +
                        TABLE_WEIGHT + " WHERE fechaPeso BETWEEN '" + fechaInicialPeso + "' AND '" + fechaFinalPeso +"'",
                null);

        if (cursorPesoHistorico.moveToFirst()){
            do{

               pesoHistorico = new Peso();
               pesoHistorico.setIdPeso(cursorPesoHistorico.getInt(0));
                pesoHistorico.setPeso(cursorPesoHistorico.getDouble(1));
                pesoHistorico.setFechaPeso(cursorPesoHistorico.getString(2));
                pesoHistorico.setImc(cursorPesoHistorico.getDouble(3));
                pesoHistorico.setDiferenciaPeso(cursorPesoHistorico.getDouble(4));
                listaPesoHistorico.add(pesoHistorico);

            }while(cursorPesoHistorico.moveToNext());
        }

        cursorPesoHistorico.close();
        db.close();

        return listaPesoHistorico;
    }

    /**
     * editarPeso: modifica un registro de la tabla Peso con la informacion de los parametros
     * @param idPeso
     * @param peso
     * @param imc
     * @param diferenciaPeso
     * @return true si la transaccion se lleva a cabo, falso si hay algun error
     */
    @Override
    public boolean editarPeso(int idPeso, double peso, double imc, double diferenciaPeso) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("UPDATE " + TABLE_WEIGHT + " SET peso = " + peso+ "," +
                    "imc = " + imc + "," + "diferenciaPeso = '" + diferenciaPeso + "'" +
                    "WHERE idPeso = '" + idPeso + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        }


        return correcto;
    }

    /**
     * eliminarPeso: elimina el registro de la tabla Peso cuyo id se correspponda con el parametro idPeso
     * @param idPeso
     * @return true si la transaccion se lleva a cabo, falso si hay algun error
     */
    @Override
    public boolean eliminarPeso(int idPeso) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);

        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.execSQL("DELETE FROM " + TABLE_WEIGHT + " WHERE idPeso = " + idPeso);
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        }

        return correcto;
    }

    /**
     * ultimoPeso: recupera el ultimo registro introducido en la tabla Peso
     * @return un objeto Peso que contiene la informacion recuperada
     */
    @Override
    public Peso ultimoPeso() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Peso peso = null;
        Cursor cursorPeso = null;

        cursorPeso = db.rawQuery("SELECT peso, diferenciaPeso FROM " +
                TABLE_WEIGHT + " ORDER BY fechaPeso DESC LIMIT 1", null);

        if(cursorPeso.moveToFirst()){
            do{
                peso = new Peso(cursorPeso.getDouble(0), cursorPeso.getDouble(1));

            }while(cursorPeso.moveToNext());
        }

        cursorPeso.close();
        db.close();
        return peso;

    }

    /**
     * verPeso: recupera un registro de la tabla Peso cuyo id se corresponda con el parametro id
     * @param id
     * @return un objeto Peso que contiene la informacion recuperada
     */
    @Override
    public Peso verPeso(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Peso peso = null;
        Cursor cursorPeso;

        cursorPeso = db.rawQuery("SELECT idPeso, peso, fechaPeso, imc, " +
                "diferenciaPeso FROM " +
                TABLE_WEIGHT + " WHERE idPeso = " + id + " LIMIT 1",null);

        if(cursorPeso.moveToFirst()){
            peso = new Peso();
            peso.setIdPeso(cursorPeso.getInt(0));
            peso.setPeso(cursorPeso.getDouble(1));
            peso.setFechaPeso(cursorPeso.getString(2));
            peso.setImc(cursorPeso.getDouble(3));
            peso.setDiferenciaPeso(cursorPeso.getDouble(4));
        }
        cursorPeso.close();
        db.close();

        return peso;
    }


}
