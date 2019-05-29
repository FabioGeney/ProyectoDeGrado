package com.proyecto.marketdillo;

import java.io.Serializable;

public class Producto implements Serializable{
    //id del campesino
    private String id;
    private String idDocument;
    private String nombre;
    private String descripcion;
    private int precio;
    private String tipo;
    private String cantidad;
    private String imagen;
    private int contador = 0;

    Producto(){

    };

    public Producto(String nombre, int contador, String imagen) {
        this.nombre = nombre;
        this.contador = contador;
        this.imagen = imagen;
    }

    Producto(String nombre, String descripcion, int precio, String imagen){

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    };
    /*
    Producto( String nombre, String descripcion, String precioCantidad, String tipo, String cantidad, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCantidad = precioCantidad;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }
    */
    Producto(String id, String nombre, String descripcion, int precio, String cantidad, String imagen){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.imagen = imagen;
    };

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioCantidad(int precio) {
        this.precio = precio;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPrecioCantidad() {
        return precio;
    }

    public String getImagen() {
        return imagen;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "¨Producto{" +
                "ID='" + id + '\'' +
                "Nombre='" + nombre + '\'' +
                ", Descripcion='" + descripcion + '\'' +
                ", Tipo='" + tipo + '\'' +
                '}';
    }

}
