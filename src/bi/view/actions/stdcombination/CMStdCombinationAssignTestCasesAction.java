/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.stdcombination;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.EquivalenceClass;
import model.StdCombination;
import model.Structure;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.view.actions.CMAction;
import bi.view.actions.combination.CMPanelCombination;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMDefaultDialog;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMStdCombinationAssignTestCasesAction extends AbstractAction
		implements Action {
	public CMStdCombinationAssignTestCasesAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_ASSIGN_STD_COMBINATION_TO_TEST_CASES"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_STD_COMBINATION_TO_TEST_CASES"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.STDCOMBINATION_ASSIGN_TEST_CASES.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_TO_TEST_CASES_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		   StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
	        CMDefaultDialog dlg = new CMDefaultDialog();
			 dlg.setSize(CMCombinationEditController.DEFAULT_DIALOG_SIZE);
			 CMCombinationEditController editControl = new CMCombinationEditController(StructureManager.getSelectedStructure(), selectedStdCombination);
			 dlg.setJPanelContained(((CMPanelCombination)editControl.getPanel()).getPanelTestCasesAssigment());
			   dlg.setTitle(selectedStdCombination.getName()+" - "+this.getValue(Action.NAME));
			   dlg.show();
			    if (dlg.getModalResult() == CMModalResult.OK){
			    	CMCompoundEdit ce = new CMCompoundEdit();
			    	ce.addEdit(editControl.applyChanges());
			    	CMUndoMediator.getInstance().doEdit(ce);			
			    }
			    dlg.dispose();
	        CMApplication.frame.getCMStdCombinationViews().requestFocus();
	     }

}
