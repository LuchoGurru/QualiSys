/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.memento;

import ar.unsl.qualisys.paneles.texto.memento.*;

/**
 *
 * @author luciano.gurruchaga
 */
public class Memento {

    private EstadoGrafo estado;
    
    public Memento(EstadoGrafo estado){
        this.estado=estado;
    }
    
    public EstadoGrafo getEstado(){
        return estado;
    }
}
