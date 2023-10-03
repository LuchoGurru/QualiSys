/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes.nodos;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsNodo extends JPanel {
    //2664554567
    private String padreID;
    private float valorResultado; 
    private float ponderacion;

    /**
     * Constructor que van a usar las variables/que para el caso son constantes por eso no hay "set"
     * @param resValue 
     */
    public QsNodo(float resValue){
        this.valorResultado = resValue;
        this.padreID = ""; 
    }
    /**
     * Constructor que van a usar los operadores
     */
    public QsNodo(){
        this.valorResultado = 0f;
        this.padreID ="";
    }

    public String getPadreID() {
        return padreID;
    }

    public void setPadreID(String padreID) {
        this.padreID = padreID;
    }

    public float getResValue() {
        return valorResultado;
    }

    public float getValorResultado() {
        return valorResultado;
    }

    public void setValorResultado(float valorResultado) {
        this.valorResultado = valorResultado;
    }

    public float getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(float ponderacion) {
        this.ponderacion = ponderacion;
    }
    
 
}

