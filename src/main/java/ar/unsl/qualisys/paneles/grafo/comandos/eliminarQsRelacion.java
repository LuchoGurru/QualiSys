/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.comandos;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import java.util.ArrayList;

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
        QsNodo nodoSelec = this.pizzarra.getNodoSeleccionado();
        String padre = nodoSelec.getPadreID();
        if(!padre.equals("")){ // Si tenia padre (DEBERIA)
            ArrayList<QsNodo> hermanos = this.pizzarra.getRelPadreHijos().get(padre);
            nodoSelec.setPadreID("");
            hermanos.remove(nodoSelec); // jeje
        }
        this.pizzarra.repaint(); // Repaint 
    }
    
    
}
