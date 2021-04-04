/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testcase;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.Combination;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;
import bi.controller.StructureManager;
import bi.controller.TestCaseManager;
import bi.view.actions.CMAction;
import bi.view.actions.CMEnabledAction;
import bi.view.cells.renderers.CMCellRendererFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author smoreno
 *
 */
public class CMTestCaseCancelAssignEquivalenceClassAction extends
		AbstractAction implements CMEnabledAction {
	public CMTestCaseCancelAssignEquivalenceClassAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_CANCEL_EQUIVALENCE_CLASS_ASSIGNMENT"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_CANCEL_EQUIVALENCE_CLASS_ASSIGNMENT_TO_TEST_CASE"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_CANCEL_ASSIGN_EQUIVALENCECLASS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_CANCEL_EQUIVALENCE_CLASS_ASSIGNMENT_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
	        EquivalenceClass selectedEquivalenceClass = TestCaseManager.getSelectedEquivalenceClass();
	        TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
	        Structure selectedStructure = StructureManager.getSelectedStructure();
	        if (selectedEquivalenceClass != null && selectedTestCase != null) {
	            if (selectedTestCase.getEquivalenceClasses().contains(selectedEquivalenceClass)) {
	        		List<EquivalenceClass> equivList = selectedTestCase.getEquivalenceClasses();
            		if (equivList.contains(selectedEquivalenceClass))
            			equivList.remove(selectedEquivalenceClass);
	               //if( selectedStructure.getTestCases(equivList).size()==0){
            		if(canRemove(selectedTestCase, selectedEquivalenceClass)){
	            	   CMUndoMediator.getInstance().doEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(selectedTestCase, selectedEquivalenceClass));
		                selectedTestCase.removeEquivalenceClass(selectedEquivalenceClass);
		        	}
	            	else {
	                     JOptionPane.showMessageDialog(CMApplication.frame,
	                            CMMessages.getString("INFO_EQUIVALENCE_CLASS_CAN_NOT_BE_ASSIGNED_TO_TEST_CASE_3")); //$NON-NLS-1$
	              	}
	            }
	        }
	    }
	public boolean canRemove(TestCase p_testCase, EquivalenceClass p_EQ){
		
		Element parentElement = p_EQ.getLnkElement();
		for(EquivalenceClass eq1: p_testCase.getEquivalenceClasses()){
			if(parentElement.equals(eq1.getLnkElement()) && eq1 != p_EQ)
				return true;
		}
		for(Combination comb: p_testCase.getCombinations()){
			for(EquivalenceClass eq: comb.getEquivalenceClasses()){
				if(parentElement.equals(eq.getLnkElement()) && eq != p_EQ)
					return true;
			}
		}
		return false;
	}
	public boolean calculateEnabled() {
		 EquivalenceClass selectedEquivalenceClass = TestCaseManager.getSelectedEquivalenceClass();
	      TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
		return (selectedEquivalenceClass != null && selectedTestCase != null) && (selectedTestCase.getEquivalenceClasses().contains(selectedEquivalenceClass));            

	}


	}


