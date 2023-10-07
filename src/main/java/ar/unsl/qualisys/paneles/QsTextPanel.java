package ar.unsl.qualisys.paneles;

import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.utils.Item;
import ar.unsl.qualisys.utils.JTextPaneUtils;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class QsTextPanel extends JPanel {

    private static boolean TURN_OFF_LISTENERS = false;

    private JTextPane panelDeTexto;
    private ArrayList<Item> renglones;
    private Item renglonActual;
    private QsFrame parent;

    /**
     * Constructor Panel de Texto - Menu popup
     */
    public QsTextPanel(QsFrame parent) {
        this.setLayout(new BorderLayout());
        this.parent = parent;
        panelDeTexto = new JTextPane() {

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                        <= getParent().getSize().width;
            }

        };
        renglones = new ArrayList<>();
        renglonActual = new Item(0, 0, "1.", ""); // inicializo item  ... Cambiar por CARGAR ARCHHIVO O NUEVO ARCHIVO
        renglones.add(renglonActual);
        panelDeTexto.setText(renglonActual.constructRenglon()); // ANTES DE AGREGAR LISTENERS

        //     this.add(new QsBarraHerramientas(this.parent,null), BorderLayout.NORTH);
        textContent();
        menuPopUp();
        this.setVisible(true);
    }

    public void menuPopUp() {
        JPopupMenu contextual = new JPopupMenu();

        JMenuItem deshacer = new JMenuItem("Deshacer");
        JMenuItem rehacer = new JMenuItem("Rehacer");
        JMenuItem cortar = new JMenuItem("Cortar");
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem pegar = new JMenuItem("Pegar");

        deshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        rehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
        cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));
        copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));

        cortar.addActionListener(new StyledEditorKit.CutAction());
        copiar.addActionListener(new StyledEditorKit.CopyAction());
        pegar.addActionListener(new StyledEditorKit.PasteAction());

        contextual.add(deshacer);
        contextual.add(rehacer);
        contextual.add(cortar);
        contextual.add(copiar);
        contextual.add(pegar);

        panelDeTexto.setComponentPopupMenu(contextual);
    }

    public void textContent() {
        //Scroll
        JScrollPane scroll = new JScrollPane(panelDeTexto);

        //  Font font = new Font("Sans_Serif", Font.PLAIN, 30);
        // panelDeTexto.setFont(font);
        // Crear el documento
        StyledDocument doc = panelDeTexto.getStyledDocument();

        // Crear un estilo
        Style style = panelDeTexto.addStyle("DefaultStyle", null);

        // Configurar la alineación del estilo
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);

        // Añadir el estilo al documento
        doc.setParagraphAttributes(0, doc.getLength(), style, false);

        // Establecer el texto en el JTextPane
        viewNumeracion(true, panelDeTexto, scroll);
        //Wheel scroll
        scroll.setWheelScrollingEnabled(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Agregamos el texto al SctollPanel
        manejarEventosPanelDeTexto();
        this.add(scroll, BorderLayout.CENTER);
    }

    public static void viewNumeracion(boolean numeracion, JTextPane textArea, JScrollPane scroll) {
//        scroll.setRowHeaderView(new TextLineNumber(textArea));  
    }

    /**
     * Reduce la numeracion del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*
     */
    private String quitarNivel(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        String numeracionDecrementada = "";
        for (int i = 0; i < numeracionAnterior.length - 1; i++) { // MENOS UNO 
            numeracionDecrementada += numeracionAnterior[i] + ".";
        }
        return numeracionDecrementada;
    }

    /**
     * Aumenta la numeracion del item anterior. numeracion anterior es *.*.n
     * @return *.*.n+1
     */
    private String aumentarNumeracion(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        int numeracionAumentada = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1]) + 1;
        String numeracion = "";

        // Concateno la numeracion anterior
        for (int i = 0; i < numeracionAnterior.length - 1; i++) {
            numeracion += numeracionAnterior[i] + ".";
        }
        numeracion += numeracionAumentada + ".";
        return numeracion;
    }
    /**
     * decrementa la numeracion del item anterior. numeracion anterior es *.*.n
     * @return *.*.n-1
     */
    private String decrementarNumeracion(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        int numeracionDecrementada = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1]) - 1;
        String numeracion = "";

        // Concateno la numeracion anterior
        for (int i = 0; i < numeracionAnterior.length - 1; i++) {
            numeracion += numeracionAnterior[i] + ".";
        }
        numeracion += numeracionDecrementada + ".";
        return numeracion;
    }
    /**
     * Aumenta el nivel del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*.n.1
     */
    private String aumentarNivel(Item it) {
        //int numeracion = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1 ]) + 1;
        String nuevoNivel = it.getNumeration() + "1.";
        return nuevoNivel;

    }

    private void manejarEventosPanelDeTexto() {
        Document documento = panelDeTexto.getDocument();
        Set<Integer> pressedKeys = new HashSet<>();
        //EVENTOS DE EDICION DE TEXTO . Añado los caret Listeners
        panelDeTexto.addKeyListener(new KeyListener() {
            boolean shiftPressed = false;
            boolean ctrlPressed = false;

            @Override
            public void keyTyped(KeyEvent ke) {
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = true;
                } else if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = true;
                } else if (ctrlPressed && ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    int nivelAc = renglonActual.getNivel();
                    if (nivelAc > 0) {
                        if (TURN_OFF_LISTENERS == false) {
                            TURN_OFF_LISTENERS = true;
                            quitarNivelANumeracion();
                        }
                    }
                } else if (shiftPressed && ke.getKeyCode() == KeyEvent.VK_TAB) {
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        quitarNivelARenglon();
                    }
                } else if (shiftPressed && ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        agregarNivelANumeracion();
                    }
                } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    ke.consume(); // PARA QUE NO ME TOME EL ENTER, lo hago manual
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        agregarNumeracionANivel();
                    }
                } else if (ke.getKeyCode() == KeyEvent.VK_TAB) {
                    ke.consume(); // PARA QUE NO ME TOME EL ENTER, lo hago manual
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        agregarNivelARenglon();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SHIFT) {
                    shiftPressed = false;
                }
                if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = false;
                }
            }

        });
        documento.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println("panelCaretPos = " + panelDeTexto.getCaretPosition());
                System.out.println("Tipo = " + e.getType());
                System.out.println("Longitud = " + e.getLength());
                System.out.println("Offset = " + e.getOffset());
                //System.out.println("INSERTUPDATE  = " + panelDeTexto.getText());
                if (TURN_OFF_LISTENERS == false) {
                    TURN_OFF_LISTENERS = true;
//                    int lineaAnterior = renglonActual.getNumeroDeLinea(); // inicialmente 0
//                    int offsetResultado = e.getOffset() + e.getLength();
//                    int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, offsetResultado);
//                    System.out.println("lineaNueva " + lineaNueva);
//                    System.out.println("lineaAnterior " + lineaAnterior);
                    actualizarEstado(0, 1);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("panelCaretPos = " + panelDeTexto.getCaretPosition());
                System.out.println("Tipo = " + e.getType());
                System.out.println("Longitud = " + e.getLength());
                System.out.println("Offset = " + e.getOffset());
                //    System.out.println("REMOVEUPDATE  = " + panelDeTexto.getText());
                if (TURN_OFF_LISTENERS == false) {
                    TURN_OFF_LISTENERS = true;
//                    int lineaAnterior = renglonActual.getNumeroDeLinea(); // inicialmente 0 
//                    int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, e.getOffset());
                    actualizarEstado(0, 1);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        panelDeTexto.addCaretListener(new CaretListener() {
            //ACTUALIZA, o inicializa el itemActual by caret
            @Override
            public void caretUpdate(CaretEvent e) {
                //System.out.println("panelDeTexto.getCaretPosition() = " + panelDeTexto.getCaretPosition());
                System.out.println(panelDeTexto.getText() + "hola");
                System.out.println(" " + e.getDot() + "ooo" + e.getMark()); // Mark donde empieza la selecion 
                if (renglonActual != null) {
                    int posAnterior = renglonActual.getNumeroDeLinea();
                    int posNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, panelDeTexto.getCaretPosition());
                    if (posAnterior != posNueva) { //cambio de linea
                        if (renglones.size() > posNueva) {
                            renglonActual = renglones.get(posNueva); // actualizo item
                        }
                    }
                }
            }
        });
    }

    /**
     * @param lineaAnterior posision de linea antes de la modificacion
     * @param lineaNueva posision de linea despues de la modificacion
     */
    private void actualizarEstado(int lineaAnterior, int lineaNueva) {
        if (lineaAnterior == lineaNueva) { // Modificamos la linea actual
            actualizarRenglonActual();
        } else {
            actualizarEstructuraDeTexto();
        }
    }

    private boolean isRenglonBienFormado(String renglon) {
        String regex = "^\\t*(\\d+\\.)+\\s.*(\\r\\n|\\r|\\n|^$)?$"; // para la mariconeada de windol! 
        System.out.println("renglon.matches(regex) = " + renglon.matches(regex));
        return renglon.matches(regex);
    }

    private Item armarItem(String renglon, int numeroDeLinea) {
        String[] arregloRenglon = renglon.split(" ");
        String numeracionAndNivel = arregloRenglon[0]; //obtengo la primer parte del renglon
        int nivelItem = numeracionAndNivel.split("\t").length - 1; //cantidad de tabs
        String numeracionItem = numeracionAndNivel.split("\t")[nivelItem];  //numeracion del indice
        String textoItem = renglon.substring(arregloRenglon[0].length() + 1); // obtengo el resto del texto
        System.out.println("textoItem = " + textoItem);
        return new Item(numeroDeLinea, nivelItem, numeracionItem, textoItem);
    }

    private void actualizarRenglonActual() {
        int linea = renglonActual.getNumeroDeLinea();
        String texto = JTextPaneUtils.getTextoDeLineaByCaret(panelDeTexto);
        if (isRenglonBienFormado(texto)) {
            Item itemActualizado = armarItem(texto, linea);
            renglonActual.setCadenaDeTexto(itemActualizado.getCadenaDeTexto());
        } else {
            //escribirlo bien.
            renglonActual.setCadenaDeTexto(texto);
        }
        renglones.add(linea, renglonActual);
        TURN_OFF_LISTENERS = false;
    }

    /*
    private void resaltarIndices(){
        String texto =panelDeTexto.getText();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setBold(attrs, true);
        panelDeTexto.setCharacterAttributes(attrs, true);
        Element root = panelDeTexto.getStyledDocument().getDefaultRootElement();

        for (int i = 0; i < root.getElementCount(); i++) {
            Element line = root.getElement(i);

            // Get the start offset of the line and set bold for the first character
            int lineStart = line.getStartOffset();
            panelDeTexto.getStyledDocument().setCharacterAttributes(lineStart, 1, attrs, true);
        }
    } */
    /**
     * Actualiza toda la estructura del texto convirtiendo a todas las lineas de texto como Items de formato correcto.
     * Nota : Corre en hilo de ejecucion aparte. Asyncrono.
     */
    private void actualizarEstructuraDeTexto() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String[] arregloDeNiveles = new String[100]; // Permitimos una anidacion maximas de 10 niveles ... sobra para la practica 
                String nuevoTexto = "";
                int caretPosition = panelDeTexto.getCaretPosition();
                String texto = panelDeTexto.getText();

                //si la ultima linea es un /n, agrego espacio en blanco
                //if(texto.lastIndexOf("\n") == texto.length()-1){// osea que no me tomo la ultima linea vacia 
                //    texto+= " "; // para que me tome la ultima linea sino confunde con EOF y da problemas
                //} // Parche grande como una casa 
                String[] lineas = texto.split("\n");
                renglones = new ArrayList<>();
                renglonActual = null;
                for (int i = 0; i < lineas.length; i++) {
                    Item renglon = null;
                    if (i == 0) {// i = 0 - Primer Renglon
                        arregloDeNiveles[0] = "1.";
                        if (isRenglonBienFormado(lineas[i])) {
                            renglonActual = new Item(i, 0, "1.", armarItem(lineas[i], 0).getCadenaDeTexto()); // init primer item 
                        } else {
                            renglonActual = new Item(i, 0, "1.", lineas[i]); // init 
                        }
                        nuevoTexto = renglonActual.constructRenglon();
                        renglones.add(renglonActual);
                        continue;// o break 
                    } else if (isRenglonBienFormado(lineas[i])) {
                        renglon = armarItem(lineas[i], i);
                        if (renglon.getNivel() == renglonActual.getNivel()) {
                            renglon.setNumeration(aumentarNumeracion(renglonActual)); // numeracion + 1 
                            arregloDeNiveles[renglon.getNivel()] = renglon.getNumeration();
                        } else if (renglon.getNivel() < renglonActual.getNivel()) {
                            String num = arregloDeNiveles[renglon.getNivel()]; // Obtengo numeracion del corriente nivel.
                            renglon.setNumeration(num);
                            renglon.setNumeration(aumentarNumeracion(renglon));
                            arregloDeNiveles[renglon.getNivel()] = renglon.getNumeration();
                        } else {
                            renglon.setNivel(renglonActual.getNivel() + 1);//aumenta SOLO UN nivel
                            renglon.setNumeration(aumentarNivel(renglonActual));
                            arregloDeNiveles[renglon.getNivel()] = renglon.getNumeration();
                        }
                    } else { // EL renglon está mal formado asique puede venir cualquier cosa, lo ignoro y pongo en el mismo nivel de la corriente numeracion ... 
                        renglon = new Item(i,
                                renglonActual.getNivel(),
                                aumentarNumeracion(renglonActual),
                                lineas[i]);
                    }
                    /*if(!renglon.getCadenaDeTexto().equals("")){ // Limpio renglones en blanco
                    }*/
                    nuevoTexto += "\n" + renglon.constructRenglon();
                    renglonActual = renglon;
                    renglones.add(renglonActual);

                }

                panelDeTexto.setText(nuevoTexto); // actualizo el texto
                System.out.println("");
                try {
                    panelDeTexto.setCaretPosition(caretPosition);
                } catch (IllegalArgumentException IAE) {
                    System.out.println("KUEKUEKUE");
                    System.out.println("caretPosition = " + caretPosition);
                    System.out.println("panelDeTexto.getText().length() = " + panelDeTexto.getText().length());
                    System.out.println("document.getLength() = " + panelDeTexto.getDocument().getLength());
                }

                int pos = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, caretPosition);
                renglonActual = renglones.get(pos);
                //Falta actualizar el caret
