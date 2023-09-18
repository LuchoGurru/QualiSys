package ar.unsl.qualisys.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalTabbedPaneUI;

public class QsTabPanel extends JTabbedPane {

    public QsTabPanel() {
        setUI(new MaterialTabbedUI());
    }

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
                g2.setColor(new Color(3, 155, 216));
                currentRectangle = new Rectangle(x, y, w, h);
                g2.fillRect(currentRectangle.x, currentRectangle.y + currentRectangle.height - 3, currentRectangle.width, 3);
            }
            g2.dispose();
        } 
    }
}