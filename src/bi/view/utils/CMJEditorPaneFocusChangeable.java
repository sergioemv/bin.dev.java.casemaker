package bi.view.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.KeyStroke;

public class CMJEditorPaneFocusChangeable extends JEditorPane {

	public CMJEditorPaneFocusChangeable() {
		this.getActionMap().put(previousFocusAction.getValue(Action.NAME), previousFocusAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB,java.awt.event.InputEvent.SHIFT_MASK), previousFocusAction.getValue(Action.NAME));
		this.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(
		        KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB,0), nextFocusAction.getValue(Action.NAME));
	}

	 public Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
	        public void actionPerformed(ActionEvent evt) {
	            ((Component)evt.getSource()).transferFocus();
	            if (evt.getSource() instanceof JEditorPane)
	            {
	              int l_finalPosition = ((JEditorPane)evt.getSource()).getText().length()-1;
	              if (l_finalPosition>=0)
	              {
	            	  String l_lastChar =((JEditorPane)evt.getSource()).getText().substring(l_finalPosition);
	            	  if (l_lastChar.equalsIgnoreCase("	"))
	            	  {
	            		  String l_newText = ((JEditorPane)evt.getSource()).getText().substring(0,l_finalPosition);
	            		  ((JEditorPane)evt.getSource()).setText(l_newText);
	            	  }
	              }
	            }
	        }
	    };
	    public Action previousFocusAction = new AbstractAction("Move Focus Backwards") {
	        public void actionPerformed(ActionEvent evt) {
	            ((Component)evt.getSource()).transferFocusBackward();
	            if (evt.getSource() instanceof JEditorPane)
	            {
	              int l_finalPosition = ((JEditorPane)evt.getSource()).getText().length()-1;
	              if (l_finalPosition>=0)
	              {
	            	  String l_lastChar =((JEditorPane)evt.getSource()).getText().substring(l_finalPosition);
	            	  if (l_lastChar.equalsIgnoreCase("	"))
	            	  {
	            		  String l_newText = ((JEditorPane)evt.getSource()).getText().substring(0,l_finalPosition);
	            		  ((JEditorPane)evt.getSource()).setText(l_newText);
	            	  }
	              }
	            }
	        }
	    };


}