package ui;
import java.awt.*;
import javax.swing.*;
import com.mycompany.proyecto.*;
import javax.swing.border.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import javax.swing.text.*;
//modifcar peli con solapamienti
public class PantallaAdmin extends JFrame {
    private final GestionCines gestion;
    private DefaultListModel<String> modeloPeliculas = new DefaultListModel<>();
    private final Cine cine;
    private JPanel panelContenido;
    private CardLayout cardLayout;
    private String nombrePeliculaActual = "";
    private int horariosRestantes = 0;
    private static final String[] CLASIFICACIONES = {"AA", "A", "B", "B15", "C", "D"};
    private static final String[] GENEROS = {"Accion", "Aventuras", "Ciencia Ficcion", "Comedia", "Drama", "Thriller", "Suspenso", "Terror", "Romance", "Animacion"};

    public PantallaAdmin(GestionCines gestion, Cine cine) {
        this.gestion = gestion;
        this.cine = cine;
        setTitle("Cine — Administrador | " + cine.getIdEstado() + "-" + cine.getIdMunicipio());
        setSize(1000, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Ventanaprincipal.COLOR_FONDO);
        setContentPane(root);

        root.add(crearMenuLateral(), BorderLayout.WEST);

        cardLayout = new CardLayout();
        panelContenido = new JPanel(cardLayout);
        panelContenido.setBackground(Ventanaprincipal.COLOR_FONDO);

        panelContenido.add(crearPanelBienvenida(), "inicio");
        panelContenido.add(crearPanelAltaPelicula(), "alta");
        panelContenido.add(crearPanelAltaHorario(), "horario");
        panelContenido.add(crearPanelBajaPelicula(), "baja");
        panelContenido.add(crearPanelBajaHorario(), "bajaHorario");
        panelContenido.add(crearPanelModificar(), "modificar");
        panelContenido.add(crearPanelConsultar(), "consultar");
        panelContenido.add(crearPanelCartelera(), "cartelera");
        panelContenido.add(crearPanelCombo(), "combo");

        root.add(panelContenido, BorderLayout.CENTER);
    }

