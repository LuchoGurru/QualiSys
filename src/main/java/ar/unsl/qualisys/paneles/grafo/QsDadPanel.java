/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable; 
import ar.unsl.qualisys.controllers.PanelGrafoController;
import ar.unsl.qualisys.paneles.grafo.memento.CaretTaker;
import ar.unsl.qualisys.paneles.grafo.memento.EstadoGrafo;
import ar.unsl.qualisys.paneles.grafo.memento.Originator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Lo que voy a hacer en este componente sera lo siguiente : 
 * 
 * lo primero que tengo que hacer es tener algo lindo para arrastrar en mi panel
 * lo segundo es francia
 * lo tercero es importar variables de tipo texto al panel como un rectangulo en principio 
 * lo cuarto te puse 
 * lo quinto es permitir asignaciones de variables a operandos y de operandos a variables 
 * restringir operandos con operandos 
 * y variables con variables 
 * 
 * IDEA : poner un cursor focus y un textito de ayuda para que sepa lo que tenga que hacer es decir armar la funcion o circuito 
 * 
 * Por un lado podemos tener una estructura de datos para lograr almacenar las variables de entrada, es decir 
 * las variables numeradas añadidas desde el editor de texto con una expresion regular reestringica por <1.1>_<categorizadas> 
 * y inicializadas en este panel.
 * 
 * por otro lado me gustaría que se dibujaran flechas euclideas con maximo de 1 dobles de 90°
 * 
 * Esto esta siendo ideado aproximadamente desde fines de octube o principios de noviembre del 2022/ 16 / 9  ... fue en septiembre
 * 
 * Hasta la fecha sigo teniendo 24 años, Padre de Joaquina que tiene 2 años y 2 meses y 4 dias ... junto a muchas personas y sobretodo mi familia 
 * ancestral contemporanea y progenitora n't .
 * 
 * Una vez terminado esto rendiré ingles para festejar ! 
 *  Ya tengo 25 y medio y todavía no me recibo 
 * 17/09/23
 * Que sean 26
 * 
 * 
 * @author luciano.gurruchaga
 */
public class QsDadPanel extends JPanel implements DrawAndDropView { //implements LspTreeCotrols { ControlesArbolLSP

    private QsGraphicPanel GUIpadre;
    private QsOperatorsPanel GUIBrother;
    private QsDadPanel esta;
    
    private Dimension area; //indicates area taken up by graphics 
    

    /**
     * Constructores 
     */
     public QsDadPanel(QsGraphicPanel parent,QsOperatorsPanel brother){    
        this.setLayout(null);
        this.esta = this;
        this.GUIpadre = parent;
        this.GUIBrother = brother;
        this.area = new Dimension(); 
        this.setBackground(Color.decode("#FFFFFF")); 
    }

    public QsOperatorsPanel getGUIBrother() {
        return GUIBrother;
    }

    public QsGraphicPanel getGUIPadre() {
        return GUIpadre;
    } 
   