//                resaltarIndices();
                TURN_OFF_LISTENERS = false;
            }

        });
    }

    /**
     * Este método se activa con CTRL + ENTER
     */
    private void quitarNivelANumeracion() {
        String texto = panelDeTexto.getText();
        int caret = panelDeTexto.getCaretPosition();
        String preCaret = texto.substring(0, caret);
        String posCaret = texto.substring(caret);
        renglonActual.setNivel(renglonActual.getNivel() - 1); // LE SACO UN NIVEL
        renglonActual.setNumeration(quitarNivel(renglonActual));
        renglonActual.setNumeration(aumentarNumeracion(renglonActual)); 
        renglonActual.setCadenaDeTexto("");
        String agregado = "\n" + renglonActual.constructRenglon() + " ";
        int caretAumentado = caret + agregado.length();
//        System.out.println("texto = " + texto);
//        System.out.println("caret = " + caret);
//        System.out.println("caretAumentado = " + caretAumentado);
//        System.out.println("preCaret = " + preCaret);
//        System.out.println("posCaret = " + posCaret);
//        System.out.println("agregado = " + agregado);
//        System.out.println("preCaret + agregado + posCaret = " + preCaret + agregado + posCaret);
//        this.setTextoConCaret(preCaret + agregado + posCaret, caretAumentado);
        panelDeTexto.setText(preCaret + agregado + posCaret);
        actualizarEstado(0, 1);
        panelDeTexto.setCaretPosition(caretAumentado);
    }
    /**
     * Este método se activa con ENTER
     */
    private void agregarNumeracionANivel() {
        String texto = panelDeTexto.getText();
        int caret = panelDeTexto.getCaretPosition();
        String preCaret = texto.substring(0, caret);
        String posCaret = texto.substring(caret);
        String tabs = "";
        for (int i = 0; i < renglonActual.getNivel(); i++) {
            tabs += "\t";
        }
        String agregado = "\n" + tabs + aumentarNumeracion(renglonActual) + " ";
        int caretAumentado = caret + agregado.length();
//        System.out.println("texto = " + texto);
//        System.out.println("caret = " + caret);
//        System.out.println("caretAumentado = " + caretAumentado);
//        System.out.println("preCaret = " + preCaret);
//        System.out.println("posCaret = " + posCaret);
//        System.out.println("agregado = " + agregado);
//        System.out.println("preCaret + agregado + posCaret = " + preCaret + agregado + posCaret);
//        this.setTextoConCaret(preCaret + agregado + posCaret, caretAumentado);
        panelDeTexto.setText(preCaret + agregado + posCaret);
        actualizarEstado(0, 1);
        panelDeTexto.setCaretPosition(caretAumentado); // Actualizo caret
    }
    /**
     * Este método se activa con SHIFT + ENTER
     */
    private void agregarNivelANumeracion() {
        //renglonActual.getNivel();
        String texto = panelDeTexto.getText();
        int caret = panelDeTexto.getCaretPosition();
        String preCaret = texto.substring(0, caret);
        String posCaret = texto.substring(caret);
        renglonActual.setNivel(renglonActual.getNivel() + 1); // LE AGREGO UN NIVEL
        renglonActual.setNumeration(renglonActual.getNumeration() + "1."); 
        renglonActual.setCadenaDeTexto("");
        String agregado = "\n" + renglonActual.constructRenglon() + " ";
        int caretAumentado = caret + agregado.length();
//        System.out.println("texto = " + texto);
//        System.out.println("caret = " + caret);
//        System.out.println("caretAumentado = " + caretAumentado);
//        System.out.println("preCaret = " + preCaret);
//        System.out.println("posCaret = " + posCaret);
//        System.out.println("agregado = " + agregado);
//        System.out.println("preCaret + agregado + posCaret = " + preCaret + agregado + posCaret);
//        this.setTextoConCaret(preCaret + agregado + posCaret, caretAumentado);
        panelDeTexto.setText(preCaret + agregado + posCaret);
        actualizarEstado(0, 1);
        panelDeTexto.setCaretPosition(caretAumentado); // Actualizo caret
    }
    /**
     * Este Método se activa con SHIFT + TAB
     */
    private void quitarNivelARenglon() {
        if (renglonActual.getNivel() > 0) {
            int inicioRenglon = JTextPaneUtils.getPrincipioDeRow(panelDeTexto);
            int finRenglon = JTextPaneUtils.getFinalDeRow(panelDeTexto);
            String texto = panelDeTexto.getText();
            int caret = inicioRenglon;
            String preCaret = texto.substring(0, caret);
            String posCaret = texto.substring(finRenglon);
            renglonActual.setNivel(renglonActual.getNivel() - 1); // LE SACO UN NIVEL
            renglonActual.setNumeration(quitarNivel(renglonActual));
            renglonActual.setNumeration(decrementarNumeracion(renglonActual));
            String agregado = renglonActual.constructRenglon();
            int caretAumentado = caret + agregado.length();
//            System.out.println("texto = " + texto);
//            System.out.println("caret = " + caret);
//            System.out.println("finRenglon = " + finRenglon);
//            System.out.println("caretAumentado = " + caretAumentado);
//            System.out.println("preCaret = " + preCaret);
//            System.out.println("posCaret = " + posCaret);
//            System.out.println("agregado = " + agregado);
//            System.out.println("preCaret + agregado + posCaret = " + preCaret + agregado + posCaret);
//            this.setTextoConCaret(preCaret + agregado + posCaret, caretAumentado);
           // panelDeTexto.setText(preCaret + agregado + posCaret);
            JTextPaneUtils.setTextoDeLineaByCaret(panelDeTexto, agregado);
            //actualizarEstado(0, 0);
            //TURN_OFF_LISTENERS=true;
            actualizarEstado(0, 1);
            panelDeTexto.setCaretPosition(caretAumentado);//Actualizo caret
        } else {
            TURN_OFF_LISTENERS = false;
        }
    }
    /**
     * Este Método se actia con TAB
     */
    private void agregarNivelARenglon() {
        if (renglonActual.getNumeroDeLinea() > 0) {
            Item renglonPadre = renglones.get(renglonActual.getNumeroDeLinea() - 1);
            if (renglonPadre.getNivel() < renglonActual.getNivel()) {
                TURN_OFF_LISTENERS = false; // mucho muy importante
                return; // para explicacion, no puede tener mas de dos niveles mas adentro que el padre
            }
            int inicioRenglon = JTextPaneUtils.getPrincipioDeRow(panelDeTexto);
            int finRenglon = JTextPaneUtils.getFinalDeRow(panelDeTexto);
            String texto = panelDeTexto.getText();
            int caret = panelDeTexto.getCaretPosition(); // la uso para obtener el fin de linea dsp
            String preCaret = texto.substring(0, inicioRenglon);
            String posCaret = texto.substring(finRenglon);
            renglonActual.setNivel(renglonActual.getNivel() + 1); // LE AGREGO UN NIVEL
            renglonActual.setNumeration(renglonActual.getNumeration() + "1."); // LE SACO UN NIVEL
            String agregado =renglonActual.constructRenglon();
//            System.out.println("texto = " + texto);
//            System.out.println("caret = " + caret);
//            System.out.println("inicioRenglon = " + inicioRenglon);
//            System.out.println("finRenglon = " + finRenglon);
//            System.out.println("preCaret = " + preCaret);
//            System.out.println("posCaret = " + posCaret);
//            System.out.println("agregado = " + agregado);
//            System.out.println("preCaret + agregado + posCaret = " + preCaret + agregado + posCaret);
//            this.setTextoConCaret(preCaret + agregado + posCaret, caretAumentado);
//            panelDeTexto.setText(preCaret + agregado + posCaret);
            JTextPaneUtils.setTextoDeLineaByCaret(panelDeTexto, agregado);
           // actualizarEstado(0, 0);
          //  TURN_OFF_LISTENERS=true;
            actualizarEstado(0, 1);
            panelDeTexto.setCaretPosition(caret);// queda ajustar el caret con nuevo fin de string.
            finRenglon = JTextPaneUtils.getFinalDeRow(panelDeTexto);
            panelDeTexto.setCaretPosition(finRenglon);
        }

    }
    /**
     * Limpia los \r agregados en fin de linea en WINDOWS:(\r\n) UNIX:(\n) como correspondee.
     * @param texto
     * @return 
     */
    private String limpiarWindowsCarryReturn(String texto) {
        texto = texto.replaceAll("\\r", "");
        return texto;
    }
    
    public JTextPane getJTextPanel() {
        return this.panelDeTexto;
    }

    public void setTexto(String texto) {
        TURN_OFF_LISTENERS = true;
        if (texto.contains("\r")) {
            texto = limpiarWindowsCarryReturn(texto);
        }
        this.panelDeTexto.setText(texto);
        actualizarEstado(0, 1);//Le paso lineas distintas para que actualice todo el texto.
    }

    public void setTextoConCaret(String texto, int caret) {
        TURN_OFF_LISTENERS = true;
        this.panelDeTexto.setText(texto);
        this.panelDeTexto.setCaretPosition(caret);
        actualizarEstado(0, 1);//Le paso lineas distintas para que actualice todo el texto.
    }

    public boolean isTextoBienFormado() {
        String[] lineas = this.panelDeTexto.getText().split("\n");
        int i = 0;
        while (isRenglonBienFormado(lineas[i]) && i < lineas.length - 1) {
            i++;
        }
        return i == lineas.length - 1;
    }

    /**
     * Devuelve solo los renglones que son Variables de Preferencia
     *
     * @return
     */
    public ArrayList<Item> getVariables() {
        boolean primero = true;
        Item anterior = null;
        ArrayList<Item> rVariables = new ArrayList<>();
        for (Item renglon : this.renglones) {
            if (primero) {
                anterior = renglon;
                primero = false;
                continue;
            }
            if (anterior.getNivel() >= renglon.getNivel()) { // Si el nivel actual es el mismo que el anterior entonces el nivel anterior es una variable .
                rVariables.add(anterior);
            }//else if(anterior.getNivel() < renglon.getNivel()){// Si el nivel anterior es menor al nivel actual entonces No es variable.
            anterior = renglon;
            //}//else{ // Si el nivel anterior es mayor al nivel actual entonces es una variable
            //}
        }
        rVariables.add(anterior);    //El ultimo nivel es una variable if or if         
        return rVariables;
    }

    public ArrayList<Item> getRenglones() {
        return renglones;
    }

    public void setRenglones(ArrayList<Item> renglones) {
        this.renglones = renglones;
    }

}

//NOTA TENGO QUE HACER EL QUE CUANDMO E BORRE LA MITAD DE LOS NUMERITOS ME CREE LOS NUMERITOS DE NUEVO EN NEGRITA PARA QUE SENO TE O BUSCAR LA MANERA DE NOPPERMITIR ESO 

