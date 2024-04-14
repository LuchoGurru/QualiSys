/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.memento;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luciano.gurruchaga
 */
public class EstadoGrafo {
    
    private Map<String, QsVariable> variables;
    private Map<String, QsOperador> operadores;
    private Map<String, ArrayList<QsNodo>> relPadreHijos;

    public EstadoGrafo(Map<String, QsVariable> variables, Map<String, QsOperador> operadores,Map<String, ArrayList<QsNodo>> relPadreHijos) {
        this.variables = factoryVaiables(variables);
        this.operadores = factoryOperadores(operadores);
        this.relPadreHijos = factoryRelPadreHijos(this.variables,this.operadores,relPadreHijos); 
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
    
    public Map<String, QsVariable> factoryVaiables(Map<String, QsVariable> variables){
        HashMap<String, QsVariable> m = new HashMap<String, QsVariable>();
        ArrayList<String> varKeys = new ArrayList<>(variables.keySet());
        for(String k : varKeys){
            QsVariable varVieja = variables.get(k);

                    QsVariable hijoClonado = new QsVariable(
                        varVieja.getGUIParent(),
                        varVieja.getBounds().x,
                        varVieja.getBounds().y,
                        varVieja.getName(),
                        varVieja.getDescripcion(),
                        varVieja.getOrden(),
                        varVieja.getPonderacion()
                    );
            m.put(k,hijoClonado);
        }
        return m;
    }
    
    public Map<String, QsOperador> factoryOperadores(Map<String, QsOperador> operadores){
        HashMap<String, QsOperador> m = new HashMap<String, QsOperador>();
        ArrayList<String> opKeys = new ArrayList<>(operadores.keySet());
        for(String k : opKeys){
            QsOperador opViejo = operadores.get(k);

                    QsOperador hijoClonado = new QsOperador(
                        opViejo.getGUIParent(),
                        opViejo.getBounds().x,
                        opViejo.getBounds().y,
                        opViejo.getBounds().width,
                        opViejo.getBounds().height,
                        opViejo.getName(),
                        opViejo.getNombre(),
                        opViejo.getSymbol(),
                        opViejo.getD(),
                        opViejo.getR2(),
                        opViejo.getR3(),
                        opViejo.getR4(),
                        opViejo.getR5(),
                        opViejo.getPonderacion()
                    );
            
            m.put(k,hijoClonado);
        }
        return m;
    }
    
    public Map<String,ArrayList<QsNodo>> factoryRelPadreHijos(Map<String, QsVariable> variables, Map<String, QsOperador> operadores, Map<String,ArrayList<QsNodo>> relPadreHijos){
        HashMap m = new HashMap<String,ArrayList<QsNodo>>();
        //this.variables && this.operadores 
        //tengo que usar estas dos referencias
        
        
        
        ArrayList<String> relPHKeys = new ArrayList<>(relPadreHijos.keySet());
        for(String k : relPHKeys){ // llaves, se mantienen.
            ArrayList<QsNodo> hijos = new ArrayList<>();
            for(QsNodo h : relPadreHijos.get(k)){ // ojo
                if(h instanceof QsVariable){
                    QsVariable hija = variables.get(h.getName());
                    hija.setPadreID(k);
                    hijos.add(hija); 
                }else{//nodo
                    QsOperador hijo = operadores.get(h.getName()); 
                    hijo.setPadreID(k);
                    hijos.add(hijo);
                }
            }
            m.put(k,hijos);
        }
        return m;
    }
    
    
}