    private JPanel crearPanelCartelera() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(620, 500));

        JLabel titulo = Ventanaprincipal.crearLabel("📋 Cartelera General", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        controles.setOpaque(false);
        controles.setAlignmentX(LEFT_ALIGNMENT);
        JComboBox<String> cOrd = Ventanaprincipal.crearCombo(new String[]{"Ascendente", "Descendente"});
        JComboBox<String> cTipo = Ventanaprincipal.crearCombo(new String[]{"Por Fecha", "Por Nombre"});
        JButton btnVer = Ventanaprincipal.crearBoton("Ver Cartelera", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btnVer.setPreferredSize(new Dimension(140, 34));
        controles.add(Ventanaprincipal.crearLabel("Orden:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        controles.add(cOrd);
        controles.add(Ventanaprincipal.crearLabel("Criterio:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        controles.add(cTipo);
        controles.add(btnVer);

        String[] cols = {"Pelicula", "Sala", "Hora", "Fecha"};
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        JTable tabla = new JTable(tableModel);
        tabla.setBackground(Ventanaprincipal.COLOR_CAMPO);
        tabla.setForeground(Ventanaprincipal.COLOR_TEXTO);
        tabla.setFont(Ventanaprincipal.FUENTE_NORMAL);
        tabla.setGridColor(Ventanaprincipal.COLOR_BORDE);
        tabla.getTableHeader().setBackground(Ventanaprincipal.COLOR_PANEL);
        tabla.getTableHeader().setForeground(Ventanaprincipal.COLOR_ACENTO2);
        tabla.getTableHeader().setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        tabla.setRowHeight(28);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 340));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE));
        scroll.setAlignmentX(LEFT_ALIGNMENT);

        btnVer.addActionListener(e -> {
            tableModel.setRowCount(0);
            boolean asc = cOrd.getSelectedIndex() == 0;
            boolean porFecha = cTipo.getSelectedIndex() == 0;
            NodoPelicula nodo = cine.peliculas.cabeza;
            int cnt = 0;
            CarteleraGeneral[] arr = new CarteleraGeneral[200];
            while (nodo != null) {
                Pelicula pel = nodo.pelicula;
                for (int i = 0; i < pel.totalHorarios && cnt < 200; i++) {
                    arr[cnt++] = new CarteleraGeneral(pel.nombre, pel.horarios[i].sala, pel.horarios[i].hora, pel.horarios[i].fecha);
                }
                nodo = nodo.siguiente;
            }
            CarteleraGeneral.quicksort(arr, 0, cnt - 1, asc, porFecha);
            for (int i = 0; i < cnt; i++) {
                tableModel.addRow(new Object[]{arr[i].nombrePelicula, arr[i].sala, arr[i].hora, arr[i].fecha});

            }
        });

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(controles);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(scroll);
        p.add(tarjeta);
        return p;
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Ventanaprincipal.COLOR_PANEL);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setBorder(new EmptyBorder(24, 16, 24, 16));

        JLabel logo = new JLabel("🎬 Cine");
        logo.setFont(Ventanaprincipal.FUENTE_SUBTITULO);
        logo.setForeground(Ventanaprincipal.COLOR_ACENTO);
        logo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel rol = new JLabel("  Administrador");
        rol.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        rol.setForeground(Ventanaprincipal.COLOR_ACENTO2);
        rol.setAlignmentX(LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Ventanaprincipal.COLOR_BORDE);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        menu.add(logo);
        menu.add(Box.createVerticalStrut(4));
        menu.add(rol);
        menu.add(Box.createVerticalStrut(20));
        menu.add(sep);
        menu.add(Box.createVerticalStrut(16));

        agregarItemMenu(menu, "🏠  Inicio", "inicio");
        agregarItemMenu(menu, "➕  Alta de Pelicula", "alta");
        agregarItemMenu(menu, "🕐  Alta de Horario", "horario");
        agregarItemMenu(menu, "🗑️  Baja de Pelicula", "baja");
        agregarItemMenu(menu, "🗑️  Baja de Horario", "bajaHorario");
        agregarItemMenu(menu, "✏️  Modificar Pelicula", "modificar");
        agregarItemMenu(menu, "🔍  Consultar Pelicula", "consultar");
        agregarItemMenu(menu, "📋  Ver Cartelera", "cartelera");
        agregarItemMenu(menu, "🍿  Atender Dulceria", "combo");

        menu.add(Box.createVerticalGlue());

        JButton btnSalir = Ventanaprincipal.crearBoton("⬅  Cerrar sesion", new Color(60, 20, 20), Ventanaprincipal.COLOR_ACENTO);
        btnSalir.setAlignmentX(LEFT_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnSalir.addActionListener(e -> {
            new PantallaSeleccioncine(gestion).setVisible(true);
            dispose();
        });
        menu.add(btnSalir);

        return menu;
    }

    private void agregarItemMenu(JPanel menu, String texto, String card) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isRollover()) {
                    g2.setColor(new Color(50, 50, 80));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }

                g2.setColor(getForeground());
                g2.setFont(getFont());

                FontMetrics fm = g2.getFontMetrics();
                int x = 10;
                int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        btn.setFont(Ventanaprincipal.FUENTE_NORMAL);
        btn.setForeground(Ventanaprincipal.COLOR_TEXTO);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(e -> {

            JPanel nuevoPanel = null;

            switch (card) {
                case "alta":        nuevoPanel = crearPanelAltaPelicula(); break;
                case "horario":
                    nombrePeliculaActual = "";
                    nuevoPanel = crearPanelAltaHorario();
                    break;
                case "baja":        nuevoPanel = crearPanelBajaPelicula(); break;
                case "bajaHorario": nuevoPanel = crearPanelBajaHorario(); break;
                case "modificar":   nuevoPanel = crearPanelModificar(); break;
                case "consultar":   nuevoPanel = crearPanelConsultar(); break;
                case "cartelera":   nuevoPanel = crearPanelCartelera(); break;
                case "combo":       nuevoPanel = crearPanelCombo(); break;
                case "inicio":      nuevoPanel = crearPanelBienvenida(); break;
            }

            if (nuevoPanel != null) {
                panelContenido.add(nuevoPanel, card);
                cardLayout.show(panelContenido, card);

                // Refresh visual
                panelContenido.revalidate();
                panelContenido.repaint();
            }
        });

        menu.add(btn);
        menu.add(Box.createVerticalStrut(4));
    }

    private JPanel crearPanelBienvenida() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);

        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(25, 60, 40, 60));

        JLabel ic = new JLabel("🎬");
        ic.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        ic.setAlignmentX(CENTER_ALIGNMENT);
        ic.setHorizontalAlignment(SwingConstants.CENTER);

        Dimension dIcono = new Dimension(100, 100);
        ic.setPreferredSize(dIcono);
        ic.setMinimumSize(dIcono);
        ic.setMaximumSize(dIcono);

        JLabel t = Ventanaprincipal.crearLabel("Bienvenido, Administrador", Ventanaprincipal.FUENTE_TITULO, Ventanaprincipal.COLOR_TEXTO);
        t.setAlignmentX(CENTER_ALIGNMENT);

        JLabel s = Ventanaprincipal.crearLabel("Cine: " + cine.getIdEstado() + " — Sucursal " + cine.getIdMunicipio(), Ventanaprincipal.FUENTE_NORMAL, Ventanaprincipal.COLOR_SUBTEXTO);
        s.setAlignmentX(CENTER_ALIGNMENT);

        tarjeta.add(ic);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(t);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(s);

        p.add(tarjeta);
        return p;
    }

    private JPanel crearPanelAltaPelicula() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);

        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(620, 520));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = Ventanaprincipal.crearLabel(
                "➕ Alta de Pelicula",
                Ventanaprincipal.FUENTE_SUBTITULO,
                Ventanaprincipal.COLOR_ACENTO2
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tarjeta.add(titulo, gbc);
        gbc.gridwidth = 1;

        JTextField fNombre = campo(tarjeta, gbc, "Nombre", 0, 1);
        JTextField fDirector = campo(tarjeta, gbc, "Director", 0, 2);
        JTextField fProductor = campo(tarjeta, gbc, "Productor", 0, 3);
        JTextField fDuracion = campo(tarjeta, gbc, "Duracion (min)", 0, 4);
        JTextField fNumeroSalas = campo(tarjeta, gbc, "Cantidad de horarios (Max 10)", 0, 5);


        ((AbstractDocument) fNumeroSalas.getDocument()).setDocumentFilter(new SalaFilter());

        gbc.gridx = 0;
        gbc.gridy = 6;
        tarjeta.add(Ventanaprincipal.crearLabel("Genero",
                Ventanaprincipal.FUENTE_PEQUEÑA,
                Ventanaprincipal.COLOR_SUBTEXTO), gbc);

        JComboBox<String> cGenero = Ventanaprincipal.crearCombo(GENEROS);
        gbc.gridx = 1;
        tarjeta.add(cGenero, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        tarjeta.add(Ventanaprincipal.crearLabel("Clasificacion",
                Ventanaprincipal.FUENTE_PEQUEÑA,
                Ventanaprincipal.COLOR_SUBTEXTO), gbc);

        JComboBox<String> cClasif = Ventanaprincipal.crearCombo(CLASIFICACIONES);
        gbc.gridx = 1;
        tarjeta.add(cClasif, gbc);

        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        tarjeta.add(lblMsg, gbc);

        JButton btn = Ventanaprincipal.crearBoton(
                "Guardar Pelicula",
                Ventanaprincipal.COLOR_ACENTO,
                Color.WHITE
        );

        gbc.gridy = 9;
        tarjeta.add(btn, gbc);

        // Boton de guardar
        btn.addActionListener(e -> {

            String nom = fNombre.getText().trim();
            String dir = fDirector.getText().trim();
            String prod = fProductor.getText().trim();
            String durStr = fDuracion.getText().trim();
            String cantStr = fNumeroSalas.getText().trim();

            // validacion que campos no esten vacios
            if (nom.isEmpty() || dir.isEmpty() || prod.isEmpty()
                    || durStr.isEmpty() || cantStr.isEmpty()) {

                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Completa todos los campos.");
                return;
            }

            int dur, cantidad;

            try {
                dur = Integer.parseInt(durStr);
                cantidad = Integer.parseInt(cantStr);
            } catch (NumberFormatException ex) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Duración y cantidad deben ser números.");
                return;
            }
                //mas validaciones
            if (cantidad < 1 || cantidad > 10) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Máximo 10 horarios.");
                return;
            }

            int clasif = cClasif.getSelectedIndex() + 1;
            int gen = cGenero.getSelectedIndex() + 1;

            // damos de alta pelicula
            cine.altaPeliculavisual(nom, dir, prod, clasif, dur, gen);
            actualizarListaPeliculas();
            Pelicula peli = cine.buscarNombre(nom);//buscamos para actualizar
            if (peli != null) {
                peli.maxHorarios = cantidad;
            }

            lblMsg.setForeground(new Color(80, 220, 120));
            lblMsg.setText("✔ Película agregada.");

            fNombre.setText("");
            fDirector.setText("");
            fProductor.setText("");
            fDuracion.setText("");
            fNumeroSalas.setText("");
            //estas variables las usamos en dar de alta horario para mmostrar cuanto nos queda de salas por llenar
            nombrePeliculaActual = nom;
            horariosRestantes = cantidad;

            cardLayout.show(panelContenido, "horario");
            actualizarPanelHorarios();//actualizamos horario
        });

        p.add(tarjeta);
        return p;
    }

    private JTextField campo(JPanel panel, GridBagConstraints gbc, String etiqueta, int x, int y)
    { gbc.gridx = x; gbc.gridy = y; panel.add(Ventanaprincipal.crearLabel(etiqueta, Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO), gbc); JTextField tf = Ventanaprincipal.crearCampo(18); gbc.gridx = x + 1; panel.add(tf, gbc); return tf; }


    // alta de horario
    private JPanel crearPanelAltaHorario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);

        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(560, 520));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = Ventanaprincipal.crearLabel("🕐 Alta de Horario",
                Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tarjeta.add(titulo, gbc);
        gbc.gridwidth = 1;

        JLabel lblContador = new JLabel(" ");
        lblContador.setFont(Ventanaprincipal.FUENTE_NORMAL);
        lblContador.setForeground(Ventanaprincipal.COLOR_ACENTO2);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        tarjeta.add(lblContador, gbc);
        gbc.gridwidth = 1;

        JTextField fNom = campo(tarjeta, gbc, "Nombre de pelicula", 0, 2);
        JTextField fSala = campo(tarjeta, gbc, "Sala (1-10)", 0, 3);

        ((AbstractDocument) fSala.getDocument()).setDocumentFilter(new SalaFilter());

        JFormattedTextField fHora = null;
        JFormattedTextField fFecha = null;

        try {
            MaskFormatter formatoHora = new MaskFormatter("##:##");
            formatoHora.setPlaceholderCharacter('_');
            fHora = new JFormattedTextField(formatoHora);

            MaskFormatter formatoFecha = new MaskFormatter("##/##/####");
            formatoFecha.setPlaceholderCharacter('_');
            fFecha = new JFormattedTextField(formatoFecha);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        gbc.gridx = 0;
        gbc.gridy = 4;
        tarjeta.add(Ventanaprincipal.crearLabel("Hora (HH:mm)",
                Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO), gbc);

        gbc.gridx = 1;
        tarjeta.add(fHora, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        tarjeta.add(Ventanaprincipal.crearLabel("Fecha (dd/MM/yyyy)",
                Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO), gbc);

        gbc.gridx = 1;
        tarjeta.add(fFecha, gbc);

        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        tarjeta.add(lblMsg, gbc);

        JButton btn = Ventanaprincipal.crearBoton("Agregar Horario",
                new Color(40, 140, 200), Color.WHITE);

        gbc.gridy = 7;
        tarjeta.add(btn, gbc);

        JFormattedTextField finalFHora = fHora;
        JFormattedTextField finalFFecha = fFecha;

        fNom.setText(nombrePeliculaActual);
        if (nombrePeliculaActual != null && !nombrePeliculaActual.isEmpty()) { // ojitooo
            fNom.setText(nombrePeliculaActual);
            fNom.setEditable(false);
        } else {
            fNom.setEditable(true);
        }
        actualizarContador(lblContador);

        btn.addActionListener(e -> {
            String nom = fNom.getText().trim();
            String salaStr = fSala.getText().trim();
            String hora = finalFHora.getText().trim();
            String fecha = finalFFecha.getText().trim();
            //validaciones no campos vacios
            if (nom.isEmpty() || salaStr.isEmpty()) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Completa todos los campos.");
                return;
            }
            if (!esHoraValida(hora)) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Hora inválida (Use formato 24hrs: 00:00-23:59).");
                return;
            }

            if (!esFechaValida(fecha)) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Fecha inválida (Verifique día y mes).");
                return;
            }

            int sala = Integer.parseInt(salaStr);
            if (cine.altaHorario(nom, sala, fecha, hora)) { //llamamos para validar con alta horarios

                horariosRestantes--; //reducimos contador
                actualizarContador(lblContador);


                if (horariosRestantes <= 0) { //si se agrega individual sale esto porque tecnicamente el valor es vacio
                    lblMsg.setForeground(new Color(80, 220, 120));
                    lblMsg.setText("✔ ¡Todas las salas fueron agregadas con exito!");
                    btn.setEnabled(false);

                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                            SwingUtilities.invokeLater(() -> {
                                nombrePeliculaActual = "";
                                horariosRestantes = 0;
                                fNom.setText("");
                                fNom.setEditable(true);
                                fSala.setText("");
                                finalFHora.setText("");
                                finalFFecha.setText("");
                                lblMsg.setText(" ");
                                btn.setEnabled(true);
                                actualizarContador(lblContador);
                            });
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();

                } else {
                    lblMsg.setForeground(new Color(80, 220, 120));
                    lblMsg.setText("✔ Horario agregado correctamente.");

                    // Limpiar solo sala, hora y fecha
                    fSala.setText("");
                    finalFHora.setText("");
                    finalFFecha.setText("");
                }

            } else {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("✖ Solapamiento o pelicula no encontrada.");
            }
        });

        p.add(tarjeta);
        return p;
    }


    // baja de pelicula
    private JPanel crearPanelBajaPelicula() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(460, 280));

        JLabel titulo = Ventanaprincipal.crearLabel("🗑️ Baja de Pelicula", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lbl = Ventanaprincipal.crearLabel("Nombre de pelicula a eliminar:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        JTextField fNom = Ventanaprincipal.crearCampo(20);
        fNom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        fNom.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
        lblMsg.setAlignmentX(LEFT_ALIGNMENT);
        JButton btn = Ventanaprincipal.crearBoton("Eliminar Pelicula", new Color(180, 40, 40), Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(e -> {
            String nom = fNom.getText().trim();
            if (nom.isEmpty()) { //validacion para que no se escriba vacio
                lblMsg.setText("Escribe el nombre de la pelicula.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar '" + nom + "' y todos sus horarios?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                cine.bajaPelicula(nom);//llamamos el metodo para dar de baja pelicula
                lblMsg.setForeground(new Color(80, 220, 120));
                lblMsg.setText("✔ Pelicula eliminada.");
                fNom.setText("");
                actualizarListaPeliculas();//actualizamos
            }
        });
        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(lbl);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(fNom);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblMsg);
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(btn);
        p.add(tarjeta);
        return p;
    }

    // baja de horario
    private JPanel crearPanelBajaHorario() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(520, 400));

        JLabel titulo = Ventanaprincipal.crearLabel("🗑️ Baja de Horario", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        JLabel lbl = Ventanaprincipal.crearLabel("Nombre de pelicula:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        JTextField fNom = Ventanaprincipal.crearCampo(20);
        fNom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        fNom.setAlignmentX(LEFT_ALIGNMENT);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);
        lista.setBackground(Ventanaprincipal.COLOR_CAMPO);
        lista.setForeground(Ventanaprincipal.COLOR_TEXTO);
        lista.setFont(Ventanaprincipal.FUENTE_NORMAL);
        lista.setSelectionBackground(Ventanaprincipal.COLOR_ACENTO);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setPreferredSize(new Dimension(400, 120));
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE));
        scroll.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
        lblMsg.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnBuscar = Ventanaprincipal.crearBoton("Buscar Horarios", new Color(40, 100, 180), Color.WHITE);
        btnBuscar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnBuscar.setAlignmentX(LEFT_ALIGNMENT);

        btnBuscar.addActionListener(e -> {
            model.clear();
            String nom = fNom.getText().trim();
            //buscamos la pelicula
            Pelicula pel = cine.buscarNombre(nom);
            if (pel == null) {//vemos si existe
                lblMsg.setText("Pelicula no encontrada.");
                return;
            }//recorremos
            for (int i = 0; i < pel.totalHorarios; i++) {
                model.addElement((i + 1) + ". " + pel.horarios[i].fecha + "  " + pel.horarios[i].hora + "  Sala " + pel.horarios[i].sala);
            }
            if (model.isEmpty()) lblMsg.setText("Sin horarios registrados.");
            else lblMsg.setText(" ");
        });

        JButton btnEliminar = Ventanaprincipal.crearBoton("Eliminar Horario Seleccionado", new Color(180, 40, 40), Color.WHITE);
        btnEliminar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnEliminar.setAlignmentX(LEFT_ALIGNMENT);

        btnEliminar.addActionListener(e -> {
            int idx = lista.getSelectedIndex();

            if (idx < 0) {
                lblMsg.setText("Selecciona un horario.");
                return;
            }
            String nom = fNom.getText().trim();
            Pelicula pel = cine.buscarNombre(nom);
            if (pel == null) return;
            if (pel.totalHorarios <= 1) {
                lblMsg.setText("La pelicula debe tener al menos un horario.");
                return;
            }
            pel.eliminarHorario(idx);//borramos
            cine.guardarDatos();//actualizamos el txt
            model.remove(idx);//actualizamos visual
            lblMsg.setForeground(new Color(80, 220, 120));
            lblMsg.setText("✔ Horario eliminado.");
        });

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(lbl);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(fNom);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(btnBuscar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(scroll);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblMsg);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(btnEliminar);
        p.add(tarjeta);
        return p;
    }

    private JPanel crearPanelModificar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new GridBagLayout());
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(600, 540));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 8, 5, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = Ventanaprincipal.crearLabel("✏️ Modificar Pelicula", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        tarjeta.add(titulo, gbc);
        gbc.gridwidth = 1;

        JTextField fBuscar = campo(tarjeta, gbc, "Nombre a buscar", 0, 1);
        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);

        JTextField fNom = campo(tarjeta, gbc, "Nuevo nombre", 0, 3);
        JTextField fDir = campo(tarjeta, gbc, "Nuevo director", 0, 4);
        JTextField fProd = campo(tarjeta, gbc, "Nuevo productor", 0, 5);
        JTextField fDur = campo(tarjeta, gbc, "Nueva duracion (min)", 0, 6);

        gbc.gridx = 0;
        gbc.gridy = 7;
        tarjeta.add(Ventanaprincipal.crearLabel("Clasificacion", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO), gbc);
        JComboBox<String> cClasif = Ventanaprincipal.crearCombo(CLASIFICACIONES);
        gbc.gridx = 1;
        tarjeta.add(cClasif, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        tarjeta.add(Ventanaprincipal.crearLabel("Genero", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO), gbc);
        JComboBox<String> cGenero = Ventanaprincipal.crearCombo(GENEROS);
        gbc.gridx = 1;
        tarjeta.add(cGenero, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        tarjeta.add(lblMsg, gbc);

        JButton btnBuscar2 = Ventanaprincipal.crearBoton("Cargar datos", new Color(40, 100, 180), Color.WHITE);
        gbc.gridy = 9;
        tarjeta.add(btnBuscar2, gbc);

        btnBuscar2.addActionListener(e -> {
            //buscamos
            Pelicula pel = cine.buscarNombre(fBuscar.getText().trim());
            //si no lo encontramos
            if (pel == null) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Pelicula no encontrada.");
                return;
            }
            //lo encontramos y jalamos los datos a la vista
            fNom.setText(pel.nombre);
            fDir.setText(pel.director);
            fProd.setText(pel.productor);
            fDur.setText(String.valueOf(pel.duracion));
            cClasif.setSelectedIndex(pel.clasificacion - 1);
            cGenero.setSelectedIndex(pel.genero - 1);
            lblMsg.setForeground(new Color(80, 220, 120));
            lblMsg.setText("✔ Pelicula cargada. Edita y guarda.");
        });

        JButton btnGuardar = Ventanaprincipal.crearBoton("Guardar Cambios", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        gbc.gridy = 10;
        tarjeta.add(btnGuardar, gbc);
        JLabel lblInstruccion = new JLabel("Verifica los horarios y administra los horarios a tu nueva disposición");
        lblInstruccion.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblInstruccion.setForeground(Ventanaprincipal.COLOR_SUBTEXTO); // Color grisáceo elegante
        lblInstruccion.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 11;
        gbc.insets = new Insets(15, 8, 5, 8);
        tarjeta.add(lblInstruccion, gbc);

        btnGuardar.addActionListener(e -> {
            Pelicula pel = cine.buscarNombre(fBuscar.getText().trim());
            if (pel == null) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("Primero carga una pelicula.");
                return;
            }
            try {
                int nuevaDuracion = Integer.parseInt(fDur.getText().trim());

                if (nuevaDuracion <= 0) {
                    lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                    lblMsg.setText("La duración debe ser mayor a 0 minutos.");
                    return;
                }

                if (nuevaDuracion > pel.duracion) {

                    Horario[] horariosRespaldados = pel.horarios;
                    int totalRespaldado = pel.totalHorarios;

                    pel.totalHorarios = 0;

                    boolean hayChoque = false;
                    for (int i = 0; i < totalRespaldado; i++) {
                        if (cine.solapamiento(horariosRespaldados[i].sala, nuevaDuracion, horariosRespaldados[i].fecha, horariosRespaldados[i].hora)) {
                            hayChoque = true;
                            break;
                        }
                    }

                    pel.totalHorarios = totalRespaldado;

                    if (hayChoque) {
                        lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                        lblMsg.setText("Error: La nueva duración choca con otra película en cartelera.");
                        return; 
                    }
                }

                pel.nombre = fNom.getText().trim();
                pel.director = fDir.getText().trim();
                pel.productor = fProd.getText().trim();
                pel.duracion = nuevaDuracion;
                pel.clasificacion = cClasif.getSelectedIndex() + 1;
                pel.genero = cGenero.getSelectedIndex() + 1;

                cine.guardarDatos();
                lblMsg.setForeground(new Color(80, 220, 120));
                lblMsg.setText("✔ Cambios guardados correctamente.");

            } catch (NumberFormatException ex) {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("La duracion debe ser un numero.");
            }
        });

        p.add(tarjeta);
        return p;
    }

    private JPanel crearPanelConsultar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);

        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(650, 550));

        JLabel titulo = Ventanaprincipal.crearLabel(
                "🔍 Consulta de Películas",
                Ventanaprincipal.FUENTE_SUBTITULO,
                Ventanaprincipal.COLOR_ACENTO2
        );
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JList<String> lista = new JList<>(modeloPeliculas);
        Ventanaprincipal.estilizarLista(lista);
        lista.setBackground(Ventanaprincipal.COLOR_CAMPO);

        JScrollPane scrollLista = new JScrollPane(lista);
        Ventanaprincipal.estilizarScroll(scrollLista);
        scrollLista.setAlignmentX(Component.LEFT_ALIGNMENT);
        scrollLista.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel lblNombre = new JLabel("Nombre de película:");
        lblNombre.setForeground(Ventanaprincipal.COLOR_SUBTEXTO);
        lblNombre.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField fNom = Ventanaprincipal.crearCampo(20);
        fNom.setAlignmentX(Component.LEFT_ALIGNMENT);
        fNom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JButton btn = Ventanaprincipal.crearBoton(
                "Buscar Película",
                new Color(40, 100, 180),
                Color.WHITE
        );
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));

        JTextArea area = new JTextArea(8, 30);
        area.setEditable(false);
        Ventanaprincipal.estilizarArea(area);
        area.setBackground(Ventanaprincipal.COLOR_CAMPO);

        JScrollPane scrollInfo = new JScrollPane(area);
        Ventanaprincipal.estilizarScroll(scrollInfo);
        scrollInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        btn.addActionListener(e -> {

            String nombre = fNom.getText().trim();
            Pelicula pel = cine.buscarNombre(nombre);//checamos
            if (pel == null) { //si es vacio
                area.setText("❌ Película no encontrada.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("🎬 ").append(pel.nombre.toUpperCase()).append("\n\n");
            sb.append("Director: ").append(pel.director).append("\n");
            sb.append("Duración: ").append(pel.duracion).append(" min\n\n");
            sb.append("Horarios:\n");
            for (int i = 0; i < pel.totalHorarios; i++) { //recorremos
                sb.append(" • ").append(pel.horarios[i].fecha).append(" - ").append(pel.horarios[i].hora).append("\n");//construimos de nuevo
            }
            area.setText(sb.toString());
        });

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(scrollLista);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(lblNombre);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(fNom);
        tarjeta.add(Box.createVerticalStrut(15));
        tarjeta.add(btn);
        tarjeta.add(Box.createVerticalStrut(20));
        tarjeta.add(scrollInfo);

        p.add(tarjeta);
        actualizarListaPeliculas();
        return p;
    }

    private JPanel crearPanelCombo() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30, 40, 30, 40));
        tarjeta.setPreferredSize(new Dimension(480, 420));

        JLabel titulo = Ventanaprincipal.crearLabel("🍿 Dulceria", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblCola = Ventanaprincipal.crearLabel("Cola de pedidos pendientes:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        lblCola.setAlignmentX(LEFT_ALIGNMENT);

        DefaultListModel<String> colaModel = new DefaultListModel<>();
        JList<String> listaCola = new JList<>(colaModel);
        listaCola.setBackground(Ventanaprincipal.COLOR_CAMPO);
        listaCola.setForeground(Ventanaprincipal.COLOR_TEXTO);
        listaCola.setFont(Ventanaprincipal.FUENTE_NORMAL);

        JScrollPane scroll = new JScrollPane(listaCola);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE));
        scroll.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblMsg = new JLabel(" ");
        lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblMsg.setForeground(new Color(80, 220, 120));
        lblMsg.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnActualizar = Ventanaprincipal.crearBoton("🔄 Actualizar Cola", new Color(50, 50, 80), Color.WHITE);
        btnActualizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnActualizar.setAlignmentX(LEFT_ALIGNMENT);
        btnActualizar.addActionListener(e -> {
            actualizarVistaCola(colaModel);
            if (colaModel.isEmpty()) {
                lblMsg.setText("No hay pedidos pendientes.");
            } else {
                lblMsg.setText("Se cargaron " + colaModel.getSize() + " pedidos.");
            }
        });

        JButton btnDespachar = Ventanaprincipal.crearBoton("Despachar Siguiente", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btnDespachar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btnDespachar.setAlignmentX(LEFT_ALIGNMENT);
        btnDespachar.addActionListener(e -> {
            //Cola fifo
            int resultado = cine.despacharCombo();
            if (resultado != -1) {
                actualizarVistaCola(colaModel);
                lblMsg.setForeground(new Color(80, 220, 120));
                lblMsg.setText("✔ Pedido despachado con exito.");
            } else {
                lblMsg.setForeground(Ventanaprincipal.COLOR_ACENTO);
                lblMsg.setText("No hay nada que despachar.");
                colaModel.clear();
            }
        });

        actualizarVistaCola(colaModel);

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(lblCola);
        tarjeta.add(Box.createVerticalStrut(6));
        tarjeta.add(scroll);
        tarjeta.add(Box.createVerticalStrut(12));
        tarjeta.add(btnActualizar);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(btnDespachar);
        tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(lblMsg);

        p.add(tarjeta);
        return p;
    }

    private void actualizarVistaCola(DefaultListModel<String> modelo) {
        modelo.clear();
        //obtiene la cola
        String[] pedidos = cine.colaDulceria.getListaParaAdmin();
        if (pedidos != null) {
            for (String p : pedidos) {
                modelo.addElement(p);
            }
        }
    }

    private void actualizarContador(JLabel lblContador) {
        //checamos el estado
        if (nombrePeliculaActual != null && !nombrePeliculaActual.isEmpty()) {
            lblContador.setText("📌 Película: " + nombrePeliculaActual +
                    "  |  Horarios restantes: " + horariosRestantes);

        } else if (!nombrePeliculaActual.isEmpty()) {
            lblContador.setText("✔ ¡Todos los horarios completados!");
            lblContador.setForeground(new Color(80, 220, 120));
        } else {
            lblContador.setText(" ");
        }
    }

    private void actualizarPanelHorarios() {
        // Forzar actualización del panel
        panelContenido.revalidate();
        panelContenido.repaint();
    }

    class SalaFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {

            if (string == null) return;

            String nuevoTexto = new StringBuilder(fb.getDocument().getText(0, fb.getDocument().getLength()))
                    .insert(offset, string).toString();

            if (esValido(nuevoTexto)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            //no procesa entradas invalidas
            if (text == null) return;
            //obtiene lo que hay en el campo
            String actual = fb.getDocument().getText(0, fb.getDocument().getLength());
            String nuevoTexto = new StringBuilder(actual).replace(offset, offset + length, text).toString();
                //checa si cumplen el es valido, esta abajo
            if (esValido(nuevoTexto)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean esValido(String texto) { //filtro para que solo se puedan poner numeros
            if (!texto.matches("\\d*")) return false; // solo números
            if (texto.isEmpty()) return true;

            int valor = Integer.parseInt(texto);
            return valor >= 1 && valor <= 10; // rango permitido
        }
    }

    private void actualizarListaPeliculas() {
        modeloPeliculas.clear();
        //llamamos a lista
        NodoPelicula nodo = cine.peliculas.cabeza;
        //checamos que no este vacia
        if (nodo == null) {
            modeloPeliculas.addElement("No hay peliculas registradas");
            return;
        }
        //recorre
        while (nodo != null) {
            modeloPeliculas.addElement("🎬 " + nodo.pelicula.nombre);
            nodo = nodo.siguiente;
        }
    }

    private boolean esHoraValida(String hora) {
        try {
            String[] partes = hora.split(":");
            int h = Integer.parseInt(partes[0]);
            int m = Integer.parseInt(partes[1]);
            return (h >= 0 && h < 24) && (m >= 0 && m < 60);
        } catch (Exception e) {
            return false;
        }
    }
    private boolean esFechaValida(String fecha) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(fecha);
            return true;
        } catch (java.text.ParseException e) {
            return false;
        }
    }

}