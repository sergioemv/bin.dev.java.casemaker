/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.stdcombination;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.StdCombination;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
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
public class CMStdCombinationEditAction extends AbstractAction implements
		CMEnabledAction {
	public CMStdCombinationEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_STD_COMBINATION"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_STANDARD_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.STDCOMBINATION_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("POPUPMENU_ITEM_EDIT_STD_COMBINATION_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		
	        StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
	        CMDefaultDialog dlg = new CMDefaultDialog();
			 dlg.setSize(CMCombinationEditController.DEFAULT_DIALOG_SIZE);
			 CMCombinationEditController editControl = new CMCombinationEditController(StructureManager.getSelectedStructure(), selectedStdCombination);
			 dlg.setJPanelContained(editControl.getPanel());
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
	public boolean calculateEnabled() {
		return CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination()!=null;
	}
	
}
