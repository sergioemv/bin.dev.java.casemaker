package bi.controller.combination.generator;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.controller.StructureManager;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;

import model.BusinessRules;
import model.Combination;
import model.Dependency;
import model.Element;
import model.EquivalenceClass;
import model.edit.CMModelEditFactory;

public class PermutationGenerator extends AbstractCombinationGenerator {

	public PermutationGenerator(Dependency dependency) {
		super(dependency);
	}

	@Override
	protected UndoableEdit doGenerate() throws Exception{
		CMCompoundEdit ce = new CMCompoundEdit();
		 if( dependency.getCombinations().size() > 0)
			  if (JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("INFO_COMBINATIONS_WILL_BE_DELETED"),CMMessages.getString("LABEL_GENERATE_COMBINATIONS"),
					  JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)
				  return ce;
		 Object result = generateCombinationsByPermutation(dependency, getMaxNumberofCombinations(),ce);

			    if( result instanceof Vector) {
			    	ce.addEdit(CombinationManager.INSTANCE.deleteAllCombinations(dependency,	StructureManager.getSelectedStructure()));
			    	ce.addEdit(addPermutationCombinationsToDependency((Vector) result, dependency));
				}
			    else
			    	throw new Exception(getErrorMessage((Integer)result));
		return ce;
	}

	 public Object generateCombinationsByPermutation(Dependency p_Dependency, int p_MaxNumOfCombinations,CMCompoundEdit p_ce) {
	       Vector combinations = new Vector(0);
	       long numOfCombinations = 0;
	       int confirmation = 0;
	       int numOfDependentElements = 0;
	       if (p_Dependency != null) {
	           if (p_Dependency.getLnkElements().size() >= BusinessRules.MIN_NUMBER_OF_DEPENDENT_ELEMENTS) {
	               numOfCombinations = DependencyManager.INSTANCE.getNumberOfCombinations(p_Dependency);
	               if (numOfCombinations > 0) {
	                   if (numOfCombinations <= p_MaxNumOfCombinations) {
	                       // this.initializeCombinations(p_Dependency);
	                       Vector combinationElements = new Vector(0);
	                       numOfCombinations = 1;
	                       numOfDependentElements = p_Dependency.getLnkElements().size();
	                       int numOfDependentEquivalenceClasses = 0;
	                       Vector dependentElements = new Vector(0);
	                       Vector dependentEquivalenceClasses = new Vector(0);
	                       Vector<EquivalenceClass> dependentElement = null;

	                       for (int de = 0; de < numOfDependentElements; de++) {
	                           Element element = (Element) p_Dependency.getLnkElements().elementAt(de);
	                           int numOfEquivalenceClasses = element.getEquivalenceClasses().size();
	                           dependentElement = new Vector<EquivalenceClass>(0);
	                           for (int ec = 0; ec < numOfEquivalenceClasses; ec++) {
	                               EquivalenceClass equivalenceClass = (EquivalenceClass) element.getEquivalenceClasses().get(ec);
	                               if (p_Dependency.getLnkEquivalenceClasses().contains(equivalenceClass)) {
	                                   numOfDependentEquivalenceClasses++;
	                                   dependentElement.addElement(equivalenceClass);
	                               }
	                           }
	                           numOfCombinations = numOfCombinations * dependentElement.size();
	                           dependentElements.add(dependentElement);
	                       }

	                       long tempNumOfCombinations = numOfCombinations;
	                       long distance = 1;
	                       for (int de = 0; de < numOfDependentElements; de++) {
	                           dependentElement = (Vector) dependentElements.elementAt(de);
	                           numOfDependentEquivalenceClasses = dependentElement.size();
	                           Vector combinationEquivalenceClasses = new Vector(0);
	                           if (numOfDependentEquivalenceClasses > 0) {
	                               distance = tempNumOfCombinations / numOfDependentEquivalenceClasses;
	                           }
	                           Vector pattern = new Vector(0);
	                           for (int dec = 0; dec < numOfDependentEquivalenceClasses; dec++) {
	                               EquivalenceClass equivalenceClass = (EquivalenceClass) dependentElement.elementAt(dec);
	                               for (int x = 0; x < distance; x++) { // build
	                                   // distance
	                                   pattern.addElement(equivalenceClass);
	                               }
	                           }
	                           int countPattern = pattern.size();
	                           long numOfAddingPatterns = 0;
	                           if (countPattern > 0) {
	                               numOfAddingPatterns = numOfCombinations / countPattern;
	                           }
	                           for (int p = 0; p < numOfAddingPatterns; p++) {
	                               for (int m = 0; m < countPattern; m++) {
	                                   EquivalenceClass equivalenceClass = (EquivalenceClass) pattern.elementAt(m);
	                                   combinationEquivalenceClasses.add(equivalenceClass);
	                               }
	                           }
	                           if (numOfDependentEquivalenceClasses > 0) {
	                               tempNumOfCombinations = tempNumOfCombinations / numOfDependentEquivalenceClasses;
	                           }
	                           combinationElements.addElement(combinationEquivalenceClasses);
	                       }

	                       for (int i = 0; i < numOfCombinations; i++) {
	                           Combination combination = new Combination();
	                           combination.setOriginType(Combination.Origin.PERMUTATION);
	                           // old:
	                           // p_Dependency.getLnkCombinations().addElement(combination);
	                           combinations.addElement(combination);
	                       }

	                       int numOfCombinationElements = combinationElements.size();
	                       for (int i = 0; i < numOfCombinationElements; i++) {
	                           Vector combinationEquivalenceClasses = (Vector) combinationElements.elementAt(i);
	                           int numOfCombinationEquivalenceClasses = combinationEquivalenceClasses.size();
	                           for (int j = 0; j < numOfCombinationEquivalenceClasses; j++) {
	                               EquivalenceClass equivalenceClass = (EquivalenceClass) combinationEquivalenceClasses.elementAt(j);
	                               // Combination combination = (Combination)
	                               // p_Dependency.getLnkCombinations().elementAt(j);
	                               Combination combination = (Combination) combinations.elementAt(j);
	                               p_ce.addEdit(CombinationManager.INSTANCE.addEquivalenceClassToCombination(equivalenceClass, combination));
	                           }
	                       }

	                   } // <= MAX_NUMBER_OF_IDS
	                   else { // > MAX_NUMBER_OF_IDS
	                       return new Integer(1);
	                   }
	               } // > 0
	               else { // <= 0
	                   return new Integer(1);
	               }
	           } else {
	               return new Integer(3);
	           }
	       }
	       return combinations;
	   }
	 public UndoableEdit addPermutationCombinationsToDependency(Vector p_Combinations, Dependency p_Dependency) {
		   CMCompoundEdit ce = new CMCompoundEdit();
	       EquivalenceClass equivalenceClass = null;
	       Combination combination = null;


	       for (int i = 0; i < p_Combinations.size(); i++) {
	    	   int id = CombinationManager.INSTANCE.getNextId(p_Dependency);

	           combination = (Combination) p_Combinations.elementAt(i);
	           ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(combination, id));
	           combination.setId(id);
	           ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(p_Dependency, combination));
	           p_Dependency.addCombination(combination);

	       }

	       return ce;
	   }

}
