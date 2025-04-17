/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bryanjara.proyectotienda.models;

import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author bryan
 */
public class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private double peso;
    private String dimensiones;
    private ArrayList<Image> imagenes;
    private String descripcion;
    private int inventarioDisponible;
    private Vendedor vendedor;

    // Constructor vacío
    public Producto() {
        this.nombre = "";
        this.categoria = "";
        this.precio = 0.0;
        this.peso = 0.0;
        this.dimensiones = "";
        this.imagenes = new ArrayList<>();
        this.descripcion = "";
        this.inventarioDisponible = 0;
        this.vendedor = new Vendedor();
    }

    // Constructor con parámetros
    public Producto(String nombre, String categoria, double precio, double peso, String dimensiones,
                     ArrayList<Image> imagenes, String descripcion, int inventarioDisponible, Vendedor vendedor) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.peso = peso;
        this.dimensiones = dimensiones;
        this.imagenes = imagenes;
        this.descripcion = descripcion;
        this.inventarioDisponible = inventarioDisponible;
        this.vendedor = vendedor;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    public ArrayList<Image> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<Image> imagenes) {
        this.imagenes = imagenes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getInventarioDisponible() {
        return inventarioDisponible;
    }

    public void setInventarioDisponible(int inventarioDisponible) {
        this.inventarioDisponible = inventarioDisponible;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public boolean disponibleParaVenta() {
        return inventarioDisponible > 0;
    }

    public void reducirInventario(int cantidad) {
        if (cantidad <= inventarioDisponible) {
            inventarioDisponible -= cantidad;
        } else {
            throw new IllegalArgumentException("Cantidad mayor a la disponible en inventario.");
        }
    }

    // Metodo toString
    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", peso=" + peso +
                ", dimensiones='" + dimensiones + '\'' +
                ", imagenes=" + imagenes +
                ", descripcion='" + descripcion + '\'' +
                ", inventarioDisponible=" + inventarioDisponible +
                ", vendedor=" + vendedor +
                '}';
    }
}
