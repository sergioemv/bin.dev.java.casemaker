/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.Event;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.Effect;
import model.Structure;
import bi.controller.EffectManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMEffectEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.edit.CMViewEditFactory;
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
public class CMEffectEditAction extends CMAbstractAction  implements CMEnabledAction{

	public CMEffectEditAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EDIT_CAUSE_EFFECT"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EFFECT_EDIT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EDIT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		Structure structure = StructureManager.getSelectedStructure();
		 CMEffectDialog dlg = new CMEffectDialog();
			 //get the selected effect
		 Effect effect = EffectManager.getSelectedEffect();
		 //create a new effect edit controller
		 CMEffectEditController editController = new CMEffectEditController(effect);
		dlg.setEffectDialogPanel(editController.getPanel());
		 if (effect== null) return;
		 //set the dialog title
		 dlg.setTitle(effect.getName()+" - "+this.getValue(Action.NAME));
	        dlg.show();
	        if (dlg.getModalResult() == CMModalResult.OK) {

	        	CMCompoundEdit ce = new CMCompoundEdit();
	        	//set the description of the new effect
	        	ce.addEdit(new CMComponentAwareEdit());
	        	ce.addEdit(editController.applyChanges());
	        	 ce.addEdit(CMViewEditFactory.INSTANCE.createSelectEffectGridViewEdit(effect));
	        	 CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().selectCMCauseEffectView(effect);
	        	 CMUndoMediator.getInstance().doEdit(ce);
				getWarningMessages().addAll(editController.getWarningMessages());
	        }
	       CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().requestFocus();


	}
	public boolean calculateEnabled() {

		return StructureManager.getSelectedStructure()!=null && StructureManager.getSelectedStructure().getEffects().size()>0;
	}


}
