package com.proyecto.marketdillo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Pedidos implements Serializable {

    private String idDocument;
    private String idCampesino;
    private String idConsumidor;
    private String nombreMercadillo;
    private String nombreComprador;
    private String direccionEntrega;
    private String estado;
    private List<Producto> productos;
    private String total;
    private String fecha;
    private double calificacion;

    public Pedidos() {
    }

    public Pedidos(String idCampesino, String idConsumidor,String nombreComprador, String nombreMercadillo, String direccionEntrega, String estado, List<Producto> productos, String total, String fecha) {
        this.idCampesino = idCampesino;
        this.idConsumidor = idConsumidor;
        this.nombreMercadillo = nombreMercadillo;
        this.nombreComprador = nombreComprador;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.productos = productos;
        this.total = total;
        this.fecha = fecha;
    }

    public Pedidos(String idCampesino, String idConsumidor, String nombreMercadillo, String nombreComprador, String direccionEntrega, String estado, List<Producto> productos, String total, String fecha, double calificacion) {
        this.idCampesino = idCampesino;
        this.idConsumidor = idConsumidor;
        this.nombreMercadillo = nombreMercadillo;
        this.nombreComprador = nombreComprador;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.productos = productos;
        this.total = total;
        this.fecha = fecha;
        this.calificacion = calificacion;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public String getIdDocument() {
        return idDocument;
    }

    public void setIdDocument(String idDocument) {
        this.idDocument = idDocument;
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
        return nombreMercadillo;
    }

    public void setNombreMercadillo(String nombreMercadillo) {
        this.nombreMercadillo = nombreMercadillo;
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
