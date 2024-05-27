/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
 import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author luciano
 */
public interface DrawAndDropView {
    /**
     * Se cargan las relaciones y con las variables reinicializadas
     * @param variables 
     */
    //void initData(Map<String, QsVariable> varsNuevas);
    void paintVariables(Graphics g,Map<String, QsVariable> variables);
    void paintOperadores(Graphics g,Map<String, QsOperador> operadores);
    void paintLineas(Graphics g,Map<String, QsOperador> operadores,Map<String, ArrayList<QsNodo>> relPadreHijos);
    /**
     * Retorna null si no es un QsOperador
     */
    QsOperador getOperatorAtPoint(Point p);
    boolean isColision(QsNodo colisionador);    
    
    
    //Metodos Enmascarados de JPanel.
    void repintar(); // llama a repaint
    
}
