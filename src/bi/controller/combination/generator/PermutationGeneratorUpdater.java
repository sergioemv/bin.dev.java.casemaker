package bi.controller.combination.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.undo.UndoableEdit;

import model.Combination;
import model.Dependency;
import model.Element;
import model.Structure;
import model.edit.CMModelEditFactory;
import bi.controller.CombinationManager;
import bi.controller.StructureManager;
import bi.view.undomanagementviews.CMCompoundEdit;

public class PermutationGeneratorUpdater extends PermutationGenerator {

	public PermutationGeneratorUpdater(Dependency dependency) {
		super(dependency);
	}

	@Override
	protected UndoableEdit doGenerate() throws Exception {
		CMCompoundEdit ce = new CMCompoundEdit();
		Object result = updateCombinationsByPermutation(dependency,getMaxNumberofCombinations(),dependency.getElements(),ce);
        if( result != null) {
         if( result instanceof Integer) {
        	 throw new Exception(getErrorMessage((Integer)result));
          }
        }
		return ce;
	}
	/**
	    * It is a overloaded version of <code>updateCombinationsByPermutation</code>. Watch out it modified
	    * the <code>Dependency</code> passed as parameter, i. e., the result is put in the dependency passed
	    * as parameter. To difference of the another version, it take care of the merged combinations, well,
	    * at least it tries it :).
	    *
	    * @see CombinationManager#updateCombinationsByPermutation(Dependency, int, Structure)
	    * @param selectedDependency
	    * @param maxNumOfCombinations
	    * @param selectedStructure
	    * @return does not matter
	    */
	   public Object updateCombinationsByPermutation(Dependency dependency, int maxNumOfCombinations,List<Element> elements, CMCompoundEdit p_ce) {
//	       if ((dependency.getTypeofCombinations() == Combination.Origin.ALLPAIRS)) {
//	           return CombinationManager.INSTANCE.updateCombinationsByAllPairs(dependency, maxNumOfCombinations, structure, oldElements,p_ce);
//	       }
	       Vector newCombinations = null;
	       Object result = generateCombinationsByPermutation(dependency, maxNumOfCombinations,p_ce);
	       if (result == null) {
	           return null;
	       }
	       if (result instanceof Vector) {
	           newCombinations = (Vector) result;
	       } else if (result instanceof Integer) {
	           return p_ce;

	       }

	       List matchPattern = CombinationManager.INSTANCE.matchPattern(elements, dependency.getLnkElements());
	       Structure structure = StructureManager.getSelectedStructure();
	       List currentCombinations = getUnmergedCombinations(dependency.getLnkCombinations());

	       p_ce.addEdit(CombinationManager.INSTANCE.matchEffects(newCombinations, currentCombinations, matchPattern.size()));
	       p_ce.addEdit(CombinationManager.INSTANCE.matchIDs(dependency, newCombinations, currentCombinations, matchPattern.size(), structure));

	       p_ce.addEdit(reAssignOrderedIDsToCombinations(newCombinations));
	       List list = matchCombinations(dependency.getLnkCombinations(), newCombinations, matchPattern, p_ce);

	       if (dependency.getCombinations().size()>0)
	    	   for( Iterator iterator = dependency.getCombinations().iterator(); iterator.hasNext(); ) {
	               Combination combination = (Combination) iterator.next();
	               p_ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(dependency, combination));
	               dependency.removeCombination(combination);
	           }

	       for( Iterator iterator = list.iterator(); iterator.hasNext(); ) {
	           Combination combination = (Combination) iterator.next();
	           p_ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(dependency, combination));
	           dependency.addCombination(combination);

	         }
	       return new Vector(list);
	   }


	    /**
	     * It returns a list of combinations without merges.
	     * @param combinations, a list of combinations.
		 * @return a new list with all merge combinations undone.
		 */
		private List getUnmergedCombinations(List<Combination> combinations) {
			List undo = new ArrayList();

			for (Iterator iterCombinations = combinations.iterator(); iterCombinations.hasNext();) {
				Combination combination = (Combination) iterCombinations.next();
				undo.add(combination);
				List childs = combination.getCombinations();
				undo.addAll(getUnmergedCombinations(childs));
			}

			return undo;
		}
		 /**
		    * This method looks for the merges elements in the <code>before</code> list, and tries to do the same merge in
		    * the <code>after</code> list. It returns the result in a new list.
		    * @param before, the before list of combinations.
		    * @param after, the after list of combinations.
		    * @param elements, a list of elements, these are the common elements in both lists of combinations.
		    * @return a list of combinations, which tries to map the closer possible the <code>before</code> list.
		    */
		   public List<Combination> matchCombinations(List before, List after, List elements, CMCompoundEdit ce) {
		       List<Combination> withMatch = new ArrayList<Combination>();

		       for (; !after.isEmpty(); ){
		           Combination newCombination = (Combination) after.get(0);

		           withMatch.add(newCombination);
		           after.remove(newCombination);
		           for (Iterator iterBefore = before.iterator(); iterBefore.hasNext();) {
		               Combination oldCombination = (Combination) iterBefore.next();
		               if (CombinationManager.INSTANCE.matchCombinations(newCombination, oldCombination, elements.size())) {
		                   ce.addEdit(CombinationManager.INSTANCE.matchCombinations(newCombination, oldCombination, after, withMatch, elements));
		               }
		           }
		       }

		       return withMatch;
		   }

}
