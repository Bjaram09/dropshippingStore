package com.bryanjara.proyectotienda.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class ViewRegistrarAdministrador extends JFrame {
    private JTextField inputNombreUsuario = new JTextField();
    private JTextField inputNombreCompleto = new JTextField();
    private JTextField inputCedula = new JTextField();
    private JTextField inputCorreo = new JTextField();
    private JPasswordField inputContrasenia = new JPasswordField();

    private JComboBox<Integer> comboDia;
    private JComboBox<Integer> comboMes;
    private JComboBox<Integer> comboAnio;

    private JButton btnRegistrar = new JButton("Registrar");
    private JButton btnCancelar = new JButton("Cancelar");

    public ViewRegistrarAdministrador() {
        setTitle("Registrar Administrador");
        setSize(400, 350);
        setLayout(new GridLayout(8, 2));

        // Inicializar combos de fecha
        comboDia = new JComboBox<>();
        for (int i = 1; i <= 31; i++) comboDia.addItem(i);

        comboMes = new JComboBox<>();
        for (int i = 1; i <= 12; i++) comboMes.addItem(i);

        comboAnio = new JComboBox<>();
        int anioActual = LocalDate.now().getYear();
        for (int i = anioActual; i >= 1950; i--) comboAnio.addItem(i);

        add(new JLabel("Nombre de Usuario:")); add(inputNombreUsuario);
        add(new JLabel("Nombre Completo:")); add(inputNombreCompleto);
        add(new JLabel("Cédula:")); add(inputCedula);

        add(new JLabel("Fecha de Nacimiento:"));
        JPanel fechaPanel = new JPanel();
        fechaPanel.add(comboDia);
        fechaPanel.add(new JLabel("/"));
        fechaPanel.add(comboMes);
        fechaPanel.add(new JLabel("/"));
        fechaPanel.add(comboAnio);
        add(fechaPanel);

        add(new JLabel("Correo Electrónico:")); add(inputCorreo);
        add(new JLabel("Contraseña:")); add(inputContrasenia);

        add(btnRegistrar); add(btnCancelar);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public String getNombreUsuario() { return inputNombreUsuario.getText(); }
    public String getNombreCompleto() { return inputNombreCompleto.getText(); }
    public String getCedula() { return inputCedula.getText(); }
    public String getCorreo() { return inputCorreo.getText(); }
    public String getContrasenia() { return new String(inputContrasenia.getPassword()); }

    public String getFechaNacimiento() {
        int dia = (int) comboDia.getSelectedItem();
        int mes = (int) comboMes.getSelectedItem();
        int anio = (int) comboAnio.getSelectedItem();
        return String.format("%02d-%02d-%04d", dia, mes, anio); // Formato dd-MM-yyyy
    }

    public void limpiarCampos() {
        inputNombreUsuario.setText("");
        inputNombreCompleto.setText("");
        inputCedula.setText("");
        inputCorreo.setText("");
        inputContrasenia.setText("");
        comboDia.setSelectedIndex(0);
        comboMes.setSelectedIndex(0);
        comboAnio.setSelectedIndex(0);
    }

    public void addRegistrarListener(ActionListener listener) {
        btnRegistrar.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }
}