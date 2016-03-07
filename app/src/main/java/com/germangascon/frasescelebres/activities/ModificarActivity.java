package com.germangascon.frasescelebres.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.Setting.SettingsActivity;
import com.germangascon.frasescelebres.interfaces.Resultado;
import com.germangascon.frasescelebres.soap.FrasesCelebresTask;
import com.germangascon.frasescelebres.soap.SoapParam;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by usuario on 07/03/2016.
 */
public class ModificarActivity extends Activity implements Resultado {
    private final static String TAG = "ModificarActivity";
    private String method;
    private SharedPreferences prefs;




    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autor_edit);


        Button guardar=(Button)findViewById(R.id.btnSave);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        method = getIntent().getExtras().getString("method");

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nombre=(EditText)findViewById(R.id.editNombre);
                EditText nacimiento =(EditText)findViewById(R.id.editNacimiento);
                EditText muerte =(EditText)findViewById(R.id.editMuerte);
                EditText profesion =(EditText)findViewById(R.id.editProfesion);




            /* pasamos por parámetro un objeto que implemente la Interfie Resultado, para
            que desde FrasesCelebresTask llame a cada actividad y allí se ejecute el código que llama
            al servidor.
             */
                FrasesCelebresTask task = new FrasesCelebresTask(ModificarActivity.this, prefs);

                // Le pasamos por parámetro el método que ha de ejecutar
                SoapParam soapParam = new SoapParam(method);
                soapParam.addParam("nombre",nombre.getText().toString());
                soapParam.addParam("nacimiento",nacimiento.getText().toString());
                soapParam.addParam("muerte",muerte.getText().toString());
                soapParam.addParam("profesion",profesion.getText().toString());

                task.execute(soapParam);

            }


        });




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

        // final TextView textView = (TextView) findViewById(R.id.textNombre);
        final   EditText nombre=(EditText)findViewById(R.id.editNombre);
        JSONObject json;
        try {
            json = (JSONObject) new JSONTokener(s).nextValue();

            if((json.getString("nombre").toString()).equalsIgnoreCase((nombre.getText().toString()))){
                Toast.makeText(this, "El autor se ha agregado correctamente", Toast.LENGTH_SHORT).show();


            }
        } catch (JSONException jsone) {
            Log.e(TAG, "Error al parsear el json");
        } catch (Exception ex) {
            Log.e(TAG, "Error al interpretar el json");
            Log.e(TAG, ex.getMessage());
        }


    }
}
