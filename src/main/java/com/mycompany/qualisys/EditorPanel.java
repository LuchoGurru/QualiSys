package com.mycompany.qualisys;




import GUIComponents.GUINodos.QualyVariable;
import GUIComponents.Laminas.DragAndDropVariablesAndOperandsPanel;
import GUIComponents.Laminas.QualyGraphicPanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class EditorPanel extends JFrame{
    public EditorPanel(){
        this.setSize(1300,900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       // this.add(new PanelTexto());
      //  this.add(new QualyOperatorsPanel());
      
         QualyVariable[] arr = new QualyVariable[10];
         arr[0] = new QualyVariable("NOMBRE", 1f);
      //  this.add(new DragAndDropVariablesAndOperandsPanel(arr));
        this.add(new QualyGraphicPanel());
      this.setVisible(true);
      
    }

    /*  private JPanel panel1;
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
