/**
 * 
 */
package bi.view.actions.edit;

import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author hcanedo
 *
 */
public class CMSelectAllAction extends AbstractAction implements Action {

	/**
	 * 
	 */
	public CMSelectAllAction() {
		super(CMMessages.getString("MENU_ITEM_SELECT_ALL"));    
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_SELECT_ALL"));
	    putValue(Action.SMALL_ICON, CMAction.SELECTALL.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_SELECT_ALL_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		CMApplication.frame.eventMouseClicked(null);
		CMApplication.frame.setWaitCursor(true);
	    Component selectedComponent = CMApplication.frame.getSelectedComponent();

		if (selectedComponent==CMApplication.frame.getBusinessRulesPanelView()) {
			CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().selectAll(e);
		}
		CMApplication.frame.setWaitCursor(false);
	}

}
