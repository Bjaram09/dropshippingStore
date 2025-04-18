package com.bryanjara.proyectotienda.controller;

import com.bryanjara.proyectotienda.models.Administrador;
import com.bryanjara.proyectotienda.views.RegistrarAdministrador;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdministradorController {
    private RegistrarAdministrador vista;

    public AdministradorController(RegistrarAdministrador vista) {
        this.vista = vista;

        this.vista.addRegistrarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombreUsuario = vista.getNombreUsuario();
                String nombreCompleto = vista.getNombreCompleto();
                String cedula = vista.getCedula();
                String fechaNacimiento = vista.getFechaNacimiento(); // formato dd-MM-yyyy
                String correo = vista.getCorreo();
                String contrasenia = vista.getContrasenia();

                // Validación previa de contraseña
                if (!contrasenia.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")) {
                    JOptionPane.showMessageDialog(null,
                            "La contraseña debe tener al menos:\n- 8 caracteres\n- Una letra mayúscula\n- Una letra minúscula\n- Un número\n- Un carácter especial",
                            "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    Administrador admin = Administrador.obtenerInstancia(
                            nombreUsuario,
                            nombreCompleto,
                            cedula,
                            fechaNacimiento,
                            correo,
                            contrasenia
                    );

                    JOptionPane.showMessageDialog(null, "Administrador registrado:\n" + admin);
                    vista.limpiarCampos();

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace(); // para debug
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.vista.addCancelarListener(e -> vista.dispose());
    }
}
