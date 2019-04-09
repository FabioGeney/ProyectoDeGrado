package com.proyecto.marketdillo;

public class Mercadillo {
    private String id;
    private String nombreMercadillo;
    private String calificacion;
    private String tiempoEntrega;
    private String costoEnvio;
    private int imagen;

    Mercadillo(){


    };

    public Mercadillo( String id, String nombreMercadillo, String tiempoEntrega){
        this.id=id;
        this.nombreMercadillo = nombreMercadillo;
        this.tiempoEntrega = tiempoEntrega;


    }

    public Mercadillo( String nombreMercadillo, String calificacion, String tiempoEntrega, String costoEnvio){
        this.nombreMercadillo = nombreMercadillo;
        this.tiempoEntrega = tiempoEntrega;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
    }

    public Mercadillo( String nombreMercadillo, String tiempoEntrega, String calificacion, String costoEnvio, int imagen){
        this.nombreMercadillo = nombreMercadillo;
        this.tiempoEntrega = tiempoEntrega;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
        this.imagen = imagen;
    }

    public Mercadillo( String id, String nombreMercadillo, String tiempoEntrega, String calificacion, String costoEnvio, int imagen){
        this.id = id;
        this.nombreMercadillo = nombreMercadillo;
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
        return nombreMercadillo;
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
        this.nombreMercadillo = nombre;
    }

    public void setTiempoEntrega(String tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }



}


