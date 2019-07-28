package com.proyecto.marketdillo;


import java.util.ArrayList;
import java.util.List;

public class SingletonHistorial {
    private static SingletonHistorial singletonHistorial = new SingletonHistorial();
    public static SingletonHistorial getInstance(){
        return singletonHistorial;
    }

    public static void setSingletonHistorialo(SingletonHistorial singletonUsuario){
        SingletonHistorial.singletonHistorial = singletonUsuario;
    }

    private ArrayList<Producto> productos = new ArrayList<>();

    private SingletonHistorial(){

    }

    public void setHistorial(List<Producto> productosTemp){
        for (Producto producto : productosTemp ){
            productos.add(producto);
        }
     }
    public ArrayList<Producto> getHistorial(){
        return productos;
    }

}