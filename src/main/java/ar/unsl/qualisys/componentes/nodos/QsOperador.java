package ar.unsl.qualisys.componentes.nodos;

import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import ar.unsl.qualisys.paneles.grafo.QsOperatorsPanel;
import ar.unsl.qualisys.paneles.grafo.comandos.cambiarOperador;
import ar.unsl.qualisys.paneles.grafo.comandos.editarPesoPonderado;
import ar.unsl.qualisys.paneles.grafo.comandos.eliminarQsNodo;
import ar.unsl.qualisys.paneles.grafo.comandos.eliminarQsRelacion;
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
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import org.json.JSONObject;

/**
 * Esta clase forma parte de los operadores 
 * tiene un nombre un symbolo, 
 * @author luciano.gurruchaga
 */
public class QsOperador extends QsNodo implements QsOperacion{
    
    private QsDadPanel DADParent; // Solo se asigna en caso de que sea hijo del componente DAD (Drag and Drop).
    private QsOperatorsPanel OPParent; // Solo se asigna en caso de que sea hijo del componente DAD (Drag and Drop).
    private String symbol ;
    private String nombre;
    private String padreID;
    private float resultValue;
    private float d;
    private double r2;
    private double r3;
    private double r4;
    private double r5;
    private JPopupMenu menuDesplegable = new JPopupMenu();
    // private ArrayList<JPanel> dominio = new ArrayList<>();; //nodo hijo// Recibe un maximo de 5 argum 
    
    public QsOperador(JPanel GUIparent, int name,String nombre,String symbol, float d, double r2, double r3, double r4, double r5){
        super();
        this.menuPopUp();
        this.setName("op_" + name); 
        this.padreID = "";
        this.symbol = symbol;
        this.nombre = nombre;
        this.d = d;
        this.r2=r2;
        this.r3=r3;
        this.r4=r4;
        this.r5=r5;

        if(GUIparent.getClass() == QsDadPanel.class){ 
            this.DADParent = (QsDadPanel) GUIparent;     // Para usar los metodos del DandD directamente
        }else{
            this.OPParent = (QsOperatorsPanel) GUIparent;
        }
        this.setPreferredSize(new Dimension(51,51));
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
//        DragListener dragListener = new DragListener(this);
   //     this.addMouseMotionListener(dragListener);
        this.setToolTipText(this.nombre);

    } 
    
