/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.bryanjara.proyectotienda;

import com.bryanjara.proyectotienda.controllers.AdministradorController;
import com.bryanjara.proyectotienda.views.ViewRegistrarAdministrador;

/**
 *
 * @author bryan
 */
public class ProyectoTienda {

    public static void main(String[] args) {
        ViewRegistrarAdministrador vista = new ViewRegistrarAdministrador();
        new AdministradorController(vista);
    }
}
