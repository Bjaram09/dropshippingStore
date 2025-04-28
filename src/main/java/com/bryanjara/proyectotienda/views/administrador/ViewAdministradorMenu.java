package com.bryanjara.proyectotienda.views.administrador;

import com.bryanjara.proyectotienda.controllers.*;
import com.bryanjara.proyectotienda.views.*;
import com.bryanjara.proyectotienda.views.comprador.ViewComprador;
import com.bryanjara.proyectotienda.views.producto.ViewProductos;
import com.bryanjara.proyectotienda.views.vendedor.ViewVendedor;

import javax.swing.*;
import java.awt.*;

public class ViewAdministradorMenu extends BaseView {
    public ViewAdministradorMenu() {
        setTitle("Dashboard - Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(400, 80)); 
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 25));

        JLabel titleLabel = new JLabel("Bienvenido Admin!");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); 
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton vendedorButton = createStyledButton("Administrar Vendedores", SECONDARY_COLOR);
        JButton consultarCompradoresButton = createStyledButton("Administrar Compradores", SECONDARY_COLOR);
        JButton productosButton = createStyledButton("Administrar Productos", ACCENT_COLOR);

        Dimension buttonSize = new Dimension(300, 50); 
        productosButton.setPreferredSize(buttonSize);
        vendedorButton.setPreferredSize(buttonSize);
        consultarCompradoresButton.setPreferredSize(buttonSize);

        buttonPanel.add(consultarCompradoresButton);
        buttonPanel.add(vendedorButton);
        buttonPanel.add(productosButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Action Listeners
        productosButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(ViewAdministradorMenu.this, "Navengando a Producto...");
            ViewProductos viewProductos = new ViewProductos();
            ProductoController productoController = new ProductoController(viewProductos);
            productoController.iniciar();
            viewProductos.setVisible(true);
        });

        vendedorButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(ViewAdministradorMenu.this, "Navengando a Vendedor...");
            ViewVendedor viewVendedor = new ViewVendedor();

            VendedorController vendedorController = new VendedorController(viewVendedor);
            vendedorController.iniciar();
            viewVendedor.setVisible(true);
        });

        consultarCompradoresButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(ViewAdministradorMenu.this, "Navengando a Comprador...");
            ViewComprador ViewComprador = new ViewComprador();

            CompradorController vendedorController = new CompradorController(ViewComprador);
            vendedorController.iniciar();
            ViewComprador.setVisible(true);
        });

        setLocationRelativeTo(null);
    }
}

