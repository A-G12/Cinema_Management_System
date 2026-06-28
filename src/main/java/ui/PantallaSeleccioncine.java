package ui;

import com.mycompany.proyecto.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PantallaSeleccioncine extends JFrame {
    private final GestionCines gestion;
    private JComboBox<String> comboEstado;
    private JComboBox<String> comboMunicipio;

    private static final String[] ESTADOS = {"J11 - Jalisco", "N11 - Nuevo Leon", "E11 - Estado de Mexico", "C11 - Chihuahua", "S11 - Sinaloa"};
    private static final String[][] MUNICIPIOS = {
            {"A","B","C","D","E","F","G","H","I","J"},
            {"A","B","C","D","E","F","G","H","I","J"},
            {"A","B","C","D","E","F","G","H","I","J"},
            {"A","B","C","D","E","F","G","H","I","J"},
            {"A","B","C","D","E","F","G","H","I","J"}
    };

    public PantallaSeleccioncine(GestionCines gestion) {
        this.gestion = gestion;
        setTitle("Cine — Seleccion de Sucursal");
        setSize(520, 560);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel fondo = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, Ventanaprincipal.COLOR_FONDO,
                        0, getHeight(), new Color(40, 0, 60)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        setContentPane(fondo);

        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(25, 50, 25, 50));
        tarjeta.setPreferredSize(new Dimension(420, 440));


        JLabel icono = new JLabel("🎬");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        Dimension dimensionesIcono = new Dimension(80, 80);
        icono.setPreferredSize(dimensionesIcono);
        icono.setMinimumSize(dimensionesIcono);
        icono.setMaximumSize(dimensionesIcono);

        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setVerticalAlignment(SwingConstants.CENTER);
        icono.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel titulo = Ventanaprincipal.crearLabel("Cine<3",
                Ventanaprincipal.FUENTE_TITULO,
                Ventanaprincipal.COLOR_ACENTO);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);


        JLabel subtitulo = Ventanaprincipal.crearLabel("Selecciona tu sucursal",
                Ventanaprincipal.FUENTE_NORMAL,
                Ventanaprincipal.COLOR_SUBTEXTO);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Ventanaprincipal.COLOR_BORDE);
        sep.setMaximumSize(new Dimension(300, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblEstado = Ventanaprincipal.crearLabel("Estado",
                Ventanaprincipal.FUENTE_PEQUEÑA,
                Ventanaprincipal.COLOR_SUBTEXTO);
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblMunicipio = Ventanaprincipal.crearLabel("Municipio (Sucursal)",
                Ventanaprincipal.FUENTE_PEQUEÑA,
                Ventanaprincipal.COLOR_SUBTEXTO);
        lblMunicipio.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMunicipio.setHorizontalAlignment(SwingConstants.CENTER);

        comboEstado = Ventanaprincipal.crearCombo(ESTADOS);
        comboEstado.setMaximumSize(new Dimension(260, 40));
        comboEstado.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboMunicipio = Ventanaprincipal.crearCombo(MUNICIPIOS[0]);
        comboMunicipio.setMaximumSize(new Dimension(260, 40));
        comboMunicipio.setAlignmentX(Component.CENTER_ALIGNMENT);

        comboEstado.addActionListener(e -> {
            int idx = comboEstado.getSelectedIndex();
            comboMunicipio.setModel(new DefaultComboBoxModel<>(MUNICIPIOS[idx]));
        });

        JButton btnEntrar = Ventanaprincipal.crearBoton(
                "Entrar al cine",
                Ventanaprincipal.COLOR_ACENTO,
                Color.WHITE
                );
        btnEntrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEntrar.setMaximumSize(new Dimension(260, 45));
        btnEntrar.addActionListener(e -> seleccionarCine());

        tarjeta.add(icono);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(subtitulo);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(sep);
        tarjeta.add(Box.createVerticalStrut(15));

        tarjeta.add(lblEstado);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(comboEstado);

        tarjeta.add(Box.createVerticalStrut(18));

        tarjeta.add(lblMunicipio);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(comboMunicipio);

        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(btnEntrar);

        fondo.add(tarjeta);
    }

    private void seleccionarCine() {
        // Obtiene el estado
        String estadoCompleto = (String) comboEstado.getSelectedItem();
        String idEstado = estadoCompleto.substring(0, 3);
        // Obtiene el municipio
        String idMunicipio = ((String) comboMunicipio.getSelectedItem()).trim();

        //busca el cine con los datos
        Cine cine = gestion.seleccionar(idEstado,idMunicipio );
        //verifica que el cine exista
        if (cine == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo cargar el cine seleccionado.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] opciones = {"Administrador", "Cliente"};
        int tipo = JOptionPane.showOptionDialog(this,
                "¿Como deseas ingresar?", "Tipo de usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[1]);

        if (tipo == 0) {
            new PantallaLogin(gestion, cine).setVisible(true);
        } else if (tipo == 1) {
            new PantallaCliente(gestion, cine).setVisible(true);
        }
        dispose();
    }
}