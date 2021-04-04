package bi.view.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Document;

public class JTextAreaFocusChangeable extends JTextArea {

	public JTextAreaFocusChangeable() {
		super();
		   this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		   this.getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
		   this.getInputMap(JComponent.WHEN_FOCUSED ).put(
			        KeyStroke.getKeyStroke(new Character('	'),0), nextFocusAction.getValue(Action.NAME));
	}

	public JTextAreaFocusChangeable(int arg0, int arg1) {
		super(arg0, arg1);
		 this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		   this.getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
		   this.getInputMap(JComponent.WHEN_FOCUSED ).put(
			        KeyStroke.getKeyStroke(new Character('	'),0), nextFocusAction.getValue(Action.NAME));
		 
	}

	public JTextAreaFocusChangeable(String arg0) {
		super(arg0);
		 this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		   this.getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
		   this.getInputMap(JComponent.WHEN_FOCUSED ).put(
			        KeyStroke.getKeyStroke(new Character('	'),0), nextFocusAction.getValue(Action.NAME));
		 
	}

	public JTextAreaFocusChangeable(String arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
		 this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		   this.getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
		   this.getInputMap(JComponent.WHEN_FOCUSED ).put(
			        KeyStroke.getKeyStroke(new Character('	'),0), nextFocusAction.getValue(Action.NAME));
		 
	}

	public JTextAreaFocusChangeable(Document arg0) {
		super(arg0);
		 this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		   this.getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
		   this.getInputMap(JComponent.WHEN_FOCUSED ).put(
			        KeyStroke.getKeyStroke(new Character('	'),0), nextFocusAction.getValue(Action.NAME));
	}

	public JTextAreaFocusChangeable(Document arg0, String arg1, int arg2,
			int arg3) {
		super(arg0, arg1, arg2, arg3);
		 this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		   this.getActionMap().put(prevFocusAction.getValue(Action.NAME), prevFocusAction);
		   this.getInputMap(JComponent.WHEN_FOCUSED ).put(
			        KeyStroke.getKeyStroke(new Character('	'),0), nextFocusAction.getValue(Action.NAME));
	}

//	 Add actions
    
    // The actions
    public Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
        public void actionPerformed(ActionEvent evt) {
            ((Component)evt.getSource()).transferFocus();
        }
    };
    public Action prevFocusAction = new AbstractAction("Move Focus Backwards") {
        public void actionPerformed(ActionEvent evt) {
            ((Component)evt.getSource()).transferFocusBackward();
        }
    };

}
