/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.controllers;

import ar.unsl.qualisys.Sesion;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import ar.unsl.qualisys.paneles.grafo.DrawAndDropView;
import ar.unsl.qualisys.paneles.grafo.memento.CaretTaker;
import ar.unsl.qualisys.paneles.grafo.memento.EstadoGrafo;
import ar.unsl.qualisys.paneles.grafo.memento.Originator;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
/**
 *
 * @author luciano
 */
public class PanelGrafoController implements ArrastrarYSoltarPresentadorLSP{
    
    private static PanelGrafoController controller;

    private DrawAndDropView vista;
    private Sesion sesion = null;
    private Originator originator;
    private CaretTaker caretTaker;
    private QsNodo nodoSeleccionado; 

    public static int cantOperadores = -1;



    private PanelGrafoController(){
        this.sesion = Sesion.getInstance();
    }
    
    public void setvista(DrawAndDropView vista){
        this.vista = vista; 
        this.originator = new Originator();
        this.caretTaker = new CaretTaker();
    }
    
    public static PanelGrafoController getInstance(){
        if(controller == null){
            controller = new PanelGrafoController();
        }
        return controller;
    }
    
    public QsNodo getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public void setNodoSeleccionado(QsNodo nodoSeleccionado) {
        if(this.nodoSeleccionado!=null)
            this.nodoSeleccionado.setBorder(null);
        this.nodoSeleccionado = nodoSeleccionado;
        if(this.nodoSeleccionado!=null)
            this.nodoSeleccionado.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
    
    public Map<String, QsOperador> getOperadores(){
        return sesion.operadores;
    } 

    public void setOperadores(Map<String, QsOperador> operadores) {
        sesion.operadores = operadores;
    }

    public Map<String, ArrayList<QsNodo>> getRelPadreHijos() {
        return sesion.relPadreHijos;
    }

    public Originator getOriginator() {
        return originator;
    }

    public void setOriginator(Originator originator) {
        this.originator = originator;
    }

    public CaretTaker getCaretTaker() {
        return caretTaker;
    }

    public void setCaretTaker(CaretTaker caretTaker) {
        this.caretTaker = caretTaker;
    }
    
    public Map<String, QsVariable> getVariables() {
        return sesion.variables;
    }
    
    @Override
    public void initData(Map<String, QsVariable> varsNuevas) {
        HashMap<String,ArrayList<QsNodo>> relPadreHijosSinVars = new HashMap<>();
//        Map<String, QsVariable> varsViejas = this.variables;
        this.sesion.variables = varsNuevas;
        for(String padreID : this.sesion.relPadreHijos.keySet()){
            relPadreHijosSinVars.put(padreID, new ArrayList<>());
            for(QsNodo h : this.sesion.relPadreHijos.get(padreID)){
                if(h.getClass()!=QsVariable.class){ // Operador
                    relPadreHijosSinVars.get(padreID).add(h);
                }else if (this.sesion.variables.keySet().contains(h.getName())){
                    QsVariable varVieja = (QsVariable) h;
                    String varID = h.getName();
                    this.sesion.variables.get(varID).setPadreID(varVieja.getPadreID());
                    this.sesion.variables.get(varID).setPonderacion(varVieja.getPonderacion());
                    this.sesion.variables.get(varID).setName(varVieja.getName());
                    this.sesion.variables.get(varID).setDescripcion(varVieja.getDescripcion());
                   // this.variables.get(varID).setBounds(varVieja.getBounds());
                    this.sesion.variables.get(varID).setGUIParent(varVieja.getGUIParent());
                    relPadreHijosSinVars.get(padreID).add(this.sesion.variables.get(varID)); //Añado variable a hermanos List
                }
            }
        }   
        this.sesion.relPadreHijos = relPadreHijosSinVars; // relaciones entre operadores
        this.caretTaker = new CaretTaker(); //Borro memoria 'cache'.
        this.guardarEstado(); // actualiza memento con las nuevas variables traidas del panel anterior
        vista.repintar(); 
    }
    
    /**
     * El árbol (imaginario) está formado por 3 estructuras de datos.
     * Estas son 2 listas de nosod. una de variables y otra de operadores. las cuales se implementan mediante un hashMap distinguido por ID .
     * Y Una estructura Auxiliar que contiene la relacion Padre - Hijos . La cual es la encargada de realizar todos lontroles y las restricciones necesarias en la formacion del arbol 
     * implementada con un hasMap distinguido por Padre ID y un conjunto de hijos[2,5],  
     */
    @Override
    public void guardarEstado(){
        EstadoGrafo nuevoEstado = new EstadoGrafo(sesion.variables, sesion.operadores,sesion.relPadreHijos);//Guardo Estado 
        originator.setEstado(nuevoEstado);
        caretTaker.addMemento(originator.guardar());
    }
    /**
     * Árbol Bien Formado : means, a imaginary tree. In wich the  leaves are variables and the root its an operator wich no father. Or a symbolic Father.
     * Todas las hojas tienen padre y ningun hijo.
     * Todos los nodos operadores - root tienen a lo más 5 hijos y minimo 2 excepto root que tiene a lo mas 5 hijos y ningun padre.
     * Ningun nodo puede ser hijo de algun descendiente.
    */
    @Override
    public boolean isArbolBienFormado(){//HACER LO MISMO CON EL TEXTO
        //THere is cicles ? 
        // All variables HAve one father ? 
        // All operators are ¨2,5* domain 
        // Cant  operators with out father == 1 
        boolean bienFormado = true; 
        for(QsVariable qv: this.sesion.variables.values()){
            if(qv.getPadreID().equals("")){
                qv.setBackground(Color.red);
                bienFormado = false;
                break;
            }
        }
        if(bienFormado){
            int onlyOneRoot = 0;
            for(QsOperador qo: this.sesion.operadores.values()){
                if(qo.getPadreID().equals("")){
                    onlyOneRoot ++;
                    if(onlyOneRoot > 1){
                        qo.setBackground(Color.red);
                        bienFormado = false;
                        break;
                    }
                }
            }
            if(bienFormado)
                bienFormado = onlyOneRoot > 0;//tampoco puede estar vacia
        }
        if(bienFormado){
            for(String padreID : this.sesion.relPadreHijos.keySet()){
                if (this.sesion.relPadreHijos.get(padreID).size() < 2 || this.sesion.relPadreHijos.get(padreID).size() > 5 ) {// aca tengo uqe jajaja me qcague de miedo,
                    System.out.println("Mal Rango"); 
                    System.out.println(padreID);
                    this.sesion.operadores.get(padreID).setBackground(Color.red);
                    bienFormado = false;
                }
            }
        }
        
        return bienFormado;
    }
    //this.getSesion().estructura;
    
    /**
     * Actualizo la estructura solo en casos de Modificaciones en relaciones.
     * Modificacion de padre.
     * Eliminacion de nodo. 
     * ¿Eliminacion de relacion? 
     */
    @Override
    public void actualizarArbolGenealogico(QsNodo adoptado, String padreViejo, String padreNuevo){
        System.out.println("padre del adoptado " + padreViejo);
        //Ahora
        if(!padreViejo.equals("")){                                             // Si tenia padre, actualizo los ex hermanos 
            ArrayList<QsNodo> hermanosViejos = this.sesion.relPadreHijos.get(padreViejo);
            for(int i =0; i<hermanosViejos.size(); i++){
                if(hermanosViejos.get(i).getName().equals(adoptado.getName())){
                    hermanosViejos.remove(i);
                    break;
                }
            }
            if(!hermanosViejos.isEmpty()){
                updatePonderValue(this.sesion.operadores.get(padreViejo));             //actualizao balanceo
            }
        }   
        if(!padreNuevo.equals("")){                                             // Lo agrego a lista de hermanos del nuevo padre
            //Despues
            ArrayList<QsNodo> hermanosNuevos = this.sesion.relPadreHijos.get(padreNuevo);
            hermanosNuevos.add(adoptado); 
            this.updatePonderValue(this.sesion.operadores.get(padreNuevo));            //Actualizo los nuevos hermanos 
        }else{ // ELIMINACION no hay padre nuevo
            String clave = adoptado.getName();
            
            ArrayList<QsNodo> hijos = this.sesion.relPadreHijos.get(clave);
            for(QsNodo h : hijos){                                              // Limpio padreID de hijos huerfanos
                System.out.println("hijo" +h.getName() +" al que borro el padreID = " + h.getPadreID());
                h.setPadreID("");
            }
            this.sesion.relPadreHijos.remove(clave);                                   // Lo borro en la relacion de padre
            this.sesion.operadores.remove(clave);                                      //Lo borro en la lista de operadores
            this.setNodoSeleccionado(null);                                     //Limpio el operador seleccionado
        }
        this.guardarEstado();
    }
        
    /**
     * Actualiza todos las ponderaciones de los hermanos del padre
     * @param padre 
     */
    @Override
    public void updatePonderValue(QsNodo padre) {
        ArrayList<QsNodo> hermanos=this.sesion.relPadreHijos.get(padre.getName());
        double balanceo = getPonderacionBalanceada(hermanos.size());
        for(QsNodo h :hermanos){
            h.setPonderacion(balanceo);
        }

    }
    /** 
     * @return 100/Cantidad de hijos
     */
   @Override
    public double getPonderacionBalanceada(int cantHijos){
        double balanceo = 1d/(double)cantHijos;
        System.out.println("cantHijos = " + cantHijos);
        System.out.println("balanceo = " + balanceo);
        return balanceo;
    }
    
    /**
     * Actualiza todos las ponderaciones de los hermanos del padre
     * @param padre 
     */
    @Override
    public void actualizarArbolGenealogico(QsNodo padre) {
        ArrayList<QsNodo> hermanos=this.sesion.relPadreHijos.get(padre.getName());
        
        double balanceo = getPonderacionBalanceada(hermanos.size());
        for(QsNodo h :hermanos){
            h.setPonderacion(balanceo);
        }
    }
     
    /**
     * Sí el hijo puede ser dominiio ... 
     * se setea el peso ponderado de la relacion 
     * y esta se agrega como nueva relacion controlando que no sea mayor a uno 
     * 
     * @param hijoCandidato
     * @param padreLocation 
     */
    @Override
    public void addToDomain(QsNodo hijoCandidato, Point  padreLocation){
        try{
            QsOperador operadorPadre = vista.getOperatorAtPoint(padreLocation) ; 
            System.out.println("canBeDomain?");
            if(canBeDomain(hijoCandidato,operadorPadre)){
                if(hijoCandidato instanceof QsVariable){
                    QsVariable var_candidato = (QsVariable) hijoCandidato;
                    actualizarArbolGenealogico(var_candidato,var_candidato.getPadreID(),operadorPadre.getName());
                    var_candidato.setPadreID(operadorPadre.getName());//SETTEO PADRE ADOPTIVO
                }else{
                    QsOperador op_candidato =(QsOperador) hijoCandidato;
                    actualizarArbolGenealogico(op_candidato,op_candidato.getPadreID(),operadorPadre.getName());
                    op_candidato.setPadreID(operadorPadre.getName());//SETTEO PADRE ADOPTIVO
                }
                vista.repintar();
            }

        }catch(NullPointerException ce){
            System.out.println("ERROR  = DAD.addToDomain no es un operador. " + padreLocation );
            System.out.println(ce.getMessage()); 
            vista.repintar();
        }
        
        
        
    }
    /**
     * Las variables pueden ser dominio de cualquier operador. Solo de uno a la vez.
     * @param qo
     * @param posX
     * @param posY
     */
    @Override
    public boolean canBeDomain(QsNodo hijoCandidato, QsOperador padreAdoptivo){
        boolean allow=true;
        if(padreAdoptivo != null){//OK
            if(this.sesion.relPadreHijos.get(padreAdoptivo.getName()).size() >= 5){
                allow=false;
                System.out.println("el dominio ya esta lleno");
            }
            if(allow && hijoCandidato.getName().equals(padreAdoptivo.getName())){
                System.out.println("No podes formar parte de tu dominio");
                allow = false;
            }
            if(allow && !(hijoCandidato instanceof QsVariable)){// es un operador ... 
                QsOperador op_candidato = (QsOperador) hijoCandidato; 
                if(!padreAdoptivo.getPadreID().equals("")){ //... operador con padre
                    System.out.println("controlando ciclos... operador adoptivo tiene padre");
                    if(!noCicles(op_candidato,padreAdoptivo)){
                        System.out.println("no podes ser parte de este dominio por que formaras un ciclo");
                        allow = false;
                    }else{
                        System.out.println("!noCicles means that no hay cicles");
                    }    
                }
            }
        }else{
            System.out.println("No podes ser parte de su dominio por que no es un operador o es una variable");
            allow = false;
        }
        return allow;
    }
    @Override
    public void addOperator(QsOperador q){
        this.sesion.operadores.put(q.getName(),q);
        this.sesion.relPadreHijos.put(q.getName(),new ArrayList<QsNodo>());
        this.guardarEstado();
    }
    @Override
    public void addPonderacionToNodo(QsNodo nodo, Double valor){
        if(nodo instanceof QsVariable)
            this.sesion.variables.get(nodo.getName()).setPonderacion(valor);
        else
            this.sesion.operadores.get(nodo.getName()).setPonderacion(valor);            
    }
    //ERROR : un abuelo que se tenia como su propio abuelo, entonces, este chabon, 
    //        tenia infinitos abuelos que no eran él!!! 
    @Override
    public boolean noCicles(QsOperador hijoCandidato, QsOperador padreCandidato){
        boolean adopto=true;     
        String abuelo = padreCandidato.getPadreID();
        //   System.out.println("No me digas que me quedo loopeando" + abuelo);
        while(!abuelo.equals("") && adopto){ // esta restringido que sea yo mismo la primera vez 
            QsOperador abu = this.sesion.operadores.get(abuelo);
            System.out.println("abu " + abu.getPadreID());
            System.out.println("hijoCandidato " + hijoCandidato.getName());
            if(abu.getName().equals(hijoCandidato.getName()))
                adopto = false;
            abuelo = abu.getPadreID();
        }
        return adopto;
    } 
         
    @Override
    public ArrayList<QsVariable> getListaOrdenadaVariables(){
         // Sort the list based on the 'name' attribute using a custom comparator
               // Convert the map values to a list
        ArrayList<QsVariable> variableList = new ArrayList<>(this.sesion.variables.values());
        // Sort the list based on the 'nombre' attribute using a custom comparator
        Collections.sort(variableList, Comparator.comparing(QsVariable::getOrden));
        return variableList;
    }
}
