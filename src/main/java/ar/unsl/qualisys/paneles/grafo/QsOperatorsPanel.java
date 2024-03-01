package ar.unsl.qualisys.paneles.grafo;

import ar.unsl.qualisys.componentes.nodos.QsOperador;
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
import javax.swing.JScrollPane;
/**
 *
 * @author luciano
 */
public class QsOperatorsPanel extends JPanel {
    
    private QsGraphicPanel GUIParent;
    private JPanel esta;
    private HashMap<String,QsOperador> operadorBySymbol;
    private QsOperador operador1;
    private QsOperador operador2;
    private QsOperador operador3;
    private QsOperador operador4;
    private QsOperador operador5;
    private QsOperador operador6;
    private QsOperador operador7;
    private QsOperador operador8;
    private QsOperador operador9;
    private QsOperador operador10;
    private QsOperador operador11;
    private QsOperador operador12;
    private QsOperador operador13;
    private QsOperador operador14;
    private QsOperador operador15;
    private QsOperador operador16;
    private QsOperador operador17;
    private QsOperador operador18;
    private QsOperador operador19;
    private QsOperador operador20;
    
    public QsOperatorsPanel(QsGraphicPanel parent) {
        operadorBySymbol=new HashMap<>();
        
        operador1= new QsOperador(this,101,"Disjunction","D",1d,1.7e+103d,1.7e+103d,1.7e+103d,1.7e+103d);
        operador1.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador1.getSymbol(),operador1);
        operador2= new QsOperador(this,102,"Strong QD (+)","D++",0.9375d,20.630d,24.300d,27.110d,30.090d);
        operador2.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador2.getSymbol(),operador2);
        operador3= new QsOperador(this,103,"Strong QD","D+",0.8750d,9.521d,11.095d,12.270d,13.235d);
        operador3.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador3.getSymbol(),operador3);
        operador4= new QsOperador(this,104,"Strong QD (-)","D+-",0.8125d,5.802d,6.675d,7.316d,7.819d);
        operador4.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador4.getSymbol(),operador4);
        operador5= new QsOperador(this,105,"Medium QD","DA",0.7500d,3.929d,4.450d,4.825d,5.111d);
        operador5.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador5.getSymbol(),operador5);
        operador6= new QsOperador(this,106,"Weak QD (+)","D-+",0.6875d,2.792d,3.101d,3.318d,3.479d);
        operador6.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador6.getSymbol(),operador6);
        operador7= new QsOperador(this,107,"Weak QD","D-",0.6250d,2.018d,2.187d,2.302d,2.384d);        
        operador7.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador7.getSymbol(),operador7);
        operador8= new QsOperador(this,108,"Square Mean","SQU",0.6232d,2.000d,2.00d,2.00d,2.00d);        
        operador8.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador8.getSymbol(),operador8);
        operador9= new QsOperador(this,109,"Weak QD (-)","D--",0.5625d,1.449d,1.519d,1.565d,1.596d);        
        operador9.setBackground(Color.MAGENTA);
        operadorBySymbol.put(operador9.getSymbol(),operador9);
        operador10= new QsOperador(this,110,"Arithmetic Mean","A",0.5d,1.00d,1.00d,1.00d,1.00d);        
        operador10.setBackground(Color.PINK);
        operadorBySymbol.put(operador10.getSymbol(),operador10);
        operador11= new QsOperador(this,111,"Weak QC (-)","C--",0.4375d,0.619d,0.573d,0.546d,0.526d);        
        operador11.setBackground(Color.GREEN);
        operadorBySymbol.put(operador11.getSymbol(),operador11);
        operador12= new QsOperador(this,112,"Weak QC","C-",0.3750d,0.261d,0.192d,0.153d,0.129d);                
        operador12.setBackground(Color.GREEN);
        operadorBySymbol.put(operador12.getSymbol(),operador12);
        operador13= new QsOperador(this,113,"Geometric Mean","GEO",0.3333d,0d,-0.067d,-0.101d,-0.121d);      
        operador13.setBackground(Color.GREEN);
        operadorBySymbol.put(operador13.getSymbol(),operador13);
        operador14= new QsOperador(this,114,"Weak QC (+)","C-+",0.3125d,-0.148d,-0.208d,-0.235d,-0.251d);        
        operador14.setBackground(Color.GREEN);
        operadorBySymbol.put(operador14.getSymbol(),operador14);
        operador15= new QsOperador(this,115,"Medium QC","CA",0.2500d,-0.720d,-0.732d,-0.721d,-0.707d);        
        operador15.setBackground(Color.GREEN);
        operadorBySymbol.put(operador15.getSymbol(),operador15);
        operador16= new QsOperador(this,116,"Harmonic Mean","HAR",0.2274d,-1.000d,-1.000d,-1.000d,-1.000d);        
        operador16.setBackground(Color.GREEN);
        operadorBySymbol.put(operador16.getSymbol(),operador16);
        operador17= new QsOperador(this,117,"Strong QC (-)","C+-",0.1875d,-1.655d,-1.550d,-1.455d,-1.380d);        
        operador17.setBackground(Color.GREEN);
        operadorBySymbol.put(operador17.getSymbol(),operador17);
        operador18= new QsOperador(this,118,"Strong QC","C+",0.1250d,-3.510d,-3.114d,-2.823d,-2.606d);        
        operador18.setBackground(Color.GREEN);
        operadorBySymbol.put(operador18.getSymbol(),operador18);
        operador19= new QsOperador(this,119,"Stronc QC (+)","C++",0.0625d,-9.060d,-7.639d,-6.689d,-6.013d);        
        operador19.setBackground(Color.GREEN);
        operadorBySymbol.put(operador19.getSymbol(),operador19);
        operador20= new QsOperador(this,120,"Conjunction","C",0.0000d,5.0e-324d,5.0e-324d,5.0e-324d,5.0e-324d);
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
    
    public void dibujarOperadorEn(Point punto,QsOperador operador){
        
        if(GUIParent.getComponentAt(punto) != null && GUIParent.getComponentAt(punto).getClass()==JScrollPane.class){
            QsGraphicPanel panelGrafico = (QsGraphicPanel) this.getParent();
            panelGrafico.agregarOperadorANulLayout(punto,operador);
        } 
    }

    public HashMap<String, QsOperador> getOperadorBySymbol() {
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
