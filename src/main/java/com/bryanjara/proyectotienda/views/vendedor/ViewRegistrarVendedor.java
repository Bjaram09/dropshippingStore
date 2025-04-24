package com.bryanjara.proyectotienda.views.vendedor;

import com.bryanjara.proyectotienda.views.BaseView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewRegistrarVendedor extends BaseView {
    private JPanel form;
    private JPanel buttonField;

    private JTextField inputCedula;
    private JTextField inputNombre;
    private JTextField inputUbicacion;
    private JTextField inputCorreoContacto;
    private JTextField inputNumeroTelefono;

    private JLabel labelCedula;
    private JLabel labelNombre;
    private JLabel labelUbicacion;
    private JLabel labelCorreoContacto;
    private JLabel labelNumeroTelefono;

    private JButton btnRegistrar;

    public ViewRegistrarVendedor() {
        super();
        setTitle("Registrar Vendedor");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel("Registrar Vendedor");
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
        labelCedula = createStyledLabel("Cédula:", labelFont);
        labelNombre = createStyledLabel("Nombre:", labelFont);
        labelUbicacion = createStyledLabel("Ubicación:", labelFont);
        labelCorreoContacto = createStyledLabel("Correo de Contacto:", labelFont);
        labelNumeroTelefono = createStyledLabel("Número de Teléfono:", labelFont);

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        inputCedula = createStyledTextField(inputFont);
        inputNombre = createStyledTextField(inputFont);
        inputUbicacion = createStyledTextField(inputFont);
        inputCorreoContacto = createStyledTextField(inputFont);
        inputNumeroTelefono = createStyledTextField(inputFont);

        int row = 0;
        addFormRow(gbc, labelCedula, inputCedula, row++);
        addFormRow(gbc, labelNombre, inputNombre, row++);
        addFormRow(gbc, labelUbicacion, inputUbicacion, row++);
        addFormRow(gbc, labelCorreoContacto, inputCorreoContacto, row++);
        addFormRow(gbc, labelNumeroTelefono, inputNumeroTelefono, row++);

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

    public String getCedula() {
        return inputCedula.getText();
    }

    public String getNombre() {
        return inputNombre.getText();
    }

    public String getUbicacion() {
        return inputUbicacion.getText();
    }

    public String getCorreoContacto() {
        return inputCorreoContacto.getText();
    }

    public String getNumeroTelefono() {
        return inputNumeroTelefono.getText();
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    public void setCedula(String cedula) {
        inputCedula.setText(cedula);
    }

    public void setNombre(String nombre) {
        inputNombre.setText(nombre);
    }

    public void setUbicacion(String ubicacion) {
        inputUbicacion.setText(ubicacion);
    }

    public void setCorreoContacto(String correoContacto) {
        inputCorreoContacto.setText(correoContacto);
    }

    public void setNumeroTelefono(String numeroTelefono) {
        inputNumeroTelefono.setText(numeroTelefono);
    }

    public JTextField getTxtCedula() {
        return inputCedula;
    }

    public void addUpdateListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }
}
