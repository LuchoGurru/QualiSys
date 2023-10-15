/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.unsl.qualisys.paneles;

import GUIUtils.CustomTableModel;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import GUIUtils.TableCustom;
import LSP.QsInstancia;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsEvaluacionPanel extends javax.swing.JPanel {
    private ArrayList<QsVariable> vars;
    private ArrayList<QsInstancia> instancias;
   //private HashMap<String,ArrayList<Float>> instancias;
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private int instanciaSeleccionada;
    /**
     * Creates new form ValorInstancias
     */
    public QsEvaluacionPanel(JFrame parent) {
        initComponents();
        this.vars = new ArrayList<>();
        this.instancias = new ArrayList<>();
        
        //Tabla de instancias
        
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable(new CustomTableModel());
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTable1);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
      //  model.addColumn("Variables :");
        jTable1.setModel(toTableModel());
        
        
        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jScrollPane1,BorderLayout.CENTER);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
             //  int row = jTable1.rowAtPoint(evt.getPoint());
               int col = jTable1.columnAtPoint(evt.getPoint());
                System.out.println(col);
               if ( col > 0 ) {
                    instanciaSeleccionada = col;
               }
               int[] selectedColumns = jTable1.getSelectedColumns();
                for (int c : selectedColumns) {
                    TableColumn column = jTable1.getColumnModel().getColumn(c);
                    // Realiza operaciones en la columna seleccionada, por ejemplo:
                    // column.setMinWidth(50); // Establece el ancho mínimo de la columna
                    // column.setMaxWidth(200); // Establece el ancho máximo de la columna
                }
            }
        });
        //Tabla de evaluacion de resultados
        
        jScrollPane2 = new JScrollPane();
        jTable2 = new JTable(new CustomTableModel());
        jTable2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(jTable2);
        DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
        model2.addColumn("Instancias :");
        model2.addColumn("Resultados :");
        TableCustom.apply(jScrollPane2, TableCustom.TableType.MULTI_LINE); //UI
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(jScrollPane2,BorderLayout.CENTER);
        
      //  jButtonModif.setEnabled(false);
      //  jButtonElim.setEnabled(false);

        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        initData();
    }
    
    public ArrayList<QsVariable> getVars(){
        return vars;
    }

    public void setVars(ArrayList<QsVariable> vars) {
        this.vars = vars;
        
        this.repaint();
    }


    public TableModel toTableModel() {
        System.out.println("Using toTableModel");
        int cols = this.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.instancias.get(i).getNombre();
        }
        DefaultTableModel tmodel = new DefaultTableModel(
                columnas, 0);
        
        for (int i = 0; i < this.vars.size(); i++) {
            Object[] fila = new Object[cols]; 
            for(int j=0;j<cols;j++){
                 if(j==0){
                     fila[j] = this.vars.get(i);
                 }else{
                     fila[j] = this.instancias.get(j).getValores().get(i);
                 }    
             }
            tmodel.addRow(fila);           
        }
        
        
       
        return tmodel;
    }
    
    
    
    /*{
    Control de celda on focus lost que no se pierda si esta mal el valor, o reemplazar por cero 
            
     }*/
    public void agregarValor(Float flo, int instancia,int variable){

     
        
        
    }
    
    private void crearInstancia(String nombre){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addColumn(nombre);
        QsInstancia nuevaI = new QsInstancia(nombre, new ArrayList<>());
        for(int i = 0 ; i < this.vars.size();i++){
            nuevaI.getValores().add(0f);
            model.setValueAt(0f, i, model.getColumnCount()-1); // Set "New York" in the first row, third column
        }
        this.instancias.add(nuevaI);
    }
    private void eliminarInstancia(){
        if(instanciaSeleccionada > 0 ){
            TableColumnModel columnasModel = jTable1.getColumnModel();
              int[] selectedColumns = jTable1.getSelectedColumns();
            for (int col : selectedColumns){ 
                TableColumn column = columnasModel.getColumn(col);
                columnasModel.removeColumn(column);
                jTable1.removeColumn(column);

                // Realiza operaciones en la columna seleccionada, por ejemplo:
                // column.setMinWidth(50); // Establece el ancho mínimo de la columna
                // column.setMaxWidth(200); // Establece el ancho máximo de la columna
                }
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una instancia de la tabla.");
        }
    }
        private void modificarInstancia(String nuevoNombre){
        if(instanciaSeleccionada > 0 ){
            TableColumnModel columnasModel = jTable1.getColumnModel();
            int[] selectedColumns = jTable1.getSelectedColumns();
            for (int col : selectedColumns){ 
                TableColumn column = columnasModel.getColumn(col);
                column.setHeaderValue(nuevoNombre);
                // Realiza operaciones en la columna seleccionada, por ejemplo:
                // column.setMinWidth(50); // Establece el ancho mínimo de la columna
                // column.setMaxWidth(200); // Establece el ancho máximo de la columna
                }
            jTable1.getTableHeader().repaint();
        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una instancia de la tabla.");
        }
    }
    private void initData() {
        int cantVars = vars.size();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        
        if (model.getRowCount() > 0) { // Elimino filas
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
        
        for(QsVariable v: this.vars){
            Object[] fila = new Object[]{v.getNombre()};
            model.addRow(fila);
        }
        /*model.addRow(new Object[]{1, "Chai", "Beverages", 18, 39});
        model.addRow(new Object[]{2, "Chang", "Beverages", 19, 39});
        model.addRow(new Object[]{3, "Aniseed Syrup", "Beverages", 18, 39});
        model.addRow(new Object[]{4, "Chef Anton's Cajun Seasoning", "Beverages", 19, 39});
        model.addRow(new Object[]{5, "Chef Anton's Gumbo Mix", "Beverages", 18, 39});
        model.addRow(new Object[]{6, "Grandma's Boysenberry Spread", "Beverages", 19, 39});
        model.addRow(new Object[]{7, "Uncle Bob's Organic Dried Pears", "Beverages", 18, 39});
        model.addRow(new Object[]{8, "Northwoods Cranberry Sauce", "Beverages", 19, 39});
        model.addRow(new Object[]{9, "Mishi Kobe Niku", "Beverages", 18, 39});
        model.addRow(new Object[]{10, "Ikura", "Beverages", 19, 39});
        model.addRow(new Object[]{11, "Queso Cabrales", "Beverages", 18, 39});
        model.addRow(new Object[]{12, "Queso Manchego La Pastora", "Beverages", 19, 39});
        model.addRow(new Object[]{13, "Konbu", "Beverages", 18, 39});
        model.addRow(new Object[]{14, "Tofu", "Seafood", 19, 39});
        model.addRow(new Object[]{15, "Genen Shouyu", "Seafood", 18, 39});
        model.addRow(new Object[]{16, "Pavlova", "Seafood", 19, 39});
        model.addRow(new Object[]{17, "Alice Mutton", "Seafood", 18, 39});
        model.addRow(new Object[]{18, "Carnarvon Tigers", "Seafood", 19, 39});
        model.addRow(new Object[]{19, "Teatime Chocolate Biscuits", "Seafood", 19, 39});
        model.addRow(new Object[]{20, "Sir Rodney's Marmalade", "Seafood", 19, 39});
        model.addRow(new Object[]{21, "Sir Rodney's Scones", "Seafood", 19, 39});
        model.addRow(new Object[]{22, "Gustaf's Knäckebröd", "Seafood", 19, 39});
        model.addRow(new Object[]{23, "Tunnbröd", "Seafood", 19, 39});
        model.addRow(new Object[]{24, "Guaraná Fantástica", "Seafood", 19, 39});
        model.addRow(new Object[]{25, "NuNuCa Nuß-Nougat-Creme", "Seafood", 19, 39});
        model.addRow(new Object[]{26, "Gumbär Gummibärchen", "Seafood", 19, 39});
        model.addRow(new Object[]{27, "Schoggi Schokolade", "Seafood", 19, 39});
        model.addRow(new Object[]{28, "Rössle Sauerkraut", "Seafood", 19, 39});
        model.addRow(new Object[]{29, "Thüringer Rostbratwurst", "Seafood", 19, 39});
        model.addRow(new Object[]{30, "Nord-Ost Matjeshering", "Seafood", 19, 39});
        model.addRow(new Object[]{31, "Gorgonzola Telino", "Seafood", 19, 39});
        model.addRow(new Object[]{32, "Mascarpone Fabioli", "Seafood", 19, 39});
        model.addRow(new Object[]{33, "Geitost", "Seafood", 19, 39});
        model.addRow(new Object[]{34, "Sasquatch Ale", "Seafood", 19, 39});
        model.addRow(new Object[]{35, "Steeleye Stout", "Seafood", 19, 39});
        model.addRow(new Object[]{36, "Inlagd Sill", "Seafood", 19, 39});
        model.addRow(new Object[]{37, "Gravad lax", "Seafood", 19, 39});
        model.addRow(new Object[]{38, "Côte de Blaye", "Seafood", 19, 39});
        model.addRow(new Object[]{39, "Chartreuse verte", "Seafood", 19, 39});
        model.addRow(new Object[]{40, "Boston Crab Meat", "Seafood", 19, 39});
        model.addRow(new Object[]{41, "Jack's New England Clam Chowder", "Seafood", 19, 39});
        model.addRow(new Object[]{42, "Singaporean Hokkien Fried Mee", "Seafood", 19, 39});
        model.addRow(new Object[]{43, "Ipoh Coffee", "Seafood", 19, 39});
        */
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel0 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButtonModif = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonElim = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        jPanel0.setLayout(null);

        jLabel1.setText("Agregue los modelos y valores que desee.");
        jPanel0.add(jLabel1);
        jLabel1.setBounds(6, 6, 463, 16);

        jButton1.setText("Crear Instancia");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel0.add(jButton1);
        jButton1.setBounds(10, 110, 150, 29);

        jButton2.setText("Ver Resultados");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel0.add(jButton2);
        jButton2.setBounds(12, 186, 132, 32);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel0.add(jTextField1);
        jTextField1.setBounds(10, 70, 470, 29);

        jButtonModif.setText("Modificar Instancia");
        jButtonModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifActionPerformed(evt);
            }
        });
        jPanel0.add(jButtonModif);
        jButtonModif.setBounds(170, 110, 150, 30);
        jPanel0.add(jSeparator1);
        jSeparator1.setBounds(6, 158, 469, 22);

        jButtonElim.setText("Eliminar Instancia");
        jButtonElim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonElimActionPerformed(evt);
            }
        });
        jPanel0.add(jButtonElim);
        jButtonElim.setBounds(330, 110, 150, 30);

        jLabel3.setText("Debe haber al menos un Modelo a evaluar. Revise sus variables.");
        jPanel0.add(jLabel3);
        jLabel3.setBounds(12, 242, 457, 16);

        jButton5.setText("Expoprtar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel0.add(jButton5);
        jButton5.setBounds(12, 632, 132, 32);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 449, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 356, Short.MAX_VALUE)
        );

        jPanel0.add(jPanel2);
        jPanel2.setBounds(12, 270, 449, 356);

        jLabel2.setText("Nombre de la Instancia");
        jPanel0.add(jLabel2);
        jLabel2.setBounds(12, 32, 457, 20);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 750, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 682, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel0, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(14, 14, 14))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        /*Object[][] data = {
            {"Row 1, Col 1", "Row 1, Col 2"},
            {"Row 2, Col 1", "Row 2, Col 2"},
            {"Row 3, Col 1", "Row 3, Col 2"}
        };
        // Column names
        String[] columnNames = {"Column 1", "Column 2"};        
        model.setColumnIdentifiers(columnNames);
        model.setDataVector(data, columnNames);*/
        crearInstancia(jTextField1.getText());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButtonElimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonElimActionPerformed
        eliminarInstancia();
    }//GEN-LAST:event_jButtonElimActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButtonModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifActionPerformed
        modificarInstancia(jTextField1.getText());
    }//GEN-LAST:event_jButtonModifActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonElim;
    private javax.swing.JButton jButtonModif;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel0;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
