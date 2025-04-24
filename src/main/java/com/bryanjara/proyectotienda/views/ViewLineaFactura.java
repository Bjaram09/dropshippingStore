package com.bryanjara.proyectotienda.views;

import com.bryanjara.proyectotienda.models.ItemCarrito;
import com.bryanjara.proyectotienda.models.LineaFactura;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Fio
 */
public class ViewLineaFactura extends BaseView  {

    private final JComboBox<ItemCarrito> dropdownItemCarrito;
    private final JTextField inputCantidad;
    private final JTextField inputPrecioIndividual;
    private final JTextField inputMontoTotal;

    private final JButton btnCancelar;
    private final JButton btnAgregarLinea;
    private final JButton btnIrAFactura;

    public ViewLineaFactura() {
        setTitle("Registrar Línea de Factura");
        setSize(800, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        // Header
        JPanel headerPanel = createHeaderPanel("Registrar Línea de Factura");
        add(headerPanel, BorderLayout.NORTH);

        // Centro con el formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        dropdownItemCarrito = new JComboBox<>();
        dropdownItemCarrito.addActionListener(e -> mostrarDatosDeItemSeleccionado());

        inputCantidad = new JTextField();
        inputPrecioIndividual = new JTextField();
        inputMontoTotal = new JTextField();

        inputCantidad.setEditable(false);
        inputPrecioIndividual.setEditable(false);
        inputMontoTotal.setEditable(false);

        inputCantidad.setBackground(INPUT_BACKGROUND);
        inputPrecioIndividual.setBackground(INPUT_BACKGROUND);
        inputMontoTotal.setBackground(INPUT_BACKGROUND);

        formPanel.add(new JLabel("Nombre del Producto:"));
        formPanel.add(dropdownItemCarrito);
        formPanel.add(new JLabel("Cantidad:"));
        formPanel.add(inputCantidad);
        formPanel.add(new JLabel("Precio Individual:"));
        formPanel.add(inputPrecioIndividual);
        formPanel.add(new JLabel("Monto Total:"));
        formPanel.add(inputMontoTotal);

        add(formPanel, BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        btnAgregarLinea = createStyledButton("Agregar Factura", PRIMARY_COLOR);
        btnIrAFactura = createStyledButton("Ir a Factura", SECONDARY_COLOR);
        btnCancelar = createStyledButton("Cancelar", ACCENT_COLOR);

        buttonPanel.add(btnAgregarLinea);
        buttonPanel.add(btnIrAFactura);
        buttonPanel.add(btnCancelar);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void cargarItemsCarrito(ItemCarrito[] items) {
        for (ItemCarrito item : items) {
            dropdownItemCarrito.addItem(item);
        }
    }

    public ItemCarrito getItemCarritoSeleccionado() {
        return (ItemCarrito) dropdownItemCarrito.getSelectedItem();
    }

    public void mostrarDatosDeItemSeleccionado() {
        ItemCarrito item = getItemCarritoSeleccionado();
        if (item != null) {
            inputCantidad.setText(String.valueOf(item.getCantidad()));
            inputPrecioIndividual.setText(String.valueOf(item.getProducto().getPrecio()));
            inputMontoTotal.setText(String.valueOf(item.calcularSubtotal()));
        }
    }

    public void limpiarCampos() {
        dropdownItemCarrito.setSelectedIndex(-1);
        inputCantidad.setText("");
        inputPrecioIndividual.setText("");
        inputMontoTotal.setText("");
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public void actualizarPrecioIndividual(double precioIndividual) {
        inputPrecioIndividual.setText(String.valueOf(precioIndividual));
    }

    public void actualizarMontoTotal(double montoTotal) {
        inputMontoTotal.setText(String.valueOf(montoTotal));
    }

    public void addAgregarLineaListener(ActionListener listener) {
        btnAgregarLinea.addActionListener(listener);
    }

    public void addIrAFacturaListener(ActionListener listener) {
        btnIrAFactura.addActionListener(listener);
    }

    public LineaFactura construirLineaFactura() {
        ItemCarrito item = getItemCarritoSeleccionado();

        LineaFactura linea = new LineaFactura();
        linea.setItemCarrito(item);
        linea.setMontoTotal(item.calcularSubtotal());

        return linea;
    }
}
