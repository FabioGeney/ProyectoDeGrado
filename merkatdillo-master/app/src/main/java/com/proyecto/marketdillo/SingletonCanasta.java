package com.proyecto.marketdillo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SingletonCanasta {

    private static SingletonCanasta singletonCanasta = new SingletonCanasta();
    public static SingletonCanasta getInstance(){
        return singletonCanasta;
    }

    public static void setSingletonCanasta(SingletonCanasta singletonUsuario){
        SingletonCanasta.singletonCanasta = singletonUsuario;
    }

    private ArrayList<Producto> canastas = new ArrayList<>();
    private HashMap<String, Producto> productos = new HashMap<>();
    private ArrayList<Producto> historial = new ArrayList<>();

    private SingletonCanasta(){

    }

    public static SingletonCanasta getSingletonCanasta() {
        return singletonCanasta;
    }

    public static void setSingletonSingletonCanasta(SingletonCanasta singletonCanasta) {
        SingletonCanasta.singletonCanasta = singletonCanasta;
    }

    public ArrayList<Producto> getCanastas() {
        if(canastas.size()==0){
            Collection<Producto> temp = productos.values();
            return new ArrayList<> (temp);
        }else{
            return canastas;
        }

    }

    public void setHistorial(List<Producto> productos){

        for(Producto temp: productos){
            historial.add(temp);
        }
    }

    public ArrayList<Producto> getHistorial(){
        return historial;
    }

    public Producto getProducto(String key){
        return productos.get(key);
    }

    public void setMap(String key){
        productos.remove(key);
    }

    public void setCanastas(String index, Producto producto) {
        productos.put( index, producto);
    }

    public void setCantidad(Producto producto){
       for(Producto producto1: canastas){
           if(producto.getIdDocument().equals(producto1.getIdDocument())){
               canastas.remove(producto1);
               canastas.add(producto);
           }
       }

    }

    public void borrarProducto(int index){
        productos.remove(index);
    }

    public void borrarLista(){
        canastas = new ArrayList<>();
        productos = new HashMap<>();
    }

    public void setProductosCanasta(List<Producto> canastas){
        for(Producto producto: canastas){
            this.canastas.add(producto);
        }
    }
}
