

package bi.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.CMError;
import model.Combination;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.FaultyTestCaseGroup;
import model.IrrelevantTestCaseGroup;
import model.NegativeTestCaseGroup;
import model.PositiveTestCaseGroup;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.TestCaseGroup;
import model.TestObject;
import model.edit.CMModelEditFactory;
import model.edit.structure.CMAddTestCaseModelEdit;
import model.util.CMStateBean;
import model.util.IdSet;

import org.apache.log4j.Logger;

import bi.controller.editcontrol.CMRiskLevelEditController;
import bi.controller.editcontrol.CMStateEditController;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;

public class TestCaseManager {
	public static final TestCaseManager INSTANCE = new TestCaseManager();
    public TestCaseManager() {


    }



    public TestCase createTestCaseWithoutId(int state) {
        TestCase t = new TestCase();
        t.setState(state);
        return t;
    }

    public UndoableEdit addTestCaseToStructure(Structure p_Structure, TestCase t) {
    CMCompoundEdit ce = new CMCompoundEdit();
        if (t.getState() == CMStateBean.STATE_FAULTY) {
        	try
        	{
        		int nextId = this.getNextFaultyId(p_Structure);
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(t,nextId));
        	     t.setId(nextId);
        	}
        	catch (Exception e) {
        		e.printStackTrace();
				return null;
			}
        }
        else if (t.getState() == CMStateBean.STATE_POSITIVE || t.getState() == CMStateBean.STATE_IRRELEVANT) {
        	int nextId = this.getNextPositiveId(p_Structure);
        	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(t,nextId));
            t.setId(nextId);
        }
        else if (t.getState() == CMStateBean.STATE_NEGATIVE) {
            try {
            	int nextId = this.getNextNegativeId(p_Structure);
            	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(t,nextId));
				t.setId(nextId);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
        }
        ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestCaseModelEdit(p_Structure,t));
        p_Structure.addTestCase(t);
		return ce;
    }

    public UndoableEdit insertTestCaseInStructureAt(Structure p_Structure, TestCase t) {
        //look for the new id of the test case based on his test case group
    	if (t.getState() == CMStateBean.STATE_FAULTY) {
            try {
				t.setId(this.getNextFaultyId(p_Structure));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
        }
        else if (t.getState() == CMStateBean.STATE_POSITIVE || t.getState() == CMStateBean.STATE_IRRELEVANT) {
            t.setId(this.getNextPositiveId(p_Structure));
        }
        else if (t.getState() == CMStateBean.STATE_NEGATIVE) {
            try {
				t.setId(this.getNextNegativeId(p_Structure));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
        }
       //do the insertion
        p_Structure.addTestCase(t);
        //create the edit
		return new CMAddTestCaseModelEdit(p_Structure,t);
    }

    public int getNextPositiveId(Structure p_Structure) {
    	IdSet idSet = new IdSet();
    	for (TestCase testCase : p_Structure.getLnkTestCases()){
    			idSet.registerId(testCase.getId());
    	}
    	if (idSet.nextValidId()<=BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES)
    		return idSet.nextValidId();
    	else
    		return -1;
    }

    /**
     *  Calculate the next negative ID for a test case
     * @param p_structure
     * @return
     * @throws Exception
     */
    public int getNextNegativeId(Structure p_structure) throws Exception {
    	IdSet idSet = new IdSet();
    	for (TestCase testCase : p_structure.getLnkTestCases()){
    			idSet.registerId(testCase.getId());
    	}
    	if (idSet.nextValidId(BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES+1)<=
    		(BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES+BusinessRules.MAX_NUMBER_OF_NEGATIVE_TEST_CASES))
    		return idSet.nextValidId(BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES+1);
    	else
    		return -1;
    }

    /**
     *  Get the next faulty id for test cases
     * @param p_structure
     * @return
     * @throws Exception
     */
    public int getNextFaultyId(Structure p_structure) throws Exception {
    	IdSet idSet = new IdSet();
    	for (TestCase testCase : p_structure.getLnkTestCases()){
    			idSet.registerId(testCase.getId());
    	}
    	if (idSet.nextValidId(BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES+
    			BusinessRules.MAX_NUMBER_OF_NEGATIVE_TEST_CASES+1)<=
    		(BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES+
    		 BusinessRules.MAX_NUMBER_OF_NEGATIVE_TEST_CASES+
    		 BusinessRules.MAX_NUMBER_OF_FAULTY_TEST_CASES))
    		return idSet.nextValidId(BusinessRules.MAX_NUMBER_OF_POSITIVE_TEST_CASES+
        			BusinessRules.MAX_NUMBER_OF_NEGATIVE_TEST_CASES+1);
        	else
    		return -1;

    }



    //grueda14092004_begin




    public int getTestCasesByRiskLevel(int p_RiskLevel, TestObject p_TestObject) {
        int counter = 0;
        int numOfTestCases = p_TestObject.getStructure().getLnkTestCases().size();
        TestCase testCase = null;
        for (int i = 0; i < numOfTestCases; i++) {
            testCase = (TestCase)p_TestObject.getStructure().getLnkTestCases().elementAt(i);
            if (testCase.getRiskLevel() == p_RiskLevel) {
                counter++;
            }
        }
        return counter;
    }




    public boolean areEquivalenceClassesInTestCaseWithinTheSameElement(TestCase testCase, EquivalenceClass equivalenceClass) {
        Vector equivalenceClasses = testCase.getLnkEquivalenceClasses();
        int numOfEquivalenceClasses = testCase.getLnkEquivalenceClasses().size();
        Element element = equivalenceClass.getLnkElement();
        EquivalenceClass ec;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            ec = (EquivalenceClass)testCase.getLnkEquivalenceClasses().elementAt(i);
            if (ec.getLnkElement() == element) {
                return true;
            }
        }
        return false;
    }

    public boolean areCombinationsInTestCaseWithinTheSameElement(TestCase testCase, EquivalenceClass equivalenceClass) {
        Element element = equivalenceClass.getLnkElement();
        int numOfCombinations = testCase.getLnkCombinations().size();
        Combination combination;
        for (int i = 0; i < numOfCombinations; i++) {
            combination = (Combination)testCase.getLnkCombinations().elementAt(i);
            if (!(combination instanceof StdCombination)) {
                if (isCombinationInElement(combination, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isCombinationInElement(Combination p_Combination, Element p_Element) {
        int numOfEquivalenceClasses = p_Combination.getEquivalenceClassesRecursiv().size();
        EquivalenceClass equivalenceClass = null;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)p_Combination.getEquivalenceClassesRecursiv().get(i);
            if (equivalenceClass.getLnkElement() == p_Element) {
                return true;
            }
        }
        return false;
    }

    public Vector<EquivalenceClass> getVisibleEquivalenceClassesOfStdCombination(TestCase p_TestCase) {
        StdCombination stdCombination = p_TestCase.getStdCombination();
        Vector<EquivalenceClass> visibleEquivalenceClasses = new Vector<EquivalenceClass>(0);
        if (stdCombination != null) {
            EquivalenceClass equivalenceClass;
            int numOfEquivalenceClasses = stdCombination.getEquivalenceClasses().size();
            for (int i = 0; i < numOfEquivalenceClasses; i++) {
                equivalenceClass = (EquivalenceClass)stdCombination.getEquivalenceClasses().get(i);
                if (!areEquivalenceClassesInTestCaseWithinTheSameElement(p_TestCase, equivalenceClass)) {
                    if (!areCombinationsInTestCaseWithinTheSameElement(p_TestCase, equivalenceClass)) {
                        visibleEquivalenceClasses.addElement(equivalenceClass);
                    }
                }
            }
            return visibleEquivalenceClasses;
        }
        else {
            return visibleEquivalenceClasses;
        }
    }

  
  


    /**
     * Deletes the test case from the structure and the references from the errors
     * @param p_TestCase
     * @param p_Structure
     * @return
     */
    public UndoableEdit deleteTestCase(TestCase p_TestCase, Structure p_Structure) {
    	CMCompoundEdit ce = new CMCompoundEdit();
        if (p_Structure.getTestCases().contains(p_TestCase)) {
        	ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestCaseModelEdit(p_Structure,p_TestCase));
            p_Structure.removeTestCase(p_TestCase);
            ce.addEdit(ErrorManager.INSTANCE.removeTestCaseIfAssigned(p_Structure.getM_CMErrors(),p_TestCase));        }
        return ce;
    }

  
    





    public boolean isThereAnEquivalenceClassInTheTestCaseThatBelongsToTheSameElement(TestCase p_TestCase,
        EquivalenceClass p_EquivalenceClass) {
            int numOfCombinationsInTestCase = p_TestCase.getLnkCombinations().size();
            Vector equivalenceClassesOfCombinationsInTestCase = new Vector(0);
            Combination combination;
            EquivalenceClass equivalenceClass;
            int numOfEquivalenceClasses = 0;
            for (int i = 0; i < numOfCombinationsInTestCase; i++) {
                combination = (Combination)p_TestCase.getLnkCombinations().elementAt(i);
                if (!(combination instanceof StdCombination)) { //NEW 16.08.2003
                    numOfEquivalenceClasses = combination.getEquivalenceClassesRecursiv().size();
                    for (int j = 0; j < numOfEquivalenceClasses; j++) {
                        equivalenceClass = (EquivalenceClass)combination.getEquivalenceClassesRecursiv().get(j);
                        if (equivalenceClass.getLnkElement() == p_EquivalenceClass.getLnkElement()) {
                            if (equivalenceClass != p_EquivalenceClass) {
                                return true;
                            }
                        }
                    }
                }
            }
            numOfEquivalenceClasses = p_TestCase.getLnkEquivalenceClasses().size();
            for (int i = 0; i < numOfEquivalenceClasses; i++) {
                equivalenceClass = (EquivalenceClass)p_TestCase.getLnkEquivalenceClasses().elementAt(i);
                if (equivalenceClass.getLnkElement() == p_EquivalenceClass.getLnkElement()) {
                    if (equivalenceClass != p_EquivalenceClass) {
                        return true;
                    }
                }
            }
            return false;
    }

    public boolean isThereACombinationInTheTestCaseThatBelongsToAnEquivalenceClassOfTheSameElement(TestCase p_TestCase,
        EquivalenceClass p_EquivalenceClass) {
            int numOfCombinations = p_TestCase.getLnkCombinations().size();
            int numOfEquivalenceClasses;
            Combination combination;
            EquivalenceClass equivalenceClass;
            for (int i = 0; i < numOfCombinations; i++) {
                combination = (Combination)p_TestCase.getLnkCombinations().elementAt(i);
                if (!(combination instanceof StdCombination)) {
                    numOfEquivalenceClasses = combination.getEquivalenceClassesRecursiv().size();
                    for (int j = 0; j < numOfEquivalenceClasses; j++) {
                        equivalenceClass = (EquivalenceClass)combination.getEquivalenceClassesRecursiv().get(j);
                        if (equivalenceClass.getLnkElement() == p_EquivalenceClass.getLnkElement()) {
                            if (equivalenceClass != p_EquivalenceClass) { //NEW 16.08.2003
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
    }


    public boolean canCombinationBeAssignedToTestCase(Structure p_Structure, TestCase p_TestCase, Combination p_Combination) {
        Combination combination;
        EquivalenceClass equivalenceClass;
        EquivalenceClass ec;
        int numOfEquivalenceClasses = p_Combination.getEquivalenceClassesRecursiv().size();
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)p_Combination.getEquivalenceClassesRecursiv().get(i);
            if (isThereAnEquivalenceClassInTheTestCaseThatBelongsToTheSameElement(p_TestCase, equivalenceClass) &&
                !(areElementsEqual(p_Structure, p_TestCase, p_Combination, equivalenceClass.getLnkElement()))) {
                    return false;
            }
            else {
                int numOfCombinations = p_TestCase.getLnkCombinations().size();
                for (int j = 0; j < numOfCombinations; j++) {
                    combination = (Combination)p_TestCase.getLnkCombinations().elementAt(j);
                    if (!(combination instanceof StdCombination)) {
                        if (!combination.contains(equivalenceClass)) {
                            int numOfEquivalenceClassesInOtherCombinations =
                                combination.getEquivalenceClassesRecursiv().size();
                            for (int k = 0; k < numOfEquivalenceClassesInOtherCombinations; k++) {
                                ec = (EquivalenceClass)combination.getEquivalenceClassesRecursiv().get(k);
                                if (equivalenceClass.getLnkElement() == ec.getLnkElement()) {
                                    if (equivalenceClass != ec) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean areElementsEqual(Structure p_Structure, TestCase p_TestCase, Combination p_Combination, Element p_Element) {
        int numOfElements = p_Structure.getLnkElements().size();
        Element element;
        EquivalenceClass equivalenceClass;
        for (int i = 0; i < numOfElements; i++) {
            element = (Element)p_Structure.getLnkElements().elementAt(i);
            if (element == p_Element) {
                int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
                for (int j = 0; j < numOfEquivalenceClasses; j++) {
                    equivalenceClass = (EquivalenceClass)element.getEquivalenceClasses().get(j);
                    if ((p_Combination.contains(equivalenceClass) && isEquivalenceClassInTestCase(p_TestCase, equivalenceClass))
                        || (!(p_Combination.contains(equivalenceClass)) &&
                        !(isEquivalenceClassInTestCase(p_TestCase, equivalenceClass)))) {
                            ; //Equal
                    }
                    else {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     *  Checks if a equivalence class is contained in a test case
     * @param p_TestCase
     * the test case to search the equivalence class
     * @param p_EquivalenceClass
     * the key equivalence class
     * @return
     * if is referenced in the given test case
     */
    public boolean isEquivalenceClassInTestCase(TestCase p_TestCase, EquivalenceClass p_EquivalenceClass) {
        Vector equivalenceClasses = getEquivalenceClassesOfTestCase_2(p_TestCase);
        if (equivalenceClasses.contains(p_EquivalenceClass)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * retturns all the equivalence classes that are referenced in a specific equivalence class
     * this method differs from the regular in that the equivalence classes returned are unique
     * and also take in count the equivalence classes from the combinations
     * @param p_TestCase
     * the test case refenciator
     * @return
     * referenciated equivalence classes
     */
    public Vector getEquivalenceClassesOfTestCase_2(TestCase p_TestCase) {

    	//get the equivalence classes directly referenciated without  repeating
        int numOfEquivalenceClasses = p_TestCase.getLnkEquivalenceClasses().size();
        EquivalenceClass equivalenceClass = null;
        Vector equivalenceClassesOfTestCase = new Vector(0);
        Combination combination;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)p_TestCase.getLnkEquivalenceClasses().elementAt(i);
            if (!equivalenceClassesOfTestCase.contains(equivalenceClass)) {
                equivalenceClassesOfTestCase.addElement(equivalenceClass);
            }
        }
        //gets the equivalence classes from the combinations
        int numOfCombinations = p_TestCase.getLnkCombinations().size();
        for (int i = 0; i < numOfCombinations; i++) {
            combination = (Combination)p_TestCase.getLnkCombinations().elementAt(i);
            if (!(combination instanceof StdCombination)) {
                numOfEquivalenceClasses = combination.getEquivalenceClassesRecursiv().size();
                for (int j = 0; j < numOfEquivalenceClasses; j++) {
                    equivalenceClass = (EquivalenceClass)combination.getEquivalenceClassesRecursiv().get(j);
                    if (!equivalenceClassesOfTestCase.contains(equivalenceClass)) {
                        equivalenceClassesOfTestCase.addElement(equivalenceClass);
                    }
                }
            }
        }

        //gets the equialence classes from visible standart combinations
        Vector visibleEquivalenceClasses = this.getVisibleEquivalenceClassesOfStdCombination(p_TestCase);
        int numOfVisibleEquivalenceClasses = visibleEquivalenceClasses.size();
        for (int i = 0; i < numOfVisibleEquivalenceClasses; i++) {
            equivalenceClass = (EquivalenceClass)visibleEquivalenceClasses.elementAt(i);
            equivalenceClassesOfTestCase.addElement(equivalenceClass);
        }
        //grueda27042005_end
        return equivalenceClassesOfTestCase;
    }



    public boolean existsTestCaseWithTheSameValues(TestCase p_TestCase, Vector p_TestCases, Vector p_Elements){
    return false;
//      int numOfExistingTestCases = p_TestCases.size();
//      TestCase testCase = null;
//      Vector elementValuesOfNewTestCase = getAllEquivalenceClassesReferencedInTestCase(p_Elements, p_TestCase);
//      Vector elementValuesOfExistingTestCase = null;
//
//      for( int i = 0; i < numOfExistingTestCases; i++){
//        testCase = (TestCase) p_TestCases.elementAt(i);
//        elementValuesOfExistingTestCase = getAllEquivalenceClassesReferencedInTestCase(p_Elements, testCase);
//        if( elementValuesOfNewTestCase.equals(elementValuesOfExistingTestCase) ){
//          return true;
//        }
//      }
//      return false;
    }
    /**
     *  Gets all the equivalence classes from a list of elements that are referenced in a list of test cases (join)
     * @param p_TestCases
     * @param p_Elements
     * @return
     */
    public Vector getAllEquivalenceClassesReferencedInTestCases(Vector p_TestCases, Vector p_Elements){

    	Vector result= new Vector(0);
    	//for each test case
    	for( Iterator i = p_TestCases.iterator(); i.hasNext();){
            TestCase testCase = (TestCase) i.next();
            Set elementValuesOfExistingTestCase = getAllEquivalenceClassesReferencedInTestCase(p_Elements, testCase);
            result.addElement(elementValuesOfExistingTestCase);
    	}
    	return result;

    }
    /**
     * Compares a generated vector of referenced equivalence classes to see if equals any of the vectors contained in te referenced equivalence class vector
     * if it is add the vector to the p_ref vector and return true else a
     * @param p_TestCase
     * @param p_Elements
     * @param p_ExistedValuesTestCases
     * @return
     */
    public boolean existsTestCaseWithTheSameValues2(TestCase p_TestCase, Vector p_Elements, Vector p_referencedEquivalenceClasses){
        //gets all referenced equivalence classes in the new test case
        Set referencedEqClOfNewTestCase = getAllEquivalenceClassesReferencedInTestCase(p_Elements, p_TestCase);

        Set referencedEqClOfOldTestCase = null;
        //for each vector of equivalence classes referenced
        for( Iterator i = p_referencedEquivalenceClasses.iterator(); i.hasNext();){

        	referencedEqClOfOldTestCase=(Set) i.next();
        	//if the passed new vector equals the new one add it to the referenced equivalence class vector and return true
          if( referencedEqClOfNewTestCase.equals(referencedEqClOfOldTestCase) ){
        	  p_referencedEquivalenceClasses.addElement(referencedEqClOfNewTestCase);
            return true;
          }
        }
        p_referencedEquivalenceClasses.addElement(referencedEqClOfNewTestCase);
        return false;
      }

    
    /**
     *  Returns all the equivalence classes from a element that are referenced by a test case
     * @author smoreno
     * @param p_Element
     * @param p_TestCase
     * @return
     */
    public Vector<EquivalenceClass> getAllEquivalenceClassesReferencedInTestCase(Element p_Element, TestCase p_TestCase) {
    	  Vector<EquivalenceClass> result = new Vector();
          result.addAll(p_TestCase.getRelatedEquivalenceClasses());
          result.retainAll(p_Element.getEquivalenceClasses());

          return result;

    }

    /**
     *  gets all the equivalence classes from a list of elements that are referenced in a test case
     * @author smoreno
     * @param p_Elements
     * @param p_TestCase
     * @return
     */
    public Set getAllEquivalenceClassesReferencedInTestCase(Vector p_Elements, TestCase p_TestCase){
    	TreeSet values = new TreeSet();
    	for (Iterator i = p_Elements.iterator(); i.hasNext();)
    		values.addAll(getAllEquivalenceClassesReferencedInTestCase((Element)i.next(), p_TestCase));
    	return values;


    }

    public UndoableEdit addCombinationToTestCase(Combination p_Combination, TestCase p_TestCase) {
    	CMCompoundEdit ce = new CMCompoundEdit();

    	ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(p_TestCase,p_Combination));
        p_TestCase.addCombination(p_Combination);

    	CMRiskLevelEditController riskLevelEditController = new CMRiskLevelEditController();
    	riskLevelEditController.setRiskLevelView(new CMRiskLevelView());
		riskLevelEditController.setRiskLevelBean(p_TestCase);
		ce.addEdit(riskLevelEditController.applyChanges());

		CMStateEditController stateEditController = new CMStateEditController();
		stateEditController.setStateView(new CMStateView());
    	stateEditController.setStateBean(p_TestCase);
		ce.addEdit(stateEditController.applyChanges());




        return ce;
    }

    public UndoableEdit addEquivalenceClassToTestCase(EquivalenceClass p_EquivalenceClass, TestCase p_TestCase) {
    	CMCompoundEdit ce = new CMCompoundEdit();
    	 ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(p_TestCase,p_EquivalenceClass));
         p_TestCase.addEquivalenceClass(p_EquivalenceClass);

        	CMRiskLevelEditController riskLevelEditController = new CMRiskLevelEditController();
        	riskLevelEditController.setRiskLevelView(new CMRiskLevelView());
    		riskLevelEditController.setRiskLevelBean(p_TestCase);
    		ce.addEdit(riskLevelEditController.applyChanges());

        	CMStateEditController stateEditController = new CMStateEditController();
        	stateEditController.setStateView(new CMStateView());
        	stateEditController.setStateBean(p_TestCase);
    		ce.addEdit(stateEditController.applyChanges());


        return ce;
    }

    public int getNumberOfTestCases(Structure p_Structure, TestCaseGroup p_Group) {
        int numOfTestCases = p_Structure.getLnkTestCases().size();
        TestCase testCase;
        int counter = 0;
        for (int i = 0; i < numOfTestCases; i++) {
            testCase = (TestCase)p_Structure.getLnkTestCases().elementAt(i);
            if (testCase.getTestCaseGroup() == p_Group) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Gets the number of Test Cases from a Structure in a specific group that has a common dependency with the
     * passed dependencies
     * @param p_Structure
     * @param p_Group
     * @param p_Dependencies
     * @return
     */
    public int getNumberOfTestCases(Structure p_Structure, TestCaseGroup p_Group, Vector p_Dependencies) {

        TestCase testCase;
        int counter = 0;
        for (Iterator i = p_Structure.getLnkTestCases().iterator();i.hasNext();) {
            testCase = (TestCase)i.next();
            if (testCase.getTestCaseGroup() == p_Group) {
                if (!hasCommonDependency(testCase.getDependencies(), p_Dependencies)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    /**
     * gets the last test case position in a structure  a given group of test cases that has common dependencies
     * with the vector of dependencies and a position in the list of test cases
     * @param p_Position
     * the relative initial position
     * @param p_Group
     * the group of test cases to find the test cases
     * @param p_Dependencies
     * the list of dependencies that are to be taked in  count
     * @param p_Structure
     * the structure that holds the test cases
     * @return
     * the real insertion position
     */
    public int getRealPositionInStructure(int p_Position, TestCaseGroup p_Group, Vector p_Dependencies,
        Structure p_Structure) {
    	    //initially the position is the beginning
            int realPosition = 0;
            //get all the posible groups of tests cases
            TestCaseGroup positiveGroup = (PositiveTestCaseGroup)p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES);
            TestCaseGroup negativeGroup = (NegativeTestCaseGroup)
                p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES);
            TestCaseGroup faultyGroup = (FaultyTestCaseGroup)
                p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_FAULTIES);
            TestCaseGroup irrelevantGroup = (IrrelevantTestCaseGroup)
                p_Structure.getM_TestCaseGroups().get(BusinessRules.TEST_CASE_GROUP_POSITION_OF_IRRELEVANTS);
            //if the given position is of the positive test cases
            if (p_Position == BusinessRules.TEST_CASE_GROUP_POSITION_OF_POSITIVES) {
            	//the real position is the postion plus the existin positive related by dependency testcases count
                realPosition = p_Position + getNumberOfTestCases(p_Structure, positiveGroup, p_Dependencies);
            }
//          if the given position is of the negative test cases
            else if (p_Position == BusinessRules.TEST_CASE_GROUP_POSITION_OF_NEGATIVES) {
//            	the real position is the postion plus the existin positive and negative related by dependency testcases count
                realPosition = getNumberOfTestCases(p_Structure, positiveGroup) +
                    getNumberOfTestCases(p_Structure, negativeGroup, p_Dependencies);
            }
            else if (p_Position == BusinessRules.TEST_CASE_GROUP_POSITION_OF_FAULTIES) {
//            	the real position is the postion plus the existin positive, negative and faulty related by dependency testcases count
                realPosition = getNumberOfTestCases(p_Structure, positiveGroup) +
                    getNumberOfTestCases(p_Structure, negativeGroup) +
                    getNumberOfTestCases(p_Structure, faultyGroup, p_Dependencies);
            }
            else if (p_Position == BusinessRules.TEST_CASE_GROUP_POSITION_OF_IRRELEVANTS) {
//            	the real position is the postion plus the existin positive, negative,faulty and irrelevant related by dependency testcases count
                realPosition = getNumberOfTestCases(p_Structure, positiveGroup) +
                    getNumberOfTestCases(p_Structure, negativeGroup) + getNumberOfTestCases(p_Structure, faultyGroup) +
                    getNumberOfTestCases(p_Structure, irrelevantGroup, p_Dependencies);
            }
            return realPosition;
    }

    public boolean canCombinationBeAssignedToTestCaseConsideringTheState(TestCase p_TestCase, Combination combination) {
        if (combination.getState() != CMStateBean.STATE_IRRELEVANT) {
            if (p_TestCase.getState() == combination.getState()) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    

    /**
     * check if one dependency from p_DependenciesOfTestCase is contained in p_DependenciesOfSelected
     * or if the passed vectors are empty
     * @param p_DependenciesOfTestCase
     * @param p_DependenciesOfSelected
     * @return
     */
    public boolean hasCommonDependency(Vector p_DependenciesOfTestCase, Vector p_DependenciesOfSelected) {
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

   

    public void deleteCMErrorOfTestCases(CMError p_CMError, Structure p_Structure) {
        int numOfTestCases = p_Structure.getLnkTestCases().size();
        TestCase testCase = null;
        for (int i = 0; i < numOfTestCases; i++) {
            testCase = (TestCase)p_Structure.getLnkTestCases().elementAt(i);
            if (testCase.getM_CMErrors() != null) {
                if (testCase.getM_CMErrors().contains(p_CMError)) {
                    testCase.getM_CMErrors().removeElement(p_CMError);
                }
            }
        }
    }

    public ElementManager getM_ElementManager() {
        return m_ElementManager;
    }

    public void setM_ElementManager(ElementManager m_ElementManager) {
        this.m_ElementManager = m_ElementManager;
    }

    public CombinationManager getM_CombinationManager() {
    	//lazy initialization
    	if (m_CombinationManager==null)
    		m_CombinationManager = CombinationManager.INSTANCE;
        return m_CombinationManager;
    }



    public DependencyManager getM_DependencyManager() {
        return m_DependencyManager;
    }

    public void setM_DependencyManager(DependencyManager m_DependencyManager) {
        this.m_DependencyManager = m_DependencyManager;
    }

 /*
 * BUG 326
 *Description :interne Verwaltungsindizes nicht in exportierte Testf�lle �bernehmen
 *Realizado por : Harold Canedo Lopez
 */

    public int countTestCases(Structure p_Structure, int p_State) {
        int counter = 0;
        int numOfTestCases = p_Structure.getLnkTestCases().size();
        TestCase testCase = null;
        for (int i = 0; i < numOfTestCases; i++) {
            testCase = (TestCase)p_Structure.getLnkTestCases().elementAt(i);
            if (testCase.getState() == p_State) {
                counter++;
            }
        }
        return counter;
    }


  

    public UndoableEdit removeCombinationInTestCases(Combination p_Combination, Structure p_Structure) {
    	CMCompoundEdit ce = new CMCompoundEdit();
        int numOfTestCases = p_Structure.getLnkTestCases().size();
        TestCase testCase;
        for (int i = 0; i < numOfTestCases; i++) {
            testCase = (TestCase)p_Structure.getLnkTestCases().elementAt(i);
            if (testCase.getCombinations().contains(p_Combination)) {
            	ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(testCase, p_Combination));
                testCase.removeCombination(p_Combination);
              //  updateTestCaseDescription(testCase, p_Structure);
            }
        }
        return ce;
    }

   

    public ErrorManager getM_ErrorManager(){
        return m_ErrorManager;
    }

    public void setM_ErrorManager(ErrorManager m_ErrorManager){
            this.m_ErrorManager = m_ErrorManager;
        }

  

    private ElementManager m_ElementManager;
    private CombinationManager m_CombinationManager;
    private DependencyManager m_DependencyManager;
    private ErrorManager m_ErrorManager;
	public void findTestCaseWithStdCombinationToDelete(Element element, Structure structure, List indexesTestCaseDeleted) {
		// TODO Auto-generated method stub

	}



	public boolean testCaseHasTheSameNegativeEquivalenceClasses(TestCase p_TestCase, Structure p_Structure){
	    int numOfTestCases = p_Structure.getLnkTestCases().size();
	    TestCase testCase = null;
	    for( int i = 0; i < numOfTestCases; i++) {
		  testCase = (TestCase) p_Structure.getLnkTestCases().elementAt(i);
	      Vector dependencies = testCase.getDependencies();
	      if( dependencies == null) {
	        if( p_TestCase.getDependencies() == null){
	          if( haveTheSameNegativeEquivalenceClasses(testCase, p_TestCase)) {
	            return true;
	          }
	        }
	      }
	    }
	    return false;
	  }

	
	public boolean haveTheSameNegativeEquivalenceClasses(TestCase p_TestCase1, TestCase p_TestCase2){
	    int numOfEquivalenceClasses = p_TestCase1.getLnkEquivalenceClasses().size();
		EquivalenceClass equivalenceClass = null;

	    for( int i = 0; i < numOfEquivalenceClasses; i++) {
	      equivalenceClass = (EquivalenceClass) p_TestCase1.getLnkEquivalenceClasses().elementAt(i);
	      if( !p_TestCase2.getLnkEquivalenceClasses().contains(equivalenceClass)) {
	        return false;
	      }
	    }
	    numOfEquivalenceClasses = p_TestCase2.getLnkEquivalenceClasses().size();
	    for( int i = 0; i < numOfEquivalenceClasses; i++) {
	      equivalenceClass = (EquivalenceClass) p_TestCase2.getLnkEquivalenceClasses().elementAt(i);
	      if( !p_TestCase1.getLnkEquivalenceClasses().contains(equivalenceClass)) {
	        return false;
	      }
	    }
	    return true;
	  }




	/**
	 * @param p_GeneratedTestCases
	 * @param p_Structure
	 * @return
	 * @deprecated
	 * @since 2.6
	 */
	public Vector getOnlyTheNewFaultyTestCases(Vector p_GeneratedTestCases, Structure p_Structure){
	    int numOfTestCases = p_GeneratedTestCases.size();
		TestCase testCase = null;
		Vector newTestCases = new Vector(0);

	    for( int i = 0; i < numOfTestCases; i++) {
	      testCase = (TestCase) p_GeneratedTestCases.elementAt(i);
	      if( !testCaseHasTheSameFaultyEquivalenceClasses(testCase, p_Structure)) {
	        newTestCases.addElement(testCase);
	      }
	    }
	    return newTestCases;
	  }

	public boolean testCaseHasTheSameFaultyEquivalenceClasses( TestCase p_TestCase, Structure p_Structure){
	    int numOfTestCases = p_Structure.getLnkTestCases().size();
	    TestCase testCase = null;
	    for( int i = 0; i < numOfTestCases; i++) {
		  testCase = (TestCase) p_Structure.getLnkTestCases().elementAt(i);
	      Vector dependencies = testCase.getDependencies();
	      if( dependencies == null) {
	        if( p_TestCase.getDependencies() == null){
	          if( haveTheSameFaultyEquivalenceClasses(testCase, p_TestCase)) {
	            return true;
	          }
	        }
	      }
	    }
	    return false;
	  }

	public boolean haveTheSameFaultyEquivalenceClasses(TestCase p_TestCase1, TestCase p_TestCase2){
	    int numOfEquivalenceClasses = p_TestCase1.getLnkEquivalenceClasses().size();
		EquivalenceClass equivalenceClass = null;

	    for( int i = 0; i < numOfEquivalenceClasses; i++) {
	      equivalenceClass = (EquivalenceClass) p_TestCase1.getLnkEquivalenceClasses().elementAt(i);
	      if( !p_TestCase2.getLnkEquivalenceClasses().contains(equivalenceClass)) {
	        return false;
	      }
	    }
	    numOfEquivalenceClasses = p_TestCase2.getLnkEquivalenceClasses().size();
	    for( int i = 0; i < numOfEquivalenceClasses; i++) {
	      equivalenceClass = (EquivalenceClass) p_TestCase2.getLnkEquivalenceClasses().elementAt(i);
	      if( !p_TestCase1.getLnkEquivalenceClasses().contains(equivalenceClass)) {
	        return false;
	      }
	    }
	    return true;
	  }


	/**
	 * @author smoreno
	 * Searchs and destroy every test object that is referenced in one of the combinations of the vector of combinations
	 * @param mergingCombinations
	 * @param p_Structure
	 * @return
	 * an undoable operation
	 */
	public UndoableEdit removeReferecedTestCasesbyCombinations(List<Combination> mergingCombinations, Structure p_Structure) {
		//compound edit to hold the edition
		CMCompoundEdit ce = new CMCompoundEdit();
		//create a list of test cases to be deleted based on a vector of combinations
		Vector testCasesToBeDeleted = findReferecedTestCasesbyCombinations(mergingCombinations,p_Structure);
		//for each one of the list delete all references
		for (Iterator i = testCasesToBeDeleted.iterator();i.hasNext();)
		{
			ce.addEdit(deleteTestCase((TestCase) i.next(), p_Structure));
		}
		//return the undoable operation
		return ce;
	}
	/**
	 * @author smoreno
	 * finds all test cases from a structure that references a list of combinations
	 * @param mergingCombinations
	 * @param structure
	 * @return
	 */
	private Vector findReferecedTestCasesbyCombinations(List<Combination> mergingCombinations, Structure structure) {
		Vector referencedTestCases = new Vector(0);
		//for each combination
		for (Iterator i = mergingCombinations.iterator();i.hasNext();)
		{
			Combination combination = (Combination)i.next();
			for (Iterator j = structure.getLnkTestCases().iterator();j.hasNext();)
			{
				//for esch testcase of the structure
				TestCase testCase = (TestCase) j.next();
				//if the combination is referenced
				if (testCase.getCombinations().contains(combination))
				{
					//add the test case
					if (!referencedTestCases.contains(testCase))
						referencedTestCases.add(testCase);
				}
			}
		}
		return referencedTestCases;
	}

	/**
	 * Reload all test Cases views based on the structure passed
	 * @param structure
	 */
	public void reloadTestCaseViews(Structure structure) {
		//CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().
		//	getLnkCMElementViews().update();
		try
		{CMApplication.frame.getJSPlitPaneTestCaseStructureView().setM_Structure(structure);

		CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().
			getLnkCMTestCaseViews().getM_CMElementAndTestCaseViews().getM_CMTestCaseStructureView().
			getM_CMDescriptionTestCaseViews().updateAll(structure.getLnkTestCases());
		}
		catch (Exception e) {
			Logger.getLogger(this.getClass()).error("Could not update the test case views " + e.getMessage());
		}

	}

	public void reloadTestCaseViews() {
		reloadTestCaseViews(CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMElementAndTestCaseViews().
				getLnkCMElementViews().getM_Structure());
	}

	/**
	*@autor smoreno
	 * @return
	 */
	public static TestCase getSelectedTestCase() {

		TestCase tc = CMApplication.frame.getCMTestCaseViews().getSelectedTestCase();
		if (tc==null)
			tc= CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews().getSelectedTestCase();
		return tc;
	}

	/**
	*@autor smoreno
	 * @return
	 */
	public static EquivalenceClass getSelectedEquivalenceClass() {
		// TODO Auto-generated method stub
		return CMApplication.frame.getCMTestCaseViews().getSelectedEquivalenceClass();
	}


}
