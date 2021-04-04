
package bi.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import model.ApplicationSetting;
import model.BusinessRules;
import model.Combination;
import model.Dependency;
import model.Effect;
import model.Element;
import model.EquivalenceClass;
import model.State;
import model.StdCombination;
import model.Structure;
import model.TestCase;
import model.TestObject;
import model.edit.CMModelEditFactory;
import model.util.CMModelEventHandler;
import model.util.CMOriginTypeBean;
import model.util.CMStateBean;
import model.util.IdSet;
import model.util.CMOriginTypeBean.Origin;

import org.apache.log4j.Logger;

import bi.controller.combination.generator.AllPairsGenerator;
import bi.controller.combination.generator.AllPairsGeneratorUpdater;
import bi.controller.combination.generator.PermutationGeneratorUpdater;
import bi.controller.editcontrol.CMRiskLevelEditController;
import bi.controller.editcontrol.CMStateEditController;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.utils.CMRiskLevelView;
import bi.view.utils.CMStateView;



public class CombinationManager {

   public static final CombinationManager INSTANCE = new CombinationManager();

public CombinationManager() {

   }

   

   /**
    * Given two list of elements, this method returns a list with the common elements in both list
    * passed as parameters.
    * @param oldElements
    * @param lnkElements
    * @return
    */
   public List matchPattern(List<Element> oldElements, List<Element> newElements) {
       List<Element> matchPattern = new ArrayList<Element>();
       for (Iterator iterator = newElements.iterator(); iterator.hasNext();) {
           Element element = (Element) iterator.next();
           if (oldElements.contains(element)) {
               matchPattern.add(element);
           }
       }

       return matchPattern;
   }
   /**
    * It is a overloaded version of <code>updateCombinationsByAllPairs</code>. Watch out, it modified
    * the <code>Dependency</code> passed as parameter, i. e., the result is put in the dependency passed
    * as parameter. To difference of the another version, it take care of the merged combinations, well,
    * at least it tries it :).
    *
    * @see CombinationManager#updateCombinationsByAllPairs(Dependency, int, Structure)
    * @param selectedDependency
    * @param maxNumOfCombinations
    * @param selectedStructure
    * @param oldElements
    * @return does not matter
    */
   public Object updateCombinationsByAllPairs(Dependency dependency, int maxNumOfCombinations, Structure structure, List oldElements,CMCompoundEdit p_ce) {
       Vector newCombinations = null;

       AllPairsGenerator generator = new AllPairsGenerator(dependency);
       Object result = generator.generateCombinationsByAllPairs(dependency, maxNumOfCombinations,p_ce);
       if (result == null) {
           return null;
       }
       if (result instanceof Vector) {
           newCombinations = (Vector) result;
       } else if (result instanceof Integer) {
           return p_ce;//(Integer) result;
       }

       List matchPattern = matchPattern(oldElements, dependency.getLnkElements());

       List currentCombinations = dependency.getLnkCombinations();
       List currentManualCombinations = findCombinationsOfOriginType(Combination.Origin.MANUAL, currentCombinations);

       p_ce.addEdit(matchEffects(newCombinations, currentCombinations, matchPattern.size()));
       p_ce.addEdit(matchIDs(dependency, newCombinations, currentCombinations, matchPattern.size(), structure));

       newCombinations.addAll(currentManualCombinations);

       //List list = matchCombinations(dependency.getLnkCombinations(), newCombinations, matchPattern);
       p_ce.addEdit(reAssignOrderedIDsToCombinations(newCombinations));
       Vector<Combination> combinations = new Vector<Combination>(newCombinations);

       if (dependency.getCombinations().size()>0){
    	   for(Iterator i = dependency.getCombinations().iterator(); i.hasNext();) {
               Combination combination = (Combination)i.next();
               p_ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(dependency, combination));
               dependency.removeCombination(combination);
           }
       }
       for(Iterator j = combinations.iterator(); j.hasNext();) {
           Combination combination = (Combination)j.next();
           p_ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(dependency, combination));
           dependency.addCombination(combination);
       }
       return new Vector(newCombinations);
   }

   public List getDependencyCombinationsRecursiv(Dependency p_Dependency) {
     return null;
   }

   public UndoableEdit reAssignOrderedIDsToCombinations(List p_Combinations){
	   CMCompoundEdit ce = new CMCompoundEdit();
       Combination combination = null;
       int i = 0;
       for( Iterator iterator = p_Combinations.iterator(); iterator.hasNext(); ) {
         combination = (Combination) iterator.next();
         i++;
         ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(combination, i));
		 combination.setId(i);
        }
       return ce;
   }

   public List<Combination> findCombinationsOfOriginType(Origin p_OriginType, List<Combination> p_Combinations) {
     List<Combination> combinations = new ArrayList<Combination>();
     Combination combination = null;
     for( Iterator<Combination> iterator = p_Combinations.iterator(); iterator.hasNext(); ) {
       combination =  iterator.next();
       if( combination.getOriginType().equals(p_OriginType)) {
         combinations.add(combination);
       }
     }
     return combinations;
   }

    /**
    * This method assigns, if it is possible, all the <code>Effects</code> of the oldList to newList of
    * combinations.
    *
    * @param newList, a list of combinations
    * @param oldList, a list of combinations
    * @param oldElements, a list with the old elements
    */
   public UndoableEdit matchEffects(List newList, List oldList, int matchPattern) {
	   CMCompoundEdit ce = new CMCompoundEdit();
       List matched = new ArrayList();
       for (Iterator iterNewCombinations = newList.iterator(); iterNewCombinations.hasNext();) {
           Combination newCombination = (Combination) iterNewCombinations.next();
           matched.clear();
           for (Iterator iterOldCombinations = oldList.iterator(); iterOldCombinations.hasNext();) {
               Combination oldCombination = (Combination) iterOldCombinations.next();
               if (matchCombinations(newCombination, oldCombination, matchPattern)) {
                   matched.add(oldCombination);
               }
               /*
               else if( (newCombination.getEquivalenceClassesRecursiv().size() == 0) && (oldCombination.getEquivalenceClassesRecursiv().size() == 0)) {
                  matched.add(oldCombination);
               }
               */
           }
           Vector<Effect> effects = new Vector<Effect>();
           effects = getEffectsOfCombinations(matched);
           for( Iterator i = effects.iterator(); i.hasNext();) {
				  Effect effect = (Effect) i.next();
				  ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(newCombination, effect));
		          newCombination.addEffect(effect);
           }

           int riskLevel = getRiskOfCombinations(matched);
           ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(newCombination, riskLevel));
           newCombination.setRiskLevel(riskLevel);
           int state = getStateOfCombinations(matched);
           ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(newCombination, Arrays.asList(State.values()).get(state)));
           newCombination.setState(state);
           /*if( matched.size() > 0 ) {
        	 String description = getDescriptionOfCombinations(matched);
        	 ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionModelEdit(newCombination, description));
             newCombination.setDescription(description);
           }*///ccastedo 01.11.06
       }
       return ce;
   }

   /**
    * This method returns a int with the risk level, guessed, of the combinations.
    *
    * @param combinations.
    * @return a int, representing a risk level.
    */
   public int getRiskOfCombinations(List combinations) {
       int risk = 0;
       for (Iterator iterator = combinations.iterator(); iterator.hasNext();) {
           Combination combination = (Combination) iterator.next();
           return combination.getRiskLevel();
       }

       return risk;
   }

   public String getDescriptionOfCombinations(List combinations) {
     for( Iterator iterator = combinations.iterator(); iterator.hasNext();) {
       Combination combination = (Combination) iterator.next();
       return combination.getDescription();
     }
     return "";
   }

   /**
    * This method returns a int with the stated, guessed, of the combinations.
    *
    * @param combinations.
    * @return a int, representing a state.
    */
   public int getStateOfCombinations(List combinations) {
       int state = CMStateBean.STATE_POSITIVE;
       for (Iterator iterator = combinations.iterator(); iterator.hasNext();) {
           Combination combination = (Combination) iterator.next();
           return combination.getState();
       }

       return state;
   }

   /**
    * It returns true if the equivalence clases of the first combination matched with the the equivalence
    * classes of the second combination, are equal to <code>matchPattern</code>, otherwise false.
    *
    * @param new combination
    * @param old combination
    * @param oldElements
    * @return true if the combinations are matching
    */
   public boolean matchCombinations(Combination neew, Combination old, int matchPattern) {
       List newEquivalences = neew.getEquivalenceClasses();
       List oldEquivalences = old.getEquivalenceClasses();
       int matchs = 0;

       for (Iterator iterNewEquivalences = newEquivalences.iterator(); iterNewEquivalences.hasNext();) {
           EquivalenceClass newEquivalenceClass = (EquivalenceClass) iterNewEquivalences.next();
           if (oldEquivalences.contains(newEquivalenceClass)) {
               matchs++;
           }
       }

       if (matchs == matchPattern) {
           return true;
       } else {
           return false;
       }
   }


   /**
     * Given two combinations, this method tries to do the same merge that there are in the <code>toMatch</code>
     * combination. For that, this method looks in the list of combinations passed as parameter. If this method found
     * candidates to merge in <code>target</code> combination, this do it, and <b>removes</b> from the list of
     * combinations.
	 * @param target, the combination to look for merges.
	 * @param toMatch, the combination to match.
	 * @param combinations, the list of combinations to look for merges.
	 * @param withMatch, the list of combinations to look for merges.
	 * @param elements, a list of elemets.
	 */
   public UndoableEdit matchCombinations(Combination target, Combination toMatch, List combinations, List withMatch, List<Element> elements) {
       List childs = toMatch.getCombinations();
       CMCompoundEdit ce = new CMCompoundEdit();
       for (Iterator iterChilds = childs.iterator(); iterChilds.hasNext();) {
           Combination child = (Combination) iterChilds.next();
           EquivalenceClass equivalence = difference(toMatch, child);

           if (equivalence!=null && elements.contains(equivalence.getLnkElement())) {
               List candidates = getCandidatesToMerge(combinations, equivalence);
               for (Iterator iterCandidates = candidates.iterator(); iterCandidates.hasNext();) {
                   Combination candidate = (Combination) iterCandidates.next();
                   if (canMergeCombinations(target, candidate)) {
                       ce.addEdit(addChildCombination(target,candidate));
                       combinations.remove(candidate);

                       if (child.getCombinations().size() > 0) {
                           matchCombinations(candidate, child, combinations, withMatch, elements);
                       }
                       break;
                   }
               }

               candidates = getCandidatesToMerge(withMatch, equivalence);
               for (Iterator iterCandidates = candidates.iterator(); iterCandidates.hasNext();) {
                   Combination candidate = (Combination) iterCandidates.next();
                   if (canMergeCombinations(target, candidate)) {
                       ce.addEdit(addChildCombination(target,candidate));
                       withMatch.remove(candidate);

                       if (child.getCombinations().size() > 0) {
                           matchCombinations(candidate, child, combinations, withMatch, elements);
                       }
                       break;
                   }
               }
               
           }
       }
       return ce;
   }



   /**
    * Given a list of combinations, this method returns a list with the combinations that have the
    * <code>equivalence</code> passed as parameter.
    * @param combinations, a list of combinatoins.
    * @param equivalence, the equivalence class to search.
    * @return a list of combinations, which have the equivalence passed as parameter.
    */
	private List getCandidatesToMerge(List combinations, EquivalenceClass equivalence) {
	    List candidates = new ArrayList();
	    for (Iterator iterator = combinations.iterator(); iterator.hasNext();) {
            Combination combination = (Combination) iterator.next();
            List equivalences = combination.getEquivalenceClasses();
            if (equivalences.contains(equivalence)) {
                candidates.add(combination);
            }
        }

	    return candidates;
	}

	/**
    * Given two combinations, two merged combinations of course, this method returns the
    * equivalence class responsible of merging. This method hopes that the <code>second</code> combination is
    * merged into the <code>first</code> combination.
    * @param first, a combination.
    * @param second, a combination merged in the above.
    * @return the equivalence class responsible of the merge.
    */
	private EquivalenceClass difference(Combination first, Combination second) {
	    List firstList = first.getEquivalenceClasses();
	    List secondList = second.getEquivalenceClasses();

	    for (Iterator iterator = secondList.iterator(); iterator.hasNext();) {
            EquivalenceClass equivalence = (EquivalenceClass) iterator.next();
            if (!firstList.contains(equivalence)) {
                return equivalence;
            }
        }

	    return null;
	}


   /**
    * By definition a combination <b>B</b> can be merged with another <b>A</b>, if only if, the equivalence
    * class of <b>B</b> is a <b>subset</b> of the equivalence class of combination <b>A</b>, except of the
    * equivalence class responsible of the merge.
    *
    * This method does not take care if they have child combinations.
    * @param targetCombination, combination target.
    * @param combinationToMerge, the combination to merge.
    * @return true if the second combination can be merge <b>into</b> the first combination passed as parameters.
    */
   private boolean canMergeCombinations(Combination targetCombination, Combination combinationToMerge) {
       List target = targetCombination.getEquivalenceClasses();
       List toMerge = combinationToMerge.getEquivalenceClasses();
       int difference = 0;

       for (Iterator iterator = toMerge.iterator(); iterator.hasNext();) {
           EquivalenceClass equivalence = (EquivalenceClass) iterator.next();
           if (!target.contains(equivalence)) {
               difference++;
           }
           if (difference > 1) {
               return false;
           }
       }

       return true;
   }



   /**
    * This method takes a list of combinations and assigns to these IDs and a dependency.
    * It tries to keep the IDs belong to an old list of combinations, <code>before</code> in the
    * method signature.
    * Watch out, for a work around, in this method is added the work of delete the test cases which use
    * combinations to be deleted.
    *
    * @param dependency, the <code>Dependency</code> object to get the IDs.
    * @param after, the new list of combinations to assign IDs.
    * @param before, the old list of combinations to get the old IDs.
    * @param matchPattern, used in <code>matchCombinations</code> method.
    * @param structure, a structure to delete the test cases which use combinations to disappear.
    */
   public UndoableEdit matchIDs(Dependency dependency, List after, List before, int matchPattern, Structure structure) {
       //grueda20032005_begin
	   CMCompoundEdit ce = new CMCompoundEdit();
       List oldCombinations = new ArrayList(before);
       //grueda20032005_end
       //List oldCombinations = before;
       for (Iterator iterNewCombinations = after.iterator(); iterNewCombinations.hasNext();) {
           Combination newCombination = (Combination) iterNewCombinations.next();
           Combination matchCombination = null;
           Combination oldCombination = null;
           for (Iterator iterOldCombinations = oldCombinations.iterator(); iterOldCombinations.hasNext();) {
               oldCombination = (Combination) iterOldCombinations.next();
               if (matchCombinations(newCombination, oldCombination, matchPattern)) {
                   matchCombination = oldCombination;
                   break;
               }
           }
           /*ce.addEdit(CMModelEditFactory.INSTANCE.createChangeCombinationDependencyModelEdit(newCombination, dependency));
           newCombination.setDependency(dependency);*///prueba


           if (matchCombination != null) {
        	   ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(newCombination, matchCombination.getId()));
               newCombination.setId(matchCombination.getId());

               for (Iterator iterator = structure.getTestCases(matchCombination).iterator(); iterator.hasNext();) {
                   TestCase testcase = (TestCase) iterator.next();

                   ce.addEdit(TestCaseManager.INSTANCE.removeCombinationInTestCases(matchCombination, structure));
                   ce.addEdit(TestCaseManager.INSTANCE.addCombinationToTestCase(newCombination, testcase));
               }
               oldCombinations.remove(matchCombination);
           } else {

               //grueda19032005_begin
        	  /* int index = getNextId(dependency);
        	   ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(newCombination, index));
               newCombination.setId(index);*/
               //grueda19032005_end
               ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(dependency, newCombination));
               dependency.addCombination(newCombination);
           }


       }

       //grueda19032005


       return ce;
   }



   /**
    * This method returns a list with the effects of the combinations.
    *
    * @param combinations
    * @return a list of <code>Effects</code>
    */
   public Vector getEffectsOfCombinations(List combinations) {
       Set set = new HashSet();
       for (Iterator iterator = combinations.iterator(); iterator.hasNext();) {
           List effects = ((Combination) iterator.next()).getEffects();
           for (Iterator iterEffects = effects.iterator(); iterEffects.hasNext();) {
               Effect effect = (Effect) iterEffects.next();
               set.add(effect);
           }
       }

       return new Vector(set);
   }

                           Combination combination = this.createCombinationWithoutId();

   public Combination createCombination(Dependency p_dependency,CMCompoundEdit p_ce) {

       Combination c = new Combination();
       int index = this.getNextId(p_dependency);
       p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(c, index));
       c.setId(index);
      /* p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeCombinationDependencyModelEdit(c, p_dependency));
       c.setDependency(p_dependency);*///prueba
       return c;
   }

   public Combination createCombinationWithoutId() {
       Combination t = new Combination();
       return t;
   }

   public StdCombination createStdCombination(Structure p_Structure) {
       StdCombination c = new StdCombination();
       c.setId(this.getNextId(p_Structure));
       c.setM_Structure(p_Structure);
       return c;

   }

   public int getNextId(Dependency p_dependency) {
	   IdSet idSet = new IdSet();
	   for (Combination combination : p_dependency.getCombinations())
		   idSet.registerIds(getAllIdsOfCombinationRecursiv(combination));
       return idSet.nextValidId();
   }

   public int getNextId(Structure p_Structure) {
	   IdSet ids = new IdSet();
	   for (Combination std : p_Structure.getCombinations()){
			ids.registerId(std.getId());
	   }
       return ids.nextValidId();

   }

   public UndoableEdit deleteAllCombinations(Dependency dependency, Structure p_Structure) {
	   CMCompoundEdit ce = new CMCompoundEdit();
       int numOfCombinations = dependency.getLnkCombinations().size();
       Combination combination = null;
       for (int i = 0; i < numOfCombinations; i++) {
           combination = (Combination) dependency.getLnkCombinations().elementAt(i);
           deleteCombinationRecursiv(combination);

           ce.addEdit(TestCaseManager.INSTANCE.removeCombinationInTestCases(combination, p_Structure));
       }
       if (dependency.getCombinations().size()>0){
    	   for(Iterator i = dependency.getCombinations().iterator(); i.hasNext();) {
               Combination combinationtobedeleted = (Combination)i.next();
               ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(dependency, combinationtobedeleted));
               dependency.removeCombination(combinationtobedeleted);
           }
       }
        return ce;
   }

   public void deleteCombinationRecursiv(Combination combination) {

	   if(!(combination.getId() >= BusinessRules.MAX_NUMBER_OF_IDS)&& (combination.getId()>= 0)){
		   if (combination.getCombinations().size() > 0) {
	           //ccastedo 30.10.06 ids.setElementAt(new Boolean(false), combination.getId());
	           int numOfChildrenCombinations = combination.getCombinations().size();
	           Combination childCombination = null;
	           for (int i = 0; i < numOfChildrenCombinations; i++) {
	               childCombination = (Combination) combination.getCombinations().get(i);
	               deleteCombinationRecursiv(childCombination);
	           }
	       } 
	   }

   }

   public void deleteEffectOfCombinations(Effect p_Effect, Structure p_Structure) {
       int numOfDependencies = p_Structure.getLnkDependencies().size();
       int numOfCombinations = 0;
       Dependency dependency = null;
       Combination combination = null;
       for (int i = 0; i < numOfDependencies; i++) {
           dependency = (Dependency) p_Structure.getLnkDependencies().elementAt(i);
           numOfCombinations = dependency.getLnkCombinations().size();
           for (int j = 0; j < numOfCombinations; j++) {
               combination = (Combination) dependency.getLnkCombinations().elementAt(j);
               if (combination.getEffects().contains(p_Effect)) {
                   combination.removeEffect(p_Effect);
               }
           }
       }
   }

   public Combination getPositiveCombinationAtTheOrderPosition(int p_position, Dependency p_Dependency) {
       int numOfCombinations = p_Dependency.getLnkCombinations().size();
       Combination combination = null;
       int counter = -1;
       for (int i = 0; i < numOfCombinations; i++) {
           combination = (Combination) p_Dependency.getLnkCombinations().elementAt(i);
           if (combination.getState() == CMStateBean.STATE_POSITIVE) {
               counter++;
               if (counter == p_position) {
                   return combination;
               }
           }
       }
       return null;
   }

 

   public Vector getEquivalenceClassCombinations(EquivalenceClass p_EquivalenceClass, TestCase p_TestCase, Structure p_Structure) {
       int numOfDependencies = p_Structure.getLnkDependencies().size();
       Dependency dependency = null;
       Combination combination = null;
       Vector equivalenceClassCombinations = new Vector(0);

       for (int i = 0; i < numOfDependencies; i++) {
           dependency = (Dependency) p_Structure.getLnkDependencies().elementAt(i);
           int numOfCombinations = dependency.getLnkCombinations().size();
           for (int j = 0; j < numOfCombinations; j++) {
               combination = (Combination) dependency.getLnkCombinations().elementAt(j);
               if (!(combination instanceof StdCombination)) {
                   if (combination.contains(p_EquivalenceClass) && !p_TestCase.getLnkCombinations().contains(combination)) {
                       if (TestCaseManager.INSTANCE.canCombinationBeAssignedToTestCase(p_Structure, p_TestCase, combination)) { // Can
                           // be
                           // Added?
                           if (TestCaseManager.INSTANCE.canCombinationBeAssignedToTestCaseConsideringTheState(p_TestCase, combination)) {
                               if (!equivalenceClassCombinations.contains(combination)) {
                                   equivalenceClassCombinations.addElement(combination);
                               }
                           }
                       }
                   }
               }
           }
       }
       return equivalenceClassCombinations;
   }

   public Vector getCombinationsWithEquivalenceClassInTestCase(EquivalenceClass p_EquivalenceClass, TestCase p_TestCase, Structure p_Structure) {
       int numOfCombinations = p_TestCase.getCombinations().size();
       Vector combinationsInTestCaseInEquivalenceClass = new Vector(0);
       Combination combination = null;
       for (int i = 0; i < numOfCombinations; i++) {
           combination = (Combination) p_TestCase.getCombinations().get(i);
           if (!(combination instanceof StdCombination)) {
               if (combination.contains(p_EquivalenceClass)) {
                   if (!combinationsInTestCaseInEquivalenceClass.contains(combination)) {
                       combinationsInTestCaseInEquivalenceClass.addElement(combination);
                   }
               }
           }
       }
       return combinationsInTestCaseInEquivalenceClass;
   }

   public boolean isThereACombinationInTestCaseWithTheEquivalenceClass(EquivalenceClass p_EquivalenceClass, TestCase p_TestCase) {
       int numOfCombinations = p_TestCase.getLnkCombinations().size();
       Combination combination = null;
       for (int i = 0; i < numOfCombinations; i++) {
           combination = (Combination) p_TestCase.getLnkCombinations().elementAt(i);
           if (!(combination instanceof StdCombination)) { // New!!!!
               if (combination.contains(p_EquivalenceClass)) {
                   return true;
               }
           }
       }
       return false;
   }

   public boolean isThereAnEquivalenceClassInTheCombinationThatBelongsToTheSameElement(Combination p_Combination, EquivalenceClass p_EquivalenceClass) {
       int numOfEquivalenceClasses = p_Combination.getEquivalenceClasses().size();
       EquivalenceClass equivalenceClass;
       for (int i = 0; i < numOfEquivalenceClasses; i++) {
           equivalenceClass = (EquivalenceClass) p_Combination.getEquivalenceClasses().get(i);
           if (equivalenceClass.getLnkElement() == p_EquivalenceClass.getLnkElement()) {
               return true;
           }
       }
       return false;
   }

   /**
 * @param p_combinations
 * @return
 * @deprecated
 * @since 2.5.1
 * use <Parent> getCombinationsbyState.size()
 */
