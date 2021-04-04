/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.testcase;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.Structure;
import model.TestCase;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.controller.editcontrol.CMTestCaseEditController;
import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMTestCaseAssignStdCombinationAction extends AbstractAction
		implements Action {
	public CMTestCaseAssignStdCombinationAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_ASSIGN_STD_COMBINATION_TO_TEST_CASE"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_STD_COMBINATION_TO_TEST_CASE"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_ASSIGN_STDCOMBINATION.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_STD_COMBINATION_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		        TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
		        Structure selectedStructure = StructureManager.getSelectedStructure();
		    
			    if( selectedTestCase != null) {

			    	  CMDefaultDialog dlg = new CMDefaultDialog();
					   dlg.setSize(CMTestCaseEditController.DEFAULT_DIALOG_SIZE);
					   CMTestCaseEditController caseEditController = new CMTestCaseEditController(selectedTestCase);	
					   dlg.setJPanelContained(caseEditController.getPanel().getCMPanelStdCombinationsAssigment());
					   dlg.setTitle(selectedTestCase.getName()+selectedTestCase.getStateName()+" - "+this.getValue(Action.NAME));
					    dlg.show();
					    CMApplication.frame.setWaitCursor(false);
					    if (dlg.getModalResult() == CMModalResult.OK){
					    	CMCompoundEdit ce = new CMCompoundEdit();
					    	ce.addEdit(new CMComponentAwareEdit());
					    	ce.addEdit(caseEditController.applyChanges());
					    	ce.addEdit(CMViewEditFactory.INSTANCE.createSelectTestCaseGridViewEdit(selectedTestCase));
					    	CMUndoMediator.getInstance().doEdit(ce);
					    	CMApplication.frame.getCMTestCaseViews().getLnkCMElementViews().update();
					    }
					    dlg.dispose();
					    
			}
			    CMApplication.frame.getCMTestCaseViews().requestFocus();
	}
}
