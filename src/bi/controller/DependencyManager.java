

package bi.controller;

import java.util.List;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.Combination;
import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.Structure;
import model.TestCase;
import model.edit.CMModelEditFactory;
import model.util.CMStateBean;
import model.util.IdSet;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

public class DependencyManager {
    public static final DependencyManager INSTANCE = new DependencyManager();
    public DependencyManager() {

        }


    public Dependency createDependency(Structure p_structure) {
        Dependency d = new Dependency();
        d.setId(this.getNextId(p_structure));
        return d;
    }

    public int getNextId(Structure p_structure) {
    	 IdSet idSet = new IdSet();
    	   for (Dependency dependency : p_structure.getDependencies())
    		   idSet.registerId(dependency.getId());
           return idSet.nextValidId();
    }



    public UndoableEdit removeCombinationsFromDependency(List<Combination> mergingCombinations, Dependency dependency) {
        bi.view.undomanagementviews.CMCompoundEdit ce = new bi.view.undomanagementviews.CMCompoundEdit();

        int numOfMergingCombinations = mergingCombinations.size();
        Combination mergingCombination = null;
        for (int i = 0; i < numOfMergingCombinations; i++) {
            mergingCombination = (Combination)mergingCombinations.get(i);
            if (dependency.getCombinations().contains(mergingCombination)) {
            	ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(dependency,mergingCombination));
                dependency.removeCombination(mergingCombination);
            }
        }
        //ce.end();
		return ce;
    }

    public void removeElementsOfDependencies(Element p_element, Structure p_structure) {
        Vector dependencies = p_structure.getLnkDependencies();
        Dependency dependency = null;
        Element element = null;
        //Vector elementsToBeRemoved = new Vector(0);
        int numOfDependencies = dependencies.size();
        for (int i = 0; i < numOfDependencies; i++) {
            dependency = (Dependency)dependencies.elementAt(i);
            if (dependency.getLnkElements().contains(p_element)) {
                dependency.getLnkElements().removeElement(p_element);
            }
        }
    }


    public CombinationManager getLnkCombinationManager() {
        return lnkCombinationManager;
    }

    public void setLnkCombinationManager(CombinationManager lnkCombinationManager) {
        this.lnkCombinationManager = lnkCombinationManager;
    }

    public TestCaseManager getLnkTestCaseManager() {
        return lnkTestCaseManager;
    }

    public void setLnkTestCaseManager(TestCaseManager lnkTestCaseManager) {
        this.lnkTestCaseManager = lnkTestCaseManager;
    }



    public void removeEquivalenceClassesOfDependenciesWithTheElement(Element p_element, Structure p_structure) {
        int numOfDependencies = p_structure.getLnkDependencies().size();
        Dependency dependency = null;
        EquivalenceClass equivalenceClass = null;
        int numOfEquivalenceClasses = 0;
        Vector equivalenceClassesToBeRemoved = new Vector(0);
        for (int i = 0; i < numOfDependencies; i++) {
            dependency = (Dependency)p_structure.getLnkDependencies().elementAt(i);
            numOfEquivalenceClasses = dependency.getLnkEquivalenceClasses().size();
            for (int j = 0; j < numOfEquivalenceClasses; j++) {
                equivalenceClass = (EquivalenceClass)dependency.getLnkEquivalenceClasses().elementAt(j);
                if (equivalenceClass.getLnkElement() == p_element) {
                    equivalenceClassesToBeRemoved.addElement(equivalenceClass);
                }
            }
        }
        int numOfEquivalenceClassesToBeRemoved = equivalenceClassesToBeRemoved.size();
        for (int i = 0; i < numOfEquivalenceClassesToBeRemoved; i++) {
            equivalenceClass = (EquivalenceClass)equivalenceClassesToBeRemoved.elementAt(i);
            for (int j = 0; j < numOfDependencies; j++) {
                dependency = (Dependency)p_structure.getLnkDependencies().elementAt(j);
                if (dependency.getLnkEquivalenceClasses().contains(equivalenceClass)) {
                    dependency.getLnkEquivalenceClasses().removeElement(equivalenceClass);
                }
            }
        }
    }


    public UndoableEdit deleteDependency(Dependency p_Dependency, Structure p_Structure) {
    	CMCompoundEdit ce = new CMCompoundEdit();
    	//delete the dependency references from all test cases
    	for (TestCase testCase : p_Structure.getTestCases())
    		if (testCase.getDependencies().contains(p_Dependency)){
    			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveDependencyModelEdit(testCase,p_Dependency));
    			testCase.removeDependency(p_Dependency);
    		}
    	//delete all element references
		for (Element element : p_Dependency.getElements()){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementModelEdit(p_Dependency,element));
			p_Dependency.removeElement(element);
		}
		//delete all equivalence classes references

		for (EquivalenceClass equivalenceClass : p_Dependency.getEquivalenceClasses()){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveEquivalenceClassModelEdit(p_Dependency,equivalenceClass));
			p_Dependency.removeEquivalenceClass(equivalenceClass);
		}
		//delete all the combinations
		for (Combination combination : p_Dependency.getCombinations())
		{
			ce.addEdit(CombinationManager.INSTANCE.deleteCombination(combination,p_Dependency));
		}
    	//delete the dependency from the structure
		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveDependencyModelEdit(p_Structure,p_Dependency));
		p_Structure.removeDependency(p_Dependency);

    	return ce;
    }

    public EquivalenceClass getEquivalenceClass(Dependency p_Dependency, int p_Id) {
        int equivalenceClassId = p_Id;
        int numOfEquivalenceClasses = p_Dependency.getLnkEquivalenceClasses().size();
        EquivalenceClass ec = null;
        for (int i = 0; i < numOfEquivalenceClasses; i++) {
            ec = (EquivalenceClass)p_Dependency.getLnkEquivalenceClasses().elementAt(i);
            if (ec.getId() == p_Id) {
                return ec;
            }
        }
        return null;
    }

    public EquivalenceClass getEquivalenceClass(Dependency p_Dependency, String p_Id) {
        String[] stringIds = p_Id.split(BusinessRules.COMMA);
        if (stringIds == null) { return null; }
        if (stringIds.length > 0 && stringIds.length <= 2) {
            int elementId = Integer.parseInt(stringIds[0]);
            int equivalenceClassId = Integer.parseInt(stringIds[1]);
            int numOfElements = p_Dependency.getLnkElements().size();
            Element element = null;
            EquivalenceClass equivalenceClass = null;
            for (int i = 0; i < numOfElements; i++) {
                element = (Element)p_Dependency.getLnkElements().elementAt(i);
                if (element.getId() == elementId) {
                    int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
                    for (int j = 0; j < numOfEquivalenceClasses; j++) {
                        equivalenceClass = (EquivalenceClass)element.getEquivalenceClasses().get(j);
                        if (equivalenceClass.getId() == equivalenceClassId) {
                            return equivalenceClass;
                        }
                    }
                }
            }
        }
        return null;
    }

    public long getNumberOfCombinations(Dependency p_Dependency) {
        long numOfCombinations = 1;
        int numOfDependentEquivalenceClasses = 0;
        int numOfDependentElements = p_Dependency.getLnkElements().size();
        for (int de = 0; de < numOfDependentElements; de++) {
            Element element = (Element)p_Dependency.getLnkElements().elementAt(de);
            int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
            numOfDependentEquivalenceClasses = 0;
            for (int ec = 0; ec < numOfEquivalenceClasses; ec++) {
                EquivalenceClass equivalenceClass = element.getEquivalenceClasses().get(ec);
                if (p_Dependency.getLnkEquivalenceClasses().contains(equivalenceClass)) {
                    numOfDependentEquivalenceClasses++;
                }
            }
            numOfCombinations = numOfCombinations * numOfDependentEquivalenceClasses;
        }
        return numOfCombinations;
    }



    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     */
    private CombinationManager lnkCombinationManager;

    /**
     * @supplierCardinality 1
     * @clientCardinality 1
     * @directed
     */
    private TestCaseManager lnkTestCaseManager;


    /**
     * @Author hmendez
     * @Date 09122005
     * @Description Used to update de descriptions when the values on the elements grids are changed
     */
	public String getDescriptionBasedOnExistingElements(Vector elements) {
	     StringBuffer descriptionView = new StringBuffer();
	     descriptionView.append(CMStateBean.STATE_NEGATIVE_LABEL);
	     descriptionView.append(CMStateBean.STATE_NEGATIVE_LABEL);
	     descriptionView.append(BusinessRules.SIGN_ISGREATERTHAN);
	     descriptionView.append(BusinessRules.SPACE);
	     descriptionView.append(BusinessRules.DEPENDENT_ELEMENTS_LABEL);
	     descriptionView.append(BusinessRules.COLON);
	     descriptionView.append(BusinessRules.SPACE);
	     int numElements = elements.size();
		 for( int i = 0; i < numElements; i++) {
		  	Element element = (Element) elements.elementAt(i);
		   	descriptionView.append(element.getName());
		   	if( i < (numElements-1) ) {
		  	   descriptionView.append(" | ");
	        }
		 }
	     return descriptionView.toString();
	  }


	/**
     * hmendez_end
     */
    private int indexCombinations = 0;
    //hcanedo_09112004_end
	/**
	*@autor smoreno
	 * @return
	 */
	public static Dependency getSelectedDependency() {

		return CMApplication.frame.getPanelDependencyCombiView().getM_CMCombinationViews().getSelectedDependency();
	}

	public void selectDependencyView(Dependency dependency) {
		 CMApplication.frame.getPanelDependencyCombiView().getCMGridDependencies().changeSelection(dependency);

	}


}
