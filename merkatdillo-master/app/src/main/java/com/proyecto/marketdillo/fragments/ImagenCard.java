package com.proyecto.marketdillo.fragments;

import java.io.Serializable;

public class ImagenCard implements Serializable {

    private String imagen;
    private String nombre;
    private String precioCantidad;
    private String mercadillo;
    private String id;
    private String descripcion;
    private String unidad;

    public ImagenCard() {
    };

    ImagenCard(String imagen, String nombre, String precioCantidad, String mercadillo) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precioCantidad = precioCantidad;
        this.mercadillo = mercadillo;
    };

    public ImagenCard(String id, String nombre, String descripcion, String precioCantidad, String imagen, String unidad){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCantidad = precioCantidad;
        this.imagen = imagen;
        this.unidad = unidad;
    };

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
