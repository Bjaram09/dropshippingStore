package com.bryanjara.proyectotienda.controllers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Collection;

import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.dataaccess.ProductoDAO;
import com.bryanjara.proyectotienda.dataaccess.VendedorDAO;
import com.bryanjara.proyectotienda.models.Producto;
import com.bryanjara.proyectotienda.views.producto.ViewProductos;
import com.bryanjara.proyectotienda.views.producto.ViewRegistrarProducto;
import com.bryanjara.proyectotienda.models.Vendedor;

public class ProductoController {
    private final Producto producto;
    private final ProductoDAO productoDAO;
    private final VendedorDAO vendedorDAO;
    private ViewProductos viewProductos;

    public ProductoController(ViewProductos view) {
        this.producto = new Producto();
        this.productoDAO = new ProductoDAO();
        this.viewProductos = view;
        this.vendedorDAO = new VendedorDAO();
    }

    public void iniciar() {
        cargarProductos();
        configurarListeners();
    }

    private void configurarListeners() {
        // Listener para registrar nuevo producto
        viewProductos.getBtnRegistrarProducto().addActionListener(e -> abrirVentanaRegistro(null));

        viewProductos.getBtnBuscarProducto().addActionListener(this::buscarProducto);

        // Listener para eliminar
        viewProductos.setDeleteListener(this::manejarEliminacion);

        // Listener para actualizar
        viewProductos.setUpdateListener(this::manejarActualizacion);

        viewProductos.setChangeInventaryListener(this::manejarCambioInventario);
    }

    private void abrirVentanaRegistro(Producto productoExistente) {
        ViewRegistrarProducto registrarProducto = new ViewRegistrarProducto();
        boolean esActualizacion = productoExistente != null;

        if (esActualizacion) {
            registrarProducto.setTitle("Actualizar Producto");
            registrarProducto.getBtnRegistrar().setText("Actualizar");
            registrarProducto.getTxtNombre().setEnabled(false);
            poblarVistaDesdeProductoAActualizar(registrarProducto, productoExistente);
        }

        // Populate the combo box with vendedores
        populateVendedorCombo(registrarProducto);

        registrarProducto.addUpdateListener(ev -> manejarRegistroActualizacion(registrarProducto, esActualizacion));
    }

    private void poblarVistaDesdeProductoAActualizar(ViewRegistrarProducto vista, Producto producto) {
        vista.setID(String.valueOf(producto.getID()));
        vista.setNombre(producto.getNombre());
        vista.setCategoria(producto.getCategoria());
        vista.setPrecio(String.valueOf(producto.getPrecio()));
        vista.setPeso(String.valueOf(producto.getPeso()));
        vista.setDimensiones(producto.getDimensiones());
        vista.setDescripcion(producto.getDescripcion());
        vista.setInventarioDisponible(String.valueOf(producto.getInventarioDisponible()));
    }

    private void manejarRegistroActualizacion(ViewRegistrarProducto vista, boolean esActualizacion) {
        Producto productoTemp = new Producto();
        registrarProductoDesdeVista(productoTemp, vista);

        try {
            if (esActualizacion) {
                productoDAO.modificarProducto(productoTemp);
            } else {
                productoDAO.insertarProducto(productoTemp);
            }

            JOptionPane.showMessageDialog(null, "Operación exitosa!");
            vista.dispose();
            cargarProductos();
        } catch (GlobalException | NoDataException | SQLException ex) {
            mostrarError("Error durante la operación: " + ex.getMessage());
        }
    }

    private void registrarProductoDesdeVista(Producto producto, ViewRegistrarProducto vista) {
        try {
            producto.setID(Integer.parseInt(vista.getID()));
            producto.setNombre(vista.getNombre());
            producto.setCategoria(vista.getCategoria());
            producto.setPrecio(Double.parseDouble(vista.getPrecio()));
            producto.setPeso(Double.parseDouble(vista.getPeso()));
            producto.setDimensiones(vista.getDimensiones());
            producto.setDescripcion(vista.getDescripcion());
            producto.setInventarioDisponible(Integer.parseInt(vista.getInventarioDisponible()));
            producto.setVendedor(getSelectedVendedor(vista));
        } catch (NumberFormatException e) {
            mostrarError("Error de formato en los datos: " + e.getMessage());
        }
    }

    public Vendedor getSelectedVendedor(ViewRegistrarProducto vista) {
        String selectedItem = (String) vista.getComboVendedor().getSelectedItem();
        if (selectedItem != null) {
            String cedula = selectedItem.split(" - ")[0];
            try {
                return vendedorDAO.buscarVendedor(cedula);
            } catch (GlobalException | NoDataException | SQLException e) {
                mostrarError("Error al cargar vendedor: " + e.getMessage());
                return null;
            }
        }
        return null;
    }

