package com.bryanjara.proyectotienda.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.bryanjara.proyectotienda.dataaccess.GlobalException;
import com.bryanjara.proyectotienda.dataaccess.NoDataException;
import com.bryanjara.proyectotienda.dataaccess.ProductoDAO;
import com.bryanjara.proyectotienda.dataaccess.ServicioLineaFactura;
import com.bryanjara.proyectotienda.models.Comprador;
import com.bryanjara.proyectotienda.models.ItemCarrito;
import com.bryanjara.proyectotienda.models.LineaFactura;
import com.bryanjara.proyectotienda.models.Producto;
import com.bryanjara.proyectotienda.views.catalogo.ViewCatalogo;
import com.bryanjara.proyectotienda.views.catalogo.ViewCarrito;
import com.bryanjara.proyectotienda.views.catalogo.ViewListaFavoritos;
import com.bryanjara.proyectotienda.views.ViewFactura;

public class CatalogoController {
    private final Producto producto;
    private final ProductoDAO productoDAO;
    private ViewCatalogo viewCatalogo;
    private ViewCarrito viewCarrito;
    private final Comprador compradorActual;

    public CatalogoController(ViewCatalogo view, Comprador compradorActual) {
        this.producto = new Producto();
        this.productoDAO = new ProductoDAO();
        this.viewCatalogo = view;
        this.viewCarrito = new ViewCarrito();
        this.compradorActual = compradorActual;
    }

    public void iniciar() {
        cargarProductos();
        configurarListeners();
    }

    private void cargarProductos() {
        try {
            Collection<Producto> productos = productoDAO.listarProductos();
            viewCatalogo.listarProductos(productos);
        } catch (GlobalException | NoDataException | SQLException e) {
            mostrarError("Error al cargar productos: " + e.getMessage());
        }
    }


    private void configurarListeners() {
        viewCatalogo.getBtnVerCarrito().addActionListener(e -> abrirVentanaCarrito(null));
        viewCatalogo.getBtnBuscar().addActionListener(this::buscarProducto);
        viewCatalogo.getBtnVerListaFavoritos().addActionListener(this::abrirVentanaListaFavoritos);
        viewCatalogo.getBtnGenerarFactura().addActionListener(this::generarFactura);

        viewCatalogo.setCarritoListener(this::manejarCarrito);
        viewCatalogo.setFavoritosListener(this::manejarFavoritos);
    }

