/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import javax.swing.Action;

import model.Combination;
import model.Effect;
import model.Structure;
import model.edit.effect.CMAddEffectNotifiedModelEdit;
import bi.controller.CombinationManager;
import bi.controller.EffectManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMEffectEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.effect.CMEffectDialog;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;
import bi.view.utils.CMModalResult;

/**
 * @author smoreno
 *
 */
public class CMCombinationCreateAssingEffectsAction extends CMAbstractAction {
	public CMCombinationCreateAssingEffectsAction() {
		
		
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_AND_ASSIGN_CAUSE_EFFECT"));
		
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_CREATE_AND_ASSIGN_CAUSE_EFFECT"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_CREATE_ASSIGN_EFFECTS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_CAUSE_EFFECTS_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		// TODO Auto-generated method stub 
		int selectedColumn = CMApplication.frame.getCMGridCombinationViews().getSelectionModel().getLeadColumn();
		int lastrow = CMApplication.frame.getCMGridCombinationViews().getRowCount();
		Combination selectedCombination = CombinationManager.getSelectedCombination();
		  Structure structure = StructureManager.getSelectedStructure();
		  CMEffectDialog dlg = new CMEffectDialog();
		  Effect effect = EffectManager.INSTANCE.createEffect(structure);
			 CMEffectEditController editController = new CMEffectEditController(effect);
			 editController.setStructure(structure);
			 dlg.setTitle(selectedCombination.getName()+" - "+this.getValue(Action.NAME));
			 dlg.setEffectDialogPanel(editController.getPanel());
		     dlg.show();
		        if (dlg.getModalResult() == CMModalResult.OK) {
		        	//create the effect 
		        	CMCompoundEdit ce = new CMCompoundEdit();
		           	ce.addEdit(new CMComponentAwareEdit());
		           	//add the effect to the structure
		           	ce.addEdit(new CMAddEffectNotifiedModelEdit(structure,effect));
		        	structure.addEffect(effect);
		        	//add the effect to the equivalence class
		        	ce.addEdit(new CMAddEffectNotifiedModelEdit(selectedCombination,effect));
		        	selectedCombination.addEffect(effect);
//		        	set the effects properties
		        	ce.addEdit(editController.applyChanges());
		        	getWarningMessages().addAll(editController.getWarningMessages());
		        	 //select the specified  effect from the equivalence class
		        	CMApplication.frame.getCMGridCombinationViews().changeSelection(lastrow-1,selectedColumn,false,false);
		        	 CMUndoMediator.getInstance().doEdit(ce);
		        }
		        dlg.dispose();
		    
	}

}
