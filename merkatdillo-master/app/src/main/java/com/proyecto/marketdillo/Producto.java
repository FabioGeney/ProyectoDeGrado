package com.proyecto.marketdillo;

public class Producto {
    private String id;
    private String nombre;
    private String descripcion;
    private String precioCantidad;
    private String tipo;
    private String cantidad;
    private int imagen;

    Producto(){

    };

    Producto( String nombre, String descripcion, String precioCantidad, int imagen){

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCantidad = precioCantidad;
        this.imagen = imagen;
    };

    Producto( String nombre, String descripcion, String precioCantidad, String tipo, String cantidad, int imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCantidad = precioCantidad;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    Producto(String id, String nombre, String descripcion, String precioCantidad, int imagen){

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioCantidad = precioCantidad;
        this.imagen = imagen;
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioCantidad(String precioCantidad) {
        this.precioCantidad = precioCantidad;
    }

    public void setImagen(int imagen) {
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

    public String getPrecioCantidad() {
        return precioCantidad;
    }

    public int getImagen() {
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
                ", precioCantidad='" + precioCantidad + '\'' +
                ", Tipo='" + tipo + '\'' +
                '}';
    }

}
