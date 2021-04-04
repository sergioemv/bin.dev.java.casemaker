/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.Combination;
import model.Dependency;
import model.PositiveTestCaseGroup;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.edit.CMModelEditFactory;
import model.util.CMStateBean;
import bi.controller.CombinationManager;
import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author smoreno
 * Generates Positive test cases from a structure
 */
public class CMPositiveTestCasesGenerator extends CMAbstractTestCaseGenerator {


	/**
	 * @param p_structure
	 * @param p_Dependencies
	 */
	public CMPositiveTestCasesGenerator(Structure p_structure, List<Dependency> p_Dependencies) {
		super(p_structure, p_Dependencies);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 */
	public CMPositiveTestCasesGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bi.controller.testcase.generator.CMTestCaseGenerator#generate()
	 */
	public UndoableEdit generate() throws CMTestCaseGenerationException {
		CMCompoundEdit ce = new CMCompoundEdit();
		// get the existing positive test case group
	    TestCaseGroup testCaseGroup = (PositiveTestCaseGroup) structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES);
	    // if there are a common dependency in the current test cases
	    if( hasCommonDependency(structure,testCaseGroup, dependencies)) {
	      // delete the test cases that have a common dependency with the dependencies in the list
	      ce.addEdit(deleteTestCases(structure, testCaseGroup, dependencies));
	    }
	    //generate the new positive test cases
	    List<TestCase> positiveTestCases = generatePositiveTestCases(structure, dependencies,ce);
	    //get the number of current positive test cases
	    int numOfTestCases = TestCaseManager.INSTANCE.countTestCases(structure, CMStateBean.STATE_POSITIVE);
	    //get te maximun size of the positive test cases plus the new ones
	    int overallSizeOfTheSameTypeOfExistingTestCases = numOfTestCases + positiveTestCases.size();
	    //if it not pass the maximun size allowed insert the test cases in the correct position
	    if( overallSizeOfTheSameTypeOfExistingTestCases <= CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting().getM_MaxNumOfPositiveTestCases()) {
	    	//insert the testcases in the model and store the undo/redo history
	      ce.addEdit(insertTestCasesAt(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES,testCaseGroup, dependencies, positiveTestCases, structure));
	    }
	    else
	      	throw new CMTestCaseGenerationException(CMMessages.getString("INFO_MAX_NUM_OF_POSITIVE_TEST_CASES_EXCEEDED")); //$NON-NLS-1$
		return ce;
	}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return CMMessages.getString("LABEL_POSITIVE_TEST_CASES");
}
public List<TestCase> generatePositiveTestCases(Structure p_Structure, List<Dependency> p_Dependencies, CMCompoundEdit p_ce) {
    TestCase testCase = null;

    Combination combination = null;
    TestCase testCaseOfRejectedCombinations;
    List<Combination> rejectedCombinations = new ArrayList<Combination>(0);
    List<TestCase> generatedTestCases = new ArrayList<TestCase>();
    TestCaseGroup p_Group;
    int maxNumOfPositiveCombinations = 0;

    //check wich dependency has the maximal number of positive combniations
    for (Dependency dependency : p_Dependencies)
    {
        int numOfPositiveCombinations =  dependency.getCombinationsByState(CMStateBean.STATE_POSITIVE).size();
        	//CombinationManager.INSTANCE.getNumberOfPositiveCombinations(dependency.getLnkCombinations());
        if (numOfPositiveCombinations > maxNumOfPositiveCombinations) {
            maxNumOfPositiveCombinations = numOfPositiveCombinations;
        }
    }

    //create a test case for each of this maximal number
    for (int i = 0; i < maxNumOfPositiveCombinations; i++) {
    	//create the test case with his attributes
        testCase = TestCaseManager.INSTANCE.createTestCaseWithoutId(CMStateBean.STATE_POSITIVE);
        p_Group = (PositiveTestCaseGroup)p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES);
        Vector deps = new Vector();
        deps.addAll(p_Dependencies);
        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseDependenciesModelEdit(testCase,deps));
        testCase.setDependencies(deps);
        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseGroupModelEdit(testCase,p_Group));
        testCase.setTestCaseGroup(p_Group);
        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(testCase,TestCase.Origin.AUTOMATIC));
        testCase.setOriginType(TestCase.Origin.AUTOMATIC);
        //this method is from TC manager
        //if( !existsTestCaseWithTheSameValues(testCase, generatedTestCases, p_Structure.getLnkElements())) {
          generatedTestCases.add(testCase);
        //}
          //for each dependency
          for (Dependency dependency : p_Dependencies){
            combination = CombinationManager.INSTANCE.getPositiveCombinationAtTheOrderPosition(i, dependency);
            if (combination != null) {
                if (TestCaseManager.INSTANCE.canCombinationBeAssignedToTestCase(p_Structure, testCase, combination)) {
                    p_ce.addEdit(TestCaseManager.INSTANCE.addCombinationToTestCase(combination, testCase));
                //    p_ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCase, p_Structure));

                }
                else {
                    rejectedCombinations.add(combination);
                }
            }
        }
    }
    int numOfRejectedCombinations = rejectedCombinations.size();
    Vector usedRejectedCombinations = new Vector(0);
    boolean combinationIsAssignedToATestCase = false;
    for (int i = 0; i < numOfRejectedCombinations; i++) {
        combination = (Combination)rejectedCombinations.get(i);
        combinationIsAssignedToATestCase = false;
        int numOfGeneratedTestCases = generatedTestCases.size();
        for (int j = 0; j < numOfGeneratedTestCases; j++) {
            if (!usedRejectedCombinations.contains(combination)) { // NEW 19.08.2003
                testCase = (TestCase)generatedTestCases.get(j);
                if (TestCaseManager.INSTANCE.canCombinationBeAssignedToTestCase(p_Structure, testCase, combination)) {
                    p_ce.addEdit(TestCaseManager.INSTANCE.addCombinationToTestCase(combination, testCase));
                   // p_ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCase, p_Structure));
                    combinationIsAssignedToATestCase = true;
                    usedRejectedCombinations.addElement(combination);
                }
            }
        }
        if (!combinationIsAssignedToATestCase) {
            if (!usedRejectedCombinations.contains(combination)) { // NEW 19.08.2003
                testCaseOfRejectedCombinations = TestCaseManager.INSTANCE.createTestCaseWithoutId(CMStateBean.STATE_POSITIVE);
                p_Group = (PositiveTestCaseGroup)p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES);
                Vector deps = new Vector();
                deps.addAll(p_Dependencies);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseDependenciesModelEdit(testCaseOfRejectedCombinations,deps));
                testCaseOfRejectedCombinations.setDependencies(deps);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseGroupModelEdit(testCaseOfRejectedCombinations,p_Group));
                testCaseOfRejectedCombinations.setTestCaseGroup(p_Group);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(testCaseOfRejectedCombinations,TestCase.Origin.AUTOMATIC));
                testCaseOfRejectedCombinations.setOriginType(TestCase.Origin.AUTOMATIC);
                //if( !existsTestCaseWithTheSameValues(testCaseOfRejectedCombinations, generatedTestCases, p_Structure.getLnkElements())) {
                  generatedTestCases.add(testCaseOfRejectedCombinations);
                //}
                  p_ce.addEdit(TestCaseManager.INSTANCE.addCombinationToTestCase(combination, testCaseOfRejectedCombinations));
                  //p_ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCaseOfRejectedCombinations, p_Structure));

                usedRejectedCombinations.addElement(combination);
            }
        }
    }
    return generatedTestCases;
}

}
