/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUIComponents.Laminas;

import GUIComponents.Laminas.QualyOperatorsPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import GUIComponents.GUINodos.QualyOperator;

/**
 *
 * @author luciano
 */
public class QualyGraphicPanel extends javax.swing.JPanel {
    private QualyOperatorsPanel menuOperadores;
    private DragAndDropVariablesAndOperandsPanel DAD = new DragAndDropVariablesAndOperandsPanel();
    public boolean FLAG;
   // private static int cantoOperators;
    
    /**
     * Creates new form examples
     */
    public QualyGraphicPanel() {
        this.setLayout(new BorderLayout());
        this.setName("GUIPanel");
        
        //AGREGO LOS 2 PANELES
        menuOperadores = new QualyOperatorsPanel();
                menuOperadores.setPreferredSize(new Dimension(100,300));

        DAD.setBackground(Color.WHITE); 
        this.add(menuOperadores,BorderLayout.WEST);
        this.add(DAD ,BorderLayout.CENTER);
        
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
    //    DragListener dragListener = new DragListener();
    //    this.addMouseMotionListener(dragListener);
    }
    
    public void agregarOperadorANulLayout(Point punto,int tipoOperador){ 
        
        
        QualyOperator operador = new QualyOperator(DAD,DAD.cantOperadores);
        
        DragAndDropVariablesAndOperandsPanel.cantOperadores ++;
        
        int margin = menuOperadores.getWidth(); 
        
        operador.setBounds((int)punto.getX() - margin ,(int)punto.getY(),100,100);
        
        switch (tipoOperador) {
            case 0:
                operador.setBackground(Color.RED);
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
        
        DAD.add(operador);
        DAD.repaint();
    
        /*
                DAD.getComponentAt(punto);

        Component alguncomponente = DAD.getComponentAt(punto);
        System.out.println("alguncomponente = " + alguncomponente.toString());

        if(alguncomponente != null && alguncomponente != this.DAD && alguncomponente != this.menuOperadores){
            System.out.println("distinto de nulLLLLLLLLLLLLLLLLLLLLLLLLLLl");
        }
        
        
        if(operador.getBounds().intersects(DAD.getComponentAt(punto).getBounds())){
             System.out.println("NOOOOOOOO");
        }
        */
    }
    
    
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
        //    System.out.println(evt.getPoint());
        //    agregarOperadorANulLayout(evt.getPoint());
            //if(evt.getButton() == 3 ){
              //  Component c = DAD.getComponentAt(evt.getPoint());
            //    System.out.println("c. = " + c.toString());
          //  }
        }
        public void mouseReleased(MouseEvent e){
      //      DAD.listarOperadores();
        //    System.out.println(e.getPoint());
           //  agregarOperadorANulLayout(e.getPoint());
        // Component panel = (Component)e.getSource();
        //     System.out.println(panel.getBounds());
        }
    }
    
     /*  private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){
            
            
              //      System.out.println(evt.getPoint());
                      
       Point currentPoint = evt.getPoint();
            image_corner.translate(
                (int)(currentPoint.getX() - previousPoint.getX()),
                (int)(currentPoint.getY() - previousPoint.getY())
            );
            previousPoint = currentPoint;
            repaint(); 
        }
    }*/
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
