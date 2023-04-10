package operators;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class QualyOperator extends JComponent{
    private String nombre;
    private String[] dominio; // Recibe un maximo de 5 argumentos ... seran strings, seran float ?
    private float rango;
    
    Point previousPoint;
    
    
    public QualyOperator(JComponent parent, double x, double y){
        this.setAlignmentX((float)x);
        this.setAlignmentY((float)y);
        setPreferredSize(new Dimension(101,101));
        this.setBackground(Color.red);
        this.setVisible(true);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(new ClickListener());
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
        
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 100, 100); // 50 hasta 150 // Diametro
        g.drawOval(25, 25, 50, 50);   // 
    }
    
    public QualyOperator(String nombre, String[] dom){
        
        try{
            this.nombre = nombre;
            this.dominio = dom;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Can not operate, to many arguments");
        }
    }

    public float evaluationFunction(){
        rango = 1.0f; // dom[i] 0<i<5 evaluation by function
        return rango;
    }
    
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            previousPoint = evt.getPoint();
        }
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
            previousPoint =  evt.getPoint(); 
            //repaint();
        }
    }
}
