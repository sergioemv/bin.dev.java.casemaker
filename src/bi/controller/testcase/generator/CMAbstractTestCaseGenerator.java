/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.controller.testcase.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.Combination;
import model.Dependency;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.edit.CMModelEditFactory;
import bi.controller.TestCaseManager;
import bi.view.undomanagementviews.CMCompoundEdit;

/**
 * @author smoreno
 *
 */
public abstract class  CMAbstractTestCaseGenerator implements CMTestCaseGenerator{

	protected Structure structure;
	protected List<Dependency> dependencies;
	/**
	 *
	 */
	public CMAbstractTestCaseGenerator() {
		// TODO Auto-generated constructor stub
	}
	/**
	 *
	 */
	public CMAbstractTestCaseGenerator(Structure p_structure, List<Dependency> p_Dependencies) {
		this.structure = p_structure;
		this.dependencies = p_Dependencies;
	}
	public abstract UndoableEdit generate() throws CMTestCaseGenerationException;
	protected List<Dependency> getDependencies() {
		return this.dependencies;
	}
	public void setDependencies(List<Dependency> p_dependencies) {
		this.dependencies = p_dependencies;
	}
	protected Structure getStructure() {
		return this.structure;
	}
	public void setStructure(Structure p_structure) {
		this.structure = p_structure;
	}
	protected boolean hasCommonDependency(Structure p_Structure, TestCaseGroup p_Group, List<Dependency> p_Dependencies) {
        //num of test cases in the structure
    	final int numOfTestCases = p_Structure.getLnkTestCases().size();
        TestCase testCase;
        for (int i = 0; i < numOfTestCases; i++) {
            testCase = (TestCase)p_Structure.getLnkTestCases().elementAt(i);
            //if the testcase
            if (testCase.getTestCaseGroup() == p_Group) {
                if (hasCommonDependency(testCase.getDependencies(), p_Dependencies)) {
                    return true;
                }
            }
        }
        return false;
    }
	/**
     * check if one dependency from p_DependenciesOfTestCase is contained in p_DependenciesOfSelected
     * or if the passed vectors are empty
     * @param p_DependenciesOfTestCase
     * @param p_DependenciesOfSelected
     * @return
     */
    protected boolean hasCommonDependency(Vector p_DependenciesOfTestCase, List<Dependency> p_DependenciesOfSelected) {
        if (p_DependenciesOfTestCase != null) {
            Dependency dependency;
            int numOfDependenciesOfTestCase = p_DependenciesOfTestCase.size();
            int numOfDependenciesOfSelected = p_DependenciesOfSelected.size();
            // if the vector are empty then are equal. . .
            if (numOfDependenciesOfTestCase == 0 && numOfDependenciesOfSelected == 0) {
                return true;
            }
            //
            for (int i = 0; i < numOfDependenciesOfTestCase; i++) {
                dependency = (Dependency)p_DependenciesOfTestCase.elementAt(i);

                if (p_DependenciesOfSelected.contains(dependency)) {
                    return true;
                }
            }
            return false;
        }
        else {
            //OLD: 20.10.2003 return true;
            return false;
        }
    }
    /**
     * @author smoreno
     * Deletes the test cases from the structure that belongs to a the dependencies vector and to the test case group
     * @param p_Structure
     *  the structure that holds all test cases
     * @param p_Group
     * the group to filter the deletion
     * @param p_Dependencies
     * the dependencies to filter the deletion
     */
    public  CMCompoundEdit deleteTestCases(Structure p_Structure, TestCaseGroup p_Group, List<Dependency> p_Dependencies) {
    	CMCompoundEdit ce = new CMCompoundEdit();
    	Vector<TestCase> deletedTestCases = new Vector<TestCase>();
        for (Iterator i = p_Structure.getLnkTestCases().iterator(); i.hasNext();) {
        	//for each test case in the structure
            TestCase testCase = (TestCase)i.next();
            //if the test case belongs to the same group
            if (testCase.getTestCaseGroup() == p_Group) {
            	//if the dependencies set related to the test case has a common dependency with the passed
                if (hasCommonDependency(testCase.getDependencies(), p_Dependencies)) {
                    	//remove the element from the structure (structure manager should do this)
                	    deletedTestCases.add(testCase);

                }
            }
        }
        //to avoid concurrency problems
        for (Iterator i = deletedTestCases.iterator();i.hasNext();)
        {
        	TestCase testCase = (TestCase)i.next();
        	ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestCaseModelEdit(p_Structure, testCase));
        	p_Structure.getLnkTestCases().remove(testCase);
        }

