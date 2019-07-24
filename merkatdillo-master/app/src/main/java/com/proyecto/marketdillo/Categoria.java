package com.proyecto.marketdillo;

public class Categoria {
    private String nombre;
    private int image;

    public Categoria(String nombre, int image) {
        this.nombre = nombre;
        this.image = image;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImage() {
        return image;
    }
}
