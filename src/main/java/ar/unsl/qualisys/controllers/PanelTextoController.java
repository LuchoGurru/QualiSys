/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ar.unsl.qualisys.controllers;

import ar.unsl.qualisys.Sesion;
import ar.unsl.qualisys.componentes.nodos.QsNodo;
import ar.unsl.qualisys.paneles.grafo.DrawAndDropView;
import ar.unsl.qualisys.paneles.texto.TextEditorView;
import ar.unsl.qualisys.paneles.texto.memento.CaretTaker;
import ar.unsl.qualisys.paneles.texto.memento.Originator;
import ar.unsl.qualisys.utils.Item;
import ar.unsl.qualisys.utils.JTextPaneUtils;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JTextPane;

/**
 *
 * @author luciano
 */
public class PanelTextoController {
    
    private static PanelTextoController controller;

    private TextEditorView vista;
    private Sesion sesion = null;
    private Originator originator;
    private CaretTaker caretTaker;
    
    public static int cantOperadores = -1;



    private PanelTextoController(){
        this.sesion = Sesion.getInstance();
    }
    
    public void setvista(TextEditorView vista){
        this.vista = vista; 
        this.originator = new Originator();
        this.caretTaker = new CaretTaker();
    }
    
    public static PanelTextoController getInstance(){
        if(controller == null){
            controller = new PanelTextoController();
        }
        return controller;
    }

    
    
    
    /**
     * Reduce la numeracion del item anterior. numeracion anterior es *.*.n
     * @return *.*
     */
    private String quitarNivel(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        String numeracionDecrementada = "";
        for (int i = 0; i < numeracionAnterior.length - 1; i++) { // MENOS UNO 
            numeracionDecrementada += numeracionAnterior[i] + ".";
        }
        return numeracionDecrementada;
    }

