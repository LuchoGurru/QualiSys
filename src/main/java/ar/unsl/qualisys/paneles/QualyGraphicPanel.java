/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.unsl.qualisys.paneles;

import ar.unsl.qualisys.componentes.QsBarraHerramientas;
import ar.unsl.qualisys.paneles.QualyOperatorsPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import ar.unsl.qualisys.componentes.nodos.QualyOperator;
import ar.unsl.qualisys.frames.QsFrame;
import java.awt.GraphicsEnvironment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author luciano
 */
public class QualyGraphicPanel extends javax.swing.JPanel {
    private QualyOperatorsPanel menuOperadores;
    private QsDadPanel DAD = new QsDadPanel();
    public boolean FLAG;
    private QsFrame parent;
    
    /**
     * Creates new form examples
     */
    public QualyGraphicPanel(QsFrame parent) {
        this.parent=parent;
        this.setLayout(new BorderLayout());
        this.setName("GUIPanel");
        this.add(new QsBarraHerramientas(this.parent), BorderLayout.NORTH);

        //AGREGO LOS 2 PANELES
        menuOperadores = new QualyOperatorsPanel();
        menuOperadores.setPreferredSize(new Dimension(100,300));

        DAD.setBackground(Color.WHITE); 
        this.add(menuOperadores,BorderLayout.WEST);
        this.add(DAD ,BorderLayout.CENTER); 
    }
      /**
     * Construye la barra de herramientas superior para editar texto
     */
    public void barraDeHerramientas() {
        JMenuBar barra = new JMenuBar();
        JToolBar menuHerramientas = new JToolBar();
        JButton nuevo = new JButton();
        JButton abrir = new JButton();
        JButton guardar = new JButton();
        JButton deshacer = new JButton();
        JButton actualizar = new JButton(); // Haacer boton actualizar 
        JButton rehacer = new JButton();
        JButton color = new JButton();
        JSpinner tam = new JSpinner(new SpinnerNumberModel(12, 0, 84, 2));
        JButton centrado = new JButton();
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fuente = new JComboBox(fontNames);
        //Config
        menuHerramientas.setFloatable(false);
        abrir.setText("Open");
        nuevo.setText("New");
        guardar.setText("Save");
        deshacer.setText("<--");
        actualizar.setText("F5");
        rehacer.setText("-->");
        color.setText("Color");
        centrado.setText("Centrado");
        fuente.setSelectedIndex(15);

        //onFocus Texto
        nuevo.setToolTipText("Nuevo Archivo");
        abrir.setToolTipText("Abrir Archivo");
        actualizar.setToolTipText("Actualizar Texto");
        //
        menuHerramientas.add(guardar);
        menuHerramientas.add(abrir);
        menuHerramientas.add(deshacer);
        menuHerramientas.add(actualizar);
        menuHerramientas.add(rehacer);
        menuHerramientas.add(color);
        menuHerramientas.add(centrado);
        menuHerramientas.add(fuente);
        menuHerramientas.add(tam);
 
        this.add(menuHerramientas, BorderLayout.NORTH);
        //this.add(panelTexto, BorderLayout.CENTER);

    }
    public void agregarOperadorANulLayout(Point punto,int tipoOperador){ 
        
        
        QualyOperator operador = new QualyOperator(DAD,DAD.cantOperadores);
        
        QsDadPanel.cantOperadores ++;
        
        int margin = menuOperadores.getWidth(); 
        
        operador.setBounds((int)punto.getX() - margin ,(int)punto.getY(),100,100);
        
        switch (tipoOperador) {
            case 0:
                operador.setBackground(Color.WHITE);
                break;
            case 1:
                operador.setBackground(Color.GRAY);
                break;
            case 2:
                operador.setBackground(Color.GREEN);
                break;
            case 3:
                operador.setBackground(Color.YELLOW);
                break;
        }
        
        DAD.addOperator(operador);
        DAD.repaint();
    }

    public QsDadPanel getDAD() {
        return DAD;
    }

    public void setDAD(QsDadPanel DAD) {
        this.DAD = DAD;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
