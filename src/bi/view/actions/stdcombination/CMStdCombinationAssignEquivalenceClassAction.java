/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.stdcombination;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.EquivalenceClass;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;

import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMStdCombinationAssignEquivalenceClassAction extends
		AbstractAction implements CMEnabledAction {
	public CMStdCombinationAssignEquivalenceClassAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_ASSIGN_EQUIVALENCE_CLASS"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_EQUIVALENCE_CLASS_TO_STD_COMBINATION"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.STDCOMBINATION_ASSIGN_EQUIVALENCECLASS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_EQUIVALENCE_CLASS_MNEMONIC").charAt(0));
	 	}
	/**
	 * @param p_string
	 */
	public CMStdCombinationAssignEquivalenceClassAction(String p_string) {
		super(p_string);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		 StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
	     EquivalenceClass selectedEquivalenceClass = CMApplication.frame.getCMStdCombinationViews().getSelectedEquivalenceClass();

	        if (selectedEquivalenceClass != null && selectedStdCombination != null) {
	            if (!selectedStdCombination.getEquivalenceClasses().contains(selectedEquivalenceClass)) {
	                if (!CombinationManager.INSTANCE.isThereAnEquivalenceClassInTheCombinationThatBelongsToTheSameElement(selectedStdCombination,
	                    selectedEquivalenceClass)) {
	                	if(!existTestCaseWithTheSameValueInStandartCombination(selectedStdCombination,selectedEquivalenceClass, false)){
	                		selectedStdCombination.addEquivalenceClass(selectedEquivalenceClass);
	                		CMUndoMediator.getInstance().doEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(selectedStdCombination, selectedEquivalenceClass));
	                	}
	                	else{
	                		 JOptionPane.showMessageDialog(CMApplication.frame,
	                		            CMMessages.getString("INFO_EQUIVALENCE_CLASS_CAN_NOT_BE_ASSIGNED_TO_TEST_CASE_3")); //$NON-NLS-1$

	                	}
	                }
	                else {
	                    JOptionPane.showMessageDialog(CMApplication.frame,
	                        CMMessages.getString("INFO_EQUIVALENCE_CLASS_CAN_NOT_BE_ASSIGNED_TO_STD_COMBINATION")); //$NON-NLS-1$
	                }
	            }
	        }

	        CMApplication.frame.getCMStdCombinationViews().requestFocus();

	}
	protected boolean existTestCaseWithTheSameValueInStandartCombination(StdCombination p_StdCombination, EquivalenceClass selectedEquivalenceClass, boolean remove){
		   TestCase testCaseClone= null;
		   TestCase selectedTestCase=null;
		   Structure m_Structure = StructureManager.getSelectedStructure();
		for (Iterator iter = m_Structure .getLnkTestCases().iterator(); iter.hasNext();) {
			TestCase testCase = (TestCase) iter.next();
			if(testCase.getCombinations().contains(p_StdCombination)){
				selectedTestCase=testCase;
				testCaseClone= testCase.makeClone();
				break;
			}
		}
	    	Vector testCaseClones = (Vector) m_Structure.getLnkTestCases().clone();
	    	if(selectedTestCase != null){
	    		testCaseClones.remove(selectedTestCase);
	    		if(remove){
	    				p_StdCombination.getEquivalenceClasses().remove(selectedEquivalenceClass);
	    			testCaseClone.removeEquivalenceClass(selectedEquivalenceClass);
	    		}
	    		else
	    			testCaseClone.addEquivalenceClass(selectedEquivalenceClass);
	    	}
	    	if(testCaseClone!= null && !TestCaseManager.INSTANCE.existsTestCaseWithTheSameValues(testCaseClone, testCaseClones, m_Structure.getLnkElements())){
	    		if(remove)
	    			p_StdCombination.getEquivalenceClasses().add(selectedEquivalenceClass);
	    		return false;
	    	}
	    	else {
	    		if(remove)
	    			p_StdCombination.getEquivalenceClasses().add(selectedEquivalenceClass);

	    		if(testCaseClone == null)
	    			return false;
	    		else
	    			return true;
	   	    }
	    }
	public boolean calculateEnabled() {
		StdCombination selectedStdCombination = CMApplication.frame.getCMStdCombinationViews().getSelectedStdCombination();
	     EquivalenceClass selectedEquivalenceClass = CMApplication.frame.getCMStdCombinationViews().getSelectedEquivalenceClass();
	     if (selectedEquivalenceClass == null) return false;
	     if (selectedStdCombination == null) return false;
		return !selectedStdCombination.getEquivalenceClasses().contains(selectedEquivalenceClass);

	}
}
