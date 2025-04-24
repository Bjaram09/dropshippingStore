package com.bryanjara.proyectotienda.models;

public class ItemCarrito {
    private int id;
    private Producto producto;
    private int cantidad;

    public ItemCarrito() {
        this.id = 0;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularSubtotal() {
        return cantidad * producto.getPrecio();
    }

    @Override
    public String toString() {
        return producto.getNombre();
    }
}
