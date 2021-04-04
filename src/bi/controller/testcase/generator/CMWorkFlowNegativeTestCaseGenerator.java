/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.Dependency;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.util.CMStateBean;
import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author smoreno
 *
 */
public class CMWorkFlowNegativeTestCaseGenerator extends CMAbstractTestCaseGenerator
		implements CMTestCaseGenerator {

	/**
	 */
	public CMWorkFlowNegativeTestCaseGenerator() {
		super();
	}

	/**
	 * @param p_structure
	 * @param p_dependencies
	 */
	public CMWorkFlowNegativeTestCaseGenerator(Structure p_structure, List<Dependency> p_dependencies) {
		super(p_structure,p_dependencies);
	}

	/* (non-Javadoc)
	 * @see bi.controller.testcase.generator.CMAbstractTestCaseGenerator#generate()
	 */
	@Override
	public UndoableEdit generate() throws CMTestCaseGenerationException {
		if (getDependencies()==null || getStructure() == null)
			throw new CMTestCaseGenerationException("Generator not Initialized!"); //$NON-NLS-1$
		CMCompoundEdit ce = new CMCompoundEdit();
		// get the existing positive test case group
	    TestCaseGroup testCaseGroup = (TestCaseGroup) structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES);
	    // if there are a common dependency in the current test cases
	    if( hasCommonDependency(structure,testCaseGroup, dependencies)) {
	      // delete the test cases that have a common dependency with the dependencies in the list
	      ce.addEdit(deleteTestCases(structure, testCaseGroup, dependencies));
	    }
	    //generate the new positive workflow test cases
	    List<TestCase> testCases = generateWorkFlowTestCases(ce, CMStateBean.STATE_NEGATIVE, testCaseGroup);
	    //get the number of current positive test cases
	    int numOfTestCases = TestCaseManager.INSTANCE.countTestCases(structure, CMStateBean.STATE_NEGATIVE);
	    //get te maximun size of the positive test cases plus the new ones
	    int overallSizeOfTheSameTypeOfExistingTestCases = numOfTestCases + testCases.size();
	    //if it not pass the maximun size allowed insert the test cases in the correct position
	    if( overallSizeOfTheSameTypeOfExistingTestCases <= CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting().getM_MaxNumOfPositiveTestCases()) {
	    	//insert the testcases in the model and store the undo/redo history
	      ce.addEdit(insertTestCasesAt(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES,testCaseGroup, dependencies, testCases, structure));
	    }
	    else
	      	throw new CMTestCaseGenerationException(CMMessages.getString("INFO_MAX_NUM_OF_NEGATIVE_TEST_CASES_EXCEEDED")); //$NON-NLS-1$
		return ce;

	}



/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	// TODO Auto-generated method stub
	return CMMessages.getString("LABEL_NEGATIVE_WORKFLOW_TESTCASES"); //$NON-NLS-1$
}
}
