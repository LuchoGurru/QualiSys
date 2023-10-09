/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIUtils;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

/**
 * Crear una clase para modificar este metodo xD 
 * @author luciano.gurruchaga
 */
public class CustomTableModel extends DefaultTableModel {
    @Override
    public boolean isCellEditable(int row, int col) {
        return col!=0;
    }
} 
