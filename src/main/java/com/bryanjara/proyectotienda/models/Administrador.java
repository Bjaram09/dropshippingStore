package com.bryanjara.proyectotienda.models;

import java.util.ArrayList;

public class Administrador extends Usuario {
    private static Administrador instance;
    private ArrayList<Vendedor> vendedores;

    public Administrador(){
        super();
    }

    public Administrador(String nombreUsuario, String nombreCompleto, String cedulaIdentidad, String fechaNacimiento, String correoElectronico, String contrasenia) {
        super(nombreUsuario, nombreCompleto, cedulaIdentidad, fechaNacimiento, correoElectronico, contrasenia);
        vendedores = new ArrayList<Vendedor>();
    }

    public static Administrador getInstance() {
        return instance;
    }

    public static void setInstance(Administrador instance) {
        Administrador.instance = instance;
    }

    public ArrayList<Vendedor> getVendedores() {
        return vendedores;
    }

    public void setVendedores(ArrayList<Vendedor> vendedores) {
        this.vendedores = vendedores;
    }

    //Singleton, patron de diseño mencionado por el profe en la clase de la semana 12
    public static Administrador obtenerInstancia(String nombreUsuario, String nombreCompleto, String cedulaIdentidad, String fechaNacimiento, String correoElectronico, String contrasenia) {
        if (instance == null) {
            instance = new Administrador(nombreUsuario, nombreCompleto, cedulaIdentidad, fechaNacimiento, correoElectronico, contrasenia);
        }
        return instance;
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