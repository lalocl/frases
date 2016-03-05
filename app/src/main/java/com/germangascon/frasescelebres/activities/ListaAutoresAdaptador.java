package com.germangascon.frasescelebres.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.germangascon.frasescelebres.R;
import com.germangascon.frasescelebres.modelo.Autor;

import java.util.ArrayList;

/**
 * Created by vesprada on 04/03/2016.
 */
public class ListaAutoresAdaptador extends ArrayAdapter<Autor> {

    private ArrayList<Autor> autores;

    public ListaAutoresAdaptador(Context context, ArrayList<Autor> autores) {
        super(context, R.layout.datos_autor, autores);
        this.autores=autores;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        View autor= convertView;
        ViewHolder viewHolder;


        if(autor==null) {


            LayoutInflater inflater = LayoutInflater.from(getContext());
            autor = inflater.inflate(R.layout.datos_autor, null);


            viewHolder = new ViewHolder();

            viewHolder.textViewAutor = (TextView) autor.findViewById(R.id.textNombreAutor);
            viewHolder.textViewNacimiento = (TextView) autor.findViewById(R.id.textNacimiento);
            viewHolder.textViewMuerte = (TextView) autor.findViewById(R.id.textMuerte);
            viewHolder.textViewProfesion = (TextView) autor.findViewById(R.id.textProfesion);
            viewHolder.autor_Id = (TextView) autor.findViewById(R.id.autor_Id);

            autor.setTag(viewHolder);


        }else{

            viewHolder=(ViewHolder)autor.getTag();
        }

        viewHolder.autor_Id.setText(String.valueOf(autores.get(position).getIdAutor()));
        viewHolder.textViewAutor.setText(autores.get(position).getNombre());
        viewHolder.textViewNacimiento.setText(String.valueOf(autores.get(position).getNacimiento()));
        viewHolder.textViewMuerte.setText(String.valueOf(autores.get(position).getMuerte()));
        viewHolder.textViewProfesion.setText(autores.get(position).getProfesion());



        return autor;

    }

    /** Clase para aplicar el patrón de diseño ViewHolder */
    static class ViewHolder {
        TextView textViewAutor;
        TextView textViewNacimiento;
        TextView textViewMuerte;
        TextView textViewProfesion;
        TextView autor_Id;

    }
}
