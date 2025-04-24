/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author Fio
 */

import javax.swing.*;

import dataaccess.ServicioLineaFactura;
import dataaccess.GlobalException;
import dataaccess.NoDataException;
import Modelo.LineaFactura;
import java.util.List;

public class MenuFactura {

    public void mostrarMenuFactura() {
        ServicioLineaFactura servicioLinea = new ServicioLineaFactura();

        try {
            List<LineaFactura> lineas = (List<LineaFactura>) servicioLinea.listar_lineas_factura();

            if (lineas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay líneas de factura registradas. Por favor, registre al menos una antes de generar facturas.");
                return;
            }

            String opcion = JOptionPane.showInputDialog("""
                Operaciones con Factura:
                1. Insertar Factura
                2. Eliminar Factura
                3. Listar Facturas
                4. Buscar Factura
            """);

            if (opcion == null) return;

            FacturaController controller = new FacturaController();

            switch (opcion) {
                case "1" -> controller.registrarFactura(lineas.toArray(LineaFactura[]::new));
                case "2" -> controller.eliminarFactura();
                case "3" -> controller.listarFacturas();
                case "4" -> controller.buscarFactura();
                default -> JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } catch (GlobalException | NoDataException  e) {
            JOptionPane.showMessageDialog(null, "Error al verificar líneas de factura: " + e.getMessage());
        }
    }
}
