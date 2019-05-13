package com.proyecto.marketdillo;

public class SingletonMercadillo {
    private static SingletonMercadillo singletonMercadillo = new SingletonMercadillo();
    public static SingletonMercadillo getInstance(){
        return singletonMercadillo;
    }

    public static void setSingletonMercadillo(SingletonMercadillo singletonUsuario){
        SingletonMercadillo.singletonMercadillo = singletonUsuario;
    }

    private Mercadillo mercadillo;

    private SingletonMercadillo(){

    }

    public Mercadillo getMercadillo() {
        return mercadillo;
    }

    public void setMercadillo(Mercadillo mercadillo) {
        this.mercadillo = mercadillo;
    }
}
