package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.utils.Item;
import ar.unsl.qualisys.utils.JTextPaneUtils;
import static ar.unsl.qualisys.utils.JTextPaneUtils.insertStringAtTheEnd;
//import ar.unsl.qualisys.utils.TextLineNumber;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
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

    JPanel panelMenu = new JPanel();
    JPanel panelEstado = new JPanel();

    JPanel panel1;

    JMenuBar barra = new JMenuBar();

    JToolBar menuHerramientas = new JToolBar();

    JButton nuevo;
    JButton abrir;
    JButton guardar;

    JButton deshacer;
    JButton rehacer;

    JButton color;
    JSpinner tam;
    JComboBox fuente;
    JButton centrado;
    String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    private JTextPane panelDeTexto = new JTextPane();
    private ArrayList<Item> renglones;
    private Item itemActual;
    // POPUPMENU
    JPopupMenu contextual = new JPopupMenu();

    private static boolean TURN_OFF_LISTENERS = false;
    public QsTextPanel() {
        this.setLayout(new BorderLayout());
        //   barraDeMenu();
        barraDeHerramientas();
        textContent();
        menuPopUp();
        //barraDeEstado();
        this.setVisible(true);
    }

    public void barraDeMenu() {
        //JMenus
        JMenu archivo = new JMenu("Archivo");
        JMenu edicion = new JMenu("Edicion");
        JMenu herramientas = new JMenu("Herramientas");
        JMenu ayuda = new JMenu("Ayuda");

        //Se agregan los menus a la barra
        barra.add(archivo);
        barra.add(edicion);
        barra.add(herramientas);
        barra.add(ayuda);

        //JMenuItems: Archivo
        JMenuItem abrirArchivo = new JMenuItem("Abrir");
        JMenuItem nuevoArchivo = new JMenuItem("Nuevo");
        JMenuItem guardarArchivo = new JMenuItem("Guardar");
        JMenuItem exportar = new JMenuItem("Exportar");
        JMenuItem salir = new JMenuItem("Salir");

        // Atajos del teclado
        abrirArchivo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        //Agregamos los JMenuItems a archivo
        archivo.add(abrirArchivo);
        archivo.add(nuevoArchivo);
        archivo.add(guardarArchivo);
        archivo.add(exportar);
        archivo.add(salir);

        //JMnuItems Editar
        JMenuItem buscarEdicion = new JMenuItem("Buscar");
        JMenuItem deshacerEdicion = new JMenuItem("Deshacer");
        JMenuItem rehacerEdicion = new JMenuItem("Rehacer");
        //atajo
        buscarEdicion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        deshacerEdicion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        //rehacerEdicion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

        // Agregamos al menu edicion
        edicion.add(buscarEdicion);
        edicion.add(deshacerEdicion);
        edicion.add(rehacerEdicion);

        //JMenuItems: Ayuda
        JMenuItem listaComandos = new JMenuItem("Lista de comandos");
        ayuda.add(listaComandos);

        //Agrego la barra al panel
        this.add(barra, BorderLayout.NORTH);
    }

    public void barraDeHerramientas() {
        //Config
        menuHerramientas.setFloatable(false);
        //
        abrir = new JButton(); // new ImageIcon("src\\")
        guardar = new JButton();

        nuevo = new JButton();
        deshacer = new JButton();
        rehacer = new JButton();

        color = new JButton();
        tam = new JSpinner(new SpinnerNumberModel(12, 0, 84, 2));
        fuente = new JComboBox(fontNames);
        centrado = new JButton();
        //
        abrir.setText("Open");
        nuevo.setText("New");
        guardar.setText("Save");
        deshacer.setText("<--");
        rehacer.setText("-->");
        color.setText("Color");
        centrado.setText("Centrado");
        //

        fuente.setSelectedIndex(15);

        //onFocus Texto
        nuevo.setToolTipText("Nuevo Archivo");
        abrir.setToolTipText("Abrir Archivo");
        //
        menuHerramientas.add(guardar);
        menuHerramientas.add(abrir);
        menuHerramientas.add(deshacer);
        menuHerramientas.add(rehacer);
        menuHerramientas.add(color);
        menuHerramientas.add(centrado);
        menuHerramientas.add(fuente);
        menuHerramientas.add(tam);
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reaccion");
                JFileChooser fileExplorer = new JFileChooser(); // Elector de archivos
                FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("Archivos de calidad", "txt"); // Filtro de archivos
                fileExplorer.setFileFilter(fileExtensions);

                int selected = fileExplorer.showOpenDialog(barra);// Archivo seleccionado
                if (selected == fileExplorer.APPROVE_OPTION) {
                    File fichero = fileExplorer.getSelectedFile();
                    try (FileReader arch = new FileReader(fichero)) {
                        String cadena = "";
                        int valor = arch.read();
                        while (valor != -1) {
                            cadena = cadena + (char) valor;
                            valor = arch.read();
                        }
                        panelDeTexto.setText(cadena);
                        arch.close();
                    } catch (IOException ex) {
                        System.out.println("no file");
                    }
                }
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int selected = fileChooser.showSaveDialog(barra); // componente padre
                if (selected == fileChooser.APPROVE_OPTION) {
                    File fichero = fileChooser.getSelectedFile();
                    if (fichero.exists()) {
                        int abrir = JOptionPane.showConfirmDialog(null, "El fichero ya Existe");
                    } else {
                        File dir = fichero.getParentFile();
                        dir.mkdir();
                        try {
                            fichero.createNewFile();
                        } catch (IOException ex) {
                            System.out.println("No se pudo crear");
                        }
                    }
                    try {
                        FileWriter f = new FileWriter(fichero);
                        String texto = panelDeTexto.getText();
                        String lineas[] = texto.split("\n");
                        for (String linea : lineas) {
                            f.write(linea + "\n");
                        }
                        f.close();
                    } catch (IOException ex) {
                        System.out.println("No se pudo escribir el fichero");
                    }
                }
            }
        });
        //
        UndoManager editManager = new UndoManager();

        panelDeTexto.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent undoableEditEvent) {
                editManager.addEdit(undoableEditEvent.getEdit());
            }
        });

        deshacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (editManager.canUndo()) {
                    editManager.undo();
                }
            }
        });

        rehacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (editManager.canRedo()) {
                    editManager.redo();
                }
            }
        });

        //Stylos
        centrado.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                //add(new QualyGraphic());
                //new QualyGraphic().setVisible(true);
            }
        }); // left rigth justify
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SimpleAttributeSet atributos = new SimpleAttributeSet(panelDeTexto.getCharacterAttributes());//Obtenemos los atributos actuales
                Color c = JColorChooser.showDialog(null, "Elije un color", panelDeTexto.getSelectedTextColor());// usamos el elector decolot
                if (c != null) {
                    StyleConstants.setForeground(atributos, c); // le damos el color a las letras
                    panelDeTexto.setCharacterAttributes(atributos, false); // le damos los atributos al texto
                }
            }
        });
        tam.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                SimpleAttributeSet atributos = new SimpleAttributeSet(panelDeTexto.getCharacterAttributes());
                StyleConstants.setFontSize(atributos, (int) tam.getValue());
                panelDeTexto.setCharacterAttributes(atributos, false); // le damos los atributos al texto
            }
        });
        fuente.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                SimpleAttributeSet atributos = new SimpleAttributeSet(panelDeTexto.getCharacterAttributes());
                StyleConstants.setFontFamily(atributos, "" + fuente.getSelectedItem());
                panelDeTexto.setCharacterAttributes(atributos, false); // le damos los atributos al texto
            }
        });
        //
        this.add(menuHerramientas, BorderLayout.NORTH);
        //this.add(panelTexto, BorderLayout.CENTER);

    }

    public void textContent() {
        //Scroll
        JScrollPane scroll = new JScrollPane(panelDeTexto);
        Font font = new Font("Sans_Serif", Font.PLAIN, 30);
        panelDeTexto.setFont(font);
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
        //Agregamos el texto al SctollPanel
        initTextPanel();
        this.add(scroll, BorderLayout.CENTER);
    }

    public void menuPopUp() {
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

    public static void viewNumeracion(boolean numeracion, JTextPane textArea, JScrollPane scroll) {

//        scroll.setRowHeaderView(new TextLineNumber(textArea));  
    }

    /**
     * Aumenta la numeracion del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*.n+1
     */
    private String aumentarNumeracion(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        int numeracion = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1]) + 1;
        String numeracionAumentada = "";

        // Concateno la numeracion anterior
        for (int i = 0; i < numeracionAnterior.length - 1; i++) {
            numeracionAumentada += numeracionAnterior[i] + ".";
        }
        numeracionAumentada += numeracion + ".";
        return numeracionAumentada;
    }

    /**
     * Aumenta la numeracion del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*.n+1
     */
    private String aumentarNivel(Item it) {
        //int numeracion = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1 ]) + 1;
        String nuevoNivel = it.getNumeration() + "1.";
        return nuevoNivel;

    }
    
    
    /**
     * Instancia los renglones;
     *
     * @return
     */
    private void initTextPanel() {

        renglones = new ArrayList<>(); // Hijos 
        itemActual = new Item(0, 0, "1.", "Título.", null);
        renglones.add(itemActual);
        panelDeTexto.setText(itemActual.constructRenglon());

        manejarEventosPanelDeTexto();

    }

    private void agregarItemANivel(ArrayList<Item> items) {
        int linea = JTextPaneUtils.getLineNumberByCaret(panelDeTexto) - 1; // le resto uno para contar desde 0.
        // Aumentar Nivel Actual
        Item anterior = items.get(linea);
        String nuevaNumeracion = aumentarNumeracion(anterior);
        Item siguienteItem = new Item(linea, anterior.getNivel(), nuevaNumeracion, "", null);
        insertStringAtTheEnd(panelDeTexto, "\n" + siguienteItem.constructRenglon());
        items.add(siguienteItem);
    }

    private void agregarNivelANumeracion(ArrayList<Item> items) {
        int linea = JTextPaneUtils.getLineNumberByCaret(panelDeTexto) - 1; // le resto uno para contar desde 0.
        //Crear anidamiento desde nivel a nuevo nivel 
        Item anterior = items.get(linea);
        String nuevaNumeracion = aumentarNivel(anterior);
        int nivelAumentado = anterior.getNivel() + 1;
        Item siguienteItem = new Item(linea, nivelAumentado, nuevaNumeracion, "", null);
        insertStringAtTheEnd(panelDeTexto, "\n" + siguienteItem.constructRenglon());
        items.add(siguienteItem);

    }

    private boolean isTheEOFContainedInGruopSelectionSentenses() {
        return true;
    }

    /**
     * Huy ocurrio un hecho muy sucesoso en el cual me ha desordenado la
     * enumeracion de renglones e identaciones. Esto podria traducirse a que me
     * edíta uno o mas renglones los cuales no forman parte del el los renglones
     * final / es
     */
    private void reordenarIndice(ArrayList<Item> items) {

        //   documento.addDocumentListener(listener);
        System.out.println("TEXTIIIOOOOTOTOTO" + panelDeTexto.getSelectionEnd());        //documento.
        //   System.out.println("hjkl"+documento.getEndPosition());
        //   System.out.println("hjkl"+documento.getLength());
        JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, panelDeTexto.getCaretPosition());
        JTextPaneUtils.getLineNumberByCaret(panelDeTexto);

        System.out.println("ASDDDDDD" + JTextPaneUtils.getLineNumberByCaret(panelDeTexto));

        // Significa que me modifico algo en el medio del archivo ... osea que hay al menos una linea por debajo de la seleccion.
        // }
        /*
        
            Si yo linkeo desde el numero de linea al numero de indice, obtengo linea x indice n n.m n.m.p
            
            Entonces empiezo a reordenar desde ahí hasta end of file ... 
        
            1.  
            2.
                2.1
                2.2
                2.3
            3.
                3.1
                    3.1.1
                    3.1.2
            4.
            
        
         */
    }
    private void actualizarEstado(){
        int lineaAnterior = itemActual.getNumeroDeLinea();
        int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto,panelDeTexto.getCaretPosition());
        System.out.println("lineaNueva " + lineaNueva);
        System.out.println("lineaAnterior " + lineaAnterior);
        if(lineaAnterior == lineaNueva){ // Modificamos la linea actual
            actualizarEstructuraDeLinea(lineaNueva,"");
        }else{
             actualizarEstructuraDeTexto();
        }
        /* setItemActual();
        e.getOffset();
        e.getLength();
        System.out.println("e.getOffset() = " + e.getOffset());

        System.out.println("e.getLength() = " + e.getLength()); */
    
        }
    private boolean isRenglonBienFormado(String renglon) {
        String regex = "^\\t*(\\d\\.)+\\s.*$";
        System.out.println("renglon " + renglon);
        System.out.println("String matches regex: " + renglon.matches(regex));
        return renglon.matches(regex);
    }

    private void manejarEventosPanelDeTexto() {
        Document documento = panelDeTexto.getDocument();
        Set<Integer> pressedKeys = new HashSet<>();
        //EVENTOS TECLADO ENTER no se usa
        panelDeTexto.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
           /**     if (pressedKeys.contains(KeyEvent.VK_ENTER) && pressedKeys.size() == 1) {
                    StyledDocument doc = panelDeTexto.getStyledDocument();
                    try {
                        doc.remove(doc.getLength() - 1, 1);
                    } catch (BadLocationException ex) {
                        Logger.getLogger(QsTextPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }*/
            }

            @Override
            public void keyPressed(KeyEvent e) {
              /*  pressedKeys.add(e.getKeyCode());
                if (pressedKeys.contains(KeyEvent.VK_ENTER) && pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                    agregarNivelANumeracion(renglones);
                } else if (pressedKeys.contains(KeyEvent.VK_ENTER) && pressedKeys.contains(KeyEvent.VK_CONTROL)) {
                    //retrocederNivel();
                    //reordenarIndice(renglones);
                } else if (pressedKeys.contains(KeyEvent.VK_ENTER)) { // Local 
                    agregarItemANivel(renglones);
                }*/
            }

            @Override
            public void keyReleased(KeyEvent e) {
               // pressedKeys.remove(e.getKeyCode());

            }
        });
        //EVENTOS DE EDICION DE TEXTO . Añado los caret Listeners
        documento.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                 if(TURN_OFF_LISTENERS==false){
                    TURN_OFF_LISTENERS = true;
                    actualizarEstado();
                    
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
                
                if(TURN_OFF_LISTENERS==false){
                    TURN_OFF_LISTENERS = true;
                    actualizarEstado();
                }
                //   reordenarItems(items);
                int lineaFinalDeLaSeleccion = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, panelDeTexto.getSelectionEnd());
                int lineaFinalDelDocumento = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, documento.getLength());

                if(lineaFinalDeLaSeleccion != lineaFinalDelDocumento){
                // TODO : 
                 
                   }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        panelDeTexto.addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent e) {
                
                System.out.println(panelDeTexto.getText());
                
                System.out.println(" " + e.getDot() + "ooo" + e.getMark());
                int lineaAnterior = itemActual.getNumeroDeLinea();
                int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, panelDeTexto.getCaretPosition());
                if (lineaAnterior != lineaNueva) { // Nos movemos en la líneaActual 
                    if (renglones.size() <= lineaAnterior) {
                        itemActual = renglones.get(lineaNueva);
                    } else {
                        System.out.println("lineaNueva " + lineaNueva);
                    }

                }
            }
        });
    }

    private Item armarItem(String renglon, int numeroDeLinea) {
        String[] arregloRenglon = renglon.split(" ");
        String numeracionAndNivel = arregloRenglon[0]; //obtengo la primer parte del renglon
        int nivelItem = numeracionAndNivel.split("\t").length - 1; //cantidad de tabs
        String numeracionItem = numeracionAndNivel.split("\t")[nivelItem];  //numeracion del indice
        String textoItem = renglon.substring(arregloRenglon[0].length()); // obtengo el resto del texto
        System.out.println("textoItem = " + textoItem);
        return new Item(numeroDeLinea, nivelItem, numeracionItem, textoItem, null);
    }
    
    private void actualizarEstructuraDeLinea(int lineaActual,String textoLineaActual ) {
                //int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto,panelDeTexto.getCaretPosition());
        //String textoDeLinea = JTextPaneUtils.getTextoDeLineaByOffset(panelDeTexto,panelDeTexto.getCaretPosition()); // Obtengo el texto de la linea actual
        if(isRenglonBienFormado(textoLineaActual)){
            Item itemActualizado = armarItem(textoLineaActual,lineaActual);
            itemActual.setCadenaDeTexto(itemActualizado.getCadenaDeTexto());

        }else{
            //escribirlo bien.
            itemActual.setCadenaDeTexto(textoLineaActual);
        }
            renglones.add(itemActual.getNumeroDeLinea(), itemActual);  
        }
    /**
     *
     *
     */
    private void actualizarEstructuraDeTexto() {
 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String[] arregloDeNiveles = new String[10];
                String nuevoTexto = "";
                int caretPosition = panelDeTexto.getCaretPosition();
                String texto = panelDeTexto.getText();
                String[] lineas = texto.split("\n");
                String lineaInicial = lineas[0];
                itemActual = new Item(0, 0, "1.", lineaInicial, null);
                if (isRenglonBienFormado(lineaInicial)) {
                     itemActual.setCadenaDeTexto(armarItem(lineaInicial, 0).getCadenaDeTexto());//por ser el primero ignoro num.
                }
                arregloDeNiveles[itemActual.getNivel()] = itemActual.getNumeration(); // numeracion[0] = "1."
                nuevoTexto += itemActual.constructRenglon();

                for (int i = 1; i < lineas.length; i++) {// Empiezo desde la segunda linea ok? es decir la linea ubicada en 1
                    Item itemToBuild=null;
                    if (isRenglonBienFormado(lineas[i])) { //el renglon esta bien formado
                        
                        itemToBuild = armarItem(lineas[i], i); 
                        if(itemToBuild.getNivel() == itemActual.getNivel()){
                            itemToBuild.setNumeration(aumentarNumeracion(itemActual)); // numeracion + 1 
                            arregloDeNiveles[itemToBuild.getNivel()] = itemToBuild.getNumeration();
                        }else if(itemToBuild.getNivel() < itemActual.getNivel()){ 
                            String num = arregloDeNiveles[itemToBuild.getNivel()]; // Obtengo numeracion del corriente nivel.
                            itemToBuild.setNumeration(num);
                            itemToBuild.setNumeration(aumentarNumeracion(itemToBuild)); 
                            arregloDeNiveles[itemToBuild.getNivel()] = itemToBuild.getNumeration();
                        }else{
                            itemToBuild.setNivel(itemActual.getNivel()+1);//aumenta UN nivel
                            itemToBuild.setNumeration(aumentarNivel(itemActual));
                            arregloDeNiveles[itemToBuild.getNivel()] = itemToBuild.getNumeration();
                        }
                        
                    }else{ // EL renglon está mal formado asique puede venir cualquier cosa, lo ignoro y pongo en el mismo nivel de la corriente numeracion ... 
                        itemToBuild = new Item( i,  
                                                itemActual.getNivel(), 
                                                aumentarNumeracion(itemActual),
                                                lineas[i], 
                                                null);
                    }
                    nuevoTexto+= "\n" +  itemToBuild.constructRenglon();
                    System.out.println("nuevoTexto + i = " + nuevoTexto + " " + i);
                    itemActual = itemToBuild; 
                } 
                panelDeTexto.setText(nuevoTexto); // actualizo el texto
                System.out.println("ee qlia");
                panelDeTexto.setCaretPosition(caretPosition);
                
                
                //Falta actualizar el caret
                
                TURN_OFF_LISTENERS = false;
            }

         });
    }
    
    /**
     * IF an Action esta realizada en el No end of the File 
     */
    private void reordenarItems(ArrayList<Item> items) {
        //  View of the component has not been updated at the time
        //  the DocumentEvent is fired
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                int il = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, panelDeTexto.getCaretPosition() - 1); // Esta el coso alterado.
                if (il > 0) {        // Control de primer linea 
                    int ilAnterior = il - 1;
                    Item renglonAnterior = items.get(ilAnterior);
                    renglonAnterior.getNumeration();
                    renglonAnterior.getNivel();

                }

                Item renglonActual = items.get(il);
                renglonActual.getNumeration();
                renglonActual.getNivel();
                JTextPaneUtils.insertStringAtRow(panelDeTexto, renglonActual.getNivel() + " " + renglonActual.getNumeration());

                //aumentarElNivel
                //Get linea anterior al coso 
                //textoo.getCaretPosition();
                /*        
              hasta el anterior esta todo ordenado 
                       hasta el anterior la lista ses mantiene ok
        ell Item actual de  la linea actual tiene bien el Nivel como corresponde, a los sumo pintarlo bien y 
                Tomar el End Selection y apartir de la linea posterior
                        son elementos no corruptos entonces los tomo y los inserto en el nuevo orden 
                                GET n-1
                                Tomo el nuevo 
                                        add(n) = nuevo ;
                       Este es nuestro punto de partida pasa que hay que ver desde el caret para abajo
  
                 */
            }
        });
    }

    //NOTA TENGO QUE HACER EL QUE CUANDMO E BORRE LA MITAD DE LOS NUMERITOS ME CREE LOS NUMERITOS DE NUEVO EN NEGRITA PARA QUE SENO TE O BUSCAR LA MANERA DE NOPPERMITIR ESO 

    public JTextPane getPanelDeTexto() {
        return panelDeTexto;
    }

    public void setPanelDeTexto(JTextPane panelDeTexto) {
        this.panelDeTexto = panelDeTexto;
    } 
}
