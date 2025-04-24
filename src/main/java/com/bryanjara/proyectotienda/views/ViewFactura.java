/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Modelo.Factura;
import Modelo.LineaFactura;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Fio
 */
public class ViewFactura extends JFrame {

    private final DefaultListModel<LineaFactura> modeloLineas;
    private final JList<LineaFactura> listaLineas;
    private final JTextField inputImpuesto;
    private final JTextField inputTotal;

    private final JButton btnAgregar = new JButton("Registrar Factura");
    private final JButton btnCancelar = new JButton("Cancelar");
    private final JButton btnCalcularTotal = new JButton("Calcular Total");

    public ViewFactura() {
        setTitle("Registrar Factura");
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10));

        modeloLineas = new DefaultListModel<>();
        listaLineas = new JList<>(modeloLineas);
        listaLineas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane scroll = new JScrollPane(listaLineas);

        inputImpuesto = new JTextField();
        inputImpuesto.setEditable(false);
        inputTotal = new JTextField();
        inputTotal.setEditable(false);

        JPanel panelCentro = new JPanel(new GridLayout(2, 2, 10, 10));
        panelCentro.add(new JLabel("Impuesto (13%):"));
        panelCentro.add(inputImpuesto);
        panelCentro.add(new JLabel("Monto Total:"));
        panelCentro.add(inputTotal);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCalcularTotal);
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);

        add(scroll, BorderLayout.CENTER);
        add(panelCentro, BorderLayout.SOUTH);
        add(panelBotones, BorderLayout.NORTH);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

    public List<LineaFactura> getLineasSeleccionadas() {
        return listaLineas.getSelectedValuesList(); 
    }
    
    public Factura construirFactura() {
        List<LineaFactura> seleccionadas = getLineasSeleccionadas();
        double subtotal = 0;

        for (LineaFactura lf : seleccionadas) {
            subtotal += lf.getMontoTotal();
        }

        double impuestoRate = 0.13;
        double impuestoCalculado = subtotal * impuestoRate;
        double total = subtotal + impuestoCalculado;

        Factura factura = new Factura();
        factura.setItemFactura(new ArrayList<>(seleccionadas));
        factura.setImpuesto(impuestoCalculado);
        factura.setTotal(total);

        return factura;
    }

    public void limpiarCampos() {
        modeloLineas.clear();
        inputImpuesto.setText("");
        inputTotal.setText("");
    }
}
