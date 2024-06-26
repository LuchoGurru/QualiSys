/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperador;
import ar.unsl.qualisys.componentes.nodos.QsVariable; 
import ar.unsl.qualisys.paneles.grafo.memento.CaretTaker;
import ar.unsl.qualisys.paneles.grafo.memento.EstadoGrafo;
import ar.unsl.qualisys.paneles.grafo.memento.Originator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Lo que voy a hacer en este componente sera lo siguiente : 
 * 
 * lo primero que tengo que hacer es tener algo lindo para arrastrar en mi panel
 * lo segundo es francia
 * lo tercero es importar variables de tipo texto al panel como un rectangulo en principio 
 * lo cuarto te puse 
 * lo quinto es permitir asignaciones de variables a operandos y de operandos a variables 
 * restringir operandos con operandos 
 * y variables con variables 
 * 
 * IDEA : poner un cursor focus y un textito de ayuda para que sepa lo que tenga que hacer es decir armar la funcion o circuito 
 * 
 * Por un lado podemos tener una estructura de datos para lograr almacenar las variables de entrada, es decir 
 * las variables numeradas añadidas desde el editor de texto con una expresion regular reestringica por <1.1>_<categorizadas> 
 * y inicializadas en este panel.
 * 
 * por otro lado me gustaría que se dibujaran flechas euclideas con maximo de 1 dobles de 90°
 * 
 * Esto esta siendo ideado aproximadamente desde fines de octube o principios de noviembre del 2022/ 16 / 9  ... fue en septiembre
 * 
 * Hasta la fecha sigo teniendo 24 años, Padre de Joaquina que tiene 2 años y 2 meses y 4 dias ... junto a muchas personas y sobretodo mi familia 
 * ancestral contemporanea y progenitora n't .
 * 
 * Una vez terminado esto rendiré ingles para festejar ! 
 *  Ya tengo 25 y medio y todavía no me recibo 
 * 17/09/23
 * 
 * 
 * 
 * @author luciano.gurruchaga
 */
public class QsDadPanel extends JPanel {//implements LspTreeCotrols { ControlesArbolLSP

    private QsGraphicPanel GUIpadre;
    private QsOperatorsPanel brother;
    private Map<String, QsOperador> operadores = new HashMap<String, QsOperador>();
    
    private Map<String, QsVariable> variables = new HashMap<String, QsVariable>();
    
    private Map<String, ArrayList<QsNodo>> relPadreHijos = new HashMap<String, ArrayList<QsNodo>>();
    
    private Originator originator;
    private CaretTaker caretTaker;
    
    private QsNodo nodoSeleccionado; 
    private Dimension area; //indicates area taken up by graphics 
    public static int cantOperadores = -1;

    /**
     * Constructores 
     */
     public QsDadPanel(QsGraphicPanel parent,QsOperatorsPanel brother){    
        this.setLayout(null);
        this.GUIpadre = parent;
        this.brother =brother;
        this.area = new Dimension();
        this.originator = new Originator();
        this.caretTaker = new CaretTaker();
        this.setBackground(Color.decode("#FFFFFF"));
     }    

    public QsOperatorsPanel getBrother() {
        return brother;
    }

    public QsGraphicPanel getGUIpadre() {
        return GUIpadre;
    }
    
    public Map<String, QsOperador> getOperadores(){
        return operadores;
    } 

    public void setOperadores(Map<String, QsOperador> operadores) {
        this.operadores = operadores;
    }

    public Map<String, ArrayList<QsNodo>> getRelPadreHijos() {
        return relPadreHijos;
    }

    /**
     * Devuelve la lista ordenada de QsVariable
     */
    public ArrayList<QsVariable> getListaVariables(){
         // Sort the list based on the 'name' attribute using a custom comparator
               // Convert the map values to a list
        ArrayList<QsVariable> variableList = new ArrayList<>(variables.values());

        // Sort the list based on the 'nombre' attribute using a custom comparator
        Collections.sort(variableList, Comparator.comparing(QsVariable::getOrden));
 
        return variableList;
    }
    
