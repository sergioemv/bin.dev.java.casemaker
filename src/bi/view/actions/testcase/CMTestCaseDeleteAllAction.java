/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.testcase;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.Structure;
import model.TestCase;
import model.util.CMModelEventHandler;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMTestCaseDeleteAllAction extends AbstractAction implements Action {
	public CMTestCaseDeleteAllAction() {
		   super(CMMessages.getString("POPUPMENU_ITEM_DELETE_ALL_TESTCASES"));    
		    // Set tool tip text
		    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_TEST_CASE"));

		    putValue(Action.SMALL_ICON,  CMAction.TESTCASE_DELETE.getIcon());
		    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_ALL_MNEMONIC").charAt(0));
		    
		}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
			Structure selectedStructure = StructureManager.getSelectedStructure();
		    int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_TEST_CASES"),CMMessages.getString("LABEL_DELETE_ALL_TEST_CASES"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
			if( confirmation == JOptionPane.YES_OPTION) {
			  CMCompoundEdit ce = new CMCompoundEdit();
			  CMModelEventHandler.setNotifyEnabled(false);
			  for (TestCase testCase : selectedStructure.getTestCases())
				  ce.addEdit(TestCaseManager.INSTANCE.deleteTestCase(testCase,selectedStructure));
			  CMModelEventHandler.setNotifyEnabled(true);
		      CMUndoMediator.getInstance().doMassiveEdit(ce);
		      CMApplication.frame.getCMTestCaseViews().getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().getM_CMDescriptionTestCaseViews().deleteAllDescriptionTestCaseViews();
		      CMApplication.frame.getCMTestCaseViews().update();
		    }
		  }


}