public int getNumberOfPositiveCombinations(Vector p_combinations) {
       int size = p_combinations.size();
       int counter = 0;
       Combination combination = null;
       for (int i = 0; i < size; i++) {
           combination = (Combination) p_combinations.elementAt(i);
           if (combination.getState() == CMStateBean.STATE_POSITIVE) {
               counter++;
           }
       }
       return counter;
   }






   public UndoableEdit addEquivalenceClassToCombination(EquivalenceClass p_EquivalenceClass, Combination p_Combination) {
       CMCompoundEdit ce = new CMCompoundEdit();
	   if (p_EquivalenceClass == null || p_Combination == null) {
           return ce;
       }
        ce.addEdit(CMModelEditFactory.INSTANCE.createAddEquivalenceClassModelEdit(p_Combination, p_EquivalenceClass));
        p_Combination.addEquivalenceClass(p_EquivalenceClass);
       //update the risk level
		CMRiskLevelEditController riskLevelEditController = new CMRiskLevelEditController();
		riskLevelEditController.setRiskLevelView(new CMRiskLevelView());
		riskLevelEditController.setRiskLevelBean(p_Combination);
		ce.addEdit(riskLevelEditController.applyChanges());
        return ce;
   }

   public void deleteEquivalenceFromCombination(EquivalenceClass p_EquivalenceClass, Combination p_Combination) {
       if (p_EquivalenceClass == null || p_Combination == null) {
           return;
       }
       if (p_Combination.getEquivalenceClasses().contains(p_EquivalenceClass)) {
           p_Combination.removeEquivalenceClass(p_EquivalenceClass);
       }
   }

   public UndoableEdit addAllPairsCombinationsToDependency(Vector p_AllPairsCombinations, Dependency p_Dependency) {
       CMCompoundEdit ce = new CMCompoundEdit();
	   Combination combination = null;
       String equivalenceClassIdString = null;
       EquivalenceClass equivalenceClass = null;
       CMModelEventHandler.setNotifyEnabled(false);
       for (int i = 0; i < p_AllPairsCombinations.size(); i++) {
    	   if (i==1){
    		   ce.addUndoDelegateEdit(CMModelEventHandler.startUpdatingDelegate());
    		   ce.addRedoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
    	   }
    	   int id = getNextId(p_Dependency);

           combination = (Combination) p_AllPairsCombinations.elementAt(i);
           ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIdModelEdit(combination, id));
           combination.setId(id);
           ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(p_Dependency, combination));
           p_Dependency.addCombination(combination);
           if (i== p_AllPairsCombinations.size()-2){
        	   CMModelEventHandler.setNotifyEnabled(true);
        	   ce.addRedoDelegateEdit(CMModelEventHandler.startUpdatingDelegate());
           }
       }
       ce.addUndoDelegateEdit(CMModelEventHandler.stopUpdatingDelegate());
       return ce;
   }









   public boolean isCombinationInDependency(Combination p_Combination, Dependency p_Dependency) {
       Combination combination = null;
       int numOfCombinations = p_Dependency.getLnkCombinations().size();
       for (int i = 0; i < numOfCombinations; i++) {
           combination = (Combination) p_Dependency.getLnkCombinations().elementAt(i);
           if (combination != p_Combination) {
               if (areEqual(p_Combination, combination)) {// fcastro_02092004
                   return true;
               }
           }
       }
       return false;
   }

   public boolean areEqual(Combination p_NewCombination, Combination p_OldCombination) {
       int numOfEquivalenceClassesInNewCombination = p_NewCombination.getEquivalenceClasses().size();
       int numOfEquivalenceClassesInOldCombination = p_OldCombination.getEquivalenceClasses().size();
       int count = 0;
       EquivalenceClass ecOfNew = null;
       EquivalenceClass ecOfOld = null;
       boolean found = false;

       if (numOfEquivalenceClassesInNewCombination == numOfEquivalenceClassesInOldCombination) {// fcastro_02092004
           for (int i = 0; i < numOfEquivalenceClassesInNewCombination; i++) {
               ecOfNew = (EquivalenceClass) p_NewCombination.getEquivalenceClasses().get(i);
               if (!p_OldCombination.getEquivalenceClasses().contains(ecOfNew)) {
                   return false; // Combinations are not equal!
               }
           }
           return true; // Combinations are equal!!
       } else { // Combinations are not equal!
           return false;
       }
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

   public boolean isCombinationComplete(Combination p_Combination, Dependency p_Dependency) {
       if (p_Dependency == null || p_Combination == null) {
           return false;
       }
       int numOfElements = p_Dependency.getLnkElements().size();
       if (numOfElements == p_Combination.getEquivalenceClasses().size()) {
           return true;
       } else if (numOfElements > p_Combination.getEquivalenceClasses().size()) {
           return false;
       } else {
           return false;
       }
   }



   public boolean isCombinationInCombinations_2(Combination p_NewCombination, Vector p_CurrentCombinations) {
       Combination currentCombination = null;
       int numOfCurrentCombinations = p_CurrentCombinations.size();
       for (int i = 0; i < numOfCurrentCombinations; i++) {
           currentCombination = (Combination) p_CurrentCombinations.elementAt(i);
           if (currentCombination != p_NewCombination) {
               if (isCombinationInCombination(p_NewCombination, currentCombination)) {
                   return true;
               }
           }
       }
       return false;
   }

   public UndoableEdit deleteCombinationFromDependency(Combination p_CurrentCombination, Dependency p_Dependency, Structure p_Structure) {
       CMCompoundEdit ce = new CMCompoundEdit();
	   deleteCombinationRecursiv(p_CurrentCombination);
	   ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(p_Dependency, p_CurrentCombination));
       p_Dependency.removeCombination(p_CurrentCombination);//)nkCombinations().removeElement(p_CurrentCombination);
       ce.addEdit(TestCaseManager.INSTANCE.removeCombinationInTestCases(p_CurrentCombination, p_Structure));
       return ce;
   }




   public Vector getAllIdsOfCombinationRecursiv(Combination combination) {
       Vector idsOfCombination = new Vector(0);
       if (combination.getCombinations().size() > 0) {
           //grueda_13032005_begin
           if( combination.getId() != -1) {
             idsOfCombination.addElement(new Integer(combination.getId()));
           }
           //grueda_13032005_end
           int numOfChildrenCombinations = combination.getCombinations().size();
           Combination childCombination = null;
           for (int i = 0; i < numOfChildrenCombinations; i++) {
               childCombination = (Combination) combination.getCombinations().get(i);
               Vector idsOfChildCombination = getAllIdsOfCombinationRecursiv(childCombination);
               for (int j = 0; j < idsOfChildCombination.size(); j++) {
                   idsOfCombination.addElement((Integer) idsOfChildCombination.elementAt(j));
               }
           }
           return idsOfCombination;
       } else {
           //grueda_13032005_begin
           if( combination.getId() != -1) {
             idsOfCombination.addElement(new Integer(combination.getId()));
           }
           //grueda_13032005_end
           return idsOfCombination;
       }
   }


   // grueda_26072004_end



   public TestCaseManager getM_TestCaseManager() {
       return TestCaseManager.INSTANCE;
   }


   public DependencyManager getM_DependencyManager() {
       return DependencyManager.INSTANCE;
   }



