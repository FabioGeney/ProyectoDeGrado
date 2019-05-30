package com.proyecto.marketdillo;

import java.util.ArrayList;
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

    private SingletonCanasta(){

    }

    public static SingletonCanasta getSingletonCanasta() {
        return singletonCanasta;
    }

    public static void setSingletonSingletonCanasta(SingletonCanasta singletonCanasta) {
        SingletonCanasta.singletonCanasta = singletonCanasta;
    }

    public ArrayList<Producto> getCanastas() {
        return canastas;
    }

    public void setCanastas(Producto producto) {
        canastas.add(producto);
    }

    public void setCanidad(Producto producto){
        Iterator<Producto> it = canastas.iterator();
        while (it.hasNext()){
            Producto producto1 = it.next();
            if(producto.getIdDocument().equals(producto1.getIdDocument())){
                canastas.remove(producto1);
                canastas.add(producto);
            }

        }

    }

    public void setContador(Producto producto){
        Iterator<Producto> it = canastas.iterator();
        while (it.hasNext()){
            Producto producto1 = it.next();
            if(producto.getIdDocument().equals(producto1.getIdDocument())){
                canastas.remove(producto1);
                canastas.add(producto);
            }

        }

    }
    public void setProductosCanasta(List<Producto> canastas){
        for(Producto producto: canastas){
            this.canastas.add(producto);
        }

    }
}
