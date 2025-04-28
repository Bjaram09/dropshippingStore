package com.bryanjara.proyectotienda.views.producto;

import com.bryanjara.proyectotienda.models.Producto;
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

public class ViewProductos extends BaseView {
    private DefaultTableModel modeloTablaProducto;
    private JTable tablaProductos;
    private JButton btnRegistrarProducto;
    private ActionListener deleteListener;
    private ActionListener updateListener;
    private ActionListener changeInventoryListener;
    
    //buscar
    private JTextField txtBuscarProducto;
    private JButton btnBuscarProducto;

    public ViewProductos() {
        setTitle("Gestión de Productos");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(false);
        setSize(1400, 800);
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); 
        panelSuperior.setBackground(BACKGROUND_COLOR);

        panelSuperior.add(createHeaderPanel("Lista de Productos"));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(BACKGROUND_COLOR);
        
        txtBuscarProducto = new JTextField(30);
        btnBuscarProducto = new JButton("Buscar");
        btnBuscarProducto.setSize(50, 30);
        btnBuscarProducto.setBackground(PRIMARY_COLOR);
        btnBuscarProducto.setForeground(Color.WHITE);
        btnBuscarProducto.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscarProducto.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnBuscarProducto.setFocusPainted(false);
        btnBuscarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscarProducto);
        panelBusqueda.add(btnBuscarProducto);
        
        panelSuperior.add(panelBusqueda);
        
        add(panelSuperior, BorderLayout.NORTH);
        

        String[] columnNames = {"ID", "Nombre", "Categoría", "Precio", "Inventario Disponible", "Vendedor", "Acciones"};
        modeloTablaProducto = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        tablaProductos = new JTable(modeloTablaProducto);
        configureTableStyle(tablaProductos);

        configurarColumnas();

        JScrollPane scrollPane = new JScrollPane(tablaProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        btnRegistrarProducto = createStyledButton("Registrar Nuevo Producto", PRIMARY_COLOR);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(BACKGROUND_COLOR);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBotones.add(btnRegistrarProducto);
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

        for (int i = 0; i < tablaProductos.getColumnCount() - 1; i++) {
            tablaProductos.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }


        TableColumn actionsColumn = tablaProductos.getColumnModel().getColumn(6);
        actionsColumn.setPreferredWidth(325);
        actionsColumn.setMaxWidth(325); 
        actionsColumn.setMinWidth(325); 
        actionsColumn.setCellRenderer(new ActionsRenderer());
        actionsColumn.setCellEditor(new ActionsEditor(new JTextField()));
    }

    public void listarProductos(Collection<Producto> productos) {
        modeloTablaProducto.setRowCount(0);
        for (Producto producto : productos) {

            Object[] row = {
                    producto.getID(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    "₡" + producto.getPrecio(),
                    producto.getInventarioDisponible() + " Unidades",   
                    producto.getVendedor().getNombre(),
                    producto
            };
            modeloTablaProducto.addRow(row);
        }
    }

    public void setDeleteListener(ActionListener listener) {
        this.deleteListener = listener;
    }

    public void setUpdateListener(ActionListener listener) {
        this.updateListener = listener;
    }

    public void setChangeInventaryListener(ActionListener listener) {
        this.changeInventoryListener = listener;
    }

    public JTable getTablaProductos() {
        return tablaProductos;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTablaProducto;
    }

    public JButton getBtnRegistrarProducto() {
        return btnRegistrarProducto;
    }
    
    //txt y btnBuscar
    public JTextField getTxtBuscarProducto(){
        return txtBuscarProducto;
    }
    
    public JButton getBtnBuscarProducto(){
        return btnBuscarProducto;
    }

    private class ActionsRenderer extends JPanel implements TableCellRenderer {
        private JButton deleteButton;
        private JButton updateButton;
        private JButton changeInventoryButton;

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

            changeInventoryButton = new JButton("Cambiar Inventario");
            changeInventoryButton.setBackground(PRIMARY_COLOR);
            changeInventoryButton.setForeground(Color.WHITE);
            changeInventoryButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            changeInventoryButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            changeInventoryButton.setFocusPainted(false);
            changeInventoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            add(deleteButton);
            add(updateButton);
            add(changeInventoryButton);
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
        private JButton changeInventoryButton;
        private Producto producto;
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
                if (deleteListener != null && producto != null) {
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
                if (updateListener != null && producto != null) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "update");
                    updateListener.actionPerformed(event);
                }
                fireEditingStopped();
            });

            changeInventoryButton = new JButton("Cambiar Inventario");
            changeInventoryButton.setBackground(PRIMARY_COLOR);
            changeInventoryButton.setForeground(Color.WHITE);
            changeInventoryButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            changeInventoryButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            changeInventoryButton.setFocusPainted(false);
            changeInventoryButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            changeInventoryButton.addActionListener(e -> {
                if (changeInventoryListener != null && producto != null) {
                    ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "changeInventory");
                    changeInventoryListener.actionPerformed(event);
                }
                fireEditingStopped();
            });

            panel.add(deleteButton);
            panel.add(updateButton);
            panel.add(changeInventoryButton);
        }

        public Producto getProducto() {
            return producto;
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            producto = (Producto) value;
            isPushed = true;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return producto;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
}
