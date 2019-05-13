package com.proyecto.marketdillo;

public class SingletonUsuario {
    private static SingletonUsuario singletonUsuario = new SingletonUsuario();
    public static SingletonUsuario getInstance(){
        return singletonUsuario;
    }

    public static void setSingletonUsuario(SingletonUsuario singletonUsuario){
        SingletonUsuario.singletonUsuario = singletonUsuario;
    }
    private Usuario usuario;

    private SingletonUsuario(){

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
