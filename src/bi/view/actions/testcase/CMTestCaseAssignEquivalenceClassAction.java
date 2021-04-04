/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testcase;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import model.EquivalenceClass;
import model.Structure;
import model.TestCase;
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
public class CMTestCaseAssignEquivalenceClassAction extends AbstractAction
		implements CMEnabledAction {
	public CMTestCaseAssignEquivalenceClassAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_ASSIGN_EQUIVALENCE_CLASS_TO_TEST_CASE"));
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_ASSIGN_EQUIVALENCE_CLASS_TO_TEST_CASE"));
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_ASSIGN_EQUIVALENCECLASS.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_ASSIGN_EQUIVALENCE_CLASS_MNEMONIC").charAt(0));
	 	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {

		CMApplication.frame.setCursor(new Cursor(Cursor.WAIT_CURSOR));
        EquivalenceClass selectedEquivalenceClass = TestCaseManager.getSelectedEquivalenceClass();
        TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
        Structure selectedStructure = StructureManager.getSelectedStructure();
        if (selectedEquivalenceClass != null && selectedTestCase != null) {
            if (!selectedTestCase.getEquivalenceClasses().contains(selectedEquivalenceClass)) {
                if (!CombinationManager.INSTANCE.isThereACombinationInTestCaseWithTheEquivalenceClass(selectedEquivalenceClass,
                    selectedTestCase)) {
                        if (!TestCaseManager.INSTANCE.isThereAnEquivalenceClassInTheTestCaseThatBelongsToTheSameElement(selectedTestCase,
                            selectedEquivalenceClass)) {
                                if (!TestCaseManager.INSTANCE.isThereACombinationInTheTestCaseThatBelongsToAnEquivalenceClassOfTheSameElement(selectedTestCase,
                                    selectedEquivalenceClass)) {
                                		List<EquivalenceClass> equivList = selectedTestCase.getEquivalenceClasses();
                                		if (!equivList.contains(selectedEquivalenceClass))
                                			equivList.add(selectedEquivalenceClass);
                                	    if( selectedStructure.getTestCases(equivList).size()==0){
                                	    		CMUndoMediator.getInstance().doEdit(TestCaseManager.INSTANCE.addEquivalenceClassToTestCase(selectedEquivalenceClass, selectedTestCase));
                                	    }
                                	    else {
                                            JOptionPane.showMessageDialog(CMApplication.frame,
                                            CMMessages.getString("INFO_EQUIVALENCE_CLASS_CAN_NOT_BE_ASSIGNED_TO_TEST_CASE_3")); //$NON-NLS-1$

                                	    }
                                 }
                                else {
                                    JOptionPane.showMessageDialog(CMApplication.frame,
                                        CMMessages.getString("INFO_EQUIVALENCE_CLASS_CAN_NOT_BE_ASSIGNED_TO_TEST_CASE_1")); //$NON-NLS-1$
                                }
                        }
                        else {
                            JOptionPane.showMessageDialog(CMApplication.frame,
                                CMMessages.getString("INFO_EQUIVALENCE_CLASS_CAN_NOT_BE_ASSIGNED_TO_TEST_CASE_2")); //$NON-NLS-1$
                        }
                }
            }
        }
        CMApplication.frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        CMApplication.frame.getCMTestCaseViews().requestFocus();
	}
	public boolean calculateEnabled() {
		 EquivalenceClass selectedEquivalenceClass = TestCaseManager.getSelectedEquivalenceClass();
	      TestCase selectedTestCase = TestCaseManager.getSelectedTestCase();
		return (selectedEquivalenceClass != null && selectedTestCase != null) && (!selectedTestCase.getEquivalenceClasses().contains(selectedEquivalenceClass));            
	}


}
