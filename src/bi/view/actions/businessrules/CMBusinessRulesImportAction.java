package bi.view.actions.businessrules;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.TestObject;
import bi.view.actions.CMAction;
import bi.view.businessrulesviews.brimport.CMBusinessRulesImportWizard;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

public class CMBusinessRulesImportAction extends AbstractAction implements
		Action {

	
	/**
	 * 
	 */
	public CMBusinessRulesImportAction() {
		super(CMMessages.getString("LABEL_BUTTON_IMPORT_FILE"));//$NON-NLS-1$  
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TOOLTIP_BUTTON_IMPORT_FILE"));
	    putValue(Action.SMALL_ICON, CMAction.BI_BUSINESS_RULES_IMPORT.getIcon());
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, Event.CTRL_MASK));
		putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_IMPORT_MNEMONIC").charAt(0));

	}

	public void actionPerformed(ActionEvent e) {
		
		 CMBusinessRulesImportWizard wizard = new CMBusinessRulesImportWizard();		 
		 TestObject selectedTestObject= CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	        wizard.getDialog().setTitle(selectedTestObject.getName()+" - "+CMMessages.getString("BUSINESS_RULES_IMPORTDIALOG_TITLE"));
	        wizard.showModalDialog();

	}

	
}
