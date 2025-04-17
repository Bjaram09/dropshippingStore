/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bryanjara.proyectotienda.models;

/**
 *
 * @author bryan
 */
public class Vendedor {
    private String nombre;
    private String ubicacion;
    private String correoContacto;
    private String numeroTelefono;

    public Vendedor() {
        this.nombre = "";
        this.ubicacion = "";
        this.correoContacto = "";
        this.numeroTelefono = "";
    }

    public Vendedor(String nombre, String ubicacion, String correoContacto, String numeroTelefono) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.correoContacto = correoContacto;
        this.numeroTelefono = numeroTelefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    @Override
    public String toString() {
        return "Vendedor{" +
                "nombre='" + nombre + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", correoContacto='" + correoContacto + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}
