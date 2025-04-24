package com.bryanjara.proyectotienda.models;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private ArrayList<LineaFactura> itemsFactura;
    private double impuesto;
    private double total;

    public Factura() {
        this.itemsFactura = new ArrayList<LineaFactura>();
        this.impuesto = 0;
        this.total = 0;
    }

    public Factura(ArrayList<LineaFactura> itemsFactura, double impuesto) {
        this.itemsFactura = itemsFactura;
        this.impuesto = impuesto;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        double subtotal = 0;
        for (LineaFactura item : itemsFactura) {
            subtotal += item.getMontoTotal();
        }
        return subtotal + (subtotal * impuesto);
    }

    public List<LineaFactura> getItemFactura() {
        return itemsFactura;
    }

    public void setItemFactura(ArrayList<LineaFactura> itemsFactura) {
        this.itemsFactura = this.itemsFactura;
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
                "itemFactura=" + itemsFactura +
                ", impuesto=" + impuesto +
                ", total=" + total +
                '}';
    }
}
