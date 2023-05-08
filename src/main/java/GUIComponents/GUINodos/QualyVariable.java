package GUIComponents.GUINodos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class QualyVariable extends JPanel{
    private String padreID;
    private float value; 
    
    public QualyVariable(JPanel parent, double x, double y,int id, float value){
        this.value = value;
        this.padreID = "";
        this.setName("var_" + id); 
        this.setBounds((int)x,(int)y,101,200);
        this.setBackground(Color.PINK);
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(new ClickListener());
    }

    public String getPadreID() {
        return padreID;
    }

    public void setPadreID(String padreID) {
        this.padreID = padreID;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    /*                                                     *
     *  -------------------- GUI WORK ---------------------* 
     *                                                     */
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(1, 1, 100, 200); 
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
