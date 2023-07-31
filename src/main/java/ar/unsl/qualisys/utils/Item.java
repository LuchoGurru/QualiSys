/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.utils;

import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author luciano.gurruchaga
 */
public class Item {

    private String numeration;// 1.
    private int nivel;// t
    private String cadenaDeTexto; // Name
   // private Set<Item> arregloDeItems; // Hijos 
    //private int numeroDeColumna;
    private int numeroDeLinea;
    
    
    
    public Item(int numeroDeLinea, int nivel,String numeration, String cadenaDeTexto, Set<Item> arregloDeItems) {
        this.numeroDeLinea = numeroDeLinea;
        this.cadenaDeTexto = cadenaDeTexto;
        this.numeration = numeration;
        this.nivel = nivel;
        //this.arregloDeItems = arregloDeItems;
    }

    public String getNumeration() {
        return numeration;
    }

    public void setNumeration(String numeration) {
        this.numeration = numeration;
    }
  
    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNumeroDeLinea() {
        return numeroDeLinea;
    }

    public void setnumeroDeLinea(int numeroDeLinea) {
        this.numeroDeLinea = numeroDeLinea;
    }

    public String getCadenaDeTexto() {
        return cadenaDeTexto;
    }

    public void setCadenaDeTexto(String cadenaDeTexto) {
        this.cadenaDeTexto = cadenaDeTexto;
    }
    
    public String constructRenglon(){
        
        String identacion = "";
        
        for (int i =0;i<this.getNivel();i++){
            
            identacion += "\t";
            
        }
        return identacion + this.getNumeration() + " " +this.getCadenaDeTexto();
        
    }
    
    public void deleteRenglon(){
        
    }
    
    public void retrocederNivel(){
        
    }
    
    
}
