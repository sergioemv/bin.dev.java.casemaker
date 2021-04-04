/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import javax.swing.Action;

import model.Combination;
import model.Structure;
import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMEffectsEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.effect.CMEffectsEditDialog;
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
public class CMCombinationEditEffectsAction extends CMAbstractAction{
	public CMCombinationEditEffectsAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECTS"));
	     // Set an icon
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECTS"));
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_EDIT_EFFECTS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECT_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		Combination selectedCombination = CombinationManager.getSelectedCombination();
	    if (selectedCombination != null) {
	    	CMApplication.frame.setWaitCursor(true);
	    	Structure structure = StructureManager.getSelectedStructure();
//	    	create the manager to control the edition of the effects 
			CMEffectsEditController effectsEditController = new CMEffectsEditController(selectedCombination,structure); 
			//create the dialog to edit the effects
			CMEffectsEditDialog dlg = new CMEffectsEditDialog(effectsEditController.getPanel());
			//dlg.setEditEffectsPanel(effectsEditController.getPanel());
			dlg.setTitle(selectedCombination.getName()+" - "+this.getValue(Action.NAME));
			dlg.show();
			CMApplication.frame.setWaitCursor(false);
			if (dlg.getModalResult() == CMModalResult.OK)
			{
				CMCompoundEdit ce = new CMCompoundEdit();
				ce.addEdit(new CMComponentAwareEdit());
				ce.addEdit(effectsEditController.applyChanges());
				getWarningMessages().addAll(effectsEditController.getWarningMessages());
				CMUndoMediator.getInstance().doEdit(ce);
			}
			dlg.dispose();
          }
	    
	     CMApplication.frame.getCMGridCombinationViews().requestFocus();
	}

}
