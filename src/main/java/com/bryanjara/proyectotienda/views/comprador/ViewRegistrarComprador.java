package com.bryanjara.proyectotienda.views.comprador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.bryanjara.proyectotienda.views.BaseView;

public class ViewRegistrarComprador extends BaseView{
    private JPanel form;
    private JPanel buttonField;
    private JPanel birthDatePanel;
    private JPanel headerPanel;

    private JLabel titleLabel;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField fullNameField;
    private JTextField idField;
    private JTextField emailField;

    private JComboBox<Integer> dayCombo;
    private JComboBox<Integer> monthCombo;
    private JComboBox<Integer> yearCombo;

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel confirmPasswordLabel;
    private JLabel fullNameLabel;
    private JLabel idLabel;
    private JLabel birthDateLabel;
    private JLabel emailLabel;
    private JLabel messageLabel;

    private JButton registerButton;

    public ViewRegistrarComprador() {
        super();
        setTitle("Iniciar Sesion");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        titleLabel = new JLabel("Registration");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        buttonField = new JPanel();
        buttonField.setBackground(BACKGROUND_COLOR);
        buttonField.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        form = new JPanel(new GridBagLayout());
        form.setBackground(BACKGROUND_COLOR);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        idLabel = createStyledLabel("ID:", labelFont);
        fullNameLabel = createStyledLabel("Nombre Completo:", labelFont);
        birthDateLabel = createStyledLabel("Fecha de nacimiento (dd/mm/aaaa):", labelFont);
        emailLabel = createStyledLabel("Email:", labelFont);
        usernameLabel = createStyledLabel("Nombre de Usuario:", labelFont);
        passwordLabel = createStyledLabel("Contraseña:", labelFont);
        confirmPasswordLabel = createStyledLabel("Confirmar Contraseña:", labelFont);

        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);
        idField = createStyledTextField(inputFont);
        fullNameField = createStyledTextField(inputFont);

        dayCombo = new JComboBox<>();
        dayCombo.setBackground(INPUT_BACKGROUND);
        for (int i = 1; i <= 31; i++) dayCombo.addItem(i);

        monthCombo = new JComboBox<>();
        monthCombo.setBackground(INPUT_BACKGROUND);
        for (int i = 1; i <= 12; i++) monthCombo.addItem(i);

        yearCombo = new JComboBox<>();
        yearCombo.setBackground(INPUT_BACKGROUND);
        int currentYear = java.time.LocalDate.now().getYear();
        for (int i = currentYear; i >= 1900; i--) yearCombo.addItem(i);

        birthDatePanel = new JPanel();
        birthDatePanel.setBackground(BACKGROUND_COLOR);
        birthDatePanel.setLayout(new FlowLayout());
        birthDatePanel.add(new JLabel("Dia:"));
        birthDatePanel.add(dayCombo);
        birthDatePanel.add(new JLabel("Mes:"));
        birthDatePanel.add(monthCombo);
        birthDatePanel.add(new JLabel("Año:"));
        birthDatePanel.add(yearCombo);

        emailField = createStyledTextField(inputFont);
        usernameField = createStyledTextField(inputFont);
        passwordField = createStyledPasswordField(inputFont);
        confirmPasswordField = createStyledPasswordField(inputFont);

        int row = 0;
        addFormRow(gbc, idLabel, idField, row++);
        addFormRow(gbc, fullNameLabel, fullNameField, row++);
        addFormRow(gbc, birthDateLabel, birthDatePanel, row++);
        addFormRow(gbc, emailLabel, emailField, row++);
        addFormRow(gbc, usernameLabel, usernameField, row++);
        addFormRow(gbc, passwordLabel, passwordField, row++);
        addFormRow(gbc, confirmPasswordLabel, confirmPasswordField, row++);

        registerButton = new JButton("Registrar Admin");
        registerButton.setBackground(PRIMARY_COLOR);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonField.add(registerButton);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        add(messageLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);
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

    private JPasswordField createStyledPasswordField(Font font) {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(font);
        passwordField.setBackground(INPUT_BACKGROUND);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return passwordField;
    }

    private void addFormRow(GridBagConstraints gbc, JLabel label, JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        form.add(label, gbc);

        gbc.gridx = 1;
        form.add(component, gbc);
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public String getFullName() {
        return fullNameField.getText();
    }

    public String getId() {
        return idField.getText();
    }

    public String getBirthDate() {
        int day = (int) dayCombo.getSelectedItem();
        int month = (int) monthCombo.getSelectedItem();
        int year = (int) yearCombo.getSelectedItem();
        return String.format("%02d/%02d/%d", day, month, year);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public JTextField getTxtCedula() {
        return idField;
    }

    public JButton getBtnRegistrar() {
        return registerButton;
    }

    public void setUsername(String username) {
        usernameField.setText(username);
    }
    
    public void setPassword(String password) {
        passwordField.setText(password);
    }
    
    public void setConfirmPassword(String confirmPassword) {
        confirmPasswordField.setText(confirmPassword);
    }
    
    public void setFullName(String fullName) {
        fullNameField.setText(fullName);
    }
    
    public void setId(String id) {
        idField.setText(id);
    }
    
    public void setBirthDate(int day, int month, int year) {
        dayCombo.setSelectedItem(day);
        monthCombo.setSelectedItem(month);
        yearCombo.setSelectedItem(year);
    }
    
    public void setEmail(String email) {
        emailField.setText(email);
    }

    public void addUpdateListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

}
