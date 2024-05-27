/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.unsl.qualisys.controllers;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author luciano
 */
public interface ArrastrarYSoltarPresentadorLSP {
    void guardarEstado();
    boolean isArbolBienFormado();
    void actualizarArbolGenealogico(QsNodo adoptado, String padreViejo, String padreNuevo);
    void actualizarArbolGenealogico(QsNodo padre);
    void updatePonderValue(QsNodo hijo);
    double getPonderacionBalanceada(int cantHijos);
    void addToDomain(QsNodo hijoCandidato, Point  padreLocation);
    boolean canBeDomain(QsNodo hijoCandidato, QsOperador padreAdoptivo);
    void addOperator(QsOperador q);
    boolean noCicles(QsOperador hijoCandidato, QsOperador padreCandidato);
//    boolean isGoodPonder(QsNodo hijo, String valor,String padre);
//    void setPonderValue(QsNodo hijo, String padre);
    void initData(Map<String, QsVariable> varsNuevas);
    void addPonderacionToNodo(QsNodo nodo, Double valor);
    ArrayList<QsVariable> getListaOrdenadaVariables();
}
