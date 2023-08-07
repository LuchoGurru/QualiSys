/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

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
 *
 * @author luciano.gurruchaga
 */
public class QsMenuSuperior extends JMenuBar {
   
//    private JMenuBar barra = new JMenuBar();
    private JButton nuevo;
    private JButton abrir;
    private JButton guardar;
    private JTextPane textoo = new JTextPane();
    private int ancho = 1300;
    private int alto = 70;
    private QsTextPanel panelDeLlenadoDeVariables;
    
    public QsMenuSuperior (QsTextPanel panelDeLlenadoDeVariables){
        System.out.println("1");
        this.panelDeLlenadoDeVariables = panelDeLlenadoDeVariables;
       // this.setLayout(new BorderLayout());
        this.setBounds(0, 0, ancho, alto);
        this.setBackground(Color.decode("#F09757"));
        barraDeMenu();
        this.setVisible(true);
    }

/*    @Override 
    public void paintComponent(Graphics g){
        System.out.println("2");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0,0, Color.decode("#F09757"), 0,getWidth(), Color.decode("#F000000"));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, 1300,70, 15, 15);
      //  g2.fillRect(500-20, 0, 15,15);
    }*/
    
    public void barraDeMenu(){
        //JMenus
        JMenu archivo = new JMenu("Archivo");
        JMenu edicion = new JMenu("Edicion");
        JMenu herramientas = new JMenu("Herramientas");
        JMenu ayuda = new JMenu("Ayuda");
        QsMenuSuperior instancia = this;


        //JMenuItems: Archivo
        JMenuItem abrirArchivo = new JMenuItem("Abrir");
        abrirArchivo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Reaccion");
                JFileChooser fileExplorer = new JFileChooser(); // Elector de archivos
                FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("Archivos de calidad", "txt"); // Filtro de archivos
                fileExplorer.setFileFilter(fileExtensions);

                int selected = fileExplorer.showOpenDialog(instancia);// Archivo seleccionado
                if (selected == fileExplorer.APPROVE_OPTION) {
                    File fichero = fileExplorer.getSelectedFile();
                    try (FileReader arch = new FileReader(fichero)) {
                        String cadena = "";
                        int valor = arch.read();
                        while (valor != -1) {
                            cadena = cadena + (char) valor;
                            valor = arch.read();
                        }
                        JTextPane panelDeTexto = new JTextPane();
                        panelDeTexto.setText(cadena);
                        panelDeLlenadoDeVariables.setPanelDeTexto(panelDeTexto);
                        arch.close();
                    } catch (IOException ex) {
                        System.out.println("no file");
                    }
                }
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
        //this.add(BorderLayout.NORTH);
                //Se agregan los menus a la barra
        this.add(archivo);
        this.add(edicion);
        this.add(herramientas);
        this.add(ayuda);
    }
}
