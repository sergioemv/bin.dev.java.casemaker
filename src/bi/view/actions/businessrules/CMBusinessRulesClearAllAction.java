package bi.view.actions.businessrules;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

@SuppressWarnings("serial")
public class CMBusinessRulesClearAllAction extends AbstractAction implements
Action {

	/**
	 * 
	 */
	public CMBusinessRulesClearAllAction() {
		super(CMMessages.getString("BUTTON_CLEAR_BUSINESS_RULE_EDITOR_CONTENT"));//$NON-NLS-1$  
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TOOLTIP_CLEAR_BUSINESS_RULE_EDITOR_CONTENT"));
	    putValue(Action.SMALL_ICON, CMAction.BI_BUSINESS_RULES_CLEAR_ALL.getIcon());
	  //  putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, Event.CTRL_MASK));
	//	putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_IMPORT_MNEMONIC").charAt(0));

	}

	public void actionPerformed(ActionEvent e) {
		boolean readOnly= CMApplication.frame.getBusinessRulesPanelView().isReadOnly();
		if (!readOnly) {
			int confirmation = JOptionPane
					.showConfirmDialog(	CMApplication.frame,CMMessages.getString("LABEL_MESSAGE_DELETE_ALL_BUSINESS_RULES"),
							CMMessages.getString("TESTDATA_IMPORT_TITLE_INFORMATION"),
							JOptionPane.YES_NO_OPTION);
			if (confirmation == JOptionPane.YES_OPTION) {
				CMApplication.frame.getBusinessRulesPanelView().getCMBusinessRuleEditor().clear();
				CMApplication.frame.getBusinessRulesPanelView().setModified(true);

			}
		}
		
	}

}
