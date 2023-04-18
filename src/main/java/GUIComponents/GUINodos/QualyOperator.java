package GUIComponents.GUINodos;

import GUIComponents.Laminas.DragAndDropVariablesAndOperandsPanel;
import GUIComponents.Laminas.QualyOperatorsPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class QualyOperator extends JPanel{
    
    
    private String nombre;
    private String[] dominio; // Recibe un maximo de 5 argum
    private float rango;
    private DragAndDropVariablesAndOperandsPanel padre;
    Point previousPoint;
    private QualyOperator yoOperador;
    private String componentID;
    private ArrayList<Line2D> lines = new ArrayList<>();

    
    public QualyOperator(JPanel parent, int id){
        this.setName("operador" + id); 
        if(parent.getClass() == DragAndDropVariablesAndOperandsPanel.class){ 
            this.padre = (DragAndDropVariablesAndOperandsPanel) parent;            
        }
        this.setPreferredSize(new Dimension(100,100));
        //this.setVisible(true); 
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(new ClickListener());
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
        yoOperador = this; // PARA MANEJAR LA INSTANCIA DENTRO DE LOS LISTENERS
        
        
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    //    g.setColor(Color.red);
        g.drawOval(0, 0, 100, 100); // 50 hasta 150 // Diametro
        g.drawOval(25, 25, 50, 50);   // 
                Graphics2D g2 = (Graphics2D)g;
    for (Line2D line : lines) {
            g2.draw(line);
        }
    }
    
    public QualyOperator(String nombre, String[] dom){
        
        try{
            this.nombre = nombre;
            this.dominio = dom;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Can not operate, to many arguments");
        }
    }

    public String getComponentID() {
        return componentID;
    }

    public float evaluationFunction(){
        rango = 1.0f; // dom[i] 0<i<5 evaluation by function
        return rango;
    }
    
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            System.out.println("Soy un operador");
            
            yoOperador.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        }
        public void mouseReleased(MouseEvent e){
            if(yoOperador.getPadre() != null ){
                       Rectangle oldPosition = yoOperador.getBounds();
                yoOperador.setBounds(yoOperador.getBounds().getLocation().x + e.getX(),
                        yoOperador.getBounds().getLocation().y + e.getY(), 100, 100);
                /*QualyOperator aux=null;
            try {
                aux = (QualyOperator) yoOperador.clone();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(QualyOperator.class.getName()).log(Level.SEVERE, null, ex);
            }*/
                //aux.setBounds(yoOperador.getBounds().getLocation().x + e.getX(),
                //    yoOperador.getBounds().getLocation().y + e.getY(),100,100);
                boolean puedoMoverme = padre.listarOperadores(yoOperador);

                if (!puedoMoverme) {
                    yoOperador.setBounds(oldPosition);
                    dibujarLineaBetweenOperators(e);

                } else {
                    dibujarLineaBetweenOperators(e);
                }
                //  if(this.get)
                System.out.println("operador releaseEEEEEEE");
            }else{
                exportarOperadorADibujar(e);
            }
     
  //            agregarOperadorANulLayout(e.getPoint());
  //       Component panel = (Component)e.getSource();
  //           System.out.println(panel.getBounds());
        }
        
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
            
            //previousPoint =  evt.getPoint(); 
          //  System.out.println(evt.getPoint());
            //repaint();
        }
    }

    public Point getPreviousPoint() {
        return previousPoint;
    }

    public DragAndDropVariablesAndOperandsPanel getPadre() {
        return padre;
    }
    
    public void dibujarLineaBetweenOperators(MouseEvent e ){
        Point p1 = new Point(e.getPoint().x , e.getPoint().y);
        Point p2 = new Point();
        System.out.println("POINT : (" + p1.x +","+ p1.y + ")asdasdsadsad" + this.getPadre().getComponentAt(e.getPoint())); 
        System.out.println("LOCATION : (" + this.getLocation().x +","+ this.getLocation().y + ")asdasdsadsad" + this.getPadre().getComponentAt(e.getPoint())); 
        // CREAR PUNTO QUE QUIERO REALMENTE 
        p2.x = this.getLocation().x + p1.x;
        p2.y = this.getLocation().y + p1.y;
        System.out.println("POINT2.0 : (" + p2.x +","+ p2.y + ")asdasdsadsad" + this.getPadre().getComponentAt(p2)); 
        Line2D linea = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
         this.lines.add(linea);
    //    this.padre.dibujarLineaEntre2Copmponentes(linea);
        
        
    //    COmponente lista3 asd 146 ),(29 //
    //    (3,-7) asdasdsadsadnull //
        
        
        
    /*
    Point aPoint = new Point();
    Point bPoint = new Point(50, 25);
    Point cPoint = new Point(bPoint);
    
    System.out.println("cPoint is located at: " + cPoint);
    
    System.out.println("aPoint is located at: " + aPoint);
    aPoint.move(100, 50);
    System.out.println("aPoint is located at: " + aPoint);

    bPoint.x = 110;
    bPoint.y = 70;
    System.out.println("bPoint is located at: " + bPoint);

    aPoint.translate(10, 20);
    
    System.out.println("aPoint is now at: " + aPoint);
    

    if (aPoint.equals(bPoint))
      System.out.println("aPoint and bPoint are at the same location.");
*/
    }    
    
        public void exportarOperadorADibujar(MouseEvent e ){
            QualyOperatorsPanel papi = (QualyOperatorsPanel) this.getParent();
            Point ubiActual = yoOperador.getLocation();
            ubiActual.x += e.getPoint().x;
            ubiActual.y += e.getPoint().y;
            int tipoOperador; 
            Color bg = this.getBackground();
        if (bg == Color.RED) {
            tipoOperador = 0;
        } else if (bg == Color.GRAY) {
            tipoOperador = 1;
        } else if (bg == Color.GREEN) {
            tipoOperador = 2;
        } else{ // if (bg == Color.YELLOW) 
            tipoOperador = 3;
        }
        papi.setPositionToDraw(ubiActual,tipoOperador);
    }
}
