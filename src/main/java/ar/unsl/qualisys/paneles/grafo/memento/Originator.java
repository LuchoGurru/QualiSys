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
public class Originator {
    private EstadoGrafo estado;
    
    public void setEstado(EstadoGrafo estado){
        this.estado = estado;
    }
    
    public EstadoGrafo getEstado(){
        return this.estado;
    }
    
    public Memento guardar(){
        return new Memento(estado);
    }
    
    public void restaurar(Memento m){
        this.estado = m.getEstado();
    }
}
