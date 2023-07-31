package ar.unsl.qualisys.frames;
 
import ar.unsl.qualisys.componentes.MaterialTabbed;
import ar.unsl.qualisys.componentes.QsMenuSuperior;
import ar.unsl.qualisys.componentes.nodos.QualyVariable;
import ar.unsl.qualisys.paneles.DragAndDropVariablesAndOperandsPanel;
import ar.unsl.qualisys.paneles.PanelTexto;
import ar.unsl.qualisys.paneles.QsTabPanel;
import ar.unsl.qualisys.componentes.QsTextPanel;
import ar.unsl.qualisys.paneles.QualyGraphicPanel;
import ar.unsl.qualisys.utils.ValorInstancias;
import com.raven.chart.Chart;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.raven.chart.ModelChart;
import javaswingdev.form.Form_Dashboard;
import javaswingdev.swing.RoundPanel;
import javaswingdev.swing.table.Table;
/**
 *          VENTANA
 * @author luciano.gurruchaga
 */
public class QsFrame extends JFrame{
    
    public QsFrame(){
        this.setBounds(200,200,1300,700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
              JTabbedPane tabbedPane = new MaterialTabbed();
        
        this.setLayout(new BorderLayout());
        this.add(new QsMenuSuperior(),BorderLayout.NORTH); 
        JPanel panel1, panel2, panel3, panel4, panel5;
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel4.add(createChart());
        
        tabbedPane.addTab("Variables de Preferencia", new QsTextPanel());
        tabbedPane.addTab("Árbol ", new QualyGraphicPanel());
        tabbedPane.addTab("Llenado de Instancias ", new ValorInstancias());
        tabbedPane.addTab("Resultados ", panel4);
        tabbedPane.setBackground(Color.decode("#F09757"));
        JPanel paneel = new RoundPanel();
        paneel.add(new Form_Dashboard());
        tabbedPane.addTab("Resultados 5", paneel);
            //this.add(new Table());

        this.add(tabbedPane,BorderLayout.CENTER);
        this.setVisible(true);  

// this.add(new PanelTexto());
      //  this.add(new QualyOperatorsPanel());
      
         //QualyVariable[] arr = new QualyVariable[10];
        // arr[0] = new QualyVariable("NOMBRE", 1f);
        
     //   this.add(new QualyGraphicPanel());
      
      
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

}