    @Override
    public void paintComponent(Graphics g) {
        super.removeAll();
        super.paintComponent(g); 
        System.out.println("Paint: ");
        PanelGrafoController c = PanelGrafoController.getInstance();
        area.width=0;
        area.height=0;
        paintVariables(g, c.getVariables());
        paintOperadores(g,c.getOperadores());
        paintLineas(g,c.getOperadores(),c.getRelPadreHijos()); 
        this.revalidate(); //Let the scroll pane know to update itself and its scrollbars.

    }
    @Override
    public void paintVariables(Graphics g, Map<String, QsVariable> variables){
        boolean changed = false;
        for(QsVariable qv: variables.values()){
           // System.out.println("qv.getName() = " + qv.getName());
            this.add(qv);
            // Listo, ahora a ajustar el SCROLL panel
            //   this.scrollRectToVisible(qo.getBounds());
            int this_width = (qv.getX() + qv.getWidth());
            int this_height = (qv.getY() + qv.getHeight());
            //Update client's preferred size because the area taken up by the graphics has gotten larger or smaller (if cleared).
            if (this_width > area.width) {
                area.width = this_width; 
                this.setPreferredSize(new Dimension(this_width, area.height));
            }
            if (this_height > area.height) {
                area.height = this_height; 
                this.setPreferredSize(new Dimension(area.width, this_height));
            }
//            if (changed) {
//                              this.setPreferredSize(new Dimension(this_width, this_height));           
//        }

        }
    }
    @Override
    public void paintOperadores(Graphics g, Map<String, QsOperador> operadores){
        for(QsOperador qo: operadores.values()){
            boolean changed = false;
           // System.out.println("qo.getName() = " + qo.getName());
            this.add(qo);
            // Listo, ahora a ajustar el SCROLL panel
            //   this.scrollRectToVisible(qo.getBounds());
            int this_width = (qo.getX() + qo.getWidth());
            int this_height = (qo.getY() + qo.getHeight());
            //Update client's preferred size because the area taken up by the graphics has gotten larger or smaller (if cleared).
            if (this_width > area.width) {
                area.width = this_width; 
                this.setPreferredSize(new Dimension(this_width, area.height));
            }
            if (this_height > area.height) {
                area.height = this_height; 
                this.setPreferredSize(new Dimension(area.width, this_height));
            }
        } 
        
    }
    /**
     * Recorre, anidacion de arreglos.
     * @param g 
     */
    @Override
    public void paintLineas(Graphics g,Map<String, QsOperador> operadores,Map<String, ArrayList<QsNodo>> relPadreHijos){
        ArrayList<ArrayList<QsNodo>> hijos = new ArrayList<ArrayList<QsNodo>>(relPadreHijos.values());
        for(int j=0;j < hijos.size(); j++){
            ArrayList<QsNodo> hermanos = hijos.get(j);
            Point padreLocation=null;
            for(int i=0;i< hermanos.size();i++){
                QsNodo h = hermanos.get(i);
                if(padreLocation == null){
                    padreLocation = obtenerPadreLocation(h,operadores);
                }
                //Pinto (x,y) to (x',y')
                if(h.getClass() == QsVariable.class)
                    g.drawLine(h.getLocation().x + 80,h.getLocation().y+15, padreLocation.x,padreLocation.y+25);
                else
                    g.drawLine(h.getLocation().x + 50,h.getLocation().y+25, padreLocation.x,padreLocation.y+25);
                 // Dibujo peso, ponderaje de la relacion
                g.drawString(""+String.format("%.2f", h.getPonderacion()),
                        h.getLocation().x + (padreLocation.x - h.getLocation().x)/2 + 20 ,
                        h.getLocation().y + (padreLocation.y - h.getLocation().y)/2 + 20) ;
                //Flecha
                g.fillOval(padreLocation.x-8, padreLocation.y+20, 10, 10);
            }
        }
    }
    
    private Point obtenerPadreLocation(QsNodo h,Map<String, QsOperador> operadores){
        Point padreLocation = null;
        padreLocation = operadores.get(h.getPadreID()).getLocation();   
        return padreLocation;
    }
     /**
      * Chequea si colisiona con algun componente del panel operador o variable
      * @param colisionador
      * @return 
      */
    @Override
    public boolean isColision(QsNodo colisionador){
        boolean colision = false;
        for(int i=0;i<this.getComponents().length;i++){
            Component c = this.getComponent(i);
            System.out.println("Componente c is Nodo ?" + (c instanceof QsNodo));
            System.out.println("Componente null is Integer ?" + (null instanceof Integer));
            System.out.println("Componente c is DaD ?" + (c instanceof QsDadPanel));
            if(colisionador.getBounds().intersects(c.getBounds()) && !colisionador.getName().equals(c.getName())){
                //System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);
                System.out.println("colision = true");
                colision = true;
                break;
            }else{
                System.out.println("colision = false");
            }
        }
        return colision;
    }
     
    @Override
    public QsOperador getOperatorAtPoint(Point p){
        Component c = this.getComponentAt(p);
        if(c instanceof QsOperador){
            return (QsOperador) c;
        }
        return null;
    }      
    @Override 
    public void repintar(){
        this.repaint();
    } 
}