    public Map<String, QsVariable> getVariables() {
        return variables;
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
    
    
    
    /**
     * Carga las relaciones de los operadores y reinicializa las variables
     * @param variables 
     */
    public void initVariables(Map<String, QsVariable> varsNuevas) {
        HashMap<String,ArrayList<QsNodo>> relPadreHijosSinVars = new HashMap<>();
//        Map<String, QsVariable> varsViejas = this.variables;
        this.variables = varsNuevas;
        for(String padreID : this.relPadreHijos.keySet()){
            relPadreHijosSinVars.put(padreID, new ArrayList<>());
            for(QsNodo h : this.relPadreHijos.get(padreID)){
                if(h.getClass()!=QsVariable.class){ // Operador
                    relPadreHijosSinVars.get(padreID).add(h);
                }else if (this.variables.keySet().contains(h.getName())){
                    QsVariable varVieja = (QsVariable) h;
                    String varID = h.getName();
                    this.variables.get(varID).setPadreID(varVieja.getPadreID());
                    this.variables.get(varID).setPonderacion(varVieja.getPonderacion());
                    this.variables.get(varID).setName(varVieja.getName());
                    this.variables.get(varID).setDescripcion(varVieja.getDescripcion());
                   // this.variables.get(varID).setBounds(varVieja.getBounds());
                    this.variables.get(varID).setGUIParent(varVieja.getGUIParent());
                    relPadreHijosSinVars.get(padreID).add(this.variables.get(varID)); //Añado variable a hermanos List
                }
            }
        }   
        this.relPadreHijos = relPadreHijosSinVars; // relaciones entre operadores
        this.caretTaker = new CaretTaker(); //Borro memoria 'cache'.
        this.guardarEstado(); // actualiza memento con las nuevas variables traidas del panel anterior
        this.repaint(); 
    }
    
    public void setVariables(Map<String, QsVariable> varsNuevas){
        this.variables = varsNuevas;
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
    
    /**
     * Pintado del Drag And Drop component:
     *      Parametros del constructor : 
     *          Lista de Variables,
     *          Lista de Operadores (inicialmente vacia)
     * @param g 
     */
    public void setRelPadreHijos(Map<String, ArrayList<QsNodo>> relPadreHijos) {
        this.relPadreHijos = relPadreHijos;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.removeAll();
        super.paintComponent(g); 
        System.out.println("Paint: ");
        area.width=0;
        area.height=0;
        pintarVariables(g);
        pintarOperadores(g);
        dibujarLineas(g); 
        this.revalidate(); //Let the scroll pane know to update itself and its scrollbars.

    }
    
    private void pintarVariables(Graphics g){
        boolean changed = false;
        for(QsVariable qv: this.variables.values()){
           // System.out.println("qv.getName() = " + qv.getName());
            this.add(qv);
            // Listo, ahora a ajustar el SCROLL panel
            //   this.scrollRectToVisible(qo.getBounds());
            int this_width = (qv.getX() + qv.getWidth());
            int this_height = (qv.getY() + qv.getHeight());
            //Update client's preferred size because the area taken up by the graphics has gotten larger or smaller (if cleared).
            if (this_width > area.width) {
                area.width = this_width; 
                this.setPreferredSize(new Dimension(this_width, area.height));
            }
            if (this_height > area.height) {
                area.height = this_height; 
                this.setPreferredSize(new Dimension(area.width, this_height));
            }
//            if (changed) {
//                              this.setPreferredSize(new Dimension(this_width, this_height));           
//        }

        }
    }
    
    private void pintarOperadores(Graphics g){
        for(QsOperador qo: this.operadores.values()){
            boolean changed = false;
           // System.out.println("qo.getName() = " + qo.getName());
            this.add(qo);
            // Listo, ahora a ajustar el SCROLL panel
            //   this.scrollRectToVisible(qo.getBounds());
            int this_width = (qo.getX() + qo.getWidth());
            int this_height = (qo.getY() + qo.getHeight());
            //Update client's preferred size because the area taken up by the graphics has gotten larger or smaller (if cleared).
            if (this_width > area.width) {
                area.width = this_width; 
                this.setPreferredSize(new Dimension(this_width, area.height));
            }
            if (this_height > area.height) {
                area.height = this_height; 
                this.setPreferredSize(new Dimension(area.width, this_height));
            }
        } 
        
    }
    /**
     * Recorre, anidacion de arreglos.
     * @param g 
     */
    public void dibujarLineas(Graphics g){
        ArrayList<ArrayList<QsNodo>> hijos = new ArrayList<ArrayList<QsNodo>>(this.relPadreHijos.values());
        for(int j=0;j < hijos.size(); j++){
            ArrayList<QsNodo> hermanos = hijos.get(j);
            Point padreLocation=null;
            for(int i=0;i< hermanos.size();i++){
                QsNodo h = hermanos.get(i);
                if(padreLocation == null){
                    padreLocation = obtenerPadreLocation(h);
                }
                //Pinto (x,y) to (x',y')
                if(h.getClass() == QsVariable.class)
                    g.drawLine(h.getLocation().x + 80,h.getLocation().y+15, padreLocation.x,padreLocation.y+25);
                else
                    g.drawLine(h.getLocation().x + 50,h.getLocation().y+25, padreLocation.x,padreLocation.y+25);
                 // Dibujo peso, ponderaje de la relacion
                g.drawString(""+String.format("%.2f", h.getPonderacion()),
                        h.getLocation().x + (padreLocation.x - h.getLocation().x)/2 + 20 ,
                        h.getLocation().y + (padreLocation.y - h.getLocation().y)/2 + 20) ;
                //Flecha
                g.fillOval(padreLocation.x-8, padreLocation.y+20, 10, 10);
            }
        }
    }

    private Point obtenerPadreLocation(QsNodo h){
        Point padreLocation = null;
        padreLocation = this.operadores.get(h.getPadreID()).getLocation();   
        return padreLocation;
    }
    /**
     * Drawablecomponent ... Variables - Operadores y Flechas 
     * 
     * Lista de Variables 
     * Lista de operadores con Dominio () Flechas  
     */

     /**
      * Chequea si colisiona con algun componente del panel operador o variable
      * @param colisionador
      * @return 
      */
    public boolean isColision(QsNodo colisionador){
        boolean colision = false;
        for(int i=0;i<this.getComponents().length;i++){
            Component c = this.getComponent(i);
            System.out.println("Componente c is Nodo ?" + (c instanceof QsNodo));
            System.out.println("Componente null is Integer ?" + (null instanceof Integer));
            System.out.println("Componente c is DaD ?" + (c instanceof QsDadPanel));
            if(colisionador.getBounds().intersects(c.getBounds()) && !colisionador.getName().equals(c.getName())){
                //System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);
                System.out.println("colision = true");
                colision = true;
                break;
            }else{
                                System.out.println("colision =false");

            }
        }
        return colision;
    }
    
    public QsOperador getOperatorByLocation(Point loc){
        Component c = this.getComponentAt(loc);
        if(this.getComponentAt(loc).getClass() == QsOperador.class){
            return (QsOperador) c;
        }
        return null;
    }
    private boolean isVariable(QsNodo var){
        return var.getClass() == QsVariable.class;
    }
    private QsVariable getVariable(QsNodo var){
        return (QsVariable) var;
    }  
    private QsOperador getOperator(QsNodo var){
        return (QsOperador) var;
    }   
    private boolean isGoodPonder(QsNodo hijo, String valor,String padre){
        boolean isGood = true;
        Double ponderValue = 0d;
        try{
            ponderValue = Double.parseDouble(valor);
        }catch(NumberFormatException nex){
            JOptionPane.showMessageDialog(this,"Debe ingresar un valor real.");
            isGood = false;
        }
        //Control <= a 1 
        if(ponderValue>1){
            JOptionPane.showMessageDialog(this,"Debe ingresar un valor <= 1.");
            isGood = false;
        }else if(isGood && ponderValue<0){
            JOptionPane.showMessageDialog(this,"Debe ingresar un valor > 0.");
            isGood = false;            
        }else if(isGood){
            Double ponderacionTotal = ponderValue;
            for(QsNodo n: this.relPadreHijos.get(padre)){
                if(!n.getName().equals(hijo.getName())){ // No se suma asi mismo
                    ponderacionTotal += n.getPonderacion();
                    if(ponderacionTotal > 1 ){
                        JOptionPane.showMessageDialog(this,"La suma total de los pesos del dominio de un operador debe ser igual a 1.");
                        isGood = false;
                        break;
                    }
                } 
            }
        }
        return isGood;
    }
    
    /** 
     * @return 100/Cantidad de hijos
     */
    private double getPonderacionBalanceada(int cantHijos){
        double balanceo = 1d/(double)cantHijos;
        System.out.println("cantHijos = " + cantHijos);
        System.out.println("balanceo = " + balanceo);
        return balanceo;
    }
    
    /**
     * Actualiza todos las ponderaciones de los hermanos del padre
     * @param padre 
     */
    private void updatePonderValue(QsNodo padre) {
        ArrayList<QsNodo> hermanos=this.relPadreHijos.get(padre.getName());
        
        double balanceo = getPonderacionBalanceada(hermanos.size());
        for(QsNodo h :hermanos){
            h.setPonderacion(balanceo);
        }
        // create a dialog Box
/*        do {
            s = JOptionPane.showInputDialog(
                    this,
                    "Selecciona el peso deseado de la relación (recuerda que la suma de los pesos tiene que ser igual a 1).",
                    1f);
        } while (!isGoodPonder(s, padre.getName()));
*/
       //hijo.setPonderacion(Double.valueOf(s));
    }
    
    /**
     * Actualiza todos las ponderaciones de los hermanos del padre
     * @param padre 
     */
    public void setPonderValue(QsNodo hijo, String padre) {
        // create a dialog Box
        String valor;
        do {
            valor = JOptionPane.showInputDialog(
                    this,
                    "Selecciona el peso deseado de la relación (recuerda que la suma de los pesos tiene que ser igual a 1).",
                    1d);
        } while (!isGoodPonder(hijo,valor, padre));

       hijo.setPonderacion(Double.valueOf(valor));
       
    
    }
    
    /**
     * Sí el hijo puede ser dominiio ... 
     * se setea el peso ponderado de la relacion 
     * y esta se agrega como nueva relacion controlando que no sea mayor a uno 
     * 
     * @param hijoCandidato
     * @param padreLocation 
     */
    public void addToDomain(QsNodo hijoCandidato, Point  padreLocation){
        try{
            QsOperador operadorPadre = (QsOperador) this.getComponentAt(padreLocation) ; 
            System.out.println("canBeDomain?");
            if(canBeDomain(hijoCandidato,operadorPadre)){
                if(isVariable(hijoCandidato)){
                    QsVariable var_candidato = (QsVariable) hijoCandidato;
                    actualizarArbolGenealogico(var_candidato,var_candidato.getPadreID(),operadorPadre.getName());
                    var_candidato.setPadreID(operadorPadre.getName());//SETTEO PADRE ADOPTIVO
                }else{
                    QsOperador op_candidato =(QsOperador) hijoCandidato;
                    actualizarArbolGenealogico(op_candidato,op_candidato.getPadreID(),operadorPadre.getName());
                    op_candidato.setPadreID(operadorPadre.getName());//SETTEO PADRE ADOPTIVO
                }
                this.repaint();
            }

        }catch(ClassCastException ce){
            System.out.println("ERROR  = DAD.addToDomain no es un operador. " + padreLocation );
            System.out.println(ce.getMessage()); 
            this.repaint();
        }
        
        
        
    }
    /**
     * Las variables pueden ser dominio de cualquier operador. Solo de uno a la vez.
     * @param qo
     * @param posX
     * @param posY
     */
    public boolean canBeDomain(QsNodo hijoCandidato, QsOperador padreAdoptivo){
        boolean allow=true;
        if(padreAdoptivo != null){//OK
            if(this.relPadreHijos.get(padreAdoptivo.getName()).size() >= 5){
                allow=false;
                System.out.println("el dominio ya esta lleno");
            }
            if(allow && hijoCandidato.getName().equals(padreAdoptivo.getName())){
                System.out.println("No podes formar parte de tu dominio");
                allow = false;
            }
            if(allow && !isVariable(hijoCandidato)){// es un operador ... 
                QsOperador op_candidato = getOperator(hijoCandidato); 
                if(!padreAdoptivo.getPadreID().equals("")){ //... operador con padre
                    System.out.println("controlando ciclos... operador adoptivo tiene padre");
                    if(!noCicles(op_candidato,padreAdoptivo)){
                        System.out.println("no podes ser parte de este dominio por que formaras un ciclo");
                        allow = false;
                        // Este padre soy yo ? no  
                        // GetPadres ancestros  
                        // este otro padre tiene padre ?  no 
                        // op_candidato.setPadreID(padreAdoptivo.getName());
                        // actualizarArbolGenealogico(op_candidato,op_candidato.getPadreID(),padreAdoptivo.getName());
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
    public void addOperator(QsOperador q){
        this.operadores.put(q.getName(),q);
        this.relPadreHijos.put(q.getName(),new ArrayList<QsNodo>());
        this.guardarEstado();
    }
    /**
     * El planteamiento del problema 
     * @param hijo
     * @param padreLoc 
    public void addHijoToRel(QsNodo hijo, Point padreLoc){
        QsOperator padreOperator = getOperatorByLocation(padreLoc);
        if(canBeDomain(hijo, padreOperator)){
            ArrayList<QsNodo> sons = relPadreHijos.get(padreOperator.getName());
            if(sons.size() < 5){
                sons.add(hijo);
                relPadreHijos.put(padreOperator.getName(), sons);
            }else{
                System.out.println("YA tiene 5 hijos");
            }
        }
    }     */

    //ERROR : un abuelo que se tenia como su propio abuelo, entonces, este chabon, 
    //        tenia infinitos abuelos que no eran él!!! 
    public boolean noCicles(QsOperador hijoCandidato, QsOperador padreCandidato){
        boolean adopto=true;     
        String abuelo = padreCandidato.getPadreID();
        //   System.out.println("No me digas que me quedo loopeando" + abuelo);
        while(!abuelo.equals("") && adopto){ // esta restringido que sea yo mismo la primera vez 
            QsOperador abu = this.operadores.get(abuelo);
            System.out.println("abu " + abu.getPadreID());
            System.out.println("hijoCandidato " + hijoCandidato.getName());
            if(abu.getName().equals(hijoCandidato.getName()))
                adopto = false;
            abuelo = abu.getPadreID();
        }
        return adopto;
    }
    
    
    /**
     * Actualizo la estructura solo en casos de Modificaciones en relaciones.
     * Modificacion de padre.
     * Eliminacion de nodo. 
     * ¿Eliminacion de relacion? 
     */
    public void actualizarArbolGenealogico(QsNodo adoptado, String padreViejo, String padreNuevo){
        System.out.println("padre del adoptado " + padreViejo);
        //Ahora
        if(!padreViejo.equals("")){                                             // Si tenia padre, actualizo los ex hermanos 
            ArrayList<QsNodo> hermanosViejos = this.relPadreHijos.get(padreViejo);
            for(int i =0; i<hermanosViejos.size(); i++){
                if(hermanosViejos.get(i).getName().equals(adoptado.getName())){
                    hermanosViejos.remove(i);
                    break;
                }
            }
            if(!hermanosViejos.isEmpty()){
                updatePonderValue(this.operadores.get(padreViejo));             //actualizao balanceo
            }
        }   
        if(!padreNuevo.equals("")){                                             // Lo agrego a lista de hermanos del nuevo padre
            //Despues
            ArrayList<QsNodo> hermanosNuevos = this.relPadreHijos.get(padreNuevo);
            hermanosNuevos.add(adoptado); 
            this.updatePonderValue(this.operadores.get(padreNuevo));            //Actualizo los nuevos hermanos 
        }else{ // ELIMINACION no hay padre nuevo
            String clave = adoptado.getName();
            
            ArrayList<QsNodo> hijos = this.relPadreHijos.get(clave);
            for(QsNodo h : hijos){                                              // Limpio padreID de hijos huerfanos
                System.out.println("hijo" +h.getName() +" al que borro el padreID = " + h.getPadreID());
                h.setPadreID("");
            }
            this.relPadreHijos.remove(clave);                                   // Lo borro en la relacion de padre
            this.operadores.remove(clave);                                      //Lo borro en la lista de operadores
            this.setNodoSeleccionado(null);                                     //Limpio el operador seleccionado
        }
        this.guardarEstado();
    }
    
    
     
    /**
     * Árbol Bien Formado : means, a imaginary tree. In wich the  leaves are variables and the root its an operator wich no father. Or a symbolic Father.
     * Todas las hojas tienen padre y ningun hijo.
     * Todos los nodos operadores - root tienen a lo más 5 hijos y minimo 2 excepto root que tiene a lo mas 5 hijos y ningun padre.
     * Ningun nodo puede ser hijo de algun descendiente.
    */
    public boolean isArbolBienFormado(){//HACER LO MISMO CON EL TEXTO
        //THere is cicles ? 
        // All variables HAve one father ? 
        // All operators are ¨2,5* domain 
        // Cant  operators with out father == 1 
        boolean bienFormado = true; 
        for(QsVariable qv: this.variables.values()){
            if(qv.getPadreID().equals("")){
                qv.setBackground(Color.red);
                bienFormado = false;
                break;
            }
        }
        if(bienFormado){
            int onlyOneRoot = 0;
            for(QsOperador qo: this.operadores.values()){
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
            for(String padreID : this.relPadreHijos.keySet()){
                if (this.relPadreHijos.get(padreID).size() < 2 || this.relPadreHijos.get(padreID).size() > 5 ) {// aca tengo uqe jajaja me qcague de miedo,
                    System.out.println("Mal Rango"); 
                    System.out.println(padreID);
                    this.operadores.get(padreID).setBackground(Color.red);
                    bienFormado = false;
                }
                
            //   if (this.relPadreHijos.get(padreID).size() < 2 || > 5 ) { aca tengo uqe jajaja me qcague de miedo,
            //             tengo que agarrar todos que tengan entre el r ango pero con la raiz no o con las variables no jaja si es != var si sino no        
            // }
            }
        }
        
        return bienFormado;
    }
    /**
     * El árbol (imaginario) está formado por 3 estructuras de datos.
     * Estas son 2 listas de nosod. una de variables y otra de operadores. las cuales se implementan mediante un hashMap distinguido por ID .
     * Y Una estructura Auxiliar que contiene la relacion Padre - Hijos . La cual es la encargada de realizar todos lontroles y las restricciones necesarias en la formacion del arbol 
     * implementada con un hasMap distinguido por Padre ID y un conjunto de hijos[2,5],  
     */
    public void guardarEstado(){
        EstadoGrafo nuevoEstado = new EstadoGrafo(this.variables, this.operadores,this.relPadreHijos);//Guardo Estado 
        originator.setEstado(nuevoEstado);
        caretTaker.addMemento(originator.guardar());
    }
}
/**
 * *LISTENER DOBLE CLICK DESPLIEGO MENU *
 * Simple click set Nodo Activo + shortcuts .


        public void menuPopUp(){
             
        JMenuItem deshacer = new JMenuItem("Deshacer");
        JMenuItem rehacer = new JMenuItem("Rehacer");
        JMenuItem guardar = new JMenuItem("guardar");
        JMenuItem abrir = new JMenuItem("abrir");
        JMenuItem cerrar = new JMenuItem("cerrar");
        JMenuItem archivosRecientes = new JMenuItem("archivos recientes");
        JMenuItem exportar = new JMenuItem("exportar");

        deshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
        rehacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,InputEvent.CTRL_DOWN_MASK));
        cortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_DOWN_MASK));
        copiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_DOWN_MASK));
        pegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,InputEvent.CTRL_DOWN_MASK));
        deshacer.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,InputEvent.CTRL_DOWN_MASK));
        cortar.addActionListener(new eliminar.ejecutar());
        copiar.addActionListener(new StyledEditorKit.CopyAction());
        pegar.addActionListener(new StyledEditorKit.PasteAction());

        menuDesplegable.add(deshacer);
        menuDesplegable.add(rehacer);
        menuDesplegable.add(cortar);
        menuDesplegable.add(copiar);
        menuDesplegable.add(pegar);

        this.setComponentPopupMenu(menuDesplegable);
    }

*/