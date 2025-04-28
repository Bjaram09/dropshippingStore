package com.bryanjara.proyectotienda.models;

import java.util.ArrayList;
import java.util.List;

public class Factura {
    private int id;
    private ArrayList<LineaFactura> itemsFactura;
    private double impuesto;
    private double total;

    public Factura() {
        this.id = 0;
        this.itemsFactura = new ArrayList<LineaFactura>();
        this.impuesto = 0;
        this.total = 0;
    }

    public Factura(ArrayList<LineaFactura> itemsFactura, double impuesto) {
        this.id = 0;
        this.itemsFactura = itemsFactura;
        this.impuesto = impuesto;
        this.total = calcularTotal();
    }

    private double calcularTotal() {
        double subtotal = 0;
        for (LineaFactura item : itemsFactura) {
            subtotal += item.getMontoTotal();
        }
        this.impuesto = subtotal * 0.13;
        return subtotal + impuesto;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
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
        return """
                Factura
                Items de la Factura: """ + itemsFactura
                + "\nTotal Impuesto: " + impuesto
                + "\nTotal: " + total;
    }
}
