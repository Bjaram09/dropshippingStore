package com.bryanjara.proyectotienda.models;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private List<LineaFactura> itemFactura;
    private double impuesto;
    private double total;

    public Factura() {
        this.itemFactura = new ArrayList<LineaFactura>();
        this.impuesto = 0;
        this.total = 0;
    }

    public Factura(List<LineaFactura> itemFactura, double impuesto) {
        this.itemFactura = itemFactura;
        this.impuesto = impuesto;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        double subtotal = 0;
        for (LineaFactura item : itemFactura) {
            subtotal += item.getMontoTotal();
        }
        return subtotal + (subtotal * impuesto);
    }

    public List<LineaFactura> getItemFactura() {
        return itemFactura;
    }

    public void setItemFactura(List<LineaFactura> itemFactura) {
        this.itemFactura = itemFactura;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "itemFactura=" + itemFactura +
                ", impuesto=" + impuesto +
                ", total=" + total +
                '}';
    }
}
