package com.bryanjara.proyectotienda.controllers;

import com.bryanjara.proyectotienda.models.ItemCarrito;
import com.bryanjara.proyectotienda.models.Producto;
import com.bryanjara.proyectotienda.models.Vendedor;
import com.bryanjara.proyectotienda.views.ViewRegistrarItemCarrito;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*public class ItemCarritoController {
    private ViewRegistrarItemCarrito vista;

    public ItemCarritoController(ViewRegistrarItemCarrito vista) {
        this.vista = vista;

        this.vista.addAgregarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Captura de datos desde la vista
                    String nombre = vista.getNombreProducto();
                    String categoria = vista.getCategoria();
                    double precio = vista.getPrecio();
                    double peso = vista.getPeso();
                    String dimensiones = vista.getDimensiones();
                    String descripcion = vista.getDescripcion();
                    int inventario = vista.getInventario();
                    int cantidad = vista.getCantidad();

                    // Datos no disponibles en la vista: imágenes y vendedor
                    ArrayList<Image> imagenes = new ArrayList<>();
                    Vendedor vendedorFicticio = new Vendedor(); // debe existir constructor sin argumentos

                    // Crear el producto con todos los argumentos necesarios
                    /*Producto producto = new Producto(
                            nombre,
                            categoria,
                            precio,
                            peso,
                            dimensiones,
                            imagenes,
                            descripcion,
                            inventario,
                            vendedorFicticio
                    );

                    // Crear el ítem y mostrar
                    ItemCarrito item = new ItemCarrito(producto, cantidad);

                    JOptionPane.showMessageDialog(null,
                            "Item agregado al carrito:\n" +
                                    "Producto: " + producto.getNombre() +
                                    "\nCantidad: " + cantidad +
                                    "\nSubtotal: ₡" + item.calcularSubtotal());

                    vista.limpiarCampos();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.vista.addCancelarListener(e -> vista.dispose());
    }
}*/
