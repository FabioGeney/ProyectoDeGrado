package com.proyecto.marketdillo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;



public class CanastaClass  {

    private String id;
    private int total = 0;
    private int costoDomi;
    private HashMap<String, Producto> productos;

    public CanastaClass(){}

    public CanastaClass(String id, int costoDomi){
        productos = new HashMap<>();
        this.id = id;
        this.costoDomi = costoDomi;
    }
    public Producto getProducto(String key){
        return productos.get(key);
    }

    public String getId() {
        return id;
    }

    public int getCostoDomi() {
        return costoDomi;
    }

    public HashMap<String, Producto> getProductos() {
        return productos;
    }

    public void agregarProducto(String key, Producto producto){
        productos.put(key, producto);
    }

    public void borrarProducto(String key){
        productos.remove(key);
    }

    public void aumentaContador( String key){
        Producto producto = productos.get(key);
        if(producto.getContador() == 0){
            productos.get(key).setContador(1);
        }else {
            productos.get(key).setContador(producto.getContador() + 1);
        }

    }

    public void restaContador( String key){
        Producto producto = productos.get(key);
        productos.get(key).setContador(producto.getContador() - 1);
    }

    public void borraProducto(String key){
        productos.remove(key);
    }

    public int getTotal(){
        int temp = costoDomi;
        Collection<Producto> tempCollection = productos.values();
        ArrayList<Producto> productosTemp =  new ArrayList<> (tempCollection);
        for(Producto producto : productosTemp){
            temp = temp + producto.getPrecioCantidad()*producto.getContador();
        }
        total = temp;
        return  total;
    }

    public ArrayList<Producto> getList(){
        Collection<Producto> tempCollection = productos.values();
        return  new ArrayList<> (tempCollection);
    }

    public void borraLista(){
        productos = new HashMap<>();
    }
}

