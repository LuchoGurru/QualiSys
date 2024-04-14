/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LSP;

import java.util.ArrayList;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author luciano.gurruchaga
 */
public class QsInstancia {
    String nombre;
    Map<String,Double> valores;

    public QsInstancia(String nombre, Map<String,Double> valores) {
        this.nombre = nombre;
        this.valores = valores;
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Map<String,Double> getValores() {
        return valores;
    }

    public void setValores(Map<String,Double> valores) {
        this.valores = valores;
    }
    
    public JSONObject toJSON(){
        JSONObject jsonInst= new JSONObject();
        jsonInst.put("nombre", this.nombre);
        ArrayList<String> keyValores = new ArrayList<>(valores.keySet());    
        JSONArray valoresI = new JSONArray();
        for(String k : keyValores){                                          // Por cada variable clave, valor Double
            JSONObject v = new JSONObject();
            v.put("varID", k);
            v.put("valor", valores.get(k));
            valoresI.put(v);// Armo listo de valores
        }
        jsonInst.put("valores",valoresI);
        return jsonInst;
    }
}
