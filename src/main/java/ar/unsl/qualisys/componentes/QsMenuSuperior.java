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
import javax.swing.JOptionPane;
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
        QsBarraHerramientas toolBarra = new QsBarraHerramientas(parent,tabTexto,tabGrafico,tabInstanciado);
        barraDeMenu(parent, toolBarra);
        this.add(barra,BorderLayout.NORTH);
        this.add(toolBarra,BorderLayout.CENTER);
        this.setVisible(true);
  
    }        

    public void barraDeMenu(QsFrame parent, QsBarraHerramientas toolBarra){
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
        JMenuItem nuevoArchivo = new JMenuItem("Nuevo");
        JMenuItem guardarArchivo = new JMenuItem("Guardar");
        JMenuItem exportarArchivo = new JMenuItem("Exportar");
        JMenuItem salir = new JMenuItem("Salir");
        
        salir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBarra.salir();
            }
        });     
        
        abrirArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBarra.abrirArchivo();
                parent.reinicializarTab();
            }
        });
        
        nuevoArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBarra.nuevoArchivo();
            }
        });

        guardarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toolBarra.guardarArchivo();
            }
        });
        

        exportarArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int selected = fileChooser.showSaveDialog(parent); // componente padre
                if (selected == fileChooser.APPROVE_OPTION) {
                    File fichero = fileChooser.getSelectedFile();


                    if (fichero.exists()) {
                        int sobreescribir = JOptionPane.showConfirmDialog(null, "El fichero ya Existe");
                        if(sobreescribir==0){ // Opcion si
                            toolBarra.exportarArchivo(fichero.getPath());
                        }
                    } else {
                        toolBarra.exportarArchivo(fichero.getPath());
                    }
                }            
            }
        });
        
        // Atajos del teclado
        abrirArchivo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));
        //Agregamos los JMenuItems a archivo
        archivo.add(abrirArchivo);
        archivo.add(nuevoArchivo);
        archivo.add(guardarArchivo);
        archivo.add(exportarArchivo);
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
        JMenuItem contactoDesarrollador = new JMenuItem("Manual de usuario");
        ayuda.add(listaComandos);
        ayuda.add(contactoDesarrollador);

        //Agrego la barra al panel
        //this.add(BorderLayout.NORTH);
        //Se agregan los menus a la barra
        barra.add(archivo);
    //    barra.add(herramientas);
        barra.add(ayuda);
    }
     
    
    
    
    
    
    
}
