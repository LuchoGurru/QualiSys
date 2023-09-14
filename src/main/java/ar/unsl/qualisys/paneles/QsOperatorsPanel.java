package ar.unsl.qualisys.paneles;

import ar.unsl.qualisys.componentes.nodos.QsOperator;
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
public class QsOperatorsPanel extends JPanel {
    
    private JPanel esta;
    private JPanel operador1;
    private JPanel operador2;
    private JPanel operador3;
    private JPanel operador4;
    private JPanel operador5;
    private JPanel operador6;
    private JPanel operador7;
    private JPanel operador8;
    private JPanel operador9;
    private JPanel operador10;
    private JPanel operador11;
    private JPanel operador12;
    private JPanel operador13;
    private JPanel operador14;
    private JPanel operador15;
    private JPanel operador16;
    private JPanel operador17;
    private JPanel operador18;
    private JPanel operador19;
    private JPanel operador20;
    private Point position;
    public QsOperatorsPanel() {
        
        
        operador1= new QsOperator(this,101,"Disjunction","D",1f,1700d,1700d,1700d,1700d);
        operador2= new QsOperator(this,102,"Strong QD (+)","D++",0.9375f,1700d,1700d,1700d,1700d);
        operador3= new QsOperator(this,103,"Strong QD","D+",0.8750f,1700d,1700d,1700d,1700d);
        operador4= new QsOperator(this,104,"Strong QD (-)","D+-",0.8125f,1700d,1700d,1700d,1700d);
        operador5= new QsOperator(this,105,"Medium QD","DA",0.7500f,1700d,1700d,1700d,1700d);
        operador6= new QsOperator(this,106,"Weak QD (+)","D-+",0.6875f,1700d,1700d,1700d,1700d);
        operador7= new QsOperator(this,107,"Weak QD","D-",0.6250f,1700d,1700d,1700d,1700d);        
        operador8= new QsOperator(this,108,"Square Mean","SQU",0.6232f,1700d,1700d,1700d,1700d);        
        operador9= new QsOperator(this,109,"Weak QD (-)","D--",0.5625f,1700d,1700d,1700d,1700d);        
        operador10= new QsOperator(this,110,"Arithmetic Mean","A",0.5f,1700d,1700d,1700d,1700d);        
        operador11= new QsOperator(this,111,"Weak QC (-)","C--",0.4375f,1700d,1700d,1700d,1700d);        
        operador12= new QsOperator(this,112,"Weak QC","C-",0.3750f,1700d,1700d,1700d,1700d);        
        operador13= new QsOperator(this,113,"Geometric Mean","GEO",0.3333f,1700d,1700d,1700d,1700d);      
        operador14= new QsOperator(this,114,"Weak QC (+)","C-+",0.3125f,1700d,1700d,1700d,1700d);        
        operador15= new QsOperator(this,115,"Medium QC","CA",0.2500f,1700d,1700d,1700d,1700d);        
        operador16= new QsOperator(this,116,"Harmonic Mean","HAR",0.2274f,1700d,1700d,1700d,1700d);        
        operador17= new QsOperator(this,117,"Strong QC (-)","C+-",0.1875f,1700d,1700d,1700d,1700d);        
        operador18= new QsOperator(this,118,"Strong QC","C+",0.1250f,1700d,1700d,1700d,1700d);        
        operador19= new QsOperator(this,119,"Stronc QC (+)","C++",0.0625f,1700d,1700d,1700d,1700d);        
        operador20= new QsOperator(this,120,"Conjunction","C",0.0000f,1700d,1700d,1700d,1700d);
        
        // operador12= new QsOperator(this,106,"Weak QD (+)","D-+",0.6875f,1700d,1700d,1700d,1700d));
        
        operador13.setBackground(Color.red);
        operador14.setBackground(Color.GRAY);
        operador15.setBackground(Color.GREEN);
        operador16.setBackground(Color.YELLOW);
        operador17.setBackground(Color.WHITE);
        operador18.setBackground(Color.PINK);

        this.add(operador1);
        this.add(operador2);
        this.add(operador3);
        this.add(operador4);
        this.add(operador5);
        this.add(operador6);
        
        esta = this;
    }
    
    public void setPositionToDraw(Point p,int tipoOperador){
        QsGraphicPanel qp = (QsGraphicPanel) this.getParent();
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
