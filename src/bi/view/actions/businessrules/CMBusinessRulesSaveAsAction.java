package bi.view.actions.businessrules;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

@SuppressWarnings("serial")
public class CMBusinessRulesSaveAsAction extends AbstractAction implements
Action {


	/**
	 * 
	 */
	public CMBusinessRulesSaveAsAction() {
		super(CMMessages.getString("LABEL_BR_SAVEAS_BUTTON"));//$NON-NLS-1$  
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TOOLTIP_BR_SAVEAS_BUTTON"));
	    putValue(Action.SMALL_ICON, CMAction.BI_BUSINESS_RULES_SAVE_AS.getIcon());
	//    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, Event.CTRL_MASK));
	//	putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_IMPORT_MNEMONIC").charAt(0));

	}

	public void actionPerformed(ActionEvent e) {
		CMApplication.frame.getBusinessRulesPanelView().saveAsFile();

	}

}
