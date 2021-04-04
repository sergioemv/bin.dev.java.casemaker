/**
 *
 */
package bi.view.actions.businessrules;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;

/**
 * @author hcanedo
 *
 */
@SuppressWarnings("serial")
public class CMBusinessRulesCheckAndGenerateAction extends AbstractAction implements Action{


	/**
	 *
	 */
	public CMBusinessRulesCheckAndGenerateAction() {
		super(CMMessages.getString("MENU_ITEM_BUSINESS_RULES_CHECK_AND_GENERATE_TEST_CASES")); //$NON-NLS-1$
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("MENU_ITEM_BUSINESS_RULES_CHECK_AND_GENERATE_TEST_CASES"));
	    putValue(Action.SMALL_ICON, CMAction.BUSINESSRULES_CHECK_AND_GENERATE.getIcon());
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, Event.CTRL_MASK|Event.SHIFT_MASK));
		putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_BUSINESS_RULES_CHECK_AND_GENERATE_TEST_CASES_MNEMONIC").charAt(0));

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(((CMBusinessRulesCheckAction)CMAction.BUSINESSRULES_CHECK.getAction()).checkSyntax()){
			CMAction.BUSINESSRULES_GENERATE.getAction().actionPerformed(e);
		}

	}

}
