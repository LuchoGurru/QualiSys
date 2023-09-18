/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.comandos;

import ar.unsl.qualisys.paneles.grafo.QsDadPanel;

/**
 *
 * @author luciano.gurruchaga
 */
public class eliminarQsRelacion implements IComando{
     
    QsDadPanel pizzarra;
    
    public eliminarQsRelacion(QsDadPanel pizzarra){
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