package com.proyecto.marketdillo;

public class Mercadillo {
    private String id;
    private String nombre;
    private String tiempoEntrega;
    private float calificacion;
    private int costoEnvio;
    private int imagen;

    Mercadillo(){


    };

    public Mercadillo( String nombre, String tiempoEntrga, float calificacion, int costoEnvio, int imagen){
        this.nombre = nombre;
        this.tiempoEntrega = tiempoEntrga;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
        this.imagen = imagen;
    }

    public Mercadillo( String id, String nombre, String tiempoEntrga, float calificacion, int costoEnvio, int imagen){
        this.id = id;
        this.nombre = nombre;
        this.tiempoEntrega = tiempoEntrga;
        this.calificacion = calificacion;
        this.costoEnvio = costoEnvio;
        this.imagen = imagen;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public int getCostoEnvio() {
        return costoEnvio;
    }

    public String getId() {
        return id;
    }

    public int getImagen() {
        return imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void setCostoEnvio(int costoEnvio) {
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

    @Override
    public String toString() {
        return "Mercadillo{" +
                "ID='" + id + '\'' +
                "Nombre='" + nombre + '\'' +
                ", TiempoEntrga='" + tiempoEntrega + '\'' +
                ", Calificacion='" + calificacion + '\'' +
                ", CostoEnvio='" + costoEnvio + '\'' +
                '}';
    }

}


