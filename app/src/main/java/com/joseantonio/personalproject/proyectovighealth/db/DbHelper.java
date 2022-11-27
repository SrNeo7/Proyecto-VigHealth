package com.joseantonio.personalproject.proyectovighealth.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "vighealth.db";
    public static final String TABLE_USER = "t_usuario";
    public static final String TABLE_MEDICINE = "t_medicamento";
    public static final String TABLE_ACTIVITY = "t_actividad";
    public static final String TABLE_WEIGHT = "t_peso";
    public static final String TABLE_ALERGY = "t_alergia";
    public static final String TABLE_TENSION = "t_tension";
    public static final String TABLE_WATER = "t_hidratacion";

    //Constructor de la clase
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Sentencia para crear la tabla de la base de datos
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USER + "(" +
                "idUsuario PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "apellidos TEXT NOT NULL," +
                "edad INTEGER," +
                "genero TEXT," +
                "peso NUMERIC," +
                "altura NUMERIC," +
                "categoria TEXT NOT NULL )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TENSION + "(" +
                "idTension PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER REFERENCES " + TABLE_USER + "," +
                "fechaTension TEXT NOT NULL," +
                "sistolica NUMERIC NOT NULL," +
                "diastolica NUMERIC NOT NULL," +
                "valoracion TEXT NOT NULL )");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_MEDICINE + "(" +
                "idMedicamento PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER REFERENCES " + TABLE_USER + "," +
                "nombreMedicamento TEXT NOT NULL," +
                "dosis INTEGER," +
                "medidaDosis TEXT NOT NULL," +
                "periodicidad INTEGER," +
                "comentario TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_WEIGHT + "(" +
                "idPeso PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER REFERENCES " + TABLE_USER + "," +
                "peso NUMERIC NOT NULL," +
                "fechaPeso TEXT NOT NULL," +
                "imc NUMERIC NOT NULL," +
                "difereciaPeso NUMERIC NOT NULL)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ACTIVITY + "(" +
                "idActividad PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER REFERENCES " + TABLE_USER + "," +
                "tipo TEXT NOT NULL," +
                "duracion TEXT," +
                "distancia NUMERIC," +
                "ritmo TEXT," +
                "fechaActividad TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_ALERGY + "(" +
                "idAlergia PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER REFERENCES " + TABLE_USER + "," +
                "nombre TEXT NOT NULL," +
                "fechaDatos TEXT," +
                "concentracionAtm TEXT," +
                "valoracion TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_WATER + "(" +
                "idHidratacion PRIMARY KEY AUTOINCREMENT," +
                "idUsuario INTEGER REFERENCES " + TABLE_USER + "," +
                "estado NUMERIC NOT NULL," +
                "frecuencia INTEGER)");

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //Si cambiamos la version de la base de datos borra la tabla y la vuelve a crear de cero
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TENSION);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_MEDICINE);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_WEIGHT);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_ACTIVITY);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_ALERGY);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_WATER);
        onCreate(sqLiteDatabase);

    }
}
