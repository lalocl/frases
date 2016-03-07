package com.germangascon.frasescelebres.modelo;

import java.util.Date;

/**
 * Created by usuario on 07/03/2016.
 */
public class Frase {

    private int id;
    private int idAutor;
    private int idCategoria;
    private String texto;
    private Date fechaProgramada;

    public Frase(){

    }

    public Frase(int id) {
        this.id = id;
    }
    public Frase(int id,String texto) {
        this.id = id;
        this.texto = texto;
    }

    public Frase(int id, int idAutor, int idCategoria, String texto, Date fechaProgramada) {
        this.id = id;
        this.idAutor = idAutor;
        this.idCategoria = idCategoria;
        this.texto = texto;
        this.fechaProgramada = fechaProgramada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }
}
