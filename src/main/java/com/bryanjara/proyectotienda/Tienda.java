package com.bryanjara.proyectotienda;


import java.util.ArrayList;

public class Tienda {
    private ArrayList<Producto> catalogo;
    private boolean existeAdministrador;

    public Tienda() {
        this.catalogo = new ArrayList<>();
        this.existeAdministrador = false;
    }

    public Tienda(ArrayList<Producto> catalogo, boolean existeAdministrador) {
        this.catalogo = catalogo;
        this.existeAdministrador = existeAdministrador;
    }

    public ArrayList<Producto> getCatalogo() {
        return catalogo;
    }

    public void agregarProducto(Producto producto) {
        catalogo.add(producto);
    }

    public boolean getExisteAdministrador() {
        return existeAdministrador;
    }

    public void setExisteAdministrador(boolean existeAdministrador) {
        this.existeAdministrador = existeAdministrador;
    }

    @Override
    public String toString() {
        return "Tienda{" +
                "catalogo=" + catalogo +
                ", existeAdministrador=" + existeAdministrador +
                '}';
    }
}
