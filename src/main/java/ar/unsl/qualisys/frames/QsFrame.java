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
 
    private Chart createChart (){
        Chart chart = new GUIUtils.Chart();

        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(250, 250, 250));
        chart.addLegend("Income", new Color(245, 189, 135));
        chart.addLegend("Expense", new Color(135, 189, 245));
        chart.addLegend("Profit", new Color(189, 135, 245));
        chart.addLegend("Cost", new Color(139, 229, 222));
        chart.addData(new ModelChart("January", new double[]{500, 200, 80,89}));
        chart.addData(new ModelChart("February", new double[]{600, 750, 90,150}));
        chart.addData(new ModelChart("March", new double[]{200, 350, 460,900}));
        chart.addData(new ModelChart("April", new double[]{480, 150, 750,700}));
        chart.addData(new ModelChart("May", new double[]{350, 540, 300,150}));
        chart.addData(new ModelChart("June", new double[]{190, 280, 81,200}));
        return chart;
}

    public static boolean isTURN_OFF_LISTENERS() {
        return TURN_OFF_LISTENERS;
    }

    public static void setTURN_OFF_LISTENERS(boolean TURN_OFF_LISTENERS) {
        QsFrame.TURN_OFF_LISTENERS = TURN_OFF_LISTENERS;
    }
    /**
     * Llamar antes de cambiar la pestaña
     */
    public void initPanelGrafico(){
        if(tabTexto.isTextoBienFormado()){ 
            this.tabGrafico.setVariables(tabTexto.getVariables()); 
        }
    }

    /**
     * Llamar antes de cambiar la pestaña
     */
    public void initPanelInstancias(){
        //ArrayList<QsVariable> vars = new ArrayList<QsVariable>(this.tabGrafico.getDAD().getVariables().values()) ; 
        //this.tabInstancias.setVars(vars);
        ArrayList<QsVariable> vars = new ArrayList<QsVariable>();
        for(Item item : tabTexto.getVariables()){
            QsVariable var = new QsVariable(this.tabGrafico.getDAD(), 50, 0, item.getNumeration(),item.getCadenaDeTexto(),item.getNumeroDeLinea());
            vars.add(var);
        }
        Map<String, ArrayList<QsNodo>>  relPadreHijos = this.tabGrafico.getDAD().getRelPadreHijos();
        Map<String, QsOperador> opers = this.tabGrafico.getDAD().getOperadores();
        Map<String, QsVariable> variablesMap = this.tabGrafico.getDAD().getVariables();
        this.tabInstancias.setRelPadreHijos(relPadreHijos);
        this.tabInstancias.setOperadores(opers);
        this.tabInstancias.setVars(vars); // Asigno lista de variables ordenadas para muestra por pantalla
        this.tabInstancias.setVariablesMap(variablesMap);
        this.tabInstancias.setDAD(this.tabGrafico.getDAD());
    }

    /*
        private JPanel panel1;  
        private JButton button1;
        private JTabbedPane JTParchivo;
        private JTextPane textPane1;
        private JTextArea textArea1;

        public EditorPanel(){
            this.setContentPane(new EditorPanel().panel1);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.pack();
            this.setVisible(true);
        }
    */

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
