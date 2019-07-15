package com.proyecto.marketdillo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Mercadillo extends RealmObject {
    @PrimaryKey
    private String id;
    private String nombre;
    private String calificacion;
    private String tiempoEntrega;
    private String costoEnvio;
    private int imagen;

    public Mercadillo(){


    };

    public Mercadillo( String id, String nombre, String tiempoEntrega){
        this.id=id;
        this.nombre = nombre;
        this.tiempoEntrega = tiempoEntrega;


    }

    public Mercadillo( String nombre, String calificacion, String tiempoEntrega, String costoEnvio){
        this.nombre = nombre;
        this.tiempoEntrega = tiempoEntrega;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
    }

    public Mercadillo( String nombre, String tiempoEntrega, String calificacion, String costoEnvio, int imagen){
        this.nombre = nombre;
        this.tiempoEntrega = tiempoEntrega;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
        this.imagen = imagen;
    }

    public Mercadillo( String id, String nombre, String tiempoEntrega, String calificacion, String costoEnvio, int imagen){
        this.id = id;
        this.nombre = nombre;
        this.tiempoEntrega = tiempoEntrega;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
        this.imagen = imagen;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public String getCostoEnvio() {
        return costoEnvio;
    }

    public String getId() {
        return id;
    }

    public int getImagen() {
        return imagen;
    }

    public  String getNombre() {
        return nombre;
    }

    public String getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public void setCostoEnvio(String costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTiempoEntrega(String tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }



}


