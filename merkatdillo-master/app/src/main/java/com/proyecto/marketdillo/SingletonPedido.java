package com.proyecto.marketdillo;

public class SingletonPedido {

    private static SingletonPedido singletonPedido = new SingletonPedido();
    public static SingletonPedido getInstance(){
        return singletonPedido;
    }

    public static void setSingletonPedido(SingletonPedido singletonPedido){
        SingletonPedido.singletonPedido = singletonPedido;
    }
    private Pedidos pedido;

    private SingletonPedido(){

    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }
}
