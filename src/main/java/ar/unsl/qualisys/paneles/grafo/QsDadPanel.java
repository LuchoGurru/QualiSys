/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.paneles.grafo;

import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.componentes.nodos.QsOperator;
import ar.unsl.qualisys.componentes.nodos.QsVariable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
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
    // La estructura de datos conveniente es : 
    // Una lista por niveles ... jajaja
    // Una lista vinculada 
    // Un rebalse directo o funcionHash de coso, funcion ideal ... ver bibliografía, para mapear cada categoría en una lista vinculada, es decir rebalse abierto vinculado 
    
    /**
     * -----IMPLEMENTACION-----
     */ 
    //private QualyVariable[] rebalseArray; // recibe por parametro la lista de variables sin gategorizar por el momento 
    //Las insertaré dentro del panel a unos pixeles del borde para luego dejarlas fijas inamobibles y aplicarles solo un operador 
    
    /// Entonces cuando arrastre el muse released del operador y lo suelte en DAD agrego un operador nuevito a esta lista y los vamos pintanding 
    // Despues vamos a ir agarrando y editando atributos de los operadores para ir pintando 
    
    
    //private ArrayList<QualyOperator> operadores = new ArrayList<>();
    //private ArrayList<QualyVariable> variables = new ArrayList<>();
    private QsGraphicPanel parent;
    private QsOperatorsPanel brother;
    private Map<String, QsOperator> operadores = new HashMap<String, QsOperator>();
    
    private Map<String, QsVariable> variables = new HashMap<String, QsVariable>();
    
    private Map<String, ArrayList<QsNodo>> relPadreHijos = new HashMap<String, ArrayList<QsNodo>>();
    
    private QsOperator operadorSeleccionado;
    JPopupMenu menuDesplegable = new JPopupMenu();


    // Los operadores que manejamos van a poder recibir de rango un valor y podran ser asignados como dominio de otro operador 
    public static int cantOperadores = 0;

    /**
     * Constructores 
     */
     public QsDadPanel(QsGraphicPanel parent,QsOperatorsPanel brother){    
        this.setLayout(null);
        this.parent = parent;
        this.brother =brother;
     }    

    public QsGraphicPanel getParent() {
        return parent;
    }

    public QsOperatorsPanel getBrother() {
        return brother;
    }
    
    public Map<String, QsOperator> getOperadores(){
        return operadores;
    } 

    public void setOperadores(Map<String, QsOperator> operadores) {
        this.operadores = operadores;
    }

    public Map<String, ArrayList<QsNodo>> getRelPadreHijos() {
        return relPadreHijos;
    }

    public Map<String, QsVariable> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, QsVariable> variables) {
        this.variables = variables;
        this.repaint();
    }

    public QsOperator getOperadorSeleccionado() {
        return operadorSeleccionado;
    }

    public void setOperadorSeleccionado(QsOperator operadorSeleccionado) {
        if(this.operadorSeleccionado!=null)
            this.operadorSeleccionado.setBorder(null);
        this.operadorSeleccionado = operadorSeleccionado;
        if(this.operadorSeleccionado!=null)
            this.operadorSeleccionado.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
    
    /**
     * Eliminamos todas las relaciones al nodo actual y las borramos posteriormente borramos al operador
    
    public void eliminarOperadorSeleccionado(){
        
    }
     */
    
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
        System.out.println("Paint: ");
        super.paintComponent(g);
        this.removeAll();
        pintarVariables(g);
        pintarOperadores(g);
        dibujarLineas(g);
        // repaint();
    }
    
    
    
    private void pintarVariables(Graphics g){
        for(QsVariable qv: this.variables.values()){
            System.out.println("qv.getName() = " + qv.getName());
            this.add(qv);
        }
    }
    
    private void pintarOperadores(Graphics g){
        for(QsOperator qo: this.operadores.values()){
            System.out.println("qo.getName() = " + qo.getName());
            this.add(qo);
        }
    }
    /**
     * Recorre, anidacion de arreglos.
     * @param g 
     */
    public void dibujarLineas(Graphics g){
        ArrayList<ArrayList<QsNodo>> hermanos = new ArrayList<ArrayList<QsNodo>>(this.relPadreHijos.values());
        for(int j=0;j < hermanos.size(); j++){
            ArrayList<QsNodo> hijos = hermanos.get(j);
            Point padreLocation=null;
            for(int i=0;i< hijos.size();i++){
                QsNodo h = hijos.get(i);
                if(padreLocation == null){
                    padreLocation = obtenerPadreLocation(h);
                }
                //Pinto (x,y) to (x',y')
                g.drawLine(h.getLocation().x,h.getLocation().y+25, padreLocation.x,padreLocation.y+25);
                
                System.out.println("X " + (padreLocation.x - h.getLocation().x)/2);
                System.out.println("Y " + padreLocation.y);
                System.out.println("X/2 " + padreLocation.x/2);
                System.out.println("Y/2 " + padreLocation.y/2);
                // Dibujo peso, ponderaje de la relacion
                g.drawString(""+h.getPonderacion(),h.getLocation().x + (padreLocation.x - h.getLocation().x)/2 ,
                    -5 + h.getLocation().y + (padreLocation.y - h.getLocation().y)/2) ;
                //Flecha
                g.fillOval(padreLocation.x-8, padreLocation.y+20, 10, 10);
            }
        }
    }

    private Point obtenerPadreLocation(QsNodo h){
        Point padreLocation = null;
        padreLocation = this.operadores.get((h).getPadreID()).getLocation();   
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
        boolean legal = false;
        for(int i=0;i<this.getComponents().length;i++){
            Component c = this.getComponent(i);
            if(colisionador.getBounds().intersects(c.getBounds()) && !colisionador.getName().equals(c.getName())){
                //colisionador.setBackground(Color.blue);
                //System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);
                legal = true;
            }
        }
        return legal;
    }
    
    public QsOperator getOperatorByLocation(Point loc){
        Component c = this.getComponentAt(loc);
        if(this.getComponentAt(loc).getClass() == QsOperator.class){
            return (QsOperator) c;
        }
        return null;
    }
    private boolean isVariable(JPanel var){
        return var.getClass() == QsVariable.class;
    }
    private QsVariable getVariable(JPanel var){
        return (QsVariable) var;
    }  
    private QsOperator getOperator(JPanel var){
        return (QsOperator) var;
    }   
    private boolean isGoodPonder(String s,String padre){
        boolean isGood = true;
        Float ponderValue = 0f;
        try{
            ponderValue = Float.parseFloat(s);
        }catch(NumberFormatException nex){
            JOptionPane.showMessageDialog(this,"Debe ingresar un valor real.");
            isGood = false;
        }
        //Control <= a 1 
        if(ponderValue>1){
            JOptionPane.showMessageDialog(this,"Debe ingresar un valor <= 1.");
            isGood = false;
        }else{
            Float ponderacionTotal = ponderValue;
            for(QsNodo n: this.relPadreHijos.get(padre)){
                ponderacionTotal += n.getPonderacion();
                if(ponderacionTotal > 1 ){
                    JOptionPane.showMessageDialog(this,"La suma total de los pesos del dominio de un operador debe ser igual a 1.");
                    isGood = false;
                    break;
                }
            }
        }
        return isGood;
    }
    private void setPonderValue(QsNodo hijo, QsNodo padre) {
        String s;
        // create a dialog Box
        do {
            s = JOptionPane.showInputDialog(
                    this,
                    "Selecciona el peso deseado de la relación (recuerda que la suma de los pesos tiene que ser igual a 1).",
                    1f);
        } while (!isGoodPonder(s, padre.getName()));

        hijo.setPonderacion(Float.valueOf(s));
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
        QsOperator operadorPadre = (QsOperator) this.getComponentAt(padreLocation) ;
        if(canBeDomain(hijoCandidato,operadorPadre)){

            this.setPonderValue(hijoCandidato,operadorPadre); 
            
            if(isVariable(hijoCandidato)){
                QsVariable var_candidato = (QsVariable) hijoCandidato;
                actualizarArbolGenealogico(var_candidato,var_candidato.getPadreID(),operadorPadre.getName());
                var_candidato.setPadreID(operadorPadre.getName());//SETTEO PADRE ADOPTIVO
            }else{
                QsOperator op_candidato =(QsOperator) hijoCandidato;
                actualizarArbolGenealogico(op_candidato,op_candidato.getPadreID(),operadorPadre.getName());
                op_candidato.setPadreID(operadorPadre.getName());//SETTEO PADRE ADOPTIVO
            }
            this.repaint();
        }
        
    }
    /**
     * Las variables pueden ser dominio de cualquier operador. Solo de uno a la vez.
     * @param qo
     * @param posX
     * @param posY
     */
    public boolean canBeDomain(JPanel hijoCandidato, QsOperator padreAdoptivo){
        boolean allow=true;
        if(padreAdoptivo != null){//OK
            if(!isVariable(hijoCandidato)){// es un operador ... 
                QsOperator op_candidato = getOperator(hijoCandidato);
                if(!padreAdoptivo.getPadreID().equals("")){ //operador con padre
                    System.out.println("operador con padre");
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
    public void addOperator(QsOperator q){
        this.operadores.put(q.getName(),q);
        this.relPadreHijos.put(q.getName(),new ArrayList<QsNodo>());
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

    
    public boolean noCicles(QsOperator hijoCandidato, QsOperator padreCandidato){
        boolean adopto=true;     
        String abuelo = padreCandidato.getPadreID();
        //   System.out.println("No me digas que me quedo loopeando" + abuelo);
        while(!abuelo.equals("") && adopto){ // esta restringido que sea yo mismo la primera vez 
            QsOperator abu = this.operadores.get(abuelo);
            System.out.println("abu " + abu.getPadreID());
            System.out.println("hijoCandidato " + hijoCandidato.getName());
            if(abu.getName().equals(hijoCandidato.getName()))
                adopto = false;
            abuelo = abu.getPadreID();
        }
        return adopto;
    }
    /**
     * Actualizo la estructura primero tengo que borrar el hijo del dominio 
     * y agregarlo al nuevo dominio 
     */
    public void actualizarArbolGenealogico(QsNodo adoptado, String padreViejo, String padreNuevo){
        //Ahora
        if(!padreViejo.equals("")){ // Si tenia padre
            ArrayList<QsNodo> hermanosViejos = this.relPadreHijos.get(padreViejo);
            for(int i =0; i<hermanosViejos.size(); i++){
                if(hermanosViejos.get(i).getName().equals(adoptado.getName())){
                    hermanosViejos.remove(i);
                    break;
                }
            }
        }  
        //Despues
        ArrayList<QsNodo> hermanosNuevos = this.relPadreHijos.get(padreNuevo);
        hermanosNuevos.add(adoptado); 
    }
    
    /**
     * Árbol Bien Formado : means, a imaginary tree. In wich the  leaves are variables and the root its an operator wich no father. Or a symbolic Father.
     * Todas las hojas tienen padre y ningun hijo.
     * Todos los nodos operadores - root tienen a lo más 5 hijos y minimo 2 excepto root que tiene a lo mas 5 hijos y ningun padre.
     * Ningun nodo puede ser hijo de algun descendiente.
    */
    public boolean isArbolBienFormado(){
        //THere is cicles ? 
        // All variables HAve one father ? 
        // All operators are ¨2,5* domain 
        // Cant  operators with out father == 1 
        boolean bienFormado = true; 
        for(QsVariable qv: this.variables.values()){
            if(qv.getPadreID().equals("")){
                bienFormado = false;
                break;
            }
        }
        if(bienFormado){
            int onlyOneRoot = 0;
            for(QsOperator qo: this.operadores.values()){
                if(qo.getPadreID().equals("")){
                    onlyOneRoot ++;
                    if(onlyOneRoot > 1){
                        bienFormado = false;
                        break;
                    }
                }
            }
            if(bienFormado)
                bienFormado = onlyOneRoot > 0;
        }
        if(bienFormado){
            for(String padreID : this.relPadreHijos.keySet()){
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