/**
 * do the merge of a combination with a vector of combinations
 * for each one:
 * 1. Merge the cause effects
 * 3. Set the state idem to the parent
 * 4. Add the combination as a child of the combination
 * @param p_Combination
 * combination to be parent of the combinations
 * @param mergingCombinations
 * list of combinations to become child of the combination
 * @return
 * an undoable edit to implement the undo of this operation
 *
 */
public UndoableEdit mergeCombinations(Combination p_Combination, List<Combination> mergingCombinations) {
	CMCompoundEdit ce = new CMCompoundEdit();

		 //merge the cause effect of all combinations with the parent

	    ce.addEdit(mergeCauseEffects(p_Combination,mergingCombinations));
	    for( Iterator i = mergingCombinations.iterator(); i.hasNext();) {
		  Combination combination = (Combination) i.next();


		   if (!p_Combination.getCombinations().contains(combination))
				  ce.addEdit( addChildCombination(p_Combination, combination));
	    }
	return ce;
  }


/**
 * Merge the cause effects from a combination and a vector of combinations
 * both must have the same cause effects
 * @param p_Combination
 * parent combination
 * @param mergingCombinations
 * vector of combinations
 * @return
 * an undoable edit to perform undo and redo of the operation
 */
public CMCompoundEdit mergeCauseEffects(Combination p_Combination, List<Combination> mergingCombinations) {
	CMCompoundEdit ce = new CMCompoundEdit();
	//get the effects from  the combination
  	List mergedCauseEffects = p_Combination.getEffects();

    for( Iterator i  = mergingCombinations.iterator(); i.hasNext();){
    	//for each combination of the vector
      Combination c = (Combination) i.next();

      for( Iterator j = c.getEffects().iterator(); j.hasNext();){
    	  //for each effect of each combination
      		Effect causeEffect = (Effect) j.next();
      		if( !mergedCauseEffects.contains(causeEffect)){
      			//if is not contained in the parent add it
      			ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(p_Combination, causeEffect));
      			p_Combination.addEffect(causeEffect);


      		}
      }
    }

     //now all the combination childs must have the same effects
      for( Iterator i  = mergingCombinations.iterator(); i.hasNext();){
      	//for each combination of the vector
        Combination combination = (Combination) i.next();
        for (Iterator j = mergedCauseEffects.iterator();j.hasNext();){
        	Effect effect = (Effect) j.next();
        	if (!combination.getEffects().contains(effect))
        	{
        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(combination,effect));
      			combination.addEffect(effect);
        	}
//        	update the risk level
  			CMRiskLevelEditController riskLevelEditController = new CMRiskLevelEditController();
  			riskLevelEditController.setRiskLevelView(new CMRiskLevelView());
  			riskLevelEditController.setRiskLevelBean(effect);
  			ce.addEdit(riskLevelEditController.applyChanges());
  			//update the state
  			CMStateEditController stateEditController = new CMStateEditController();
  			stateEditController.setStateView(new CMStateView());
  			stateEditController.setStateBean(effect);
  			ce.addEdit(stateEditController.applyChanges());
        }

      }

	return ce;
   }


   /**
    *@Author hmendez
    *@date 28112005
    *@description Used to rebuild the Combinations Descriptions
    *@precondition Elements with repeated names will generate a wrong description, elements names are unique
   **/
   private class TemporalCombination{
		  private String m_value = "";
		  private String m_idElement = "";
		public String getM_idElement() {
			return m_idElement;
		}
		public void setM_idElement(String element) {
			m_idElement = element;
		}
		public String getM_value() {
			return m_value;
		}
		public void setM_value(String m_value) {
			this.m_value = m_value;
		}
	  }

   public String rebuildCombinationDescription(Combination p_Combination)
   {
       List l_combinationsList = new ArrayList();
       List l_elementsList = new ArrayList();
       TemporalCombination l_TC = new TemporalCombination();
       for (int i=0;i<p_Combination.getEquivalenceClasses().size();i++)
       {
    	   if (p_Combination.getEquivalenceClasses().get(i).getLnkElement() == null) continue;
       	l_TC = new TemporalCombination();
       	l_TC.setM_value(((EquivalenceClass)p_Combination.getEquivalenceClasses().get(i)).getValue());

       	l_TC.setM_idElement(((EquivalenceClass)p_Combination.getEquivalenceClasses().get(i)).getLnkElement().getName());
       	l_combinationsList.add(l_TC);
       	if (!l_elementsList.contains(l_TC.getM_idElement()))
       	  l_elementsList.add(l_TC.getM_idElement());
       }
       for (int j=0;j<p_Combination.getMergedEquivalenceClasses().size();j++)
       {
       	l_TC = new TemporalCombination();
       	l_TC.setM_value(((EquivalenceClass)p_Combination.getMergedEquivalenceClasses().get(j)).getValue());
       	l_TC.setM_idElement(((EquivalenceClass)p_Combination.getMergedEquivalenceClasses().get(j)).getLnkElement().getName());
       	l_combinationsList.add(l_TC);
       	if (!l_elementsList.contains(l_TC.getM_idElement()))
         	  l_elementsList.add(l_TC.getM_idElement());
       }
       String l_element = "";
       String l_newDescription = "";
       String l_newSubDescription = "";
       int l_elementcount;
       boolean l_addOr = false;
       for (int i=0;i<l_elementsList.size();i++)
       {
     	l_elementcount = 0;
         l_element = (String)l_elementsList.get(i);
         l_newSubDescription = "";
         for (int j=0;j<l_combinationsList.size();j++)
         {
       	 l_TC = new TemporalCombination();
       	 l_TC.setM_value(((TemporalCombination)l_combinationsList.get(j)).getM_value());
       	 l_TC.setM_idElement(((TemporalCombination)l_combinationsList.get(j)).getM_idElement());
       	 if (l_element.equalsIgnoreCase(l_TC.getM_idElement()))
            {
       		 if (l_addOr)
       			l_newSubDescription = l_newSubDescription+BusinessRules.OR+BusinessRules.SPACE;
       		 l_newSubDescription = l_newSubDescription+l_element+BusinessRules.EQUAL+BusinessRules.DOUBLE_QUOTE;
       		 l_newSubDescription = l_newSubDescription+l_TC.getM_value()+BusinessRules.DOUBLE_QUOTE+BusinessRules.SPACE;
       		 l_addOr= true;
       		 l_elementcount++;
            }
         }
         l_addOr = false;
         l_newSubDescription = l_newSubDescription.substring(0,l_newSubDescription.length()-1);
         //if (l_elementcount>1) <-- Take out comment to avoid expressions like (a=1)
         l_newSubDescription = BusinessRules.OPEN_PARENTHESES+l_newSubDescription+BusinessRules.CLOSED_PARENTHESES+BusinessRules.SPACE;
         l_newDescription = l_newDescription+l_newSubDescription;
 		if (i+1<l_elementsList.size())
 		  l_newDescription = l_newDescription+BusinessRules.SPACE+BusinessRules.AND+BusinessRules.SPACE;
       }
       String l_thenStmt = "";
       for (int l=0;l<p_Combination.getEffects().size();l++)
       {
     	l_thenStmt = l_thenStmt + ((Effect)p_Combination.getEffects().get(l)).getDescription();
     	if (l+1<p_Combination.getEffects().size())
     	  l_thenStmt = l_thenStmt+BusinessRules.SPACE+BusinessRules.COMMA+BusinessRules.SPACE;
       }
       if (p_Combination.getEffects().size()>=1)
       {
         l_thenStmt = BusinessRules.SPACE+BusinessRules.THEN+BusinessRules.SPACE+BusinessRules.OPEN_CURLY_BRACKET+l_thenStmt;
         l_thenStmt= l_thenStmt + BusinessRules.CLOSED_CURLY_BRACKET;

       }
       l_newDescription = l_newDescription + l_thenStmt;
       return l_newDescription;
   }
   /**
    * hmendez_end
  *
 * @param views TODO
 * @param includingExcludingCombinations
 * @param dependency
 * @return
 */
