package com.mycompany.proyecto;

import ui.PantallaSeleccioncine;
import javax.swing.UIManager;

public class Proyecto {

    public static void main(String[] args) {
        //intenta aplicar estilo nimbus
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }

        GestionCines gestion = new GestionCines();// Crea el objeto que gestiona los cines
        // Ejecuta ui mediante eventos de Swing
        java.awt.EventQueue.invokeLater(() -> {
            //crea la pantalla de seleccion cine
            PantallaSeleccioncine inicio = new PantallaSeleccioncine(gestion);
            inicio.setVisible(true);
        });
    }
}