package com.bryanjara.proyectotienda;

import com.bryanjara.proyectotienda.controllers.RegistrationController;
import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.views.RegistrationView;

import java.sql.SQLException;

public class ProyectoTienda {
    public static void main(String[] args) {
        try {
            RegistrationView view = new RegistrationView();
            new RegistrationController(view);
        } catch (GlobalException | SQLException | NoDataException e) {
            e.printStackTrace();
        }
    }
}
