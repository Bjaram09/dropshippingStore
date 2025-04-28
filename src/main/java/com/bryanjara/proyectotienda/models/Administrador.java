package com.bryanjara.proyectotienda.models;

import java.util.ArrayList;

public class Administrador extends Usuario {
    private ArrayList<Vendedor> vendedores;

    public Administrador(){
        super();
    }

    public Administrador(String nombreUsuario, String nombreCompleto, String cedulaIdentidad, String fechaNacimiento, String correoElectronico, String contrasenia) {
        super(nombreUsuario, nombreCompleto, cedulaIdentidad, fechaNacimiento, correoElectronico, contrasenia);
        vendedores = new ArrayList<Vendedor>();
    }

    public ArrayList<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    public void registrarVendedor(Vendedor vendedor) {
        vendedores.add(vendedor);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "vendedores=" + vendedores +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", cedulaIdentidad='" + cedulaIdentidad + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}