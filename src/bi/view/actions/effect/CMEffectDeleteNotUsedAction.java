/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.effect;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.Structure;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
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
public class CMEffectDeleteNotUsedAction extends AbstractAction implements
		CMEnabledAction {
	public CMEffectDeleteNotUsedAction() {
		super(CMMessages.getString("LABEL_DELETE_CAUSEEFFECT_EXCEL_NOT_USED")); //$NON-NLS-1$
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("LABEL_DELETE_CAUSEEFFECT_EXCEL_NOT_USED")); //$NON-NLS-1$
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.EFFECT_DELETE_NOT_USED.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_CAUSEEFFECT_EXCEL_NOT_USED_MNEMONIC").charAt(0)); //$NON-NLS-1$
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		  int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("QUESTION_DELETE_CAUSE_EFFECT_NOT_USED"),CMMessages.getString("LABEL_DELETE_CAUSE_EFFECT"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	      if( confirmation == JOptionPane.YES_OPTION) {
	    	Structure selectedStructure= StructureManager.getSelectedStructure();
	    	CMCompoundEdit ce = new CMCompoundEdit();
	    	for (int i = selectedStructure.getEffects().size()-1;i>=0;i--)
	    	{
	    		if (!selectedStructure.getEffects().get(i).isUsed())
	    		{
	    			ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteEffectModelEdit(selectedStructure,selectedStructure.getEffects().get(i)));
	    			selectedStructure.removeEffect(selectedStructure.getEffects().get(i));
	    		}
	    	}
	    	ce.addDelegateEdit(new CMDelegate(){
				public void execute() {
			    	EffectManager.reloadEffectGrid();
				}});

	    	CMUndoMediator.getInstance().doEdit(ce);
	    	EffectManager.reloadEffectGrid();
	      }
	      CMApplication.frame.getCauseEffectStructureView().getM_CMCauseEffectStructureGridView().requestFocus();

	}
	public boolean calculateEnabled() {

		return StructureManager.getSelectedStructure()!=null && StructureManager.getSelectedStructure().getEffects().size()>0;
	}

}
