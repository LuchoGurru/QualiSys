package javaswingdev.swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javaswingdev.swing.table.Table;
import javax.swing.JPanel;

public class RoundPanel extends JPanel {

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
        this.add(new Table());
        repaint();
    }

    private int round;

    public RoundPanel() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Area area = new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), round, round));
        g2.setColor(getBackground());
        g2.fill(area);
        area.subtract(new Area(new Rectangle2D.Double(1, 1, getWidth(), getHeight() - 3)));
        g2.setPaint(new GradientPaint(0, 0, Color.RED, getWidth(), 0, Color.BLUE));
        g2.fill(area);
        g2.dispose();
        super.paintComponent(g);
    }
}
