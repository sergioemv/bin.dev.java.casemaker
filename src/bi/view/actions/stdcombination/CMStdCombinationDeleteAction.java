/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.stdcombination;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;

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
public class CMStdCombinationDeleteAction extends AbstractAction implements
		CMEnabledAction {
	public CMStdCombinationDeleteAction() {
		   super(CMMessages.getString("POPUPMENU_ITEM_DELETE_STANDARD_COMBINATION"));    
		    // Set tool tip text
		    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_DELETE_STANDARD_COMBINATION"));
		    putValue(Action.SMALL_ICON,  CMAction.STDCOMBINATION_DELETE.getIcon());
		    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DELETE_1_MNEMONIC").charAt(0));
		    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,0));
		    
		}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
			 StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
			 Structure structure = StructureManager.getSelectedStructure();
	        if (selectedStdCombination == null) return;
	        
	        if (JOptionPane.showConfirmDialog(CMApplication.frame, CMMessages.getString("QUESTION_DELETE_STD_COMBINATION"),
		                CMMessages.getString("LABEL_DELETE_STD_COMBINATION"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
	        {
	        	CMCompoundEdit ce = new CMCompoundEdit();
	        	ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(structure, selectedStdCombination));
	        	structure.removeCombination(selectedStdCombination);
	        	for (TestCase testCase : structure.getTestCases())
	        		if (testCase.getCombinations().contains(selectedStdCombination))
	        		{
	        			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(testCase, selectedStdCombination));
	        			testCase.removeCombination(selectedStdCombination);
	        		}
	        	CMUndoMediator.getInstance().doEdit(ce);
	        }
	        
	        CMApplication.frame.getCMStdCombinationViews().requestFocus();
	        

	}
	public boolean calculateEnabled() {
		return CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination()!=null;
	}

}
