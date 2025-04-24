package com.bryanjara.proyectotienda.models;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public abstract class Usuario {
    protected String nombreUsuario;
    protected String nombreCompleto;
    protected String cedulaIdentidad;
    protected String fechaNacimiento; // Formato: "dd/MM/yyyy"
    protected String correoElectronico;
    protected String contrasenia;

    public Usuario() {
        this.nombreUsuario = "";
        this.nombreCompleto = "";
        this.cedulaIdentidad = "";
        this.fechaNacimiento = "";
        this.correoElectronico = "";
        this.contrasenia = "";
    }

    public Usuario(String nombreUsuario, String nombreCompleto, String cedulaIdentidad, String fechaNacimiento, String correoElectronico, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.nombreCompleto = nombreCompleto;
        this.cedulaIdentidad = cedulaIdentidad;
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;

        if (!validarEdad()) {
            throw new IllegalArgumentException("El usuario debe ser mayor de 18 años.");
        }

        if (validarContrasenia(contrasenia)) {
            this.contrasenia = contrasenia;
        } else {
            throw new IllegalArgumentException("La contraseña no cumple con los requisitos de seguridad.");
        }
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCedulaIdentidad() {
        return cedulaIdentidad;
    }

    public void setCedulaIdentidad(String cedulaIdentidad) {
        this.cedulaIdentidad = cedulaIdentidad;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean validarEdad() {
        try {
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formateador);
            LocalDate fechaActual = LocalDate.now();
            return Period.between(fechaNac, fechaActual).getYears() >= 18;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validarContrasenia(String contrasenia) {
        if (contrasenia == null || contrasenia.length() < 8) {
            return false;
        }

        return contrasenia.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$");
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", cedulaIdentidad='" + cedulaIdentidad + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                '}';
    }
}
