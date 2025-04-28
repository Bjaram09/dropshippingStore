package com.bryanjara.proyectotienda.views.catalogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.bryanjara.proyectotienda.models.Producto;
import com.bryanjara.proyectotienda.views.BaseView;

public class ViewCatalogo extends BaseView{
    private DefaultTableModel modeloTablaCatalogo;
    private JTable tablaCatalogo;
    private JButton btnVerCarrito;
    private JButton btnVerListaFavoritos;
    private JButton btnGenerarFactura;
    private ActionListener favoritosListener;
    private ActionListener carritoListener;

    private JTextField txtBuscar;
    private JButton btnBuscar;


    public ViewCatalogo() {
        setTitle("Catalogo");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_COLOR);
        setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(false);
        setSize(1400, 800);

        // Crear un contenedor para el encabezado y el panel de búsqueda
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); // Cambiar a BoxLayout
        panelSuperior.setBackground(BACKGROUND_COLOR);

        // Agregar el encabezado al contenedor
        panelSuperior.add(createHeaderPanel("Catalogo"));

        // Crear el panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(BACKGROUND_COLOR);
        txtBuscar = new JTextField(30);
        btnBuscar = new JButton("Buscar");
        btnBuscar.setSize(50, 30);
        btnBuscar.setBackground(PRIMARY_COLOR);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBuscar);
        panelBusqueda.add(btnBuscar);

        // Agregar el panel de búsqueda al contenedor
        panelSuperior.add(panelBusqueda);

        // Agregar el contenedor al BorderLayout.NORTH
        add(panelSuperior, BorderLayout.NORTH);

        // Configurar la tabla
        String[] columnNames = {"Nombre", "Categoría", "Precio", "Peso", "Dimensiones", "Descripcion", "Vendedor", "Acciones"};
        modeloTablaCatalogo = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        tablaCatalogo = new JTable(modeloTablaCatalogo);
        configureTableStyle(tablaCatalogo);

        configurarColumnas();

        JScrollPane scrollPane = new JScrollPane(tablaCatalogo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Botones inferiores
        btnVerCarrito = createStyledButton("Ver Carrito", PRIMARY_COLOR);
        btnVerListaFavoritos = createStyledButton("Ver Lista de Favoritos", PRIMARY_COLOR);
        btnGenerarFactura = createStyledButton("Generar Factura", PRIMARY_COLOR);

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(BACKGROUND_COLOR);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelBotones.add(btnVerCarrito);
        panelBotones.add(btnVerListaFavoritos);
        panelBotones.add(btnGenerarFactura);
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

        for (int i = 0; i < tablaCatalogo.getColumnCount() - 1; i++) {
            tablaCatalogo.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        TableColumn actionsColumn = tablaCatalogo.getColumnModel().getColumn(7);
        actionsColumn.setPreferredWidth(325);
        actionsColumn.setMaxWidth(325);
        actionsColumn.setMinWidth(325);
        actionsColumn.setCellRenderer(new ActionsRenderer());
        actionsColumn.setCellEditor(new ActionsEditor(new JTextField()));
    }

    public void listarProductos(Collection<Producto> productos) {
        modeloTablaCatalogo.setRowCount(0);
        for (Producto producto : productos) {

            Object[] row = {
                    producto.getNombre(),
                    producto.getCategoria(),
                    "₡" + producto.getPrecio(),
                    producto.getPeso() + " Kg",
                    producto.getDimensiones(),
                    producto.getDescripcion(),
                    producto.getVendedor().getNombre(),
                    producto
            };
            modeloTablaCatalogo.addRow(row);
        }
    }

    public void setCarritoListener(ActionListener listener) {
        this.carritoListener = listener;
    }

    public void setFavoritosListener(ActionListener listener) {
        this.favoritosListener = listener;
    }

    public JTable getTablaCatalogo() {
        return tablaCatalogo;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTablaCatalogo;
    }

    public JButton getBtnVerCarrito() {
        return btnVerCarrito;
    }
    
    public JButton getBtnVerListaFavoritos(){
        return btnVerListaFavoritos;
    }
    
    public JButton getBtnGenerarFactura(){
        return btnGenerarFactura;
    }

    public JTextField getTxtBuscar() {
        return txtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    private class ActionsRenderer extends JPanel implements TableCellRenderer {
        public ActionsRenderer() {
            setLayout(new BorderLayout());
        }
    
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            removeAll();
            setBackground(row % 2 == 0 ? TABLE_ROW_COLOR : TABLE_ALTERNATE_ROW_COLOR);
    
            JPanel panel = createActionsPanel(e -> {}, e -> {});
            add(panel, BorderLayout.CENTER);
    
            return this;
        }
    }

    public class ActionsEditor extends DefaultCellEditor {
        private final JPanel panel;
        private Producto producto;

        public ActionsEditor(JTextField textField) {
            super(textField);

            // Create the panel with action listeners
            panel = createActionsPanel(
                e -> {
                    if (favoritosListener != null && producto != null) {
                        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "favorito");
                        favoritosListener.actionPerformed(event);
                    }
                    fireEditingStopped();
                },
                e -> {
                    if (carritoListener != null && producto != null) {
                        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "carrito");
                        carritoListener.actionPerformed(event);
                    }
                    fireEditingStopped();
                }
            );
        }
        

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            producto = (Producto) value;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return producto;
        }


        public Producto getProducto() {
            return producto;
        }
    }

    private JPanel createActionsPanel(ActionListener favoritosListener, ActionListener carritoListener) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(TABLE_ROW_COLOR);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
    
        JButton favoritosButton = new JButton("Añadir a Favoritos");
        favoritosButton.setBackground(ACCENT_COLOR);
        favoritosButton.setForeground(Color.WHITE);
        favoritosButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        favoritosButton.setFocusPainted(false);
        favoritosButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favoritosButton.addActionListener(favoritosListener);
    
        JButton carritoButton = new JButton("Agregar al Carrito");
        carritoButton.setBackground(SECONDARY_COLOR);
        carritoButton.setForeground(Color.WHITE);
        carritoButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        carritoButton.setFocusPainted(false);
        carritoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        carritoButton.addActionListener(carritoListener);
    
        gbc.gridx = 0; // First column
        panel.add(favoritosButton, gbc);

        gbc.gridx = 1; // Second column
        panel.add(carritoButton, gbc);
    
        return panel;
    }
}
