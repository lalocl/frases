package com.germangascon.frasescelebres.modelo;

/**
 * Created by usuario on 07/03/2016.
 */
public class Categoria {
    private  int id;
    private String nombre;


    public Categoria(){

    }

    public Categoria(int id) {
        this.id = id;
    }



    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
