package com.proyecto.marketdillo;

public class Pedidos {

    private String id;
    private String nombreMercadillo;
    private String estado;
    private int imagen;
    private String total;


    Pedidos(){

    };

    Pedidos( String nombreMercadillo, String estado,  int imagen, String total){
        this.nombreMercadillo = nombreMercadillo;
        this.estado = estado;
        this.imagen = imagen;
        this.total = total;

    };

    Pedidos( String id,  String nombreMercadillo, String estado, int imagen, String total){
        this.id = id;
        this.nombreMercadillo = nombreMercadillo;
        this.estado = estado;
        this.imagen = imagen;
        this.total = total;
    };

    public void setId(String id) {
        this.id = id;
    }

    public void setNombreMercadillo(String nombreMercadillo) {
        this.nombreMercadillo = nombreMercadillo;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public void setTotal(String total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public String getNombreMercadillo() {
        return nombreMercadillo;
    }

    public String getEstado() {
        return estado;
    }

    public int getImagen() {
        return imagen;
    }


    public String getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "ID='" + id + '\'' +
                ", nombreMercadillo='" + nombreMercadillo + '\'' +
                ", estado='" + estado + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
