/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.utils;

import ar.unsl.qualisys.paneles.DragAndDropVariablesAndOperandsPanel;

/**
 *
 * @author luciano.gurruchaga
 */
public class eliminarQsRelacion implements IComando{
     
    DragAndDropVariablesAndOperandsPanel pizzarra;
    
    public eliminarQsRelacion(DragAndDropVariablesAndOperandsPanel pizzarra){
        this.pizzarra = pizzarra;
        
    }
    @Override
    public void ejecutar() {
        System.out.println("this.pizzarra.getOperadores() = " + this.pizzarra.getOperadores().values());
        this.pizzarra.getOperadores().get(this.pizzarra.getOperadorSeleccionado().getName());
        this.pizzarra.getOperadores().get(this.pizzarra.getOperadorSeleccionado().getName());
        System.out.println("this.pizzarra.getOperadores() = " + this.pizzarra.getOperadores().values());
        this.pizzarra.setOperadorSeleccionado(null);
        this.pizzarra.repaint();
    }
    
    
}
