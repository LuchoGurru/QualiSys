/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.paneles.QsTextPanel;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.grafo.QsGraphicPanel;
import ar.unsl.qualisys.paneles.QsEvaluacionPanel;
import ar.unsl.qualisys.utils.Item;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsBarraHerramientas extends JToolBar{
    // Rest of the code for your JPanel
    
    
    protected QsFrame ventana; // Ventana Principal
    private QsTextPanel tabTexto; // panel donde se forma la estructura de variables
    private QsGraphicPanel tabGrafico; // panel grafico donde se forma el árbol de preferencias
    private QsEvaluacionPanel tabInstanciado;
    
    
    
    public QsBarraHerramientas(QsFrame ventana,QsTextPanel tabText,QsGraphicPanel tabGrafic,QsEvaluacionPanel tabInstancias){//[Mostrar resultados en el panel de instancias todo junto],JPanel panelDeResultados) {
        this.ventana = ventana;
        this.tabTexto = tabText; // panel donde se forma la estructura de variables
        this.tabGrafico = tabGrafic;
        this.tabInstanciado = tabInstancias;
      //  JToolBar menuHerramientas = new JToolBar();
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
        //JButton centrado = new JButton();
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fuente = new JComboBox(fontNames);
        //Config
        this.setFloatable(false);
        volver.setText("< Volver");
        siguiente.setText("Siguiente >");
        abrir.setText("Open");
        nuevo.setText("New");
        guardar.setText("Save");
        deshacer.setText("<--");
        actualizar.setText("F5");
        rehacer.setText("-->");
        color.setText("Color");
        //centrado.setText("Centrado");
        fuente.setSelectedIndex(15);

 
        JTextPane panelDeTexto = tabTexto.getJTextPanel();
        panelDeTexto.addKeyListener(new KeyListener(){
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) { 
                if(ke.getKeyCode() == KeyEvent.VK_F5) {
                    actualizar.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                //hrow new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
            
        });
        
        //onFocus Texto
        nuevo.setToolTipText("Nuevo Archivo");
        abrir.setToolTipText("Abrir Archivo");
        actualizar.setToolTipText("Actualizar Texto");
        //
        this.add(volver);
        this.add(guardar);
        this.add(abrir);
        this.add(deshacer);
        this.add(actualizar);
        this.add(rehacer);
        this.add(color);
        //this.add(centrado);
        this.add(fuente);
        this.add(tam);
        this.add(siguiente);
        volver.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                retrocederTab();
            } 
        });
        
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avanzarTab();
            }
        });
        
        // Listeners
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                guardarArchivo();
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int caretPosition = tabTexto.getJTextPanel().getCaretPosition();
                tabTexto.setTextoConCaret(tabTexto.getJTextPanel().getText(),caretPosition);// El setTexto llama ala ctualizar  estado
            }
        }
        );
        
        UndoManager editManager = new UndoManager();

        tabTexto.getJTextPanel().getDocument().addUndoableEditListener(new UndoableEditListener() {
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
        //centrado.addActionListener(new StyledEditorKit.AlignmentAction("Medio", StyleConstants.ALIGN_CENTER)); // left rigth justify
        color.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JTextPane panelDeTexto = tabTexto.getJTextPanel();
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
                JTextPane panelDeTexto = tabTexto.getJTextPanel();
                SimpleAttributeSet atributos = new SimpleAttributeSet(panelDeTexto.getCharacterAttributes());
                StyleConstants.setFontSize(atributos, (int) tam.getValue());
                panelDeTexto.setCharacterAttributes(atributos, false); // le damos los atributos al texto
            }
        });
        fuente.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                JTextPane panelDeTexto = tabTexto.getJTextPanel();
                SimpleAttributeSet atributos = new SimpleAttributeSet(panelDeTexto.getCharacterAttributes());
                StyleConstants.setFontFamily(atributos, "" + fuente.getSelectedItem());
                panelDeTexto.setCharacterAttributes(atributos, false); // le damos los atributos al texto
            }
        });
    } 
    
    
    public void mostrarPanelGrafico(){
        ventana.setTURN_OFF_LISTENERS(true);         
        ventana.getTabbedPane().setSelectedIndex(1);
        ventana.setIndiceActual(1);
        ventana.setTURN_OFF_LISTENERS(false); 
        ventana.initPanelGrafico();
    }
    
    
    public void mostrarPanelDeModeladoLSP(){
        ventana.setTURN_OFF_LISTENERS(true);         
        ventana.getTabbedPane().setSelectedIndex(2);
        ventana.setIndiceActual(2);
        ventana.setTURN_OFF_LISTENERS(false); 
        ventana.initPanelModelos();
    }
    /**
     * 
    */
    public String getTextoVariables(){
       ArrayList<Item> variables = this.tabTexto.getVariables();
       String textoVar="";
       for(Item v : variables){
           textoVar += v.constructRenglon() + "\n";
       }
        return textoVar;
    }
    
    
    
    public void manejarCambioDePagina(int pagina){
        switch(pagina){
            case 1:{
                if(tabTexto.isTextoBienFormado()){
                    QsVistaPreviaModal modal = new QsVistaPreviaModal(ventana,this,"Vista Previa:",true);
                    getTextoVariables();
                    modal.setTextoPane1(this.getTextoVariables());
                    modal.setVisible(true);
                }else{
                    ventana.getTabbedPane().setSelectedIndex(0);
                    JOptionPane.showConfirmDialog(this, "El listado de variables no esta bien formado");
                }
                break;
            }
            case 2:{
                if(tabGrafico.getDAD().isArbolBienFormado()){
                    // CAMBIO DE PAGINA
                    mostrarPanelDeModeladoLSP();
                }else{
                    ventana.getTabbedPane().setSelectedIndex(1);//nota: hacer ponderacion automatica
                    JOptionPane.showMessageDialog(this, "La funcion de Evaluacion no esta correcta.\n- Asigne todas las variables,-Respete el dominio[2,5] de cada operador\nY recuerde que la raíz de el árbol debe ser unica.");
                }
                break;
            }
            case 3:{
                //if(controlarValoresInstantias){
                  //EVALUAR N FUNCIONES  
                //}
                break;
            }
            case 4:{
                
                break;
            }
        } 
    }
    private void retrocederTab(){
       ventana.setTURN_OFF_LISTENERS(true);         
       // SET READ ONLY 
       int anterior = ventana.getIndiceActual() - 1;
       if(anterior>-1){
           ventana.getTabbedPane().setSelectedIndex(anterior);
           ventana.setIndiceActual(anterior);
       }
       ventana.setTURN_OFF_LISTENERS(false);
    }
    
    private void avanzarTab(){
        int siguiente = ventana.getIndiceActual() + 1;
        int cantidadTabs = ventana.getTabbedPane().getTabCount();
        if(siguiente<cantidadTabs){
            manejarCambioDePagina(siguiente);
        }
    }
    private void abrirArchivo(){
        JFileChooser fileExplorer = new JFileChooser(); // Elector de archivos
        JMenuBar barra = new JMenuBar();
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
                tabTexto.setTexto(cadena);
                arch.close();
            } catch (IOException ex) {
                System.out.println("no file");
            }
        }
    }
    private void guardarArchivo(){
        /*    JFileChooser fileChooser = new JFileChooser();
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
                }*/
    }
}          

