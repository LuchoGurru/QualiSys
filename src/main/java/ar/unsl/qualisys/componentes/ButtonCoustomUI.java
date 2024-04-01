/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author luciano
 */
public class ButtonCoustomUI extends JButton{

    public ButtonCoustomUI() {
        this.setIcon(new ImageIcon("/home/luciano/Documentos/Proyectos-Git/QualiSys/src/main/resources/save-25.png"));
        this.setBackground(Color.decode("#D6CE93"));
        this.setForeground(Color.decode("#BB8588"));
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#A3A380"),0,true));
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    } 
    
     @Override
     public void paintComponent(Graphics g){
         super.paintComponent(g);
     }
}
