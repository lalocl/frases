package com.germangascon.frasescelebres.activities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.modelo.Autor;
import com.germangascon.frasescelebres.modelo.Frase;

import java.util.ArrayList;

/**
 * Created by vesprada on 04/03/2016.
 */
public class ListaFraseAdaptador extends ArrayAdapter<Frase> {
    private final static String TAG = "ListaFraseAdaptador";

    private ArrayList<Frase> frases;

    public ListaFraseAdaptador(Context context, ArrayList<Frase> frases) {
        super(context,R.layout.contenido, frases);
        this.frases = frases;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        View frase= convertView;
        ViewHolder viewHolder;

        Log.d(TAG, frases.get(0).getTexto());

        if(frase==null) {


            LayoutInflater inflater = LayoutInflater.from(getContext());
            frase = inflater.inflate(R.layout.contenido, null);


            viewHolder = new ViewHolder();

            viewHolder.id = (TextView) frase.findViewById(R.id.id);
            viewHolder.textContenido = (TextView) frase.findViewById(R.id.textContenido);


            frase.setTag(viewHolder);


        }else{

            viewHolder=(ViewHolder)frase.getTag();
        }

        viewHolder.id.setText(String.valueOf(frases.get(position).getId()));
       viewHolder.textContenido.setText(frases.get(position).getTexto());

        return frase;

    }

    /** Clase para aplicar el patrón de diseño ViewHolder */
    static class ViewHolder {
        TextView textContenido;

        TextView id;


    }
}
