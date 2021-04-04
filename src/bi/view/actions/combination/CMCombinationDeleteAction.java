/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.Combination;
import model.Dependency;
import model.edit.CMModelEditFactory;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMCombinationDeleteAction extends AbstractAction implements Action {
	public CMCombinationDeleteAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_DELETE_COMBINATION"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_DELETE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0 ));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		  int selectedColumn =CMApplication.frame.getCMGridCombinationViews().getSelectionModel().getLeadColumn();
	    Combination selectedCombination = CombinationManager.getSelectedCombination();
	    Dependency selectedDependency = DependencyManager.getSelectedDependency();
	    if( selectedCombination != null) {
	      if( selectedCombination.getOriginType() != null) {
	        if( selectedCombination.getOriginType().equals(Combination.Origin.MANUAL)) {
	        	CMCompoundEdit ce = new CMCompoundEdit();
	        	ce.addEdit(new CMComponentAwareEdit());
	        	ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(selectedDependency, selectedCombination));
	        	selectedDependency.removeCombination(selectedCombination);
	        	CMUndoMediator.getInstance().doEdit(ce);
	        }
	      }
	    }
	    CMApplication.frame.getCMGridCombinationViews().requestFocus();
	}

}