public Vector unMergeallCombinations( Vector includingExcludingCombinations, Dependency dependency,CMCompoundEdit p_ce) {
  Vector includingCombinations = (Vector) includingExcludingCombinations.elementAt(0);
  Vector excludingCombinations = (Vector) includingExcludingCombinations.elementAt(1);
  Vector unmergedCombinations = new Vector(0);
  int numOfIncludingCombinations = includingCombinations.size();
  int numOfExcludingCombinations = excludingCombinations.size();
  Combination combination = null;
  //////////////////// DESTROY ANY HIERARCHY //////////////////////////////////
	for( int i = 0; i < numOfIncludingCombinations; i++) {
    combination = (Combination) includingCombinations.elementAt(i);

    p_ce.addEdit(deleteAllChildCombinations(combination));

    p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeCombinationParentModelEdit(combination,null));//CC 14-12-05
    combination.setLnkCombinationParent(null);
  }
  for( int i = 0; i < numOfExcludingCombinations; i++) {
    combination = (Combination) excludingCombinations.elementAt(i);


    p_ce.addEdit(deleteAllChildCombinations(combination));//getLnkCombinations().removeAllElements();
    p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeCombinationParentModelEdit(combination,null));//CC 14-12-05
    combination.setLnkCombinationParent(null);
  }
  //////////////////////////////////////////////////////////////////////////////
  CMCompoundEdit ce1 = new CMCompoundEdit();
	Combination mergedCombinationOfIncludingCombinations = mergeCombinations( includingCombinations,ce1);
	p_ce.addEdit(ce1);
	CMCompoundEdit ce2 = new CMCompoundEdit();
  Combination mergedCombinationOfExcludingCombinations = mergeCombinations(excludingCombinations,ce2);
  p_ce.addEdit(ce2);
  if( mergedCombinationOfIncludingCombinations.getId() < mergedCombinationOfExcludingCombinations.getId()) {
    unmergedCombinations.addElement(mergedCombinationOfIncludingCombinations);
    unmergedCombinations.addElement(mergedCombinationOfExcludingCombinations);
  }
  else  {
			unmergedCombinations.addElement(mergedCombinationOfExcludingCombinations);
			unmergedCombinations.addElement(mergedCombinationOfIncludingCombinations);
  }
  return unmergedCombinations;
 }
