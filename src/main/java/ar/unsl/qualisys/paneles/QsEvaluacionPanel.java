/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ar.unsl.qualisys.paneles;

import GUIUtils.CustomTableModel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import GUIUtils.TableCustom;
import GUIUtils.TableHeaderCustomCellRender;
import LSP.QsInstancia;
import ar.unsl.qualisys.paneles.ModalTablaHijos;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.componentes.tabla.*;
import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsEvaluacionPanel extends javax.swing.JPanel {
    private ArrayList<QsVariable> vars;
    private Map<String, QsVariable> variablesMap;
    private ArrayList<QsInstancia> instancias;
    private Map<String, QsOperador> operadores = new HashMap<String, QsOperador>();
    private Map<String, ArrayList<QsNodo>> relPadreHijos = new HashMap<String, ArrayList<QsNodo>>();

    ArrayList<String> orderOper = null;
        
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableInstancias;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableResultados;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableResultadosParciales;
    private int instanciaSeleccionada;
    private boolean init;
    
        private QsDadPanel DAD;

    
    /**
     * Creates new form ValorInstancias
     */
    public QsEvaluacionPanel(JFrame parent) {
        System.out.println("Constructor1");
        initComponents();
        System.out.println("constructor2");
        this.vars = new ArrayList<>();
        this.instancias = new ArrayList<>();
        this.init = true;
        this.initTablaInstancias();
        this.initTablaResultados();
        this.initTableResultadosParciales();
        this.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.init)   
            initData(); 
        init = false;
        System.out.println("initData");
        //tablaSettings(jTable1,-1);
        
    }

    public QsDadPanel getDAD() {
        return DAD;
    }

    public void setDAD(QsDadPanel DAD) {
        this.DAD = DAD;
    }
    
    private boolean isVariable(QsNodo var){
        return var.getClass() == QsVariable.class;
    }
    
    private void initTablaInstancias(){
        //Tabla de instancias 
        jScrollPane1 = new JScrollPane();
        jTableInstancias = new JTable(new CustomTableModel()); // para deshabilitar la edicion de la primer columna
        jTableInstancias.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTableInstancias);
        DefaultTableModel model = (DefaultTableModel) jTableInstancias.getModel();

        jTableInstancias.setModel(instanciasTableModel());
        
        jTableInstancias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTableInstancias.setColumnSelectionAllowed(true);
        jTableInstancias.setRowSelectionAllowed(true);

        TableCustom.apply(jScrollPane1, TableCustom.TableType.MULTI_LINE);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jScrollPane1,BorderLayout.CENTER);
        jTableInstancias.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
             //  int row = jTable1.rowAtPoint(evt.getPoint());
               int col = jTableInstancias.columnAtPoint(evt.getPoint());
                System.out.println(col);
               if ( col > 0 ) {
                    instanciaSeleccionada = col;
               }
               int[] selectedColumns = jTableInstancias.getSelectedColumns();
                for (int c : selectedColumns) {
                    TableColumn column = jTableInstancias.getColumnModel().getColumn(c);
                    // Realiza operaciones en la columna seleccionada, por ejemplo:
                    // column.setMinWidth(50); // Establece el ancho mínimo de la columna
                    // column.setMaxWidth(200); // Establece el ancho máximo de la columna
                }
            }
        });
    }
    
    private void initTablaResultados(){

        //Tabla de evaluacion de resultados
        jScrollPane2 = new JScrollPane();
        jTableResultados = new JTable(new CustomTableModel());
        jTableResultados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(jTableResultados);
        DefaultTableModel model2 = (DefaultTableModel) jTableResultados.getModel();
        model2.addColumn("Instancias :");
        model2.addColumn("Resultados :");
        model2.addColumn("Acciones :");
        //TableCustom.apply(jScrollPane2, TableCustom.TableType.DEFAULT);
        jTableResultados.getTableHeader().setBackground(new Color(250, 250, 250));
        jTableResultados.getTableHeader().setReorderingAllowed(false);
        jTableResultados.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(jTableResultados));
        jPanel4.add(jScrollPane2);
        jPanel4.setLayout(new BorderLayout());      
        jPanel4.add(jScrollPane2,BorderLayout.CENTER);
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
                System.out.println("Edit row : " + row);
                
            }

            @Override
            public void onDelete(int row) {
                if (jTableResultados.isEditing()) {
                    jTableResultados.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) jTableResultados.getModel();
                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                mostrarResultadosParciales(row);
                
                
                
            }
        }; 
        tablaSettings(jTableResultados,2,event);

    }
    
    public void initTablaModal(int row) {
        JTable table = new JTable();
            DefaultTableModel model = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Hijo", "Ponderación", "Retorno"}
            );
            String operFilaId = this.orderOper.get(row);
            ArrayList<QsNodo> hijos = this.relPadreHijos.get(operFilaId);
        //Filas
        int cols = model.getColumnCount();
        
        for (int i = 0; i < hijos.size(); i++) {
            Object[] fila = new Object[cols]; 
            QsNodo hijoFila = hijos.get(i);
            if(isVariable(hijoFila)){
                QsVariable hijoVar = (QsVariable) hijoFila;
                fila[0] = hijoVar.getName() + ": " + hijoVar.getDescripcion();
                fila[1] = hijoVar.getPonderacion(); 
                fila[2] = hijoVar.getValorResultado(); 
            }else{
                QsOperador hijoOp = (QsOperador) hijoFila;
                fila[0] = hijoOp.getSymbol() + ": " + hijoOp.getNombre();
                fila[1] = hijoOp.getPonderacion();
                fila[2] = hijoOp.getValorResultado();
            }
            model.addRow(fila);    
        }// Crear el diálogo modal
                    table.setModel(model);

            JDialog dialog = new JDialog();
            dialog.setLayout(new BorderLayout());
            Container contenedor = this.DAD.getParent();
             JScrollPane scrollPanel = new JScrollPane(DAD);
             Dimension viewportSize = scrollPanel.getViewport().getSize();

        // Calcular la posición dentro del componente grande que deseas centrar
        System.out.println(viewportSize.height + " " + viewportSize.width);
        QsOperador op = this.operadores.get(operFilaId);
        DAD.setNodoSeleccionado(op);
        int x = op.getLocation().x; // coordenada x deseada
        int y = op.getLocation().y; // coordenada y deseada// Ajustar la posición de desplazamiento del JScrollPane para que la posición deseada esté centrada en la vista
