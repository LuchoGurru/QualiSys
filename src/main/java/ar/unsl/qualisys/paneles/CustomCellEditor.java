package ar.unsl.qualisys.paneles;


import ar.unsl.qualisys.controllers.PanelEvaluacionController;
import java.awt.Component;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CustomCellEditor extends DefaultCellEditor {
    
    private boolean isValid = true;
    private int rowSelected=-1;
    private PanelEvaluacionController evaluacionController;
    
    public CustomCellEditor(PanelEvaluacionController evaluacionController, JTextField textField) {
        super(textField);
        this.evaluacionController = evaluacionController;
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
        evaluacionController.agregarValor(Double.valueOf(value), rowSelected);
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
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
        isValid = true; // Restablece la validación al editar una nueva celda.
        rowSelected=row;
        evaluacionController.setInstanciaSeleccionada(col);
       // if(instanciaSeleccionada==column){
       //     System.out.println("yout rock");
        //}
        return super.getTableCellEditorComponent(table, value, isSelected, row, col);
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