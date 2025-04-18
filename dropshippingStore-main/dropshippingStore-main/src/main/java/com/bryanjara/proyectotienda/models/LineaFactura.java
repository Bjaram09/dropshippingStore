package com.bryanjara.proyectotienda.models;

public class LineaFactura {
    private Producto producto;
    private int cantidad;
    private double precioIndividual;
    private double montoTotal;

    public LineaFactura() {
        this.producto = new Producto();
        this.cantidad = 0;
        this.precioIndividual = 0;
        this.montoTotal = 0;
    }

    public LineaFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioIndividual = producto.getPrecio();
        this.montoTotal = cantidad * precioIndividual;
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

    public double getPrecioIndividual() {
        return precioIndividual;
    }

    public void setPrecioIndividual(double precioIndividual) {
        this.precioIndividual = precioIndividual;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    @Override
    public String toString() {
        return "LineaFactura{" +
                "producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioIndividual=" + precioIndividual +
                ", montoTotal=" + montoTotal +
                '}';
    }
}
