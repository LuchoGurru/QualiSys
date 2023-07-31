package ar.unsl.qualisys.paneles;

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
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

public class PanelTexto extends JPanel {

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

    private JTextPane textoo = new JTextPane();

    // POPUPMENU
    JPopupMenu contextual = new JPopupMenu();
    
    public PanelTexto (){
        this.setLayout(new BorderLayout());
        this.setLayout(new BorderLayout());
        barraDeMenu();
   //     barraDeHerramientas();
        textContent();
        menuPopUp();
        //barraDeEstado();
        this.setVisible(true);
    }

    public void barraDeMenu(){
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
        this.add(barra,BorderLayout.NORTH);
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
        tam = new JSpinner(new SpinnerNumberModel(12,0,84,2));
//        fuente = new JComboBox(fontNames);
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
            public void actionPerformed(ActionEvent e){
                System.out.println("Reaccion");
                JFileChooser fileExplorer = new JFileChooser(); // Elector de archivos
                FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("Archivos de calidad", "txt"); // Filtro de archivos
                fileExplorer.setFileFilter(fileExtensions);

                int selected = fileExplorer.showOpenDialog(barra);// Archivo seleccionado
                if(selected == fileExplorer.APPROVE_OPTION){
                    File fichero = fileExplorer.getSelectedFile();
                    try(FileReader arch = new FileReader(fichero)){
                        String cadena = "";
                        int valor = arch.read();
                        while(valor != -1){
                            cadena = cadena + (char) valor;
                            valor= arch.read();
                        }
                        textoo.setText(cadena);
                        arch.close();
                    }catch (IOException ex){
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
                if(selected == fileChooser.APPROVE_OPTION){
                    File fichero = fileChooser.getSelectedFile();
                    if(fichero.exists()){
                        int abrir = JOptionPane.showConfirmDialog(null,"El fichero ya Existe");
                    }else{
                        File dir = fichero.getParentFile();
                        dir.mkdir();
                        try{
                            fichero.createNewFile();
                        }catch(IOException ex){
                            System.out.println("No se pudo crear");
                        }
                    }
                    try{
                        FileWriter f = new FileWriter(fichero);
                        String texto = textoo.getText();
                        String lineas[] = texto.split("\n");
                        for (String linea : lineas ){
                            f.write(linea + "\n");
                        }
                        f.close();
                    }catch(IOException ex){
                        System.out.println("No se pudo escribir el fichero");
                    }
                }
            }
        });

        //
        UndoManager editManager = new UndoManager();

        textoo.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent undoableEditEvent) {
                editManager.addEdit(undoableEditEvent.getEdit());
            }
        });

        deshacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(editManager.canUndo()){
                    editManager.undo();
                }
            }
        });

        rehacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(editManager.canRedo()){
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
                SimpleAttributeSet atributos = new SimpleAttributeSet(textoo.getCharacterAttributes());//Obtenemos los atributos actuales
                Color c = JColorChooser.showDialog(null,"Elije un color", textoo.getSelectedTextColor());// usamos el elector decolot
                if(c !=null){
                    StyleConstants.setForeground(atributos,c); // le damos el color a las letras
                    textoo.setCharacterAttributes(atributos,false); // le damos los atributos al texto
                }
            }
        });
        tam.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                SimpleAttributeSet atributos = new SimpleAttributeSet(textoo.getCharacterAttributes());
                StyleConstants.setFontSize(atributos, (int) tam.getValue());
                 textoo.setCharacterAttributes(atributos,false); // le damos los atributos al texto
            }
        });
        fuente.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                SimpleAttributeSet atributos = new SimpleAttributeSet(textoo.getCharacterAttributes());
                StyleConstants.setFontFamily(atributos,"" + fuente.getSelectedItem());
                textoo.setCharacterAttributes(atributos,false); // le damos los atributos al texto
            }
        });
        //
        this.add(menuHerramientas,BorderLayout.NORTH);
      //  this.add(panelTexto, BorderLayout.CENTER);


    }

    public void textContent(){
        //Scroll
        JScrollPane scroll = new JScrollPane(textoo);
        Font font = new Font("TimesRoman", Font.PLAIN, 25);
        textoo.setFont(font);
        //Wheel scroll
        scroll.setWheelScrollingEnabled(true);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
/*        
     StyledDocument doc = (StyledDocument) textoo.getDocument();

    // Create a style object and then set the style attributes
    Style style = doc.addStyle("StyleName", null);

    StyleConstants.setFontSize(style, 30);

    doc.insertString(doc.getLength(), "Some Text", style);
        */
        
        //Agregamos el texto al SctollPanel
        this.add(scroll, BorderLayout.CENTER);
    }

    public void menuPopUp(){ 
        JMenuItem deshacer = new JMenuItem("Deshacer");
        JMenuItem rehacer = new JMenuItem("Rehacer");
        JMenuItem cortar = new JMenuItem("Cortar");
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem pegar = new JMenuItem("Pegar");

        deshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
        rehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
        cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
        copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK));

        cortar.addActionListener(new StyledEditorKit.CutAction());
        copiar.addActionListener(new StyledEditorKit.CopyAction());
        pegar.addActionListener(new StyledEditorKit.PasteAction());

        contextual.add(deshacer);
        contextual.add(rehacer);
        contextual.add(cortar);
        contextual.add(copiar);
        contextual.add(pegar);

        textoo.setComponentPopupMenu(contextual);
    }

}
