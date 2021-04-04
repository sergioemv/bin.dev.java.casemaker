/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.testcase;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.TestCase;

import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMTestCaseDeleteAction extends AbstractAction implements Action {
	public CMTestCaseDeleteAction() {
		   super(CMMessages.getString("POPUPMENU_ITEM_DELETE_TEST_CASE"));    
		    // Set tool tip text
		    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_TEST_CASE"));

		    putValue(Action.SMALL_ICON,  CMAction.TESTCASE_DELETE.getIcon());
		    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_1_MNEMONIC").charAt(0));
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));
		    
		}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		  TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
		  
			if( selectedTestCase != null) {
			    int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_TEST_CASE"),CMMessages.getString("LABEL_DELETE_TEST_CASE"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
				if( confirmation == JOptionPane.YES_OPTION) {
					CMUndoMediator.getInstance().doEdit(TestCaseManager.INSTANCE.deleteTestCase(selectedTestCase, selectedTestCase.getStructure()));
		  
				  }
			}
		    CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews().requestFocus();
		    //CMApplication.frame.getCMTestCaseViews().requestFocus();
	}

}
