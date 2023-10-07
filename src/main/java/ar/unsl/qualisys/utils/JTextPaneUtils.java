/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.utils;

/**
 *
 * @author luciano.gurruchaga
 */
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
/**
 * PROVEE METODOS PARA MANIPULAR el editor de texto
 * @author luciano.gurruchaga
 */
public class JTextPaneUtils {
     
    /**
     * Obtiene La línea contando desde 0. es decir la Primer Linea es la línea 0.
     * @param offset desplazamiento desde el comienzo del archivo.
     * @return 
     */
    public static int getIndexLineNumberByOffset(JTextComponent textComponent,int pos) {
        int lineIndex=0;
        try { 
            DefaultStyledDocument doc = (DefaultStyledDocument) textComponent.getDocument();
            lineIndex = doc.getDefaultRootElement().getElementIndex(pos);
            System.out.println("Line Index: " + lineIndex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lineIndex;
    }

    /**
     * Debuelve el texto en la linea donde esta el cursor
     */
    public static String getTextoDeLineaByCaret(JTextPane textPane) {
        String texto="";
        try {
            int inicioRow = 0;
            int finRow = 0; 
            inicioRow = Utilities.getRowStart(textPane, textPane.getCaretPosition()) ;
            finRow = Utilities.getRowEnd(textPane, textPane.getCaretPosition()) ; 
            texto = textPane.getText().substring(inicioRow, finRow);
         } catch (javax.swing.text.BadLocationException ex) {
            System.out.println(ex.offsetRequested());
            ex.printStackTrace();
        } 
        return texto;
    }   
    
    /**
     * Escribe el texto en la linea donde esta el cursor
     */
    public static void setTextoDeLineaByCaret(JTextPane textPane,String texto) {
        try {
            int inicioRow = 0;
            int finRow = 0; 
            inicioRow = Utilities.getRowStart(textPane, textPane.getCaretPosition()) ;
            finRow = Utilities.getRowEnd(textPane, textPane.getCaretPosition()) ; 
            textPane.select(inicioRow, finRow);
            textPane.replaceSelection(texto); 
          //  int linea = Utilities.getRowStart(textPane, textPane.getCaretPosition());
          //  textPane.getDocument().insertString(linea, texto, null); // inserto la linea en el documento 
         } catch (javax.swing.text.BadLocationException ex) {
            ex.printStackTrace();
        } 
    } 
    /**
     * Get comienzo de linea by caret
     * @param textPane
     * @param textAtTheBeginingOfTheRow 
     */
    public static int getPrincipioDeRow(JTextPane textPane){
        int lineaEnLaCualQuedaElCursor=0;
        try {
            Document doc = textPane.getDocument();
            lineaEnLaCualQuedaElCursor = Utilities.getRowStart(textPane, textPane.getCaretPosition());
        } catch (BadLocationException e) {
             System.out.println(e.offsetRequested());
          //  e.printStackTrace();
        }
        return lineaEnLaCualQuedaElCursor;
    }
        /**
     * Get comienzo de linea by caret
     * @param textPane
     * @param textAtTheBeginingOfTheRow 
     */
    public static int getFinalDeRow(JTextPane textPane){
        int lineaEnLaCualQuedaElCursor=0;
        try {
            Document doc = textPane.getDocument();
            lineaEnLaCualQuedaElCursor = Utilities.getRowEnd(textPane, textPane.getCaretPosition());
        } catch (BadLocationException e) {
             System.out.println(e.offsetRequested());
          //  e.printStackTrace();
        }
        return lineaEnLaCualQuedaElCursor;
    }
}