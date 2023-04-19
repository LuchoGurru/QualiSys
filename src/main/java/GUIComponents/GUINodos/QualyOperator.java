package GUIComponents.GUINodos;

import GUIComponents.Laminas.DragAndDropVariablesAndOperandsPanel;
import GUIComponents.Laminas.QualyOperatorsPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class QualyOperator extends JPanel{
    
    private String nombre;
    private float rango;
    private DragAndDropVariablesAndOperandsPanel GUIParent; // Panel DandD padre ... si esta en el panel de operadores queda en null
    private ArrayList<JPanel> dominio = new ArrayList<>();; //nodo hijo// Recibe un maximo de 5 argum 
    
    public QualyOperator(JPanel parent, int id){
        this.setName("operador" + id); 
        if(parent.getClass() == DragAndDropVariablesAndOperandsPanel.class){ 
            this.GUIParent = (DragAndDropVariablesAndOperandsPanel) parent;     // Para usar los metodos del DandD directamente
        }
        this.setPreferredSize(new Dimension(100,100));
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(new ClickListener());
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 100, 100);
        g.drawOval(25, 25, 50, 50);   
    }

    public float evaluationFunction(){
        rango = 1.0f; // dom[i] 0<i<5 evaluation by function
        return rango;
    }
    
    public DragAndDropVariablesAndOperandsPanel getPadre() {
        return GUIParent;
    }
    public void dibujarHijos(Graphics g){
        //Como soy un operador 
        for(JPanel hijo : this.dominio){
            Point origen = hijo.getLocation();
            dibujarLineaHijo(g,origen);
        }
    }
    public void dibujarLineaHijo(Graphics g, Point h){
        Line2D linea = new Line2D.Double(h.getX(), h.getY(), this.getX(), this.getY()); 
    }
    
    
    /**
     * Decide la accion a realizar en caso de que se haya hecho un soltado de click
     * @param e evento del mouse
     */
    public void determinarAccionReleased(MouseEvent e){
       if(this.getPadre() != null ){ // Verdadero si esta en DandD
            Rectangle oldPosition = this.getBounds(); // Guardo la posision inicial
            this.setBounds(this.getBounds().getLocation().x + e.getX(),
                                 this.getBounds().getLocation().y + e.getY(),
                                100, 100);
            if (!GUIParent.isColision(this)) { 
                GUIParent.repaint();
            } else {
                this.setBounds(oldPosition); //Vuelvo a la posision Inicial
                this.addToFatherDomain(e.getPoint());//Sí 
            }
        }else{
            exportarOperadorADibujar(e);
        }
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
    }    
    public ArrayList<JPanel> getDominio(){
        return this.dominio;
    }
    /**
        Cuando el componente esta en el panel de operadores se arrastra hasta el DandD panel para crear una nueva instancia del operadore en la posicion deseada 
    */ 
    public void exportarOperadorADibujar(MouseEvent e ){
        QualyOperatorsPanel papi = (QualyOperatorsPanel) this.getParent();
        Point ubiActual = this.getLocation();
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

   /**
    * Agregamos esta instancia al dominio del padre
    */     
   public void addToFatherDomain(Point padreRelativeLocation){ 
         Point padreLocation = new Point();
        // CREAR PUNTO QUE QUIERO REALMENTE 
        padreLocation.x = this.getLocation().x + padreRelativeLocation.x;
        padreLocation.y = this.getLocation().y + padreRelativeLocation.y;
        GUIParent.canBeDomain(this,padreLocation);
        
        QualyOperator operadorPadre = (QualyOperator) this.getPadre().getComponentAt(padreLocation) ;
        if(operadorPadre.addToDomain(this)){
            this.getPadre().repaint();
        }
        
        System.out.println("POINT2.0 : (" + padreLocation.x +","+ padreLocation.y + ")CERO KM" + this.getPadre().getComponentAt(padreLocation)); 
        //Line2D linea = new Line2D.Double(this.getLocation().getX(), this.getLocation().getY(), padreLocation.getX(), padreLocation.getY());
   }     

   
   /**
    * Dado una entrada, agregamos la entrada al dominio
    * @param entrada 
    */
   public boolean addToDomain(JPanel entrada){
       boolean added=false;
       if(this.dominio.size()<5){
           this.dominio.add(entrada);
           added=true;
       }
       return added;
   }     
    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            //.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        }
        public void mouseReleased(MouseEvent e){
            determinarAccionReleased(e); 
        }
        
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
             //TODO
        }
    }
}
