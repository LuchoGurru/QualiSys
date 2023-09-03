/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.utils;

import ar.unsl.qualisys.paneles.QsDadPanel;

/**
 *
 * @author luciano.gurruchaga
 */
public class editarPesoPonderado implements IComando {
         
    QsDadPanel pizzarra;
    
    public editarPesoPonderado(QsDadPanel pizzarra){
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
