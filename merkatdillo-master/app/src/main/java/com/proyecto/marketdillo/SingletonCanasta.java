package com.proyecto.marketdillo;

import java.util.ArrayList;

public class SingletonCanasta {

    private static SingletonCanasta singletonCanasta = new SingletonCanasta();
    public static SingletonCanasta getInstance(){
        return singletonCanasta;
    }

    public static void setSingletonUsuario(SingletonCanasta singletonUsuario){
        SingletonCanasta.singletonCanasta = singletonUsuario;
    }

    private ArrayList<Producto> canastas = new ArrayList<>();

    private SingletonCanasta(){

    }

    public static SingletonCanasta getSingletonCanasta() {
        return singletonCanasta;
    }

    public static void setSingletonCanasta(SingletonCanasta singletonCanasta) {
        SingletonCanasta.singletonCanasta = singletonCanasta;
    }

    public ArrayList<Producto> getCanastas() {
        return canastas;
    }

    public void setCanastas(Producto producto) {
        canastas.add(producto);
    }
}
