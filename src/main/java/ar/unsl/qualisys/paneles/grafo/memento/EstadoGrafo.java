/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.memento;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.paneles.texto.memento.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luciano.gurruchaga
 */
public class EstadoGrafo {
    
    private Map<String, QsVariable> variables = new HashMap<String, QsVariable>();
    private Map<String, QsOperador> operadores = new HashMap<String, QsOperador>();
    private Map<String, ArrayList<QsNodo>> relPadreHijos = new HashMap<String, ArrayList<QsNodo>>();

    public EstadoGrafo(Map<String, QsVariable> variables ,Map<String, QsOperador> operadores,Map<String, ArrayList<QsNodo>> relPadreHijos) {
        this.variables=variables;
        this.operadores=operadores;
        this.relPadreHijos=relPadreHijos;
    }

    public Map<String, QsVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, QsVariable> variables) {
        this.variables = variables;
    }

    public Map<String, QsOperador> getOperadores() {
        return operadores;
    }

    public void setOperadores(Map<String, QsOperador> operadores) {
        this.operadores = operadores;
    }

    public Map<String, ArrayList<QsNodo>> getRelPadreHijos() {
        return relPadreHijos;
    }

    public void setRelPadreHijos(Map<String, ArrayList<QsNodo>> relPadreHijos) {
        this.relPadreHijos = relPadreHijos;
    }
    
    
}
