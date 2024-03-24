/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes.nodos;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsNodo extends JPanel {
    //2664554567
    private String padreID;
    private double valorResultado; 
    private double ponderacion;

    /**
     * Constructor que van a usar las variables/que para el caso son constantes por eso no hay "set"
     * @param resValue 
     */
    public QsNodo(double resValue){
        this.valorResultado = resValue;
        this.padreID = ""; 
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // This one line changes the cursor.
    }
    /**
     * Constructor que van a usar los operadores
     */
    public QsNodo(){
        this.valorResultado = -1d;
        this.padreID ="";
        this.setCursor(new Cursor(Cursor.HAND_CURSOR)); // This one line changes the cursor.
    }

    public String getPadreID() {
        return padreID;
    }

    public void setPadreID(String padreID) {
        this.padreID = padreID;
    }

    public double getResValue() {
        return valorResultado;
    }

    public double getValorResultado() {
        return valorResultado;
    }

    public void setValorResultado(double valorResultado) {
        this.valorResultado = valorResultado;
    }

    public double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(double ponderacion) {
        this.ponderacion = ponderacion;
    }
    
 
}

