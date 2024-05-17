/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys;

import LSP.QsInstancia;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author luciano
 */
public class Sesion {
    
    private static Sesion sesion;
    //Qualisys
    private String texto;
    private Map<String, QsVariable> variables;
    private Map<String, QsOperador> operadores;
    private Map<String, ArrayList<QsNodo>> relPadreHijos;
    private ArrayList<QsInstancia> instancias; 
    private ArrayList<QsVariable> listaVariables;
    
    private Sesion(String texto, Map<String, QsVariable> variables, Map<String, QsOperador> operadores, Map<String, ArrayList<QsNodo>> relPadreHijos, ArrayList<QsInstancia> instancias) {
        this.texto = texto;
        this.variables = variables;
        this.operadores = operadores;
        this.relPadreHijos = relPadreHijos;
        this.instancias = instancias;
    }
    
    public static Sesion getInstance(){
        if(sesion == null){
            sesion = new Sesion(null,null,null, null, null);
        }
        return sesion;
    }
    
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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

    public ArrayList<QsInstancia> getInstancias() {
        return instancias;
    }

    public void setInstancias(ArrayList<QsInstancia> instancias) {
        this.instancias = instancias;
    }
    
    
    
}
