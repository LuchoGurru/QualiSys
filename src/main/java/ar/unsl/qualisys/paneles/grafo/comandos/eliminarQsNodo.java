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
public class eliminarQsNodo implements IComando {
 
    QsDadPanel pizzarra;
    
    public eliminarQsNodo(QsDadPanel pizzarra){
        this.pizzarra = pizzarra;
        
    }
    @Override
    public void ejecutar() {   
        QsOperador opSelec = (QsOperador) this.pizzarra.getNodoSeleccionado();
        String padreViejo = opSelec.getPadreID();
      /*  if(!padreViejo.equals("")){ // Si tenia padre
            ArrayList<QsNodo> hermanos = this.pizzarra.getRelPadreHijos().get(padreViejo);
            hermanos.remove(opSelec); // jeje
            this.pizzarra.actualizarArbolGenealogico(opSelec, padreViejo, "");

        }
        
        String clave = opSelec.getName();
        ArrayList<QsNodo> hijos = this.pizzarra.getRelPadreHijos().get(clave);
        for(QsNodo h : hijos){ // Limpio padreID de hijos
            h.setPadreID("");
        }
        this.pizzarra.getRelPadreHijos().remove(clave); // Lo borro en la relacion de padre
        this.pizzarra.getOperadores().remove(clave); //Lo borro en la lista de operadores
        
        this.pizzarra.setNodoSeleccionado(null); //Limpio el operador seleccionado
        this.pizzarra.repaint(); // Repaint 

        */
        this.pizzarra.actualizarArbolGenealogico(opSelec, padreViejo, "");
        this.pizzarra.repaint(); // Repaint 
    }
    
    
}
