package com.germangascon.frasescelebres;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.germangascon.frasescelebres.Services.AlarmaService;
import com.germangascon.frasescelebres.Services.MyReceiver;
import com.germangascon.frasescelebres.Services.onAlarmaService;
import com.germangascon.frasescelebres.Setting.SettingsActivity;
import com.germangascon.frasescelebres.activities.AdministadorActivity;
import com.germangascon.frasescelebres.activities.InsertarActivity;
import com.germangascon.frasescelebres.soap.SoapMethod;
import com.germangascon.frasescelebres.activities.ContenidoActivity;
import com.germangascon.frasescelebres.activities.ListadoAutoresActivity;

import java.util.Calendar;


//public class MainActivity extends AppCompatActivity implements onAlarmaService {
public class MainActivity extends AppCompatActivity{
    private final static String TAG = "MainActivity";
    private SharedPreferences prefs;
    PendingIntent pi;
  //  AlarmaService alarmaService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String hora= prefs.getString("hora","12");
        String min= prefs.getString("min", "00");

        Log.d(TAG, hora + ":" + min);




            Calendar aviso = Calendar.getInstance();
            aviso.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hora));
            aviso.set(Calendar.MINUTE, Integer.parseInt(min));
            int horaAprox= (int) aviso.getTimeInMillis();
            Log.d(TAG, " Se ha guardado la alarma a las :" + aviso.getTime().toString());


            Intent i = new Intent("miAlarma");
            //  Intent i = new Intent(MainActivity.this, MyReceiver.class);
            pi = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);

            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, horaAprox, pi);



        FloatingActionButton noti = (FloatingActionButton) findViewById(R.id.noti);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent service = new Intent(MainActivity.this, AlarmaService.class);
                 startService(service);
                Snackbar.make(view, "Activar notificación", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipoAcceso=prefs.getString("log","con");
                String usu=prefs.getString("Usuario", "");
                String pass=prefs.getString("Pass", "");

                if(tipoAcceso.equalsIgnoreCase("adm")&&(usu.equals("frase")&& pass.equals("frase"))) {
                    Snackbar.make(view, "Ole, puedes editar", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Intent i = new Intent(MainActivity.this, AdministadorActivity.class);
                    startActivity(i);
                }else{

                    Snackbar.make(view, "Necesita permiso de administrador. Cambie la configuración en Settings", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();


                }
            }
        });



            final TextView textView = (TextView) findViewById(R.id.textContenido);

            Button fraseDelDia = (Button) findViewById(R.id.button1);
            Button frasesPorAutor = (Button) findViewById(R.id.button2);
            Button frasesPorCategoria = (Button) findViewById(R.id.button3);

            Button insertar = (Button) findViewById(R.id.insertar);
            Button modificar = (Button) findViewById(R.id.modificar);

            fraseDelDia.setText("Frase del día");
            frasesPorAutor.setText("Frases por autor");
            frasesPorCategoria.setText("Frases por Categoría");

            fraseDelDia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(MainActivity.this, ContenidoActivity.class);
                    i.putExtra("method", SoapMethod.GET_FRASE_DEL_DIA);
                    startActivity(i);

                }


            });

            frasesPorAutor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(MainActivity.this, ListadoAutoresActivity.class);
                    i.putExtra("method", SoapMethod.GET_LIST_AUTORES);
                    startActivity(i);

                }


            });

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, InsertarActivity.class);
                i.putExtra("method", SoapMethod.ADD_AUTOR);
                startActivity(i);

            }


        });



       }








    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
 /*
    @Override
    public void setAlarma(AlarmaService alarmaService) {
        this.alarmaService=alarmaService;

    }*/
}


    /*
    @Override
    public void onJsonResult(String method, String json) {
        JSONObject jsonObject;
        JSONArray jsonArray;

        if(json != null) {
            try {
                switch (method) {
                    case SoapMethod.GET_FRASE_DEL_DIA:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("texto"));
                        //TODO Mostrar la frase del día en la Activity que corresponda
                        break;
                    case SoapMethod.GET_FRASES_BY_AUTOR:
                        jsonArray = (JSONArray) new JSONTokener(json).nextValue();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            textView.setText(jsonObject.getString("texto"));
                        }
                        //TODO Mostrar las frases del autor en un ListView/RecyclerView en la Activity que corresponda
                        break;

                    case SoapMethod.GET_FRASES_BY_CATEGORIA:
                        jsonArray = (JSONArray) new JSONTokener(json).nextValue();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            textView.setText(jsonObject.getString("texto"));
                        }
                        //TODO Mostrar las frases de la categoría en un ListView/RecyclerView en la Activity que corresponda
                        break;
                    case SoapMethod.GET_AUTOR:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("nombre"));
                        //TODO Mostrar los datos del autor en la Activity que corresponda
                        break;

                    case SoapMethod.GET_CATEGORIA:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("nombre"));
                        //TODO Mostrar los datos de la categoría en la Activity que corresponda
                        break;

                    case SoapMethod.ADD_AUTOR:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("nombre"));
                        //TODO Mostrar feedback
                        break;

                    case SoapMethod.ADD_CATEGORIA:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("nombre"));
                        //TODO Mostrar feedback
                        break;

                    case SoapMethod.ADD_FRASE:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("texto"));
                        //TODO Mostrar feedback
                        break;

                    case SoapMethod.MODIFY_AUTOR:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("nombre"));
                        //TODO Mostrar feedback
                        break;

                    case SoapMethod.MODIFY_CATEGORIA:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("nombre"));
                        //TODO Mostrar feedback
                        break;

                    case SoapMethod.MODIFY_FRASE:
                        jsonObject = (JSONObject) new JSONTokener(json).nextValue();
                        textView.setText(jsonObject.getString("texto"));
                        //TODO Mostrar feedback
                        break;


                    case SoapMethod.GET_LIST_AUTORES:
                        jsonArray = (JSONArray) new JSONTokener(json).nextValue();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            textView.setText(textView.getText() + " " + jsonObject.getString("nombre"));
                        }
                        break;

                    case SoapMethod.GET_LIST_CATEGORIAS:
                        jsonArray = (JSONArray) new JSONTokener(json).nextValue();
                        for(int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            textView.setText(textView.getText() + " " + jsonObject.getString("nombre"));
                        }
                        break;
                }

            } catch (JSONException jsone) {
                Log.e(TAG, "Error al parsear el json " + json);
            } catch (Exception ex) {
                Log.e(TAG, "Error al interpretar el json " + json);
                Log.e(TAG, ex.getMessage());
            }
        }
    }

    */


