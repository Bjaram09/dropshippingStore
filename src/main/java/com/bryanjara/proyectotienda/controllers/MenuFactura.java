package com.bryanjara.proyectotienda.controllers;

import javax.swing.*;
import com.bryanjara.proyectotienda.dataaccess.*;
import com.bryanjara.proyectotienda.models.LineaFactura;
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
                default -> JOptionPane.showMessageDialog(null, "Opción inválida");
            }

        } catch (GlobalException | NoDataException  e) {
            JOptionPane.showMessageDialog(null, "Error al verificar líneas de factura: " + e.getMessage());
        }
    }
}