public Combination mergeCombinations( Vector mergingCombinations, CMCompoundEdit p_ce) {
   int numOfMergingCombinations = mergingCombinations.size();
   Combination firstCombination = null;
   Combination mergingCombination = null;
   Combination mergedCombination = null;

   Vector remainingCombinations = new Vector(0);
   if( numOfMergingCombinations >= 2) {
     firstCombination = (Combination) mergingCombinations.elementAt(0);
		 for( int i = 0; i < numOfMergingCombinations; i++){
       mergingCombination = (Combination) mergingCombinations.elementAt(i);
       if( mergingCombination != firstCombination) {
         remainingCombinations.addElement(mergingCombination);
       }
     }
		  p_ce.addEdit(mergeCombinations(firstCombination,remainingCombinations));
		  mergedCombination = firstCombination;
   }
   else {
	   if( numOfMergingCombinations == 1) {
       firstCombination = (Combination) mergingCombinations.elementAt(0);
       mergedCombination = firstCombination;
     }
   }
   for (int i=0;i<mergingCombinations.size();i++){
	   if (mergingCombinations.elementAt(i)!=mergedCombination){
		   mergingCombinations.removeElementAt(i);
	   }
   }
   return mergedCombination;
 }
public List<EquivalenceClass> buildSearchMergingPattern(Combination combination,EquivalenceClass mergingEquivalenceClass) {
    List<EquivalenceClass> searchMergingPattern = new ArrayList<EquivalenceClass>();
    
    searchMergingPattern.add(mergingEquivalenceClass);
    
	Element selectedElement = mergingEquivalenceClass.getLnkElement();
	
			
			for(EquivalenceClass equivalenceClass : combination.getEquivalenceClasses()) {
           //// WITHIN OTHER ELEMENTS
				if( equivalenceClass.getLnkElement() != selectedElement 
						&& !searchMergingPattern.contains(equivalenceClass)) {
					searchMergingPattern.add(equivalenceClass);
				}
			}

      for(Combination childCombination : combination.getCombinations()) {
        
    	  List<EquivalenceClass> partOfSearchMergingPattern = buildSearchMergingPattern(childCombination, mergingEquivalenceClass);
        
        for(EquivalenceClass equivalenceClass : partOfSearchMergingPattern) {
          
          if (!searchMergingPattern.contains(equivalenceClass))
        	  searchMergingPattern.add(equivalenceClass);
        }
      }
      return searchMergingPattern;
    
  }
