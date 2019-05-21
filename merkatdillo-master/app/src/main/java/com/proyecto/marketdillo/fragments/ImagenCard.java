package com.proyecto.marketdillo.fragments;

import java.io.Serializable;

public class ImagenCard implements Serializable {

    private int imagen;
    private String nombre;
    private String precioCantidad;
    private String mercadillo;
    private String id;
    private String descripcion;

    public ImagenCard() {
    };

    ImagenCard(int imagen, String nombre, String precioCantidad, String mercadillo) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precioCantidad = precioCantidad;
        this.mercadillo = mercadillo;
    };

    ImagenCard(String id, String nombre, String descripcion, String precioCantidad, int imagen){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCantidad = precioCantidad;
        this.imagen = imagen;
    };

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecioCantidad() {
        return precioCantidad;
    }

    public void setPrecioCantidad(String precioCantidad) {
        this.precioCantidad = precioCantidad;
    }

    public String getMercadillo() {
        return mercadillo;
    }

    public void setMercadillo(String mercadillo) {
        this.mercadillo = mercadillo;
    }


}
