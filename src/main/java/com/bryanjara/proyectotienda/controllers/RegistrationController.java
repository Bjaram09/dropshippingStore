package com.bryanjara.proyectotienda.controllers;

import com.bryanjara.proyectotienda.dataaccess.AdministradorDAO;
import com.bryanjara.proyectotienda.dataaccess.CompradorDAO;
import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.models.Administrador;
import com.bryanjara.proyectotienda.models.Comprador;
import com.bryanjara.proyectotienda.models.Usuario;
import com.bryanjara.proyectotienda.views.RegistrationView;
import com.bryanjara.proyectotienda.views.administrador.ViewAdministradorMenu;
import com.bryanjara.proyectotienda.views.catalogo.ViewCatalogo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class RegistrationController {

    private RegistrationView view;
    private AdministradorDAO administradorDAO;
    private CompradorDAO compradorDAO;

    public RegistrationController(RegistrationView view) throws GlobalException, SQLException, NoDataException {
        this.view = view;
        this.administradorDAO = new AdministradorDAO();
        this.compradorDAO = new CompradorDAO();
        boolean adminExists = false;

        // Check if an admin exists
        try {
            adminExists = !administradorDAO.listarAdministrador().isEmpty();
        } catch (NoDataException ex) {
            throw new NoDataException("No hay administradores en nuestra base de datos" + ex);
        }

        view.showRegistrationForm(!adminExists);
        view.showLoginButtons(adminExists);

        view.addRegisterListener(new RegisterListener());
        view.addLoginAdminListener(new LoginAdminListener());
        view.addRegisterCompradorListener(new RegisterCompradorListener());
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
                view.setMessage("Por favor, llena toda la informacion");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.setMessage("Las contraseñas no son iguales.");
                return;
            }

            Usuario user = new Administrador();
            if (!user.validarContrasenia(password)) {
                view.setMessage("La contraseña no cumple con los requerimientos minimos de seguridad");
                return;
            }

            System.out.println("RegistrationController: line 69 - " + birthDate);

            Administrador newAdmin = new Administrador(username, fullName, id, birthDate, email, password);

            try {
                administradorDAO.insertarAdministrador(newAdmin);
            } catch (GlobalException | NoDataException | SQLException ex) {
                view.setMessage("Error al registrar administrador: " + ex.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(null, "Registro exitoso!");
            view.dispose();

            try {
                new RegistrationController(new RegistrationView());
            } catch (GlobalException | SQLException | NoDataException ex) {
                ex.printStackTrace();
            }
        }
    }

    class RegisterCompradorListener implements ActionListener {
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
                view.setMessage("Por favor, llena toda la informacion");
                return;
            }

            if (!password.equals(confirmPassword)) {
                view.setMessage("Las contraseñas no son iguales.");
                return;
            }

            Usuario user = new Comprador();
            if (!user.validarContrasenia(password)) {
                view.setMessage("La contraseña no cumple con los requerimientos minimos de seguridad");
                return;
            }

            System.out.println("RegistrationController: line 123 - " + birthDate);

            Comprador newComprador = new Comprador(username, fullName, id, birthDate, email, password);

            try {
                compradorDAO.insertarComprador(newComprador);
            } catch (GlobalException | NoDataException | SQLException ex) {
                view.setMessage("Error al registrar comprador: " + ex.getMessage());
                return;
            }

            JOptionPane.showMessageDialog(null, "Registro exitoso!");
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
                JOptionPane.showMessageDialog(null, "Error buscando un administrador: " + ex.getMessage());
                return;
            } catch (NoDataException ex) {
                JOptionPane.showMessageDialog(null, "Usuario incorrecto");
                return;
            }

            if (admin != null && admin.getContrasenia().equals(password)) {
                JOptionPane.showMessageDialog(null, "Logueo exitoso!");
                openAdminMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectas");
            }
        }
    }

    class LoginCompradorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            Comprador comprador = null;

            try {
                comprador = compradorDAO.buscarCompradorPorNombreUsuario(username);
            } catch (GlobalException | SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error buscando un comprador: " + ex.getMessage());
                System.out.println(ex);
                return;
            } catch (NoDataException ex) {
                JOptionPane.showMessageDialog(null, "Usuario incorrecto");
                return;
            }

            if (comprador != null && comprador.getContrasenia().equals(password)) {
                JOptionPane.showMessageDialog(null, "Logueo exitoso!");
                openCatalogo(comprador);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectas");
            }
        }
    }

    private void openAdminMenu() {
        ViewAdministradorMenu adminMenu = new ViewAdministradorMenu();
        adminMenu.setVisible(true);
        view.dispose();
    }

    private void openCatalogo(Comprador comprador) {
        ViewCatalogo catalogoMenu = new ViewCatalogo();
        CatalogoController catalogoController = new CatalogoController(catalogoMenu, comprador);
        catalogoController.iniciar();
        catalogoMenu.setVisible(true);
        view.dispose();
    }
}
