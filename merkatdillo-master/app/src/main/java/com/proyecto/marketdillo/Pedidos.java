package com.proyecto.marketdillo;


import java.util.ArrayList;
import java.util.List;


public class Pedidos {

    private String idCampesino;
    private String idConsumidor;
    private String NombreMercadillo;
    private String direccionEntrega;
    private String estado;
    private List<Producto> productos;
    private String total;
    private String fecha;

    public Pedidos() {
    }

    public Pedidos(String idCampesino, String idConsumidor, String nombreMercadillo, String direccionEntrega, String estado, List<Producto> productos, String total, String fecha) {
        this.idCampesino = idCampesino;
        this.idConsumidor = idConsumidor;
        NombreMercadillo = nombreMercadillo;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.productos = productos;
        this.total = total;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
