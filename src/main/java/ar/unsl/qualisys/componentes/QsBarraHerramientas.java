/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.componentes;

import LSP.QsInstancia;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.frames.QsFrame;
import ar.unsl.qualisys.paneles.*;
import ar.unsl.qualisys.paneles.texto.*;
import ar.unsl.qualisys.paneles.grafo.*;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        abrir.setText("Abrir");
        nuevo.setText("Nuevo");
        guardar.setText("Guardar");
        deshacer.setText("<--");
        actualizar.setText("F5");
        rehacer.setText("-->");
        color.setText("Color");
        //centrado.setText("Centrado");
        fuente.setSelectedIndex(15);

 
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
        
        //onFocus Texto
        nuevo.setToolTipText("Nuevo Archivo");
        abrir.setToolTipText("Abrir Archivo");
        actualizar.setToolTipText("Actualizar Texto");
        deshacer.setToolTipText("Ctrl + Z");
        rehacer.setToolTipText("Ctrl + Y");
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
                int tab = ventana.getTabbedPane().getSelectedIndex(); //ventana = this.ventana;
                if(tab == 0){
                    ar.unsl.qualisys.paneles.texto.memento.CaretTaker caretaker = tabTexto.getCaretTaker();
                    ar.unsl.qualisys.paneles.texto.memento.Originator originator = tabTexto.getOriginator();
                    ar.unsl.qualisys.paneles.texto.memento.Memento undoMemento = caretaker.undo();                    // Undo
                    if (undoMemento != null) {
                        originator.restaurar(undoMemento);
                        //System.out.println("Undo: " + originator.getEstado().getTexto() + " "+originator.getEstado().getPos());
                        //Actualizo interfaz

                       // tabTexto.getJTextPanel().setText(originator.getEstado().getTexto());
                       // tabTexto.getJTextPanel().setCaretPosition(originator.getEstado().getPos());
                        tabTexto.setTextoConCaret(originator.getEstado().getTexto(),originator.getEstado().getPos()+1); //ajusto pq se corre
                        //tabTexto.getJTextPanel().setCaretPosition();
                    }
                }else if(tab == 1){
//                    ar.unsl.qualisys.paneles.grafo.memento.CaretTaker caretaker = tabGrafico.getDAD().getCaretTaker();
//                    ar.unsl.qualisys.paneles.grafo.memento.Originator originator = tabGrafico.getDAD().getOriginator();
//                    ar.unsl.qualisys.paneles.grafo.memento.Memento undoMemento = caretaker.undo();
//                    if (undoMemento != null) {
//                        originator.restaurar(undoMemento);
//                        tabGrafico.getDAD().setVariables(originator.getEstado().getVariables());
//                        tabGrafico.getDAD().setOperadores(originator.getEstado().getOperadores());
//                        tabGrafico.getDAD().setRelPadreHijos(originator.getEstado().getRelPadreHijos());
//                         tabGrafico.getDAD().repaint();  
//                    }
//                    
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
                        //System.out.println("Redo: " + originator.getEstado());
                        //Actualizo interfaz
                    //    tabTexto.getJTextPanel().setText(originator.getEstado().getTexto());
                       // tabTexto.getJTextPanel().setCaretPosition(originator.getEstado().getPos());

                        tabTexto.setTextoConCaret(originator.getEstado().getTexto(),originator.getEstado().getPos()+1); //ajusto pq se corre


                    }
                }else if(tab == 1){
                
//                    ar.unsl.qualisys.paneles.grafo.memento.CaretTaker caretaker = tabGrafico.getDAD().getCaretTaker();
//                    ar.unsl.qualisys.paneles.grafo.memento.Originator originator = tabGrafico.getDAD().getOriginator();
//                    ar.unsl.qualisys.paneles.grafo.memento.Memento redoMemento = caretaker.redo();
//                    if (redoMemento != null) {
//                        originator.restaurar(redoMemento);
//                        tabGrafico.getDAD().setVariables(originator.getEstado().getVariables());
//                        tabGrafico.getDAD().setOperadores(originator.getEstado().getOperadores());
//                        tabGrafico.getDAD().setRelPadreHijos(originator.getEstado().getRelPadreHijos());
//                        tabGrafico.getDAD().repaint();  
//                    }
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
    
    
    public void mostrarPanelDeInstanciadoLSP(){
        ventana.setTURN_OFF_LISTENERS(true);         
        ventana.getTabbedPane().setSelectedIndex(2);
        ventana.setIndiceActual(2);
        ventana.setTURN_OFF_LISTENERS(false); 
        ventana.initPanelInstancias();
    }
    /**
     * 

    public String getTextoVariables(){
       ArrayList<Item> variables = this.tabTexto.getVariables();
       String textoVar="";
       for(Item v : variables){
           textoVar += v.constructRenglon() + "\n";
       }
        return textoVar;
    }    */
    
    
    
    public void manejarCambioDePagina(int pagina){
        switch(pagina){
            case 1:{
               // tabTexto.setTextoConCaret(tabTexto.getJTextPanel().getText(),tabTexto.getJTextPanel().getCaretPosition()); // para activar el F5

                
                if(tabTexto.isTextoBienFormado()){
                   // QsVistaPreviaModal modal = new QsVistaPreviaModal(ventana,this,"Vista Previa:",true);
                   // getTextoVariables();
                   // modal.setTextoPane1(this.getTextoVariables());
                  //  modal.setVisible(true);
                  mostrarPanelGrafico();
                }else{
                    ventana.getTabbedPane().setSelectedIndex(0);
                    JOptionPane.showMessageDialog(this, "El listado de variables no esta bien formado");
                }
                break;
            }
            case 2:{
                if(tabGrafico.getDAD().isArbolBienFormado()){
                    // CAMBIO DE PAGINA
                    mostrarPanelDeInstanciadoLSP();
                }else{
                    ventana.getTabbedPane().setSelectedIndex(1);//nota: hacer ponderacion automatica
                    JOptionPane.showMessageDialog(this, "¡La funcion de Evaluacion no esta correcta!    1) Asigne todas las variables.    2) Respete la cardinalidad del dominio de cada operador [2,5].     3) Recuerde que la raíz de el árbol debe ser unica.");
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
        
        
        /*En esta parte tenemos que leer una estructura JSON */
        
         
        
        String cadena="";
        JFileChooser fileExplorer = new JFileChooser(); // Elector de archivos
        JMenuBar barra = new JMenuBar();
        FileNameExtensionFilter fileExtensions = new FileNameExtensionFilter("Archivos de calidad", "txt"); // Filtro de archivos
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
            this.tabTexto.setTexto(sesion.getString("texto"));
//            String texto = this.tabTexto.getJTextPanel().getText();
            ArrayList<QsVariable> variablesList  = this.tabGrafico.getDAD().getListaVariables();//ordenada
            
// Map<String, QsOperador> operadores = this.tabGrafico.getDAD().getOperadores();
            Map<String, ArrayList<QsNodo>> relPadreHijos = this.tabGrafico.getDAD().getRelPadreHijos();
            ArrayList<QsInstancia> instancias = this.tabInstanciado.getInstancias();

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
                opJson.getFloat("ponderacion")
                
                );
                    // FALTA EL padre ID + el Valor de la ponderacion 
                System.out.println("-------------------------");
              //  System.out.println(opJson.getString("padreID"));
              //  System.out.println(opJson.getFloat("ponderacion"));
              //  qsVar.setPadreID(opJson.getString("padreID"));
              //  qsVar.setPonderacion(opJson.getFloat("ponderacion"));
                mapaDeVariables.put(qsVar.getName(), qsVar);
            }   
             
            
            HashMap<String,ArrayList<QsNodo>> relPadreHijosSinVars = new HashMap<>();
            JSONArray operadores = nodos.getJSONArray("operadores");
            for(Object op : operadores){
                JSONObject opJson = (JSONObject) op;
                System.out.println(opJson.get("name"));
                QsOperador qsOp = new QsOperador(this.tabGrafico.getDAD(),
                    0, // name que despues lo reemplazo
                    opJson.getString("nombre"),
                    opJson.getString("symbol"),
                    opJson.getFloat("d"),
                    opJson.getDouble("r2"),
                    opJson.getDouble("r3"),
                    opJson.getDouble("r4"),
                    opJson.getDouble("r5"),
                    opJson.getFloat("ponderacion")
                );
                qsOp.setName(opJson.getString("name"));
                QsDadPanel.cantOperadores ++;
                int x = opJson.getInt("x") ;
                int y = opJson.getInt("y") ;
                int width = opJson.getInt("width") ;
                int height = opJson.getInt("height");
                qsOp.setBounds(x,y,width,height);
                qsOp.setBackground(Color.white);
                this.tabGrafico.getDAD().addOperator(qsOp);
            }        
            
            this.tabGrafico.getDAD().repaint();
            
            JSONArray relaciones = nodos.getJSONArray("relaciones");
            /*
            //Si hay al menos una cosa, voy a empezar desde el operador _ op0
            int cantOperadores = 0 ;
            for(Object rel : relaciones){
                JSONObject relJson = (JSONObject) rel;
                String padreID = "op_"+cantOperadores;
                JSONArray hijos = relJson.getJSONArray("hijos");
                                        System.out.println("sdasadsadsadsadsadsad");

                for( Object h : hijos ){
                    String hijoID = "" + h;
                    System.out.println(hijoID);
                    if(hijoID.contains(".")){ // variable
                        QsVariable varHija = mapaDeVariables.get(hijoID);
                        varHija.setPadreID(padreID);
                        relPadreHijos.get(padreID).add(varHija); 
                    }else{
                        QsOperador opHijo = this.tabGrafico.getDAD().getOperadores().get(hijoID);
                        opHijo.setPadreID(padreID);
                        relPadreHijos.get(padreID).add(opHijo); 
                    }
                }
                //            var get var add a la lista de relaciones .
                
                
                cantOperadores ++;
                
            }*/
            
            for(Object rel : relaciones){
                JSONObject relJson = (JSONObject) rel;
                String padreID = relJson.getString("padreID");
                JSONArray hijos = relJson.getJSONArray("hijos");

                for( Object h : hijos ){
                    String hijoID = "" + h;
                    System.out.println(hijoID);
                    if(hijoID.contains(".")){ // variable
                        QsVariable varHija = mapaDeVariables.get(hijoID);
                        varHija.setPadreID(padreID);
                        relPadreHijos.get(padreID).add(varHija); 
                    }else{
                        QsOperador opHijo = this.tabGrafico.getDAD().getOperadores().get(hijoID);
                        opHijo.setPadreID(padreID);
                        relPadreHijos.get(padreID).add(opHijo); 
                    }
                }
                //            var get var add a la lista de relaciones . 
             //   cantOperadores ++; 
            }
            
            
            
            
            
        }
        
        
        
        
    }
    /**
    {
           "texto" : "
      
           ",
           "nodos" : {
               "variables":[],
               "operadores":[],
               "relaciones":[
                   {"padre0":["hijoID1","hijo2","hijo3"]}
               ]
           },
           "instancias" : [
                {"nombre":[0,1,2,3,4,5,6,7,8,9]}
            ]
    }
     */
    private void guardarArchivo(){
        
        JSONObject sesion = new JSONObject();
        JSONObject nodos = new JSONObject();
        JSONArray jsonVariables = new JSONArray();
        JSONArray jsonOperadores = new JSONArray();
        JSONArray jsonRelaciones = new JSONArray();
        JSONArray jsonInstancias = new JSONArray();
        
        String texto = this.tabTexto.getJTextPanel().getText();
        ArrayList<QsVariable> variablesList  = this.tabGrafico.getDAD().getListaVariables();//ordenada
        Map<String, QsOperador> operadores = this.tabGrafico.getDAD().getOperadores();
        Map<String, ArrayList<QsNodo>> relPadreHijos = this.tabGrafico.getDAD().getRelPadreHijos();
        ArrayList<QsInstancia> instancias = this.tabInstanciado.getInstancias();

        for(QsVariable v : variablesList ){
            System.out.println("v = " + v.getName());
            System.out.println("v = " + v.getDescripcion());
            jsonVariables.put(v.toJSON());
        }
        
        ArrayList<QsOperador> operadoresList = new ArrayList<>(operadores.values());
        
        for(QsOperador o : operadoresList ){
            System.out.println("v = " + o.getName());
            System.out.println("v = " + o.getNombre());
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
        
        for(QsInstancia i: instancias ){
            JSONArray valoresI = new JSONArray();
            for(Double valor : i.getValores()){            
                valoresI.put(valor);// Armo listo de valores
            }
            JSONObject relacionPH = new JSONObject();
            relacionPH.put(i.getNombre(),valoresI); // Creo el objeto instancia con el nombre correspondiente
            jsonInstancias.put(relacionPH);
        }
        
        sesion.put("texto",texto) ;
        nodos.put("variables",jsonVariables);
        nodos.put("operadores",jsonOperadores);
        nodos.put("relaciones",jsonRelaciones);
        sesion.put("nodos", nodos);
        sesion.put("instancias", jsonInstancias);
        
        JFileChooser fileChooser = new JFileChooser();
        int selected = fileChooser.showSaveDialog(this); // componente padre
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
                String jsonString = sesion.toString();
                String lineas[] = jsonString.split("\n");
                for (String linea : lineas) {
                    f.write(linea + "\n");
                }
                f.close();
            } catch (IOException ex) {
                System.out.println("No se pudo escribir el fichero");
            }
        }
    }
}