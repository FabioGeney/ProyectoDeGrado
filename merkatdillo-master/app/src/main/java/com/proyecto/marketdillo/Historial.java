package com.proyecto.marketdillo;

public class Historial {
    String id;
    private String nombreMercadillo;
    private String fecha;
    private int imagen;


    Historial(){

    };

    Historial(String nommbreMercadillo, String fecha, int imagen){
        this.nombreMercadillo = nommbreMercadillo;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    Historial(String id, String nommbreMercadillo, String fecha, int imagen){
        this.id = id;
        this.nombreMercadillo = nommbreMercadillo;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public String getNombreMercadillo() {
        return nombreMercadillo;
    }

    public int getImagen() {
        return imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public void setNombreMercadillo(String nombreMercadillo) {
        this.nombreMercadillo = nombreMercadillo;
    }

    @Override
    public String toString() {
        return "Historial{" +
                "ID='" + id + '\'' +
                "Nombre='" + nombreMercadillo + '\'' +
                ", Fecha='" + fecha + '\'' +
                '}';
    }

}
