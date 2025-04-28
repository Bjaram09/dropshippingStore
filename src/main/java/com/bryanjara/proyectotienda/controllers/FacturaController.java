package com.bryanjara.proyectotienda.controllers;

import com.bryanjara.proyectotienda.dataaccess.*;
import com.bryanjara.proyectotienda.models.Factura;
import com.bryanjara.proyectotienda.models.LineaFactura;
import com.bryanjara.proyectotienda.views.ViewFactura;

import javax.swing.*;
import java.awt.HeadlessException;
import java.sql.SQLException;
import java.util.ArrayList;
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
                servicio.insertarFactura(factura);
                JOptionPane.showMessageDialog(vista, "Factura registrada correctamente:\n" + factura);
                vista.dispose();
            } catch (GlobalException | NoDataException | HeadlessException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(vista, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                Logger.getLogger(FacturaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        vista.addCancelarListener(e -> vista.dispose());

        vista.addCalcularTotalListener(e -> calcularTotal(vista));

        vista.setVisible(true);
    }

    public void listarFacturas() {
        try {
            var facturas = servicio.listarFacturas();
            StringBuilder sb = new StringBuilder("Facturas registradas:\n\n");
            for (Factura f : facturas) {
                sb.append(f).append("\n--------------------------\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (GlobalException | NoDataException e) {
            JOptionPane.showMessageDialog(null, "Error al listar facturas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularTotal(ViewFactura vista) {
        double subtotal = 0.0;
        DefaultListModel<LineaFactura> modelo = vista.getModeloLineas();

        for (int i = 0; i < modelo.getSize(); i++) {
            subtotal += modelo.get(i).getMontoTotal();
        }

        double impuesto = subtotal * 0.13;
        double total = subtotal + impuesto;

        // Actualizar los campos de la vista
        vista.actualizarImpuesto(impuesto);
        vista.actualizarTotal(total);

        // Mostrar un mensaje con la información calculada
        JOptionPane.showMessageDialog(
                vista,
                "Subtotal: " + String.format("%.2f", subtotal) + "\n" +
                        "Impuesto (13%): " + String.format("%.2f", impuesto) + "\n" +
                        "Total: " + String.format("%.2f", total),
                "Resumen de Factura",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
}