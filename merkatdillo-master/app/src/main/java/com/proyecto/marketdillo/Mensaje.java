package com.proyecto.marketdillo;

public class Mensaje {

    private String nombreUsuario;
    private String Mensaje;
    private String hora;

    public Mensaje() {
    }

    public Mensaje(String nombreUsuario, String mensaje, String hora) {
        this.nombreUsuario = nombreUsuario;
        Mensaje = mensaje;
        this.hora = hora;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public String getHora() {
        return hora;
    }

}
