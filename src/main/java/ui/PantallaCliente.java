package ui;
import com.mycompany.proyecto.*;
import ui.PantallaSeleccioncine;
import ui.Ventanaprincipal;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
public class PantallaCliente extends JFrame {
    private final GestionCines gestion;
    private final Cine cine;
    private DefaultListModel<String> modeloPeliculas = new DefaultListModel<>();
    private JPanel panelContenido;
    private CardLayout cardLayout;

    private static final String[] CLASIFICACIONES = {"AA","A","B","B15","C","D"};
    private static final String[] GENEROS = {"Accion","Aventuras","Ciencia Ficcion","Comedia","Drama","Thriller","Suspenso","Terror","Romance","Animacion"};

    public PantallaCliente(GestionCines gestion, Cine cine) {
        this.gestion = gestion;
        this.cine = cine;
        setTitle("Cine — Cliente | " + cine.getIdEstado() + "-" + cine.getIdMunicipio());
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

        panelContenido.add(crearPanelBienvenida(),     "inicio");
        panelContenido.add(crearPanelBuscarNombre(),   "buscarNombre");
        panelContenido.add(crearPanelBuscarClasif(),   "buscarClasif");
        panelContenido.add(crearPanelBuscarGenero(),   "buscarGenero");
        panelContenido.add(crearPanelCartelera(),      "cartelera");
        panelContenido.add(crearPanelConsultar(),      "consultar");
        panelContenido.add(crearPanelDulceria(),       "dulceria");
        panelContenido.add(crearPanelHistorial(),      "historial");
        panelContenido.add(crearPanelCineCercano(),    "cercano");

        root.add(panelContenido, BorderLayout.CENTER);
    }

    private JPanel crearMenuLateral() {
        JPanel menu = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(35, 35, 55),
                        0, getHeight(), Ventanaprincipal.COLOR_PANEL
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(Ventanaprincipal.COLOR_BORDE);
                g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());

