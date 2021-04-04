package bi.controller.combination.generator;

import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.view.undomanagementviews.CMCompoundEdit;

import model.Combination;
import model.Dependency;
import model.EquivalenceClass;
import model.Structure;
import model.edit.CMModelEditFactory;

public class AllPairsGeneratorUpdater extends AllPairsGenerator {

	public AllPairsGeneratorUpdater(Dependency dependency) {
		super(dependency);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected UndoableEdit doGenerate() throws Exception {
		CMCompoundEdit ce = new CMCompoundEdit();
	    Object result = updateCombinationsByAllPairs(dependency,getMaxNumberofCombinations(), StructureManager.getSelectedStructure(),ce);

	  if( result instanceof Integer)
		   	throw new Exception(getErrorMessage((Integer)result));

		return ce;
	}
	   public Object updateCombinationsByAllPairs(Dependency p_Dependency, int p_MaxNumOfCombinations, Structure p_Structure,CMCompoundEdit p_ce) {
	       Vector newCombinations = null;

	       Object result = generateCombinationsByAllPairs(p_Dependency, p_MaxNumOfCombinations,p_ce);
	       if (result == null) {
	           return null;
	       }
	       if (result instanceof Vector) {
	           newCombinations = (Vector) result;
	       } else if (result instanceof Integer) {
	           return p_ce;//(Integer) result;
	       }

	       Vector currentCombinations = p_Dependency.getLnkCombinations();
	       int numOfCurrentCombinations = currentCombinations.size();
	       int numOfNewCombinations = newCombinations.size();
	       Combination newCombination = null;
	       Combination currentCombination = null;
	       Combination combination = null;
	       Vector combinationsToBeDeleted = new Vector(0);

	       for (int i = 0; i < numOfCurrentCombinations; i++) {
	           currentCombination = (Combination) currentCombinations.elementAt(i);
	           if (!isCombinationInCombinations(currentCombination, newCombinations)) {
	               if (currentCombination.getOriginType() != Combination.Origin.MANUAL) {
	            	   combinationsToBeDeleted.addElement(currentCombination);
	               }
	           }
	       }

	       for (int i = 0; i < combinationsToBeDeleted.size(); i++) {
	           combination = (Combination) combinationsToBeDeleted.elementAt(i);
	           p_ce.addEdit(CombinationManager.INSTANCE.deleteCombinationFromDependency(combination, p_Dependency, p_Structure));
	       }

	       Combination patternOfNeighborCombination = null;
	       for (int i = 0; i < numOfNewCombinations; i++) {
	           newCombination = (Combination) newCombinations.elementAt(i);
	           if (!isCombinationInCombinations(newCombination, currentCombinations)) {
	               if (i > 0) {
	                   patternOfNeighborCombination = (Combination) newCombinations.elementAt(i - 1);
	               }
	               p_ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(p_Dependency, newCombination));
	               p_Dependency.addCombination(newCombination);//getLnkCombinations().insertElementAt(p_NewCombination, 0);
	           }
	       }
	       p_ce.addEdit(reAssignOrderedIDsToCombinations(p_Dependency.getLnkCombinations()));
	       //setCombinationsDescriptionsForDependency(p_Dependency);
	       return currentCombinations;
	   }

	   public boolean isCombinationInCombinations(Combination p_CurrentCombination, Vector p_NewCombinations) {
	       Combination combination = null;
	       int numOfCombinations = p_NewCombinations.size();
	       for (int i = 0; i < numOfCombinations; i++) {
	           combination = (Combination) p_NewCombinations.elementAt(i);
	           if (combination != p_CurrentCombination) {
	               if (isCombinationInCombination(combination, p_CurrentCombination)) {
	                   return true;
	               }
	           }
	       }
	       return false;
	   }


	   public boolean isCombinationInCombination(Combination p_NewCombination, Combination p_OldCombination) {
	       int numOfEquivalenceClassesInNewCombination = p_NewCombination.getEquivalenceClasses().size();
	       int numOfEquivalenceClassesInOldCombination = p_OldCombination.getEquivalenceClasses().size();
	       int count = 0;
	       EquivalenceClass ecOfNew = null;
	       EquivalenceClass ecOfOld = null;
	       boolean found = false;

	       if (numOfEquivalenceClassesInNewCombination == 0 && numOfEquivalenceClassesInOldCombination != 0) {
	           return false;
	       }

	       for (int i = 0; i < numOfEquivalenceClassesInNewCombination; i++) {
	           ecOfNew = (EquivalenceClass) p_NewCombination.getEquivalenceClasses().get(i);
	           if (!p_OldCombination.contains(ecOfNew)) {
	               return false; // Combinations is not in Combination
	           }
	       }
	       return true; // Combinations is in Combination
	   }


	   // fcastro_30082004_end


	   private Combination findTheBestNeighborCombination(Combination p_PatternCombination, Dependency p_Dependency) {
	       int numOfCurrentCombinations = p_Dependency.getLnkCombinations().size();
	       Combination currentCombination = null;
	       for (int i = 0; i < numOfCurrentCombinations; i++) {
	           currentCombination = (Combination) p_Dependency.getLnkCombinations().elementAt(i);
	           if (isCombinationInCombination(p_PatternCombination, currentCombination)) {
	               return currentCombination;
	           }
	       }
	       return null;
	   }
}
