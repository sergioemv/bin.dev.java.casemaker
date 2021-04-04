/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.stdcombination;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;

import model.EquivalenceClass;
import model.StdCombination;
import model.edit.CMModelEditFactory;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMStdCombinationCancelAssignEquivalenceClassAction extends
		CMStdCombinationAssignEquivalenceClassAction implements CMEnabledAction {
	public CMStdCombinationCancelAssignEquivalenceClassAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CANCEL_EQUIVALENCE_CLASS_ASSIGNMENT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CANCEL_EQUIVALENCE_CLASS_ASSIGNMENT_TO_STD_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.STDCOMBINATION_CANCEL_ASSIGN_EQUIVALENCECLASS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CANCEL_EQUIVALENCE_CLASS_ASSIGNMENT_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		// TODO Auto-generated method stub
		 StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
	     EquivalenceClass selectedEquivalenceClass = CMApplication.frame.getCMStdCombinationViews().getSelectedEquivalenceClass();
	     if (!calculateEnabled()) return;  
	    
	     selectedStdCombination.removeEquivalenceClass(selectedEquivalenceClass);
         CMUndoMediator.getInstance().doEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(selectedStdCombination, selectedEquivalenceClass));
	            	            
	      	 
	        CMApplication.frame.getCMStdCombinationViews().requestFocus();

	}
	public boolean calculateEnabled() {
		StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
	     EquivalenceClass selectedEquivalenceClass = CMApplication.frame.getCMStdCombinationViews().getSelectedEquivalenceClass();
	     if (selectedEquivalenceClass == null) return false;
	     if (selectedStdCombination == null) return false;
		return selectedStdCombination.getEquivalenceClasses().contains(selectedEquivalenceClass);
	}

}
