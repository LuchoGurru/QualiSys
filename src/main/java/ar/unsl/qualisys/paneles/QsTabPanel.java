package ar.unsl.qualisys.paneles;

import ar.unsl.qualisys.componentes.QsTextPanel;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class QsTabPanel extends JTabbedPane {
    // POPUPMENU
    JPopupMenu contextual = new JPopupMenu();
    
    public QsTabPanel (){
        super();
  //    this.addTab("Variables de Preferencia",new QsTextPanel());
        this.setVisible(true);
    }


}
