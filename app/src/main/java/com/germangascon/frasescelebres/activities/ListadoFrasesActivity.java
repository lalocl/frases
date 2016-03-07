package com.germangascon.frasescelebres.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.Setting.SettingsActivity;
import com.germangascon.frasescelebres.interfaces.Resultado;
import com.germangascon.frasescelebres.modelo.Frase;
import com.germangascon.frasescelebres.soap.FrasesCelebresTask;
import com.germangascon.frasescelebres.soap.SoapParam;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by usuario on 07/03/2016.
 */
public class ListadoFrasesActivity extends ListActivity implements Resultado {

    private final static String TAG = "ListadoFrasesActivity";
    private String method;
    private ListView lista;
    TextView textContenido;
    TextView id;
    //id de autor o de categoria sobre la que hacer listado de frases
    private int listado_Id;

    private SharedPreferences prefs;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        method=getIntent().getExtras().getString("method");
        lista=(ListView)getListView();


        listado_Id=0;
        Intent i=getIntent();
        listado_Id=i.getIntExtra("id_autor", 0);



            /* pasamos por parámetro un objeto que implemente la Interfie Resultado, para
            que desde FrasesCelebresTask llame a cada actividad y allí se ejecute el código que llama
            al servidor.
             */
        FrasesCelebresTask task = new FrasesCelebresTask(this,prefs);

        // Le pasamos por parámetro el método que ha de ejecutar
        SoapParam soapParam = new SoapParam(method);

        soapParam.addParam("idAutor",String.valueOf(listado_Id) );



        task.execute(soapParam);



    }
    @Override
    public void onStart(){

        super.onStart();
    }

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


    @Override
    public void onResult(String s) {

        Log.d(TAG, s);
        ArrayList<Frase> contenido = new ArrayList<Frase>();

        JSONArray jsonArray;
        JSONObject json;


        try {
            jsonArray = (JSONArray) new JSONTokener(s).nextValue();



            for(int i = 0; i < jsonArray.length(); i++) {

                json = jsonArray.getJSONObject(i);
                int id = Integer.parseInt((String) json.getString("id"));
                String texto = (String) json.getString("texto");
                contenido.add(new Frase(id, texto));


            }

        } catch (JSONException jsone) {
            Log.e(TAG, "Error al parsear el json");
        } catch (Exception ex) {
            Log.e(TAG, "Error al interpretar el json");
            Log.e(TAG, ex.getMessage());
        }


        ListaFraseAdaptador adapter;
        adapter= new ListaFraseAdaptador(this,contenido);
        lista.setAdapter(adapter);






    }

}