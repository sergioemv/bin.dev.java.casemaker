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
import model.edit.effect.CMAddEffectNotifiedModelEdit;
import bi.controller.EffectManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMEffectEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
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
public class CMEffectCreateAction extends CMAbstractAction {

	public CMEffectCreateAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_CAUSE_EFFECT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CREATE_CAUSE_EFFECT"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EFFECT_CREATE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MNEMONIC_CREATE_EFFECT").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {

		Structure structure = StructureManager.getSelectedStructure();
		 CMEffectDialog dlg = new CMEffectDialog();
		 Effect effect = EffectManager.INSTANCE.createEffect(structure);
		 CMEffectEditController editController = new CMEffectEditController(effect);
		 dlg.setTitle(CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject().getName()+" - "+this.getValue(Action.NAME));
		 //set the available requirements
		 dlg.setEffectDialogPanel(editController.getPanel());
	        dlg.show();
	        if (dlg.getModalResult() == CMModalResult.OK) {
	        	CMCompoundEdit ce = new CMCompoundEdit();
	        	//add the effect to the structure
	        	 ce.addEdit(new CMComponentAwareEdit());
	        	 ce.addEdit(new CMAddEffectNotifiedModelEdit(structure,effect));
	        	 structure.addEffect(effect);
	        	 ce.addEdit(editController.applyChanges());
	             ce.addEdit(CMViewEditFactory.INSTANCE.createSelectEffectGridViewEdit(effect));
	        	 CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().selectCMCauseEffectView(effect);
	        	 CMUndoMediator.getInstance().doEdit(ce);

	        }
	       CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().requestFocus();
	}

}
