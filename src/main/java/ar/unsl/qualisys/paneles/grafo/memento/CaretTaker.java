/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo.memento;

import ar.unsl.qualisys.paneles.texto.memento.*;
import java.util.ArrayList;

/**
 *
 * @author luciano.gurruchaga
 */
public class CaretTaker {
     private ArrayList<Memento> mementos = new ArrayList<>();
    private int currentIndex = -1; // Current position in mementos list
    
    public void addMemento(Memento memento) {
        // When a new memento is added, we discard any future redos
        mementos.subList(currentIndex + 1, mementos.size()).clear();
        
        mementos.add(memento);
        currentIndex++;
    }
    
    public Memento getMemento(int index) {
        return mementos.get(index);
    }
    
    public Memento undo() {
        if (currentIndex > 0) {
            currentIndex--;
            return getMemento(currentIndex);
        } else {
            return null;
        }
    }
    
    public Memento redo() {
        if (currentIndex < mementos.size() - 1) {
            currentIndex++;
            return getMemento(currentIndex);
        } else {
            return null;
        }
    }
    
}
