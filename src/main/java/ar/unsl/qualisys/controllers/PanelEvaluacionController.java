/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.controllers;

import GUIUtils.CustomTableModel;
import GUIUtils.TableHeaderCustomCellRender;
import LSP.QsInstancia;
import ar.unsl.qualisys.Sesion;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.componentes.tabla.TableActionCellEditor;
import ar.unsl.qualisys.componentes.tabla.TableActionCellRender;
import ar.unsl.qualisys.componentes.tabla.TableActionEvent;
import ar.unsl.qualisys.paneles.CustomCellEditor;
import ar.unsl.qualisys.paneles.EvaluacionView;
import ar.unsl.qualisys.paneles.QsEvaluacionPanel;
import ar.unsl.qualisys.paneles.grafo.QsDadPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

/**
 *
 * @author luciano
 */
public class PanelEvaluacionController {
    
    private static PanelEvaluacionController controller;

    private EvaluacionView vista;
    private Sesion sesion = null; 
    private int instanciaSeleccionada;

    private JScrollPane jScrollPane1;
    private JTable jTableInstancias;
    private JScrollPane jScrollPane2;
    private JTable jTableResultados;
    private JScrollPane jScrollPane3;
    private JTable jTableResultadosParciales;
//    private JScrollPane jScrollPane4;
    private JTable jTableDetallesModal;    
    
    public static int cantOperadores = -1;

    ArrayList<String> orderOper = null;//El primero en calcular su resultado


    private PanelEvaluacionController(){
        this.sesion = Sesion.getInstance();
    }    
    
    public static PanelEvaluacionController getInstance(){
        if(controller == null){
            controller = new PanelEvaluacionController();
        }
        return controller;
    }
    
    public void setVista(EvaluacionView vista){
        this.vista = vista;  
    }
    
    /** 
     * Iniciacion de la tabla 
     */
    public void initTablaInstancias(JPanel jPanel1){
        jTableInstancias = new JTable();                  
        configTableLookAndFeel(jTableInstancias);                               //vista
        TableModel model = instanciasTableModel();                              //modelo
        jTableInstancias.setModel(model);
        jTableInstancias.addMouseListener(new java.awt.event.MouseAdapter() {   // listenner
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int col = jTableInstancias.columnAtPoint(evt.getPoint());
                if ( col > 0 )
                     instanciaSeleccionada = col;
            }
        });         

        jScrollPane1 = new JScrollPane(jTableInstancias,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.getViewport().setBackground(Color.decode("#EFEBCE"));
 
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(jScrollPane1,BorderLayout.CENTER);
        vista.repintar();   
    }
    
    
    /**
     * Configura el aspecto de la tabla 
     * @param table la tabla a ser configurada 
     */
    private void configTableLookAndFeel(JTable table){
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(new Color(250, 250, 250));
        table.getTableHeader().setDefaultRenderer(new TableHeaderCustomCellRender(jTableInstancias));
        table.setSelectionBackground(Color.decode("#D8A48F"));  
        table.setRowHeight(40);
    }
    /** 
     * Iniciacion de la tabla 
     */    
    public void initTablaResultados(JPanel jPanel4){
        jTableResultados = new JTable();
        jTableResultados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        configTableLookAndFeel(jTableResultados);                               //vista 
        
        CustomTableModel model2 = (CustomTableModel) new CustomTableModel(); //modelo 
        model2.addColumn("Instancias :");
        model2.addColumn("Resultados :");
        model2.addColumn("Acciones :"); 
        jTableResultados.setModel(model2);
        TableActionEvent event = new TableActionEvent() {                       //listenner
            @Override
            public void onEdit(int row) {
            }
            @Override
            public void onDelete(int row) {
            }
            @Override
            public void onView(int row) {
                mostrarResultadosParciales(row);
            }
        };
        addActionPanelToColumn(jTableResultados,2,event);
        
        jScrollPane2 = new JScrollPane(jTableResultados);
        jScrollPane2.getViewport().setBackground(Color.decode("#EFEBCE"));
        jPanel4.setLayout(new BorderLayout());      
        jPanel4.add(jScrollPane2,BorderLayout.CENTER);
        vista.repintar();
    }
    
    
    public void initTableResultadosParciales(JPanel jPanel5){    
        jTableResultadosParciales = new JTable();
        configTableLookAndFeel(jTableResultadosParciales);                      // vista
        CustomTableModel model3 = (CustomTableModel) new CustomTableModel(); //modelo 
        model3.addColumn("Operador :");
        model3.addColumn("Resultados-Valor :");
        model3.addColumn("Detalle - Inputs :");  
        jTableResultadosParciales.setModel(model3);
        TableActionEvent event = new TableActionEvent() {                       //listenner
            @Override
            public void onEdit(int row) {
            }

            @Override
            public void onDelete(int row) {

            }

            @Override
            public void onView(int row) {
                initTablaModal(row);
            }
        }; 
        addActionPanelToColumn(jTableResultadosParciales,2,event);
        
        jScrollPane3 = new JScrollPane(jTableResultadosParciales);
        jScrollPane3.getViewport().setBackground(Color.decode("#EFEBCE"));
        jPanel5.setLayout(new BorderLayout());
        jPanel5.add(jScrollPane3,BorderLayout.CENTER);
        vista.repintar();
    }
    
