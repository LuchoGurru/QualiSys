package com.mycompany.qualisys;

public class QualityOperator {
    private String nombre;
    private String[] dominio; // Recibe un maximo de 5 argumentos ... seran strings, seran float ?
    private float rango;



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
