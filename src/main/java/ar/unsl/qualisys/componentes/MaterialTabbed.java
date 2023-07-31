package ar.unsl.qualisys.componentes;

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

public class MaterialTabbed extends JTabbedPane {

    public MaterialTabbed() {
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

              /*  if (tabPlacement == TOP) {
                } else if (tabPlacement == BOTTOM) {
                    g2.fillRect(currentRectangle.x, currentRectangle.y, currentRectangle.width, 3);
                } else if (tabPlacement == LEFT) {
                    g2.fillRect(currentRectangle.x + currentRectangle.width - 3, currentRectangle.y, 3, currentRectangle.height);
                } else if (tabPlacement == RIGHT) {
                    g2.fillRect(currentRectangle.x, currentRectangle.y, 3, currentRectangle.height);
                }*/
        }

        @Override
        protected void paintContentBorder(Graphics grphcs, int tabPlacement, int selectedIndex) {
        /*    Graphics2D g2 = (Graphics2D) grphcs.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200));
            Insets insets = getTabAreaInsets(tabPlacement);
            int width = tabPane.getWidth();
            int height = tabPane.getHeight();
            if (tabPlacement == TOP) {
                int tabHeight = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                g2.drawLine(insets.left, tabHeight, width - insets.right - 1, tabHeight);
            } else if (tabPlacement == BOTTOM) {
                int tabHeight = height - calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight);
                g2.drawLine(insets.left, tabHeight, width - insets.right - 1, tabHeight);
            } else if (tabPlacement == LEFT) {
                int tabWidth = calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth);
                g2.drawLine(tabWidth, insets.top, tabWidth, height - insets.bottom - 1);
            } else if (tabPlacement == RIGHT) {
                int tabWidth = width - calculateTabAreaWidth(tabPlacement, runCount, maxTabWidth) - 1;
                g2.drawLine(tabWidth, insets.top, tabWidth, height - insets.bottom - 1);
            }
            g2.dispose();*/
        }

        @Override
        protected void paintFocusIndicator(Graphics grphcs, int i, Rectangle[] rctngls, int i1, Rectangle rctngl, Rectangle rctngl1, boolean bln) {

        }

        @Override
        protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
            //if (tabPane.isOpaque()) {
            //    super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);
           // }
        }
    }
}