package com.bryanjara.proyectotienda.models;

public class ItemCarrito {
    private Producto producto;
    private int cantidad;

    public ItemCarrito() {
        this.producto = new Producto();
        this.cantidad = 0;
    }

    public ItemCarrito(Producto producto, int cantidad) {
        if (cantidad <= 0 || cantidad > producto.getInventarioDisponible()) {
            throw new IllegalArgumentException("Cantidad inválida.");
        }
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double calcularSubtotal() {
        return cantidad * producto.getPrecio();
    }
}
