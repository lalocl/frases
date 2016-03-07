package com.germangascon.frasescelebres.modelo;

/**
 * Created by vesprada on 03/03/2016.
 */
public class Autor {
   private int idAutor;
   private String nombre;
   private int nacimiento;
   private int muerte;
    private String profesion;

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(int nacimiento) {
        this.nacimiento = nacimiento;
    }

    public int getMuerte() {
        return muerte;
    }

    public void setMuerte(int muerte) {
        this.muerte = muerte;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }



    public Autor(int idAutor, String nombre, int nacimiento, int muerte, String profesion) {

        this.idAutor=idAutor;
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
        this.profesion = profesion;
    }

    public Autor(){

    }

}
