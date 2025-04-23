package com.bryanjara.proyectotienda.models;

public class LineaFactura {
    private String ID;
    private ItemCarrito itemCarrito;
    private double montoTotal;

    public LineaFactura() {
        this.itemCarrito = new ItemCarrito();
        this.montoTotal = 0;
    }

    public LineaFactura(ItemCarrito itemCarrito, int montoTotal) {
        this.itemCarrito = itemCarrito;
        this.montoTotal = itemCarrito.getCantidad() * itemCarrito.getProducto().getPrecio();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ItemCarrito getItemCarrito() {
        return itemCarrito;
    }

    public void setItemCarrito(ItemCarrito itemCarrito) {
        this.itemCarrito = itemCarrito;
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
                "itemCarrito=" + itemCarrito +
                ", montoTotal=" + montoTotal +
                '}';
    }
}
