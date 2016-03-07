package com.germangascon.frasescelebres.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.germangascon.frasescelebres.R;

/**
 * Created by vesprada on 03/03/2016.
 */
public class DetalleAutor extends ActionBarActivity implements View.OnClickListener{
    TextView textViewAutor ;
    TextView textViewNacimiento;
    TextView textViewMuerte ;
    TextView textViewProfesion ;

    private int autor_Id;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datos_autor);

        textViewAutor = (TextView) findViewById(R.id.textNombreAutor);
        textViewNacimiento = (TextView) findViewById(R.id.textNacimiento);
        textViewMuerte = (TextView) findViewById(R.id.textCategoria);
        textViewProfesion = (TextView) findViewById(R.id.textProfesion);

        autor_Id=0;
        Intent intent= getIntent();
        autor_Id=intent.getIntExtra("autor_Id",0);


    }

    @Override
    public void onClick(View v) {

    }
}