    public void initTablaModal(int row){
        try{
            jTableDetallesModal = new JTable();
            configTableLookAndFeel(jTableDetallesModal);                            //vista

            CustomTableModel model = new CustomTableModel(                        //modelo
                new Object[][] {},
                new String[] {"Hijo", "Ponderación", "Retorno"}
            );
            //Filas
            int cols = model.getColumnCount();
            String operFilaId = this.orderOper.get(row); 
            ArrayList<QsNodo> hijos = this.sesion.relPadreHijos.get(operFilaId);

            for (int i = 0; i < hijos.size(); i++) {
                Object[] fila = new Object[cols]; 
                QsNodo hijoFila = hijos.get(i);
                if(hijoFila instanceof QsVariable){
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
            QsOperador op = this.sesion.operadores.get(operFilaId);
            jTableDetallesModal.setModel(model);
            vista.crearDialog(jTableDetallesModal,op);
        }catch(NullPointerException npe){
            JOptionPane.showMessageDialog(jScrollPane1, "Se ha cambiado la funcion, calcule los resultados nuevamente...");
            return;
        }
    } 


    
    public void setEditableNodos(boolean editable) {
    ArrayList<String> nodos = new ArrayList<>(this.sesion.operadores.keySet()); // copiade operadores a evaluar
        for (int i = 0; i < nodos.size(); i++) {
            this.sesion.operadores.get(nodos.get(i)).setEditable(editable);
        }
    nodos = new ArrayList<>(this.sesion.variables.keySet()); // copiade operadores a evaluar
        for (int i = 0; i < nodos.size(); i++) {
            this.sesion.variables.get(nodos.get(i)).setEditable(editable);
        }    
    }
    
    public void addActionPanelToColumn(JTable table, int actionColumn,TableActionEvent event){
        table.getColumnModel().getColumn(actionColumn).setCellRenderer(new TableActionCellRender());
        table.getColumnModel().getColumn(actionColumn).setCellEditor(new TableActionCellEditor(event));
    }
    
    public void reinicializarOperadores(){
        ArrayList<String> ops = new ArrayList<>(this.sesion.operadores.keySet()); // copiade operadores a evaluar
        for (int i = 0; i < ops.size(); i++) {
            this.sesion.operadores.get(ops.get(i)).setValorResultado(-1d);
        }
   }
    
    public void mostrarResultadosParciales(int instanciaSeleccionada){
        //QsInstancia instancia = this.instancias.get(instanciaSeleccionada -1);
        ArrayList<String> ops = new ArrayList<>(this.sesion.operadores.keySet()); // copiade operadores a evaluar
        asignarInstancia(instanciaSeleccionada);
        reinicializarOperadores();
        calcularFuncion(true);
       
        CustomTableModel model = (CustomTableModel) jTableResultadosParciales.getModel(); //modelo  
        model.setRowCount(0);
        //Filas
        int cols = model.getColumnCount();
        
        for (int i = 0; i < ops.size(); i++) {
            String ordenOp = orderOper.get(i);
            Object[] fila = new Object[cols]; 
            fila[0] = this.sesion.operadores.get(ordenOp).getSymbol();
            fila[1] = this.sesion.operadores.get(ordenOp).getValorResultado();
//            fila[2] = this.operadores.get(ordenOp).getName(); // no hace falta por que el render de la tabla lo complleta con los botones ... 
            model.addRow(fila);           
        }       
        
    }

    
    public ArrayList<QsVariable> getVars(){
        return sesion.listaVariables;
    }
    /**
     * Invoca: Repaint
     * @param variables 
     */
    public void setListaVariables(ArrayList<QsVariable> variables) {
        this.sesion.listaVariables = variables;
    }

    public Map<String, QsVariable> getVariablesMap() {
        return sesion.variables;
    }

    public void setVariables(Map<String, QsVariable> variables) {
        this.sesion.variables = variables;
    }
    
    public Map<String, QsOperador> getOperadores() {
        return sesion.operadores;
    }

    public void setOperadores(Map<String, QsOperador> operadores) {
        this.sesion.operadores = operadores;
    }
    
    public Map<String, ArrayList<QsNodo>> getRelPadreHijos() {
        return sesion.relPadreHijos;
    }

    public void setRelPadreHijos(Map<String, ArrayList<QsNodo>> relPadreHijos) {
        this.sesion.relPadreHijos = relPadreHijos;
    }
    
    public TableModel instanciasTableModel() {
        System.out.println("Using toTableModel");
        int cols = this.sesion.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        //Columnas
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.sesion.instancias.get(i).getNombre();
        }
        CustomTableModel tmodel = new CustomTableModel(columnas, 0); //// deshabilita la edicion de la primer columna
        //Filas
        for (int i = 0; i < this.sesion.listaVariables.size(); i++) {
            Object[] fila = new Object[cols]; 
            for(int j=0;j<cols;j++){
                
                 if(j==0){
                     fila[j] = this.sesion.listaVariables.get(i).getName() + " " +this.sesion.listaVariables.get(i).getDescripcion();
                 }else{
                     fila[j] = this.sesion.instancias.get(j).getValores().get(this.sesion.listaVariables.get(i).getName());
                 }    
             }
            tmodel.addRow(fila);           
        }
        return tmodel;
    }
    
    public void mostrarResultados(double[] resultados) {
        CustomTableModel model = (CustomTableModel) jTableResultados.getModel(); //modelo  
        model.setRowCount(0);
        //Filas
        int cols = model.getColumnCount();
        for (int i = 0; i < this.sesion.instancias.size(); i++) {
            Object[] fila = new Object[cols]; 
            fila[0] = this.sesion.instancias.get(i).getNombre();
            fila[1] = resultados[i];
//                fila[2] = ""; // no hace falta por que el render de la tabla lo complleta con los botones ... 
            model.addRow(fila);           
        }
        jTableResultados.setModel(model);
        //return model;
    }
    
    /**
     * Asigna la instancia a las variables a evaluar, en toria si sigue vigente la referencia ya estaria resuelto
     */
    public void asignarInstancia(int instancia){
        Map<String,Double> valores = this.sesion.instancias.get(instancia).getValores();
        for(int i = 0 ; i < valores.size() ; i++){
            QsVariable qsv = this.sesion.listaVariables.get(i);
            String nameID = qsv.getName();
            qsv.setValorResultado(valores.get(qsv.getName()));
            this.sesion.variables.get(nameID).setValorResultado(valores.get(qsv.getName()));
        }
    }
    
    /*
    
    {
        Control de celda on focus lost que no se pierda si esta mal el valor, o reemplazar por cero 
    }
    
    */
    public void agregarValor(Double dou, int variable){
        int instancia = this.instanciaSeleccionada;
        this.sesion.instancias.get(instancia - 1 ).getValores().put(this.sesion.listaVariables.get(variable).getName(), dou); // -1 para respetar el arreglo por la columna q sobra al principio
    }
    
    public void crearInstancia(String nombre){
        QsInstancia nuevaI = new QsInstancia(nombre, new HashMap<String,Double>());
        for(int i = 0 ; i < this.sesion.listaVariables.size();i++){
            nuevaI.getValores().put(sesion.listaVariables.get(i).getName(),0d);
        }
        this.sesion.instancias.add(nuevaI);
        cargarInstancias();
    }
    public void eliminarInstancia(){
        if(instanciaSeleccionada > 0 ){
            this.sesion.instancias.remove(instanciaSeleccionada -1 );
            cargarInstancias();
        }else{
            vista.mostrarError("Seleccione una instancia de la tabla.");

        }
    }
    public void modificarInstancia(String nombre){
        if(instanciaSeleccionada > 0 ){
            this.sesion.instancias.get(instanciaSeleccionada -1 ).setNombre(nombre);
            cargarInstancias();
        }else{
            vista.mostrarError("Seleccione una instancia de la tabla.");
        }
    }

    private void cargarInstancias(){
        int cols = this.sesion.instancias.size() + 1 ; // A grego columna de variables
        Object[] columnas = new Object[cols];
        
        for(int i=0;i<cols;i++){
            if(i==0)
                columnas[i] = "Variables :";
            else
                columnas[i] = this.sesion.instancias.get(i-1).getNombre();
        }        
        
        CustomTableModel tmodel = new CustomTableModel(columnas, 0);

            //Filas
            for (int i = 0; i < this.sesion.listaVariables.size(); i++) {
                Object[] fila = new Object[cols]; 
                for(int j=0;j<cols;j++){
                     if(j==0){
                         fila[j] = this.sesion.listaVariables.get(i).getDescripcion();
                     }else{
                         String var =  this.sesion.listaVariables.get(i).getName();
                         fila[j] = this.sesion.instancias.get(j-1).getValores().get(var);
                     }    
                 }
                tmodel.addRow(fila);           
            }
            jTableInstancias.setModel(tmodel);
            jTableInstancias.getColumnModel().getColumn(0).setMinWidth(160);

            for (int columnIndex = 1; columnIndex < jTableInstancias.getColumnCount(); columnIndex++) {
                jTableInstancias.getColumnModel().getColumn(columnIndex).setMinWidth(99);
                jTableInstancias.getColumnModel().getColumn(columnIndex).setCellEditor(new CustomCellEditor(PanelEvaluacionController.getInstance(), new JTextField()));
            }
             if(jTableInstancias.getAutoResizeMode()!= 0 && jTableInstancias.getColumnCount() > 11){
                jTableInstancias.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
             }
    }
     /**
     * Carga las relaciones de los operadores y reinicializa las variables
     * @param variables 
     */
    public void initInstancias() {
        ArrayList<QsInstancia> instanciasRefresh = new ArrayList<>();
        for(QsInstancia i : this.sesion.instancias){
            String nombre = i.getNombre();
            Map<String,Double> valores = new HashMap<>();
            for(QsVariable v : this.sesion.listaVariables){
                Double ceroOValor = i.getValores().getOrDefault(v.getName(),0d);// Asigna el valor viejo o deja un 0 en su lugar.
                valores.put(v.getName(), ceroOValor);
            }
            instanciasRefresh.add(new QsInstancia(nombre, valores));
        }
        this.sesion.instancias = instanciasRefresh;
        cargarInstancias(); // las carga en la tabla si es que existen
    }

    public double[] getResultados() {
        return this.sesion.resultados;
    }
    
    public void setResultados(double [] resultados) {
        this.sesion.resultados = resultados;
    }
    
    public ArrayList<QsInstancia> getInstancias() {
        return this.sesion.instancias;
    }

    public void setInstancias(ArrayList<QsInstancia> instancias) {
        this.sesion.instancias = instancias;
    }
    
    public void evaluarResultados(){
        // Para que cancele el foco, un ebola por que tiene que estar en la vista .
        if(jTableInstancias.isEditing())
            jTableInstancias.getCellEditor().stopCellEditing();
        
        double[] resultados = new double[this.sesion.instancias.size()];
        for (int i = 0; i < this.sesion.instancias.size(); i++) {
            //this.relPadreHijos = null;
            reinicializarOperadores();
            asignarInstancia(i);
            resultados[i] = calcularFuncion(false);
        }
        this.sesion.setResultados(resultados);
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
        ArrayList<String> ops = new ArrayList<>(this.sesion.relPadreHijos.keySet()); // copiade operadores a evaluar
        boolean evaluar = true; // en teoria las variables ya estan setteadas por referencias en el mapa este ,,, pero hay que ver
        double resultado = -1d;
        while (ops.size() > 0) {
            System.out.println("infinite loop" + ops.size());
            int tam = ops.size();
            for (int j = 0; j < tam; j++) {
                QsOperador candidato = this.sesion.operadores.get(ops.get(j));
                System.out.println("candidato : " + candidato.getName() + candidato.getSymbol());
                ArrayList<QsNodo> hijos = this.sesion.relPadreHijos.get(candidato.getName());
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

    public int getInstanciaSeleccionada() {
        return instanciaSeleccionada;
    }

    public void setInstanciaSeleccionada(int instanciaSeleccionada) {
        this.instanciaSeleccionada = instanciaSeleccionada;
    }
    
}
