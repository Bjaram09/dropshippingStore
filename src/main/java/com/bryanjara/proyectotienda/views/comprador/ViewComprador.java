package com.bryanjara.proyectotienda.views.comprador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.bryanjara.proyectotienda.models.Comprador;
import com.bryanjara.proyectotienda.views.BaseView;

public class ViewComprador extends BaseView {
    private DefaultTableModel modeloTablaComprador;
    private JTable tablaCompradores;
    private JButton btnRegistrarComprador;
    private ActionListener deleteListener;
    private ActionListener updateListener;

    private JTextField txtBuscarComprador;
    private JButton btnBuscarComprador;

    public ViewComprador() {
        setTitle("Gestión de Compradores");
        setSize(1400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.setBackground(BACKGROUND_COLOR);

        panelSuperior.add(createHeaderPanel("Catalogo"));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(BACKGROUND_COLOR);
        txtBuscarComprador = new JTextField(30);
        btnBuscarComprador = new JButton("Buscar");
        btnBuscarComprador.setSize(50, 30);
        btnBuscarComprador.setBackground(PRIMARY_COLOR);
        btnBuscarComprador.setForeground(Color.WHITE);
        btnBuscarComprador.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscarComprador.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnBuscarComprador.setFocusPainted(false);
        btnBuscarComprador.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscarComprador);
        panelBusqueda.add(btnBuscarComprador);

        panelSuperior.add(panelBusqueda);

        add(panelSuperior, BorderLayout.NORTH);

        add(createHeaderPanel("Lista de Compradores"));

        String[] columnNames = {"Cédula", "Nombre", "Correo Electronico", "Nombre Usuario", "Acciones"};
        modeloTablaComprador = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        tablaCompradores = new JTable(modeloTablaComprador);
        configureTableStyle(tablaCompradores);

        configurarColumnas();

        JScrollPane scrollPane = new JScrollPane(tablaCompradores);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnRegistrarComprador = createStyledButton("Registrar Nuevo Comprador", PRIMARY_COLOR);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(BACKGROUND_COLOR);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBotones.add(btnRegistrarComprador);
        add(panelBotones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void configurarColumnas(){
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? TABLE_ROW_COLOR : TABLE_ALTERNATE_ROW_COLOR);
                return c;
            }
        };
        renderer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int i = 0; i < tablaCompradores.getColumnCount() - 1; i++) {
            tablaCompradores.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        TableColumn actionsColumn = tablaCompradores.getColumnModel().getColumn(4);
        actionsColumn.setCellRenderer(new ActionsRenderer());
        actionsColumn.setCellEditor(new ActionsEditor(new JTextField()));
    }

    public void listarCompradores(Collection<Comprador> compradores) {
        modeloTablaComprador.setRowCount(0);
        for (Comprador comprador : compradores) {

            Object[] row = {
                    comprador.getCedulaIdentidad(),
                    comprador.getNombreCompleto(),
                    comprador.getCorreoElectronico(),
                    comprador.getNombreUsuario(),
                    comprador
            };
            System.out.println(comprador);
            modeloTablaComprador.addRow(row);
        }
    }

    public void setDeleteListener(ActionListener listener) {
        this.deleteListener = listener;
    }

    public void setUpdateListener(ActionListener listener) {
        this.updateListener = listener;
    }

    public JTable getTablaCompradores() {
        return tablaCompradores;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTablaComprador;
    }

    public JButton getBtnRegistrarComprador() {
        return btnRegistrarComprador;
    }

    public JTextField getTxtBuscarComprador() {
        return txtBuscarComprador;
    }

    public JButton getBtnBuscarComprador() {
        return btnBuscarComprador;
    }

    private class ActionsRenderer extends JPanel implements TableCellRenderer {
        private JButton deleteButton;
        private JButton updateButton;

        public ActionsRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setBackground(TABLE_ROW_COLOR);

            deleteButton = new JButton("Eliminar");
            deleteButton.setBackground(ACCENT_COLOR);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            deleteButton.setFocusPainted(false);
            deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            updateButton = new JButton("Actualizar");
            updateButton.setBackground(SECONDARY_COLOR);
            updateButton.setForeground(Color.WHITE);
            updateButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            updateButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            updateButton.setFocusPainted(false);
            updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            add(deleteButton);
            add(updateButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(row % 2 == 0 ? TABLE_ROW_COLOR : TABLE_ALTERNATE_ROW_COLOR);
            return this;
        }
    }

    public class ActionsEditor extends DefaultCellEditor {
        private JPanel panel;
        private JButton deleteButton;
        private JButton updateButton;
        private Comprador comprador;
        private boolean isPushed;

        public ActionsEditor(JTextField textField) {
            super(textField);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setBackground(TABLE_ROW_COLOR);

            deleteButton = new JButton("Eliminar");
            deleteButton.setBackground(ACCENT_COLOR);
            deleteButton.setForeground(Color.WHITE);
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            deleteButton.setFocusPainted(false);
            deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            deleteButton.addActionListener(e -> {
                if (deleteListener != null && comprador != null) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "delete");
                    deleteListener.actionPerformed(event);
                }
                fireEditingStopped();
            });

            updateButton = new JButton("Actualizar");
            updateButton.setBackground(SECONDARY_COLOR);
            updateButton.setForeground(Color.WHITE);
            updateButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            updateButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            updateButton.setFocusPainted(false);
            updateButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            updateButton.addActionListener(e -> {
                if (updateListener != null && comprador != null) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "update");
                    updateListener.actionPerformed(event);
                }
                fireEditingStopped();
            });

            panel.add(deleteButton);
            panel.add(updateButton);
        }

        public Comprador getComprador() {
            return comprador;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            comprador = (Comprador) value;
            isPushed = true;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return comprador;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
