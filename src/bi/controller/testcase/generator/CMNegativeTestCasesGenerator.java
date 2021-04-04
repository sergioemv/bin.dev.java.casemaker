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
import model.Element;
import model.EquivalenceClass;
import model.NegativeTestCaseGroup;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.edit.CMModelEditFactory;
import model.util.CMStateBean;
import bi.controller.TestCaseManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author smoreno
 * Generates Negative test cases from a structure
 */
public class CMNegativeTestCasesGenerator extends CMAbstractTestCaseGenerator {


	/**
	 * @param p_structure
	 * @param p_Dependencies
	 */
	public CMNegativeTestCasesGenerator(Structure p_structure, List<Dependency> p_Dependencies) {
		super(p_structure, p_Dependencies);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 */
	public CMNegativeTestCasesGenerator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see bi.controller.testcase.generator.CMTestCaseGenerator#generate()
	 */
	public UndoableEdit generate() throws CMTestCaseGenerationException {

		CMCompoundEdit ce = new CMCompoundEdit();

		if (dependencies == null || structure == null)
			return null;
	    TestCaseGroup testCaseGroup = (NegativeTestCaseGroup) structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES);
	    //if the passed dependencies are into the existing ones delete all
	    if( hasCommonDependency(structure,testCaseGroup, dependencies)) {
	      ce.addEdit(deleteTestCases(structure, testCaseGroup, dependencies));
	      //testCasesGrid.deleteTestCaseViews(testCaseGroup, p_Dependencies);
	    }
	    //generate the negative test cases for the dependencies
	    List<TestCase> generatedTestCases = generateNegativeTestCases(structure, dependencies,ce);
	    //gary_14072004_begin
	    int numOfTestCases = TestCaseManager.INSTANCE.countTestCases(structure, CMStateBean.STATE_NEGATIVE);
	    int overallSizeOfTheSameTypeOfExistingTestCases = numOfTestCases + generatedTestCases.size();

	    if( overallSizeOfTheSameTypeOfExistingTestCases <= CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting().getM_MaxNumOfNegativeTestCases()) {
			List<TestCase> newTestCases =  getOnlyTheNewNegativeTestCases(generatedTestCases, structure);
			ce.addEdit(insertTestCasesAt(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES,testCaseGroup, dependencies, newTestCases, structure));
	    }
	    else
	      	throw new CMTestCaseGenerationException(CMMessages.getString("INFO_MAX_NUM_OF_NEGATIVE_TEST_CASES_EXCEEDED")); //$NON-NLS-1$


	    //end the Compound

	    return ce;



	}
/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Override
public String toString() {
	return CMMessages.getString("LABEL_NEGATIVE_TEST_CASES"); //$NON-NLS-1$
}
public List<TestCase> generateNegativeTestCases(Structure p_Structure, List<Dependency> p_Dependencies, CMCompoundEdit p_ce) {
    List<TestCase> generatedTestCasesWithoutId_1 = generateNegativeTestCasesFromEquivalenceClasses(p_Structure,p_ce);
    List<TestCase> generatedTestCasesWithoutId_2 = generateNegativeTestCasesFromCombinations(p_Structure, p_Dependencies, p_ce);
    TestCase testCase;
    int numOfTestCases = generatedTestCasesWithoutId_2.size();
    for (int i = 0; i < numOfTestCases; i++) {
        testCase = (TestCase)generatedTestCasesWithoutId_2.get(i);
        generatedTestCasesWithoutId_1.add(testCase);
    }
    return generatedTestCasesWithoutId_1;
}
public List<TestCase> generateNegativeTestCasesFromEquivalenceClasses(Structure p_Structure, CMCompoundEdit p_ce) {
    ArrayList<TestCase> generatedTestCases = new ArrayList<TestCase>(0);
    int numOfElements = p_Structure.getLnkElements().size();
    int numOfEquivalenceClasses = 0;
    Element element = null;
    EquivalenceClass equivalenceClass = null;
    TestCase testCase = null;
    for (int i = 0; i < numOfElements; i++) {
        element = (Element)p_Structure.getLnkElements().elementAt(i);
        numOfEquivalenceClasses = element.getEquivalenceClasses().size();
        for (int j = 0; j < numOfEquivalenceClasses; j++) {
            equivalenceClass = (EquivalenceClass)element.getEquivalenceClasses().get(j);
            if (equivalenceClass.getState() == CMStateBean.STATE_NEGATIVE) {
                testCase = TestCaseManager.INSTANCE.createTestCaseWithoutId(CMStateBean.STATE_NEGATIVE);
                TestCaseGroup testCaseGroup = (NegativeTestCaseGroup)
                    p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseGroupModelEdit(testCase,testCaseGroup));
                testCase.setTestCaseGroup(testCaseGroup);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseDependenciesModelEdit(testCase,null));
                testCase.setDependencies(null);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(testCase,TestCase.Origin.AUTOMATIC));
                testCase.setOriginType(TestCase.Origin.AUTOMATIC);

                p_ce.addEdit(TestCaseManager.INSTANCE.addEquivalenceClassToTestCase(equivalenceClass, testCase));
              //  p_ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCase, p_Structure));

                generatedTestCases.add(testCase);
            }
        }
    }
    return generatedTestCases;
}
public List<TestCase> generateNegativeTestCasesFromCombinations(Structure p_Structure, List<Dependency> p_Dependencies, CMCompoundEdit p_ce) {
    ArrayList<TestCase> generatedTestCases = new ArrayList<TestCase>();
    int numOfDependencies = p_Dependencies.size();
    int numOfCombinations = 0;
    Dependency dependency = null;
    Combination combination = null;
    TestCase testCase = null;
    for (int i = 0; i < numOfDependencies; i++) {
        dependency = (Dependency)p_Dependencies.get(i);
        numOfCombinations = dependency.getLnkCombinations().size();
        for (int j = 0; j < numOfCombinations; j++) {
            combination = (Combination)dependency.getLnkCombinations().elementAt(j);
            if (combination.getState() == CMStateBean.STATE_NEGATIVE) {
                testCase = TestCaseManager.INSTANCE.createTestCaseWithoutId(CMStateBean.STATE_NEGATIVE);
                TestCaseGroup testCaseGroup = (NegativeTestCaseGroup)p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES);
                Vector<Dependency> deps = new Vector<Dependency>();
                deps.addAll(p_Dependencies);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseDependenciesModelEdit(testCase,null));
                testCase.setDependencies(deps);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseGroupModelEdit(testCase,testCaseGroup));
                testCase.setTestCaseGroup(testCaseGroup);
                p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(testCase,TestCase.Origin.AUTOMATIC));
                testCase.setOriginType(TestCase.Origin.AUTOMATIC);
                p_ce.addEdit(TestCaseManager.INSTANCE.addCombinationToTestCase(combination, testCase));
              //  p_ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCase, p_Structure));

                generatedTestCases.add(testCase);
            }
        }
    }
    return generatedTestCases;
}
public List<TestCase> getOnlyTheNewNegativeTestCases(List<TestCase> p_GeneratedTestCases, Structure p_Structure){
    int numOfTestCases = p_GeneratedTestCases.size();
	TestCase testCase = null;
	ArrayList<TestCase> newTestCases = new ArrayList<TestCase>(0);

    for( int i = 0; i < numOfTestCases; i++) {
      testCase = (TestCase) p_GeneratedTestCases.get(i);
      if( !TestCaseManager.INSTANCE.testCaseHasTheSameNegativeEquivalenceClasses(testCase, p_Structure)) {
        newTestCases.add(testCase);
      }
    }
    return newTestCases;
  }
}
