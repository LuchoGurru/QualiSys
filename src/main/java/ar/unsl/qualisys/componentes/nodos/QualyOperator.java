package ar.unsl.qualisys.componentes.nodos;

import ar.unsl.qualisys.paneles.DragAndDropVariablesAndOperandsPanel;
import ar.unsl.qualisys.paneles.QualyOperatorsPanel;
import ar.unsl.qualisys.utils.eliminarQsNodo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 * Esta clase forma parte de los operadores 
 * tiene un nombre un symbolo, 
 * @author luciano.gurruchaga
 */
public class QualyOperator extends QsNodo implements QsOperacion{
    
    private DragAndDropVariablesAndOperandsPanel GUIParent; // Solo se asigna en caso de que sea hijo del componente DAD (Drag and Drop).
    private String padreID;
    private float resultValue;
    JPopupMenu menuDesplegable = new JPopupMenu();
 
    // private ArrayList<JPanel> dominio = new ArrayList<>();; //nodo hijo// Recibe un maximo de 5 argum 
    
    public QualyOperator(JPanel parent, int id){
        super();
        this.menuPopUp();
        this.setName("op_" + id); 
        this.padreID = "";
        if(parent.getClass() == DragAndDropVariablesAndOperandsPanel.class){ 
            this.GUIParent = (DragAndDropVariablesAndOperandsPanel) parent;     // Para usar los metodos del DandD directamente
        }
        this.setPreferredSize(new Dimension(100,100));
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);
    } 
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 100, 100);
        g.fillOval(25, 25, 50, 50);
        g.setFont(new Font("Serif", Font.BOLD, 24));
        g.drawString("D++", 30, 56);
        
        
    }

    public DragAndDropVariablesAndOperandsPanel getGUIParent() {
        return GUIParent;
    } 

    public String getPadreID() {
        return padreID;
    }

    public void setPadreID(String padreID) {
        this.padreID = padreID;
    }

    public float getResultValue() {
        return resultValue;
    }

    public void setResultValue(float resultValue) {
        this.resultValue = resultValue;
    }
  /* ASII DIBUJABA EL DOMINIO 
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
    */
    
    /**
     * Decide la accion a realizar en caso de que se haya hecho un soltado de click
     * @param e evento del mouse
     */
    public void determinarAccionReleased(MouseEvent e){
       if(this.getGUIParent()!= null ){ // Verdadero si esta en DandD
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
        System.out.println("POINT : (" + p1.x +","+ p1.y + ")asdasdsadsad" + this.getGUIParent().getComponentAt(e.getPoint())); 
        System.out.println("LOCATION : (" + this.getLocation().x +","+ this.getLocation().y + ")asdasdsadsad" + this.getGUIParent().getComponentAt(e.getPoint())); 
        // CREAR PUNTO QUE QUIERO REALMENTE 
        p2.x = this.getLocation().x + p1.x;
        p2.y = this.getLocation().y + p1.y;
        System.out.println("POINT2.0 : (" + p2.x +","+ p2.y + ")asdasdsadsad" + this.getGUIParent().getComponentAt(p2)); 
        Line2D linea = new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }    
/*    public ArrayList<JPanel> getDominio(){
        return this.dominio;
    }
  /*
    
    /**
        Cuando el componente esta en el panel de operadores se arrastra hasta el DandD panel para crear una nueva instancia del operadore en la posicion deseada 
    */ 
    public void exportarOperadorADibujar(MouseEvent e){
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
    * EL evento toma el punto relativo de el arrastrado del mouse.
    * Ajusto las coordenadas sumandoles la posicion del componente para enviar 
    * la posision absoluta en el panel DAndD
    */     
   public void addToFatherDomain(Point padreRelativeLocation){ 
        // CREAR PUNTO QUE QUIERO REALMENTE 
        Point padreLocation = new Point();
        padreLocation.x = this.getLocation().x + padreRelativeLocation.x;
        padreLocation.y = this.getLocation().y + padreRelativeLocation.y;
        this.GUIParent.addToDomain(this,padreLocation);
   }

    @Override
    public double calcularOperacion(double... dominio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double functionEvaluation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double calcularOperacion(ArrayList<Double> dominio) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
   /**
    * Dado una entrada, agregamos la entrada al dominio
    * @param entrada 
    *
   public boolean addToDomain(JPanel entrada){
       boolean added=false;
       if(this.dominio.size()<5){
           this.dominio.add(entrada);
           added=true;
       }
       return added; ctrl +8
   }     */
    private class ClickListener extends MouseAdapter {
        private QualyOperator qsOpInstance;
        public ClickListener(QualyOperator qsOpInstance){
            this.qsOpInstance = qsOpInstance;
        }
        /**
         * El Click despliega el menu item
         * @param evt 
         */
        public void mousePressed(MouseEvent evt){
            //.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
            if(GUIParent!=null)
                GUIParent.setOperadorSeleccionado(this.qsOpInstance);
        }
        public void mouseReleased(MouseEvent e){
            determinarAccionReleased(e); 
        }
        
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
             //TODO
        }
    }/*
    private class menuDesplegableListener implements ActionListener{
        private QualyOperator qsOpInstance;
        public menuDesplegableListener(QualyOperator qsOpInstance){
            this.qsOpInstance = qsOpInstance;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
                new eliminarQsNodo(GUIParent,qsOpInstance).ejecutar();
        }
        
    }*/
    public void menuPopUp(){
        JMenuItem eliminar = new JMenuItem("Eliminar");
        JMenuItem eliminarRel = new JMenuItem("Eliminar relación");
        JMenuItem setPonderación = new JMenuItem("Editar ponderación");
        eliminar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,InputEvent.CTRL_DOWN_MASK));
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("e = " + e);
                new eliminarQsNodo(GUIParent).ejecutar();
            }
        });
      
        menuDesplegable.add(eliminar);
        menuDesplegable.add(eliminarRel);
        menuDesplegable.add(setPonderación);

        this.setComponentPopupMenu(menuDesplegable);
    }
}
