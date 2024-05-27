/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.comandos;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.controllers.PanelGrafoController;
import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author luciano.gurruchaga
 */
public class editarPesoPonderado implements IComando {
         
    QsDadPanel pizzarra;
    
    public editarPesoPonderado(QsDadPanel pizzarra){
        this.pizzarra = pizzarra;
        
    }
     
     /**
     * Actualiza todos las ponderaciones de los hermanos del padre
     * @param padre 

     */
    public Double getPonderValue(QsNodo hijo, String padre,Map<String, ArrayList<QsNodo>> relPadreHijos) {
        // create a dialog Box
        String valor;
        do {
            valor = JOptionPane.showInputDialog(
                    pizzarra,
                    "Selecciona el peso deseado de la relación (recuerda que la suma de los pesos tiene que ser igual a 1).",
                    1d);
        } while (!isGoodPonder(hijo,valor, padre, relPadreHijos));
        return(Double.valueOf(valor));
    }
    
    private boolean isGoodPonder(QsNodo hijo, String valor,String padre, Map<String, ArrayList<QsNodo>> relPadreHijos){
        boolean isGood = true;
        Double ponderValue = 0d;
        try{
            ponderValue = Double.parseDouble(valor);
        }catch(NumberFormatException nex){
            JOptionPane.showMessageDialog(pizzarra,"Debe ingresar un valor real.");
            isGood = false;
        }
        //Control <= a 1 
        if(ponderValue>1){
            JOptionPane.showMessageDialog(pizzarra,"Debe ingresar un valor <= 1.");
            isGood = false;
        }else if(isGood && ponderValue<0){
            JOptionPane.showMessageDialog(pizzarra,"Debe ingresar un valor > 0.");
            isGood = false;            
        }else if(isGood){
            Double ponderacionTotal = ponderValue;
            for(QsNodo n: relPadreHijos.get(padre)){
                if(!n.getName().equals(hijo.getName())){ // No se suma asi mismo
                    ponderacionTotal += n.getPonderacion();
                    if(ponderacionTotal > 1 ){
                        JOptionPane.showMessageDialog(pizzarra,"La suma total de los pesos del dominio de un operador debe ser igual a 1.");
                        isGood = false;
                        break;
                    }
                } 
            }
        }
        return isGood;
    }
    @Override
    public void ejecutar() {
        PanelGrafoController c = PanelGrafoController.getInstance();
        QsNodo nodoSelec = c.getNodoSeleccionado();
        String padre = nodoSelec.getPadreID();
        if(!padre.equals("")){ // Si tenia padre (DEBERIA)
            Double valor = this.getPonderValue(nodoSelec, padre,c.getRelPadreHijos());
            //c.addPonderacionToNodo(nodoSelec,valor); ver q onda la referencia
            nodoSelec.setPonderacion(valor);
        } 
        c.guardarEstado();
        this.pizzarra.repaint(); // Repaint 
    }
    
}
