package com.bryanjara.proyectotienda.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RegistrationView extends BaseView {

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
    private JButton loginAdminButton;
    private JButton loginCompradorButton;

    public RegistrationView() {
        super();
        setTitle("Iniciar Sesion");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
        birthDateLabel = createStyledLabel("Fecha de nacimiento (dd/MM/yyyy):", labelFont);
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

        loginAdminButton = new JButton("Loguearse como Admin");
        loginAdminButton.setBackground(PRIMARY_COLOR);
        loginAdminButton.setForeground(Color.WHITE);
        loginAdminButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginAdminButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        loginAdminButton.setFocusPainted(false);
        loginAdminButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonField.add(loginAdminButton);

        loginCompradorButton = new JButton("Loguearse como Comprador");
        loginCompradorButton.setBackground(PRIMARY_COLOR);
        loginCompradorButton.setForeground(Color.WHITE);
        loginCompradorButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginCompradorButton.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        loginCompradorButton.setFocusPainted(false);
        loginCompradorButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonField.add(loginCompradorButton);

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

    public void addRegisterListener(ActionListener listener) {
        registerButton.addActionListener(listener);
    }

    public void addLoginAdminListener(ActionListener listener) {
        loginAdminButton.addActionListener(listener);
    }

    public void addLoginCompradorListener(ActionListener listener) {
        loginCompradorButton.addActionListener(listener);
    }

    public void showRegistrationForm(boolean show) {
        usernameLabel.setVisible(show);
        usernameField.setVisible(show);
        passwordLabel.setVisible(show);
        passwordField.setVisible(show);
        registerButton.setVisible(show);
        fullNameLabel.setVisible(show);
        fullNameField.setVisible(show);
        idLabel.setVisible(show);
        idField.setVisible(show);
        birthDateLabel.setVisible(show);
        dayCombo.setVisible(show);
        monthCombo.setVisible(show);
        yearCombo.setVisible(show);
        emailLabel.setVisible(show);
        emailField.setVisible(show);
    }

    public void showLoginButtons(boolean show) {
        loginAdminButton.setVisible(show);
        loginCompradorButton.setVisible(show);
        fullNameLabel.setVisible(!show);
        fullNameField.setVisible(!show);
        idLabel.setVisible(!show);
        idField.setVisible(!show);
        birthDateLabel.setVisible(!show);
        birthDatePanel.setVisible(!show);
        dayCombo.setVisible(!show);
        monthCombo.setVisible(!show);
        yearCombo.setVisible(!show);
        emailLabel.setVisible(!show);
        emailField.setVisible(!show);
        usernameLabel.setVisible(show);
        usernameField.setVisible(show);
        passwordLabel.setVisible(show);
        passwordField.setVisible(show);
        confirmPasswordLabel.setVisible(!show);
        confirmPasswordField.setVisible(!show);
    }
}