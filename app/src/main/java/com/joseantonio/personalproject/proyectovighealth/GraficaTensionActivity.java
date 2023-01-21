package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasTensionImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityGraficaTensionBinding;
import com.joseantonio.personalproject.proyectovighealth.interfaces.ConsultasTension;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GraficaTensionActivity extends DrawerBaseActivity {

    ActivityGraficaTensionBinding graficaTensionBinding;

    EditText fechaInicial, fechaFinal;
    Button desdeBtn,hastaBtn,generarBtn;
    TextView periodoRepresentado;

    String fechaDesde,fechaHasta;
    int dia, mes, anno;
    ArrayList<Tension>datosTension;

    BarChart grafico;
    BarDataSet barDataSet1,barDataSet2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graficaTensionBinding = ActivityGraficaTensionBinding.inflate(getLayoutInflater());
        setContentView(graficaTensionBinding.getRoot());
        allocateActivityTitle("Gráfico de tensión");

        fechaInicial = findViewById(R.id.et_fechaGraTensionDesde);
        fechaFinal = findViewById(R.id.et_fechaGraTensionHasta);
        desdeBtn = findViewById(R.id.btn_fechaGraTensionDesde);
        hastaBtn = findViewById(R.id.btn_fechaGraTensionHasta);
        generarBtn = findViewById(R.id.btn_GTGenerar);
        periodoRepresentado = findViewById(R.id.tvGraficoGenerado);
        grafico = findViewById(R.id.graficaTension);

        desdeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFecha(fechaInicial);
            }
        });

        hastaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFecha(fechaFinal);
            }
        });

        generarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                grafico.clear();

                String periodoGrafico,fechaInicialString,fechaFinalString;

                fechaInicialString = fechaInicial.getText().toString();
                fechaFinalString = fechaFinal.getText().toString();

                //Recogemos la fecha de los editText y la convertimos al formato fecha de SQLite
                fechaDesde = fechaAmericana(fechaInicialString);
                fechaHasta = fechaAmericana(fechaFinalString);
                boolean comprobacionFecha = verificaFecha(fechaDesde,fechaHasta);

                if(fechaDesde!="" && fechaHasta!=""&& comprobacionFecha) {

                    periodoGrafico = "Periodo representado desde " + fechaInicialString + " hasta " + fechaFinalString;
                    periodoRepresentado.setText(periodoGrafico);
                    fechaHasta = fechaHasta + " 23:59";

                    ConsultasTensionImpl consultasTension = new ConsultasTensionImpl(GraficaTensionActivity.this);
                    datosTension = new ArrayList<>(consultasTension.mostrarPorFechas(fechaDesde,fechaHasta));
                    ArrayList<String> labelsEjeX = new ArrayList<>(ejeX(datosTension));
                    barDataSet1 = new BarDataSet(obtenerDatosSistolica(datosTension), "Tensión Sistólica");
                    barDataSet1.setColor(Color.BLUE);
                    barDataSet2 = new BarDataSet(obtenerDatosDiastolica(datosTension),"Tensión Diastólica");
                    barDataSet2.setColor(Color.RED);

                    BarData data = new BarData(barDataSet1,barDataSet2);
                    grafico.setData(data);

                    grafico.getDescription().setEnabled(false);

                    //Definimos una varible para el eje X
                    XAxis xAxis = grafico.getXAxis();
                    //Cargamos los datos de las etiques del eje X en la variable
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsEjeX));
                    //Centramos los labels del eje X
                    xAxis.setCenterAxisLabels(true);
                    //Situamos los labels debajo del eje X
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    //La granularidad permite que podamos hacer zoom en los datos del grafico
                    xAxis.setGranularity(1);

                    xAxis.setGranularityEnabled(true);
                    //Permitimos el desplazamiento en el grafico para ver todos los valores
                    grafico.setDragEnabled(true);
                    //Definimos el numero maximo de datos que se mostraran en pantalla
                    grafico.setVisibleXRangeMaximum(3);

                    float barSpace = 0.1f;

                    float groupSpace = 0.5f;
                    //Establecemos el ancho de las barras
                    data.setBarWidth(0.15f);
                    //Definimos el maximo y el minimo de datos que podra albergar el eje X
                    grafico.getXAxis().setAxisMinimum(-data.getBarWidth()/2);

                    grafico.getXAxis().setAxisMaximum(labelsEjeX.size()-data.getBarWidth()/2);

                    grafico.animate();
                    //Agrupamos las barras para su representacion grafica
                    grafico.groupBars(0f,groupSpace,barSpace);

                    grafico.invalidate();



                }else{
                    Toast.makeText(GraficaTensionActivity.this,
                            "Fechas no introducidas o introducidas de forma incorrecta",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    private boolean verificaFecha(String inicial, String fin){

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

    private void seleccionarFecha(EditText fecha){

        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anno = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(GraficaTensionActivity.this ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = dosDigitos(day) + "/" + dosDigitos(month+1) +
                        "/" + year;
                fecha.setText(selectedDate);
            }
        },dia,mes,anno);

        datePickerDialog.updateDate(anno,mes,dia);
        datePickerDialog.show();

    }

    /**
     * dosDigitos: Funcion para corregir la fecha recogida de los datepicker para que el dia y
     * el mes tengan dos digitos
     * @param n
     * @return String con el dia o el mes corregid0.
     */
    private String dosDigitos (int n){
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private ArrayList<String>ejeX(ArrayList<Tension>datos){

        ArrayList<String>datosX = new ArrayList<>();
        String fechaModificada,fechaLabel;
        String dia,mes;

        for (int i=0;i<datos.size();i++){
            fechaModificada = datos.get(i).getFechaTension();
            dia=fechaModificada.substring(8,10);
            mes =fechaModificada.substring(5,7);
            fechaLabel = dia + "/" + mes;
            datosX.add(fechaLabel);

        }
        return datosX;
    }

    /**
     * obtenerDatosSistolica: Funcion para extraer los datos de los registros seleccionados
     * que formaran parte del primer set de datos del grafico
     * @param datos
     * @return ArrayList con los datos del primer set de datos
     */
    private ArrayList<BarEntry>obtenerDatosSistolica(ArrayList<Tension>datos){

        ArrayList<BarEntry>datosSistolica = new ArrayList<>();

        for (int i=0;i<datos.size();i++){

            datosSistolica.add(new BarEntry(i, (float) datos.get(i).getSistolica()));

        }
        return datosSistolica;

    }

    /**
     * obtenerDatosDiastolica: Funcion para extraer los datos de los registros seleccionados
     * que formaran parte del segundo set de datos del grafico
     * @param datos
     * @return ArrayList con los datos del segundo set de datos
     */
    private ArrayList<BarEntry>obtenerDatosDiastolica(ArrayList<Tension>datos){

        ArrayList<BarEntry>datosDiastolica = new ArrayList<>();

        for (int i=0;i<datos.size();i++){

            datosDiastolica.add(new BarEntry(i, (float) datos.get(i).getDiastolica()));

        }
        return datosDiastolica;

    }

    /**
     * fechaAmericana: Funcion para formatear la fecha recogida en formato europeo a
     * formato internacional soportado por SQLite
     * @param fecha
     * @return String con la fecha formateada soportada por SQLite
     */
    private String fechaAmericana(String fecha){

        String fechaformateada;

        String dia,mes,anno;

        dia = fecha.substring(0,2);

        mes= fecha.substring(3,5);

        anno = fecha.substring(6,10);

        fechaformateada = anno + "-" + mes + "-" + dia;

        return fechaformateada;
    }


}