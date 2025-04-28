package com.bryanjara.proyectotienda.views.producto;

import com.bryanjara.proyectotienda.views.BaseView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewRegistrarProducto extends BaseView {
    private JPanel form;
    private JPanel buttonField;

    private JTextField inputID;
    private JTextField inputNombre;
    private JComboBox<String> comboCategoria;
    private JTextField inputPrecio;
    private JTextField inputPeso;
    private JTextField inputDimensiones;
    private JTextArea inputDescripcion;
    private JTextField inputInventarioDisponible;
    private JComboBox<String> comboVendedor;

    private JLabel labelID;
    private JLabel labelNombre;
    private JLabel labelCategoria;
    private JLabel labelPrecio;
    private JLabel labelPeso;
    private JLabel labelDimensiones;
    private JLabel labelDescripcion;
    private JLabel labelInventarioDisponible;
    private JLabel labelVendedor;

    private JButton btnRegistrar;

    public ViewRegistrarProducto() {
        super();
        setTitle("Registrar Producto");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Registrar Producto");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        form = new JPanel(new GridBagLayout());
        form.setBackground(BACKGROUND_COLOR);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        labelID = createStyledLabel("ID:", labelFont);
        labelNombre = createStyledLabel("Nombre:", labelFont);
        labelCategoria = createStyledLabel("Categoría:", labelFont);
        labelPrecio = createStyledLabel("Precio:", labelFont);
        labelPeso = createStyledLabel("Peso", labelFont);
        labelDimensiones = createStyledLabel("Dimensiones", labelFont);
        labelDescripcion = createStyledLabel("Descripcion", labelFont);
        labelInventarioDisponible = createStyledLabel("Inventario Disponible:", labelFont);
        labelVendedor = createStyledLabel("Vendedor:", labelFont);

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        inputID = createStyledTextField(inputFont);
        inputNombre = createStyledTextField(inputFont);

        comboCategoria = new JComboBox<>(new String[] {
            "Comida", "Ropa", "Entretenimiento", "Tecnología", "Hogar", "Accesorios", "Electrodomésticos"
        });
        comboCategoria.setFont(inputFont);
        comboCategoria.setBackground(INPUT_BACKGROUND);
        comboCategoria.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        inputPrecio = createStyledTextField(inputFont);
        inputPeso = createStyledTextField(inputFont);
        inputDimensiones = createStyledTextField(inputFont);
        
        inputDescripcion = new JTextArea(5, 20);
        inputDescripcion.setFont(inputFont);
        inputDescripcion.setBackground(INPUT_BACKGROUND);
        inputDescripcion.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        )); 

        inputInventarioDisponible = createStyledTextField(inputFont);

        comboVendedor = new JComboBox<>();
        comboVendedor.setBackground(INPUT_BACKGROUND);

        int row = 0;
        addFormRow(gbc, labelID, inputID, row++);
        addFormRow(gbc, labelNombre, inputNombre, row++);
        addFormRow(gbc, labelCategoria, comboCategoria, row++);
        addFormRow(gbc, labelPrecio, inputPrecio, row++);
        addFormRow(gbc, labelPeso, inputPeso, row++);
        addFormRow(gbc, labelDimensiones, inputDimensiones, row++);
        addFormRow(gbc, labelDescripcion, inputDescripcion, row++);
        addFormRow(gbc, labelInventarioDisponible, inputInventarioDisponible, row++);
        addFormRow(gbc, labelVendedor, comboVendedor, row++);

        buttonField = new JPanel();
        buttonField.setBackground(BACKGROUND_COLOR);
        buttonField.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setBackground(PRIMARY_COLOR);
        btnRegistrar.setForeground(Color.WHITE);
        btnRegistrar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRegistrar.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        btnRegistrar.setFocusPainted(false);
        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonField.add(btnRegistrar);

        add(form, BorderLayout.CENTER);
        add(buttonField, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JLabel createStyledLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(new Color(52, 73, 94));
        return label;
    }

    private JTextField createStyledTextField(Font font) {
        JTextField textField = new JTextField(20);
        textField.setFont(font);
        textField.setBackground(INPUT_BACKGROUND);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }

    private void addFormRow(GridBagConstraints gbc, JLabel label, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(label, gbc);

        gbc.gridx = 1;
        form.add(component, gbc);
    }

    public String getNombre() {
        return inputNombre.getText();
    }

    public String getCategoria() {
        return (String) comboCategoria.getSelectedItem();
    }

    public String getPrecio() {
        return inputPrecio.getText();
    }

    public String getInventarioDisponible() {
        return inputInventarioDisponible.getText();
    }

    public String getSelectedVendedorCedula() {
        String selectedItem = (String) this.getComboVendedor().getSelectedItem();
        if (selectedItem != null) {
            return selectedItem.split(" - ")[0];
        }
        return null;
    }

    public JTextField getTxtNombre() {
        return inputNombre;
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public JComboBox<String> getComboVendedor() {
        return comboVendedor;
    }

    public void setNombre(String nombre) {
        this.inputNombre.setText(nombre);
    }

    public void setCategoria(String categoria) {
        comboCategoria.setSelectedItem(categoria);
    }

    public void setPrecio(String precio) {
        this.inputPrecio.setText(precio);
    }

    public void setInventarioDisponible(String inventarioDisponible) {
        this.inputInventarioDisponible.setText(inventarioDisponible);
    }

    public void setComboVendedor(String vendedor) {
        this.comboVendedor.setSelectedItem(vendedor);
    }

    public void addUpdateListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }

    public String getID() {
        return inputID.getText();
    }

    public void setID(String id) {
        this.inputID.setText(id);
    }

    public String getPeso() {
        return inputPeso.getText();
    }

    public void setPeso(String peso) {
        this.inputPeso.setText(peso);
    }

    public String getDimensiones() {
        return inputDimensiones.getText();
    }

    public void setDimensiones(String dimensiones) {
        this.inputDimensiones.setText(dimensiones);
    }

    public String getDescripcion() {
        return inputDescripcion.getText();
    }

    public void setDescripcion(String descripcion) {
        this.inputDescripcion.setText(descripcion);
    }
}
