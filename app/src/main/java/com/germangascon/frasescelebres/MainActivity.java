package com.germangascon.frasescelebres;

import android.os.Bundle;
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

import com.germangascon.frasescelebres.interfaces.JsonResult;
import com.germangascon.frasescelebres.soap.FrasesCelebresTask;
import com.germangascon.frasescelebres.soap.SoapMethod;
import com.germangascon.frasescelebres.soap.SoapParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class MainActivity extends AppCompatActivity implements JsonResult {

    private final static String TAG = "MainActivity";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        textView = (TextView) findViewById(R.id.textView);

        Button btSoapQuery = (Button) findViewById(R.id.btSoapQuery);
        btSoapQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FrasesCelebresTask task = new FrasesCelebresTask(MainActivity.this);

                /** Ejemplos de uso */
                /** IMPORTANTE: NO olvidar la llamada final a task.execute(soapParam) **/

                SoapParam soapParam = new SoapParam(SoapMethod.GET_FRASE_DEL_DIA);

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.GET_FRASES_BY_AUTOR);
                soapParam.addParam("idAutor", "2");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.GET_FRASES_BY_CATEGORIA);
                soapParam.addParam("idCategoria", "1");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.GET_AUTOR);
                soapParam.addParam("id", "1");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.GET_CATEGORIA);
                soapParam.addParam("id", "1");
                */


                /*
                SoapParam soapParam = new SoapParam(SoapMethod.ADD_CATEGORIA);
                soapParam.addParam("nombre", "Inteligencia");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.ADD_AUTOR);
                soapParam.addParam("nombre", "Aristóteles");
                soapParam.addParam("nacimiento", "-384");
                soapParam.addParam("muerte", "-322");
                soapParam.addParam("profesion", "Filósofo griego");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.ADD_FRASE);
                soapParam.addParam("idCategoria", "3");
                soapParam.addParam("idAutor", "3");
                soapParam.addParam("texto", "La inteligencia consiste no sólo en el conocimiento, sino también en la destreza de aplicar los conocimientos en la práctica.");
                soapParam.addParam("fechaProgramada", "");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.GET_LIST_AUTORES);
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.MODIFY_AUTOR);
                soapParam.addParam("id", "3");
                soapParam.addParam("nombre", "Aristóteles");
                soapParam.addParam("nacimiento", "-384");
                soapParam.addParam("muerte", "-322");
                soapParam.addParam("profesion", "Filósofo griego");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.MODIFY_CATEGORIA);
                soapParam.addParam("id", "3");
                soapParam.addParam("nombre", "Inteligencia");
                */


                /*
                SoapParam soapParam = new SoapParam(SoapMethod.MODIFY_FRASE);
                soapParam.addParam("id", "5");
                soapParam.addParam("idCategoria", "3");
                soapParam.addParam("idAutor", "3");
                soapParam.addParam("texto", "La inteligencia consiste no sólo en el conocimiento, sino también en la destreza de aplicar los conocimientos en la práctica.");
                soapParam.addParam("fechaProgramada", "");
                */

                /*
                SoapParam soapParam = new SoapParam(SoapMethod.GET_LIST_CATEGORIAS);
                */

                task.execute(soapParam);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
}
