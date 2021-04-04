/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.combination;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.List;

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
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMCombinationMergeAction extends AbstractAction implements Action {
	public CMCombinationMergeAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_MERGE_COMBINATION"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_MERGE_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_MERGE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_MERGE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, Event.CTRL_MASK));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		  	CMCompoundEdit ce  = new CMCompoundEdit();
		    Combination selectedCombination = CombinationManager.getSelectedCombination();
		    EquivalenceClass selectedEquivalenceClass = CombinationManager.getSelectedEquivalenceClass();
		    Dependency selectedDependency = DependencyManager.getSelectedDependency();
		    if( selectedDependency != null && selectedCombination != null && selectedEquivalenceClass != null) {
			  if( !(selectedCombination.getOriginType() == Combination.Origin.PERMUTATION)) {
				  return;
			  }
		      if( !selectedCombination.contains(selectedEquivalenceClass)) {
			 
		    	List<Combination> mergingCombinations = CombinationManager.INSTANCE.lookForMergingCombinations( selectedEquivalenceClass, selectedCombination,selectedDependency);
				int confirmation=-1;
				int confirmation1=-1;
				for( Iterator<Combination> i = mergingCombinations.iterator(); i.hasNext();) {
					  Combination childCombination = i.next();
					  if (!selectedCombination.getEffects().containsAll(childCombination.getEffects())){
						  confirmation = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("TRYING_TO_MERGE_WITH_COMBINATION_WITH_DIFFERENT_EFFECTS"),
					                CMMessages.getString("DIFFERENT_EFFECTS"), JOptionPane.YES_NO_OPTION);
						
					  }
					  if (selectedCombination.getState()!=childCombination.getState() && confirmation==-1){				  
						  confirmation1 = JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("TRYING_TO_MERGE_WITH_COMBINATION_WITH_DIFFERENT_STATES"),
					                CMMessages.getString("DIFFERENT_STATES"), JOptionPane.YES_NO_OPTION);
						 
					  }	
					  if (confirmation != -1 || confirmation1 != -1)
						  break;
					  	  
				}
				if (confirmation == JOptionPane.YES_OPTION  || confirmation1 == JOptionPane.YES_OPTION) {
					CMModelEventHandler.setNotifyEnabled(false);
					ce.addEdit(CombinationManager.INSTANCE.mergeCombinationsAt(selectedEquivalenceClass, selectedCombination, selectedDependency, mergingCombinations));
					CMModelEventHandler.setNotifyEnabled(true);
				CMApplication.frame.getCMGridCombinationViews().handleCMModelChange(new CMModelEvent(selectedDependency,CMField.COMBINATIONS));
				}
				else{
					if (confirmation == -1 && confirmation1== -1)
						CMModelEventHandler.setNotifyEnabled(false);
						ce.addEdit(CombinationManager.INSTANCE.mergeCombinationsAt(selectedEquivalenceClass, selectedCombination, selectedDependency, mergingCombinations));
						CMModelEventHandler.setNotifyEnabled(true);
					CMApplication.frame.getCMGridCombinationViews().handleCMModelChange(new CMModelEvent(selectedDependency,CMField.COMBINATIONS));
				}
		              
			  }
		      else {
		          JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("LABEL_NOT_POSIBLE_TO_MERGE")); //$NON-NLS-1$
		      }
		    }
		    
		    CMUndoMediator.getInstance().doMassiveEdit(ce);
		    int row = CMApplication.frame.getCMGridCombinationViews().getSelectedRow();
		    int column = CMApplication.frame.getCMGridCombinationViews().getSelectedColumn();
		    CMApplication.frame.getCMGridCombinationViews().changeSelection(row, column, false, false);
		    CMApplication.frame.getCMGridCombinationViews().requestFocus();
	}

}
