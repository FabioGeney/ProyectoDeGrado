package com.proyecto.marketdillo;

public class Mensaje {

    private String de;
    private String mensaje;
    private String hora;

    public Mensaje() {
    }

    public Mensaje(String de, String mensaje, String hora) {
        this.de = de;
        this.mensaje = mensaje;
        this.hora = hora;
    }

    public String getDe() {
        return de;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getHora() {
        return hora;
    }

}