/**
   * This method is almost the same as <code>searchForMergingCombinations</code> just change the way to compare
   * if a combination can be merged or not.
   * @param selectedCombination
   * @param views TODO
 * @param equivalenceClass
 * @param p_selectedCombination TODO
 * @param p_Dependency TODO
 * @return a vector with the list of combinations that can be merged according to equivalence class passed
   */
  //MODIFIED: almost new method
  public List<Combination> lookForMergingCombinations( EquivalenceClass equivalenceClass, Combination p_selectedCombination, Dependency p_Dependency) {
      Dependency dependency = p_Dependency;
      List<Combination> mergingCombinations = new ArrayList<Combination>();
      
      List<EquivalenceClass> searchMergingPattern = buildSearchMergingPattern(p_selectedCombination, equivalenceClass);     

      for (Combination combination : dependency.getCombinations()) {
          if (p_selectedCombination != combination) { 
              if (canMergeCombination(combination, searchMergingPattern)) {
        	  			mergingCombinations.add(combination);
              }
          }
      }

      return mergingCombinations;
  }
/**
    * @param views TODO
 * @param combination
 * @param searchMergingPattern
 * @return true if the combination can be merged with the merging pattern passed as parameter
    */
  	public boolean canMergeCombination( Combination combination, List<EquivalenceClass> searchMergingPattern) {
        
        Element element = searchMergingPattern.get(0).getLnkElement();
        
  
        List<EquivalenceClass> equivalenceClassesInCombination = combination.getEquivalenceClasses();
        

       
       Map mapElements = new HashMap();
      

        for (EquivalenceClass equivalenceClass : searchMergingPattern) {
            
            element = equivalenceClass.getLnkElement();
            if ( mapElements.containsKey(element.getName()) ) {
                if ( ((Boolean) mapElements.get(element.getName())).booleanValue() ) {
                    continue;
                }
            } else {
                mapElements.put(element.getName(), new Boolean(false));
            }

            if (combination.contains(equivalenceClass)) {
                mapElements.put(element.getName(), new Boolean(true));
            }
        }

        Iterator iterator = mapElements.values().iterator();
        while (iterator.hasNext()) {
            Boolean boolElement = (Boolean) iterator.next();
            if (!boolElement.booleanValue()) {
                return false;
            }
        }

        if (AreEquivalenceClassesNotInSearchPattern(combination, searchMergingPattern)) {
            return false;
        } else {
            return true;
        }
  	}
  	 public boolean AreEquivalenceClassesNotInSearchPattern(Combination combination, List searchMergingPattern) {
  		EquivalenceClass equivalenceClassInCombination = null;
     if( combination.getCombinations().size() > 0) {
       int numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();

 		  for( int i = 0; i < numOfEquivalenceClassesInCombination; i++) {
 			  equivalenceClassInCombination = (EquivalenceClass) combination.getEquivalenceClasses().get(i);
 				  if( !searchMergingPattern.contains(equivalenceClassInCombination)) {
 						 return true;
 					}
 			}
       int numOfChildrenCombinations = combination.getCombinations().size();
       for( int i = 0; i < numOfChildrenCombinations; i++) {
         Combination childCombination = (Combination) combination.getCombinations().get(i);
         if( AreEquivalenceClassesNotInSearchPattern(childCombination, searchMergingPattern)) {
           return true;
         }
       }
       return false;
 		}
 		else {
       int numOfEquivalenceClassesInCombination = combination.getEquivalenceClasses().size();
 		  for( int i = 0; i < numOfEquivalenceClassesInCombination; i++) {
 			  equivalenceClassInCombination = (EquivalenceClass) combination.getEquivalenceClasses().get(i);
 				  if( !searchMergingPattern.contains(equivalenceClassInCombination)) {
 						 return true;
 					}
       }
       return false;
 		}
   }