//        int newX = Math.max(0, x - viewportSize.width / 2);
//        int newY = Math.max(0, y - viewportSize.height / 2);

         scrollPanel.getViewport().setViewPosition(new Point(x-100, y ));
                                dialog.add(new JScrollPane(table), BorderLayout.WEST); // Añadir la tabla a un JScrollPane
                               dialog.add(scrollPanel, BorderLayout.CENTER); // Añadir la tabla a un JScrollPane
            dialog.setTitle("Detalles del nodo : dominio");
            dialog.setSize(800, 600); // Establecer tamaño
            dialog.setLocationRelativeTo(null); // Centrar en la pantalla
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Cerrar al hacer clic en la cruz
            dialog.setFocusable(true); // Establecer enfoque
            dialog.requestFocusInWindow(); // Solicitar enfoque
            dialog.setAlwaysOnTop(true);
            dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setEditableNodos(true);
                contenedor.remove(DAD); // Remove panel from its original container
               contenedor.add(DAD); // Add panel to the new container
               contenedor.revalidate(); // Revalidate the new container to reflect changes
               contenedor.repaint();
                System.out.println("habilitao");
             }
            
            public void windowOpened(WindowEvent e) {
                setEditableNodos(false);

                 System.out.println("deshabilitao");             }
        });
            
            
            
            dialog.setVisible(true);

    } 
    
    public void setEditableNodos(boolean editable) {
    ArrayList<String> nodos = new ArrayList<>(this.operadores.keySet()); // copiade operadores a evaluar
        for (int i = 0; i < nodos.size(); i++) {
            this.operadores.get(nodos.get(i)).setEditable(editable);
        }
    nodos = new ArrayList<>(this.variablesMap.keySet()); // copiade operadores a evaluar
        for (int i = 0; i < nodos.size(); i++) {
            this.variablesMap.get(nodos.get(i)).setEditable(editable);
        }    
    }
    
    private void initTableResultadosParciales(){    
        jScrollPane3 = new JScrollPane();
        jTableResultadosParciales = new JTable(new CustomTableModel());
        jTableResultadosParciales.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane3.setViewportView(jTableResultadosParciales);
        DefaultTableModel model3 = (DefaultTableModel) jTableResultadosParciales.getModel();
        model3.addColumn("Operador :");
        model3.addColumn("Resultados-Valor :");
        model3.addColumn("Dominio(hijos) :");  
        jTableResultadosParciales.getTableHeader().setBackground(new Color(250, 250, 250));
        jTableResultadosParciales.getTableHeader().setReorderingAllowed(false);
        jTableResultadosParciales.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(jTableResultados));
        
        jPanel5.add(jScrollPane3);
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(jScrollPane3,BorderLayout.CENTER);
        
        TableActionEvent event = new TableActionEvent() {
            @Override
            public void onEdit(int row) {
            // Crear y configurar la tabla
            }

            @Override
            public void onDelete(int row) {
                if (jTableResultadosParciales.isEditing()) {
                    jTableResultadosParciales.getCellEditor().stopCellEditing();
                }
                DefaultTableModel model = (DefaultTableModel) jTableResultadosParciales.getModel();
                model.removeRow(row);
            }

            @Override
            public void onView(int row) {
                System.out.println("View row : " + row);
                               initTablaModal(row);

                
                
            }
        }; 
        tablaSettings(jTableResultadosParciales,2,event);
     }
    public void tablaSettings(JTable table, int actionColumn,TableActionEvent event){
        table.setRowHeight(35);
        table.setSelectionBackground(new java.awt.Color(56, 138, 112));
        
        if(actionColumn<0){   
        }else{
            table.getColumnModel().getColumn(2).setCellRenderer(new TableActionCellRender());
            table.getColumnModel().getColumn(2).setCellEditor(new TableActionCellEditor(event));
        }
        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable jtable, Object o, boolean bln, boolean bln1, int i, int i1) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                return super.getTableCellRendererComponent(jtable, o, bln, bln1, i, i1);
            }
        });
    }
    
    public void reinicializarOperadores(){
        ArrayList<String> ops = new ArrayList<>(this.operadores.keySet()); // copiade operadores a evaluar
        for (int i = 0; i < ops.size(); i++) {
            this.operadores.get(ops.get(i)).setValorResultado(-1d);
        }
   }
    
    public void mostrarResultadosParciales(int instanciaSeleccionada){
        //QsInstancia instancia = this.instancias.get(instanciaSeleccionada -1);
        ArrayList<String> ops = new ArrayList<>(this.operadores.keySet()); // copiade operadores a evaluar
        asignarInstancia(instanciaSeleccionada);
        reinicializarOperadores();
        calcularFuncion(true);
       
        DefaultTableModel model = (DefaultTableModel) jTableResultadosParciales.getModel();
        model.setRowCount(0);
        //Filas
        int cols = model.getColumnCount();
        
        for (int i = 0; i < ops.size(); i++) {
            String ordenOp = orderOper.get(i);
            Object[] fila = new Object[cols]; 
            fila[0] = this.operadores.get(ordenOp).getSymbol() + ":" +this.operadores.get(ordenOp).getNombre();
            fila[1] = this.operadores.get(ordenOp).getValorResultado();
            fila[2] = this.operadores.get(ordenOp).getName(); // no hace falta por que el render de la tabla lo complleta con los botones ... 
            model.addRow(fila);           
        }       
    }
    
    
    public ArrayList<QsVariable> getVars(){
        return vars;
    }

    public void setVars(ArrayList<QsVariable> vars) {
        this.vars = vars;
        this.repaint();
    }

    public Map<String, QsVariable> getVariablesMap() {
        return variablesMap;
    }

    public void setVariablesMap(Map<String, QsVariable> variablesMap) {
        this.variablesMap = variablesMap;
    }
    
    public Map<String, QsOperador> getOperadores() {
        return operadores;
    }

    public void setOperadores(Map<String, QsOperador> operadores) {
        this.operadores = operadores;
    }
    
    public Map<String, ArrayList<QsNodo>> getRelPadreHijos() {
        return relPadreHijos;
    }

    public void setRelPadreHijos(Map<String, ArrayList<QsNodo>> relPadreHijos) {
        this.relPadreHijos = relPadreHijos;
    }
    
    public TableModel instanciasTableModel() {
        System.out.println("Using toTableModel");
        int cols = this.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        //Columnas
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.instancias.get(i).getNombre();
        }
        DefaultTableModel tmodel = new DefaultTableModel(
                columnas, 0);
        //Filas
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
    
    public void mostrarResultados(double[] resultados) {
        DefaultTableModel model = (DefaultTableModel) jTableResultados.getModel();
        model.setRowCount(0);
        //Filas
        int cols = model.getColumnCount();
        for (int i = 0; i < this.instancias.size(); i++) {
            Object[] fila = new Object[cols]; 
            fila[0] = this.instancias.get(i).getNombre();
            fila[1] = resultados[i];
//                fila[2] = ""; // no hace falta por que el render de la tabla lo complleta con los botones ... 
            model.addRow(fila);           
        }
        //return model;
    }
    
    /**
     * Asigna la instancia a las variables a evaluar, en toria si sigue vigente la referencia ya estaria resuelto
     */
    public void asignarInstancia(int instancia){
        ArrayList<Double> valores =  this.instancias.get(instancia).getValores();
        for(int i = 0 ; i < valores.size() ; i++){
            QsVariable qsv = this.vars.get(i);
            String nameID = qsv.getName();
            this.variablesMap.get(nameID).setValorResultado(valores.get(i));
        }
    }
    
    /*
    
    {
        Control de celda on focus lost que no se pierda si esta mal el valor, o reemplazar por cero 
    }
    
    */
    public void agregarValor(Double dou, int instancia,int variable){
        this.instancias.get(instancia -1 ).getValores().set(variable, dou); // -1 para respetar el arreglo por la columna q sobra al principio
    }
    
    private void crearInstancia(String nombre){
        QsInstancia nuevaI = new QsInstancia(nombre, new ArrayList<>());
        for(int i = 0 ; i < this.vars.size();i++){
            nuevaI.getValores().add(0d);
        }
        this.instancias.add(nuevaI);
        int cols = this.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.instancias.get(i-1).getNombre();
        }        
        
        DefaultTableModel tmodel = new DefaultTableModel(
                columnas, 0);
        //Filas
        for (int i = 0; i < this.vars.size(); i++) {
            Object[] fila = new Object[cols]; 
            for(int j=0;j<cols;j++){
                 if(j==0){
                     fila[j] = this.vars.get(i).getDescripcion();
                 }else{
                     fila[j] = this.instancias.get(j-1).getValores().get(i);
                 }    
             }
            tmodel.addRow(fila);            
        }
       
        jTableInstancias.setModel(tmodel);
        for (int columnIndex = 1; columnIndex < jTableInstancias.getColumnCount(); columnIndex++) {
            jTableInstancias.getColumnModel().getColumn(columnIndex).setCellEditor(new CustomCellEditor(new JTextField()));
        }

    }
    private void eliminarInstancia(){
        if(instanciaSeleccionada > 0 ){
        this.instancias.remove(instanciaSeleccionada -1 );
        int cols = this.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.instancias.get(i-1).getNombre();
        }        
        
        DefaultTableModel tmodel = new DefaultTableModel(
                columnas, 0);
        //Filas
        for (int i = 0; i < this.vars.size(); i++) {
            Object[] fila = new Object[cols]; 
            for(int j=0;j<cols;j++){
                 if(j==0){
                     fila[j] = this.vars.get(i).getDescripcion();
                 }else{
                     fila[j] = this.instancias.get(j-1).getValores().get(i);
                 }    
             }
            tmodel.addRow(fila);           
        }
        jTableInstancias.setModel(tmodel);
            JOptionPane.showMessageDialog(this, "Seleccione una instancia de la tabla.");
        }
    }
    private void modificarInstancia(String nombre){
        if(instanciaSeleccionada > 0 ){
        this.instancias.get(instanciaSeleccionada -1 ).setNombre(nombre);
        int cols = this.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.instancias.get(i-1).getNombre();
        }        
        
        DefaultTableModel tmodel = new DefaultTableModel(
                columnas, 0);
        //Filas
        for (int i = 0; i < this.vars.size(); i++) {
            Object[] fila = new Object[cols]; 
            for(int j=0;j<cols;j++){
                 if(j==0){
                     fila[j] = this.vars.get(i).getDescripcion();
                 }else{
                     fila[j] = this.instancias.get(j-1).getValores().get(i);
                 }    
             }
            tmodel.addRow(fila);           
        }
        jTableInstancias.setModel(tmodel);

        }else{
            JOptionPane.showMessageDialog(this, "Seleccione una instancia de la tabla.");
        }
    }
    private void initData() {
        int cantVars = vars.size();
        DefaultTableModel model = (DefaultTableModel) jTableInstancias.getModel();
        
        if (model.getRowCount() > 0) { // Elimino filas
            for (int i = model.getRowCount() - 1; i > -1; i--) {
                model.removeRow(i);
            }
        }
        
        for(QsVariable v: this.vars){
            Object[] fila = new Object[]{v.getDescripcion()};
            model.addRow(fila);
        }
    }

    public ArrayList<QsInstancia> getInstancias() {
        return instancias;
    }

    public void setInstancias(ArrayList<QsInstancia> instancias) {
        this.instancias = instancias;
    }
    
    public void evaluarResultados(){
        double[] resultados = new double[this.instancias.size()];
        for (int i = 0; i < this.instancias.size(); i++) {
            //this.relPadreHijos = null;
            reinicializarOperadores();
            asignarInstancia(i);
            resultados[i] = calcularFuncion(false);
        }
        mostrarResultados(resultados);
    }
