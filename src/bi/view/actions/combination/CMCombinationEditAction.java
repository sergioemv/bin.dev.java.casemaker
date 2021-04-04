/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import java.awt.Event;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.Combination;
import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.view.actions.CMAbstractAction;
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
public class CMCombinationEditAction extends CMAbstractAction {
	public CMCombinationEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_COMBINATION"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EDIT_1_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		getWarningMessages().clear();
	   Combination selectedCombination = CombinationManager.getSelectedCombination();
		 CMDefaultDialog dlg = new CMDefaultDialog();
		   dlg.setSize(CMCombinationEditController.DEFAULT_DIALOG_SIZE);
		   CMCombinationEditController editControl = new CMCombinationEditController(selectedCombination.getDependency().getLnkStructure(), selectedCombination);
		   dlg.setJPanelContained(editControl.getPanel());
		   dlg.setTitle(selectedCombination.getName()+" - "+this.getValue(Action.NAME));
		   dlg.show();
		    CMApplication.frame.setWaitCursor(false);
		    if (dlg.getModalResult() == CMModalResult.OK){
		    	CMCompoundEdit ce = new CMCompoundEdit();
		    	ce.addEdit(new CMComponentAwareEdit());
		    	ce.addEdit(editControl.applyChanges());
		    	CMApplication.frame.getCMTestCaseViews().deleteAllTestCaseViews();
		    	CMApplication.frame.getCMTestCaseViews().addTestCaseViews(StructureManager.getSelectedStructure());
		    	CMUndoMediator.getInstance().doEdit(ce);	
				getWarningMessages().addAll(editControl.getWarningMessages());
		    }
		    dlg.dispose();
		
			CMApplication.frame.getCMGridCombinationViews().requestFocus();
	}

}
