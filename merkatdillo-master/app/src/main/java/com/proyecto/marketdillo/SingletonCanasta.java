package com.proyecto.marketdillo;

import java.util.ArrayList;
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
        if(canastas.size() != 0){
            for(Producto producto1 : canastas){
                if(producto.getIdDocument().equals(producto1.getIdDocument())){
                    producto.setContador(producto.getContador()+1);
                    canastas.add(producto);
                    canastas.remove(producto1);
                }else {
                    canastas.add(producto);
                }
            }
        }else {
            canastas.add(producto);
        }

    }

    public void setContador(Producto producto){
       for(Producto producto1:canastas){
          if(producto.getIdDocument().equals(producto1.getIdDocument())){
            producto.setContador(producto1.getContador()+1);
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
