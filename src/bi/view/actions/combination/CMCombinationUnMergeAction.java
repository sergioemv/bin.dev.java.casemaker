/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.combination;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.CMField;
import model.Combination;
import model.Dependency;
import model.EquivalenceClass;
import model.util.CMModelEvent;
import model.util.CMModelEventHandler;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMCombinationUnMergeAction extends AbstractAction implements
		Action {
	public CMCombinationUnMergeAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_SEPARATE_COMBINATION_MERGE"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_MERGE_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_UNMERGE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_SEPARATE_MERGE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, Event.SHIFT_MASK|Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		    Combination selectedCombination = CombinationManager.getSelectedCombination();
		    EquivalenceClass selectedEquivalenceClass = CombinationManager.getSelectedEquivalenceClass();
		    Dependency selectedDependency = DependencyManager.getSelectedDependency();
		 if( selectedDependency != null && selectedCombination != null && selectedEquivalenceClass != null ) {
		 if( !(selectedCombination.getOriginType() == (Combination.Origin.PERMUTATION) )) return;

	     if( selectedCombination.containsInChildrenCombinations(selectedEquivalenceClass)) {//fcastro_13092004
	    	 CMModelEventHandler.setNotifyEnabled(false);
	    	 CMUndoMediator.getInstance().doMassiveEdit(
	    			 CombinationManager.INSTANCE.separateCombinationsAt(selectedEquivalenceClass,selectedCombination, selectedDependency));
	    	 CMModelEventHandler.setNotifyEnabled(true);
	    	 CMApplication.frame.getCMGridCombinationViews().handleCMModelChange(new CMModelEvent(selectedDependency,CMField.COMBINATIONS));
	    	 
	     }
	     else {
	         JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("LABEL_NOT_POSIBLE_TO_UNMERGE_2")); //$NON-NLS-1$
	     }
	   }
		 int row = CMApplication.frame.getCMGridCombinationViews().getSelectedRow();
		 int column = CMApplication.frame.getCMGridCombinationViews().getSelectedColumn();
		 CMApplication.frame.getCMGridCombinationViews().changeSelection(row, column, false, false);
		 CMApplication.frame.getCMGridCombinationViews().requestFocus();

	}

}
