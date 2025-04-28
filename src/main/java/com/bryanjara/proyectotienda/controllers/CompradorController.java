package com.bryanjara.proyectotienda.controllers;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.dataaccess.CompradorDAO;
import com.bryanjara.proyectotienda.models.Comprador;
import com.bryanjara.proyectotienda.views.comprador.ViewRegistrarComprador;
import com.bryanjara.proyectotienda.views.comprador.ViewComprador;

public class CompradorController {
    private final Comprador comprador;
    private final CompradorDAO compradorDAO;
    private ViewComprador viewComprador;

    public CompradorController(ViewComprador vista) {
        this.comprador = new Comprador();
        this.compradorDAO = new CompradorDAO();
        this.viewComprador = vista;
    }

    public void iniciar() {
        cargarCompradores();
        configurarListeners();
    }

    private void configurarListeners() {
        viewComprador.getBtnRegistrarComprador().addActionListener(e -> abrirVentanaRegistro(null));
        viewComprador.getBtnBuscarComprador().addActionListener(this::buscarComprador);

        viewComprador.setDeleteListener(this::manejarEliminacion);

        viewComprador.setUpdateListener(this::manejarActualizacion);
    }

    private void abrirVentanaRegistro(Comprador compradorExistente) {
        ViewRegistrarComprador registrarComprador = new ViewRegistrarComprador();
        boolean esActualizacion = compradorExistente != null;

        if (esActualizacion) {
            registrarComprador.setTitle("Actualizar Comprador");
            registrarComprador.getBtnRegistrar().setText("Actualizar");
            registrarComprador.getTxtCedula().setEnabled(false);
            poblarVistaDesdeCompradorAActualizar(registrarComprador, compradorExistente);
        }

        registrarComprador.addUpdateListener(ev -> manejarRegistroActualizacion(registrarComprador, esActualizacion));
    }

    private void buscarComprador(ActionEvent e) {
        String criterio = viewComprador.getTxtBuscarComprador().getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(viewComprador,
                    "Por favor, ingresa un criterio de búsqueda.",
                    "Búsqueda",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        try {
            Collection<Comprador> compradoresEncontrados = compradorDAO.buscarCompradorPorCriterio(criterio);
            if (compradoresEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(viewComprador,
                        "No se encontraron compradores que coincidan con el criterio de búsqueda.",
                        "Búsqueda",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                actualizarTabla(compradoresEncontrados);
            }
        } catch (GlobalException | NoDataException | SQLException ex) {
            mostrarError("Error al buscar compradores: " + ex.getMessage());
        }
    }
    
    private void actualizarTabla(Collection<Comprador> compradores) {
        DefaultTableModel modeloTabla = viewComprador.getModeloTabla();
        modeloTabla.setRowCount(0);
        for (Comprador comprador : compradores) {
            Object[] row = {
                    comprador.getCedulaIdentidad(),
                    comprador.getNombreCompleto(),
                    comprador.getNombreUsuario(),
                    comprador.getCorreoElectronico(),
                    comprador.getFechaNacimiento(), 
                    comprador
            };
            modeloTabla.addRow(row);
        }
    }

    private void poblarVistaDesdeCompradorAActualizar(ViewRegistrarComprador vista, Comprador comprador) {
        System.out.println("ID: " + comprador.getCedulaIdentidad());
        System.out.println("Nombre Completo: " + comprador.getNombreCompleto());
        System.out.println("Nombre de Usuario: " + comprador.getNombreUsuario());
        
        vista.setId(comprador.getCedulaIdentidad());
        vista.setFullName(comprador.getNombreCompleto());
        vista.setUsername(comprador.getNombreUsuario());
        vista.setPassword(comprador.getContrasenia());
        vista.setConfirmPassword(comprador.getContrasenia());
        vista.setEmail(comprador.getCorreoElectronico());
        
        String[] fechaParts = comprador.getFechaNacimiento().split("/");
        int day = Integer.parseInt(fechaParts[0]);
        int month = Integer.parseInt(fechaParts[1]);
        int year = Integer.parseInt(fechaParts[2]); 
    
        vista.setBirthDate(day, month, year);
    }

    private void manejarRegistroActualizacion(ViewRegistrarComprador vista, boolean esActualizacion) {
        Comprador compradorTemp = new Comprador();
        registrarCompradorDesdeVista(compradorTemp, vista);

        try {
            if (esActualizacion) {
                compradorDAO.modificarComprador(compradorTemp);
            } else {
                compradorDAO.insertarComprador(compradorTemp);
            }

            JOptionPane.showMessageDialog(null, "Operación exitosa!");
            vista.dispose();
            cargarCompradores();
        } catch (GlobalException | NoDataException | SQLException ex) {
            mostrarError("Error durante la operación: " + ex.getMessage());
        }
    }

    private void registrarCompradorDesdeVista(Comprador comprador, ViewRegistrarComprador vista) {
        comprador.setCedulaIdentidad(vista.getId());
        comprador.setNombreCompleto(vista.getFullName());
        comprador.setNombreUsuario(vista.getUsername());
        comprador.setContrasenia(vista.getPassword());
        comprador.setCorreoElectronico(vista.getEmail());
        comprador.setFechaNacimiento(vista.getBirthDate());
    }

    private void manejarEliminacion(ActionEvent e) {
        if (e.getSource() instanceof ViewComprador.ActionsEditor) {
            Comprador compradorAEliminar = ((ViewComprador.ActionsEditor) e.getSource()).getComprador();
            if (compradorAEliminar != null) {
                eliminarComprador(compradorAEliminar);
            }
        }
    }

    private void manejarActualizacion(ActionEvent e) {
        if (e.getSource() instanceof ViewComprador.ActionsEditor) {
            Comprador compradorAActualizar = ((ViewComprador.ActionsEditor) e.getSource()).getComprador();
            if (compradorAActualizar != null) {
                abrirVentanaRegistro(compradorAActualizar);
            }
        }
    }

    private void cargarCompradores() {
        try {
            Collection<Comprador> compradores = compradorDAO.listarComprador();
            viewComprador.listarCompradores(compradores);
        } catch (GlobalException | NoDataException | SQLException e) {
            mostrarError("Error al cargar compradores: " + e.getMessage());
        }
    }

    private void eliminarComprador(Comprador comprador) {
        int confirmacion = JOptionPane.showConfirmDialog(
            viewComprador,
            "¿Está seguro que desea eliminar al comprador " + comprador.getCedulaIdentidad() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Intentando eliminar comprador con ID: " + comprador.getCedulaIdentidad());
                compradorDAO.eliminarComprador(comprador.getCedulaIdentidad());
               
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) viewComprador.getTablaCompradores().getModel();
                    int rowCount = model.getRowCount();
                    if (rowCount > 0) {
                        cargarCompradores();
                    } else {
                        model.setRowCount(0);
                    }
                });
            } catch (SQLException | GlobalException | NoDataException ex) {
                mostrarError("Error al eliminar comprador: " + ex.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(viewComprador, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
