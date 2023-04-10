/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.qualisys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import operators.QualyOperator;

/**
 *
 * @author luciano
 */
public class QualyGraphicPanel extends javax.swing.JPanel {
    private JPanel menuOperadores;
    private JPanel panelNullLayout;
    /**
     * Creates new form examples
     */
    public QualyGraphicPanel() {
        this.setLayout(new BorderLayout());
        menuOperadores = new QualyOperatorsPanel();
        
        menuOperadores.setPreferredSize(new Dimension(100,300));

        
        
        panelNullLayout = new JPanel(null);
        panelNullLayout.setBackground(Color.WHITE);
        this.add(menuOperadores,BorderLayout.WEST);
        this.add(panelNullLayout ,BorderLayout.CENTER);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    }
    
    public void agregarOperadorANulLayout(Point punto){
        QualyOperator operador = new QualyOperator(panelNullLayout,punto.getX(),punto.getY());
        operador.setBounds((int)punto.getX(),(int)punto.getY(),100,100);
        panelNullLayout.add(operador);
        panelNullLayout.repaint();
    }
    
    
    
    
    
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            System.out.println(evt.getPoint());
            agregarOperadorANulLayout(evt.getPoint());
        }
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){
                    System.out.println(evt.getPoint());
                       agregarOperadorANulLayout(evt.getPoint());
         
/*    Point currentPoint = evt.getPoint();
            image_corner.translate(
                (int)(currentPoint.getX() - previousPoint.getX()),
                (int)(currentPoint.getY() - previousPoint.getY())
            );
            previousPoint = currentPoint;
            repaint();*/
        }
    }
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // </editor-fold>
@SuppressWarnings("unchecked")
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
