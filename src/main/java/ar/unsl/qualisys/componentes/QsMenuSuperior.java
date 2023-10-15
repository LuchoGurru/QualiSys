/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.paneles.texto.QsTextPanel;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.grafo.QsGraphicPanel;
import ar.unsl.qualisys.paneles.QsEvaluacionPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
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
 * Contiene el Menu Bar de arriba y 
 * La Barra de Herramientas
 * @author luciano.gurruchaga
 */
public class QsMenuSuperior extends JPanel {
   
    private JMenuBar barra = new JMenuBar();
    private QsFrame parent;
    private QsTextPanel tabTexto; 
    private QsGraphicPanel tabGrafico; 
    private QsEvaluacionPanel tabInstanciado;
    
    public QsMenuSuperior (QsFrame parent, QsTextPanel tabTexto,QsGraphicPanel tabGrafico, QsEvaluacionPanel tabInstanciado){
        this.setLayout(new BorderLayout());
        this.setBackground(Color.decode("#F09757"));
        this.tabTexto = tabTexto;
        barraDeMenu();
        this.add(new QsBarraHerramientas(parent,tabTexto,tabGrafico,tabInstanciado),BorderLayout.CENTER);
        this.setVisible(true);
  
    }
    public void barraDeMenu(){
        //JMenus
        JMenu archivo = new JMenu("Archivo");
        JMenu herramientas = new JMenu("Herramientas");
        JMenu ayuda = new JMenu("Ayuda");
        QsMenuSuperior instancia = this;


        //JMenuItems: Archivo
        JMenuItem abrirArchivo = new JMenuItem("Abrir");
        abrirArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
            }
        });

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
        JMenuItem actualizarEdicion = new JMenuItem("Actualizar");
        //atajo
        buscarEdicion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK));
        deshacerEdicion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        //rehacerEdicion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));

        // Agregamos al menu edicion
        herramientas.add(buscarEdicion);
        herramientas.add(deshacerEdicion);
        herramientas.add(rehacerEdicion);
        herramientas.add(actualizarEdicion);

        //JMenuItems: Ayuda
        JMenuItem listaComandos = new JMenuItem("Lista de comandos");
        JMenuItem contactoDesarrollador = new JMenuItem("Contacto con el desarrolador");
        ayuda.add(listaComandos);
        ayuda.add(contactoDesarrollador);

        //Agrego la barra al panel
        //this.add(BorderLayout.NORTH);
        //Se agregan los menus a la barra
        barra.add(archivo);
        barra.add(herramientas);
        barra.add(ayuda);
        this.add(barra,BorderLayout.NORTH);
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
}