    /**
     * Aumenta la numeracion del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*.n+1
     */
    public String aumentarNumeracion(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        int numeracionAumentada = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1]) + 1;
        String numeracion = "";

        // Concateno la numeracion anterior
        for (int i = 0; i < numeracionAnterior.length - 1; i++) {
            numeracion += numeracionAnterior[i] + ".";
        }
        numeracion += numeracionAumentada + ".";
        return numeracion;
    }

    /**
     * decrementa la numeracion del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*.n-1
     */
    private String decrementarNumeracion(Item it) {
        String[] numeracionAnterior = it.getNumeration().split("\\.");
        int numeracionDecrementada = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1]) - 1;
        String numeracion = "";

        // Concateno la numeracion anterior
        for (int i = 0; i < numeracionAnterior.length - 1; i++) {
            numeracion += numeracionAnterior[i] + ".";
        }
        numeracion += numeracionDecrementada + ".";
        return numeracion;
    }

    /**
     * Aumenta el nivel del item anterior. numeracion anterior es *.*.n
     *
     * @return *.*.n.1
     */
    public String aumentarNivel(Item it) {
        //int numeracion = Integer.parseInt(numeracionAnterior[numeracionAnterior.length - 1 ]) + 1;
        String nuevoNivel = it.getNumeration() + "1.";
        return nuevoNivel;

    }

    // Function to count words in a string
    public static int contarPalabras(String text) {
        StringTokenizer tokenizer = new StringTokenizer(text);
        return tokenizer.countTokens();
    }
    public boolean isRenglonBienFormado(String renglon) {
        String regex = "^\\t*(\\d+\\.)+[ ].*(\\r\\n|\\r|\\n|^$)?$"; // para la mariconeada de windol! \r
        System.out.println("renglon.matches(regex) = " + renglon.matches(regex));
        return renglon.matches(regex);
    }

    public Item armarItem(String renglon, int numeroDeLinea) {
        String[] arregloRenglon = renglon.split(" ");
        String numeracionAndNivel = arregloRenglon[0]; //obtengo la primer parte del renglon
        int nivelItem = numeracionAndNivel.split("\t").length - 1; //cantidad de tabs
        String numeracionItem = numeracionAndNivel.split("\t")[nivelItem];  //numeracion del indice
        String textoItem = renglon.substring(arregloRenglon[0].length() + 1); // obtengo el resto del texto
        System.out.println("textoItem = " + textoItem);
        return new Item(numeroDeLinea, nivelItem, numeracionItem, textoItem);
    }
    //COMMANDO 
    /**
     * Este método se activa con CTRL + ENTER
     */
    public void quitarNivelANumeracion(JTextPane panelDeTexto, Item renglonActual) {
        int nivelAc = renglonActual.getNivel();
        if (nivelAc > 0) {
            String texto = panelDeTexto.getText();
            int caret = panelDeTexto.getCaretPosition();
            String preCaret = texto.substring(0, caret);
            String posCaret = texto.substring(caret);
            renglonActual.setNivel(renglonActual.getNivel() - 1); // LE SACO UN NIVEL
            renglonActual.setNumeration(quitarNivel(renglonActual));
            renglonActual.setNumeration(aumentarNumeracion(renglonActual));
            renglonActual.setCadenaDeTexto("");
            String agregado = "\n" + renglonActual.constructRenglon() + " ";
            int caretAumentado = caret + agregado.length();
            panelDeTexto.setText(preCaret + agregado + posCaret);
            vista.actualizarEstructuraDeTexto();
            panelDeTexto.setCaretPosition(caretAumentado);
        } else {
            vista.setTURN_OFF_LISTENERS(false);
        }
    }

    /**
     * Este método se activa con ENTER
     */
    public void agregarNumeracionANivel(JTextPane panelDeTexto, Item renglonActual) {
        String texto = panelDeTexto.getText();
        int caret = panelDeTexto.getCaretPosition();
        String preCaret = texto.substring(0, caret);
        String posCaret = texto.substring(caret);
        String tabs = "";
        for (int i = 0; i < renglonActual.getNivel(); i++) {
            tabs += "\t";
        }
        String agregado = "\n" + tabs + aumentarNumeracion(renglonActual) + " ";
        int caretAumentado = caret + agregado.length();
        panelDeTexto.setText(preCaret + agregado + posCaret);
        vista.actualizarEstructuraDeTexto();
        panelDeTexto.setCaretPosition(caretAumentado); // Actualizo caret
    }

    /**
     * Este método se activa con SHIFT + ENTER
     */
    public void agregarNivelANumeracion(JTextPane panelDeTexto, Item renglonActual) {
        //renglonActual.getNivel();
        String texto = panelDeTexto.getText();
        int caret = panelDeTexto.getCaretPosition();
        String preCaret = texto.substring(0, caret);
        String posCaret = texto.substring(caret);
        renglonActual.setNivel(renglonActual.getNivel() + 1); // LE AGREGO UN NIVEL
        renglonActual.setNumeration(renglonActual.getNumeration() + "1.");
        renglonActual.setCadenaDeTexto("");
        String agregado = "\n" + renglonActual.constructRenglon() + " ";
        int caretAumentado = caret + agregado.length();
        panelDeTexto.setText(preCaret + agregado + posCaret);
        vista.actualizarEstructuraDeTexto();
        panelDeTexto.setCaretPosition(caretAumentado); // Actualizo caret
    }

    /**
     * Este Método se activa con SHIFT + TAB
     */
    public void quitarNivelARenglon(JTextPane panelDeTexto, Item renglonActual) {
        if (renglonActual.getNivel() > 0) {
            int inicioRenglon = JTextPaneUtils.getPrincipioDeRow(panelDeTexto);
            int finRenglon = JTextPaneUtils.getFinalDeRow(panelDeTexto);
            String texto = panelDeTexto.getText();
            int caret = inicioRenglon;
            String preCaret = texto.substring(0, caret);
            String posCaret = texto.substring(finRenglon);
            renglonActual.setNivel(renglonActual.getNivel() - 1); // LE SACO UN NIVEL
            renglonActual.setNumeration(quitarNivel(renglonActual));
            renglonActual.setNumeration(decrementarNumeracion(renglonActual));
            String agregado = renglonActual.constructRenglon();
            int caretAumentado = caret + agregado.length();
            JTextPaneUtils.setTextoDeLineaByCaret(panelDeTexto, agregado);
            vista.actualizarEstructuraDeTexto();
            panelDeTexto.setCaretPosition(caretAumentado);//Actualizo caret
        } else {
            vista.setTURN_OFF_LISTENERS(false);
        }
    }

    /**
     * Este Método se actia con TAB
     */
    public void agregarNivelARenglon(JTextPane panelDeTexto, Item renglonActual, ArrayList<Item> renglones) {
        if (renglonActual.getNumeroDeLinea() > 0) {
            Item renglonPadre = renglones.get(renglonActual.getNumeroDeLinea() - 1);
            if (renglonPadre.getNivel() < renglonActual.getNivel()) {
                vista.setTURN_OFF_LISTENERS(false); // mucho muy importante
                return; // para explicacion, no puede tener mas de dos niveles mas adentro que el padre
            }
            int inicioRenglon = JTextPaneUtils.getPrincipioDeRow(panelDeTexto);
            int finRenglon = JTextPaneUtils.getFinalDeRow(panelDeTexto);
            String texto = panelDeTexto.getText();
            int caret = panelDeTexto.getCaretPosition(); // la uso para obtener el fin de linea dsp
            String preCaret = texto.substring(0, inicioRenglon);
            String posCaret = texto.substring(finRenglon);
            renglonActual.setNivel(renglonActual.getNivel() + 1); // LE AGREGO UN NIVEL
            renglonActual.setNumeration(renglonActual.getNumeration() + "1."); // LE SACO UN NIVEL
            String agregado = renglonActual.constructRenglon();
            JTextPaneUtils.setTextoDeLineaByCaret(panelDeTexto, agregado);
            vista.actualizarEstructuraDeTexto();
            panelDeTexto.setCaretPosition(caret);// queda ajustar el caret con nuevo fin de string.
            finRenglon = JTextPaneUtils.getFinalDeRow(panelDeTexto);
            panelDeTexto.setCaretPosition(finRenglon);
        } else {
            vista.setTURN_OFF_LISTENERS(false);
        }

    }

    /**
     * Limpia los \r agregados en fin de linea en WINDOWS:(\r\n) UNIX:(\n) como
     * correspondee.
     *
     * @param texto
     * @return
     */
    public String limpiarWindowsCarryReturn(String texto) {
        texto = texto.replaceAll("\\r", "");
        return texto;
    }
        public void setTexto(String texto) {
        if (vista.isTURN_OFF_LISTENERS() == false) {

            vista.setTURN_OFF_LISTENERS(true);
            if (texto.contains("\r")) {
                texto = limpiarWindowsCarryReturn(texto);
            }
            this.originator = new Originator(); // reinicializo Originator del memento
            this.caretTaker = new CaretTaker(); // reinicializo carettaker de los mementos.
            vista.getPanelDeTexto().setText(texto);
            vista.actualizarEstructuraDeTexto();//Le paso lineas distintas para que actualice todo el texto.
        }
    }

    public void setTextoConCaret(String texto, int caret) {
        if (vista.isTURN_OFF_LISTENERS() == false) {
            vista.setTURN_OFF_LISTENERS(true);
            vista.getPanelDeTexto().setText(texto);
            try {
                vista.getPanelDeTexto().setCaretPosition(caret);
            } catch (IllegalArgumentException ia) {
                System.out.println("Caret Exception en SetTexto con Caret, probamos con uno menos = ");
                System.out.println("texto = " + texto);
                System.out.println("caret = " + caret);
                System.out.println("texto.length = " + texto.length());
                vista.getPanelDeTexto().setCaretPosition(texto.length());
            }
            vista.actualizarEstructuraDeTexto();//Le paso lineas distintas para que actualice todo el texto..
            
        }
    }

    public boolean isTextoBienFormado() {
        String[] lineas = vista.getPanelDeTexto().getText().split("\n");
        int i = 0;
        while (i <= lineas.length - 1 && isRenglonBienFormado(lineas[i])) {
            i++;
        }
        return i == lineas.length ;
    }

    /**
     * Devuelve solo los renglones que son Variables de Preferencia
     *
     * @return
     */
    public ArrayList<Item> getVariablesDelTexto() { 
        String texto = vista.getPanelDeTexto().getText();
        String[] lineas = texto.split("\n");
        ArrayList<Item> renglones = new ArrayList<>();
        for (int i = 0; i < lineas.length; i++) {
            Item renglonActual = armarItem(lineas[i], i); // init  
            renglones.add(renglonActual);
        }
        boolean primero = true;
        Item anterior = null;
        ArrayList<Item> rVariables = new ArrayList<>();
        for (Item renglon : renglones) {
            if (primero) {
                anterior = renglon;
                primero = false;
                continue;
            }
            if (anterior.getNivel() >= renglon.getNivel()) { // Si el nivel actual es el mismo que el anterior entonces el nivel anterior es una variable .
                rVariables.add(anterior);
            }
            anterior = renglon;
        }
        rVariables.add(anterior);    //El ultimo nivel es una variable if or if         
        return rVariables;
    }
}
