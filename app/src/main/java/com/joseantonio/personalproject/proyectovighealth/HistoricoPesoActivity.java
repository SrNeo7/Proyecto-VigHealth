package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joseantonio.personalproject.proyectovighealth.adaptadores.PesoAdapter;
import com.joseantonio.personalproject.proyectovighealth.adaptadores.TensionAdapter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasPesoImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasTensionImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityHistoricoPesoBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HistoricoPesoActivity extends DrawerBaseActivity {

    ActivityHistoricoPesoBinding historicoPesoBinding;

    EditText fechaInicial, fechaFinal;
    Button desdeBtn,hastaBtn,consultarBtn;
    TextView registrosMostrados;

    String fechaDesde,fechaHasta,activityTitle;
    int dia, mes, anno;

    RecyclerView historialPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historicoPesoBinding = ActivityHistoricoPesoBinding.inflate(getLayoutInflater());
        setContentView(historicoPesoBinding.getRoot());
        activityTitle = getString(R.string.at_peso_historial);
        allocateActivityTitle(activityTitle);

        fechaInicial = findViewById(R.id.et_fechaPesoDesde);
        fechaFinal = findViewById(R.id.et_fechaPesoHasta);
        desdeBtn = findViewById(R.id.btn_fechaPesoDesde);
        hastaBtn = findViewById(R.id.btn_fechaPesoHasta);
        consultarBtn = findViewById(R.id.btn_HPConsultar);
        registrosMostrados = findViewById(R.id.tvRegistrosPesoRecuperados);
        historialPeso = findViewById(R.id.rvHistoricoPeso);
        historialPeso.setLayoutManager(new LinearLayoutManager(this));

        registrosMostrados.setText(R.string.tv_ultimos_registros);

        ConsultasPesoImpl consultasPeso = new ConsultasPesoImpl(HistoricoPesoActivity.this);
        PesoAdapter adapter = new PesoAdapter(consultasPeso.mostrarRegistrosPeso());
        historialPeso.setAdapter(adapter);

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

        consultarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String registrosConsultados,fechaInicialString,fechaFinalString;

                fechaInicialString = fechaInicial.getText().toString();
                fechaFinalString = fechaFinal.getText().toString();

                //Recogemos la fecha de los editText y la convertimos al formato fecha de SQLite
                fechaDesde = fechaAmericana(fechaInicialString);
                fechaHasta = fechaAmericana(fechaFinalString);
                boolean comprobacionFecha = verificaFecha(fechaDesde,fechaHasta);

                if(fechaDesde!="" && fechaHasta!=""&& comprobacionFecha) {

                    registrosConsultados= "Registros desde " + fechaInicialString + " hasta " + fechaFinalString;
                    registrosMostrados.setText(registrosConsultados);
                    //Con esto se consigue mostrar los registros de la fecha "Desde" incluida.
                    fechaHasta = fechaHasta + " 23:59";


                    ConsultasPesoImpl consultasHistorialPeso = new ConsultasPesoImpl(HistoricoPesoActivity.this);
                    PesoAdapter adapterHistorico = new PesoAdapter(consultasHistorialPeso.mostrarPorFecha(fechaDesde,fechaHasta));
                    historialPeso.setAdapter(adapterHistorico);

                }else{
                    Toast.makeText(HistoricoPesoActivity.this,"Fechas no introducidas o introducidas de forma incorrecta",Toast.LENGTH_LONG).show();
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(HistoricoPesoActivity.this ,new DatePickerDialog.OnDateSetListener() {
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