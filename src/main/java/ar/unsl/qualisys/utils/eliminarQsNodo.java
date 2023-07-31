/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.utils;

import ar.unsl.qualisys.componentes.nodos.QualyOperator;
import ar.unsl.qualisys.paneles.DragAndDropVariablesAndOperandsPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author luciano.gurruchaga
 */
public class eliminarQsNodo implements IComando {
 
    DragAndDropVariablesAndOperandsPanel pizzarra;
    
    public eliminarQsNodo(DragAndDropVariablesAndOperandsPanel pizzarra){
        this.pizzarra = pizzarra;
        
    }
    @Override
    public void ejecutar() {
        System.out.println("this.pizzarra.getOperadores() = " + this.pizzarra.getOperadores().values());
        this.pizzarra.getOperadores().remove(this.pizzarra.getOperadorSeleccionado().getName());
        System.out.println("this.pizzarra.getOperadores() = " + this.pizzarra.getOperadores().values());
        this.pizzarra.setOperadorSeleccionado(null);
        this.pizzarra.repaint();
    }
    
    
}
