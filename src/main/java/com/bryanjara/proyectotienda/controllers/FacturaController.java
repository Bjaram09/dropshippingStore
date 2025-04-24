package com.bryanjara.proyectotienda.controllers;


import com.bryanjara.proyectotienda.dataaccess.*;
import com.bryanjara.proyectotienda.models.Factura;
import com.bryanjara.proyectotienda.models.LineaFactura;
import com.bryanjara.proyectotienda.views.ViewFactura;
import javax.swing.*;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FacturaController {
    private final ViewFactura vista;
    private final FacturaDAO servicio;

    public FacturaController() {
        this.vista = new ViewFactura();
        this.servicio = new FacturaDAO();
    }

    public void registrarFactura(LineaFactura[] lineas) {
        List<LineaFactura> listaLineas = Arrays.asList(lineas);
        vista.cargarLineasFactura(listaLineas);

        vista.addAgregarListener(e -> {
            try {
                Factura factura = vista.construirFactura();

                if (factura.getItemFactura() == null || factura.getItemFactura().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Seleccione al menos una línea para registrar la factura.");
                    return;
                }

                servicio.insertarFactura(factura);
                JOptionPane.showMessageDialog(null, "Factura registrada correctamente:\n" + factura);
                vista.limpiarCampos();
            } catch (GlobalException | NoDataException | HeadlessException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            } catch (SQLException ex) {
                Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        vista.addCancelarListener(e -> vista.dispose());

        vista.addCalcularTotalListener(e -> {
            List<LineaFactura> seleccionadas = vista.getLineasSeleccionadas();

            if (seleccionadas == null || seleccionadas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor seleccione al menos una línea de factura.");
                return;
            }

            double subtotal = 0;
            for (LineaFactura lf : seleccionadas) {
                subtotal += lf.getMontoTotal();
            }

            double impuesto = subtotal * 0.13;
            double total = subtotal + impuesto;

            vista.actualizarImpuesto(impuesto);
            vista.actualizarTotal(total);
        });

        vista.setVisible(true);
    }

    public void eliminarFactura() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la factura que desea eliminar:");
        try {
            int id = Integer.parseInt(idStr);
            servicio.eliminarFactura(id);
            JOptionPane.showMessageDialog(null, "Factura eliminada correctamente.");
        } catch (GlobalException | NoDataException | HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar factura: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarFactura() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la factura que desea buscar:");
        try {
            int id = Integer.parseInt(idStr);
            Factura factura = servicio.buscarFactura(id);
            if (factura != null) {
                JOptionPane.showMessageDialog(null, "Factura encontrada:\n" + factura);
            } else {
                JOptionPane.showMessageDialog(null, "Factura no encontrada.");
            }
        } catch (GlobalException | NoDataException | HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar factura: " + e.getMessage());
        }
    }

    public void listarFacturas() {
        try {
            var facturas = servicio.listarFacturas();
            StringBuilder sb = new StringBuilder("Facturas registradas:\n\n");
            for (Factura f : facturas) {
                sb.append(f).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (GlobalException | NoDataException e) {
            JOptionPane.showMessageDialog(null, "Error al listar facturas: " + e.getMessage());
        }
    }

    public void modificarFactura() {
        JOptionPane.showMessageDialog(null, "Modificación aún no implementada.");
    }
}

