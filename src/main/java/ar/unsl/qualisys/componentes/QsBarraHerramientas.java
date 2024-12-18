/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import LSP.QsInstancia;
import ar.unsl.qualisys.Constantes;
import ar.unsl.qualisys.QualiSys;
import ar.unsl.qualisys.Sesion;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.controllers.PanelEvaluacionController;
import ar.unsl.qualisys.controllers.PanelGrafoController;
import ar.unsl.qualisys.controllers.PanelTextoController;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.*;
import ar.unsl.qualisys.paneles.texto.*;
import ar.unsl.qualisys.paneles.grafo.*;
import ar.unsl.qualisys.paneles.grafo.memento.EstadoGrafo;
import ar.unsl.qualisys.utils.Item;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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
import javax.swing.undo.UndoManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    private PanelTextoController controlTab0;
    
    private PanelGrafoController controlTab1;
    
    private PanelEvaluacionController controlTab2;
    
    
    
    public void stylingComponent(JComponent b,String toolTip){
        b.setBackground(Color.decode("#D6CE93"));
        b.setForeground(Color.decode("#BB8588"));
        b.setBorder(BorderFactory.createLineBorder(Color.decode("#A3A380"),0,true));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setToolTipText(toolTip);
    }
    public QsBarraHerramientas(QsFrame ventana,QsTextPanel tabText,QsGraphicPanel tabGrafic,QsEvaluacionPanel tabInstancias){//[Mostrar resultados en el panel de instancias todo junto],JPanel panelDeResultados) {
        
        controlTab0 = PanelTextoController.getInstance();
        controlTab1 = PanelGrafoController.getInstance();
        controlTab2 = PanelEvaluacionController.getInstance();
        
        
        this.setBackground(Color.decode("#D6CE93")); 
        //this.setForeground(Color.decode("#EFEBCE"));
        this.ventana = ventana;
        this.tabTexto = tabText; // panel donde se forma la estructura de variables
        this.tabGrafico = tabGrafic;
        this.tabInstanciado = tabInstancias;
               
        
        String strPath = QsBarraHerramientas.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        System.out.println("path :" + strPath);
        String aux = File.separator + "src" +File.separator +"main"+File.separator+"resources"+File.separator;
        System.out.println("aux :" + aux);

        strPath = strPath.replace("/target/classes/",aux);
        System.out.println("path :" + strPath);
        strPath = strPath.replace(Constantes.JAR_FILE, "classes/");
        System.out.println("path :" + strPath);
        JButton volver = new JButton(new ImageIcon(strPath + "back-30.png"));
        stylingComponent(volver,"Volver");
        JButton siguiente = new JButton(new ImageIcon(strPath + "forward-30.png"));
        stylingComponent(siguiente,"Siguiente");
        JButton nuevo = new JButton(new ImageIcon(strPath + "new-file-30.png"));
        stylingComponent(nuevo,"Nuevo Archivo");
        JButton abrir = new JButton(new ImageIcon(strPath + "open-file-30.png"));
        stylingComponent(abrir,"Abrir Archivo");
        JButton guardar = new JButton(new ImageIcon(strPath + "save-30.png"));
        stylingComponent(guardar,"Guardar Sesión");
        JButton deshacer = new JButton(new ImageIcon(strPath + "undo-30.png"));
        stylingComponent(deshacer,"Deshacer");
        JButton actualizar = new JButton(new ImageIcon(strPath + "update-30.png"));
        stylingComponent(actualizar,"Refrescar");
        JButton rehacer = new JButton(new ImageIcon(strPath + "redo-30.png"));
        stylingComponent(rehacer,"Rehacer");
        JButton exportar = new JButton(new ImageIcon(strPath + "export-30.png"));
        stylingComponent(exportar,"Exportar Sesión");
        JButton color = new JButton(new ImageIcon(strPath + "color-30.png"));
        stylingComponent(color,"Color del Texto");
        JSpinner tam = new JSpinner(new SpinnerNumberModel(12, 0, 84, 2));
        stylingComponent(tam,"Tamaño de letra"); 
        //JButton centrado = new JButton();
        String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        JComboBox fuente = new JComboBox(fontNames);
        stylingComponent(fuente,"Tipo de Fuente"); 
        fuente.setSelectedIndex(15);

        this.setFloatable(false);

 
        JTextPane panelDeTexto = tabTexto.getJTextPanel();
        panelDeTexto.addKeyListener(new KeyListener(){
            boolean ctrlPressed = false;

            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) { 
                if(ke.getKeyCode() == KeyEvent.VK_F5) {
                    actualizar.doClick();
                }else if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = true;
                }else if (ctrlPressed && ke.getKeyCode() == KeyEvent.VK_Z) {
                    deshacer.doClick();
                }else if (ctrlPressed && ke.getKeyCode() == KeyEvent.VK_Y) {
                    rehacer.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_CONTROL) {
                    ctrlPressed = false;
                }
            }            
        });
        
        this.add(volver);
        this.add(nuevo);
        this.add(abrir);
        this.add(deshacer);
        this.add(actualizar);
        this.add(rehacer);
        this.add(guardar);
        this.add(exportar);
        this.add(color);
        this.add(fuente);
        this.add(tam);
        this.add(siguiente);
        this.setBorder(BorderFactory.createLineBorder(Color.decode("#A3A380"),2,true));
        volver.addActionListener(new ActionListener() {            
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.retrocederTab();
            } 
        });
        
        siguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventana.getTabbedPane().setSelectedIndex(2); 
            }
        });
        nuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoArchivo();
            }
        });
        // Listeners
        abrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArchivo();
                ventana.reinicializarTab();
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
                controlTab0.setTextoConCaret(tabTexto.getJTextPanel().getText(),caretPosition);// El setTexto llama ala ctualizar  estado
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
                int tab = ventana.getTabbedPane().getSelectedIndex(); //ventana = this.ventana;
                if(tab == 0){
                    ar.unsl.qualisys.paneles.texto.memento.CaretTaker caretaker = tabTexto.getCaretTaker();
                    ar.unsl.qualisys.paneles.texto.memento.Originator originator = tabTexto.getOriginator();
                    ar.unsl.qualisys.paneles.texto.memento.Memento undoMemento = caretaker.undo();                    // Undo
                    if (undoMemento != null) {
                        originator.restaurar(undoMemento);
                        controlTab0.setTextoConCaret(originator.getEstado().getTexto(),originator.getEstado().getPos()+1); //ajusto pq se corre
                    }
                }else if(tab == 1){
                    ar.unsl.qualisys.paneles.grafo.memento.CaretTaker caretaker = controlTab1.getCaretTaker();
                    ar.unsl.qualisys.paneles.grafo.memento.Originator originator = controlTab1.getOriginator();
                    ar.unsl.qualisys.paneles.grafo.memento.Memento undoMemento = caretaker.undo();
                    if (undoMemento != null) {
                        originator.restaurar(undoMemento);
                        EstadoGrafo estadoSolicitado = originator.getEstado();
                        
                        Map<String,QsVariable> varSolicitadas = estadoSolicitado.getVariables();
                        Map<String,QsOperador> opSolicitados = estadoSolicitado.getOperadores();
                        Map<String,ArrayList<QsNodo>> relSolicitadas = estadoSolicitado.getRelPadreHijos();
                        
                        
                        Map<String,QsVariable> varNuevas = estadoSolicitado.factoryVaiables(varSolicitadas);
                        Map<String,QsOperador> opNuevos = estadoSolicitado.factoryOperadores(opSolicitados);
                        Map<String,ArrayList<QsNodo>> relNuevas = estadoSolicitado.factoryRelPadreHijos(varNuevas,opNuevos,relSolicitadas);
                        
                        
                        controlTab1.setVariables(varNuevas);
                        controlTab1.setOperadores(opNuevos);
                        controlTab1.setRelPadreHijos(relNuevas);
                        tabGrafico.getDAD().repaint();  
                    }
                }
                
                
        
                
            }
        });

        rehacer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int tab = ventana.getTabbedPane().getSelectedIndex(); //ventana = this.ventana;
                if(tab == 0){
                    ar.unsl.qualisys.paneles.texto.memento.CaretTaker caretaker = tabTexto.getCaretTaker();
                    ar.unsl.qualisys.paneles.texto.memento.Originator originator = tabTexto.getOriginator();
                    ar.unsl.qualisys.paneles.texto.memento.Memento redoMemento = caretaker.redo();                    // Redo
                    if (redoMemento != null) {
                        originator.restaurar(redoMemento);
                        controlTab0.setTextoConCaret(originator.getEstado().getTexto(),originator.getEstado().getPos()+1); //ajusto pq se corre
                    }
                }else if(tab == 1){
                
                    ar.unsl.qualisys.paneles.grafo.memento.CaretTaker caretaker = controlTab1.getCaretTaker();
                    ar.unsl.qualisys.paneles.grafo.memento.Originator originator = controlTab1.getOriginator();
                    ar.unsl.qualisys.paneles.grafo.memento.Memento redoMemento = caretaker.redo();
                    if (redoMemento != null) {
                        originator.restaurar(redoMemento);
                        EstadoGrafo estadoSolicitado = originator.getEstado();
                        
                        Map<String,QsVariable> varSolicitadas = estadoSolicitado.getVariables();
                        Map<String,QsOperador> opSolicitados = estadoSolicitado.getOperadores();
                        Map<String,ArrayList<QsNodo>> relSolicitadas = estadoSolicitado.getRelPadreHijos();
                        
                        
                        Map<String,QsVariable> varNuevas = estadoSolicitado.factoryVaiables(varSolicitadas);
                        Map<String,QsOperador> opNuevos = estadoSolicitado.factoryOperadores(opSolicitados);
                        Map<String,ArrayList<QsNodo>> relNuevas = estadoSolicitado.factoryRelPadreHijos(varNuevas,opNuevos,relSolicitadas);
                        
                        
                        
                        controlTab1.setVariables(varNuevas);
                        controlTab1.setOperadores(opNuevos);
                        controlTab1.setRelPadreHijos(relNuevas);
                        tabGrafico.getDAD().repaint();  
                    }
                }
            }
        });
        exportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int selected = fileChooser.showSaveDialog(ventana); // componente padre
                if (selected == fileChooser.APPROVE_OPTION) {
                    File fichero = fileChooser.getSelectedFile();


                    if (fichero.exists()) {
                        int sobreescribir = JOptionPane.showConfirmDialog(null, "El fichero ya Existe");
                        if(sobreescribir==0){ // Opcion si
                            exportarArchivo(fichero.getPath());
                        }
                    } else {
                        exportarArchivo(fichero.getPath());
                    }
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
    
    protected void abrirArchivo(){
        /*En esta parte tenemos que leer una estructura JSON */
        controlTab1.cantOperadores =-1; // x las dudas
        String cadena="";
        JFileChooser fileExplorer = new JFileChooser(); // Elector de archivos
        JMenuBar barra = new JMenuBar();
        FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("Archivos de calidad", "qsy"); // Filtro de archivos
        fileExplorer.setFileFilter(fileExtensions);
        int selected = fileExplorer.showOpenDialog(barra);// Archivo seleccionado
        if (selected == fileExplorer.APPROVE_OPTION) {
            File fichero = fileExplorer.getSelectedFile();
            try (FileReader arch = new FileReader(fichero)) {
                cadena = "";
                int valor = arch.read();
                while (valor != -1) {
                    cadena = cadena + (char) valor;
                    valor = arch.read();
                }
            //    tabTexto.setTexto(cadena);
                arch.close();
            } catch (IOException ex) {
                System.out.println("no file");
            }
        }
        
        if(!cadena.equals("")){
            JSONObject sesion = new JSONObject(cadena);
            this.controlTab0.setTexto(sesion.getString("texto"));
//            String texto = this.tabTexto.getJTextPanel().getText();
            ArrayList<QsVariable> variablesList  = this.controlTab1.getListaOrdenadaVariables();//ordenada
           
            System.out.println(sesion.getString("texto"));

            JSONObject nodos = sesion.getJSONObject("nodos");
            
            HashMap<String, QsVariable> mapaDeVariables = new HashMap();
            JSONArray variables = nodos.getJSONArray("variables");
            for(Object var : variables){
                JSONObject opJson = (JSONObject) var;
                QsVariable qsVar = new QsVariable(this.tabGrafico.getDAD(),
                opJson.getInt("x"),
                opJson.getInt("y"),
                opJson.getString("name"),
                opJson.getString("descripcion"),
                opJson.getInt("orden"),
                opJson.getDouble("ponderacion")
                
                );
                mapaDeVariables.put(qsVar.getName(), qsVar);
            }   
             
            HashMap<String, QsOperador> mapaDeOperadores = new HashMap();

            JSONArray operadores = nodos.getJSONArray("operadores");
            for(Object op : operadores){
                JSONObject opJson = (JSONObject) op;
                System.out.println(opJson.get("name"));
                QsOperador qsOp = new QsOperador(
                    this.tabGrafico.getDAD(),
                    opJson.getInt("x"),
                    opJson.getInt("y"),
                    opJson.getInt("width"),
                    opJson.getInt("height"),
                    opJson.getString("name"), // name que despues lo reemplazo
                    opJson.getString("nombre"),
                    opJson.getString("symbol"),
                    opJson.getDouble("d"),
                    opJson.getDouble("r2"),
                    opJson.getDouble("r3"),
                    opJson.getDouble("r4"),
                    opJson.getDouble("r5"),
                    opJson.getDouble("ponderacion")
                );
                mapaDeOperadores.put(qsOp.getName(),qsOp);
                int operadorMayor = Integer.parseInt(opJson.getString("name").split("_")[1]);
                if(operadorMayor>controlTab1.cantOperadores) 
                    controlTab1.cantOperadores = operadorMayor;
            }        
            this.controlTab1.setOperadores(mapaDeOperadores);

            JSONArray relaciones = nodos.getJSONArray("relaciones");

            HashMap<String,ArrayList<QsNodo>> mapaDeRelPadreHijos = new HashMap<>();
            for(Object rel : relaciones){
                JSONObject relJson = (JSONObject) rel;
                String padreID = relJson.getString("padreID");
                JSONArray hijos = relJson.getJSONArray("hijos");
                mapaDeRelPadreHijos.put(padreID, new ArrayList<>());

                for( Object h : hijos ){
                    String hijoID = "" + h;
                    System.out.println(hijoID);
                    if(hijoID.contains(".")){ // variable
                        QsVariable varHija = mapaDeVariables.get(hijoID);
                        varHija.setPadreID(padreID);
                        mapaDeRelPadreHijos.get(padreID).add(varHija); 
                    }else{
                        QsOperador opHijo = mapaDeOperadores.get(hijoID);
                        opHijo.setPadreID(padreID);
                        mapaDeRelPadreHijos.get(padreID).add(opHijo); 
                    }
                }
            }
            
            this.controlTab1.setRelPadreHijos(mapaDeRelPadreHijos); 
            JSONArray instanciasJA = sesion.getJSONArray("instancias");
            ArrayList<QsInstancia> instancias = new ArrayList<>();// this.tabInstanciado.getInstancias();

            for(Object iJson: instanciasJA){
                JSONObject instanciaJSON = (JSONObject) iJson;
                
                Map<String,Double> valores = new HashMap<>();
                JSONArray valoresJA = instanciaJSON.getJSONArray("valores");
                for(Object vJson : valoresJA){
                    JSONObject valorJO = (JSONObject) vJson;
                    valores.put(valorJO.getString("varID"), valorJO.getDouble("valor"));
                }
                QsInstancia i = new QsInstancia(
                        instanciaJSON.getString("nombre"),
                        valores);
                instancias.add(i);
            }
            this.controlTab2.setInstancias(instancias);
        }
        
    }

    protected void guardarArchivo(){
        
        JSONObject sesion = new JSONObject();
        JSONObject nodos = new JSONObject();
        JSONArray jsonVariables = new JSONArray();
        JSONArray jsonOperadores = new JSONArray();
        JSONArray jsonRelaciones = new JSONArray();
        JSONArray jsonInstancias = new JSONArray();
        
        String texto = this.tabTexto.getJTextPanel().getText();
        ArrayList<QsVariable> variablesList  = this.controlTab1.getListaOrdenadaVariables();//ordenada
        Map<String, QsOperador> operadores = this.controlTab1.getOperadores();
        Map<String, ArrayList<QsNodo>> relPadreHijos = this.controlTab1.getRelPadreHijos();
        ArrayList<QsInstancia> instancias = this.controlTab2.getInstancias();

        for(QsVariable v : variablesList ){
            System.out.println("v = " + v.getName());
            System.out.println("v = " + v.getDescripcion());
            jsonVariables.put(v.toJSON());
        }
        
        ArrayList<QsOperador> operadoresList = new ArrayList<>(operadores.values());
        
        for(QsOperador o : operadoresList ){
            System.out.println("o = " + o.getName());
            System.out.println("o = " + o.getNombre());
            jsonOperadores.put(o.toJSON());
        }
        
        ArrayList<String> padresList = new ArrayList<>(relPadreHijos.keySet());
        
        for(String p : padresList){
            JSONArray rel = new JSONArray();
            for(QsNodo nodoh : relPadreHijos.get(p)){            //recorro hijos

                rel.put(nodoh.getName());// Identificador de los nodos
            }
            JSONObject relacionPH = new JSONObject();
            relacionPH.put("padreID",p ); // Creo el objeto relacion con el padre correspondiente
            relacionPH.put("hijos", rel);
            jsonRelaciones.put(relacionPH);
        }
        
        for(QsInstancia i: instancias ){                            // Creo el objeto instancia con el nombre correspondiente
            jsonInstancias.put(i.toJSON());         //Instancia
        }
        
        sesion.put("texto",texto) ;
        nodos.put("variables",jsonVariables);
        nodos.put("operadores",jsonOperadores);
        nodos.put("relaciones",jsonRelaciones);
        sesion.put("nodos", nodos);
        sesion.put("instancias", jsonInstancias);
        
        JFileChooser fileChooser = new JFileChooser();
        int selected = fileChooser.showSaveDialog(this); // componente padre
        int sobreescribir = 0;
        if (selected == fileChooser.APPROVE_OPTION) {
            File fichero = fileChooser.getSelectedFile();
            if (fichero.exists()) {
                sobreescribir = JOptionPane.showConfirmDialog(null, "El fichero ya Existe"); // si pone que sí queda en 0
            } else {
                File dir = fichero.getParentFile();
                dir.mkdir();
                try {
                    fichero.createNewFile();
                } catch (IOException ex) {
                    System.out.println("No se pudo crear");
                }
            }
            if(sobreescribir== 0 ){
                try {
                    FileWriter f = new FileWriter(fichero);
                    String jsonString = sesion.toString();
                    String lineas[] = jsonString.split("\n");
                    for (String linea : lineas) {
                        f.write(linea + "\n");
                    }
                    f.close();
                } catch (IOException ex) {
                    System.out.println("No se pudo escribir el fichero");
                }
            }else{
                System.out.println("No quiso guardarlo");
            }
        }
    }
    
    
    private static BufferedImage panelToImage(QsDadPanel DAD, int ancho, int alto, int anchoReducido, int altoReducido) {
        BufferedImage image = new BufferedImage(ancho , alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics(); // Obtengo los Graphics2D para poder dibujar en él.
        DAD.paint(graphics); // ejecuto el metodo paint pasandole por parametro los graficos de la imagen.
        graphics.dispose(); // cierro 
        //Reduccion
        BufferedImage resizedImage = new BufferedImage(anchoReducido, altoReducido, image.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, anchoReducido, altoReducido, null);
        g.dispose();
        return resizedImage;
   //     return image; // retorno la imagen ya pintada.
    }

    private void exportarDAD(PDDocument document) throws IOException {
        QsDadPanel DAD = this.tabGrafico.getDAD();
        //Panel DAD
        int anchoPanel = (int) DAD.getArea().getWidth();
        int altoPanel = (int) DAD.getArea().getHeight();
        
        int anchoReducido = (int) ((float) anchoPanel * 0.7f);
        int altoReducido = altoPanel; // no lo reduzco(int) ((float) altoPanel * 0.7f);
        
        
        float A4X = PDRectangle.A4.getWidth() - 52f; // Margen
        float A4Y = PDRectangle.A4.getHeight(); // Tamaño
        
        int rows = (int) (Math.ceil(altoReducido / A4Y));
        int cols = (int) (Math.ceil(anchoReducido / A4X));

        if (anchoPanel > 0) { // Esto se da cuando se intenta guardar el pdf antes de haber siquiera creado el grafo
            BufferedImage imagenCompleta = panelToImage(DAD, anchoPanel, altoPanel,anchoReducido,altoReducido);
                int x = 0;
                int y = 0;
                PDPage page = null;
                PDPageContentStream contentStream = null;
                for (int col = 0; col < cols; col++) {
                    x = (int) (col * A4X);
                    for (int row = 0; row < rows; row++) {
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page); //Creo la pág
                        y = (int) (row * A4Y);
//                        int width = Math.min((int) A4X, anchoPanel - x);
//                        int height = Math.min((int) A4Y, altoPanel - y);
                        int width = Math.min((int) A4X, anchoReducido - x);
                        int height = Math.min((int) A4Y, altoReducido - y);

                        System.out.println("W " + width);
                        System.out.println("H " + height);
                        BufferedImage croppedImage = imagenCompleta.getSubimage(x, y, width, height); 
                        PDImageXObject pdImage = LosslessFactory.createFromImage(document, croppedImage);
                        contentStream.drawImage(pdImage, 26, Math.abs(A4Y - croppedImage.getHeight()), croppedImage.getWidth(), croppedImage.getHeight());
                        // Añadir marca de agua (fila y columna)
                        String watermark = String.format("Fila: %d, Columna: %d", row + 1, col + 1);
                        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                        contentStream.setNonStrokingColor(0.5f, 0.5f, 0.5f);
                        contentStream.beginText();
                        contentStream.setTextMatrix(Matrix.getTranslateInstance(26, 26));
                        contentStream.showText(watermark);
                        contentStream.endText();
                        contentStream.close();
                    }
                }
                if (contentStream != null) {
                    contentStream.close();
                }
        }
    }

    private void exportarTexto(PDDocument document,PDPageContentStream contentStream) throws IOException {
            PDPage page = null;
            ArrayList<Item> renglones = tabTexto.getRenglones();
            int posY = 520;
            for(int i = 0 ; i < renglones.size();i++){
                posY = posY-20; //le resto 20 de lugar a posY
                if(posY <= 0 ){ //se sale de la pagina
                    contentStream.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    posY=720; // principaio de la pagina
                } 
                String nombre = renglones.get(i).constructRenglon().replaceAll("\t","        ");      
                System.out.println(nombre.length()+ "Tam renglon" + nombre);
                if(nombre.length()>67){
                    posY = posY+20; //le resto 20 de lugar a posY
                    while(nombre.length()>67){
                        posY = posY-20; //le resto 20 de lugar a posY
                        System.out.println("while");
                        String rebanada = nombre.substring(0, 67);    
                        System.out.println(rebanada.length() + "Tam rebanada" + rebanada);
                        nombre = nombre.substring(67,nombre.length());
                        System.out.println(nombre.length() + "Tam renglon" +  nombre);
                        drawLine(contentStream,100,posY,rebanada,PDType1Font.COURIER_BOLD, 12);
                    }
                }
                posY = posY-20; //le resto 20 de lugar a posY
                drawLine(contentStream,100,posY,nombre,PDType1Font.COURIER_BOLD, 12);
            }
            if(contentStream != null){
                contentStream.close();
            }
        }
        
    private void drawLine(PDPageContentStream contentStream,int posX,int posY, String texto,PDType1Font font,int tam){
        try{
            contentStream.setFont(font, tam);
            contentStream.beginText();
            contentStream.newLineAtOffset(posX, posY);   
            contentStream.showText(texto); 
            contentStream.endText();  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
        
        
    private void exportarInstancias(PDDocument document) throws IOException {
            PDPage page = null;
            PDPageContentStream contentStream = null;
            ArrayList<QsVariable> variables = this.controlTab2.getVars();
            ArrayList<QsInstancia> instancias = this.controlTab2.getInstancias();
            double [] resultados = this.controlTab2.getResultados();
            int posY = 0;
            for(int i = 0 ; i < instancias.size();i++){
                posY-=20;
                if(posY <= 0 ){ //se sale de la pagina
                    if (contentStream != null) {
                        contentStream.close(); // Cerrar el flujo actual antes de crear uno nuevo
                    }
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    posY=720; // principaio de la pagina
                } 
                String nombre = instancias.get(i).getNombre();
                drawLine(contentStream,100,posY,"Instancia:     ' " + nombre + " '",PDType1Font.COURIER_BOLD,12);
                posY-=20;
                drawLine(contentStream,100,posY,"Variable:       Valor: ",PDType1Font.COURIER,12);
                for(int j=0; j < variables.size() ;j++){
                    posY-=20;
                    if(posY <= 0 ){ //se sale de la pagina
                        if (contentStream != null) {
                            contentStream.close(); // Cerrar el flujo actual antes de crear uno nuevo
                        }
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        posY=720; // principaio de la pagina
                    }
                    drawLine(contentStream, 100, posY, variables.get(j).getName() + "              " + instancias.get(i).getValores().get(variables.get(j).getName()),PDType1Font.COURIER,12);
                }
                posY-=20;
                if(posY <= 0 ){ //se sale de la pagina
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    posY=720; // principaio de la pagina
                } 
                try{
                    drawLine(contentStream, 100, posY,"Resultado Evaluación:"  + "              " + resultados[i],PDType1Font.COURIER_BOLD,12);
                }catch(IndexOutOfBoundsException iobe){
                    drawLine(contentStream, 100, posY, "No se ha evaluado la instancia",PDType1Font.COURIER,12);
                }     
            }
            if (contentStream != null) {
                    contentStream.close();
            }
        }
        
        
        // Método para obtener la fecha actual en el formato especificado
    private static String obtenerFechaActual() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }
    
    protected void exportarArchivo(String ruta) {
        PDDocument document = new PDDocument();
        try {  
            // Crear la carátula
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            drawLine(contentStream,100,700,"HeVaLog",PDType1Font.TIMES_BOLD, 26);
            drawLine(contentStream,100,650,"Sesion: "+ obtenerFechaActual(),PDType1Font.TIMES_ROMAN, 12);
            drawLine(contentStream,100,630,"Evaluación Actual"+ obtenerFechaActual(),PDType1Font.TIMES_ROMAN, 12);
            drawLine(contentStream,100,570,"Arbol de Preferencias: ",PDType1Font.TIMES_BOLD, 26);

            exportarTexto(document,contentStream); // le paso el content stream por que sigue en la misma pagina
                        
            if(contentStream != null){
                contentStream.close();
            }            
            exportarDAD(document); 
            exportarInstancias(document);            
            document.save(ruta);// Guardar el documento PDF
            document.close();
            System.out.println("PDF generado correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (document != null) {
                try {
                    document.close(); // Asegúrate de liberar el documento
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    protected void nuevoArchivo()   {
        int confirma = JOptionPane.showConfirmDialog(null, "Se perderán los cambios no guardados");
        if(confirma==0){ // Opcion si
            // Obtener la ruta al ejecutable de Java
            try{
                String java = System.getProperty("java.home") + "/bin/java";

                // Añadir .exe para Windows si fuera necesario (aunque no lo uses en este caso)
        //        if (System.getProperty("os.name").toLowerCase().contains("win")) {
        //            java += ".exe";  // Esto es opcional si usas el JAR directamente
        //        }

                // Obtener la ruta del archivo JAR actual
                String currentJar = null;
                try {
                    currentJar = new File(QsBarraHerramientas.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    System.exit(1);  // Salir con error si no se puede obtener la ruta
                }

                // Comprobar si el archivo actual es un JAR
                if (!currentJar.endsWith(".jar")) {
                    System.out.println("La aplicación no está corriendo desde un archivo JAR");
                    System.exit(1);
                }

                // Crear el nuevo proceso para reiniciar la aplicación
                ProcessBuilder builder = new ProcessBuilder(java, "-jar", currentJar);

                // Iniciar el nuevo proceso
                builder.start();

                // Salir del proceso actual
                System.exit(0); 
            }catch(IOException IOE){
                IOE.printStackTrace();
            }
        }  
    }
    
    protected void salir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Se perderán los cambios no guardados");
        if(confirma==0){ // Opcion si
            System.exit(1);
            // exportarArchivo(fichero.getPath());
        } 
    }

    
    
}
