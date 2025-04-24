/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.ItemCarrito;
import Modelo.LineaFactura;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author Fio
 */
public class ViewLineaFactura extends JFrame  {

    private final JComboBox<ItemCarrito> dropdownItemCarrito;
    private final JTextField inputCantidad;
    private final JTextField inputPrecioIndividual;
    private final JTextField inputMontoTotal;

    private final JButton btnCancelar = new JButton("Cancelar");
    private final JButton btnAgregarLinea = new JButton("Agregar Factura");
    private final JButton btnIrAFactura = new JButton("Ir a Factura");

    public ViewLineaFactura() {
        setTitle("Registrar Línea de Factura");        
        setSize(800, 500);
        setLayout(new GridLayout(8, 4));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        dropdownItemCarrito = new JComboBox<>();
        dropdownItemCarrito.addActionListener(e -> mostrarDatosDeItemSeleccionado());
        inputCantidad = new JTextField();
        inputPrecioIndividual = new JTextField();
        inputMontoTotal = new JTextField();

        inputCantidad.setEditable(false);
        inputPrecioIndividual.setEditable(false);
        inputMontoTotal.setEditable(false);

        add(new JLabel("Nombre del Producto:"));
        add(dropdownItemCarrito);
        add(new JLabel("Cantidad:"));
        add(inputCantidad);
        add(new JLabel("Precio Individual:"));
        add(inputPrecioIndividual);
        add(new JLabel("Monto Total:"));
        add(inputMontoTotal);
        add(btnAgregarLinea);
        add(btnIrAFactura);
        add(btnCancelar);

        setLocationRelativeTo(null);
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
