package com.bryanjara.proyectotienda.views.vendedor;

import com.bryanjara.proyectotienda.models.Vendedor;
import com.bryanjara.proyectotienda.views.BaseView;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class ViewVendedor extends BaseView {
    private DefaultTableModel modeloTablaVendedor;
    private JTable tablaVendedors;
    private JButton btnRegistrarVendedor;
    private ActionListener deleteListener;
    private ActionListener updateListener;

    public ViewVendedor() {
        setTitle("Gestión de Vendedors");
        setSize(1400, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);

        add(createHeaderPanel("Lista de Vendedors"), BorderLayout.NORTH);

        String[] columnNames = {"Cédula", "Nombre", "Ubicacion", "Correo Contacto", "Telefono", "Acciones"};
        modeloTablaVendedor = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        };

        tablaVendedors = new JTable(modeloTablaVendedor);
        configureTableStyle(tablaVendedors);

        configurarColumnas();

        JScrollPane scrollPane = new JScrollPane(tablaVendedors);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnRegistrarVendedor = createStyledButton("Registrar Nuevo Vendedor", PRIMARY_COLOR);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(BACKGROUND_COLOR);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBotones.add(btnRegistrarVendedor);
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

        for (int i = 0; i < tablaVendedors.getColumnCount() - 1; i++) {
            tablaVendedors.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        TableColumn actionsColumn = tablaVendedors.getColumnModel().getColumn(7);
        actionsColumn.setCellRenderer(new ActionsRenderer());
        actionsColumn.setCellEditor(new ActionsEditor(new JTextField()));
    }

    public void listarVendedors(Collection<Vendedor> vendedores) {
        modeloTablaVendedor.setRowCount(0);
        for (Vendedor vendedor : vendedores) {

            Object[] row = {
                  vendedor.getCedula(),
                    vendedor.getNombre(),
                    vendedor.getUbicacion(),
                    vendedor.getCorreoContacto(),
                    vendedor.getNumeroTelefono()
            };
            modeloTablaVendedor.addRow(row);
        }
    }

    public void setDeleteListener(ActionListener listener) {
        this.deleteListener = listener;
    }

    public void setUpdateListener(ActionListener listener) {
        this.updateListener = listener;
    }

    public JTable getTablaVendedors() {
        return tablaVendedors;
    }

    public JButton getBtnRegistrarVendedor() {
        return btnRegistrarVendedor;
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
        private Vendedor vendedor;
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
                if (deleteListener != null && vendedor != null) {
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
                if (updateListener != null && vendedor != null) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "update");
                    updateListener.actionPerformed(event);
                }
                fireEditingStopped();
            });

            panel.add(deleteButton);
            panel.add(updateButton);
        }

        public Vendedor getVendedor() {
            return vendedor;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            vendedor = (Vendedor) value;
            isPushed = true;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return vendedor;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
