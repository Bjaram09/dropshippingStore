/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import dataaccess.GlobalException;
import dataaccess.NoDataException;
import dataaccess.ServicioItemCarrito;
import Modelo.ItemCarrito;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Fio
 */
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
