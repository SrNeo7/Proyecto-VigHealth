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

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasPesoImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityGraficaPesoBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Peso;
import com.joseantonio.personalproject.proyectovighealth.objetos.Tension;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GraficaPesoActivity extends DrawerBaseActivity {

    ActivityGraficaPesoBinding graficaPesoBinding;

    EditText fechaInicial, fechaFinal;
    Button desdeBtn,hastaBtn,generarBtn;
    TextView periodoRepresentado;

    String fechaDesde,fechaHasta,activityTitle;
    int dia, mes, anno;
    ArrayList<Peso> datosPeso;

    LineChart grafico;
    LineDataSet lineDataSet;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        graficaPesoBinding = ActivityGraficaPesoBinding.inflate(getLayoutInflater());
        setContentView(graficaPesoBinding.getRoot());
        activityTitle = getString(R.string.at_peso_grafico);
        allocateActivityTitle(activityTitle);


        fechaInicial = findViewById(R.id.et_fechaGraPeDesde);
        fechaFinal = findViewById(R.id.et_fechaGraPeHasta);
        desdeBtn = findViewById(R.id.btn_fechaGraPeDesde);
        hastaBtn = findViewById(R.id.btn_fechaGraPeHasta);
        generarBtn = findViewById(R.id.btn_GPGenerar);
        periodoRepresentado = findViewById(R.id.tvGraficoPesoGenerado);
        grafico = findViewById(R.id.graficaPeso);

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
                fechaDesde = Utilidades.fechaAmericana(fechaInicialString);
                fechaHasta = Utilidades.fechaAmericana(fechaFinalString);
                boolean comprobacionFecha = Utilidades.verificaFecha(fechaDesde,fechaHasta);

                if(fechaDesde!="" && fechaHasta!=""&& comprobacionFecha) {

                    periodoGrafico = "Periodo representado desde " + fechaInicialString + " hasta " + fechaFinalString;
                    periodoRepresentado.setText(periodoGrafico);
                    fechaHasta = fechaHasta + " 23:59";

                    //Cargamos los valores para el gráfico y configuramos aspectos visuales del gráfico
                    ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(GraficaPesoActivity.this);
                    datosPeso = new ArrayList<>(consultasPeso.mostrarPorFecha(fechaDesde,fechaHasta));
                    ArrayList<String>labelsEjeX = new ArrayList<>(ejeX(datosPeso));
                    lineDataSet = new LineDataSet(obtenerDatosPeso(datosPeso),"Peso(kg)");
                    lineDataSet.setColor(Color.GREEN);
                    lineDataSet.setLineWidth(3f);
                    lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                    lineDataSet.setValueTextSize(1f);

                    LineData data = new LineData(lineDataSet);
                    grafico.setData(data);

                    grafico.getDescription().setEnabled(false);

                    grafico.setVisibleXRangeMaximum(3);

                    //Definimos una varible para el eje X
                    XAxis xAxis = grafico.getXAxis();
                    //Cargamos los datos de las etiquetas del eje X en la variable
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsEjeX));
                    //Situamos los labels debajo del eje X
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    //La granularidad permite que podamos hacer zoom en los datos del grafico
                    xAxis.setGranularityEnabled(true);
                    xAxis.setGranularity(1f);



                    //Permitimos el desplazamiento en el grafico para ver todos los valores
                    grafico.setDragEnabled(true);

                    data.setValueTextSize(15f);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    grafico.animateX(1200, Easing.EaseInSine);


                }else{
                    Toast.makeText(GraficaPesoActivity.this,
                            "Fechas no introducidas o introducidas de forma incorrecta",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * seleccionarFecho: se encarga de mostrar y gestionar el DatePicker
     * @param fecha
     */
    private void seleccionarFecha(EditText fecha){

        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        anno = c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(GraficaPesoActivity.this ,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = Utilidades.dosDigitos(day) + "/" + Utilidades.dosDigitos(month+1) +
                        "/" + year;
                fecha.setText(selectedDate);
            }
        },dia,mes,anno);

        datePickerDialog.updateDate(anno,mes,dia);
        datePickerDialog.show();

    }

    private ArrayList<String>ejeX(ArrayList<Peso>datos){

        ArrayList<String>datosX = new ArrayList<>();
        String fechaModificada,fechaLabel;
        String dia,mes;

        for (int i=0;i<datos.size();i++){
            fechaModificada = datos.get(i).getFechaPeso();
            dia=fechaModificada.substring(8,10);
            mes =fechaModificada.substring(5,7);
            fechaLabel = dia + "/" + mes;
            datosX.add(fechaLabel);

        }
        return datosX;
    }

    /**
     * obtenerDatosPeso: obtiene los valores de peso para las entradas del grafico
     * @param datos
     * @return
     */
    private ArrayList<Entry>obtenerDatosPeso(ArrayList<Peso>datos) {

        ArrayList<Entry> datosPeso = new ArrayList<>();

        for (int i = 0; i < datos.size(); i++) {

            datosPeso.add(new Entry(i, (float) datos.get(i).getPeso()));

        }
        return datosPeso;
    }

}