/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.equivalenceclass;

import java.awt.Event;

import javax.swing.Action;
import javax.swing.KeyStroke;

import model.EquivalenceClass;
import model.Structure;
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
public class CMEquivalenceClassEditAssignedEffectsAction extends CMAbstractAction {
	public CMEquivalenceClassEditAssignedEffectsAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECTS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECTS"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EQUIVALENCECLASS_EDIT_ASSIGNED_EFFECTS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("POPUPMENU_ITEM_EDIT_CAUSE_EFFECT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void execute() {
		//get the selected equivalence Class
		CMApplication.frame.setWaitCursor(true);
		int col = CMApplication.frame.getElementsGrid().getSelectionModel().getLeadColumn();
		int row = CMApplication.frame.getElementsGrid().getSelectionModel().getLeadRow();
		EquivalenceClass equivalenceClass = CMApplication.frame.getElementsGrid().getSelectedEquivalenceClass(row, col);
		//get the current Structure
		Structure structure = StructureManager.getSelectedStructure();
		//create the manager to control the edition of the effects 
		CMEffectsEditController effectsEditController = new CMEffectsEditController(equivalenceClass,structure); 
		//create the dialog to edit the effects
		CMEffectsEditDialog dlg = new CMEffectsEditDialog(effectsEditController.getPanel());
		//dlg.setEditEffectsPanel(effectsEditController.getPanel());
		dlg.setTitle(equivalenceClass.getName()+" - "+this.getValue(Action.NAME));
		dlg.setVisible(true);
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
		 CMApplication.frame.getElementsGrid().requestFocus();

	}

}
