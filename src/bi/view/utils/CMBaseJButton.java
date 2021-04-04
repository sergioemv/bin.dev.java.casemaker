//svonborries_13102005_begin
package bi.view.utils;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class CMBaseJButton extends JButton{

	public CMBaseJButton(){
		this.getActionMap().put(nextFocusAction.getValue(Action.NAME),nextFocusAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke('	',0),
				nextFocusAction.getValue(Action.NAME));
		this.getActionMap().put(previousFocusAction.getValue(Action.NAME),previousFocusAction);
		this.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke('	',InputEvent.SHIFT_MASK|InputEvent.SHIFT_DOWN_MASK),
				previousFocusAction.getValue(Action.NAME));

	}
	
	public Action nextFocusAction = new AbstractAction("Move Focus Forward"){

		public void actionPerformed(ActionEvent arg0) {
			((Component)arg0.getSource()).transferFocus();
			
			if(arg0.getSource() instanceof CMBaseJButton){
				//((Component)arg0.getSource()).requestFocus();
				((CMBaseJButton)arg0.getSource()).requestFocus();
				((CMBaseJButton)arg0.getSource()).setSelected(true);

			}
			
		}
		
	};
	
	public Action previousFocusAction = new AbstractAction("Move Focus Backward"){

		public void actionPerformed(ActionEvent arg0) {
			((Component)arg0.getSource()).transferFocusBackward();
			if(arg0.getSource() instanceof CMBaseJButton){
				//((Component)arg0.getSource()).requestFocus();
				((CMBaseJButton)arg0.getSource()).requestFocus();
				((CMBaseJButton)arg0.getSource()).setSelected(true);
				
			}
		}
		
	};
}
//svonborries_13102005_end