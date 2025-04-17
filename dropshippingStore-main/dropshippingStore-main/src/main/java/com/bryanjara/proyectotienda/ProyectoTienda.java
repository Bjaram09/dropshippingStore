/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.bryanjara.proyectotienda;

import com.bryanjara.proyectotienda.controller.AdministradorController;
import com.bryanjara.proyectotienda.views.RegistrarAdministrador;

/**
 *
 * @author bryan
 */

public class ProyectoTienda {
    public static void main(String[] args) {
        RegistrarAdministrador vista = new RegistrarAdministrador();
        new AdministradorController(vista);
    }
}

