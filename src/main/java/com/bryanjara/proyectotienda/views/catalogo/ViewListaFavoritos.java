package com.bryanjara.proyectotienda.views.catalogo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.bryanjara.proyectotienda.views.BaseView;

import java.awt.*;

public class ViewListaFavoritos extends BaseView {
    private JTable tablaFavoritos;
    private DefaultTableModel modeloTabla;
    private JButton btnEliminar;

    public ViewListaFavoritos() {
        setTitle("Lista de Favoritos");
        setSize(800, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        String[] columnNames = {"Nombre", "Categoría", "Precio"};
        modeloTabla = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaFavoritos = new JTable(modeloTabla);
        configureTableStyle(tablaFavoritos);
        JScrollPane scrollPane = new JScrollPane(tablaFavoritos);
        add(scrollPane, BorderLayout.CENTER);

        btnEliminar = createStyledButton("Eliminar Seleccionado", ACCENT_COLOR);
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setBackground(BACKGROUND_COLOR);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public JTable getTablaFavoritos() {
        return tablaFavoritos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }
}