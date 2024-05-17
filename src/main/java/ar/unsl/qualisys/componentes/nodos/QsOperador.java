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
import javax.swing.JComponent;
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
    private double d;
    private double r2;
    private double r3;
    private double r4;
    private double r5;
    private JPopupMenu menuDesplegable;
    private boolean editable;
    //--- LSP  
    private static double xmin=0;
    private static double xmax=0;
    
    
    /**
     * Usado por panel de Operadores.
     */
    public QsOperador(QsOperatorsPanel GUIparent,String nombre,String symbol, double d, double r2, double r3, double r4, double r5){
        super();
        this.OPParent=GUIparent; 
        this.DADParent=null;
        this.editable = true;
        this.menuPopUp();
        this.setName("op_0_desactivado"); 
        this.setPreferredSize(new Dimension(51,51)); // no anda setSize ni setBounds ..
        this.padreID = "";
        this.symbol = symbol;
        this.nombre = nombre;
        this.d = d;
        this.r2=r2;
        this.r3=r3;
        this.r4=r4;
        this.r5=r5;
        this.setToolTipText(this.nombre);
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
//        DragListener dragListener = new DragListener(this);
//        this.addMouseMotionListener(dragListener);
    } 
    /**
     * Usado por panel de Drag and Drop(Arrastrar y soltar).
     */
    public QsOperador(QsDadPanel GUIparent, int x,int y,int w,int h, String name,String nombre,String symbol, double d, double r2, double r3, double r4, double r5,double ponderacion){
        super();
        this.OPParent=null;
        this.DADParent=GUIparent;
        super.setPonderacion(ponderacion);
        this.setBackground(Color.white);        
        this.setBounds(x,y,w,h);
        this.menuPopUp();
        this.editable = true;
        this.setName(name); 
        this.padreID = "";
        this.symbol = symbol;
        this.nombre = nombre;
        this.d = d;
        this.r2=r2;
        this.r3=r3;
        this.r4=r4;
        this.r5=r5;  
        this.setToolTipText(this.nombre);
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
//        DragListener dragListener = new DragListener(this);
//        this.addMouseMotionListener(dragListener);

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
    private void ajustarPosicionRelativa(MouseEvent e, JComponent comparadorX, JComponent comparadorY){
        Point posRelativaPantalla = e.getLocationOnScreen();

    }
    /**
     * Decide la accion a realizar en caso de que se haya hecho un soltado de click
     * @param e evento del mouse
     */
    public void determinarAccionReleased(MouseEvent e){
 
        System.out.println("e.getLocationOnScreen() = " + e.getLocationOnScreen());
        System.out.println("e.getButton() = " + e.getButton());
        System.out.println("\ne.getButton() = " + e.getPoint()+"\n");
       if(this.getGUIParent()!= null ){ // Verdadero si esta en DandD
                   System.out.println("e.getLocationOnScreen() = " + this.getGUIParent().getLocationOnScreen());

            Rectangle oldPosition = this.getBounds(); // Guardo la posision inicial
            
                int corrimientoX = e.getLocationOnScreen().x - this.getGUIParent().getLocationOnScreen().x;
                int corrimientoY = e.getLocationOnScreen().y - this.getGUIParent().getLocationOnScreen().y;
            //        int corrimientoY = posRelativaPantalla.y - GUIParent.getGUIParent().getLocationOnScreen().y; 
                if(corrimientoX>0){
                    corrimientoX=0;
                }
                 if(corrimientoY>0){
                    corrimientoY=0; 
                }
        
            
            this.setBounds(this.getBounds().getLocation().x+ e.getPoint().x - 20 -corrimientoX, //para centrar el mouse y sacar el margin
                                 this.getBounds().getLocation().y + e.getPoint().y -20-corrimientoY , //para centrar el mouse
                                51, 51);
            if (!DADParent.isColision(this)) { 
                DADParent.repaint(); // actualizo la posicion de this en el panel.
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
        papi.dibujarOperadorEn(e.getLocationOnScreen(),ubiActual,this);
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

   // -------------------PARTE LSP------------------ 
   
    /**
     * Asigna xmin = valor 'x[i] menor' y xmax = 'x[i] mayor' 
     * @param n
     * @param x 
     */
    public static void minmax (int n, double x[]){ //, double xmin, double xmax){
      xmin = x[0];
      xmax = xmin;
      for(int i=1; i<n; i++)
	 if (x[i] < xmin)
	     xmin = x[i];
	 else
	     if (x[i] > xmax) xmax = x[i];
    }
    
    public static double plog(double t){
      return t * ( 1. - t * ( 0.5 - t / 3. ) );
    }
    /** 
     *--------- WEIGHTED POWER MEAN FUNCTION-----------------------|
     *                                                             |
     *    Xmean = [W[0]*x[0]**r + ... + W[n-1]*x[n-1]**r] ** (1/r) |
     *    x[0 .. n-1] = input values (preferences)                 |
     *    W[0 .. n-1] = positive weights; W[0]+...+W[n-1] = 1      |
     *    r           = exponent (any real value)                  |
     *-------------------------------------------------------------|
     * 
     * @param n cardinalidad del dominio
     * @param W ponderaciones de cada entrada e n
     * @param r el valor posta
     * @param x el valor de las variables
     * @return 
     */
    @Override
    public double wpmFunction (int n, double W[], double r, double x[]){
      double rmin = 1.E-16, rmax = 1.E+16, small = 1.E-9;
      double xminlog, xmaxlog, h, xmean;
      int i;

      minmax (n, x);  //, xmin, xmax );

      if ( r < -rmax )
	 xmean = xmin;
      else if (r < -rmin)
      {
	 xmean = 0.;
	 if ( xmin > 0. )
	 {
	    xminlog = Math.log(xmin);
            for(i=0; i<n; i++)
            {
               h = r * ( Math.log(x[i]) - xminlog ); // Multiplica el r * logaritmo de el valor actual - logaritmo del valor menor
               xmean += W[i] * (1. + Math.exp(h)) * Math.tanh(h/2.); // actualiza xmean pesado con W[i] y multiplicado por exp .h y tanh.h ... 
            }
            if ( Math.abs(xmean) > small) //Decide si hacer log o plog .. no se que hace el plog
               xmean = Math.exp( xminlog + Math.log(1. + xmean)/r );
            else
               xmean = Math.exp( xminlog + plog(xmean)/r );
         }
      }
      else if (r <= rmin)
      {
         xmean = 0.;
         if ( xmin > 0. )
         {
            for(i=0; i<n; i++) 
                xmean += W[i]*Math.log(x[i]); //saca una media mas poronga
            xmean = Math.exp(xmean);
         }
      }
      else if (r <= rmax)
      {
         xmean = 0.;
         if ( xmax > 0. )
         {
            xmaxlog = Math.log(xmax);
            for(i=0; i<n; i++)
               if (x[i] > 0.)
               {
                  h = r * ( Math.log(x[i]) - xmaxlog );
                  xmean += W[i] * (1. + Math.exp(h)) * Math.tanh(h/2.); //ajustaria el limite con el h
	       }
               else xmean -= W[i];

            if ( Math.abs(xmean) > small)  // segun el limite si no es muy pequeño usalog o plog ... 
               xmean = Math.exp( xmaxlog + Math.log(1. + xmean)/r );
            else
               xmean = Math.exp( xmaxlog + plog(xmean)/r );
         }
      }
      else             //     r > Rmax
         xmean = xmax; 
      return xmean;
    }

    @Override
    public double wpmFunction(ArrayList<QsNodo> dominio) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        int n = dominio.size();
        double[] x = new double[n];
        double[] w = new double[n];
        double r = this.getR(n);
        
        for(int i=0; i<n;i++){
            x[i]=dominio.get(i).getValorResultado(); 
            w[i]=dominio.get(i).getPonderacion();
        }
        
        double result = wpmFunction(n,w,r,x);
        System.out.println("result = " + result);
        return result;
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
    public void setEditable(boolean editable){
        if(!editable)
            this.setComponentPopupMenu(null);
        else
            this.menuPopUp();
        this.editable = editable;
    }
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
            if(editable && DADParent!=null){
                //this.qsOpInstance.setBackground(Color.decode("#A3A380"));
                DADParent.setNodoSeleccionado(this.qsOpInstance);
            }
        }
        public void mouseReleased(MouseEvent e){
            if(editable) 
                determinarAccionReleased(e); 
        }
        
    }

    public void menuPopUp(){
        menuDesplegable = new JPopupMenu();
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
    /**
     * n= cardinalidad del dominio
    */
    public double getR(int n){
        switch(n){
            case 2:
                return r2;
            case 3: 
                return r3;
            case 4:
                return r4;
            case 5:
                return r5;
            default:
                return 0d;
        }
    }
    
    public double getD() {
        return d;
    }

    public void setD(double d) {
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
        jsonVar.put("name", this.getName()); // op_n
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
