package ar.unsl.qualisys.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

public class QsTabPanel extends JTabbedPane {
    private JTabbedPane tabInstance;
    private Color selectedColor ;
    private Color unselectedColor;


    public QsTabPanel() {
        tabInstance = this;
       // UIManager.put("TabbedPane.selected", Color.RED); // Change this color to your desired color
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBackground(Color.decode("#D6CE93"));
        selectedColor = Color.decode("#D8A48F");
        unselectedColor = Color.decode("#A3A380");
       // unselectedColor = Color.decode("#D6CE93");
        setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        setUI(new TabbedPaneCustomUI(this));
    //    setUI(new MaterialTabbedUI());
        setOpaque(true);
    
    }
    

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        // Dibuja el fondo detrás de las pestañas
        g.setColor(Color.decode("#A3A380"));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
    
    
    
    
    

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
        repaint();
    }

    public Color getUnselectedColor() {
        return unselectedColor;
    }

    public void setUnselectedColor(Color unselectedColor) {
        this.unselectedColor = unselectedColor;
        repaint();
    }
/*
    public class MaterialTabbedUI extends MetalTabbedPaneUI {
        private Rectangle currentRectangle;
 
        public MaterialTabbedUI() {
        } 
        @Override
        protected Insets getTabInsets(int i, int i1) {
            return new Insets(10, 10, 10, 10);
        }

        @Override
        protected void paintTabBorder(Graphics grphcs, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (isSelected) {
                g2.setColor(Color.decode("#BB8588"));
                currentRectangle = new Rectangle(x, y, w, h);
                g2.fillRect(currentRectangle.x, currentRectangle.y + currentRectangle.height - 4, currentRectangle.width, 4);
            }
            g2.dispose();
        } 
        
        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            if (isSelected) {
                g.setColor(selectedColor);
                g.fillRect(x, y, w, h);
            } else {
                g.setColor(unselectedColor);
                g.fillRect(x, y, w, h);
            }
        }
        
        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
            Rectangle rect = rects[selectedIndex];
            if (rect != null && tabInstance.getTabCount() > 0) {
                g.setColor(Color.RED);
                int x = tabInstance.getBounds().x;
                int y = tabInstance.getBounds().y;
                int width = tabInstance.getBounds().width;
                int height = rect.y - y;
                g.fillRect(x, y, width, height);
            }
            super.paintTabArea(g, tabPlacement, selectedIndex);
        }
        
    }*/
}