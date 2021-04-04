/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.stdcombination;

import java.awt.Event;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.StdCombination;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMCombinationEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
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
public class CMStdCombinationCreateAction extends CMAbstractAction implements
		Action {
	public CMStdCombinationCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_STD_COMBINATION"));    
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_STANDARD_COMBINATION"));
	    putValue(Action.SMALL_ICON, CMAction.STDCOMBINATION_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_1_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	    
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		Structure structure = StructureManager.getSelectedStructure();
        StdCombination newStdCombination = CombinationManager.INSTANCE.createStdCombination(structure);
        CMDefaultDialog dlg = new CMDefaultDialog();
		 dlg.setSize(CMCombinationEditController.DEFAULT_DIALOG_SIZE);
		 CMCombinationEditController editControl = new CMCombinationEditController(structure, newStdCombination);
		 
		 dlg.setJPanelContained(editControl.getPanel());
		   dlg.setTitle(newStdCombination.getName()+" - "+this.getValue(Action.NAME));
		   dlg.show();
		   CMApplication.frame.setWaitCursor(false);
		    if (dlg.getModalResult() == CMModalResult.OK){
		    	CMCompoundEdit ce = new CMCompoundEdit();
		    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(structure, newStdCombination));
		    	structure.addCombination(newStdCombination);
		    	ce.addEdit(editControl.applyChanges());
		    	getWarningMessages().addAll(editControl.getWarningMessages());	
		    	CMUndoMediator.getInstance().doEdit(ce);			
		    }
		    dlg.dispose();
		    CMApplication.frame.getCMStdCombinationViews().selectCell(newStdCombination, null);
        CMApplication.frame.getCMStdCombinationViews().requestFocus();
        
		
	}

}
