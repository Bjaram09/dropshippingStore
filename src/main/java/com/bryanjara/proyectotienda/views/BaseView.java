package com.bryanjara.proyectotienda.views;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class BaseView extends JFrame {
    protected static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    protected static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    protected static final Color ACCENT_COLOR = new Color(231, 76, 60);
    protected static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    protected static final Color TABLE_HEADER_COLOR = new Color(52, 73, 94);
    protected static final Color TABLE_ROW_COLOR = Color.WHITE;
    protected static final Color TABLE_ALTERNATE_ROW_COLOR = new Color(245, 245, 245);
    protected static final Color INPUT_BACKGROUND = Color.WHITE;

    protected JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    protected JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    protected void configureTableStyle(JTable table) {
        table.setRowHeight(40);
        table.setIntercellSpacing(new Dimension(10, 10));
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setBackground(TABLE_ROW_COLOR);
        table.setSelectionForeground(ACCENT_COLOR);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setBackground(TABLE_HEADER_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 40));
    }
}
