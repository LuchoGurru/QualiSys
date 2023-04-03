package com.mycompany.qualisys;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class QualityOperator extends JPanel{
    private String nombre;
    private String[] dominio; // Recibe un maximo de 5 argumentos ... seran strings, seran float ?
    private float rango;

    public QualityOperator(){
        this.setBackground(Color.red);
        this.setVisible(true);
    }

    public QualityOperator(String nombre, String[] dom){
        
        try{
            this.nombre = nombre;
            this.dominio = dom;
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Can not operate, to many arguments");
        }
    }

    public float evaluationFunction(){
        rango = 1.0f; // dom[i] 0<i<5 evaluation by function
        return rango;
    }


}
