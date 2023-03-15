package com.joseantonio.personalproject.proyectovighealth.utilidades;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utilidades {

    /**
     * verificaFecha: comprobamos que el usuario haya introducido una fecha correcta
     * @param inicial
     * @param fin
     * @return
     */
    public static boolean verificaFecha(String inicial, String fin){

        boolean verificado;
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicialDate = null;
        Date fechaFinalDate = null;

        try{

            fechaInicialDate = date.parse(inicial);
            fechaFinalDate = date.parse(fin);

        }catch (Exception e){
            e.printStackTrace();
        }

        assert fechaInicialDate != null;
        verificado = !fechaInicialDate.after(fechaFinalDate);

        return verificado;
    }


    /**
     * dosDigitos: Funcion para corregir la fecha recogida de los datepicker para que el dia y
     * el mes tengan dos digitos
     * @param n
     * @return String con el dia o el mes corregid0.
     */
    public static String dosDigitos(int n){
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    /**
     * fechaAmericana: Funcion para formatear la fecha recogida en formato europeo a
     * formato internacional soportado por SQLite
     * @param fecha
     * @return String con la fecha formateada soportada por SQLite
     */
    public static String fechaAmericana(String fecha){

        String fechaformateada;

        String dia,mes,anno;

        dia = fecha.substring(0,2);

        mes= fecha.substring(3,5);

        anno = fecha.substring(6,10);

        fechaformateada = anno + "-" + mes + "-" + dia;

        return fechaformateada;
    }

    /**
     * fechaEuropea: Funcion para convertir la fecha recuperada en formato SQLite a formato europeo
     * @param fecha
     * @return fechaFormateada: String que contiene la fecha convertida a formato europeo.
     */
    public static String fechaEuropea(String fecha){

        String fechaFormateada;
        String dia, mes, anno ,hora;

        anno = fecha.substring(0,4);
        mes = fecha.substring(5,7);
        dia = fecha.substring(8,10);
        hora= fecha.substring(11,16);

        fechaFormateada = dia + "/" + mes + "/" + anno + " " + hora;

        return fechaFormateada;
    }

    /**
     * obtenerFechaActual: Funcion para obtener la fecha del momento en el que se introduce
     * el nuevo registro.
     * @return Se devuelve una cadena con la fecha en un formato fecha compatible con SQLite
     */
    public static String obtenerFechaActual(){

        String fechaActual;

        fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

        return fechaActual;
    }

    /**
     * obtenerCategoria: Funcion que asigna una categoria a la medicion de tension introducida
     * @param sistolica Float con la tension sistolica introducida por el usuario
     * @param diastolica Float con la tension diastolica introducida por el usuario
     * @return Un string con el resultado de la evaluacion de la medicion recibida como parametro
     */
    public static String obtenerCategoria(float sistolica, float diastolica){

        String categoriaAsignada;

        //Limites de los valores de tension para establecer la comparacion con los parametros
        final float limiteHipoSis = 8;
        final float limiteHipoDias = 6;
        final float limiteHiperSis = 12;
        final float limiteHiperDias = 8;

        //Comparacion de los valores recibidos por parametro con los limites establecidos
        if (sistolica<limiteHipoSis || diastolica<limiteHipoDias){
            categoriaAsignada = "Baja";
        }else if (sistolica>limiteHiperSis || diastolica>limiteHiperDias){
            categoriaAsignada = "Alta";
        }else{
            categoriaAsignada = "Normal";
        }

        return categoriaAsignada;
    }
}
