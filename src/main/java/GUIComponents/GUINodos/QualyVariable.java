package GUIComponents.GUINodos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class QualyVariable extends QsNodo{
    String nombre;
    String nombreGrupo;// Categoría de la variable 
    
    public QualyVariable (JPanel parent, double x, double y,int id, float value){
        super(value);
        this.setName("var_" + id); 
        this.setBounds((int)x,(int)y,101,200);
        this.setBackground(Color.PINK);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(new ClickListener());
    }

    /*                                                     *
     *  -------------------- GUI WORK ---------------------* 
     *                                                     */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawOval(0, 0, 350, 200);
        g.drawString("" + super.getResValue(), 30, 56);
        
        
    }


    private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            System.out.println("Soy una variablle");
        }
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
            System.out.println(evt.getPoint());
        }
    }
    
}