/**
 * La variable debug es para mantener, o no, el orden en que los operadores se fueron calculandios
 * @param debug
 * @return 
 */
    public double calcularFuncion(boolean debug) {
        
        if(debug)
            orderOper = new ArrayList<>();
        else
            orderOper = null; 
        ArrayList<String> ops = new ArrayList<>(this.relPadreHijos.keySet()); // copiade operadores a evaluar
        boolean evaluar = true; // en teoria las variables ya estan setteadas por referencias en el mapa este ,,, pero hay que ver
        double resultado = -1d;
        while (ops.size() > 0) {
            System.out.println("infinite loop" + ops.size());
            int tam = ops.size();
            for (int j = 0; j < tam; j++) {
                QsOperador candidato = this.operadores.get(ops.get(j));
                System.out.println("candidato : " + candidato.getName() + candidato.getSymbol());
                ArrayList<QsNodo> hijos = this.relPadreHijos.get(candidato.getName());
                evaluar = true;
                for (QsNodo h : hijos) { // Checkear dominio valido
                    if (h.getValorResultado() < 0d) {
                        evaluar = false;
                        break;
                    }
                }
                // Si el dominio del operador es apto para evaluarse, se evalua y se elimina de la lista 
                if (evaluar) {
                    double resultadoFuncion = candidato.wpmFunction(hijos);
                    candidato.setValorResultado(resultadoFuncion);
                    // this.operadores.get(i).calcularOperacion(dominio); si el candidato es una referencia no haria falta hacerlo de esta manera
                    if(debug)
                       orderOper.add(candidato.getName());
                    ops.remove(j);// borro el operador ya calculado
                    //SI la lista se vacio significa que candidato es el Resultado final
                    if (ops.size() == 0) {
                        //Mostrar resultado
                        System.out.println("ops resultado = " + candidato.getValorResultado());
                        resultado = candidato.getValorResultado();
                    }
                    break;
                }
            }
        }
        if (resultado < 0) {
            System.out.println("Ha ocurrido un error en el calculo de la fuunción" + resultado);
        }else{
            System.out.println("Es bueno " + resultado);
        }
        return resultado;
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
        jTextField1 = new javax.swing.JTextField();
        jButtonModif = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jButtonElim = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1280, 1200));

        jPanel0.setLayout(null);

        jLabel1.setText("Agregue las instancias que desee.");
        jPanel0.add(jLabel1);
        jLabel1.setBounds(540, 0, 463, 17);

        jButton1.setText("Crear Instancia");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel0.add(jButton1);
        jButton1.setBounds(540, 30, 170, 29);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel0.add(jTextField1);
        jTextField1.setBounds(180, 30, 350, 29);

        jButtonModif.setText("Modificar Instancia");
        jButtonModif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifActionPerformed(evt);
            }
        });
        jPanel0.add(jButtonModif);
        jButtonModif.setBounds(720, 30, 170, 30);
        jPanel0.add(jSeparator1);
        jSeparator1.setBounds(10, 80, 520, 10);

        jButtonElim.setText("Eliminar Instancia");
        jButtonElim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonElimActionPerformed(evt);
            }
        });
        jPanel0.add(jButtonElim);
        jButtonElim.setBounds(900, 30, 170, 30);

        jLabel2.setText("Nombre de la Instancia");
        jPanel0.add(jLabel2);
        jLabel2.setBounds(12, 32, 457, 20);

        jLabel4.setText("Una vez seleccionada puede modificar o eliminar una instancia.");
        jPanel0.add(jLabel4);
        jLabel4.setBounds(540, 80, 520, 17);

        jLabel3.setText("Asigne valores a sus instancias.");
        jPanel0.add(jLabel3);
        jLabel3.setBounds(10, 100, 270, 17);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );

        jLabel5.setText("Evalúe sus resultados.");

        jButton3.setText("Ver Resultados");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel9.setText("Debe haber al menos un Modelo a evaluar. Revise sus variables.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(405, 405, 405)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(550, 550, 550)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))))
                .addContainerGap(359, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jLabel9))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 480, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel0, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(47, 47, 47))
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

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButtonElimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonElimActionPerformed
        eliminarInstancia();
    }//GEN-LAST:event_jButtonElimActionPerformed

    private void jButtonModifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifActionPerformed
        modificarInstancia(jTextField1.getText());
    }//GEN-LAST:event_jButtonModifActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        evaluarResultados();
    }//GEN-LAST:event_jButton3ActionPerformed