                g2.dispose();
            }
        };
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setPreferredSize(new Dimension(220, 0));
        menu.setBorder(new EmptyBorder(24, 16, 24, 16));
        menu.setOpaque(false);

        JLabel logo = new JLabel("🎬 Cine");
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 22));
        logo.setForeground(Ventanaprincipal.COLOR_ACENTO);
        logo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel rol = new JLabel("  MODO CLIENTE");
        rol.setFont(new Font("Segoe UI", Font.BOLD, 10));
        rol.setForeground(new Color(80, 200, 120));
        rol.setAlignmentX(LEFT_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setForeground(Ventanaprincipal.COLOR_BORDE);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        menu.add(logo);
        menu.add(Box.createVerticalStrut(4));
        menu.add(rol);
        menu.add(Box.createVerticalStrut(24));
        menu.add(sep);
        menu.add(Box.createVerticalStrut(20));

        // Items
        agregarItem(menu, "🏠  Inicio",              "inicio");
        agregarItem(menu, "🔎  Buscar por nombre",    "buscarNombre");
        agregarItem(menu, "🏷️  Buscar por clasificacion","buscarClasif");
        agregarItem(menu, "🎭  Buscar por genero",    "buscarGenero");
        agregarItem(menu, "📋  Ver Cartelera",        "cartelera");
        agregarItem(menu, "🎬  Consultar Pelicula",   "consultar");
        agregarItem(menu, "🍿  Dulceria",             "dulceria");
        agregarItem(menu, "📜  Historial",            "historial");
        agregarItem(menu, "📍  Cine mas cercano",     "cercano");

        menu.add(Box.createVerticalGlue());

        JButton btnSalir = Ventanaprincipal.crearBoton("⬅  Salir", new Color(45, 25, 25), new Color(255, 100, 100));
        btnSalir.setAlignmentX(LEFT_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        btnSalir.addActionListener(e -> { new PantallaSeleccioncine(gestion).setVisible(true); dispose(); });

        menu.add(btnSalir);
        return menu;
    }

    private void agregarItem(JPanel menu, String texto, String card) {
        JButton btn = new JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) { g2.setColor(new Color(50,50,80)); g2.fillRoundRect(0,0,getWidth(),getHeight(),8,8); }
                g2.setColor(getForeground()); g2.setFont(getFont());
                g2.drawString(getText(), 10, getHeight()/2 + g2.getFontMetrics().getAscent()/2 - 2);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); btn.setAlignmentX(LEFT_ALIGNMENT);
        btn.addActionListener(e -> {
            JPanel nuevoPanel = null;

            switch (card) {
                case "inicio":          nuevoPanel = crearPanelBienvenida(); break;
                case "buscarNombre":    nuevoPanel = crearPanelBuscarNombre(); break;
                case "buscarClasif":    nuevoPanel = crearPanelBuscarClasif(); break;
                case "buscarGenero":    nuevoPanel = crearPanelBuscarGenero(); break;
                case "cartelera":       nuevoPanel = crearPanelCartelera(); break;
                case "consultar":       nuevoPanel = crearPanelConsultar(); break;
                case "dulceria":        nuevoPanel = crearPanelDulceria(); break;
                case "historial":       nuevoPanel = crearPanelHistorial(); break;
                case "cercano":         nuevoPanel = crearPanelCineCercano(); break;
            }

            if (nuevoPanel != null) {
                panelContenido.add(nuevoPanel, card);
                cardLayout.show(panelContenido, card);

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

        JPanel t = Ventanaprincipal.crearPanelTarjeta();
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.setBorder(new EmptyBorder(40,60,40,60));

        JLabel ic = new JLabel("🎬", SwingConstants.CENTER);
        ic.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        ic.setAlignmentX(CENTER_ALIGNMENT);

        Dimension d = new Dimension(100, 100);
        ic.setPreferredSize(d);
        ic.setMinimumSize(d);
        ic.setMaximumSize(d);
        ic.setVerticalAlignment(SwingConstants.CENTER);
        JLabel tit = Ventanaprincipal.crearLabel("¡Bienvenido!", Ventanaprincipal.FUENTE_TITULO, Ventanaprincipal.COLOR_TEXTO);
        tit.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub = Ventanaprincipal.crearLabel("Cine: " + cine.getIdEstado() + " — Sucursal " + cine.getIdMunicipio(), Ventanaprincipal.FUENTE_NORMAL, Ventanaprincipal.COLOR_SUBTEXTO);
        sub.setAlignmentX(CENTER_ALIGNMENT);

        JLabel sub2 = Ventanaprincipal.crearLabel("Usa el menu lateral para explorar la cartelera", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        sub2.setAlignmentX(CENTER_ALIGNMENT);

        t.add(ic);
        t.add(Box.createVerticalStrut(16));
        t.add(tit);
        t.add(Box.createVerticalStrut(8));
        t.add(sub);
        t.add(Box.createVerticalStrut(4));
        t.add(sub2);

        p.add(t);
        return p;
    }
    private JPanel crearPanelBuscarNombre() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30,40,30,40)); tarjeta.setPreferredSize(new Dimension(540, 500));
        JLabel titulo = new JLabel("🔎 Buscar Pelicula por Nombre");
        titulo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 18));
        titulo.setForeground(Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(LEFT_ALIGNMENT);
        JTextField fNom = Ventanaprincipal.crearCampo(20); fNom.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); fNom.setAlignmentX(LEFT_ALIGNMENT);
        JTextArea area = resultArea(); area.setAlignmentX(LEFT_ALIGNMENT);
        JScrollPane scroll = scrollArea(area);

        JButton btn = Ventanaprincipal.crearBoton("Buscar", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(e -> {
            String nom = fNom.getText().trim();
            Pelicula pel = cine.buscarNombre(nom);//busca la peli en el cine
            if (pel == null) { area.setText("No se encontro la pelicula '" + nom + "'."); return; }
            cine.agregarHistorial(pel.nombre);//agregamos la consulta
            area.setText(formatPelicula(pel));//muestra informacion
            area.setCaretPosition(0);
        });

        tarjeta.add(titulo); tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(Ventanaprincipal.crearLabel("Nombre:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        tarjeta.add(Box.createVerticalStrut(6)); tarjeta.add(fNom); tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btn); tarjeta.add(Box.createVerticalStrut(14)); tarjeta.add(scroll);
        p.add(tarjeta); return p;
    }

    private JPanel crearPanelBuscarClasif() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30,40,30,40)); tarjeta.setPreferredSize(new Dimension(540, 500));

        JLabel titulo = Ventanaprincipal.crearLabel("🏷️ Buscar por Clasificacion", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2); titulo.setAlignmentX(LEFT_ALIGNMENT);
        JComboBox<String> cClasif = Ventanaprincipal.crearCombo(CLASIFICACIONES);
        cClasif.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); cClasif.setAlignmentX(LEFT_ALIGNMENT);
        JTextArea area = resultArea();
        JScrollPane scroll = scrollArea(area);

        JButton btn = Ventanaprincipal.crearBoton("Buscar", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(e -> {
            //obtiene la clasificacion
            int clasif = cClasif.getSelectedIndex() + 1;
            StringBuilder sb = new StringBuilder();
            //recorre la lista
            NodoPelicula nodo = cine.peliculas.cabeza;
            boolean hay = false;
            while (nodo != null) {//checamos si el nodo es de esa clasificacion
                if (nodo.pelicula.clasificacion == clasif) {
                    //agrega la pelicula al resultado
                    sb.append(formatPeliculaResumen(nodo.pelicula)).append("\n─────────────────────\n");//division entre una y otra
                    hay = true;
                }
                nodo = nodo.siguiente;//va al siguiente
            }
            //en caso que no hay
            area.setText(hay ? sb.toString() : "No hay peliculas con clasificacion " + CLASIFICACIONES[clasif-1]);
            area.setCaretPosition(0);
        });

        tarjeta.add(titulo); tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(Ventanaprincipal.crearLabel("Clasificacion:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        tarjeta.add(Box.createVerticalStrut(6)); tarjeta.add(cClasif); tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btn); tarjeta.add(Box.createVerticalStrut(14)); tarjeta.add(scroll);
        p.add(tarjeta); return p;
    }

    private JPanel crearPanelBuscarGenero() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30,40,30,40)); tarjeta.setPreferredSize(new Dimension(540, 500));

        JLabel titulo = Ventanaprincipal.crearLabel("🎭 Buscar por Genero", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2); titulo.setAlignmentX(LEFT_ALIGNMENT);
        JComboBox<String> cGenero = Ventanaprincipal.crearCombo(GENEROS);
        cGenero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); cGenero.setAlignmentX(LEFT_ALIGNMENT);
        JTextArea area = resultArea();
        JScrollPane scroll = scrollArea(area);

        JButton btn = Ventanaprincipal.crearBoton("Buscar", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); btn.setAlignmentX(LEFT_ALIGNMENT);

        btn.addActionListener(e -> {
            int gen = cGenero.getSelectedIndex() + 1;//obtenemos el genero
            StringBuilder sb = new StringBuilder();
            NodoPelicula nodo = cine.peliculas.cabeza;//checa lista  y la recorre
            boolean hay = false;
            while (nodo != null) {
                if (nodo.pelicula.genero == gen) {
                    sb.append(formatPeliculaResumen(nodo.pelicula)).append("\n─────────────────────\n");
                    hay = true;
                }
                nodo = nodo.siguiente;
            }
            area.setText(hay ? sb.toString() : "No hay peliculas de genero " + GENEROS[gen-1]);
            area.setCaretPosition(0);
        });

        tarjeta.add(titulo); tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(Ventanaprincipal.crearLabel("Genero:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        tarjeta.add(Box.createVerticalStrut(6)); tarjeta.add(cGenero); tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btn); tarjeta.add(Box.createVerticalStrut(14)); tarjeta.add(scroll);
        p.add(tarjeta); return p;
    }

    private JPanel crearPanelCartelera() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30,40,30,40)); tarjeta.setPreferredSize(new Dimension(620, 520));

        JLabel titulo = Ventanaprincipal.crearLabel("📋 Cartelera", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2); titulo.setAlignmentX(LEFT_ALIGNMENT);

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)); controles.setOpaque(false); controles.setAlignmentX(LEFT_ALIGNMENT);
        JComboBox<String> cOrd = Ventanaprincipal.crearCombo(new String[]{"Ascendente","Descendente"});
        JComboBox<String> cTipo = Ventanaprincipal.crearCombo(new String[]{"Por Fecha","Por Nombre"});
        JButton btnVer = Ventanaprincipal.crearBoton("Actualizar", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btnVer.setPreferredSize(new Dimension(120, 34));
        controles.add(Ventanaprincipal.crearLabel("Orden:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        controles.add(cOrd);
        controles.add(Ventanaprincipal.crearLabel("Criterio:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        controles.add(cTipo); controles.add(btnVer);

        String[] cols = {"Pelicula","Sala","Hora","Fecha"};
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = new JTable(tableModel);
        estilizarTabla(tabla);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 360));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE));
        scroll.setAlignmentX(LEFT_ALIGNMENT);

        btnVer.addActionListener(e -> {
            tableModel.setRowCount(0);
            //determina el orden
            boolean asc = cOrd.getSelectedIndex() == 0;
            boolean porFecha = cTipo.getSelectedIndex() == 0;
            NodoPelicula nodo = cine.peliculas.cabeza; //recorre
            int cnt = 0; CarteleraGeneral[] arr = new CarteleraGeneral[200]; //arreglo auxiliar para ordenar
            while (nodo != null) {
                Pelicula pel = nodo.pelicula;
                //recorre horarios
                for (int i = 0; i < pel.totalHorarios && cnt < 200; i++)
                    //guarda en el arreglo
                    arr[cnt++] = new CarteleraGeneral(pel.nombre, pel.horarios[i].sala, pel.horarios[i].hora, pel.horarios[i].fecha);
                nodo = nodo.siguiente;
            }
            //ordena con quicksort
            CarteleraGeneral.quicksort(arr, 0, cnt-1, asc, porFecha);
            for (int i = 0; i < cnt; i++)
                //hace la insercion
                tableModel.addRow(new Object[]{arr[i].nombrePelicula, arr[i].sala, arr[i].hora, arr[i].fecha});
        });

        tarjeta.add(titulo); tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(controles); tarjeta.add(Box.createVerticalStrut(12)); tarjeta.add(scroll);
        p.add(tarjeta); return p;
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

        JLabel lblNombre = new JLabel("Nombre de pelicula:");
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


            //pila desde back hacia fron
            btn.addActionListener(e -> {

                String nombre = fNom.getText().trim();
                Pelicula pel = cine.buscarNombre(nombre);

                if (pel == null) {
                    area.setText("❌ Película no encontrada.");
                    return;
                }

                String ultimo = null;

                if (!cine.historial.estaVacia()) {
                    ultimo = cine.historial.pop(); // sacamos el ultimo
                }

                if (ultimo == null || !ultimo.equals(pel.nombre)) {
                    if (ultimo != null) {
                        cine.historial.push(ultimo); // regresamos el anterior
                    }
                    cine.historial.push(pel.nombre); // agregamos el nuevo
                } else {
                    cine.historial.push(ultimo);
                }

                StringBuilder sb = new StringBuilder();

                sb.append("🎬 ").append(pel.nombre.toUpperCase()).append("\n\n");
                sb.append("Director: ").append(pel.director).append("\n");
                sb.append("Duración: ").append(pel.duracion).append(" min\n\n");

                sb.append("Horarios:\n");
                for (int i = 0; i < pel.totalHorarios; i++) {
                    sb.append(" • ")
                            .append(pel.horarios[i].fecha)
                            .append(" - ")
                            .append(pel.horarios[i].hora)
                            .append("\n");
                }

                area.setText(sb.toString());
                area.setCaretPosition(0);
        });

        // UI
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

    private JPanel crearPanelDulceria() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30,40,30,40)); tarjeta.setPreferredSize(new Dimension(480, 400));

        JLabel titulo = Ventanaprincipal.crearLabel("🍿 Dulceria", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2); titulo.setAlignmentX(LEFT_ALIGNMENT);

        JComboBox<String> cCombo = Ventanaprincipal.crearCombo(new String[]{"Combo Familiar","Combo Duo","Combo Personal"});
        cCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); cCombo.setAlignmentX(LEFT_ALIGNMENT);

        DefaultListModel<String> colaModel = new DefaultListModel<>();
        JList<String> listaCola = new JList<>(colaModel);
        listaCola.setBackground(Ventanaprincipal.COLOR_CAMPO); listaCola.setForeground(Ventanaprincipal.COLOR_TEXTO); listaCola.setFont(Ventanaprincipal.FUENTE_NORMAL);
        JScrollPane scroll = new JScrollPane(listaCola);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE)); scroll.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblMsg = new JLabel(" "); lblMsg.setFont(Ventanaprincipal.FUENTE_PEQUEÑA); lblMsg.setForeground(new Color(80,220,120)); lblMsg.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnPedir = Ventanaprincipal.crearBoton("Pedir Combo", new Color(40,160,80), Color.WHITE);
        btnPedir.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38)); btnPedir.setAlignmentX(LEFT_ALIGNMENT);
        btnPedir.addActionListener(e -> {
            int idx = cCombo.getSelectedIndex() + 1;
            cine.pedirCombo(idx);
            String[] nombres = {"Familiar","Duo","Personal"};
            colaModel.addElement("Combo " + nombres[idx-1]);
            lblMsg.setForeground(new Color(80,220,120)); lblMsg.setText("✔ Combo en cola.");
        });



        tarjeta.add(titulo); tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(Ventanaprincipal.crearLabel("Selecciona el combo:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        tarjeta.add(Box.createVerticalStrut(6)); tarjeta.add(cCombo); tarjeta.add(Box.createVerticalStrut(10));
        tarjeta.add(btnPedir); tarjeta.add(Box.createVerticalStrut(14));
        tarjeta.add(Ventanaprincipal.crearLabel("Cola de pedidos:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO));
        tarjeta.add(Box.createVerticalStrut(6)); tarjeta.add(scroll);
        tarjeta.add(Box.createVerticalStrut(8)); tarjeta.add(lblMsg); tarjeta.add(Box.createVerticalStrut(8));
        p.add(tarjeta); return p;
    }

    private JPanel crearPanelHistorial() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(30,40,30,40)); tarjeta.setPreferredSize(new Dimension(480, 400));

        JLabel titulo = Ventanaprincipal.crearLabel("📜 Historial de Consultas", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2); titulo.setAlignmentX(LEFT_ALIGNMENT);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);
        lista.setBackground(Ventanaprincipal.COLOR_CAMPO); lista.setForeground(Ventanaprincipal.COLOR_TEXTO); lista.setFont(Ventanaprincipal.FUENTE_NORMAL);
        JScrollPane scroll = new JScrollPane(lista);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE)); scroll.setAlignmentX(LEFT_ALIGNMENT);

        JButton btnVer = Ventanaprincipal.crearBoton("Ver Historial", Ventanaprincipal.COLOR_ACENTO, Color.WHITE);
        btnVer.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); btnVer.setAlignmentX(LEFT_ALIGNMENT);
        btnVer.addActionListener(e -> { //OJO
            model.clear();
            PilaHistorial pila = cine.historial;
            if (pila.estaVacia()) {
                model.addElement("Sin consultas previas.");
                return;
            }

            String[] temp = new String[50];
            int count = 0;

            while (!pila.estaVacia()) {
                temp[count++] = pila.pop();
            }


            for (int i = count - 1; i >= 0; i--) {
                pila.push(temp[i]);
            }


            for (int i = 0; i < count; i++) {
                model.addElement((i + 1) + ". " + temp[i]);
            }
        });

        tarjeta.add(titulo); tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(btnVer); tarjeta.add(Box.createVerticalStrut(14)); tarjeta.add(scroll);
        p.add(tarjeta); return p;
    }

    private JPanel crearPanelCineCercano() {
        JPanel p = new JPanel(new GridBagLayout()); p.setBackground(Ventanaprincipal.COLOR_FONDO);
        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(40, 50, 40, 50));
        tarjeta.setPreferredSize(new Dimension(560, 360));

        JLabel titulo = Ventanaprincipal.crearLabel("📍 Cine Mas Cercano", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        JTextArea lblActual = new JTextArea("Cine actual: " + cine.getIdEstado() + " — Sucursal " + cine.getIdMunicipio());
        lblActual.setFont(Ventanaprincipal.FUENTE_NORMAL);
        lblActual.setForeground(Ventanaprincipal.COLOR_SUBTEXTO);
        lblActual.setBackground(new Color(0,0,0,0));
        lblActual.setOpaque(false);
        lblActual.setEditable(false);
        lblActual.setFocusable(false);
        lblActual.setLineWrap(true);
        lblActual.setWrapStyleWord(true);
        lblActual.setAlignmentX(LEFT_ALIGNMENT);
        lblActual.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton btn = Ventanaprincipal.crearBoton("Buscar Cine Mas Cercano", new Color(40,120,200), Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btn.setAlignmentX(LEFT_ALIGNMENT);

        JPanel panelRes = new JPanel();
        panelRes.setLayout(new BoxLayout(panelRes, BoxLayout.Y_AXIS));
        panelRes.setOpaque(false);
        panelRes.setAlignmentX(LEFT_ALIGNMENT);
        panelRes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        panelRes.setVisible(false);

        JLabel lblResLabel = Ventanaprincipal.crearLabel("Sucursal mas cercana:", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        lblResLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblRes = new JLabel();
        lblRes.setFont(new Font("Segoe UI Emoji", Font.BOLD, 20));
        lblRes.setForeground(new Color(80, 220, 120));
        lblRes.setAlignmentX(LEFT_ALIGNMENT);

        JTextArea lblDesc = new JTextArea("Es la sucursal mas cercana segun la red de cines de tu estado.");
        lblDesc.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblDesc.setForeground(Ventanaprincipal.COLOR_SUBTEXTO);
        lblDesc.setOpaque(false);
        lblDesc.setEditable(false);
        lblDesc.setFocusable(false);
        lblDesc.setLineWrap(true);
        lblDesc.setWrapStyleWord(true);
        lblDesc.setAlignmentX(LEFT_ALIGNMENT);
        lblDesc.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        panelRes.add(lblResLabel);
        panelRes.add(Box.createVerticalStrut(6));
        panelRes.add(lblRes);
        panelRes.add(Box.createVerticalStrut(8));
        panelRes.add(lblDesc);

        btn.addActionListener(e -> {
            Cine cercano = gestion.cineMasCercano(cine);
            if (cercano == null) {
                lblRes.setText("No se encontro un cine cercano.");
                panelRes.setVisible(true);
                return;
            }
            lblRes.setText("📍  " + cercano.getIdEstado() + " — Sucursal " + cercano.getIdMunicipio());
            panelRes.setVisible(true);
            tarjeta.revalidate();
            tarjeta.repaint();
        });

        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(lblActual);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(btn);
        tarjeta.add(Box.createVerticalStrut(30));
        tarjeta.add(panelRes);
        p.add(tarjeta);
        return p;
    }

    private JTextArea resultArea() {
        JTextArea area = new JTextArea(12, 30);
        area.setEditable(false);
        area.setBackground(Ventanaprincipal.COLOR_CAMPO);
        area.setForeground(Ventanaprincipal.COLOR_TEXTO);
        area.setFont(Ventanaprincipal.FUENTE_NORMAL);
        area.setBorder(new EmptyBorder(10,10,10,10));
        return area;
    }

    private JScrollPane scrollArea(JTextArea area) {
        JScrollPane scroll = new JScrollPane(area);
        scroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
        scroll.getViewport().setBackground(Ventanaprincipal.COLOR_CAMPO);
        scroll.setBorder(BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE));
        scroll.setAlignmentX(LEFT_ALIGNMENT);
        return scroll;
    }

    private void estilizarTabla(JTable tabla) {
        tabla.setBackground(Ventanaprincipal.COLOR_CAMPO); tabla.setForeground(Ventanaprincipal.COLOR_TEXTO);
        tabla.setFont(Ventanaprincipal.FUENTE_NORMAL); tabla.setGridColor(Ventanaprincipal.COLOR_BORDE);
        tabla.getTableHeader().setBackground(Ventanaprincipal.COLOR_PANEL);
        tabla.getTableHeader().setForeground(Ventanaprincipal.COLOR_ACENTO2);
        tabla.getTableHeader().setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        tabla.setRowHeight(28); tabla.setSelectionBackground(Ventanaprincipal.COLOR_ACENTO);
    }

    private String formatPelicula(Pelicula pel) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre:        ").append(pel.nombre).append("\n");
        sb.append("Director:      ").append(pel.director).append("\n");
        sb.append("Productor:     ").append(pel.productor).append("\n");
        sb.append("Clasificacion: ").append(CLASIFICACIONES[pel.clasificacion-1]).append("\n");
        sb.append("Duracion:      ").append(pel.duracion).append(" min\n");
        sb.append("Genero:        ").append(GENEROS[pel.genero-1]).append("\n\nHorarios:\n");
        for (int i = 0; i < pel.totalHorarios; i++)
            sb.append("  ").append(i+1).append(". ").append(pel.horarios[i].fecha).append("  ").append(pel.horarios[i].hora).append("  Sala ").append(pel.horarios[i].sala).append("\n");
        return sb.toString();
    }

    private String formatPeliculaResumen(Pelicula pel) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(pel.nombre).append("\n");
        for (int i = 0; i < pel.totalHorarios; i++)
            sb.append("  ").append(pel.horarios[i].fecha).append("  ").append(pel.horarios[i].hora).append("  Sala ").append(pel.horarios[i].sala).append("\n");
        return sb.toString();
    }

    private void actualizarListaPeliculas() {
        modeloPeliculas.clear();

        NodoPelicula nodo = cine.peliculas.cabeza;

        if (nodo == null) {
            modeloPeliculas.addElement("No hay peliculas registradas");
            return;
        }

        while (nodo != null) {
            modeloPeliculas.addElement("🎬 " + nodo.pelicula.nombre);
            nodo = nodo.siguiente;
        }
    }
}