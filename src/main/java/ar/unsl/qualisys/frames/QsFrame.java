package ar.unsl.qualisys.frames;
 
import ar.unsl.qualisys.componentes.QsTabPanel;
import ar.unsl.qualisys.componentes.QsMenuSuperior;
import ar.unsl.qualisys.paneles.texto.QsTextPanel;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.paneles.grafo.QsGraphicPanel;
import ar.unsl.qualisys.utils.Item;
import ar.unsl.qualisys.paneles.QsEvaluacionPanel;
import GUIUtils.Chart;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;

import GUIUtils.ModelChart;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *          VENTANA
 * @author luciano.gurruchaga
 */
public class QsFrame extends JFrame{
    
    private static boolean TURN_OFF_LISTENERS = false;    //ajjaja
    
    private QsTextPanel tabTexto; // panel donde se forma la estructura de variables
    private QsGraphicPanel tabGrafico; // panel grafico donde se forma el árbol de preferencias
    private QsEvaluacionPanel tabInstancias; // panel donde se asignan valores a las variables de distintos modelos LPS en particular
    private JPanel tablUtilidades; // Estadisticas TODO Opcion dejar como visual como propuesta de escalabilidad;
    private int indiceActual;
    private JTabbedPane tabbedPane;
    
    public ArrayList<Item> g_variables;
    public ArrayList<QsNodo> g_nodos;
    //public ArrayList<QualyVariable> g_instancias;
  //GLOBALES
   //cargarItems
           //Nodos 
            //Instancias
    
    // Otro panel que se me ocurra
    
    public QsFrame(){
        this.setBounds(200,200,1300,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        tabbedPane = new QsTabPanel();
        tabTexto = new QsTextPanel(this);
        tabGrafico = new QsGraphicPanel(this);
        tabInstancias = new QsEvaluacionPanel(this);
        //this.setBackground(Color.decode("#A3A380"));
        
        this.add(new QsMenuSuperior(this,tabTexto,tabGrafico,tabInstancias),BorderLayout.NORTH);  // PARA ABRIR Y CERRAR ARCHIVO
        tabbedPane.addTab("Variables de Preferencia", tabTexto);
        tabbedPane.addTab("Árbol ", tabGrafico);
        
        JScrollPane scrollPane = new JScrollPane(tabInstancias);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); 
        tabbedPane.addTab("Instanciado", scrollPane);
        /*JPanel panel4;
        panel4 = new JPanel();
        panel4.add(createChart());
        JPanel paneel = new RoundPanel();
        paneel.add(new Form_Dashboard());
        *///tabbedPane.addTab("Resultados ", panel4);
        //tabbedPane.setBackground(Color.decode("#F09757"));
        //tabbedPane.addTab("Resultados 5", paneel);

        //LISTENER
        indiceActual = 0 ; //indice anterior();

        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                //manejarTabs(indiceAnterior,tabbedPane);
                 System.out.println("mouse clicked stateChanged"+ tabbedPane.getSelectedIndex());                // No hacer nada cuando se hace clic en el JTabbedPane
                System.out.println("mouse clicked getSelectedIndice"+ tabbedPane.getSelectedIndex());                // No hacer nada cuando se hace clic en el JTabbedPane
                if (TURN_OFF_LISTENERS == false) {// para que no se vaya del limite
                    tabbedPane.setSelectedIndex(indiceActual);
                }
            }
        });
        
//        tabbedPane.addMouseListener(clickListener);
        this.add(tabbedPane,BorderLayout.CENTER);
        this.setVisible(true);   
    }
    /**
     * Llamar antes de cambiar la pestaña
     */
    public void initPanelGrafico(){
        if(tabTexto.isTextoBienFormado()){ 
            this.tabGrafico.setVariables(tabTexto.getVariablesDelTexto()); 
        }
    }

    /**
     * Llamar antes de cambiar la pestaña
     */
    public void initPanelInstancias(){
        ArrayList<QsVariable> listaVariables = new ArrayList<QsVariable>();
        for(Item item : tabTexto.getVariablesDelTexto()){
            QsVariable var = new QsVariable(this.tabGrafico.getDAD(), 50, 0, item.getNumeration(),item.getCadenaDeTexto(),item.getNumeroDeLinea());
            listaVariables.add(var);
        }
        Map<String, ArrayList<QsNodo>>  relPadreHijos = this.tabGrafico.getDAD().getRelPadreHijos();
        Map<String, QsOperador> opers = this.tabGrafico.getDAD().getOperadores();
        Map<String, QsVariable> variables = this.tabGrafico.getDAD().getVariables();
        
        this.tabInstancias.setDAD(this.tabGrafico.getDAD());
        this.tabInstancias.setListaVariables(listaVariables); // Asigno lista de variables ordenadas para muestra por pantalla
        this.tabInstancias.setRelPadreHijos(relPadreHijos);
        this.tabInstancias.setOperadores(opers);
        this.tabInstancias.setVariables(variables);
        this.tabInstancias.setInit(true);
        this.tabInstancias.repaint();
    }
    

    public static boolean isTURN_OFF_LISTENERS() {
        return TURN_OFF_LISTENERS;
    }

    public static void setTURN_OFF_LISTENERS(boolean TURN_OFF_LISTENERS) {
        QsFrame.TURN_OFF_LISTENERS = TURN_OFF_LISTENERS;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }
    
    public int getIndiceActual(){
        return this.indiceActual;
    }

    public void setIndiceActual(int indiceAnterior) {
        this.indiceActual = indiceAnterior;
    }
}