public void logger(Vector searchMergingPattern, EquivalenceClass equivalenceClass) {
			//LOGGER//////////////////////
	Logger.getLogger(this.getClass()).debug(BusinessRules.LABEL_SEARCH_PATTERN); //$NON-NLS-1$
	      int numOfSearchEquivalenceClasses = searchMergingPattern.size();
	      for( int i = 0; i < numOfSearchEquivalenceClasses; i++) {
	       if( ((EquivalenceClass) searchMergingPattern.elementAt(i)) == equivalenceClass) {
	    	   Logger.getLogger(this.getClass()).debug("[");
	    	   Logger.getLogger(this.getClass()).debug(equivalenceClass.getName());
	    	   Logger.getLogger(this.getClass()).debug("],");
	       }
	       else {
	    	   Logger.getLogger(this.getClass()).debug(((EquivalenceClass)searchMergingPattern.elementAt(i)).getName());
	    	   Logger.getLogger(this.getClass()).error(",");
	       }
	      }
	      Logger.getLogger(this.getClass()).error("");
	      //////////////////////////////

	  }
/**
 *  Assigns a vector of cause effects to a combination and all of it's children (deleteing the existing elements)
 * @param p_Combination
 * @param p_CauseEffects
 * @return
 */
public CMCompoundEdit assignCauseEffectsToCombination(Combination p_Combination, Vector p_CauseEffects){
	 CMCompoundEdit ce = new CMCompoundEdit();
	 //delete all effects
	 while (p_Combination.getEffects().size()>0)
	 {
		 ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteEffectModelEdit(p_Combination,(Effect)p_Combination.getEffects().get(0)));
		 p_Combination.getEffects().remove(0);
	 }
	  //update the vector of cause effects of the parent with new cause effects
	 for (Iterator i = p_CauseEffects.iterator();i.hasNext();)
	 {
		 Effect effect = (Effect) i.next();
		 if (!p_Combination.getEffects().contains(effect))
		 {
			 ce.addEdit(CMModelEditFactory.INSTANCE.createAddEffectModelEdit(p_Combination,effect));
			 p_Combination.getEffects().add(effect);
		 }
	 }
	 //do the same for the children combinations
	for (Iterator i=p_Combination.getCombinations().iterator();i.hasNext();){
			Combination combination = (Combination)i.next();
			//recursive call for all children
			ce.addEdit(assignCauseEffectsToCombination(combination,p_CauseEffects));
		}

	return ce;
  }


/**
*@autor smoreno
 * @return
 */
public static Combination getSelectedCombination() {

	return CMApplication.frame.getCMGridCombinationViews().getSelectedCombination();
}

/**
*@autor smoreno
 * @return
 */
public static EquivalenceClass getSelectedEquivalenceClass() {

	return CMApplication.frame.getCMGridCombinationViews().getSelectedEquivalenceClass();
}

/**
 * @param p_combination
 * @param p_dependency
 * @return
 */
public UndoableEdit deleteCombination(Combination p_combination, Dependency p_dependency) {
	Structure structure = p_dependency.getLnkStructure();
	CMCompoundEdit ce = new CMCompoundEdit();
	//removes the combination from all test Cases
	for (TestCase testCase : structure.getTestCases())
	{
		if (testCase.getCombinations().contains(p_combination)){
			ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(testCase,p_combination));
			testCase.removeCombination(p_combination);
		}

	}
	//delete this combination from the parent combination
	if (p_combination.getLnkCombinationParent()!=null)
	{
		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(p_combination.getLnkCombinationParent(),p_combination));
		p_combination.getLnkCombinationParent().removeCombination(p_combination);
	}

	//removes the childs combinations recursively
	List<Combination> childCombos = new ArrayList<Combination>();
	childCombos.addAll(p_combination.getCombinations());
	for (Combination combination : childCombos )
		ce.addEdit(deleteCombination(combination,p_dependency));

	//removes the combination from the parent dependency
	if (p_dependency.getCombinations().contains(p_combination))
	{
		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(p_dependency,p_combination));
		p_dependency.removeCombination(p_combination);
	}
	return ce;
}

public UndoableEdit changeCombinationState(State p_State){
	CMCompoundEdit ce = new CMCompoundEdit();
	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStateModelEdit(getSelectedCombination(), p_State));
	getSelectedCombination().setState(p_State);
	return ce;
}

public void updateCombinationsGeneration(Dependency dependency, CMCompoundEdit ce) {

		 ApplicationSetting appSetting = CMApplication.frame.getTreeWorkspaceView().getM_Session2().getM_ApplicationSetting();
	     int maxNumOfCombinations = appSetting.getM_MaxNumOfCombinations();
	     if (dependency.getTypeofCombinations() == CMOriginTypeBean.Origin.PERMUTATION ){
	    	 PermutationGeneratorUpdater generatorUpdater = new PermutationGeneratorUpdater(dependency);
	    	 generatorUpdater.updateCombinationsByPermutation(dependency,maxNumOfCombinations,dependency.getElements(),ce);
	     }
	     if (dependency.getTypeofCombinations() == CMOriginTypeBean.Origin.ALLPAIRS ){
	    	 AllPairsGeneratorUpdater generatorUpdater = new AllPairsGeneratorUpdater(dependency);
	    	 generatorUpdater.updateCombinationsByAllPairs(dependency,maxNumOfCombinations,dependency.getLnkStructure(),ce);
	     }
}

