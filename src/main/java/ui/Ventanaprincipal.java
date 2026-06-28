package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.mycompany.proyecto.*;

public class Ventanaprincipal {

    public static final Color COLOR_FONDO = new Color(15, 15, 25);
    public static final Color COLOR_PANEL = new Color(25, 25, 40);
    public static final Color COLOR_ACENTO = new Color(220, 50, 50);
    public static final Color COLOR_ACENTO2 = new Color(255, 180, 0);
    public static final Color COLOR_TEXTO = new Color(240, 240, 240);
    public static final Color COLOR_SUBTEXTO = new Color(160, 160, 180);
    public static final Color COLOR_CAMPO = new Color(35, 35, 55);
    public static final Color COLOR_BORDE = new Color(60, 60, 90);

    public static final Font FUENTE_TITULO = new Font("Segoe UI Emoji", Font.BOLD, 26);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI Emoji", Font.BOLD, 18);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI Emoji", Font.PLAIN, 14);
    public static final Font FUENTE_PEQUEÑA = new Font("Segoe UI Emoji", Font.PLAIN, 12);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            GestionCines gestion = new GestionCines();
            new PantallaSeleccioncine(gestion).setVisible(true);
        });
    }

    public static void estilizarLista(JList<?> lista) {
        lista.setBackground(new Color(28, 28, 45));
        lista.setForeground(COLOR_TEXTO);
        lista.setFont(FUENTE_NORMAL);
        lista.setSelectionBackground(COLOR_ACENTO);
        lista.setSelectionForeground(Color.WHITE);
        lista.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
    }

    public static void estilizarScroll(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        scroll.getViewport().setBackground(COLOR_CAMPO);
    }

    public static void estilizarArea(JTextArea area) {
        area.setBackground(COLOR_CAMPO);
        area.setForeground(COLOR_TEXTO);
        area.setCaretColor(COLOR_ACENTO);
        area.setFont(FUENTE_NORMAL);
        area.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
    }

    public static JButton crearBoton(String texto, Color fondo, Color texto2) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(fondo.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(fondo.brighter());
                } else {
                    g2.setColor(fondo);
                }

                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);

                g2.setColor(texto2);
                g2.setFont(getFont());

                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        btn.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        btn.setForeground(texto2);

        btn.setMargin(new Insets(6, 12, 6, 12));

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 42));

        return btn;
    }

    public static JTextField crearCampo(int columnas) {
        JTextField campo = new JTextField(columnas);
        campo.setBackground(COLOR_CAMPO);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_ACENTO);
        campo.setFont(FUENTE_NORMAL);

        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        return campo;
    }

    public static JComboBox<String> crearCombo(String[] opciones) {
        JComboBox<String> combo = new JComboBox<>(opciones);
        combo.setBackground(COLOR_CAMPO);
        combo.setForeground(COLOR_TEXTO);
        combo.setFont(FUENTE_NORMAL);
        combo.setBorder(BorderFactory.createLineBorder(COLOR_BORDE, 1));
        combo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                lbl.setFont(FUENTE_NORMAL);

                lbl.setBorder(new EmptyBorder(4, 8, 4, 8));
                if (isSelected) {
                    lbl.setBackground(COLOR_ACENTO);
                    lbl.setForeground(Color.WHITE);
                } else {
                    lbl.setBackground(COLOR_CAMPO);
                    lbl.setForeground(COLOR_TEXTO);
                }

                return lbl;
            }
        });

        return combo;
    }

    public static JLabel crearLabel(String texto, Font fuente, Color color) {
        JLabel lbl = new JLabel(texto);

        lbl.setFont(new Font("Segoe UI Emoji", fuente.getStyle(), fuente.getSize() + 1));

        lbl.setForeground(color);

        lbl.setBorder(new EmptyBorder(4, 0, 4, 0));

        return lbl;
    }

    public static JPanel crearPanelTarjeta() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(COLOR_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(COLOR_BORDE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
            }
        };

        panel.setOpaque(false);
        return panel;
    }

    private void limpiarFormulario(JPanel panel) {
        for (Component c : panel.getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("");
            } else if (c instanceof JFormattedTextField) {
                ((JFormattedTextField) c).setValue(null);
                ((JFormattedTextField) c).setText("");
            } else if (c instanceof JTextArea) {
                ((JTextArea) c).setText("");
            } else if (c instanceof JComboBox) {
                ((JComboBox<?>) c).setSelectedIndex(0);
            } else if (c instanceof JList) {
                if (((JList<?>) c).getModel() instanceof DefaultListModel) {
                    ((DefaultListModel<?>) ((JList<?>) c).getModel()).clear();
                }
            } else if (c instanceof JLabel) {
                JLabel lbl = (JLabel) c;
                if (lbl.getText().startsWith("✔") || lbl.getText().startsWith("✖") || lbl.getText().contains("incorrectas")) {
                    lbl.setText(" ");
                }
            }
        }
    }
}


