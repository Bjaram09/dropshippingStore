package com.bryanjara.proyectotienda.views.catalogo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bryanjara.proyectotienda.views.BaseView;

import java.awt.*;

public class ViewCarrito extends BaseView {
    private JTable tablaCarrito;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;
    private JButton btnAgregarLineaFactura;

    public ViewCarrito() {
        setTitle("Carrito de Compras");
        setSize(900, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);


        String[] columnNames = {"Nombre", "Categoría", "Precio", "Cantidad", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo permitir editar la cantidad
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) return Integer.class;
                return String.class;
            }
        };

        tablaCarrito = new JTable(modeloTabla);
        configureTableStyle(tablaCarrito);
        JScrollPane scrollPane = new JScrollPane(tablaCarrito);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(BACKGROUND_COLOR);

        btnAgregarLineaFactura = createStyledButton("Agregar a Línea Factura", PRIMARY_COLOR);
        panelBotones.add(btnAgregarLineaFactura);

        btnEliminar = createStyledButton("Eliminar Seleccionado", ACCENT_COLOR);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public JTable getTablaCarrito() {
        return tablaCarrito;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnAgregarLineaFactura() {
        return btnAgregarLineaFactura;
    }

}