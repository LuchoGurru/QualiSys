/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUIComponents.Laminas;

import GUIComponents.GUINodos.QualyOperator;
import GUIComponents.GUINodos.QualyVariable;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
 * 
 * 
 * 
 * 
 * @author luciano.gurruchaga
 */
public class DragAndDropVariablesAndOperandsPanel extends JPanel    {
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
    private Map<String, QualyOperator> operadores = new HashMap<String, QualyOperator>();
    private Map<String, QualyVariable> variables = new HashMap<String, QualyVariable>();
    private Map<String, ArrayList<JPanel>> relPadreHijos = new HashMap<String, ArrayList<JPanel>>();

    // Los operadores que manejamos van a poder recibir de rango un valor y podran ser asignados como dominio de otro operador 
    public static int cantOperadores = 0;
    
    /**
     * Pintado del Drag And Drop component: 
     *      Parametros del constructor : 
     *          Lista de Variables,
     *          Lista de Operadores (inicialmente vacia)
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g){
        System.out.println("Paint: ");
        super.paintComponent(g);
        pintarVariables(g);
        pintarOperadores(g);
        dibujarLineas(g);
       // repaint();
    } 
    
    private void pintarVariables(Graphics g){
        for(QualyVariable qv: this.variables.values()){
            System.out.println("qv.getName() = " + qv.getName());
            this.add(qv);
        }
    }
    
    private void pintarOperadores(Graphics g){
        for(QualyOperator qo: this.operadores.values()){
            System.out.println("qo.getName() = " + qo.getName());
            this.add(qo);
        }
    }
   
    public void dibujarLineas(Graphics g){
        ArrayList<ArrayList<JPanel>> hermanos = new ArrayList<ArrayList<JPanel>>(this.relPadreHijos.values());
        for(int j=0;j < hermanos.size(); j++){
            ArrayList<JPanel> hijos = hermanos.get(j);
            Point padreLocation=null;
            for(int i=0;i< hijos.size();i++){
                JPanel h = hijos.get(i);
                if(padreLocation == null){
                    padreLocation =obtenerPadreLocation(h);
                }
                g.drawLine(h.getLocation().x,h.getLocation().y, padreLocation.x,padreLocation.y);
                g.fillOval(padreLocation.x-5, padreLocation.y-5, 10, 10);
            }
        }
    }
    /*public void drawArrow(Graphics g, int x0,int y0,int x1,int y1){
    double alfa=Math.atan2(y1-y0,x1-x0);
    g.drawLine(x0,y0,x1,y1);
    int k=5;
    int xa=(int)(x1-k*Math.cos(alfa+1));
    int ya=(int)(y1-k*Math.sin(alfa+1));
    // Se dibuja un extremo de la dirección de la flecha.
    g.drawLine(xa,ya,x1,y1); 
    xa=(int)(x1-k*Math.cos(alfa-1));
    ya=(int)(y1-k*Math.sin(alfa-1));
    // Se dibuja el otro extremo de la dirección de la flecha.
    g.drawLine(xa,ya,x1,y1); 
    }*/
    private Point obtenerPadreLocation(JPanel h){
        Point padreLocation = null;
        if(h.getClass() == QualyVariable.class){
            padreLocation = this.variables.get(((QualyVariable)h).getPadreID()).getLocation();
        }else{
            padreLocation = this.operadores.get(((QualyOperator)h).getPadreID()).getLocation();
        }
        return padreLocation;
    }
    /**
     * Drawablecomponent ... Variables - Operadores y Flechas 
     * 
     * Lista de Variables 
     * Lista de operadores con Dominio () Flechas  
     */
    /**
     * Constructores 
     */
     public DragAndDropVariablesAndOperandsPanel(){    
        this.setLayout(null);
        
     }    
     /**
      * Chequea si colisiona con algun componente del panel operador o variable
      * @param colisionador
      * @return 
      */
    public boolean isColision(QualyOperator colisionador){
        boolean legal = false;
        for(int i=0;i<this.getComponents().length;i++){
            Component c = this.getComponent(i);
            if(colisionador.getBounds().intersects(c.getBounds()) && !colisionador.getName().equals(c.getName())){
                colisionador.setBackground(Color.blue);
                //System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);
                legal = true;
            }
        }
        return legal;
    }
    
    private QualyOperator getOperatorByLocation(Point loc){
        Component c = this.getComponentAt(loc);
        if(this.getComponentAt(loc).getClass() == QualyOperator.class){
            return (QualyOperator) c;
        }
        return null;
    }
    private boolean isVariable(JPanel var){
        return var.getClass() == QualyVariable.class;
    }
    private QualyVariable getVariable(JPanel var){
        return (QualyVariable) var;
    }  
    private QualyOperator getOperator(JPanel var){
        return (QualyOperator) var;
    }   
    /**
     *              
     * @param hijoCandidato
     * @param padreLocation 
     */
    public void addToDomain(JPanel hijoCandidato, Point  padreLocation){
        QualyOperator operadorPadre = (QualyOperator) this.getComponentAt(padreLocation) ;
        if(canBeDomain(hijoCandidato,operadorPadre)){
            if(isVariable(hijoCandidato)){
                QualyVariable op_candidato = (QualyVariable) hijoCandidato;
                actualizarArbolGenealogico(op_candidato,op_candidato.getPadreID(),operadorPadre.getName());
            }else{
                QualyOperator op_candidato =(QualyOperator) hijoCandidato;
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
    public boolean canBeDomain(JPanel hijoCandidato, QualyOperator padreAdoptivo){
        boolean allow=true;
        if(padreAdoptivo != null){//OK
            if(!isVariable(hijoCandidato)){// es un operador ... 
                QualyOperator op_candidato = getOperator(hijoCandidato);
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
    public void addOperator(QualyOperator q){
        this.operadores.put(q.getName(),q);
        this.relPadreHijos.put(q.getName(),new ArrayList<JPanel>());
    }
    /**
     * El planteamiento del problema 
     * @param hijo
     * @param padreLoc 
     */
    public void addHijoToRel(JPanel hijo, Point padreLoc){
        QualyOperator padreOperator = getOperatorByLocation(padreLoc);
        if(canBeDomain(hijo, padreOperator)){
            ArrayList<JPanel> sons = relPadreHijos.get(padreOperator.getName());
            if(sons.size() < 5){
                sons.add(hijo);
                relPadreHijos.put(padreOperator.getName(), sons);
            }else{
                System.out.println("YA tiene 5 hijos");
            }
        }
    }
    
    public boolean noCicles(QualyOperator hijoCandidato, QualyOperator padreCandidato){
        boolean adopto=true;     
        String abuelo = padreCandidato.getPadreID();
        //   System.out.println("No me digas que me quedo loopeando" + abuelo);
        while(!abuelo.equals("") && adopto){ // esta restringido que sea yo mismo la primera vez 
            QualyOperator abu = this.operadores.get(abuelo);
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
    public void actualizarArbolGenealogico(JPanel adoptado, String padreViejo, String padreNuevo){
        //Ahora
        if(!padreViejo.equals("")){ // Si tenia padre
            ArrayList<JPanel> hermanosViejos = this.relPadreHijos.get(padreViejo);
            for(int i =0; i<hermanosViejos.size(); i++){
                if(hermanosViejos.get(i).getName().equals(adoptado.getName())){
                    hermanosViejos.remove(i);
                    break;
                }
            }
        }  
        //Despues
        ArrayList<JPanel> hermanosNuevos = this.relPadreHijos.get(padreNuevo);
        hermanosNuevos.add(adoptado); 
    }
    
}
