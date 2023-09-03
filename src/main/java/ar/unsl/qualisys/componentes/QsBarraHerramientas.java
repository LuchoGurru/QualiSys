/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.frames.QsFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
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
import javax.swing.undo.UndoManager;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsBarraHerramientas extends JToolBar{
    
    private QsFrame parent;
    private JTextPane panelDeTexto;
    
    
    public QsBarraHerramientas(QsFrame parent) {
        this.parent = parent;
panelDeTexto=new JTextPane();        JMenuBar barra = new JMenuBar();
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
        JButton centrado = new JButton();
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
        centrado.setText("Centrado");
        fuente.setSelectedIndex(15);

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
        this.add(centrado);
        this.add(fuente);
        this.add(tam);
        this.add(siguiente);
        
        volver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
//Read Only 
       //        int anterior = parent.getIndiceAnterior() - 1;
         //      int cantidadTabs = parent.getTabbedPane().getTabCount();
           //    if(anterior>0 && anterior<cantidadTabs){
             //       parent.getTabbedPane().setSelectedIndex(anterior);
               // }
            }
        });
        
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //Read Only
              // int siguiente = parent.getIndiceAnterior() + 1;
              // int cantidadTabs = parent.getTabbedPane().getTabCount();
              // if(siguiente>0 && siguiente<cantidadTabs){
               
                   
           //     if(siguiente==1){
             //       if(panelDeTexto.isTextoBienFormado()){
               //         tabPanel.setSelectedIndex(1);
                 //       System.out.println("index = is tree good formed ? " + index);
                   //     QsModalPreview  hijo = new QsModalPreview(this,"Vista Previa: VARIABLES A GRAFICAR.",true);
                     //   hijo.setjTextoPane1(panelDeTexto.getPanelDeTexto().getText());
                       // hijo.setVisible(true);
                   // }else{
                    //     tabPanel.setSelectedIndex(0);
                     //    JOptionPane.showConfirmDialog(this, "El listado de variables no esta bien formado");
                    //}
                    //TODO TOMAR TEXTO PANEL = 0 PASARLO A CONSTRUCTOR PANEL 1 

              //  }

                   
                   
                   //parent.getTabbedPane().setSelectedIndex(siguiente);
               
               //}
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
              //  TURN_OFF_LISTENERS = true;
             //   actualizarEstado(1, 0);
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
        //this.add(menuHerramientas, BorderLayout.NORTH);
        //this.add(panelTexto, BorderLayout.CENTER);

    }
}
