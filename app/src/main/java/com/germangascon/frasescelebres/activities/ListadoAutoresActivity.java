package com.germangascon.frasescelebres.activities;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.Setting.SettingsActivity;
import com.germangascon.frasescelebres.interfaces.Resultado;
import com.germangascon.frasescelebres.modelo.Autor;
import com.germangascon.frasescelebres.soap.FrasesCelebresTask;
import com.germangascon.frasescelebres.soap.SoapMethod;
import com.germangascon.frasescelebres.soap.SoapParam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by vesprada on 01/03/2016.
 */
public class ListadoAutoresActivity extends ListActivity implements Resultado {

    private final static String TAG = "ListadoAutoresActivity";
    private String method;
    private ListView lista;
    TextView textViewAutor;
    TextView textViewNacimiento;
    TextView textViewMuerte;
    TextView textViewProfesion;
    TextView autor_Id;
    private SharedPreferences prefs;



    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        method=getIntent().getExtras().getString("method");
        lista=(ListView)getListView();


            /* pasamos por parámetro un objeto que implemente la Interfie Resultado, para
            que desde FrasesCelebresTask llame a cada actividad y allí se ejecute el código que llama
            al servidor.
             */
        FrasesCelebresTask task = new FrasesCelebresTask(this,prefs);

        // Le pasamos por parámetro el método que ha de ejecutar

        SoapParam soapParam = new SoapParam(method);
       // SoapParam soapParam = new SoapParam(method);

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
      ArrayList<Autor> autores = new ArrayList<Autor>();




        JSONArray jsonArray;
        JSONObject json;
        try {
            jsonArray = (JSONArray) new JSONTokener(s).nextValue();



            for(int i = 0; i < jsonArray.length(); i++) {

                json = jsonArray.getJSONObject(i);
                int id=Integer.parseInt((String)json.getString("id"));
                String nombre=(String)json.getString("nombre");
                int nacimiento=Integer.parseInt((String)json.getString("nacimiento"));
                int muerte=Integer.parseInt((String)json.getString("muerte"));
                String profesion=(String)json.getString("profesion");

               autores.add(new Autor(id,nombre,nacimiento,muerte, profesion));


            }




        } catch (JSONException jsone) {
            Log.e(TAG, "Error al parsear el json");
        } catch (Exception ex) {
            Log.e(TAG, "Error al interpretar el json");
            Log.e(TAG, ex.getMessage());
        }

        ListaAutoresAdaptador adapter = new ListaAutoresAdaptador(this,autores);
        lista.setAdapter(adapter);


        lista.setClickable(true);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               autor_Id = (TextView) view.findViewById(R.id.autor_Id);
                String id_autor=autor_Id.getText().toString();
                Intent i = new Intent(ListadoAutoresActivity.this, ListadoFrasesActivity.class);
                i.putExtra("method", SoapMethod.GET_FRASES_BY_AUTOR);
                i.putExtra("id_autor",Integer.parseInt(id_autor));
                startActivity(i);


            }
        });
        /*
       OnItemClickListener itemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(ListadoAutoresActivity.this, ListadoFrasesActivity.class);
                i.putExtra("method", SoapMethod.GET_FRASES_BY_AUTOR);
                i.putExtra("id_autor",String.valueOf(position));
                startActivity(i);
            }
        };
        lista.setOnClickListener((View.OnClickListener) itemClickListener);
        */





    }

}


        /*
        JSONObject json;
        JSONArray jsonArray;

        try {
            switch (method) {
                case SoapMethod.GET_FRASE_DEL_DIA:
                    json = (JSONObject) new JSONTokener(s).nextValue();
                    textView.setText(json.getString("texto"));
                    //TODO Mostrar la frase del día en la Activity que corresponda
                    break;
                case SoapMethod.GET_FRASES_BY_AUTOR:
                    jsonArray = (JSONArray) new JSONTokener(s).nextValue();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        json = jsonArray.getJSONObject(i);
                        textView.setText(json.getString("texto"));
                    }
                    //TODO Mostrar las frases del autor en un ListView/RecyclerView en la Activity que corresponda
                    break;

                case SoapMethod.GET_FRASES_BY_CATEGORIA:
                    jsonArray = (JSONArray) new JSONTokener(s).nextValue();
                    for(int i = 0; i < jsonArray.length(); i++) {
                        json = jsonArray.getJSONObject(i);
                        textView.setText(json.getString("texto"));
                    }
                    //TODO Mostrar las frases de la categoría en un ListView/RecyclerView en la Activity que corresponda
                    break;
                case SoapMethod.GET_AUTOR:
                    json = (JSONObject) new JSONTokener(s).nextValue();
                    textView.setText(json.getString("nombre"));
                    //TODO Mostrar los datos del autor en la Activity que corresponda
                    break;

                case SoapMethod.GET_CATEGORIA:
                    json = (JSONObject) new JSONTokener(s).nextValue();
                    textView.setText(json.getString("nombre"));
                    //TODO Mostrar los datos de la categoría en la Activity que corresponda
                    break;

                case SoapMethod.ADD_AUTOR:
                    json = (JSONObject) new JSONTokener(s).nextValue();
                    textView.setText(json.getString("nombre"));
                    //TODO Mostrar feedback
                    break;

                case SoapMethod.ADD_CATEGORIA:
                    json = (JSONObject) new JSONTokener(s).nextValue();
                    textView.setText(json.getString("nombre"));
                    //TODO Mostrar feedback
                    break;

                case SoapMethod.ADD_FRASE:
                    json = (JSONObject) new JSONTokener(s).nextValue();
                    textView.setText(json.getString("texto"));
                    //TODO Mostrar feedback
                    break;
            }

        } catch (JSONException jsone) {
            Log.e(TAG, "Error al parsear el json");
        } catch (Exception ex) {
            Log.e(TAG, "Error al interpretar el json");
            Log.e(TAG, ex.getMessage());
        }
        */
