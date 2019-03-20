package com.proyecto.marketdillo;

public class Canasta {
    private String id;
    private String nombreProducto;
    private int precioProducto;
    private int cantidad;
    private int imagen;

    public Canasta() {
    }

    public Canasta(String nombreProducto, int precioProducto,  int cantidad, int imagen) {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public Canasta(String id, String nombreProducto, int precioProducto,  int cantidad, int imagen) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.cantidad = cantidad;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getPrecioProducto() {
        return precioProducto;
    }



    public int getCantidad() {
        return cantidad;
    }

    public int getImagen() {
        return imagen;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setPrecioProducto(int precioProducto) {
        this.precioProducto = precioProducto;
    }


    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Canasta{" +
                "ID='" + id + '\'' +
                "Nombre='" + nombreProducto + '\'' +
                '}';
    }
}
