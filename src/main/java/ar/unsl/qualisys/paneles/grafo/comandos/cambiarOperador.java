/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.comandos;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author luciano.gurruchaga
 */
public class cambiarOperador implements IComando {
 
    QsDadPanel pizzarra;
    
    public cambiarOperador(QsDadPanel pizzarra){
        this.pizzarra = pizzarra;
        
    }

    public void myOptionPane() {  
        //Icon errorIcon = UIManager.getIcon("OptionPane.errorIcon");  
        Object[] possibilities = {
            "D",
            "D++",
            "D+",
            "D+-",
            "DA",
            "D-+",
            "D-",
            "SQU",
            "D--",
            "A",
            "C--",
            "C-",
            "GEO",
            "C-+",
            "CA",
            "HAR",
            "C+-",
            "C+",
            "C++",
            "C"
        };  
        String simbolo = (String) JOptionPane.showInputDialog(null,  
                "Selecciona el tipoa de operador que desea", "ShowInputDialog",  
                JOptionPane.PLAIN_MESSAGE, null, possibilities, "Numbers");  
        System.out.println("ii = " + simbolo);
        QsOperador opNuevo = pizzarra.getBrother().getOperadorBySymbol().get(simbolo);
        QsOperador opSelec = (QsOperador) pizzarra.getNodoSeleccionado();
        opSelec.setNombre(opNuevo.getNombre());
        opSelec.setSymbol(opNuevo.getSymbol());        
        opSelec.setD(opNuevo.getD());
        opSelec.setR2(opNuevo.getR2());
        opSelec.setR3(opNuevo.getR3());
        opSelec.setR4(opNuevo.getR4());     
        opSelec.setR5(opNuevo.getR5());       
        this.pizzarra.repaint(); // Repaint 
        }  

    @Override
    public void ejecutar() {        
        myOptionPane(); 
    }
    
    
}
