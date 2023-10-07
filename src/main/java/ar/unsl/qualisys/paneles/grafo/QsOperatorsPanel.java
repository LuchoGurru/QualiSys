package ar.unsl.qualisys.paneles.grafo;

import ar.unsl.qualisys.componentes.nodos.QsOperator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author luciano
 */
public class QsOperatorsPanel extends JPanel {
    
    private QsGraphicPanel GUIParent;
    private JPanel esta;
    private HashMap<String,QsOperator> operadorBySymbol;
    private QsOperator operador1;
    private QsOperator operador2;
    private QsOperator operador3;
    private QsOperator operador4;
    private QsOperator operador5;
    private QsOperator operador6;
    private QsOperator operador7;
    private QsOperator operador8;
    private QsOperator operador9;
    private QsOperator operador10;
    private QsOperator operador11;
    private QsOperator operador12;
    private QsOperator operador13;
    private QsOperator operador14;
    private QsOperator operador15;
    private QsOperator operador16;
    private QsOperator operador17;
    private QsOperator operador18;
    private QsOperator operador19;
    private QsOperator operador20;
    
    public QsOperatorsPanel(QsGraphicPanel parent) {
        operadorBySymbol=new HashMap<>();
        
        operador1= new QsOperator(this,101,"Disjunction","D",1f,1700d,1700d,1700d,1700d);
        operador1.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador1.getSymbol(),operador1);
        operador2= new QsOperator(this,102,"Strong QD (+)","D++",0.9375f,1700d,1700d,1700d,1700d);
        operador2.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador2.getSymbol(),operador2);
        operador3= new QsOperator(this,103,"Strong QD","D+",0.8750f,1700d,1700d,1700d,1700d);
        operador3.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador3.getSymbol(),operador3);
        operador4= new QsOperator(this,104,"Strong QD (-)","D+-",0.8125f,1700d,1700d,1700d,1700d);
        operador4.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador4.getSymbol(),operador4);
        operador5= new QsOperator(this,105,"Medium QD","DA",0.7500f,1700d,1700d,1700d,1700d);
        operador5.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador5.getSymbol(),operador5);
        operador6= new QsOperator(this,106,"Weak QD (+)","D-+",0.6875f,1700d,1700d,1700d,1700d);
        operador6.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador6.getSymbol(),operador6);
        operador7= new QsOperator(this,107,"Weak QD","D-",0.6250f,1700d,1700d,1700d,1700d);        
        operador7.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador7.getSymbol(),operador7);
        operador8= new QsOperator(this,108,"Square Mean","SQU",0.6232f,1700d,1700d,1700d,1700d);        
        operador8.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador8.getSymbol(),operador8);
        operador9= new QsOperator(this,109,"Weak QD (-)","D--",0.5625f,1700d,1700d,1700d,1700d);        
        operador9.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador9.getSymbol(),operador9);
        operador10= new QsOperator(this,110,"Arithmetic Mean","A",0.5f,1700d,1700d,1700d,1700d);        
        operador10.setBackground(Color.PINK);
        operadorBySymbol.put(operador10.getSymbol(),operador10);
        operador11= new QsOperator(this,111,"Weak QC (-)","C--",0.4375f,1700d,1700d,1700d,1700d);        
        operador11.setBackground(Color.GREEN);
        operadorBySymbol.put(operador11.getSymbol(),operador11);
        operador12= new QsOperator(this,112,"Weak QC","C-",0.3750f,1700d,1700d,1700d,1700d);                
        operador12.setBackground(Color.GREEN);
        operadorBySymbol.put(operador12.getSymbol(),operador12);
        operador13= new QsOperator(this,113,"Geometric Mean","GEO",0.3333f,1700d,1700d,1700d,1700d);      
        operador13.setBackground(Color.GREEN);
        operadorBySymbol.put(operador13.getSymbol(),operador13);
        operador14= new QsOperator(this,114,"Weak QC (+)","C-+",0.3125f,1700d,1700d,1700d,1700d);        
        operador14.setBackground(Color.GREEN);
        operadorBySymbol.put(operador14.getSymbol(),operador14);
        operador15= new QsOperator(this,115,"Medium QC","CA",0.2500f,1700d,1700d,1700d,1700d);        
        operador15.setBackground(Color.GREEN);
        operadorBySymbol.put(operador15.getSymbol(),operador15);
        operador16= new QsOperator(this,116,"Harmonic Mean","HAR",0.2274f,1700d,1700d,1700d,1700d);        
        operador16.setBackground(Color.GREEN);
        operadorBySymbol.put(operador16.getSymbol(),operador16);
        operador17= new QsOperator(this,117,"Strong QC (-)","C+-",0.1875f,1700d,1700d,1700d,1700d);        
        operador17.setBackground(Color.GREEN);
        operadorBySymbol.put(operador17.getSymbol(),operador17);
        operador18= new QsOperator(this,118,"Strong QC","C+",0.1250f,1700d,1700d,1700d,1700d);        
        operador18.setBackground(Color.GREEN);
        operadorBySymbol.put(operador18.getSymbol(),operador18);
        operador19= new QsOperator(this,119,"Stronc QC (+)","C++",0.0625f,1700d,1700d,1700d,1700d);        
        operador19.setBackground(Color.GREEN);
        operadorBySymbol.put(operador19.getSymbol(),operador19);
        operador20= new QsOperator(this,120,"Conjunction","C",0.0000f,1700d,1700d,1700d,1700d);
        operador20.setBackground(Color.GREEN);
        operadorBySymbol.put(operador20.getSymbol(),operador20);
        
        // operador12= new QsOperator(this,106,"Weak QD (+)","D-+",0.6875f,1700d,1700d,1700d,1700d));
        
//        operador13.setBackground(Color.red);
//        operador14.setBackground(Color.GRAY);
//        operador15.setBackground(Color.GREEN);
//        operador16.setBackground(Color.YELLOW);
//        operador17.setBackground(Color.WHITE);
//        operador18.setBackground(Color.PINK);
        this.add(new JLabel("Diyunción/Conjunción"));
        this.add(operador1);
        this.add(operador20);
        this.add(operador2);
        this.add(operador19);
        this.add(operador3);
        this.add(operador18);
        this.add(operador4);
        this.add(operador17);
        this.add(operador5);
        this.add(operador16);
        this.add(operador6);
        this.add(operador15);
        this.add(operador7);
        this.add(operador14);
        this.add(operador8);
        this.add(operador13);
        this.add(operador9);
        this.add(operador12);
        this.add(operador10); 
        this.add(operador11);
        esta = this;
        GUIParent=parent;
    }
    
    public void dibujarOperadorEn(Point punto,QsOperator operador){
        QsGraphicPanel panelGrafico = (QsGraphicPanel) this.getParent();
        panelGrafico.agregarOperadorANulLayout(punto,operador);
    }

    public HashMap<String, QsOperator> getOperadorBySymbol() {
        return operadorBySymbol;
    }
 
    public QsGraphicPanel getGUIpadre() {
        return this.GUIParent;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");
    }// </editor-fold>//GEN-END:initComponents

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
