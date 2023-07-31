/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.unsl.qualisys.componentes.nodos;

import java.util.ArrayList;

/**
 *
 * @author luciano.gurruchaga
 */
public interface QsOperacion {
    public double calcularOperacion(ArrayList<Double> dominio);
    public double calcularOperacion(double... dominio);
    public double functionEvaluation();
}
