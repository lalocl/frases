package com.germangascon.frasescelebres.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.Setting.SettingsActivity;
import com.germangascon.frasescelebres.interfaces.Resultado;
import com.germangascon.frasescelebres.soap.FrasesCelebresTask;
import com.germangascon.frasescelebres.soap.SoapParam;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by vesprada on 01/03/2016.
 */
public class ContenidoActivity extends Activity implements Resultado {
    private final static String TAG = "ContenidoActivity";
    private String method;
    private SharedPreferences prefs;


        public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido);

            prefs = PreferenceManager.getDefaultSharedPreferences(this);
            method=getIntent().getExtras().getString("method");


            /* pasamos por parámetro un objeto que implemente la Interfie Resultado, para
            que desde FrasesCelebresTask llame a cada actividad y allí se ejecute el código que llama
            al servidor.
             */
            FrasesCelebresTask task = new FrasesCelebresTask(this,prefs);

            // Le pasamos por parámetro el método que ha de ejecutar
            SoapParam soapParam = new SoapParam(method);

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

        final TextView textView = (TextView) findViewById(R.id.textView);
        JSONObject json;
        try {
        json = (JSONObject) new JSONTokener(s).nextValue();
        textView.setText(json.getString("texto"));
        } catch (JSONException jsone) {
            Log.e(TAG, "Error al parsear el json");
        } catch (Exception ex) {
            Log.e(TAG, "Error al interpretar el json");
            Log.e(TAG, ex.getMessage());
        }


    }
}
