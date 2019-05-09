package com.proyecto.marketdillo;

public class SingletonUsuario {
    private static SingletonUsuario singletonUsuario = new SingletonUsuario();
    public static SingletonUsuario getInstance(){
        return singletonUsuario;
    }

    public static void setSingletonUsuario(SingletonUsuario singletonUsuario){
        SingletonUsuario.singletonUsuario = singletonUsuario;
    }
    private String nombre;
    private String apellidos;
    private String email;
    private String celular;
    private String fecha;
    private String doc_identidad;
    private String direccion;
    private String id;
    private String tipoUsuario;
    private static Usuario usuario;

    private SingletonUsuario(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDoc_identidad() {
        return doc_identidad;
    }

    public void setDoc_identidad(String doc_identidad) {
        this.doc_identidad = doc_identidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public static Usuario getUsuario() {
        return usuario;
    }

    public static void setUsuario(Usuario usuario) {
        SingletonUsuario.usuario = usuario;
    }
}
