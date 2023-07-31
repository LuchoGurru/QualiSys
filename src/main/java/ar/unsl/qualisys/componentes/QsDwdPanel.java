/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsDwdPanel extends JPanel{
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
        JMenuBar barra = new JMenuBar();

    public QsDwdPanel(){
        
        this.setLayout(new BorderLayout());
        this.setBackground(Color.red);
        barraDeMenu();
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
}
