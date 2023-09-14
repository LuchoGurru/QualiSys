package ar.unsl.qualisys.componentes.nodos;

import ar.unsl.qualisys.paneles.QsDadPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
public class QsVariable extends QsNodo{
    String nombre;
    String nombreGrupo;// Categoría de la variable 
    private QsDadPanel GUIParent; // Solo se asigna en caso de que sea hijo del componente DAD (Drag and Drop).
    private Popup popup;
    public QsVariable (QsDadPanel parent, double x, double y,String id,String nombre){
        this.setName("var_" + id); 
        this.setToolTipText("Variable A Evaluar");
        this.GUIParent = parent;
        this.nombre=nombre;
        this.setBounds((int)x,(int)y,50,30);
        this.setBackground(Color.PINK);
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (popup != null) {
                    popup.hide();
                }
                JLabel text = new JLabel("You've clicked at: " + e.getPoint());
                popup = PopupFactory.getSharedInstance().getPopup(e.getComponent(), text, e.getXOnScreen(), e.getYOnScreen());
                popup.show();
            }
            @Override
                public void mouseExited(MouseEvent e) {
                    popup.hide(); 
                }
        });
    }

    /*                                                     *
     *  -------------------- GUI WORK ---------------------* 
     *                                                     */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 350, 200);
        String numeracion = this.getName().split("_")[1];
        g.drawString(numeracion , 10, 10);
        
        
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
    
    
    /**
     * Decide la accion a realizar en caso de que se haya hecho un soltado de click
     * @param e evento del mouse
     */
    public void accionSoltar(MouseEvent e){ 
        Point puntin = new Point(this.getBounds().getLocation().x + e.getX(), this.getBounds().getLocation().y + e.getY());
        System.out.println("Punto " + e.getPoint());
        if (GUIParent.getOperatorByLocation(puntin) != null) { 
            this.addToFatherDomain(e.getPoint());//Sí 
        } 

   }

    private class ClickListener extends MouseAdapter {
        private QsVariable qsVarInstance;
        public ClickListener(QsVariable qsVarInstance){
            this.qsVarInstance = qsVarInstance;
        }
        /**
         * El Click despliega el menu item
         * @param evt 
         */
        public void mousePressed(MouseEvent evt){
            //.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
            //if(GUIParent!=null)
             //   GUIParent.setOperadorSeleccionado(this.qsVarInstance);
        }
        public void mouseReleased(MouseEvent e){
            accionSoltar(e); 
        }
        
    }
    /*
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
            System.out.println(evt.getPoint());
        }
    }*/
    
}
