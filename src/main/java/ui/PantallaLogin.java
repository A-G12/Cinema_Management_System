package ui;
import com.mycompany.proyecto.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class PantallaLogin extends JFrame {
    private static final String ADMIN_USUARIO = "AlbertoCortes";
    private static final String ADMIN_CONTRA  = "Kike123";
    private int intentos = 0;

    private final GestionCines gestion;
    private final Cine cine;
    private JTextField campoUsuario;
    private JPasswordField campoContra;
    private JLabel lblError;

    public PantallaLogin(GestionCines gestion, Cine cine) {
        this.gestion = gestion;
        this.cine = cine;
        setTitle("Cine — Acceso Administrador");
        setSize(600, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
    }

    private void initUI() {
        JPanel fondo = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(0, 0, Ventanaprincipal.COLOR_FONDO, getWidth(), getHeight(), new Color(30, 10, 40));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        setContentPane(fondo);

        JPanel tarjeta = Ventanaprincipal.crearPanelTarjeta();
        tarjeta.setLayout(new BoxLayout(tarjeta, BoxLayout.Y_AXIS));
        tarjeta.setBorder(new EmptyBorder(20, 50, 30, 50));
        tarjeta.setPreferredSize(new Dimension(500, 500));

        JLabel icono = new JLabel("🔐");
        icono.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));

        Dimension d = new Dimension(60, 60);

        icono.setHorizontalAlignment(SwingConstants.CENTER);
        icono.setVerticalAlignment(SwingConstants.CENTER);

        icono.setAlignmentX(Component.CENTER_ALIGNMENT);

        icono.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel titulo = Ventanaprincipal.crearLabel("Acceso Administrativo", Ventanaprincipal.FUENTE_SUBTITULO, Ventanaprincipal.COLOR_ACENTO2);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cineLbl = Ventanaprincipal.crearLabel(
                "Cine: " + cine.getIdEstado() + " — " + cine.getIdMunicipio(),
                Ventanaprincipal.FUENTE_PEQUEÑA,
                Ventanaprincipal.COLOR_SUBTEXTO
        );
        cineLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblU = Ventanaprincipal.crearLabel("Usuario", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        lblU.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoUsuario = Ventanaprincipal.crearCampo(20);
        campoUsuario.setMaximumSize(new Dimension(260, 38));
        campoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblP = Ventanaprincipal.crearLabel("Contraseña", Ventanaprincipal.FUENTE_PEQUEÑA, Ventanaprincipal.COLOR_SUBTEXTO);
        lblP.setAlignmentX(Component.CENTER_ALIGNMENT);

        campoContra = new JPasswordField(20);
        campoContra.setBackground(Ventanaprincipal.COLOR_CAMPO);
        campoContra.setForeground(Ventanaprincipal.COLOR_TEXTO);
        campoContra.setCaretColor(Ventanaprincipal.COLOR_ACENTO);
        campoContra.setFont(Ventanaprincipal.FUENTE_NORMAL);
        campoContra.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Ventanaprincipal.COLOR_BORDE, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        campoContra.setMaximumSize(new Dimension(260, 38));
        campoContra.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblError = new JLabel(" ");
        lblError.setFont(Ventanaprincipal.FUENTE_PEQUEÑA);
        lblError.setForeground(Ventanaprincipal.COLOR_ACENTO);
        lblError.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLogin = Ventanaprincipal.crearBoton("Iniciar sesion", Ventanaprincipal.COLOR_ACENTO2, Ventanaprincipal.COLOR_FONDO);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(260, 42));

        btnLogin.addActionListener(e -> intentarLogin());
        campoContra.addActionListener(e -> intentarLogin());

        JButton btnVolver = Ventanaprincipal.crearBoton("Volver", Ventanaprincipal.COLOR_ACENTO2, Ventanaprincipal.COLOR_FONDO);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.setMaximumSize(new Dimension(260, 36));

        btnVolver.addActionListener(e -> {
            new PantallaSeleccioncine(gestion).setVisible(true);
            dispose();
        });

        tarjeta.add(icono);
        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(titulo);
        tarjeta.add(Box.createVerticalStrut(4));
        tarjeta.add(cineLbl);
        tarjeta.add(Box.createVerticalStrut(20));

        tarjeta.add(lblU);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(campoUsuario);

        tarjeta.add(Box.createVerticalStrut(12));

        tarjeta.add(lblP);
        tarjeta.add(Box.createVerticalStrut(5));
        tarjeta.add(campoContra);

        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(lblError);

        tarjeta.add(Box.createVerticalStrut(16));
        tarjeta.add(btnLogin);

        tarjeta.add(Box.createVerticalStrut(8));
        tarjeta.add(btnVolver);

        fondo.add(tarjeta);
    }
//sistema que se saca si fallas tres veces
    private void intentarLogin() {
        String u = campoUsuario.getText().trim();
        String p = new String(campoContra.getPassword());

        if (u.equals(ADMIN_USUARIO) && p.equals(ADMIN_CONTRA)) {
            new PantallaAdmin(gestion, cine).setVisible(true);
            dispose();
        } else {
            intentos++;
            int restantes = 3 - intentos;

            if (restantes <= 0) {
                JOptionPane.showMessageDialog(this, "Limite de intentos alcanzado.", "Bloqueado", JOptionPane.ERROR_MESSAGE);
                new PantallaSeleccioncine(gestion).setVisible(true);
                dispose();
            } else {
                lblError.setText("Credenciales incorrectas. Intentos restantes: " + restantes);
                campoContra.setText("");
            }
        }
    }
}