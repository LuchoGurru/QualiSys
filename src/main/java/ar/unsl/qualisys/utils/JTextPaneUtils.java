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
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;

public class JTextPaneUtils {
    /**
     * EJEMPLO DE COMO USAR LIST ITEM }
     * 
     *         private static int itemCount = 1;
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Item List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);

        // Create a styled document
        StyledDocument doc = textPane.getStyledDocument();

        // Create a paragraph style for the items
        Style itemStyle = doc.addStyle("ItemStyle", null);
        StyleConstants.setLeftIndent(itemStyle, 20);
        StyleConstants.setFirstLineIndent(itemStyle, -20);

        // Add items to the document
        addItem(doc, "Item 1", itemStyle);
        addItem(doc, "Item 2", itemStyle);
        addItem(doc, "Item 3", itemStyle);

        frame.getContentPane().add(new JScrollPane(textPane));
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    private static void addItem(StyledDocument doc, String itemText,Style style ) {
        try {
            // Append the item text with the item number
            String itemNumber = "(" + itemCount + ")";
            String itemLine = itemNumber + " " + itemText;
            doc.insertString(doc.getLength(), itemLine + "\n", null);

            // Apply the item style to the item number
            doc.setCharacterAttributes(doc.getLength() - itemLine.length(), itemNumber.length(), doc.getStyle("ItemStyle"), true);

            itemCount++;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
     */
    /** EJEMPLO PARA USARLO 
     * .addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int lineNumber = JTextPaneUtils.getLineNumber(textPane);
                int columnNumber = JTextPaneUtils.getColumnNumber(textPane);

                System.out.println("Línea: " + lineNumber);
                System.out.println("Columna: " + columnNumber);
            }
        });
     * @param textComponent
     * @return 
     */
    public static int getLineNumberByCaret(JTextComponent textComponent) {
        int caretPosition = textComponent.getCaretPosition();
        
        int lineNumber = (caretPosition == 0) ? 1 : 0;
            System.out.println("caretPosition = " + caretPosition);

        try {
            int offset = caretPosition;
            System.out.println("Offset = " + offset);
            while (offset > 0) {
                
                offset = Utilities.getRowStart(textComponent, offset) - 1; // Claro le va sacando el desplazamiento linea a linea de forma descendente,
                //                                                            aumentando el contador hasta que llega al -1 cuando es el comienzo del archivo
                System.out.println("offset = " + offset);
                lineNumber++;
                System.out.println("lineNumber = " + lineNumber);
            }
        } catch (javax.swing.text.BadLocationException ex) {
            ex.printStackTrace();
        }

        return lineNumber;
    }

    public static int getColumnNumberByCaret(JTextComponent textComponent) {
        int caretPosition = textComponent.getCaretPosition();
        int columnNumber = (caretPosition == 0) ? 1 : 0;
            System.out.println("caretPosition = " + caretPosition);

        try {
            int offset = Utilities.getRowStart(textComponent, caretPosition);
            System.out.println("offset = " + offset);
            columnNumber = caretPosition - offset + 1;
        } catch (javax.swing.text.BadLocationException ex) {
            ex.printStackTrace();
        }

        return columnNumber;
    }
        /**
         * Obtiene La línea contando desde 0. es decir la Primer Linea es la línea 0.
         * @param offset desplazamiento desde el comienzo del archivo.
         * @return 
         */
        public static int getIndexLineNumberByOffset(JTextComponent textComponent,int pos) {
        int lineIndex=0;
        try {
            int offset = pos;
            while (offset > 0) { //Significa que todavía no es el principio del archivo, Le voy quitando el length de la línea hasta el principio.
                offset = Utilities.getRowStart(textComponent, offset) - 1;
                 // Claro le va sacando el desplazamiento linea a linea de forma descendente,
                 //aumentando el contador hasta que llega al -1 cuando es el comienzo del archivo
                 //El menos 1 es para que se vaya al ultimo elemento de la fila anterior, sino siempre me va a dar la misma fila                                                      
                
                lineIndex = offset>0 ? lineIndex + 1 : lineIndex+0   ;
                
            }
        } catch (javax.swing.text.BadLocationException ex) {
            ex.printStackTrace();
        }

        return lineIndex;
    }
    
         /**
         * Obtiene La línea contando desde 0. es decir la Primer Linea es la línea 0.
         * @param offset desplazamiento desde el comienzo del archivo.
         * @return 
         */
        public static String getTextoDeLineaByOffset(JTextComponent textComponent,int pos) {
        String textoDeLinea="";
        try {
            int offset = pos;
            int inicioRow = 0;
            int finRow = 0;
            inicioRow = Utilities.getRowStart(textComponent, offset) ;
            finRow = Utilities.getRowEnd(textComponent, offset) ; 
            offset = finRow - inicioRow;
            textoDeLinea = textComponent.getText(inicioRow, offset);
         } catch (javax.swing.text.BadLocationException ex) {
            ex.printStackTrace();
        }

        return textoDeLinea;
    }    
        
        
    public static void insertStringAtTheEnd(JTextPane textPane,String textAtTheEnd){
        try {
            // Obtener el documento del JTextPane
            StyledDocument doc = textPane.getStyledDocument();
            
            // Obtener la longitud del documento
            int length = doc.getLength();

            // Insertar texto al final del documento
            doc.insertString(length, textAtTheEnd, null);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
    
        public static void insertStringAtRow(JTextPane textPane,String textAtTheBeginingOfTheRow){
        try {
            // Obtener el documento del JTextPane
            Document doc = textPane.getDocument();//StyledDocument();
            
            // Obtener la longitud del documento
            int length = doc.getLength();
            int lineaEnLaCualQuedaElCursor = Utilities.getRowStart(textPane, textPane.getCaretPosition());
            
            
            System.out.println("getRowStart = " + lineaEnLaCualQuedaElCursor);
            System.out.println("textAtTheBeginingOfTheRow = " + textAtTheBeginingOfTheRow);
            System.out.println("textPane.getCaretPosition() = " + textPane.getCaretPosition());
            // Insertar texto al final del documento
            doc.insertString(lineaEnLaCualQuedaElCursor, textAtTheBeginingOfTheRow, null);

        } catch (BadLocationException e) {
             System.out.println(e.offsetRequested());
          //  e.printStackTrace();
        }
    }
    
/*
    
    private static ArrayList<Integer> nestedList = new ArrayList<>();
    
    private static int count = 0;
    
    public static int getCount(){
        return count ;
    }
    
    public static void setCount(int count){
        JTextPaneUtils.count = count;
    }
    
    public static void addLevel(int previousLevel){
        nestedList.add(previousLevel);
        setCount(0);
    }
    
    /*public static void setLocalCount(int localCount){
        JTextPaneUtils.localCount = localCount;
    }
    
    public static int getLocalCount(){
        return localCount ;
    }
    
        
*/
    
}