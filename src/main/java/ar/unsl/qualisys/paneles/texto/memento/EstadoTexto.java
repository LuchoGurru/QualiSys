/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.texto.memento;

/**
 *
 * @author luciano.gurruchaga
 */
public class EstadoTexto {
    private String texto;
    private int pos;
    
    public EstadoTexto(String texto, int pos){
        this.texto=texto;
        this.pos=pos;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
    
    
}
