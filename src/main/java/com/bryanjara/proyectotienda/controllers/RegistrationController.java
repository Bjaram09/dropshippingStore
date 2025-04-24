package com.bryanjara.proyectotienda.controllers;

import com.bryanjara.proyectotienda.dataaccess.AdministradorDAO;
import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.models.Administrador;
import com.bryanjara.proyectotienda.models.Usuario;
import com.bryanjara.proyectotienda.views.RegistrationView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegistrationController {

    private RegistrationView view;
    private AdministradorDAO administradorDAO;

    public RegistrationController(RegistrationView view) throws GlobalException, SQLException, NoDataException {
        this.view = view;
        this.administradorDAO = new AdministradorDAO();
        boolean adminExists = false;

        // Check if an admin exists
        try {
            adminExists = !administradorDAO.listarAdministrador().isEmpty();
        } catch (NoDataException ex) {
            throw new NoDataException("No data found while checking for administrators." + ex);
        }

        view.showRegistrationForm(!adminExists);
        view.showLoginButtons(adminExists);

        view.addRegisterListener(new RegisterListener());
        view.addLoginAdminListener(new LoginAdminListener());
        view.addLoginCompradorListener(new LoginCompradorListener());
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();
            String confirmPassword = view.getConfirmPassword();
            String fullName = view.getFullName();
            String id = view.getId();
            String birthDate = view.getBirthDate();
            String email = view.getEmail();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullName.isEmpty() || id.isEmpty() || email.isEmpty()) {
                view.setMessage("Please enter all the information.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.setMessage("Passwords do not match.");
                return;
            }

            Usuario user = new Administrador();
            if (!user.validarContrasenia(password)) {
                view.setMessage("Password does not meet security requirements.");
                return;
            }

            Administrador newAdmin = new Administrador(username, fullName, id, birthDate, email, password);

            try {
                administradorDAO.insertarAdministrador(newAdmin);
            } catch (GlobalException | NoDataException | SQLException ex) {
                view.setMessage("Error registering administrator: " + ex.getMessage());
                return;
            }
            view.setMessage("Administrator registered successfully.");
            view.dispose();
            try {
                new RegistrationController(new RegistrationView());
            } catch (GlobalException | SQLException | NoDataException ex) {
                ex.printStackTrace();
            }
        }
    }

    class LoginAdminListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            Administrador admin = null;

            try {
                admin = administradorDAO.buscarAdministrador(username);
            } catch (GlobalException | SQLException ex) {
                view.setMessage("Error occurred while searching for administrator: " + ex.getMessage());
                return;
            } catch (NoDataException ex) {
                view.setMessage("Invalid username or password.");
                return;
            }

            if (admin != null && admin.getContrasenia().equals(password)) {
                view.setMessage("Administrator login successful.");
                // TODO: Redirect to administrator view
            } else {
                view.setMessage("Invalid username or password.");
            }
        }
    }

    class LoginCompradorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setMessage("Comprador login not yet implemented.");
            // TODO: Implement comprador login
        }
    }
}
