/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIComponents.Laminas;

import GUIComponents.GUINodos.QualyOperator;
import GUIComponents.GUINodos.QualyVariable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
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
 * 
 * 
 * 
 * 
 * @author luciano.gurruchaga
 */
public class DragAndDropVariablesAndOperandsPanel extends JPanel    {
    // La estructura de datos conveniente es : 
    // Una lista por niveles ... jajaja
    // Una lista vinculada 
    // Un rebalse directo o funcionHash de coso, funcion ideal ... ver bibliografía, para mapear cada categoría en una lista vinculada, es decir rebalse abierto vinculado 
    
    /**
     * -----IMPLEMENTACION-----
     */ 
    //private QualyVariable[] rebalseArray; // recibe por parametro la lista de variables sin gategorizar por el momento 
    //Las insertaré dentro del panel a unos pixeles del borde para luego dejarlas fijas inamobibles y aplicarles solo un operador 
    
    /// Entonces cuando arrastre el muse released del operador y lo suelte en DAD agrego un operador nuevito a esta lista y los vamos pintanding 
    // Despues vamos a ir agarrando y editando atributos de los operadores para ir pintando 
    
    
    private ArrayList<QualyOperator> operadores = new ArrayList<>();
    private ArrayList<QualyVariable> variables = new ArrayList<>();
    private Map<String, ArrayList<JPanel>> relPadreHijos = new HashMap<String, ArrayList<JPanel>>();
    
    // Los operadores que manejamos van a poder recibir de rango un valor y podran ser asignados como dominio de otro operador 
    public static int cantOperadores = 0;
    
    /**
     * Pintado del Drag And Drop component: 
     *      Parametros del constructor : 
     *          Lista de Variables,
     *          Lista de Operadores (inicialmente vacia)
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
       // pintarVariables();
        pintarOperadores(g);
       // repaint();
    }
    
    private void pintarOperadores(Graphics g){
        for(QualyOperator qo: this.operadores){
            this.add(qo);
            dibujarLineas(g,qo);
          //  System.out.println("reealyy nigga");
        }
    }
    
    public void dibujarLineas(Graphics g,QualyOperator padre){
        ArrayList<JPanel> hijos = padre.getDominio();
        Point padreLocation = padre.getLocation();
        for(JPanel son : hijos){
            Point sonLocation = son.getLocation();
            g.drawLine(sonLocation.x, sonLocation.y,padreLocation.x , padreLocation.y);
        }
    }
    /**
     * Drawablecomponent ... Variables - Operadores y Flechas 
     * 
     * Lista de Variables 
     * Lista de operadores con Dominio () Flechas  
     */
    /**
     * Constructores 
     */
     public DragAndDropVariablesAndOperandsPanel(){    
        this.setLayout(null);
     }    
     /**
      * Chequea si colisiona con algun componente del panel operador o variable
      * @param colisionador
      * @return 
      */
    public boolean isColision(QualyOperator colisionador){
        boolean legal = false;
        for(int i=0;i<this.getComponents().length;i++){
            Component c = this.getComponent(i);
            if(colisionador.getBounds().intersects(c.getBounds()) && !colisionador.getName().equals(c.getName())){
                colisionador.setBackground(Color.blue);
                //System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);
                legal = true;
            }
        }
        return legal;
    }
    
    private QualyOperator getOperatorByLocation(Point loc){
        Component c = this.getComponentAt(loc);
        if(this.getComponentAt(loc).getClass() == QualyOperator.class){
            return (QualyOperator) c;
        }System.out.println("Algo paso con la clase del get at component !! o es una variable");
        return null;
    }
    
    /**
     * 
     * @param qo
     * @param posX
     * @param posY 
     */
    public void canBeDomain(JPanel hijo, Point padreLoc){
        //if(padreOperator != null){
           // if(padreOperator.getDominio().size() < 5){
                
           // }else{
             //   System.out.println("No podes ser dominio por que el operador esta lleno");
           // }
            
            
        //}else{
          //  System.out.println("No podes ser dominio por que no es un operador o es una variable");
       // }
        
    }
    public void addOperator(QualyOperator q){
        this.operadores.add(q);
    }
    /*
    public void addHijoToRel(JPanel hijo, Point padreLoc){
        QualyOperator padreOperator = getOperatorByLocation(padreLoc);
        if(canBeDomain(hijo, padreLoc)){
            ArrayList<JPanel> sons = relPadreHijos.get(padreOperator.getName());
            if(sons.size() < 5){
                sons.add(hijo);
                relPadreHijos.put(padreOperator.getName(), sons);
            }else{
                System.out.println("YA tiene 5 hijos");
            }
        }
    }*/
}







