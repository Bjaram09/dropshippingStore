package com.bryanjara.proyectotienda.models;

import java.util.ArrayList;

public class Comprador extends Usuario {
    private ArrayList<ItemCarrito> carritoCompras;
    private ArrayList<Producto> listaFavoritos;

    public Comprador() {
        super();
        this.carritoCompras = new ArrayList<ItemCarrito>();
        this.listaFavoritos = new ArrayList<Producto>();
    }

    public Comprador(String nombreUsuario, String nombreCompleto, String cedulaIdentidad, String fechaNacimiento, String correoElectronico, String contrasenia) {
        super(nombreUsuario, nombreCompleto, cedulaIdentidad, fechaNacimiento, correoElectronico, contrasenia);
        this.carritoCompras = new ArrayList<ItemCarrito>();
        this.listaFavoritos = new ArrayList<Producto>();
    }

    public void agregarAlCarrito(Producto producto, int cantidad) {
        if (producto.disponibleParaVenta() && cantidad <= producto.getInventarioDisponible()) {
            carritoCompras.add(new ItemCarrito(producto, cantidad));
            producto.reducirInventario(cantidad);
        } else {
            throw new IllegalArgumentException("No hay suficiente inventario disponible.");
        }
    }

    public void verCarrito() {
        for (ItemCarrito item : carritoCompras) {
            System.out.println(item.getProducto().getNombre() + " - Cantidad: " + item.getCantidad() + " - Subtotal: " + item.calcularSubtotal());
        }
    }

    public ArrayList<Producto> getListaFavoritos() {
        if (listaFavoritos == null) {
            listaFavoritos = new ArrayList<>();
        }
        return listaFavoritos;
    }
    
    public void agregarFavoritos(Producto producto) {
        if (!getListaFavoritos().contains(producto)) {
            listaFavoritos.add(producto);
        }
    }

    public ArrayList<ItemCarrito> getCarritoCompras() {
        return carritoCompras;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                "carritoCompras=" + carritoCompras +
                ", listaFavoritos=" + listaFavoritos +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", cedulaIdentidad='" + cedulaIdentidad + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}