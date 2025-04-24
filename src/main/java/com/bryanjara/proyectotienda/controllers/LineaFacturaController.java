/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author Fio
 */
import dataaccess.GlobalException;
import dataaccess.NoDataException;
import dataaccess.ServicioLineaFactura;
import Modelo.ItemCarrito;
import Modelo.LineaFactura;
import Vista.ViewLineaFactura;
import java.awt.HeadlessException;
import javax.swing.*;
import java.util.Collection;

public class LineaFacturaController {

    private final ViewLineaFactura vista;
    private final ServicioLineaFactura servicio;

    public LineaFacturaController() {
        this.vista = new ViewLineaFactura();
        this.servicio = new ServicioLineaFactura();
    }

    public void registrarLineaFactura(ItemCarrito[] carrito) {
        vista.cargarItemsCarrito(carrito);

        vista.addAgregarLineaListener(e -> {
            LineaFactura linea = vista.construirLineaFactura();
            try {
                servicio.insertarLineaFactura(linea);
                JOptionPane.showMessageDialog(null, "Línea insertada correctamente:\n" + linea);
                vista.limpiarCampos();
            } catch (GlobalException | NoDataException | HeadlessException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        vista.addCancelarListener(e -> vista.dispose());

        vista.addIrAFacturaListener(e -> {
            try {
                MenuFactura menuFactura = new MenuFactura();
                menuFactura.mostrarMenuFactura();
                vista.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al abrir el menú de factura: " + ex.getMessage());
            }
        });

        vista.setVisible(true); 
    }

    public void eliminarLineaFactura() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la línea que desea eliminar:");
        try {
            int id = Integer.parseInt(idStr);
            servicio.eliminarLineaFactura(id);
            JOptionPane.showMessageDialog(null, "Línea eliminada correctamente.");
        } catch (GlobalException | NoDataException | HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar línea: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarLineaFactura() {
        String idStr = JOptionPane.showInputDialog("Ingrese el ID de la línea que desea buscar:");
        try {
            int id = Integer.parseInt(idStr);
            LineaFactura linea = servicio.buscarLineaFactura(id);
            if (linea != null) {
                JOptionPane.showMessageDialog(null, "Línea encontrada:\n" + linea);
            } else {
                JOptionPane.showMessageDialog(null, "Línea no encontrada.");
            }
        } catch (GlobalException | NoDataException | HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar línea: " + e.getMessage());
        }
    }

    public void listarLineasFactura() {
        try {
            Collection<LineaFactura> lineas = servicio.listar_lineas_factura();
            StringBuilder sb = new StringBuilder("Líneas de Factura:\n\n");
            for (LineaFactura lf : lineas) {
                sb.append(lf).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (GlobalException | NoDataException e) {
            JOptionPane.showMessageDialog(null, "Error al listar líneas: " + e.getMessage());
        }
    }
}
