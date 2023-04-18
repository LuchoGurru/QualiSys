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
    
    
    private ArrayList<QualyOperator> operadores = new ArrayList<>();
    private ArrayList<QualyVariable> variables = new ArrayList<>();
    // Los operadores que manejamos van a poder recibir de rango un valor y podran ser asignados como dominio de otro operador 
    public static int cantOperadores = 0;
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        
        repaint();
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
                 // initComponents();
                 this.setLayout(null);
       /* variable= new QualyVariable(this,150,50,"nombreVar",20);
       // operador1.addMouseMotionListener(l);
       variable.setBounds(50,500,50,50);
        this.add(variable);
        variable1= new QualyVariable(this,300,100,"nombreVar",30);
       // operador1.addMouseMotionListener(l);
     //  variable1.setBounds(50,50,100,100);

        this.add(variable1);
        variable2= new QualyVariable(this,150,150,"nombreVar",20);
       // operador1.addMouseMotionListener(l);

        this.add(variable2);
        
        
        if(variable.getBounds().intersects(variable1.getBounds()))System.out.println("algo = ");

      //  QualyOperatorsPanel.ClickListener clickListener = new QualyOperatorsPanel.ClickListener();
   //     this.addMouseListener(clickListener); 
    //    this.add(new QualyOperator(this,10,10));
     //   esta = this;
         /*/
     }    

    /**
     * Metodos - 

    public void initing() {
        QualyVariable[] qv = new QualyVariable[WIDTH];
        ArrayList<QualyVariable> list = new ArrayList<>();
        
        list.add(new QualyVariable("laconnch",10f));
        list.add(new QualyVariable("dela",10f));
        list.add(new QualyVariable("loraa",10f));
        //LLAMAR AL METODO PAINT COMPONENTES
        this.setLayout(null);
        this.setSize(300, 500);
        //por otro lado arrancar a probramar gatoo
        
        
        for(QualyVariable q : list){
            q.setBackground(Color.blue);
            q.setBounds(MARGEN_Y, MARGEN_Y,10,20);
            this.add(q);
            
     //       if(restriccion_que_no_haya_ningun_componente_encima(q,list)){
      //          System.out.println("esto tiene que dar falso siempre = " + q);
        //    }else{
               // this.pintarVariable(q, MARGEN_X,MARGEN_Y );
          //  }
//            System.out.println("q = " + q);
            MARGEN_Y += MARGEN_Y + q.getHeight();
        }
        
        
    }         */
    

    /**
     * Metodos privados de GUI
     */     
    private void pintarVariable(QualyVariable q, int posX,int posY){
        System.out.println("posY = " + posY);
        
        
       // q.setBounds(posX, posY,10,20);
      //  q.repaint(posX, posY, 50, 100);
        this.add(q);
        this.repaint();
    }    
    
    
    public boolean listarOperadores(QualyOperator colisionador){
        System.out.println("Que cosa barbara");
        boolean legal = true;
        for(int i=0;i<this.getComponents().length;i++){
            Component c = this.getComponent(i);
            if(colisionador.getBounds().intersects(c.getBounds()) && !colisionador.getName().equals(c.getName())){
                colisionador.setBackground(Color.blue);
                System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);
                legal = false;
            }else{
                      //          System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i));
                System.out.println("COmponente lista"+i+ " asd "+  this.getComponent(i).getLocation().x + " ),(" +  this.getComponent(i).getLocation().y);

            }
        }
        return legal;
    }
    private void dibujarOperador(GUIComponents.GUINodos.QualyOperator qo, int posX, int posY){
        // agregar reetriccion de que no puede haber un componente encima 

    }     
    /**
     * REEmplazar esta gilada por GUI operator algo asi 
     * @param postor
     * @return 
   
    private boolean restriccion_que_no_haya_ningun_componente_encima(QualyVariable postor,ArrayList<QualyVariable> list){
     /*     int i=0;
        while(!this.rebalseArray[i].getBounds().contains(postor.getBounds())){
            i++;
        }
        return i < this.rebalseArray.length;
       
     boolean seSuperpone= false;
     for(QualyVariable q : list){
         if(!q.getId().equals(postor.getId())){
             System.out.println("q = x,y " + q.getX()+ q.getY());
             System.out.println("postor x,y = " + postor.getX()+ postor.getY());
            System.out.println("q.getIIDD() = " + q.getId());
        //    System.out.println("q.getBounds() = " + q.getBounds());
        //    System.out.println("postor.getBounds() = " + postor.getBounds());
            if(q.getBounds().contains(postor.getBounds())){
                seSuperpone=true;
              //  break;
            }    
         }else{
             System.out.println("soy el mismo");
//             System.out.println("seSuperpone = " + seSuperpone);
         }
         
     }
                  System.out.println("seSuperpone = " + seSuperpone);

     return seSuperpone;
    
    }  */
    
    /*
    
    
        En el listener si no se superpone llama a repaint  XD sino no punto nos vimos la proxima    
    
    
   
          
    public void dibujarLineaEntre2Copmponentes(Point p1, Point p2){
                System.out.println("p1.x + p1.y + p2.x + p2.y = " + p1.x + " , " + p1.y + " , " + p2.x + " , " + p2.y);
    //            this.lines.add(new Line2D.Double(p1.getX(), p1.getY(), p2.getX(), p2.getY()));

      //  Graphics g = this.getGraphics();
       // super.paintComponent(g);
      //  g.drawLine(p1.x, p1.y, p2.x, p2.y);
     //   g.drawLine(430, 510, 1011, 579);
        //repaint();
    } */
    public void addOperator(QualyOperator q){
        this.operadores.add(q);
    }
}







