package com.proyecto.marketdillo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PedidosToRealm extends RealmObject {
    @PrimaryKey
    String idPedido;

    public PedidosToRealm() {
    }

    public PedidosToRealm(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }
}
