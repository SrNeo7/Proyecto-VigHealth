package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasActividadImpl;
import com.joseantonio.personalproject.proyectovighealth.databinding.ActivityDetallesActividadBinding;
import com.joseantonio.personalproject.proyectovighealth.objetos.Actividad;
import com.joseantonio.personalproject.proyectovighealth.utilidades.Utilidades;

import java.io.File;

public class DetallesActividadActivity extends DrawerBaseActivity {

    ActivityDetallesActividadBinding detallesActividadBinding;

    TextView tvFechaActividad, tvDetalleDistancia,tvDetalleDuracion,tvDetalleRitmo;

    ImageView ivCapturaMapaRuta;

    Actividad actividad;

    int id = 0;

    String activityTitle,fechaActividad,tipoActividad,fechaFormateada,nombreEjercicio,fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detallesActividadBinding = ActivityDetallesActividadBinding.inflate(getLayoutInflater());
        setContentView(detallesActividadBinding.getRoot());
        activityTitle = getString(R.string.at_detalle_actividad);
        allocateActivityTitle(activityTitle);

        tvFechaActividad = findViewById(R.id.tvTipoFechaActividad);
        tvDetalleDistancia = findViewById(R.id.tvDetalleDistancia);
        tvDetalleDuracion = findViewById(R.id.tvDetalleDuracion);
        tvDetalleRitmo = findViewById(R.id.tvDetalleRitmo);
        ivCapturaMapaRuta = findViewById(R.id.ivCapturaMapa);

        //Recupera el id enviado desde el item del recycler view seleccionado

        if(savedInstanceState==null){
            Bundle extras = getIntent().getExtras();
            if(extras==null){
                id = Integer.parseInt(null);
            }else{
                id = extras.getInt("ID");
            }
        }else{
            id = (int)savedInstanceState.getSerializable("ID");
        }

        ConsultasActividadImpl consultasActividad = new ConsultasActividadImpl(DetallesActividadActivity.this);

        actividad = consultasActividad.mostrarActividadPorId(id);

        if(actividad != null){
            fechaActividad = actividad.getFechaActividad();
            tipoActividad = actividad.getTipo();
            fechaFormateada = Utilidades.fechaEuropea(fechaActividad);
            nombreEjercicio = sinonimoTipoActividad(tipoActividad);
            tvFechaActividad.setText(nombreEjercicio + fechaFormateada);
            tvDetalleDistancia.setText(String.valueOf(actividad.getDistancia()));
            tvDetalleDuracion.setText(actividad.getDuracion());
            tvDetalleRitmo.setText(actividad.getRitmo());
            fileName= "map_capture_" + fechaActividad + ".png";
        }

        //Recupera la captura del mapa con el itinerario hecho por el usuario
        File file = new File(getFilesDir(),fileName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
        ivCapturaMapaRuta.setImageBitmap(bitmap);



    }

    /**
     * sinonimoTipoActividad: cambia el nombre de la actividad hecha por el usuario por un sinonimo
     * que se mostrara en un textview de la actividad
     * @param tipo
     * @return una cadena con el sinonimo de la actividad
     */
    private String sinonimoTipoActividad(String tipo){

        String sinonimo;

        switch (tipo){
            case "Andar":
                sinonimo = "Paseo del ";
                break;
            case "Correr":
                sinonimo = "Carrera del ";
                break;
            case "Ciclismo":
                sinonimo = "Etapa del ";
                break;
            default:
                sinonimo = tipo + " del ";
                break;
        }
        return sinonimo;
    }
}