    private void manejarEliminacion(ActionEvent e) {
        if (e.getSource() instanceof ViewProductos.ActionsEditor) {
            Producto productoAEliminar = ((ViewProductos.ActionsEditor) e.getSource()).getProducto();
            if (productoAEliminar != null) {
                eliminarProducto(productoAEliminar);
            }
        }
    }

    private void manejarActualizacion(ActionEvent e) {
        if (e.getSource() instanceof ViewProductos.ActionsEditor) {
            Producto productoAActualizar = ((ViewProductos.ActionsEditor) e.getSource()).getProducto();
            if (productoAActualizar != null) {
                abrirVentanaRegistro(productoAActualizar);
            }
        }
    }

    private void manejarCambioInventario(ActionEvent e){
        if (e.getSource() instanceof ViewProductos.ActionsEditor) {
            Producto productoActualizarInventario = ((ViewProductos.ActionsEditor) e.getSource()).getProducto();
            if (productoActualizarInventario != null) {
                modificarInventario(productoActualizarInventario);
            }
        }
    }

    private void cargarProductos() {
        try {
            Collection<Producto> productos = productoDAO.listarProductos();
            viewProductos.listarProductos(productos);
        } catch (GlobalException | NoDataException | SQLException e) {
            mostrarError("Error al cargar productos: " + e.getMessage());
        }
    }

    private void modificarInventario(Producto productoActualizar){
        try {
            String nuevoInventarioStr = JOptionPane.showInputDialog(
                viewProductos,
                "Ingrese el nuevo inventario disponible para el producto: " + productoActualizar.getNombre(),
                "Modificar Inventario",
                JOptionPane.QUESTION_MESSAGE
            );
    
            if (nuevoInventarioStr != null && !nuevoInventarioStr.trim().isEmpty()) {
                int nuevoInventario = Integer.parseInt(nuevoInventarioStr.trim());
    
                productoActualizar.setInventarioDisponible(nuevoInventario);
                productoDAO.modificarProducto(productoActualizar);
                
                JOptionPane.showMessageDialog(
                    viewProductos,
                    "El inventario del producto " + productoActualizar.getNombre() + " ha sido actualizado a " + nuevoInventario,
                    "Inventario Actualizado",
                    JOptionPane.INFORMATION_MESSAGE
                );
    
                cargarProductos();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                viewProductos,
                "El valor ingresado no es un número válido.",
                "Error de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (GlobalException | NoDataException | SQLException e) {
            mostrarError("Error al actualizar el inventario: " + e.getMessage());
        }
    }

    private void eliminarProducto(Producto producto) {
        int confirmacion = JOptionPane.showConfirmDialog(
            viewProductos,
            "¿Está seguro que desea eliminar al producto " + producto.getNombre() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                System.out.println("Intentando eliminar producto con ID: " + producto.getID());
                productoDAO.eliminarProducto(producto.getID());
                // Actualizar la tabla después de eliminar
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) viewProductos.getTablaProductos().getModel();
                    int rowCount = model.getRowCount();
                    if (rowCount > 0) {
                        cargarProductos();
                    } else {
                        model.setRowCount(0);
                    }
                });
            } catch (SQLException | GlobalException | NoDataException ex) {
                mostrarError("Error al eliminar producto: " + ex.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(viewProductos, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void populateVendedorCombo(ViewRegistrarProducto view) {
        try {
            Collection<Vendedor> vendedores = vendedorDAO.listarVendedores();

            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            for (Vendedor vendedor : vendedores) {
                model.addElement(vendedor.getCedula() + " - " + vendedor.getNombre());
            }
            view.getComboVendedor().setModel(model);

        } catch (GlobalException | NoDataException | SQLException e) {
            JOptionPane.showMessageDialog(view, "Error al cargar vendedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto(ActionEvent e) {
        String criterio = viewProductos.getTxtBuscarProducto().getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(viewProductos,
                    "Por favor, ingresa un criterio de búsqueda.",
                    "Búsqueda",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Collection<Producto> productosEncontrados = productoDAO.buscarProductoPorCriterio(criterio);
            if (productosEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(viewProductos,
                        "No se encontraron productos que coincidan con el criterio de búsqueda.",
                        "Búsqueda",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                actualizarTabla(productosEncontrados);
            }
        } catch (GlobalException | NoDataException | SQLException ex) {
            mostrarError("Error al buscar productos: " + ex.getMessage());
        }
    }

    private void actualizarTabla(Collection<Producto> productos) {
        DefaultTableModel modeloTabla = viewProductos.getModeloTabla();
        modeloTabla.setRowCount(0);
        for (Producto producto : productos) {
            Object[] row = {
                producto.getNombre(),
                producto.getCategoria(),
                "₡" + producto.getPrecio(),
                producto.getPeso() + " Kg",
                producto.getDimensiones(),
                producto.getDescripcion(),
                producto.getVendedor().getNombre(),
                producto
            };
            modeloTabla.addRow(row);
        }
    }

}
