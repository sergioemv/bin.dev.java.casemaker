/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.List;

import javax.swing.undo.UndoableEdit;

import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

import model.BusinessRules;
import model.Dependency;
import model.FaultyTestCaseGroup;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.util.CMStateBean;

/**
 * @author smoreno
 * Generates Positive test cases from a structure
 */
public class CMFaultyEClassTestCasesGenerator extends CMFaultyTestCasesGenerator {


	/**
	 * @param p_structure
	 * @param p_Dependencies
	 */
	public CMFaultyEClassTestCasesGenerator(Structure p_structure, List<Dependency> p_Dependencies) {
		super(p_structure, p_Dependencies);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 */
	public CMFaultyEClassTestCasesGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bi.controller.testcase.generator.CMTestCaseGenerator#generate()
	 */
	public UndoableEdit generate() throws CMTestCaseGenerationException {
		if (dependencies == null || structure == null)
			return null;
		CMCompoundEdit ce = new CMCompoundEdit();
	    TestCaseGroup testCaseGroup = (FaultyTestCaseGroup) structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_FAULTIES);
	    if( hasCommonDependency(structure,testCaseGroup, dependencies)) {
	      ce.addEdit(deleteTestCases(structure, testCaseGroup, dependencies));

	    }
	    List<TestCase> generatedTestCases = generateFaultyTestCasesFromEquivalenceClasses(structure,ce);

	    int numOfTestCases = TestCaseManager.INSTANCE.countTestCases(structure, CMStateBean.STATE_FAULTY);
	    int overallSizeOfTheSameTypeOfExistingTestCases = numOfTestCases + generatedTestCases.size();

	    if( overallSizeOfTheSameTypeOfExistingTestCases <= CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting().getM_MaxNumOfFaultyTestCases()) {
			List<TestCase> newTestCases = getOnlyTheNewFaultyTestCases( generatedTestCases, structure);
			ce.addEdit(insertTestCasesAt(BusinessRules.TEST_CASE_GROUP_POSITION_OF_FAULTIES,testCaseGroup, dependencies, newTestCases, structure));

	    }
	    else {
	    	throw new CMTestCaseGenerationException(CMMessages.getString("INFO_MAX_NUM_OF_FAULTY_TEST_CASES_EXCEEDED")); //$NON-NLS-1$
	    }

	    return ce;

	}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	// TODO Auto-generated method stub
	return CMMessages.getString("LABEL_FAULTY_TEST_CASES_FROM_FAULTY_EQUIVALENCE_CLASSES"); //$NON-NLS-1$
}
}
