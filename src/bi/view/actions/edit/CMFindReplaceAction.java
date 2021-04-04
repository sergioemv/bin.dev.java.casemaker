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
public class CMFindReplaceAction extends AbstractAction implements Action {

	public CMFindReplaceAction() {
		super(CMMessages.getString("MENU_ITEM_FIND_REPLACE"));    
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_FIND_REPLACE"));
	    putValue(Action.SMALL_ICON, CMAction.FINDREPLACE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_SEARCH_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, Event.CTRL_MASK));
	}

	public void actionPerformed(ActionEvent e) {

		CMApplication.frame.eventMouseClicked(null);
		CMApplication.frame.setWaitCursor(true);
	    Component selectedComponent = CMApplication.frame.getSelectedComponent();

		if (selectedComponent==CMApplication.frame.getBusinessRulesPanelView()) {
			CMApplication.frame.setWaitCursor(false);
			CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().findReplace(e);
		}
		CMApplication.frame.setWaitCursor(false);

	}

}
