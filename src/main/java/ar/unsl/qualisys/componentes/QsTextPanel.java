package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.PanelTexto;
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
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class QsTextPanel extends JPanel {

    private static boolean TURN_OFF_LISTENERS = false;

    private JTextPane panelDeTexto;
    private ArrayList<Item> renglones;
    private Item renglonActual;
    private QsFrame parent;
    /**
     * Constructor Barra Herramientas - Panel de Texto - Menu popup
     */
    public QsTextPanel(QsFrame parent) {
        this.setLayout(new BorderLayout());
        this.parent = parent;
        panelDeTexto = new JTextPane(){

            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getUI().getPreferredSize(this).width
                                <= getParent().getSize().width;
            }

        };
        renglones = new ArrayList<>();
        renglonActual = new Item(0,0,"1.",""); // inicializo item  ... Cambiar por CARGAR ARCHHIVO O NUEVO ARCHIVO
        renglones.add(renglonActual);
        //barraDeHerramientas();
                this.add(new QsBarraHerramientas(this.parent), BorderLayout.NORTH);

        textContent();
        menuPopUp();
        this.setVisible(true);
    }

    /**
     * Construye la barra de herramientas superior para editar texto
     */
    public void barraDeHerramientas() {
        JMenuBar barra = new JMenuBar();
        JToolBar menuHerramientas = new JToolBar();
        JButton volver = new JButton();
        JButton siguiente = new JButton();
        JButton nuevo = new JButton();
        JButton abrir = new JButton();
        JButton guardar = new JButton();
        JButton deshacer = new JButton();
        JButton actualizar = new JButton(); // Haacer boton actualizar 
        JButton rehacer = new JButton();
        JButton color = new JButton();
        JSpinner tam = new JSpinner(new SpinnerNumberModel(12, 0, 84, 2));
        JButton centrado = new JButton();
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fuente = new JComboBox(fontNames);
        //Config
        menuHerramientas.setFloatable(false);
        volver.setText("< Volver");
        siguiente.setText("Siguiente >");
        abrir.setText("Open");
        nuevo.setText("New");
        guardar.setText("Save");
        deshacer.setText("<--");
        actualizar.setText("F5");
        rehacer.setText("-->");
        color.setText("Color");
        centrado.setText("Centrado");
        fuente.setSelectedIndex(15);

        //onFocus Texto
        nuevo.setToolTipText("Nuevo Archivo");
        abrir.setToolTipText("Abrir Archivo");
        actualizar.setToolTipText("Actualizar Texto");
        //
        menuHerramientas.add(volver);
        menuHerramientas.add(guardar);
        menuHerramientas.add(abrir);
        menuHerramientas.add(deshacer);
        menuHerramientas.add(actualizar);
        menuHerramientas.add(rehacer);
        menuHerramientas.add(color);
        menuHerramientas.add(centrado);
        menuHerramientas.add(fuente);
        menuHerramientas.add(tam);
        menuHerramientas.add(siguiente);
        
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
//Read Only 
               int anterior = parent.getIndiceAnterior() - 1;
               int cantidadTabs = parent.getTabbedPane().getTabCount();
               if(anterior>0 && anterior<cantidadTabs){
                    parent.getTabbedPane().setSelectedIndex(anterior);
                }
            }
        });
        
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //Read Only
               int siguiente = parent.getIndiceAnterior() + 1;
               int cantidadTabs = parent.getTabbedPane().getTabCount();
               if(siguiente>0 && siguiente<cantidadTabs){
               
                   
                if(siguiente==1){
                   /*       if(panelDeTexto.isTextoBienFormado()){
                        tabPanel.setSelectedIndex(1);
                        System.out.println("index = is tree good formed ? " + index);
                        QsModalPreview  hijo = new QsModalPreview(this,"Vista Previa: VARIABLES A GRAFICAR.",true);
                        hijo.setjTextoPane1(panelDeTexto.getPanelDeTexto().getText());
                        hijo.setVisible(true);
                    }else{
                         tabPanel.setSelectedIndex(0);
                         JOptionPane.showConfirmDialog(this, "El listado de variables no esta bien formado");
                    }
                    //TODO TOMAR TEXTO PANEL = 0 PASARLO A CONSTRUCTOR PANEL 1 

                */}

                   
                   
                   //parent.getTabbedPane().setSelectedIndex(siguiente);
               
               }
            }
        });
        
        // Listeners
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
                        System.out.println(panelDeTexto.getText() + "chau");
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

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TURN_OFF_LISTENERS = true;
                actualizarEstado(1, 0);
            }
        }
        );
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
        //this.add(panelTexto, BorderLayout.CENTER);

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
 
    private void manejarEventosPanelDeTexto() {
        Document documento = panelDeTexto.getDocument();
        Set<Integer> pressedKeys = new HashSet<>();
        //EVENTOS DE EDICION DE TEXTO . Añado los caret Listeners
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
                    int lineaAnterior = renglonActual.getNumeroDeLinea(); // inicialmente 0
                    int offsetResultado  = e.getOffset() + e.getLength();
                    int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto,offsetResultado);
                    System.out.println("lineaNueva " + lineaNueva);
                    System.out.println("lineaAnterior " + lineaAnterior);
                    actualizarEstado(lineaAnterior,lineaNueva);
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
                    int lineaAnterior = renglonActual.getNumeroDeLinea(); // inicialmente 0 
                     int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto,e.getOffset());
                    actualizarEstado(lineaAnterior,lineaNueva);
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
                System.out.println("panelDeTexto.getCaretPosition() = " + panelDeTexto.getCaretPosition());

                //System.out.println(panelDeTexto.getText() + "hola");
                System.out.println(" " + e.getDot() + "ooo" + e.getMark()); // Mark donde empieza la selecion 
                if(renglonActual!=null){
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
     * @param lineaNueva  posision de linea despues de la modificacion
     */
    private void actualizarEstado(int lineaAnterior, int lineaNueva) { 
           if (lineaAnterior == lineaNueva) { // Modificamos la linea actual
                actualizarEstructuraDeLinea(lineaNueva, "");
            } else {
                actualizarEstructuraDeTexto();
            } 
    }

    private boolean isRenglonBienFormado(String renglon) {
        String regex = "^\\t*(\\d+\\.)+\\s.*(?:\\r\\n|\\r|\\n)$"; // para la mariconeada de windol! 
        System.out.println("renglon.matches(regex) = " + renglon.matches(regex));
        return renglon.matches(regex);
    }

    private Item armarItem(String renglon, int numeroDeLinea) {
        String[] arregloRenglon = renglon.split(" ");
        String numeracionAndNivel = arregloRenglon[0]; //obtengo la primer parte del renglon
        int nivelItem = numeracionAndNivel.split("\t").length - 1; //cantidad de tabs
        String numeracionItem = numeracionAndNivel.split("\t")[nivelItem];  //numeracion del indice
        String textoItem = renglon.substring(arregloRenglon[0].length()+1); // obtengo el resto del texto
        System.out.println("textoItem = " + textoItem);
        return new Item(numeroDeLinea, nivelItem, numeracionItem, textoItem);
    }

    private void actualizarEstructuraDeLinea(int lineaActual, String textoLineaActual) {
        //int lineaNueva = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto,panelDeTexto.getCaretPosition());
        //String textoDeLinea = JTextPaneUtils.getTextoDeLineaByOffset(panelDeTexto,panelDeTexto.getCaretPosition()); // Obtengo el texto de la linea actual
        if (isRenglonBienFormado(textoLineaActual)) {
            Item itemActualizado = armarItem(textoLineaActual, lineaActual);
            renglonActual.setCadenaDeTexto(itemActualizado.getCadenaDeTexto());
        } else {
            //escribirlo bien.
            renglonActual.setCadenaDeTexto(textoLineaActual);
        }
        renglones.add(renglonActual.getNumeroDeLinea(), renglonActual);
        TURN_OFF_LISTENERS = false;
    }

    /**
     *
     *
     */
    private void actualizarEstructuraDeTexto() {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String[] arregloDeNiveles = new String[10]; // Permitimos una anidacion maximas de 10 niveles ... sobra para la practica 
                String nuevoTexto = ""; 
                int caretPosition = panelDeTexto.getCaretPosition();
                String texto = panelDeTexto.getText();
                //si la ultima linea es un /n, agrego espacio en blanco
                if(texto.lastIndexOf("\n") == texto.length()-1){// osea que no me tomo la ultima linea vacia 
                    texto+= " "; // para que me tome la ultima linea sino confunde con EOF y da problemas
                } 
                String[] lineas = texto.split("\n"); 
                renglones = new ArrayList<>();
                renglonActual = null;
                for (int i = 0; i < lineas.length; i++) {// Empiezo desde la segunda linea ok? es decir la linea ubicada en 1
                    Item renglon = null;
                    if(i == 0){// i = 0 - Primer Renglon
                        arregloDeNiveles[0] = "1.";
                        if(isRenglonBienFormado(lineas[i])){ 
                            renglonActual = new Item(i,0,"1.",armarItem(lineas[i], 0).getCadenaDeTexto()); // init primer item 
                        }else{
                            renglonActual = new Item(i,0,"1.",lineas[i]); // init 
                        }
                        nuevoTexto = renglonActual.constructRenglon();
                        renglones.add(renglonActual); 
                        continue;// o break 
                    }else if (isRenglonBienFormado(lineas[i])) { 
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
                    nuevoTexto += "\n" + renglon.constructRenglon();
                    renglonActual = renglon;
                    renglones.add(renglonActual);
                }
                panelDeTexto.setText(nuevoTexto); // actualizo el texto
                panelDeTexto.setCaretPosition(caretPosition);
                int pos = JTextPaneUtils.getIndexLineNumberByOffset(panelDeTexto, caretPosition);
                renglonActual = renglones.get(pos);
                //Falta actualizar el caret
                TURN_OFF_LISTENERS = false;
            }

        });
    } 
    private void agregarItemANivel(ArrayList<Item> items) {
        int linea = JTextPaneUtils.getLineNumberByCaret(panelDeTexto) - 1; // le resto uno para contar desde 0.
        // Aumentar Nivel Actual
        Item anterior = items.get(linea);
        String nuevaNumeracion = aumentarNumeracion(anterior);
        Item siguienteItem = new Item(linea, anterior.getNivel(), nuevaNumeracion, "");
        //insertStringAtTheEnd(panelDeTexto, "\n" + siguienteItem.constructRenglon());
        items.add(siguienteItem);
    }

    private void agregarNivelANumeracion(ArrayList<Item> items) {
        int linea = JTextPaneUtils.getLineNumberByCaret(panelDeTexto) - 1; // le resto uno para contar desde 0.
        //Crear anidamiento desde nivel a nuevo nivel 
        Item anterior = items.get(linea);
        String nuevaNumeracion = aumentarNivel(anterior);
        int nivelAumentado = anterior.getNivel() + 1;
        Item siguienteItem = new Item(linea, nivelAumentado, nuevaNumeracion, "",);
        insertStringAtRow
        insertStringAtTheEnd(panelDeTexto, "\n" + siguienteItem.constructRenglon());
        items.add(siguienteItem);

    }

    public void setTexto(String texto) {
        TURN_OFF_LISTENERS = true;
        this.panelDeTexto.setText(texto);
        actualizarEstado(0,1);//Le paso lineas distintas para que actualice todo el texto.
    }
    public boolean isTextoBienFormado(){
        String[] lineas = this.panelDeTexto.getText().split("\n");
        int i=0;
        while(isRenglonBienFormado(lineas[i])){
            i++;
        }
        return i==lineas.length-1;
    }
}

/*    //NOTA TENGO QUE HACER EL QUE CUANDMO E BORRE LA MITAD DE LOS NUMERITOS ME CREE LOS NUMERITOS DE NUEVO EN NEGRITA PARA QUE SENO TE O BUSCAR LA MANERA DE NOPPERMITIR ESO 

     * IF an Action esta realizada en el No end of the File
     
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
  
                 
            }
        });
    }*/
    
