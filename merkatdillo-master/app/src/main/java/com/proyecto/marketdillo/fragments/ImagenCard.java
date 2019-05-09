package com.proyecto.marketdillo.fragments;

public class ImagenCard {

    private String imagen;
    private String nombre;
    private String precioCantidad;
    private String mercadillo;

    public ImagenCard(String imagen, String nombre, String precioCantidad, String mercadillo) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.precioCantidad = precioCantidad;
        this.mercadillo = mercadillo;
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
}
