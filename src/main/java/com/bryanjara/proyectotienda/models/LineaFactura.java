package com.bryanjara.proyectotienda.models;

public class LineaFactura {
    private int id;
    private ItemCarrito itemCarrito;
    private double montoTotal;

    public LineaFactura() {
        this.id = 0;
        this.itemCarrito = new ItemCarrito();
        this.montoTotal = 0;
    }

    public LineaFactura(int id, ItemCarrito itemCarrito) {
        this.id = id;
        this.itemCarrito = itemCarrito;
        double precio = itemCarrito.getProducto().getPrecio();
        int cantidad = itemCarrito.getCantidad();
        this.montoTotal = cantidad * precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ItemCarrito getItemCarrito() {
        return itemCarrito;
    }

    public void setItemCarrito(ItemCarrito itemCarrito) {
        this.itemCarrito = itemCarrito;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    // Método opcional para calcular el impuesto de esta línea
    public double calcularImpuesto() {
        return montoTotal * 0.13;
    }

    @Override
    public String toString() {
        double impuestoLinea = calcularImpuesto();
        return """
                LineaFactura
                Id: """ + id +
                "\nItems registrados en el carrito: " + itemCarrito +
                "\nMonto Total: " + String.format("%.2f", montoTotal) +
                "\nImpuesto (13%): " + String.format("%.2f", impuestoLinea) +
                " ";
    }
}