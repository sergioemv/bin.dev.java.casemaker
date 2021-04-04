/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.error;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.CMError;
import model.Structure;
import bi.controller.ErrorManager;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.errorviews.CMDialogAssignErrorToTestCases;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;

/**
 * @author smoreno
 *
 */
public class CMErrorAssignTestCasesAction extends AbstractAction implements
		Action {
	public CMErrorAssignTestCasesAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_ASSIGN_ERROR_TO_TEST_CASES"));    
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_ERROR_TO_TEST_CASES"));
	    putValue(Action.SMALL_ICON, CMAction.ERROR_ASSIGN_TESTCASES.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
	        CMDialogAssignErrorToTestCases cmDialogAssignErrorToTestCases;
	        CMError selectedCMError = ErrorManager.getSelectedCMError();
	        Structure selectedStructure = StructureManager.getSelectedStructure();
	        Vector oldTestCases = (Vector)selectedCMError.getM_TestCases().clone(); //fcastro_23082004
	        if (selectedCMError != null && selectedStructure != null) {
	            cmDialogAssignErrorToTestCases = new CMDialogAssignErrorToTestCases( selectedCMError, selectedStructure);
	            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	            Dimension dlgSize = cmDialogAssignErrorToTestCases.getPreferredSize();
	            cmDialogAssignErrorToTestCases.setLocation((screenSize.width - dlgSize.width) / 2,
	                (screenSize.height - dlgSize.height) / 2);
	            cmDialogAssignErrorToTestCases.show();
	            //fcastro_23082004_begin
	            if (cmDialogAssignErrorToTestCases.isEventJButtonOKClicked()) {
	                Vector newTestCases = (Vector)cmDialogAssignErrorToTestCases.getRightList().clone();
	                CMApplication.frame.getM_CMUndoMediator().changeAssignedTestCasesToError(CMApplication.frame.getCMErrorGridView().getSelectionModel().getLeadRow(),
	                    selectedCMError, oldTestCases, newTestCases);
	            }
	            //fcastro_23082004_end
	        }
	    }



}