        return ce;
    }
    /**
     * Inserts a group of generated tests case in a given position of the structure
     * @param p_Position
     * @param p_Group
     * @param p_Dependencies
     * @param p_TestCases
     * @param p_Structure
     */
    public CMCompoundEdit insertTestCasesAt(int p_Position, TestCaseGroup p_Group, List<Dependency> p_Dependencies, List<TestCase> p_TestCases,
        Structure p_Structure) {
    	   CMCompoundEdit ce = new CMCompoundEdit();
    		// get the initial position for the insertion of the test cases (dependending on what is already there)
    	   Vector<Dependency> deps = new Vector<Dependency>();
    	   deps.addAll(p_Dependencies);
            int positionInStructure = TestCaseManager.INSTANCE.getRealPositionInStructure(p_Position, p_Group, deps, p_Structure);
            //a vector that holds the test Cases that must be deleted
            Vector<TestCase> testCasesToBeDeleted = new Vector<TestCase>(0);
            Vector referencedEquivalenceClasses= TestCaseManager.INSTANCE.getAllEquivalenceClassesReferencedInTestCases(p_Structure.getLnkTestCases(), p_Structure.getLnkElements());
            for(Iterator i = p_TestCases.iterator(); i.hasNext();) {
                TestCase testCase = (TestCase)i.next();
                // for each test case passed
                //if the test case doesnt exists with the same values in the structure
                //TODO the method is doing a "hidden" add to the vector
                if((p_Structure.getTestObject().getOrigin()!=null)||
                		(!TestCaseManager.INSTANCE.existsTestCaseWithTheSameValues2(testCase, p_Structure.getLnkElements(), referencedEquivalenceClasses))){
                	//if a test case already exists in this
	                    ce.addEdit(TestCaseManager.INSTANCE.addTestCaseToStructure(p_Structure, testCase));
	                    positionInStructure++;
	               
                }
                else {

                	//if the test case already exist add it to a vector of deletion
                  testCasesToBeDeleted.addElement(testCase);
                }
            }

            int numOfTestCasesToBeDeleted = testCasesToBeDeleted.size();
            for( int i = 0; i < numOfTestCasesToBeDeleted; i++) {
              TestCase testCase = (TestCase) testCasesToBeDeleted.elementAt(i);
              if( p_TestCases.contains(testCase)){
            	  //TODO remove it from the structure???
                p_TestCases.remove(testCase);
              }
            }

			return ce;
    }
    /**
     *  From a List of dependencies obtains the number of combinations from the dependency that has more combinations in a state
	 * @param p_dependencies
	 * @param p_state_positive
	 * @return
	 */
	protected int getMaxTotalNumberOfCombinations(List<Dependency> p_Dependencies, int p_state) {
		 //check wich dependency has the maximal number of positive combniations
    	int maxNumOfPositiveCombinations = 0;
		for (Dependency dependency : p_Dependencies)
	    {
	        int numOfPositiveCombinations =  dependency.getCombinationsByState(p_state).size();

			//CombinationManager.INSTANCE.getNumberOfPositiveCombinations(dependency.getLnkCombinations());
	        if (numOfPositiveCombinations > maxNumOfPositiveCombinations) {
	            maxNumOfPositiveCombinations = numOfPositiveCombinations;
	        }
	    }
		return maxNumOfPositiveCombinations;
	}
	/**
	 *  Generates 1 test case per combination
	 * @return the list of generated test cases
	 */
	protected List<TestCase> generateWorkFlowTestCases(CMCompoundEdit p_ce, int p_State, TestCaseGroup p_testCaseGroup) {
		TestCase testCase = null;
	    List<TestCase> generatedTestCases = new ArrayList<TestCase>();
	   //for each combination in the dependency a test case must be created in the same order of the combination
	   for (Dependency dependency : getDependencies())
		   for (Combination combination : dependency.getCombinationsByState(p_State))
		   {
		        testCase = TestCaseManager.INSTANCE.createTestCaseWithoutId(p_State);
		        Vector deps = new Vector();
		        deps.addAll(getDependencies());
		        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseDependenciesModelEdit(testCase,deps));
		        testCase.setDependencies(deps);
		        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTestCaseGroupModelEdit(testCase,p_testCaseGroup));
		        testCase.setTestCaseGroup(p_testCaseGroup);
		        p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(testCase,TestCase.Origin.AUTOMATIC));
		        testCase.setOriginType(TestCase.Origin.AUTOMATIC);
		        generatedTestCases.add(testCase);
		        p_ce.addEdit(TestCaseManager.INSTANCE.addCombinationToTestCase(combination, testCase));
            //    p_ce.addEdit(TestCaseManager.INSTANCE.updateTestCaseDescription(testCase, getStructure()));

		   }

	    return generatedTestCases;
	}
}

