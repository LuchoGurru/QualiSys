/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ar.unsl.qualisys.paneles.texto;

import javax.swing.JTextPane;

/**
 *
 * @author luciano
 */
public interface TextEditorView {
    void actualizarEstructuraDeTexto();
    void setTURN_OFF_LISTENERS(boolean TURN_OFF_LISTENERS);
    boolean isTURN_OFF_LISTENERS();
    JTextPane getPanelDeTexto();
    void setPanelDeTexto(JTextPane panelDeTexto);
}
