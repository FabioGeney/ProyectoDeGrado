package com.proyecto.marketdillo;

import java.util.List;

public class SingletonFavoritos {
    private static SingletonFavoritos singletonFavoritos = new SingletonFavoritos();
    public static SingletonFavoritos getInstance(){
        return singletonFavoritos;
    }

    public static void setSingletonMercadillo(SingletonFavoritos singletonFavoritos){
        SingletonFavoritos.singletonFavoritos = singletonFavoritos;
    }

    private List<Mercadillo> mercadillo;

    private SingletonFavoritos(){

    }

    public List<Mercadillo> getFavoritos() {
        return mercadillo;
    }

    public void setFavoritos(List<Mercadillo> mercadillo) {
        this.mercadillo = mercadillo;
    }
}
