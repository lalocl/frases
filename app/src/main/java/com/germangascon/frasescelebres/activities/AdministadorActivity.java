package com.germangascon.frasescelebres.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.Setting.SettingsActivity;
import com.germangascon.frasescelebres.soap.SoapMethod;

/**
 * Created by usuario on 07/03/2016.
 */
public class AdministadorActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        final TextView textView = (TextView) findViewById(R.id.textContenido);

        Button buttonAutor = (Button) findViewById(R.id.button1);
        Button buttonCategoria = (Button) findViewById(R.id.button2);
        Button buttonFrase = (Button) findViewById(R.id.button3);



        buttonAutor.setText("Autor");
        buttonCategoria.setText("Categoria");
        buttonFrase.setText("Frase");

        buttonAutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent i = new Intent(MainActivity.this, ContenidoActivity.class);
                i.putExtra("method", SoapMethod.GET_FRASE_DEL_DIA);
                startActivity(i);
                */
            }


        });

        buttonCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent i = new Intent(MainActivity.this, ListadoAutoresActivity.class);
                i.putExtra("method", SoapMethod.GET_LIST_AUTORES);
                startActivity(i);
                */
            }


        });

        buttonFrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent i = new Intent(MainActivity.this, InsertarActivity.class);
                i.putExtra("method", SoapMethod.ADD_AUTOR);
                startActivity(i);

                */
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
}

