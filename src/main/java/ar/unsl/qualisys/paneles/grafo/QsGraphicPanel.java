/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JPanel;
import ar.unsl.qualisys.componentes.nodos.QsOperator;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.utils.Item;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JScrollPane;

/**
 * Hacer el codigo de 
 * @author luciano
 */
public class QsGraphicPanel extends JPanel {
    private QsFrame parent;
    private QsOperatorsPanel menuOperadores;
    private QsDadPanel DAD;

    /**
     * Creates new form examples
     */
    public QsGraphicPanel(QsFrame parent) {
        this.parent=parent;
        this.setLayout(new BorderLayout());
        this.setName("GUIPanel");
        //this.add(new QsBarraHerramientas(this.parent,null), BorderLayout.NORTH);

        //AGREGO LOS 2 PANELES
        this.menuOperadores = new QsOperatorsPanel(this);
        this.menuOperadores.setPreferredSize(new Dimension(150,300));

        this.DAD = new QsDadPanel(this,menuOperadores);
        JScrollPane scroll = new JScrollPane(DAD);
        
     //   scroll.setWheelScrollingEnabled(true);
      //  scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
      //  scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.DAD.setBackground(Color.WHITE); 
        this.add(menuOperadores,BorderLayout.WEST);
        this.add(scroll ,BorderLayout.CENTER); 
    }
    
    public void setVariables(ArrayList<Item> renglones){
        int desplazamiento = 5;
        //TODO  AGREGAR VARIABLES 
        HashMap mapaDeVariables = new HashMap<String, QsVariable>();
        for(Item item : renglones){
            QsVariable var = new QsVariable(DAD, 50, desplazamiento, item.getNumeration(),item.getCadenaDeTexto());
            mapaDeVariables.put(var.getName(), var);
            desplazamiento+= 35;
        }
        this.DAD.setVariables(mapaDeVariables);
    }
    
    public void agregarOperadorANulLayout(Point punto,QsOperator modelOperador){ 
        QsOperator nuevoOperador = new QsOperator(DAD,
                DAD.cantOperadores,
                modelOperador.getNombre(),
                modelOperador.getSymbol(),
                modelOperador.getD(),
                modelOperador.getR2(),
                modelOperador.getR3(),
                modelOperador.getR4(),
                modelOperador.getR5());
        QsDadPanel.cantOperadores ++;
        int margin = menuOperadores.getWidth(); 
        nuevoOperador.setBounds((int)punto.getX() - margin -20,(int)punto.getY()-20,51,51);
        nuevoOperador.setBackground(Color.white);
        DAD.addOperator(nuevoOperador);
        DAD.repaint();
    }

    public QsDadPanel getDAD() {
        return DAD;
    }

    public void setDAD(QsDadPanel DAD) {
        this.DAD = DAD;
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
