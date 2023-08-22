package ar.unsl.qualisys.paneles;

import ar.unsl.qualisys.componentes.nodos.QualyOperator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
/**
 *
 * @author luciano
 */
public class QualyOperatorsPanel extends JPanel {
    
    private JPanel esta;
    private JPanel operador1;
    private JPanel operador2;
    private JPanel operador3;
    private JPanel operador4;
    private JPanel operador5;
    private JPanel operador6;
    private Point position;
    public QualyOperatorsPanel() {
        operador1= new QualyOperator(this,101);
        operador2= new QualyOperator(this,102);
        operador3= new QualyOperator(this,103);
        operador4= new QualyOperator(this,104);
        operador5= new QualyOperator(this,105);
        operador6= new QualyOperator(this,106);
        
        operador1.setBackground(Color.red);
        operador2.setBackground(Color.GRAY);
        operador3.setBackground(Color.GREEN);
        operador4.setBackground(Color.YELLOW);
        operador5.setBackground(Color.WHITE);
        operador6.setBackground(Color.PINK);

        this.add(operador1);
        this.add(operador2);
        this.add(operador3);
        this.add(operador4);
        this.add(operador5);
        this.add(operador6);
        
        esta = this;
    }
    
    public void setPositionToDraw(Point p,int tipoOperador){
        QualyGraphicPanel qp = (QualyGraphicPanel) this.getParent();
        this.position = p;
        qp.agregarOperadorANulLayout(p,tipoOperador);
        
        // llamar al padre que dibuje en el puntooo  
    }
    
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
/*
        ImageIcon img = new ImageIcon("./src/main/resources/qualy_icon.png");
        Point image_corner;

    final int IMG_WIDTH = img.getIconWidth();
    final int IMG_HEIGHT = img.getIconHeight();
    
    
        image_corner = new Point(0,0);
        image_corner2 = new Point(400,400);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    
    public void paint(Graphics g){
        super.paint(g);
        System.out.println("asdsads");
        
        g.setColor(Color.yellow);
        g.drawLine(0, 70, 100, 70);
        Graphics2D graphic_2D = (Graphics2D) g;
        int[] xPoints = {150,250,350};
        int[] yPoints = {300,150,350};
        graphic_2D.fillPolygon(xPoints,yPoints,3);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g); 
     //   g.setColor(Color.yellow);
    //    g.drawLine(0, 70, 100, 70);
//        img.paintIcon(this, g, (int) image_corner.getX(), (int) image_corner.getY());
    }
    
     
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            previousPoint = evt.getPoint();
        }
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){
            Point currentPoint = evt.getPoint();
            image_corner.translate(
                (int)(currentPoint.getX() - previousPoint.getX()),
                (int)(currentPoint.getY() - previousPoint.getY())
            );
            previousPoint = currentPoint;
            repaint();
        }
    }
    
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */