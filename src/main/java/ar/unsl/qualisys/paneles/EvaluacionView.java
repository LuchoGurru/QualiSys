/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.unsl.qualisys.paneles;

import ar.unsl.qualisys.componentes.nodos.QsOperador;
import javax.swing.JTable;


/**
 *
 * @author luciano
 */
public interface EvaluacionView {
    void repintar(); // llama a repaint
    void mostrarError(String motivo);
    void crearDialog(JTable table,QsOperador op);
}
