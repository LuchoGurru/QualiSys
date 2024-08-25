/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import ar.unsl.qualisys.controllers.PanelTextoController;
import ar.unsl.qualisys.paneles.texto.QsTextPanel;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.grafo.QsGraphicPanel;
import ar.unsl.qualisys.paneles.QsEvaluacionPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        this.tabTexto = tabTexto;
        barraDeMenu();
        this.add(barra,BorderLayout.NORTH);
        this.add(new QsBarraHerramientas(parent,tabTexto,tabGrafico,tabInstanciado),BorderLayout.CENTER);
        this.setVisible(true);
  
    }        

    public void barraDeMenu(){
        barra.setBackground(Color.decode("#D6CE93"));
        //JMenus
        JMenu archivo = new JMenu("Archivo");
        //archivo.setForeground(Color.decode("#EFEBCE"));
        JMenu herramientas = new JMenu("Herramientas");
        //herramientas.setForeground(Color.decode("#EFEBCE"));
        JMenu ayuda = new JMenu("Ayuda");
        //ayuda.setForeground(Color.decode("#EFEBCE"));
        
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
    }
    private void abrirArchivo(){
        PanelTextoController controlTab0 = PanelTextoController.getInstance();
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
                controlTab0.setTexto(cadena);
                arch.close();
            } catch (IOException ex) {
                System.out.println("no file");
            }
        }
    }
    
    
    
    
    
    
}
