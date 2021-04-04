/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.ArrayList;
import java.util.List;

import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.PositiveTestCaseGroup;
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
 * Generates Positive test cases from a structure
 */
public class CMPositiveEquivalenceClassOneWiseGenerator extends CMAbstractTestCaseGenerator {


	/**
	 * @param p_structure
	 * @param p_Dependencies
	 */
	public CMPositiveEquivalenceClassOneWiseGenerator(Structure p_structure, List<Dependency> p_Dependencies) {
		super(p_structure, p_Dependencies);
		// TODO Auto-generated constructor stub
	}

	/**
	 *
	 */
	public CMPositiveEquivalenceClassOneWiseGenerator() {

	}

	/* (non-Javadoc)
	 * @see bi.controller.testcase.generator.CMTestCaseGenerator#generate()
	 */
	public UndoableEdit generate() throws CMTestCaseGenerationException {
		if (dependencies == null || structure == null ) return null;
		CMCompoundEdit ce = new CMCompoundEdit();
		ArrayList<TestCase> newTestCases = new ArrayList<TestCase>();
		TestCaseGroup    group = (PositiveTestCaseGroup)structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES);
		//for each element in the test object
		for (Element element : structure.getLnkElements())
		{
			//for each positive equivalence class in the element
			for (EquivalenceClass eClass : element.getEquivalenceClassesbyState(CMStateBean.STATE_POSITIVE))
			{
				//check if the equivalence class is used in any of the test cases of the structure (including negative and faulty test cases)
				boolean isUsed = false;
				for (TestCase testCase : structure.getLnkTestCases())
					if (testCase.getRelatedEquivalenceClasses().contains(eClass))
						isUsed = true;
				//if is not used find a positive test case that does not use antother equivalence class of the same element
				if (!isUsed)
				{
					boolean assigned = false;
					for (TestCase testCase : structure.getTestCasesByState(CMStateBean.STATE_POSITIVE))
						//if theres an positive test case asign the equivalence Class to the test case
						if (!testCase.getRelatedElements().contains(eClass.getLnkElement())&&!assigned)
						{
							ce.addEdit(TestCaseManager.INSTANCE.addEquivalenceClassToTestCase(eClass, testCase));
		                    assigned = true;
						}
					//check also in the newly generated test cases
					for (TestCase testCase : newTestCases)
						if (!testCase.getRelatedElements().contains(eClass.getLnkElement())&&!assigned){
							ce.addEdit(TestCaseManager.INSTANCE.addEquivalenceClassToTestCase(eClass, testCase));
				              assigned = true;
						}
					//if theres not any positive test case create a new positive test case  with that assignment of equivalence class
					if (!assigned)
					{
						//create and initialize the new test case
						TestCase testCase = TestCaseManager.INSTANCE.createTestCaseWithoutId(CMStateBean.STATE_POSITIVE);
					    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseDependenciesModelEdit(testCase,null));
				        testCase.setDependencies(null);
				        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseGroupModelEdit(testCase,group));
				        testCase.setTestCaseGroup(group);
				        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(testCase,TestCase.Origin.AUTOMATIC));
				        testCase.setOriginType(TestCase.Origin.AUTOMATIC);
				    	ce.addEdit(TestCaseManager.INSTANCE.addEquivalenceClassToTestCase(eClass, testCase));
	                   // ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCase, structure));
						newTestCases.add(testCase);
					}
				} // if is used
			}// for each equivalence class
		}//for each element
		//Verify the Size of the new test cases if is not bigger than the allowed
		if (newTestCases.size()>0)
		{
			if ((newTestCases.size()+structure.getTestCasesByState(CMStateBean.STATE_POSITIVE).size())>CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting().getM_MaxNumOfPositiveTestCases())
			{
			 	throw new CMTestCaseGenerationException(CMMessages.getString("TESTCASE_GENERATION_POSITIVE_EQUIVALENCE_CLASSES_FILL")); //$NON-NLS-1$

			}else
			{
				ce.addEdit(insertTestCasesAt(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES,group, dependencies, newTestCases, structure));

			}

		}



		//if not put the new test cases into the Structure
		return ce;
	}

}
