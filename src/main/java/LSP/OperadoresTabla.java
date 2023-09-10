/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LSP;

import java.util.HashMap;

/**
 *
 * @author luciano.gurruchaga
 */
public class OperadoresTabla {
    
    private HashMap<Integer,Operadores> operadores;
    
    public OperadoresTabla(){
        this.operadores = new HashMap<>();
        operadores.put(1,new Operadores(1,"Disjunction","D",1f,1700d,1700d,1700d,1700d));
        operadores.put(2,new Operadores(1,"Strong QD (+)","D++",0.9375f,1700d,1700d,1700d,1700d));
        operadores.put(3,new Operadores(1,"Strong QD","D+",0.8750f,1700d,1700d,1700d,1700d));
        operadores.put(4,new Operadores(1,"Strong QD (-)","D+-",0.8125f,1700d,1700d,1700d,1700d));
        operadores.put(5,new Operadores(1,"Medium QD","DA",0.7500f,1700d,1700d,1700d,1700d));
        operadores.put(6,new Operadores(1,"Weak QD (+)","D-+",0.6875f,1700d,1700d,1700d,1700d));
        operadores.put(7,new Operadores(1,"Weak QD","D-",0.6250f,1700d,1700d,1700d,1700d));
        operadores.put(8,new Operadores(1,"Square Mean","SQU",0.6232f,1700d,1700d,1700d,1700d));
        operadores.put(9,new Operadores(1,"Weak QD (-)","D--",0.5625f,1700d,1700d,1700d,1700d));
        operadores.put(10,new Operadores(1,"Arithmetic Mean","A",0.5f,1700d,1700d,1700d,1700d));

        operadores.put(11,new Operadores(1,"Weak QC (-)","C--",0.4375f,1700d,1700d,1700d,1700d));
        operadores.put(12,new Operadores(1,"Weak QC","C-",0.3750f,1700d,1700d,1700d,1700d));
        operadores.put(13,new Operadores(1,"Geometric Mean","GEO",0.3333f,1700d,1700d,1700d,1700d));
        operadores.put(14,new Operadores(1,"Weak QC (+)","C-+",0.3125f,1700d,1700d,1700d,1700d));
        operadores.put(15,new Operadores(1,"Medium QC","CA",0.2500f,1700d,1700d,1700d,1700d));
        operadores.put(16,new Operadores(1,"Harmonic Mean","HAR",0.2274f,1700d,1700d,1700d,1700d));
        operadores.put(17,new Operadores(1,"Strong QC (-)","C+-",0.1875f,1700d,1700d,1700d,1700d));
        operadores.put(18,new Operadores(1,"Strong QC","C+",0.1250f,1700d,1700d,1700d,1700d));
        operadores.put(19,new Operadores(1,"Stronc QC (+)","C++",0.0625f,1700d,1700d,1700d,1700d));
        operadores.put(20,new Operadores(1,"Conjunction","C",0.0000f,1700d,1700d,1700d,1700d));
    }
}
