package ar.unsl.qualisys.paneles.texto;

import ar.unsl.qualisys.controllers.PanelTextoController;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.texto.memento.CaretTaker;
import ar.unsl.qualisys.paneles.texto.memento.EstadoTexto;
import ar.unsl.qualisys.paneles.texto.memento.Originator;
import ar.unsl.qualisys.utils.Item;
import ar.unsl.qualisys.utils.JTextPaneUtils;
import javax.swing.*;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class QsTextPanel extends JPanel implements TextEditorView{

    private static boolean TURN_OFF_LISTENERS = false;

    private JPanel esta;
    private JTextPane panelDeTexto;

    private Originator originator;
    private CaretTaker caretTaker;
    private int cantidadPalabras;
    private ArrayList<Item> renglones;
    private Item renglonActual;
    private QsFrame parent;
    PanelTextoController textoController;

    
    
    
    /**
     * Constructor Panel de Texto - Menu popup
     */
    public QsTextPanel(QsFrame parent, PanelTextoController control) {
        this.textoController = control;
        this.textoController.setvista(this);
        this.setLayout(new BorderLayout());
        this.parent = parent;
        esta=this;
        panelDeTexto = new JTextPane() {

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                        <= getParent().getSize().width;
            }

        };
        panelDeTexto.setBackground(Color.decode("#EFEBCE"));
        renglones = new ArrayList<>();
        renglonActual = new Item(0, 0, "1.", ""); // inicializo item  ... Cambiar por CARGAR ARCHHIVO O NUEVO ARCHIVO
        renglones.add(renglonActual);
        panelDeTexto.setText(renglonActual.constructRenglon()); // ANTES DE AGREGAR LISTENERS
        originator = new Originator();
        caretTaker = new CaretTaker();
        cantidadPalabras = 0;
        EstadoTexto nuevoEstado = new EstadoTexto(panelDeTexto.getText(), panelDeTexto.getCaretPosition());
        originator.setEstado(nuevoEstado);
        caretTaker.addMemento(originator.guardar());
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
        JScrollPane scroll = new JScrollPane(panelDeTexto);
        // Crear el documento
        StyledDocument doc = panelDeTexto.getStyledDocument();
        // Crear un estilo
        Style style = panelDeTexto.addStyle("DefaultStyle", null);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
        // Añadir el estilo al documento
        doc.setParagraphAttributes(0, doc.getLength(), style, false);
        // Establecer el texto en el JTextPane
        //viewNumeracion(true, panelDeTexto, scroll);
        //Wheel scroll
        scroll.setWheelScrollingEnabled(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //Agregamos el texto al SctollPanel
        manejarEventosPanelDeTexto();
        this.add(scroll, BorderLayout.CENTER);
    }

//    public static void viewNumeracion(boolean numeracion, JTextPane textArea, JScrollPane scroll) {
//        scroll.setRowHeaderView(new TextLineNumber(textArea));  
//    }
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
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        EstadoTexto nuevoEstado = new EstadoTexto(panelDeTexto.getText(), panelDeTexto.getCaretPosition());
                        originator.setEstado(nuevoEstado);
                        caretTaker.addMemento(originator.guardar());
                        textoController.quitarNivelANumeracion(panelDeTexto, renglonActual);
                    }
                } else if (shiftPressed && ke.getKeyCode() == KeyEvent.VK_TAB) {
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        EstadoTexto nuevoEstado = new EstadoTexto(panelDeTexto.getText(), panelDeTexto.getCaretPosition());
                        originator.setEstado(nuevoEstado);
                        caretTaker.addMemento(originator.guardar());
                        textoController.quitarNivelARenglon(panelDeTexto, renglonActual);
                    }
                } else if (shiftPressed && ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        EstadoTexto nuevoEstado = new EstadoTexto(panelDeTexto.getText(), panelDeTexto.getCaretPosition());
                        originator.setEstado(nuevoEstado);
                        caretTaker.addMemento(originator.guardar());
                        textoController.agregarNivelANumeracion(panelDeTexto, renglonActual);
                    }
                } else if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    ke.consume(); // PARA QUE NO ME TOME EL ENTER, lo hago manual
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        EstadoTexto nuevoEstado = new EstadoTexto(panelDeTexto.getText(), panelDeTexto.getCaretPosition());
                        originator.setEstado(nuevoEstado);
                        caretTaker.addMemento(originator.guardar());
                        textoController.agregarNumeracionANivel(panelDeTexto, renglonActual);
                    }
                } else if (ke.getKeyCode() == KeyEvent.VK_TAB) {
                    ke.consume(); // PARA QUE NO ME TOME EL ENTER, lo hago manual
                    if (TURN_OFF_LISTENERS == false) {
                        TURN_OFF_LISTENERS = true;
                        EstadoTexto nuevoEstado = new EstadoTexto(panelDeTexto.getText(), panelDeTexto.getCaretPosition());
                        originator.setEstado(nuevoEstado);
                        caretTaker.addMemento(originator.guardar());
                        textoController.agregarNivelARenglon(panelDeTexto, renglonActual, renglones);
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
                    System.out.println("CAREEEEEEEEEEEEEEEEEEEEEEEEEET =  MEMENTOO");
                    String texto = panelDeTexto.getText();
                    int cantPalabrasActual = textoController.contarPalabras(panelDeTexto.getText());

                //   if (cantPalabrasActual != cantidadPalabras) {
                    //    cantidadPalabras = cantPalabrasActual;
                        EstadoTexto nuevoEstado = new EstadoTexto(texto, panelDeTexto.getCaretPosition());
                        originator.setEstado(nuevoEstado);
                        caretTaker.addMemento(originator.guardar());
                   // }
                    int lineaAnterior = renglonActual.getNumeroDeLinea(); // inicialmente 0
                    int offsetResultado = e.getOffset() + e.getLength();
                    int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, offsetResultado);
//                    System.out.println("lineaNueva " + lineaNueva);
//                    System.out.println("lineaAnterior " + lineaAnterior);
                    if (lineaAnterior != lineaNueva) { // Modificamos la linea actual
                        actualizarEstructuraDeTexto();
                    } else {
                        TURN_OFF_LISTENERS = false;
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println("HOAAA");
                if (TURN_OFF_LISTENERS == false) {
                    TURN_OFF_LISTENERS = true;
                    System.out.println("CAREEEEEEEEEEEEEEEEEEEEEEEEEET =  MEMENTOO");
                    String texto = panelDeTexto.getText();
                    EstadoTexto nuevoEstado = new EstadoTexto(texto, panelDeTexto.getCaretPosition());
                    originator.setEstado(nuevoEstado);
                    caretTaker.addMemento(originator.guardar());
                    int lineaAnterior = renglonActual.getNumeroDeLinea(); // inicialmente 0 
                    int offsetResultado = e.getOffset() + e.getLength();
                    int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, offsetResultado);
                    if (lineaAnterior != lineaNueva) { // Modificamos la linea actual
                        actualizarEstructuraDeTexto();
                    } else {
                        TURN_OFF_LISTENERS = false;
                    }
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
     * Actualiza toda la estructura del texto convirtiendo a todas las lineas de
     * texto como Items de formato correcto. Nota : Corre en hilo de ejecucion
     * aparte. Asyncrono.
     */
    @Override
    public void actualizarEstructuraDeTexto() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String[] arregloDeNiveles = new String[100]; // Permitimos una anidacion maximas de 10 niveles .cien. sobra para la practica 
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
                        if (textoController.isRenglonBienFormado(lineas[i])) {
                            renglonActual = new Item(i, 0, "1.", textoController.armarItem(lineas[i], 0).getCadenaDeTexto()); // init primer item 
                        } else {
                            renglonActual = new Item(i, 0, "1.", lineas[i]); // init 
                        }
                        nuevoTexto = renglonActual.constructRenglon();
                        renglones.add(renglonActual);
                        continue;// o break 
                    } else if (textoController.isRenglonBienFormado(lineas[i])) {
                        renglon = textoController.armarItem(lineas[i], i);
                        if (renglon.getNivel() == renglonActual.getNivel()) {
                            renglon.setNumeration(textoController.aumentarNumeracion(renglonActual)); // numeracion + 1 
                            arregloDeNiveles[renglon.getNivel()] = renglon.getNumeration();
                        } else if (renglon.getNivel() < renglonActual.getNivel()) {
                            String num = arregloDeNiveles[renglon.getNivel()]; // Obtengo numeracion del corriente nivel.
                            renglon.setNumeration(num);
                            renglon.setNumeration(textoController.aumentarNumeracion(renglon));
                            arregloDeNiveles[renglon.getNivel()] = renglon.getNumeration();
                        } else {
                            renglon.setNivel(renglonActual.getNivel() + 1);//aumenta SOLO UN nivel
                            renglon.setNumeration(textoController.aumentarNivel(renglonActual));
                            arregloDeNiveles[renglon.getNivel()] = renglon.getNumeration();
                        }
                    } else { // EL renglon está mal formado asique puede venir cualquier cosa, lo ignoro y pongo en el mismo nivel de la corriente numeracion ... 
                        renglon = new Item(i,
                                renglonActual.getNivel(),
                                textoController.aumentarNumeracion(renglonActual),
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
 
    public JTextPane getJTextPanel() {
        return this.panelDeTexto;
    }
 

    public ArrayList<Item> getRenglones() {
        return renglones;
    }

    public void setRenglones(ArrayList<Item> renglones) {
        this.renglones = renglones;
    }

    public Originator getOriginator() {
        return originator;
    }

    public void setOriginator(Originator originator) {
        this.originator = originator;
    }

    public CaretTaker getCaretTaker() {
        return caretTaker;
    }

    public void setCaretTaker(CaretTaker caretTaker) {
        this.caretTaker = caretTaker;
    }
    
    @Override
    public boolean isTURN_OFF_LISTENERS() {
        return TURN_OFF_LISTENERS;
    }
    @Override
    public void setTURN_OFF_LISTENERS(boolean TURN_OFF_LISTENERS) {
        QsTextPanel.TURN_OFF_LISTENERS = TURN_OFF_LISTENERS;
    }
    @Override
    public JTextPane getPanelDeTexto() {
        return panelDeTexto;
    }
    @Override
    public void setPanelDeTexto(JTextPane panelDeTexto) {
        this.panelDeTexto = panelDeTexto;
    }
    
}



//NOTA TENGO QUE HACER EL QUE CUANDMO E BORRE LA MITAD DE LOS NUMERITOS ME CREE LOS NUMERITOS DE NUEVO EN NEGRITA PARA QUE SENO TE O BUSCAR LA MANERA DE NOPPERMITIR ESO 

