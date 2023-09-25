/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.frames.QsFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsVistaPreviaModal  extends JDialog {
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane panelTexto;
    private QsBarraHerramientas GUIpadre;
    private JDialog este;
    
    public QsVistaPreviaModal(QsFrame frame, QsBarraHerramientas GUIpadre,String titulo, boolean modal) {
        super(frame,titulo, modal);
        this.setLayout(new BorderLayout());
        this.GUIpadre = GUIpadre;
        this.este = this;
        initComponents();
        //Seteo El texto
    }
    
    private void initComponents() {
        this.setBounds(200,200,750,550);
        JLabel jLabel1 = new JLabel();
        JButton jButton1 = new JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1.setText("            ESTAS SON TUS VARIABLES.");
        jLabel1.setBackground(Color.yellow);
        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GUIpadre.mostrarPanelGrafico();
                este.dispose();
            }
        });
        jScrollPane1 = new javax.swing.JScrollPane();
        panelTexto = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelTexto.setEditable(false);
        jScrollPane1.setViewportView(panelTexto);
        this.add(jLabel1,BorderLayout.NORTH);
        this.add(jScrollPane1,BorderLayout.CENTER);
        this.add(jButton1,BorderLayout.SOUTH);
    }
    
    
    public JTextPane getTextoPanel() {
        return panelTexto;
    }

    public void setTextoPane1(String texto) {
        this.panelTexto.setText(texto);
    }
}
