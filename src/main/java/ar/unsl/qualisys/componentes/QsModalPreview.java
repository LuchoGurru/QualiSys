/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.frames.QsFrame;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsModalPreview  extends JDialog {
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
    private QsBarraHerramientas parent;
    private JDialog este;
    public QsModalPreview(QsFrame frame, QsBarraHerramientas parent,String titulo, boolean modal) {
        super(frame,titulo, modal);
        this.setLayout(new BorderLayout());
        this.parent = parent;
        this.este = this;
        initComponents();
    }
    
    private void initComponents() {
        this.setBounds(200,200,750,550);
        JLabel jLabel1 = new JLabel();
        JButton jButton1 = new JButton();
        jButton1 = new javax.swing.JButton();
        jLabel1.setText("Vista Previa: Estas son las variables a graficar.");

        jButton1.setText("Aceptar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parent.mostrarPanelGrafico(1);
                este.dispose();
            }
        });
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);
       // this.add(jLabel1,BorderLayout.NORTH);
        this.add(jScrollPane1,BorderLayout.CENTER);
        this.add(jButton1,BorderLayout.SOUTH);
        
    }
    
    public JTextPane getjTextPane1() {
        return jTextPane1;
    }

    public void setjTextoPane1(String jTextPane1) {
        this.jTextPane1.setText(jTextPane1);
    }
}
