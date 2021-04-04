/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.equivalenceclass;

import java.awt.Event;
import java.util.List;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.Effect;
import model.EquivalenceClass;
import model.Structure;
import model.edit.effect.CMAddEffectNotifiedModelEdit;
import bi.controller.EffectManager;
import bi.controller.EquivalenceClassManager;
import bi.controller.StructureManager;
import bi.controller.editcontrol.CMEffectEditController;
import bi.view.actions.CMAbstractAction;
import bi.view.actions.CMAction;
import bi.view.actions.effect.CMEffectDialog;
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
@SuppressWarnings("serial")
public class CMEquivalenceClassCreateAssignEffectAction extends CMAbstractAction{
	private List<String> warningMessages;//ccastedo 06.10.06
	public CMEquivalenceClassCreateAssignEffectAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CREATE_AND_ASSIGN_CAUSE_EFFECT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_CREATE_AND_ASSIGN_CAUSE_EFFECT"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EQUIVALENCECLASS_CREATE_ASSIGN_EFFECT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CREATE_1_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		CMApplication.frame.setWaitCursor(true);
		Structure structure = StructureManager.getSelectedStructure();
		EquivalenceClass equivalenceClass = EquivalenceClassManager.getSelectedEquivalenceClass();
		 CMEffectDialog dlg = new CMEffectDialog();
		 Effect effect = EffectManager.INSTANCE.createEffect(structure);
		 CMEffectEditController editController = new CMEffectEditController(effect);
		 editController.setStructure(structure);
		 dlg.setTitle(equivalenceClass.getName()+" - "+this.getValue(Action.NAME));
		 dlg.setEffectDialogPanel(editController.getPanel());
	     dlg.show();
	     CMApplication.frame.setWaitCursor(false);
	        if (dlg.getModalResult() == CMModalResult.OK) {
	        	//create the effect 
	        	
	        	CMCompoundEdit ce = new CMCompoundEdit();
	           	ce.addEdit(new CMComponentAwareEdit());
	           	//add the effect to the structure
	           	ce.addEdit(new CMAddEffectNotifiedModelEdit(structure,effect));
	        	structure.addEffect(effect);
//	        	add the effect to the equivalence class
	        	ce.addEdit(new CMAddEffectNotifiedModelEdit(equivalenceClass,effect));
	        	equivalenceClass.addEffect(effect);
	        	//set the effects properties
	        	ce.addEdit(editController.applyChanges());
	        	getWarningMessages().addAll(editController.getWarningMessages());
	        	
	        	 //select the specified  effect from the equivalence class
	        	 ce.addEdit(CMViewEditFactory.INSTANCE.createSelecteEquivalenceClassEffectGrid(equivalenceClass));
	        	 CMApplication.frame.getElementsGrid().selectEquivalenceClassEffects(equivalenceClass);
	        	 CMUndoMediator.getInstance().doEdit(ce);

	        }
	        dlg.dispose();
	}
}
