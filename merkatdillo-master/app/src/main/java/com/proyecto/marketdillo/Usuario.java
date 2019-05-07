package com.proyecto.marketdillo;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String nombre;
    private String apellidos;
    private String email;
    private String celular;
    private String fecha;
    private String doc_identidad;
    private String direccion;
    private String password;
    private String id;
    private String tipoUsuario;

    public Usuario(){

    }

    public Usuario(String nombre, String apellidos, String email, String celular, String fecha, String direccion, String password, String id) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.celular = celular;
        this.fecha = fecha;
        this.direccion = direccion;
        this.password = password;
        this.id = id;

    }

    public Usuario(String nombre, String apellidos, String email, String celular, String fecha, String doc_identidad, String direccion, String password, String id) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.celular = celular;
        this.fecha = fecha;
        this.doc_identidad = doc_identidad;
        this.direccion = direccion;
        this.password = password;
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDoc_identidad(String doc_identidad) {
        this.doc_identidad = doc_identidad;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEmail() {
        return email;
    }

    public String getCelular() {
        return celular;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDoc_identidad() {
        return doc_identidad;
    }

    public String getDireccion() {
        return direccion;
    }
}
