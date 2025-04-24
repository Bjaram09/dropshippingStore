package com.bryanjara.proyectotienda.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewRegistrarItemCarrito extends BaseView {
    private JTextField inputNombreProducto = new JTextField();
    private JTextField inputCategoria = new JTextField();
    private JTextField inputPrecio = new JTextField();
    private JTextField inputPeso = new JTextField();
    private JTextField inputDimensiones = new JTextField();
    private JTextField inputDescripcion = new JTextField();
    private JTextField inputInventario = new JTextField();
    private JTextField inputCantidad = new JTextField();

    private JButton btnAgregar = new JButton("Agregar al Carrito");
    private JButton btnCancelar = new JButton("Cancelar");

    public ViewRegistrarItemCarrito() {
        setTitle("Registrar Item Carrito");
        setSize(500, 500);
        setLayout(new GridLayout(10, 2));

        add(new JLabel("Nombre del Producto:")); add(inputNombreProducto);
        add(new JLabel("Categoría:")); add(inputCategoria);
        add(new JLabel("Precio:")); add(inputPrecio);
        add(new JLabel("Peso:")); add(inputPeso);
        add(new JLabel("Dimensiones:")); add(inputDimensiones);
        add(new JLabel("Descripción:")); add(inputDescripcion);
        add(new JLabel("Inventario Disponible:")); add(inputInventario);
        add(new JLabel("Cantidad a Agregar:")); add(inputCantidad);

        add(btnAgregar); add(btnCancelar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public String getNombreProducto() { return inputNombreProducto.getText(); }
    public String getCategoria() { return inputCategoria.getText(); }
    public double getPrecio() { return Double.parseDouble(inputPrecio.getText()); }
    public double getPeso() { return Double.parseDouble(inputPeso.getText()); }
    public String getDimensiones() { return inputDimensiones.getText(); }
    public String getDescripcion() { return inputDescripcion.getText(); }
    public int getInventario() { return Integer.parseInt(inputInventario.getText()); }
    public int getCantidad() { return Integer.parseInt(inputCantidad.getText()); }

    public void limpiarCampos() {
        inputNombreProducto.setText("");
        inputCategoria.setText("");
        inputPrecio.setText("");
        inputPeso.setText("");
        inputDimensiones.setText("");
        inputDescripcion.setText("");
        inputInventario.setText("");
        inputCantidad.setText("");
    }

    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
}
