/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.Combination;
import model.Dependency;
import model.Structure;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.view.actions.CMAction;
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
public class CMCombinationAssingToTestCaseAction extends AbstractAction
		implements Action {
	private List<String> warningMessages;
	
	public CMCombinationAssingToTestCaseAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_ASSIGN_COMBINATION_TO_TEST_CASES"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_COMBINATION_TO_TEST_CASES"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_ASSIGN_TO_TESTCASE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_TO_TEST_CASES_MNEMONIC").charAt(0));
	 	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
			getWarningMessages().clear();
		  Combination selectedCombination = CombinationManager.getSelectedCombination();
		  Structure selectedStructure = StructureManager.getSelectedStructure();
		  Dependency selectedDependency = DependencyManager.getSelectedDependency();
		if( selectedCombination != null && selectedStructure != null) {
			CMDefaultDialog dlg = new CMDefaultDialog();
			   CMCombinationEditController editControl = new CMCombinationEditController(selectedStructure, selectedCombination);
			   dlg.setJPanelContained(editControl.getPanel().getPanelTestCasesAssigment());
			   dlg.setSize(new Dimension(598,250));
			   dlg.setTitle(selectedCombination.getName()+" - "+this.getValue(Action.NAME));
			   dlg.show();
			    CMApplication.frame.setWaitCursor(false);
			    if (dlg.getModalResult() == CMModalResult.OK){
			    	CMCompoundEdit ce = new CMCompoundEdit();
			    	ce.addEdit(new CMComponentAwareEdit());
			    	try {
			    		ce.addEdit(editControl.updateTestCasesAssigmentWithPanel());
					} catch (Exception e) {
						// TODO: handle exception
					}	    	
			    	CMApplication.frame.getCMTestCaseViews().deleteAllTestCaseViews();
			    	CMApplication.frame.getCMTestCaseViews().addTestCaseViews(StructureManager.getSelectedStructure());
			    	CMUndoMediator.getInstance().doEdit(ce);	
			    	if (editControl.getWarningMessages().size()>0)
						getWarningMessages().addAll(editControl.getWarningMessages());
			    }
			    dlg.dispose();
				CMApplication.frame.getCMGridCombinationViews().requestFocus();
    }
		CMApplication.frame.getCMGridCombinationViews().requestFocus();
	}

	public List<String> getWarningMessages() {
		if (warningMessages == null)
			warningMessages = new ArrayList<String>();
		return warningMessages;
	}
	//ccastedo ends 06.10.06
}
