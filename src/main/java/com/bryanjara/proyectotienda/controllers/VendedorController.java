package com.bryanjara.proyectotienda.controllers;

import java.sql.SQLException;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;

import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.dataaccess.VendedorDAO;
import com.bryanjara.proyectotienda.models.Vendedor;
import com.bryanjara.proyectotienda.views.vendedor.ViewRegistrarVendedor;
import com.bryanjara.proyectotienda.views.vendedor.ViewVendedor;

public class VendedorController {
    private final Vendedor vendedor;
    private final VendedorDAO vendedorDAO;
    private ViewVendedor viewVendedor;

    public VendedorController(ViewVendedor vista) {
        this.vendedor = new Vendedor();
        this.vendedorDAO = new VendedorDAO();
        this.viewVendedor = vista;
    }

    public void iniciar() {
        cargarVendedores();
        configurarListeners();
    }

    private void configurarListeners() {
        // Listener para registrar nuevo vendedor
        viewVendedor.getBtnRegistrarVendedor().addActionListener(e -> abrirVentanaRegistro(null));

        // Listener para eliminar
        viewVendedor.setDeleteListener(this::manejarEliminacion);

        // Listener para actualizar
        viewVendedor.setUpdateListener(this::manejarActualizacion);
    }

    private void abrirVentanaRegistro(Vendedor vendedorExistente) {
        ViewRegistrarVendedor registrarVendedor = new ViewRegistrarVendedor();
        boolean esActualizacion = vendedorExistente != null;

        if (esActualizacion) {
            registrarVendedor.setTitle("Actualizar Vendedor");
            registrarVendedor.getBtnRegistrar().setText("Actualizar");
            registrarVendedor.getTxtCedula().setEnabled(false);
            poblarVistaDesdeVendedorAActualizar(registrarVendedor, vendedorExistente);
        }

        registrarVendedor.addUpdateListener(ev -> manejarRegistroActualizacion(registrarVendedor, esActualizacion));
    }

    private void poblarVistaDesdeVendedorAActualizar(ViewRegistrarVendedor vista, Vendedor vendedor) {
        vista.setCedula(vendedor.getCedula());
        vista.setNombre(vendedor.getNombre());
        vista.setUbicacion(vendedor.getUbicacion());
        vista.setCorreoContacto(vendedor.getCorreoContacto());
        vista.setNumeroTelefono(vendedor.getNumeroTelefono());
    }

    private void manejarRegistroActualizacion(ViewRegistrarVendedor vista, boolean esActualizacion) {
        Vendedor vendedorTemp = new Vendedor();
        registrarVendedorDesdeVista(vendedorTemp, vista);

        try {
            if (esActualizacion) {
                vendedorDAO.modificarVendedor(vendedorTemp);
            } else {
                vendedorDAO.insertarVendedor(vendedorTemp);
            }

            JOptionPane.showMessageDialog(null, "Operación exitosa!");
            vista.dispose();
            cargarVendedores();
        } catch (GlobalException | NoDataException | SQLException ex) {
            mostrarError("Error durante la operación: " + ex.getMessage());
        }
    }

    private void registrarVendedorDesdeVista(Vendedor vendedor, ViewRegistrarVendedor vista) {
        vendedor.setCedula(vista.getCedula());
        vendedor.setNombre(vista.getNombre());
        vendedor.setUbicacion(vista.getUbicacion());
        vendedor.setCorreoContacto(vista.getCorreoContacto());
        vendedor.setNumeroTelefono(vista.getNumeroTelefono());
    }

    private void manejarEliminacion(ActionEvent e) {
        if (e.getSource() instanceof ViewVendedor.ActionsEditor) {
            Vendedor vendedorAEliminar = ((ViewVendedor.ActionsEditor) e.getSource()).getVendedor();
            if (vendedorAEliminar != null) {
                eliminarVendedor(vendedorAEliminar);
            }
        }
    }

    private void manejarActualizacion(ActionEvent e) {
        if (e.getSource() instanceof ViewVendedor.ActionsEditor) {
            Vendedor vendedorAActualizar = ((ViewVendedor.ActionsEditor) e.getSource()).getVendedor();
            if (vendedorAActualizar != null) {
                abrirVentanaRegistro(vendedorAActualizar);
            }
        }
    }

    private void cargarVendedores() {
        try {
            Collection<Vendedor> vendedores = vendedorDAO.listarVendedores();
            viewVendedor.listarVendedores(vendedores);
        } catch (GlobalException | NoDataException | SQLException e) {
            mostrarError("Error al cargar vendedores: " + e.getMessage());
        }
    }

    private void eliminarVendedor(Vendedor vendedor) {
        int confirmacion = JOptionPane.showConfirmDialog(
            viewVendedor,
            "¿Está seguro que desea eliminar al vendedor " + vendedor.getNombre() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Intentando eliminar vendedor con ID: " + vendedor.getCedula());
                vendedorDAO.eliminarVendedor(vendedor.getCedula());
                // Actualizar la tabla después de eliminar
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) viewVendedor.getTablaVendedores().getModel();
                    int rowCount = model.getRowCount();
                    if (rowCount > 0) {
                        // Si no es el último registro, actualizar la tabla normalmente
                        cargarVendedores();
                    } else {
                        // Si es el último registro, limpiar la tabla
                        model.setRowCount(0);
                    }
                });
            } catch (SQLException | GlobalException | NoDataException ex) {
                mostrarError("Error al eliminar vendedor: " + ex.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(viewVendedor, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
