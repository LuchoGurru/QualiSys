/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LSP;

import java.util.ArrayList;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsInstancia {
    String nombre;
    ArrayList<Double> valores;

    public QsInstancia(String nombre, ArrayList<Double> valores) {
        this.nombre = nombre;
        this.valores = valores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Double> getValores() {
        return valores;
    }

    public void setValores(ArrayList<Double> valores) {
        this.valores = valores;
    }
    
    
    
    
}
