package com.bryanjara.proyectotienda.views;

import com.bryanjara.proyectotienda.models.Factura;
import com.bryanjara.proyectotienda.models.LineaFactura;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ViewFactura extends BaseView {
    private final DefaultListModel<LineaFactura> modeloLineas;
    private final JList<LineaFactura> listaLineas;
    private final JTextField inputImpuesto;
    private final JTextField inputTotal;

    private final JButton btnAgregar;
    private final JButton btnCancelar;
    private final JButton btnCalcularTotal;

    public ViewFactura() {
        setTitle("Registrar Factura");
        setSize(700, 500);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);

        JPanel headerPanel = createHeaderPanel("Registrar Factura");
        add(headerPanel, BorderLayout.NORTH);

        modeloLineas = new DefaultListModel<>();
        listaLineas = new JList<>(modeloLineas);
        listaLineas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaLineas.setBackground(TABLE_ALTERNATE_ROW_COLOR);
        listaLineas.setSelectionBackground(SECONDARY_COLOR);
        listaLineas.setSelectionForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(listaLineas);
        scroll.setBorder(BorderFactory.createTitledBorder("Lineas de Factura"));
        add(scroll, BorderLayout.CENTER);

        inputImpuesto = new JTextField();
        inputTotal = new JTextField();
        inputImpuesto.setEditable(false);
        inputTotal.setEditable(false);
        inputImpuesto.setBackground(INPUT_BACKGROUND);
        inputTotal.setBackground(INPUT_BACKGROUND);

        JPanel panelCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCampos.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        panelCampos.setBackground(BACKGROUND_COLOR);

        panelCampos.add(new JLabel("Impuesto (13%):"));
        panelCampos.add(inputImpuesto);
        panelCampos.add(new JLabel("Monto Total:"));
        panelCampos.add(inputTotal);

        add(panelCampos, BorderLayout.SOUTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBotones.setBackground(BACKGROUND_COLOR);

        btnCalcularTotal = createStyledButton("Calcular Total", SECONDARY_COLOR);
        btnAgregar = createStyledButton("Registrar Factura", PRIMARY_COLOR);
        btnCancelar = createStyledButton("Cancelar", ACCENT_COLOR);

        panelBotones.add(btnCalcularTotal);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);

        add(panelBotones, BorderLayout.PAGE_END);
    }

    public void cargarLineasFactura(List<LineaFactura> lineas) {
        modeloLineas.clear();
        if (lineas != null) {
            for (LineaFactura lf : lineas) {
                modeloLineas.addElement(lf);
            }
        }
    }

    public void actualizarImpuesto(double impuestoCalculado) {
        inputImpuesto.setText(String.format("%.2f", impuestoCalculado));
    }

    public void actualizarTotal(double total) {
        inputTotal.setText(String.format("%.2f", total));
    }

    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void addCancelarListener(ActionListener listener) {
        btnCancelar.addActionListener(listener);
    }

    public void addCalcularTotalListener(ActionListener listener) {
        btnCalcularTotal.addActionListener(listener);
    }

    public Factura construirFactura() {
        List<LineaFactura> lineas = new ArrayList<>();
        for (int i = 0; i < modeloLineas.size(); i++) {
            lineas.add(modeloLineas.get(i));
        }

        double subtotal = 0;
        for (LineaFactura lf : lineas) {
            subtotal += lf.getMontoTotal();
        }

        double impuestoRate = 0.13;
        double impuestoCalculado = subtotal * impuestoRate;
        double total = subtotal + impuestoCalculado;

        Factura factura = new Factura();
        factura.setItemFactura(new ArrayList<>(lineas));
        factura.setImpuesto(impuestoCalculado);
        factura.setTotal(total);

        return factura;
    }

    public DefaultListModel<LineaFactura> getModeloLineas() {
        return modeloLineas;
    }
}
