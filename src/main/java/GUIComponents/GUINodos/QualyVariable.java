package GUIComponents.GUINodos;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class QualyVariable extends JPanel{

    String id;
    float value;
    
    
    
    
public QualyVariable(JComponent parent, double x, double y,String id, float value){
        this.setAlignmentX((float)x);
        this.setAlignmentY((float)y);
      //  setPreferredSize(new Dimension(101,101));
        this.setBackground(Color.red);
        this.setVisible(true);
    //    ClickListener clickListener = new ClickListener();
    //    this.addMouseListener(new ClickListener());
    //    DragListener dragListener = new DragListener();
    //    this.addMouseMotionListener(dragListener);
        this.setBounds((int)x,(int)y,101,200);
        this.id=id;
        this.value=value;
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(new ClickListener());
    }

    public QualyVariable(String id, float value){
        this.id=id;
        this.value=value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    //    g.setColor(Color.red);        
      // g.drawOval(25, 25, 50, 50);   // 

        g.drawRect(1, 1, 100, 200); // 50 hasta 150 // Diametro
        
    }
        private class ClickListener extends MouseAdapter {
        public void mousePressed(MouseEvent evt){
            System.out.println("Soy una variablle");
        }
    }
    
    private class DragListener extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent evt){ 
            //previousPoint =  evt.getPoint(); 
            System.out.println(evt.getPoint());
            //repaint();
        }
    }
    
}
