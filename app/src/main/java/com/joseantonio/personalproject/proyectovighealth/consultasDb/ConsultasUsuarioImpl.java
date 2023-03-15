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


    /**
     * nuevoUsuario: crea un nuevo registro en la tabla Usuario con los valores de los parametros
     * @param nombre
     * @param apellidos
     * @param edad
     * @param genero
     * @param peso
     * @param altura
     * @return el id del registro creado si la transaccion se ha llevado a cabo
     */
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

    /**
     * mostrarUsuario: recupera la informacion del registros de la tabla Usuario
     * @return un objeto Usuario que contiene la informacion recuperada
     */
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

    /**
     * modificarDatosUsuario: modifica el registro de la tabla Usuario con los valores de los parametros
     * @param idUsuario
     * @param edad
     * @param genero
     * @param peso
     * @param altura
     * @return true si la transaccion se lleva a cabo, false si ocurre un error
     */
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

    /**
     * modificarPesoUsuario: modificar el campo peso del registro de la tabla Usuario. Sirve para
     * actualizar el peso del usuario cuando se introduce un nuevo registro en la tabla Peso
     * @param idUsuario
     * @param peso
     * @return true si la transaccion se lleva a cabo, false si ocurre un error
     */
    @Override
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

    /**
     * comprobarUsuario: comprueba la existencia del registro de la tabla Usuario
     * @return true si se ha recuperado informacion del usuario, false si no existe el usuario
     */
    @Override
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

    /**
     * obtenerPesoUsuario: recupera el valor del campo peso del registro de la tabla Usuario
     * @return el valor del campo peso del usuario
     */
    @Override
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

    /**
     * obtenerAlturaUsuario: recupera el valor del campo altura del registro de la tabla Usuario
     * @return el valor del campo altura del usuario
     */
    @Override
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

    /**
     * obtenerIdUsuario: recupera el valor del campo id del registro de la tabla Usuario
     * @return el valor del campo id del Usuario
     */
    @Override
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
