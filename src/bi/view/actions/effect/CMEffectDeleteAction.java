/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.Effect;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.controller.EffectManager;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMEffectDeleteAction extends AbstractAction implements CMEnabledAction {
	public CMEffectDeleteAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_DELETE_CAUSE_EFFECT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_CAUSE_EFFECT"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EFFECT_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		Effect effect = EffectManager.getSelectedEffect();
		Structure struct = StructureManager.getSelectedStructure();
		if (effect == null) return;
		CMCompoundEdit ce  = new CMCompoundEdit();
		if (JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_DELETE_CAUSE_EFFECT"),
                CMMessages.getString("LABEL_DELETE_CAUSE_EFFECT"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
			ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteEffectModelEdit(struct, effect));
			struct.removeEffect(effect);
		}
		CMUndoMediator.getInstance().doEdit(ce);
		CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().requestFocus();
	}

	public boolean calculateEnabled() {
		return EffectManager.getSelectedEffect()!=null;
	}

}
