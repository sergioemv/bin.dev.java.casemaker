/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.combination;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.CMField;
import model.Combination;
import model.Dependency;
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
public class CMCombinationDeleteAllAction extends AbstractAction implements
		Action {
	public CMCombinationDeleteAllAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_DELETE_ALL_COMBINATIONS"));
		CMMessages.getString("MENU_ITEM_ASSIGN_CAUSE_EFFECTS_MNEMONIC").charAt(0);
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_CAUSE_EFFECTS_TO_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.COMBINATION_DELETE_ALL.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_ALL_MNEMONIC").charAt(0));
	    //TODO enable this key (check problems with the intercepter)
	//    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, Event.CTRL_MASK|Event.SHIFT_MASK));

	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		Dependency selectedDependency = DependencyManager.getSelectedDependency();
		if( selectedDependency != null) {
		      if( selectedDependency.getLnkCombinations().size() > 0) {
		 	      int confirmation = JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("LABEL_DELETE_ALL_COMBINATIONS"),CMMessages.getString("LABEL_DELETE_COMBINATIONS"),JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
		 		  if( confirmation == JOptionPane.YES_OPTION) {

		 			 CMModelEventHandler.setNotifyEnabled(false);
		 			 CMCompoundEdit  ce  = new CMCompoundEdit();
		 			 for (Combination combination : selectedDependency.getCombinations())
		 				 ce.addEdit(CombinationManager.INSTANCE.deleteCombination(combination,selectedDependency));
		 			 CMUndoMediator.getInstance().doMassiveEdit(ce);
		            CMModelEventHandler.setNotifyEnabled(true);
		            CMApplication.frame.getCMGridCombinationViews().handleCMModelChange(new CMModelEvent(selectedDependency,CMField.COMBINATIONS));
		          }
		      }
		    }

		    CMApplication.frame.getCMGridDependencies().requestFocus();
	}

}
