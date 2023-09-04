package ar.unsl.qualisys.frames;
 
import ar.unsl.qualisys.componentes.MaterialTabbed;
import ar.unsl.qualisys.componentes.NewJDialog;
import ar.unsl.qualisys.componentes.QsMenuSuperior;
import ar.unsl.qualisys.componentes.QsModalPreview;
import ar.unsl.qualisys.componentes.nodos.QualyVariable;
import ar.unsl.qualisys.paneles.QsDadPanel;
import ar.unsl.qualisys.paneles.PanelTexto;
import ar.unsl.qualisys.paneles.QsTabPanel;
import ar.unsl.qualisys.componentes.QsTextPanel;
import ar.unsl.qualisys.paneles.QualyGraphicPanel;
import ar.unsl.qualisys.utils.Item;
import ar.unsl.qualisys.utils.ValorInstancias;
import com.raven.chart.Chart;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.raven.chart.ModelChart;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javaswingdev.form.Form_Dashboard;
import javaswingdev.swing.RoundPanel;
import javaswingdev.swing.table.Table;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *          VENTANA
 * @author luciano.gurruchaga
 */
public class QsFrame extends JFrame{
    
    private static boolean TURN_OFF_LISTENERS = false;    //ajjaja

    private QsTextPanel panelDeTexto; // panel donde se forma la estructura de variables
    private QualyGraphicPanel panelGrafico; // panel grafico donde se forma el árbol de preferencias
    private JPanel panelDeInstanciado;
    private JPanel panelDelUtilidades; // Estadisticas TODO Opcion dejar como visual como propuesta de escalabilidad;
    private int indiceActual;
    private JTabbedPane tabbedPane = new MaterialTabbed();
    
    public ArrayList<Item> g_variables;
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
        panelDeTexto = new QsTextPanel(this);
        panelGrafico = new QualyGraphicPanel(this);
        this.add(new QsMenuSuperior(panelDeTexto),BorderLayout.NORTH); 
        JPanel panel4;
        panel4 = new JPanel();
        panel4.add(createChart());     
        JPanel paneel = new RoundPanel();
        paneel.add(new Form_Dashboard());
        tabbedPane.addTab("Variables de Preferencia", panelDeTexto);
        tabbedPane.addTab("Árbol ", panelGrafico);
        tabbedPane.addTab("Llenado de Instancias ", new ValorInstancias());
        tabbedPane.addTab("Resultados ", panel4);
        //tabbedPane.setBackground(Color.decode("#F09757"));
        tabbedPane.addTab("Resultados 5", paneel);

        //LISTENER
        indiceActual = 0 ; //indice anterior();

   // Crear un MouseAdapter para el JTabbedPane
        MouseAdapter clickListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouse clicked getSelectedIndice"+ tabbedPane.getSelectedIndex());                // No hacer nada cuando se hace clic en el JTabbedPane
            //    int nuevoIndice = tabbedPane.getSelectedIndex();
              //  tabbedPane.setSelectedIndex(indiceAnterior);
                //indiceAnterior = indicePrevio;
            }
        };
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
        
        tabbedPane.addMouseListener(clickListener);
        this.add(tabbedPane,BorderLayout.CENTER);
        this.setVisible(true);   
    }
 
private Chart createChart (){
        Chart chart = new com.raven.chart.Chart();

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
        if(panelDeTexto.isTextoBienFormado()){
            this.panelGrafico.setVariables(panelDeTexto.getRenglones());
            
        }
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