public class CustomCellEditor extends DefaultCellEditor {
    
    private boolean isValid = true;
    private int rowSelected=-1;
        public CustomCellEditor(JTextField textField) {
        super(textField);
    }

    @Override
    public boolean stopCellEditing() {
        // Cuando se detiene la edición, verifica si el valor es válido.
        String value = (String) getCellEditorValue();
        isValid = isValidValue(value);

        if (!isValid) {
             JOptionPane.showMessageDialog(null,"Debe ingresar un valor real.");
            // La celda no es válida, no permitas que se detenga la edición.
            return false;
        }
        agregarValor(Double.valueOf(value), instanciaSeleccionada, rowSelected);
        return super.stopCellEditing();
    } 
    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        if (!isValid) {
            
            // No permitas la selección de la celda si no es válida.
            return false;
        }
        return super.shouldSelectCell(anEvent);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        isValid = true; // Restablece la validación al editar una nueva celda.
        rowSelected=row;
       // if(instanciaSeleccionada==column){
       //     System.out.println("yout rock");
        //}
        return super.getTableCellEditorComponent(table, value, isSelected, row, column);
    } 
    private boolean isValidValue(String value) {
        try {
            double resultado = Double.parseDouble(value); // Esto generará una excepción ArithmeticException
        } catch (NumberFormatException e) {
            return false;
        }
        return true; // Cambia esto con tu validación real.
    }  
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonElim;
    private javax.swing.JButton jButtonModif;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel0;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
