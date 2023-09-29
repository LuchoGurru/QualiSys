package ar.unsl.qualisys.componentes.nodos;

import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import ar.unsl.qualisys.paneles.grafo.comandos.cambiarOperador;
import ar.unsl.qualisys.paneles.grafo.comandos.editarPesoPonderado;
import ar.unsl.qualisys.paneles.grafo.comandos.eliminarQsNodo;
import ar.unsl.qualisys.paneles.grafo.comandos.eliminarQsRelacion;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

public class QsVariable extends QsNodo{
    String nombre;
    String nombreGrupo;// Categoría de la variable 
    private QsDadPanel GUIParent; // Solo se asigna en caso de que sea hijo del componente DAD (Drag and Drop).
    private JPopupMenu menuDesplegable = new JPopupMenu();

    public QsVariable (QsDadPanel parent, double x, double y,String id,String nombre){
        this.menuPopUp();
        this.setName("var_" + id); 
        this.GUIParent = parent;
        this.nombre=nombre;
        this.setBounds((int)x,(int)y,50,30);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        ClickListener clickListener = new ClickListener(this);
        this.addMouseListener(clickListener);
        this.setToolTipText(this.nombre);
    }

    /*                                                     *
     *  -------------------- GUI WORK ---------------------* 
     *                                                     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String numeracion = this.getName().split("_")[1];
        g.drawString(numeracion , 10, 20);
        
        
    }
        public void menuPopUp(){
        JMenuItem eliminarRel = new JMenuItem("Eliminar relación");
        JMenuItem setPonderación = new JMenuItem("Editar ponderación");
        
        eliminarRel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new eliminarQsRelacion(GUIParent).ejecutar();
            }
        });
        
       setPonderación.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new editarPesoPonderado(GUIParent).ejecutar();
            }
        });
      
        
        menuDesplegable.add(eliminarRel);
        menuDesplegable.add(setPonderación);
        
        this.setComponentPopupMenu(menuDesplegable);
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
               GUIParent.setNodoSeleccionado(this.qsVarInstance);
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
    
}