    public QsOperador(JPanel GUIparent, int name,String nombre,String symbol, float d, double r2, double r3, double r4, double r5,float ponderacion){
        super();
        this.menuPopUp();
        this.setName("op_" + name); 
        this.padreID = "";
        this.symbol = symbol;
        this.nombre = nombre;
        this.d = d;
        this.r2=r2;
        this.r3=r3;
        this.r4=r4;
        this.r5=r5;
        this.setPonderacion(ponderacion);
        if(GUIparent.getClass() == QsDadPanel.class){ 
            this.DADParent = (QsDadPanel) GUIparent;     // Para usar los metodos del DandD directamente
        }else{
            this.OPParent = (QsOperatorsPanel) GUIparent;
        }
        this.setPreferredSize(new Dimension(51,51));
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
//        DragListener dragListener = new DragListener(this);
   //     this.addMouseMotionListener(dragListener);
        this.setToolTipText(this.nombre);

    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 50, 50);
        g.drawOval(5, 5, 40, 40); // (25,25,50,50)
        g.setFont(new Font("Serif", Font.BOLD, 12));
        if(this.symbol.length()==1){
            g.drawString(this.symbol, 20, 30);
        }else if(this.symbol.length()==2){
            g.drawString(this.symbol, 15, 30);
        }else{
            g.drawString(this.symbol, 10, 30);
        }
        
    }
    //METHODS
    
    /**
     * Decide la accion a realizar en caso de que se haya hecho un soltado de click
     * @param e evento del mouse
     */
    public void determinarAccionReleased(MouseEvent e){
        System.out.println("e.getButton() = " + e.getButton());
        System.out.println("\ne.getButton() = " + e.getPoint()+"\n");
       if(this.getGUIParent()!= null ){ // Verdadero si esta en DandD
            Rectangle oldPosition = this.getBounds(); // Guardo la posision inicial
            this.setBounds(this.getBounds().getLocation().x+ e.getX() - 20, //para centrar el mouse y sacar el margin
                                 this.getBounds().getLocation().y + e.getY() -20 , //para centrar el mouse
                                51, 51);
            if (!DADParent.isColision(this)) { 
                DADParent.repaint();
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
        Point scrollPosition = OPParent.getGUIpadre().getScroll().getViewport().getViewPosition();
        QsOperatorsPanel papi = (QsOperatorsPanel) this.getParent();
        Point ubiActual = this.getLocation();
        ubiActual.x += e.getPoint().x + scrollPosition.x;
        ubiActual.y += e.getPoint().y + scrollPosition.y; 
        papi.dibujarOperadorEn(ubiActual,this);
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
        this.DADParent.addToDomain(this,padreLocation);
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
        private QsOperador qsOpInstance;
        public ClickListener(QsOperador qsOpInstance){
            this.qsOpInstance = qsOpInstance;
        }
        /**
         * El Click despliega el menu item
         * @param evt 
         */
        public void mousePressed(MouseEvent evt){
            if(DADParent!=null){
                this.qsOpInstance.setBackground(Color.WHITE);
                DADParent.setNodoSeleccionado(this.qsOpInstance);
            }
        }
        public void mouseReleased(MouseEvent e){
            determinarAccionReleased(e); 
        }
        
    }
    /*
    private class DragListener extends MouseMotionAdapter {
        private QsOperator qsOpInstance;
        public DragListener(QsOperator qsOpInstance){
            this.qsOpInstance = qsOpInstance;
        }
        @Override
        public void mouseDragged(MouseEvent evt){ 
            this.qsOpInstance.setBounds(evt.getPoint().x, evt.getPoint().y, this.qsOpInstance.getWidth(), this.qsOpInstance.getHeight());
            this.qsOpInstance.repaint();
        }
    }
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
        JMenuItem cambiarOperador = new JMenuItem("Cambiar Operador");
        eliminar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,InputEvent.CTRL_DOWN_MASK));
        eliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarQsNodo(DADParent).ejecutar();
            }
        });
        
        eliminarRel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarQsRelacion(DADParent).ejecutar();
            }
        });
        
       setPonderación.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new editarPesoPonderado(DADParent).ejecutar();
            }
        });
      
        cambiarOperador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new cambiarOperador(DADParent).ejecutar();
            }
        });
        
        
        menuDesplegable.add(eliminar);
        menuDesplegable.add(eliminarRel);
        menuDesplegable.add(setPonderación);
        menuDesplegable.add(cambiarOperador);
        
        this.setComponentPopupMenu(menuDesplegable);
    }
    //GET Y SET

    public QsDadPanel getGUIParent() {
        return DADParent;
    }

    public void setGUIParent(QsDadPanel GUIParent) {
        this.DADParent = GUIParent;
    }

    @Override
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public double getR2() {
        return r2;
    }

    public void setR2(double r2) {
        this.r2 = r2;
    }

    public double getR3() {
        return r3;
    }

    public void setR3(double r3) {
        this.r3 = r3;
    }

    public double getR4() {
        return r4;
    }

    public void setR4(double r4) {
        this.r4 = r4;
    }

    public double getR5() {
        return r5;
    }

    public void setR5(double r5) {
        this.r5 = r5;
    }
    
    public JSONObject toJSON(){
        JSONObject jsonVar= new JSONObject();
        jsonVar.put("name", this.getName()); // var_1.n.n
        jsonVar.put("nombre", this.nombre);
        jsonVar.put("symbol", this.symbol);
        jsonVar.put("d", this.d);
        jsonVar.put("r2",this.r2);
        jsonVar.put("r3",this.r3);
        jsonVar.put("r4",this.r4);
        jsonVar.put("r5",this.r5);
        jsonVar.put("x",this.getBounds().x);
        jsonVar.put("y",this.getBounds().y);
        jsonVar.put("width",this.getBounds().width);
        jsonVar.put("height",this.getBounds().height);
        jsonVar.put("padreID",this.getPadreID());
        jsonVar.put("ponderacion",this.getPonderacion());
        return jsonVar;
    }
    
}