/**
 * unmerge operation
* @param dependencyIndex
* dependency index on the structure
* @param selectedViewIndex
* combination view  index
* @param selectedRow
* selected row of the grid
* @param selectedEquivalenceClass
* equivalence class of the merge
* @param selectedCombination
* combination parent of the merge
* @param withConfirmation
*
* @return
*/
public UndoableEdit separateCombinationsAt( EquivalenceClass p_selectedEquivalenceClass, Combination p_selectedCombination, Dependency selectedDependency){

		CMCompoundEdit ce = new CMCompoundEdit();
	
		Vector includingExcludingCombinations =getIncludingExcludingCombinations(p_selectedEquivalenceClass,p_selectedCombination, selectedDependency);

	    Vector includingCombinations = (Vector) includingExcludingCombinations.elementAt(0);
      Vector excludingCombinations = (Vector) includingExcludingCombinations.elementAt(1);
      
      if( excludingCombinations.size() > 0) {
      	CMCompoundEdit ce1 = new CMCompoundEdit();
		  Vector<Combination> unmergedCombinations = unMergeallCombinations( includingExcludingCombinations, selectedDependency,ce1);// Claudia adds (Vector)... .clone()
		  ce.addEdit(ce1);
           unmergedCombinations.remove(p_selectedCombination);

           ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(selectedDependency, unmergedCombinations.get(0)));
           selectedDependency.addCombination(unmergedCombinations.get(0));
       }
        else {
		      JOptionPane.showMessageDialog(CMApplication.frame,CMMessages.getString("LABEL_NOT_POSSIBLE_TO_UNMERGE_1")); //$NON-NLS-1$       
       }
      return ce;
}

private Vector getIncludingExcludingCombinations(EquivalenceClass p_selectedEquivalenceClass, Combination p_selectedCombination, Dependency p_selectedDependency){
	  Vector someCombinationsThatIncludeTheEquivalenceClass = new Vector(0);
	  int    numOfSomeCombinationsThatIncludeTheEquivalenceClass = 0;
	  Vector someCombinationsThatExcludeTheEquivalenceClass = new Vector(0);
	  int    numOfSomeCombinationsThatExcludeTheEquivalenceClass = 0;
	  Vector allCombinationsThatIncludeTheEquivalenceClass = new Vector(0);
	  Vector allCombinationsThatExcludeTheEquivalenceClass = new Vector(0);
	  Vector someCombinationsThatIncludeORExcludeTheEquivalenceClass = null;
	  Vector combinationsThatIncludeORExcludeTheEquivalenceClass = new Vector(0);
	  Combination combination = null;


	  Combination childCombination = null;
	  Vector total = new Vector(0);
	  int numOfChildrenCombinations = p_selectedCombination.getCombinations().size();
	  if( numOfChildrenCombinations > 0 ) {

			    if( p_selectedCombination.getEquivalenceClasses().contains(p_selectedEquivalenceClass)) {
					  allCombinationsThatIncludeTheEquivalenceClass.addElement(p_selectedCombination);
				  }
				  else {
					  allCombinationsThatExcludeTheEquivalenceClass.addElement(p_selectedCombination);
				  }

				for( int i = 0; i < numOfChildrenCombinations; i++) {

					childCombination = (Combination) p_selectedCombination.getCombinations().get(i);

					someCombinationsThatIncludeORExcludeTheEquivalenceClass = getIncludingExcludingCombinations(p_selectedEquivalenceClass, childCombination,p_selectedDependency);
					someCombinationsThatIncludeTheEquivalenceClass = (Vector) someCombinationsThatIncludeORExcludeTheEquivalenceClass.elementAt(0);
					numOfSomeCombinationsThatIncludeTheEquivalenceClass = someCombinationsThatIncludeTheEquivalenceClass.size();
					for( int j = 0; j < numOfSomeCombinationsThatIncludeTheEquivalenceClass; j++) {
				          combination = (Combination) someCombinationsThatIncludeTheEquivalenceClass.elementAt(j);
				          allCombinationsThatIncludeTheEquivalenceClass.addElement(combination);
				        }
					 someCombinationsThatExcludeTheEquivalenceClass = (Vector) someCombinationsThatIncludeORExcludeTheEquivalenceClass.elementAt(1);
				        numOfSomeCombinationsThatExcludeTheEquivalenceClass = someCombinationsThatExcludeTheEquivalenceClass.size();
				        for( int j = 0; j < numOfSomeCombinationsThatExcludeTheEquivalenceClass; j++) {
				          combination = (Combination) someCombinationsThatExcludeTheEquivalenceClass.elementAt(j);
				          allCombinationsThatExcludeTheEquivalenceClass.addElement(combination);
				        }

				}
				combinationsThatIncludeORExcludeTheEquivalenceClass.addElement(allCombinationsThatIncludeTheEquivalenceClass);

				combinationsThatIncludeORExcludeTheEquivalenceClass.addElement(allCombinationsThatExcludeTheEquivalenceClass);

	      return combinationsThatIncludeORExcludeTheEquivalenceClass;
	  }
	  else {
					if(p_selectedCombination.getEquivalenceClasses().contains(p_selectedEquivalenceClass)) {
						someCombinationsThatIncludeTheEquivalenceClass.add(p_selectedCombination);
					}
					else {
						someCombinationsThatExcludeTheEquivalenceClass.add(p_selectedCombination);
					}
	        combinationsThatIncludeORExcludeTheEquivalenceClass.addElement(someCombinationsThatIncludeTheEquivalenceClass);
	        combinationsThatIncludeORExcludeTheEquivalenceClass.addElement(someCombinationsThatExcludeTheEquivalenceClass);
	        return combinationsThatIncludeORExcludeTheEquivalenceClass;
	  }
}
/**
 * @param dependencyIndex
 * @param selectedViewIndex
 * @param selectedRow
 * @param selectedEquivalenceClass
 * @param selectedCombination
 * @param mergingCombinations
 * @param withConfirmation
 * @return
 * the position of final merge
 */
public UndoableEdit mergeCombinationsAt(EquivalenceClass p_selectedEquivalenceClass, Combination p_selectedCombination,Dependency p_Dependency, List<Combination> mergingCombinations) {

	CMCompoundEdit ce = new CMCompoundEdit();
	
    if (mergingCombinations.size() > 0) {
    	Combination mergedCombination  =null;
  		ce.addEdit(mergeCombinations(p_selectedCombination,mergingCombinations));
  		mergedCombination = p_selectedCombination;
        ce.addEdit(DependencyManager.INSTANCE.removeCombinationsFromDependency(mergingCombinations, p_Dependency));
        ce.addEdit(TestCaseManager.INSTANCE.removeReferecedTestCasesbyCombinations(mergingCombinations, p_Dependency.getLnkStructure()));
       CMApplication.frame.getTabbedPaneTestCasesElementsViews().setTestCasesModified(true);
    }
    else {
            JOptionPane.showMessageDialog(CMApplication.frame, CMMessages.getString("INFO_CURRENT_COMBINATION_CAN_NOT_BE_MERGED")); //$NON-NLS-1$
    }
    return ce;
}

/**
 * Adds the combination to the list of child combinations.
 * also performs some normalizations on the parent and the child
 * @param combination, the combination to add as a child.
 * @return
 * and undoable edit to do ht undo and redo
 */
public UndoableEdit addChildCombination(Combination parent, Combination combination) {
	CMCompoundEdit ce = new CMCompoundEdit();
	//set the parent link
    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeCombinationParentModelEdit(combination,parent));
    combination.setLnkCombinationParent(parent);
    //add the combination to the child list
    ce.addEdit(CMModelEditFactory.INSTANCE.createAddCombinationModelEdit(parent,combination));
   parent.addCombination(combination);
    //calculate the new state of the combination
    int newRiskLevel = parent.getRiskLevel(parent, combination);
    ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelModelEdit(parent,newRiskLevel));
    parent.setRiskLevel(newRiskLevel);
	return ce;
}

public UndoableEdit deleteAllChildCombinations(Combination combi){
	CMCompoundEdit ce = new CMCompoundEdit();
	for(Combination c  : combi.getCombinations()){
		ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveCombinationModelEdit(combi,c));
		combi.removeCombination(c);
	}
	return ce;
}

}