    private void abrirVentanaCarrito(ActionEvent e) {
        DefaultTableModel modeloTabla = viewCarrito.getModeloTabla();
        modeloTabla.setRowCount(0);

        for (ItemCarrito item : compradorActual.getCarritoCompras()) {
            Producto producto = item.getProducto();
            int cantidad = item.getCantidad();
            double subtotal = producto.getPrecio() * cantidad;

            Object[] row = {
                    producto.getNombre(),
                    producto.getCategoria(),
                    "₡" + producto.getPrecio(),
                    cantidad,
                    "₡" + subtotal
            };
            modeloTabla.addRow(row);
        }

        for (ActionListener al : viewCarrito.getBtnEliminar().getActionListeners()) {
            viewCarrito.getBtnEliminar().removeActionListener(al);
        }

        viewCarrito.getBtnEliminar().addActionListener(ev -> {
            int selectedRow = viewCarrito.getTablaCarrito().getSelectedRow();
            if (selectedRow != -1) {
                ItemCarrito itemAEliminar = compradorActual.getCarritoCompras().get(selectedRow);
                compradorActual.getCarritoCompras().remove(itemAEliminar);
                modeloTabla.removeRow(selectedRow);

                JOptionPane.showMessageDialog(viewCarrito,
                        "Producto eliminado del carrito: " + itemAEliminar.getProducto().getNombre(),
                        "Carrito",
                        JOptionPane.INFORMATION_MESSAGE);

                if (compradorActual.getCarritoCompras().isEmpty()) {
                    JOptionPane.showMessageDialog(viewCarrito,
                            "No tienes más productos en tu carrito. Cerrando la ventana.",
                            "Carrito",
                            JOptionPane.INFORMATION_MESSAGE);
                    viewCarrito.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(viewCarrito,
                        "Por favor, selecciona un producto para eliminar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Limpiar cualquier listener anterior (por si acaso)
        for (ActionListener al : viewCarrito.getBtnAgregarLineaFactura().getActionListeners()) {
            viewCarrito.getBtnAgregarLineaFactura().removeActionListener(al);
        }

        // Listener para agregar LineaFactura
        viewCarrito.getBtnAgregarLineaFactura().addActionListener(ev -> {
            int selectedRow = viewCarrito.getTablaCarrito().getSelectedRow();
            if (selectedRow != -1) {
                try {
                    ItemCarrito itemSeleccionado = compradorActual.getCarritoCompras().get(selectedRow);

                    LineaFactura nuevaLinea = new LineaFactura();
                    nuevaLinea.setItemCarrito(itemSeleccionado);
                    nuevaLinea.setMontoTotal(itemSeleccionado.getProducto().getPrecio() * itemSeleccionado.getCantidad());

                    ServicioLineaFactura servicioLineaFactura = new ServicioLineaFactura();
                    servicioLineaFactura.insertarLineaFactura(nuevaLinea);

                    JOptionPane.showMessageDialog(viewCarrito,
                            "¡Producto agregado a LINEA_FACTURA exitosamente!",
                            "Éxito",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (GlobalException | NoDataException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(viewCarrito,
                            "Error al agregar a LINEA_FACTURA: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(viewCarrito,
                        "Por favor selecciona un producto del carrito para agregar.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }
        });


        viewCarrito.setVisible(true);
    }

    private void manejarCarrito(ActionEvent e) {
        if (e.getSource() instanceof ViewCatalogo.ActionsEditor) {
            ViewCatalogo.ActionsEditor editor = (ViewCatalogo.ActionsEditor) e.getSource();
            Producto productoSeleccionado = editor.getProducto();

            if (productoSeleccionado != null) {
                boolean productoYaEnCarrito = false;
                for (ItemCarrito item : compradorActual.getCarritoCompras()) {
                    if (item.getProducto().equals(productoSeleccionado)) {
                        item.setCantidad(item.getCantidad() + 1);
                        productoYaEnCarrito = true;
                        break;
                    }
                }

                if (!productoYaEnCarrito) {
                    compradorActual.agregarAlCarrito(productoSeleccionado, 1);
                }

                JOptionPane.showMessageDialog(viewCatalogo,
                        "Producto añadido al carrito: " + productoSeleccionado.getNombre(),
                        "Carrito",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void manejarFavoritos(ActionEvent e) {
        if (e.getSource() instanceof ViewCatalogo.ActionsEditor) {
            ViewCatalogo.ActionsEditor editor = (ViewCatalogo.ActionsEditor) e.getSource();
            Producto productoSeleccionado = editor.getProducto();

            if (productoSeleccionado != null) {
                if (compradorActual.getListaFavoritos().contains(productoSeleccionado)) {
                    JOptionPane.showMessageDialog(viewCatalogo,
                            "Este producto ya está en tus favoritos.",
                            "Favoritos",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    compradorActual.agregarFavoritos(productoSeleccionado);
                    JOptionPane.showMessageDialog(viewCatalogo,
                            "Producto añadido a favoritos: " + productoSeleccionado.getNombre(),
                            "Favoritos",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    private void abrirVentanaListaFavoritos(ActionEvent e) {
        ViewListaFavoritos viewListaFavoritos = new ViewListaFavoritos();
        DefaultTableModel modeloTabla = viewListaFavoritos.getModeloTabla();
        modeloTabla.setRowCount(0);

        for (Producto producto : compradorActual.getListaFavoritos()) {
            Object[] row = {
                    producto.getNombre(),
                    producto.getCategoria(),
                    "₡" + producto.getPrecio()
            };
            modeloTabla.addRow(row);
        }

        viewListaFavoritos.getBtnEliminar().addActionListener(ev -> {
            int selectedRow = viewListaFavoritos.getTablaFavoritos().getSelectedRow();
            if (selectedRow != -1) {
                Producto productoAEliminar = compradorActual.getListaFavoritos().get(selectedRow);
                compradorActual.getListaFavoritos().remove(productoAEliminar);
                modeloTabla.removeRow(selectedRow);

                JOptionPane.showMessageDialog(viewListaFavoritos,
                        "Producto eliminado de favoritos: " + productoAEliminar.getNombre(),
                        "Favoritos",
                        JOptionPane.INFORMATION_MESSAGE);

                if (compradorActual.getListaFavoritos().isEmpty()) {
                    JOptionPane.showMessageDialog(viewListaFavoritos,
                            "No tienes más productos en tu lista de favoritos. Cerrando la ventana.",
                            "Favoritos",
                            JOptionPane.INFORMATION_MESSAGE);
                    viewListaFavoritos.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(viewListaFavoritos,
                        "Por favor, selecciona un producto para eliminar.",
                        "Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        viewListaFavoritos.setVisible(true);
    }

    private void buscarProducto(ActionEvent e) {
        String criterio = viewCatalogo.getTxtBuscar().getText().trim();
        if (criterio.isEmpty()) {
            JOptionPane.showMessageDialog(viewCatalogo,
                    "Por favor, ingresa un criterio de búsqueda.",
                    "Búsqueda",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Collection<Producto> productosEncontrados = productoDAO.buscarProductoPorCriterio(criterio);
            if (productosEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(viewCatalogo,
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
        DefaultTableModel modeloTabla = viewCatalogo.getModeloTabla();
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

    private void generarFactura(ActionEvent e) {
        if (compradorActual.getCarritoCompras().isEmpty()) {
            JOptionPane.showMessageDialog(viewCatalogo,
                    "No hay productos en el carrito para generar una factura.",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<LineaFactura> lineasFactura = new ArrayList<>();

        for (ItemCarrito item : compradorActual.getCarritoCompras()) {
            LineaFactura linea = new LineaFactura();
            linea.setItemCarrito(item);
            linea.setMontoTotal(item.getProducto().getPrecio() * item.getCantidad());
            lineasFactura.add(linea);
        }

        FacturaController facturaController = new FacturaController();
        facturaController.registrarFactura(lineasFactura.toArray(new LineaFactura[0]));
    }


    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(viewCatalogo, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
