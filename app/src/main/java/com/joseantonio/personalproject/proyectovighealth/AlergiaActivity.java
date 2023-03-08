package com.joseantonio.personalproject.proyectovighealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.joseantonio.personalproject.proyectovighealth.adaptadores.AlergiaAdapter;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasAlergiasImpl;
import com.joseantonio.personalproject.proyectovighealth.consultasDb.ConsultasUsuarioImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AlergiaActivity extends AppCompatActivity /*implements OnValuesObtainedListener*/{

    double longitud, latitud,concentracionAire;

    public String nombreAlergenoEN, nombreAlergenoES, fechaDatos,
            concentracionStr,valoracionConcentracion, valoracionTraducida;

    RecyclerView rvlistaDatosAlergia;

    Spinner spAlergenos;

    Button btnConsultar;

    TextView tvPronostico;

    int idUsuario;

    RequestQueue queue;

    final String POLENAPI_KEY = "191fef0e85281094dd38fac1acdea48f58247060";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alergia);
        queue = Volley.newRequestQueue(this);

        tvPronostico = findViewById(R.id.tvPronostico);
        rvlistaDatosAlergia = findViewById(R.id.rvAlergenoCon);
        spAlergenos = findViewById(R.id.spnAlergeno);
        btnConsultar = findViewById(R.id.btnConsultarAlergeno);
        rvlistaDatosAlergia.setLayoutManager(new LinearLayoutManager(AlergiaActivity.this));

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerUbicacion();
                nombreAlergenoES = spAlergenos.getSelectedItem().toString();
                nombreAlergenoEN = traducirNombreAlergeno(nombreAlergenoES);
                fechaDatos = obtenerFechaActual();
                ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(AlergiaActivity.this);
                idUsuario = consultasUsuario.obtenerIdUsuario();
                //peticionDatosVolley(latitud,longitud,nombreAlergenoEN,AlergiaActivity.this);
                //concentracionStr = String.valueOf(concentracionAire);

                String url = "https://api.elichens.com/v0/pollen/now?lat="+latitud+"&lon="+longitud+"&api_key="+POLENAPI_KEY;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONObject pollens = jsonResponse.getJSONObject("pollens");
                            JSONObject polen = pollens.getJSONObject(nombreAlergenoEN);
                            concentracionAire = polen
                                    .getJSONObject("concentration")
                                    .getJSONObject("grainsm3")
                                    .getDouble("value");
                            valoracionConcentracion = polen
                                    .getJSONObject("index")
                                    .getJSONObject("elichens")
                                    .getString("category");
                            valoracionTraducida = traducirValoracion(valoracionConcentracion);
                            concentracionStr = String.format("%.4f",concentracionAire);
                            ConsultasAlergiasImpl consultasAlergias = new ConsultasAlergiasImpl(AlergiaActivity.this);
                            long id = consultasAlergias.recogidaDatosAlergia(idUsuario,nombreAlergenoES,
                                    fechaDatos,concentracionStr,valoracionTraducida);

                            if(id>0){
                                Toast.makeText(AlergiaActivity.this,"Datos recuperados y guardados correctamente.",Toast.LENGTH_LONG).show();
                                AlergiaAdapter adapter = new AlergiaAdapter(consultasAlergias.mostrarRegistrosAlergia(nombreAlergenoES));
                                rvlistaDatosAlergia.setAdapter(adapter);


                            }else{
                                Toast.makeText(AlergiaActivity.this,"Se ha producido un error.",Toast.LENGTH_LONG).show();
                            }
                            System.out.println("Concentracion Olivo: " + concentracionAire);
                            System.out.println("Valoracion de concentracion: " + valoracionConcentracion);
                           /* if (listener!=null){
                                listener.onValuesObtained(concentracionAire,valoracionConcentracion);*/
                            }
                         catch (JSONException e) {

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                queue.add(stringRequest);

                //valoracionTraducida = traducirValoracion(valoracionConcentracion);





                /*ConsultasUsuarioImpl consultasUsuario = new ConsultasUsuarioImpl(AlergiaActivity.this);
                idUsuario = consultasUsuario.obtenerIdUsuario();

                valoracionTraducida = traducirValoracion(valoracionConcentracion);

                ConsultasAlergiasImpl consultasAlergias = new ConsultasAlergiasImpl(AlergiaActivity.this);
                long id = consultasAlergias.recogidaDatosAlergia(idUsuario,nombreAlergenoES,
                        fechaDatos,concentracionStr,valoracionTraducida);

                if(id>0){
                    AlergiaAdapter adapter = new AlergiaAdapter(consultasAlergias.mostrarRegistrosAlergia(nombreAlergenoES));
                    rvlistaDatosAlergia.setAdapter(adapter);
                    rvlistaDatosAlergia.setLayoutManager(new LinearLayoutManager(AlergiaActivity.this));

                }else{
                    Toast.makeText(AlergiaActivity.this,"Se ha producido un error.",Toast.LENGTH_LONG).show();
                }*/

            }
        });

        /*obtenerUbicacion();
        peticionDatosVolley(37.38789,-6.001198,"GRASSPOLLEN",AlergiaActivity.this);
        System.out.println("Nombre alergeno: " + nombreAlergenoES );
        System.out.println("Nombre del alergeno en ingles: " + nombreAlergenoEN);
        System.out.println("Fecha medicion: " + fechaDatos );
        System.out.println("Latitud: " + latitud);
        System.out.println("Longitud: " + longitud);
        System.out.println("Concentracion: " + concentracionAire);
        System.out.println("Valoracion: " + valoracionConcentracion);*/
    }



    private void obtenerUbicacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location!=null){
            latitud = location.getLatitude();
            longitud = location.getLongitude();
        }
    }

    private String traducirNombreAlergeno(String AlergenoES){
        String alergenoTraducido;

        switch (AlergenoES){
            case "Olivo":
                alergenoTraducido = "OLIVEPOLLEN";
                break;
            case "Gramíneas":
                alergenoTraducido = "GRASSPOLLEN";
                break;
            case "Abedul":
                alergenoTraducido = "BIRCHPOLLEN";
                break;
            case "Ambrosía":
                alergenoTraducido = "RAGWEEDPOLLEN";
                break;
            default:
                alergenoTraducido = null;
                break;
        }
        return alergenoTraducido;
    }

    /*private void peticionDatos (double lat, double lon, String alergeno) throws IOException, JSONException {
        URL url = new URL("https://api.elichens.com/v0/pollen/now?lat="+lat+"&lon="+lon+"&api_key="+POLENAPI_KEY);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");
        conexion.connect();

        int responseCode = conexion.getResponseCode();
        if(responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while((inputLine = in.readLine())!=null){
                response.append(inputLine);
            }
            in.close();

            String responseStr = response.toString();
            JSONObject jsonResponse = new JSONObject(responseStr);
            JSONObject pollens = jsonResponse.getJSONObject("pollens");
            if(pollens.has(alergeno)){
                JSONObject pollen = pollens.getJSONObject(alergeno);
                concentracionAire = pollen
                        .getJSONObject("concentration")
                        .getJSONObject("grainsm3")
                        .getString("value");
                valoracionConcentracion = pollen
                        .getJSONObject("index")
                        .getJSONObject("elichens")
                        .getString("category");
            }
        }


    }*/

    /*private void peticionDatosVolley(double lat, double lon, String alergeno,final OnValuesObtainedListener listener){
        String url = "https://api.elichens.com/v0/pollen/now?lat="+lat+"&lon="+lon+"&api_key="+POLENAPI_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject pollens = jsonResponse.getJSONObject("pollens");
                    JSONObject polen = pollens.getJSONObject(alergeno);
                    concentracionAire = polen
                            .getJSONObject("concentration")
                            .getJSONObject("grainsm3")
                            .getDouble("value");
                    valoracionConcentracion = polen
                            .getJSONObject("index")
                            .getJSONObject("elichens")
                            .getString("category");
                    System.out.println("Concentracion Olivo: " + concentracionAire);
                    System.out.println("Valoracion de concentracion: " + valoracionConcentracion);
                    if (listener!=null){
                        listener.onValuesObtained(concentracionAire,valoracionConcentracion);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }*/

    /**
     * obtenerFechaActual: Funcion para obtener la fecha del momento en el que se introduce
     * el nuevo registro.
     * @return Se devuelve una cadena con la fecha en un formato fecha compatible con SQLite
     */
    public String obtenerFechaActual(){

        String fechaActual;

        fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());

        return fechaActual;
    }

    private String traducirValoracion(String valoracion){
        String traduccionValoracion;

        switch (valoracion){
            case "Absent":
                traduccionValoracion = "Sin riesgo";
                break;
            case "Very Low":
                traduccionValoracion = "Muy baja";
                break;
            case "Low":
                traduccionValoracion = "Baja";
                break;
            case "Medium":
                traduccionValoracion = "Media";
                break;
            case "High":
                traduccionValoracion = "Alta";
                break;
            case "Very High":
                traduccionValoracion = "Muy alta";
                break;
            default:
                traduccionValoracion = "Incierta";
                break;

        }
        return traduccionValoracion;
    }

   /* @Override
    public void onValuesObtained(double concentracionAire,String valoracionConcentracion){
        this.concentracionAire = concentracionAire;
        this.valoracionConcentracion = valoracionConcentracion;
    }*/


}
/*interface OnValuesObtainedListener{
    void onValuesObtained(double concentracionAire,String valoracionConcentracion);
}*/