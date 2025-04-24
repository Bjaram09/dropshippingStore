package com.bryanjara.proyectotienda.controllers;

import com.bryanjara.proyectotienda.dataaccess.*;
import com.bryanjara.proyectotienda.models.ItemCarrito;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MenuLineaFactura {

    public void menuLineaFactura() {
        LineaFacturaController controlador = new LineaFacturaController();

        String opcion = JOptionPane.showInputDialog("""
        Operaciones con Línea de Factura:
        1. Insertar Línea
        2. Eliminar Línea
        3. Listar Líneas
        4. Buscar Línea
    """);

        if (opcion == null) {
            return;
        }

        switch (opcion) {
            case "1" -> {
                try {
                    ServicioItemCarrito servicioItem = new ServicioItemCarrito();
                    ItemCarrito[] carrito = servicioItem.listarItemCarrito();
                    controlador.registrarLineaFactura(carrito);
                } catch (GlobalException | NoDataException | SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar ítems del carrito: " + e.getMessage());
                }
            }
            case "2" ->
                controlador.eliminarLineaFactura();
            case "3" ->
                controlador.listarLineasFactura();
            case "4" ->
                controlador.buscarLineaFactura();
            default ->
                JOptionPane.showMessageDialog(null, "Opción inválida");
        }
    }
}
