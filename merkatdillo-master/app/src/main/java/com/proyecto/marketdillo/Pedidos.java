package com.proyecto.marketdillo;

import java.util.Map;

public class Pedidos {

    private String idCampesino;
    private String idConsumidor;
    private String NombreMercadillo;
    private String direccionEntrega;
    private String estado;
    private Map<String, String> productos;
    private String total;

    public Pedidos() {
    }

    public Pedidos(String idCampesino, String idConsumidor, String nombreMercadillo, String direccionEntrega, String estado, Map<String, String> productos, String total) {
        this.idCampesino = idCampesino;
        this.idConsumidor = idConsumidor;
        NombreMercadillo = nombreMercadillo;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.productos = productos;
        this.total = total;
    }

    public String getIdCampesino() {
        return idCampesino;
    }

    public void setIdCampesino(String idCampesino) {
        this.idCampesino = idCampesino;
    }

    public String getIdConsumidor() {
        return idConsumidor;
    }

    public void setIdConsumidor(String idConsumidor) {
        this.idConsumidor = idConsumidor;
    }

    public String getNombreMercadillo() {
        return NombreMercadillo;
    }

    public void setNombreMercadillo(String nombreMercadillo) {
        NombreMercadillo = nombreMercadillo;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Map<String, String> getProductos() {
        return productos;
    }

    public void setProductos(Map<String, String> productos) {
        this.productos = productos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
