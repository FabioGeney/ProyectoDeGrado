package com.proyecto.marketdillo;

public class Contacto {
    private String nombre;
    private String id;
    private String imagen;

    private String ultimoMensaje;

    public Contacto() {
    }

    public Contacto(String id, String nombre, String ultimoMensaje) {
        this.nombre = nombre;
        this.ultimoMensaje = ultimoMensaje;
    }

    public Contacto(String id, String imagen, String nombre, String ultimoMensaje) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;

        this.ultimoMensaje = ultimoMensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public String getImagen() {
        return imagen;
    }
    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}
