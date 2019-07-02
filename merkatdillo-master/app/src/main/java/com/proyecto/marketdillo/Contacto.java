package com.proyecto.marketdillo;

public class Contacto {
    private String nombre;
    private String id;
    private String imagen;
    private String telefono;
    private String ultimoMensaje;

    public Contacto() {
    }

    public Contacto(String id, String imagen, String nombre, String telefono, String ultimoMensaje) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.telefono = telefono;
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
    public String getTelefono(){
        return telefono;
    }
    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}
