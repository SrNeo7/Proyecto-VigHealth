package com.joseantonio.personalproject.proyectovighealth.consultasDb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.joseantonio.personalproject.proyectovighealth.db.DbHelper;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasUsuario;
import com.joseantonio.personalproject.proyectovighealth.objetos.Usuario;

public class ConsultasUsuarioImpl extends DbHelper implements ConsultasUsuario {

    Context context;

    //Constructor de la clase
    public ConsultasUsuarioImpl(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    @Override
    public long nuevoUsuario(String nombre, String apellidos, int edad, String genero, double peso,
                             double altura) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("apellidos", apellidos);
            values.put("edad", edad);
            values.put("genero", genero);
            values.put("peso", peso);
            values.put("altura", altura);

            id = db.insert(TABLE_USER, null, values);
            db.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public Usuario mostarDatosUsuario() {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Usuario usuario = null;
        Cursor cursorUsuario = null;

        cursorUsuario = db.rawQuery("SELECT nombre, apellidos, edad, genero, peso, altura FROM " +
                TABLE_USER, null);

        if (cursorUsuario.moveToFirst()) {
            do {
                usuario = new Usuario();
                usuario.setNombre(cursorUsuario.getString(0));
                usuario.setApellidos(cursorUsuario.getString(1));
                usuario.setEdad(cursorUsuario.getInt(2));
                usuario.setGenero(cursorUsuario.getString(3));
                usuario.setPeso(cursorUsuario.getDouble(4));
                usuario.setAltura(cursorUsuario.getDouble(5));

            } while (cursorUsuario.moveToNext());
        }

        cursorUsuario.close();
        db.close();

        return usuario;

    }

    @Override
    public boolean modificarDatosUsuario(int idUsuario,int edad,
                                         String genero, double peso, double altura) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_USER + " SET edad = '" + edad + "'," +
                    "genero = '" + genero + "', peso = '" + peso + "', altura = '" + altura + "'" +
                    "WHERE idUsuario = '" + idUsuario + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }


        return correcto;
    }

    public boolean modificarPesoUsuario(int idUsuario, double peso) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_USER + " SET  peso = '" + peso + "' "+
                    "WHERE idUsuario = '" + idUsuario + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            correcto = false;
        } finally {
            db.close();
        }


        return correcto;
    }

    public boolean comprobarUsuario() {

        boolean usuario = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUsuario = null;


        cursorUsuario = db.rawQuery("SELECT idUsuario FROM " +
                TABLE_USER, null);

        int id = cursorUsuario.getCount();

        if (id != 0) {
            usuario = true;
        }
        cursorUsuario.close();
        db.close();

        return usuario;
    }

    public double obtenerPesoUsuario() {

        double pesoUsuario = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUsuario = null;

        cursorUsuario = db.rawQuery("SELECT peso FROM " +
                TABLE_USER, null);

        if(cursorUsuario.moveToFirst()) {
            do {
                pesoUsuario = cursorUsuario.getFloat(0);
            } while (cursorUsuario.moveToNext());
        }
        cursorUsuario.close();
        db.close();
        return pesoUsuario;

    }

    public double obtenerAlturaUsuario() {

        double alturaUsuario = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUsuario = null;

        cursorUsuario = db.rawQuery("SELECT altura FROM " +
                TABLE_USER, null);

        if(cursorUsuario.moveToFirst()) {
            do {
                alturaUsuario = cursorUsuario.getFloat(0);
            } while (cursorUsuario.moveToNext());
        }
        cursorUsuario.close();
        db.close();
        return alturaUsuario;
    }

    public int obtenerIdUsuario(){
        int idUsuario = 0;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorUsuario = null;

        cursorUsuario = db.rawQuery("SELECT idUsuario FROM " +
                TABLE_USER, null);

        if(cursorUsuario.moveToFirst()) {
            do{
            idUsuario = cursorUsuario.getInt(0);
            }while (cursorUsuario.moveToNext());
        }
        cursorUsuario.close();
        db.close();
        return idUsuario;
    }
}
