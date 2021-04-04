package bi.controller.combination.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.undo.UndoableEdit;

import org.apache.log4j.Logger;

import bi.controller.AllPairsCombinationManager;
import bi.controller.CombinationManager;
import bi.controller.DependencyManager;
import bi.controller.IAllPairsCombinationManager;
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

public class AllPairsGenerator extends AbstractCombinationGenerator {

	public AllPairsGenerator(Dependency dependency) {
		super(dependency);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected UndoableEdit doGenerate() throws Exception {
		CMCompoundEdit ce = new CMCompoundEdit();
		 if( dependency.getCombinations().size() > 0)
			  if (JOptionPane.showConfirmDialog(CMApplication.frame,CMMessages.getString("INFO_COMBINATIONS_WILL_BE_DELETED"),CMMessages.getString("LABEL_GENERATE_COMBINATIONS"),
					  JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)
				  return ce;

		 Object result = generateCombinationsByAllPairs(dependency, getMaxNumberofCombinations(),ce);
		  if( result instanceof Vector) {
			      ce.addEdit(CombinationManager.INSTANCE.deleteAllCombinations(dependency,	StructureManager.getSelectedStructure()));
				  ce.addEdit(CombinationManager.INSTANCE.addAllPairsCombinationsToDependency((Vector)result, dependency));
				}
		else if( result instanceof Integer)
					    	throw new Exception(getErrorMessage((Integer)result));
		return ce;

	}

	public Object generateCombinationsByAllPairs(Dependency p_Dependency, int p_MaxNumOfCombinations,CMCompoundEdit p_ce) {

	       int numOfCombinations = 0;
	       ArrayList allPairsCombinations = new ArrayList();
	       Vector combinationsVector = null;
	       if (p_Dependency != null) {
	           if (p_Dependency.getLnkElements().size() >= BusinessRules.MIN_NUMBER_OF_DEPENDENT_ELEMENTS) {
	               allPairsCombinations = generateAllPairsCombinations(p_Dependency);
	               orderCombinationsBasedOnEquivalenceClassesOfElement(true, allPairsCombinations, p_Dependency);
	               numOfCombinations = allPairsCombinations.size();
	               if (numOfCombinations > 0) {
	                   if (numOfCombinations <= p_MaxNumOfCombinations) {
	                       combinationsVector = convertToCombinationsVector(allPairsCombinations, p_Dependency,p_ce);

	                   } else { // > MAX_NUMBER_OF_IDS
	                       return new Integer(1); // JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_1"));

	                   }
	               } else { // <= 0
	                   return new Integer(2);// JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_2"));

	               }

	           } else {
	               return new Integer(3);// JOptionPane.showMessageDialog(this.cmFrame,CMMessages.getString("INFO_GENERATION_OF_COMBINATIONS_NOT_POSIBLE_3"));
	           }
	       }

	       return combinationsVector;
	   }
	public ArrayList generateAllPairsCombinations(Dependency p_Dependency) {
	       ArrayList allPairsCombinations = new ArrayList();
	       IAllPairsCombinationManager iAllPairsTestCaseManager = new AllPairsCombinationManager();
	       ArrayList useCaseData = (ArrayList) preprocessUseCaseData(p_Dependency);
	       allPairsCombinations = iAllPairsTestCaseManager.generateAllPairsCombinations(useCaseData);

	       return allPairsCombinations;
	   }
	public ArrayList preprocessUseCaseData(Dependency p_Dependency) {
	       ArrayList useCaseData = new ArrayList();
	       int numOfDependentElements = p_Dependency.getLnkElements().size();
	       StringBuffer elementsBuffer = new StringBuffer();
	       for (int i = 0; i < numOfDependentElements; i++) {
	           Element element = (Element) p_Dependency.getLnkElements().elementAt(i);
	           elementsBuffer.append(element.getId());
	           if (i != numOfDependentElements - 1) {
	               elementsBuffer.append("\t");
	           }
	       }
	       useCaseData.add(elementsBuffer.toString());

	       boolean existsDependentEquivalenceClasses = true;
	       int equivalenceClassIndex = 0;
	       int counterOfNotExistingEquivalenceClasses = 0;
	       while (existsDependentEquivalenceClasses) {
	           StringBuffer equivalenceClassesBuffer = new StringBuffer();
	           counterOfNotExistingEquivalenceClasses = 0;

	           for (int i = 0; i < numOfDependentElements; i++) {
	               Element element = p_Dependency.getLnkElements().elementAt(i);
	               List<EquivalenceClass>  dependentEquivalenceClasses = p_Dependency.getEquivalenceClasses(element);

	               int numOfDependentEquivalenceClasses = dependentEquivalenceClasses.size();
	               EquivalenceClass equivalenceClass = null;
	               if (equivalenceClassIndex < numOfDependentEquivalenceClasses) {
	                   equivalenceClass = (EquivalenceClass) dependentEquivalenceClasses.get(equivalenceClassIndex);
	                   equivalenceClassesBuffer.append(element.getId());
	                   equivalenceClassesBuffer.append(BusinessRules.COMMA);
	                   equivalenceClassesBuffer.append(equivalenceClass.getId());
	                   if (i != numOfDependentElements - 1) {
	                       equivalenceClassesBuffer.append("\t");
	                   }
	               } else {
	                   if (i != numOfDependentElements - 1) {
	                       equivalenceClassesBuffer.append("\t");
	                   }
	                   counterOfNotExistingEquivalenceClasses++;
	               }
	           }
	           if (counterOfNotExistingEquivalenceClasses == numOfDependentElements) {
	               existsDependentEquivalenceClasses = false;
	           } else {
	               useCaseData.add(equivalenceClassesBuffer.toString());
	               equivalenceClassIndex++;
	           }
	       }
	       return useCaseData;
	   }
	public void orderCombinationsBasedOnEquivalenceClassesOfElement(boolean p_TopDown, List<Combination> p_Combinations, Dependency p_Dependency) {
	       final Dependency dependency = p_Dependency;
	       if (p_Combinations == null || dependency == null) {
	           return;
	       }
	       if (dependency.getLnkElements().size() > 0) {
	           final Element element = (Element) dependency.getLnkElements().elementAt(0);
	           Collections.sort(p_Combinations, new Comparator() {
	               public int compare(Object o1, Object o2) {
	                   ArrayList s1 = (ArrayList) o1;
	                   ArrayList s2 = (ArrayList) o2;
	                   String stemp1 = (String) s1.get(0);
	                   String stemp2 = (String) s2.get(0);
	                   String[] stempTokens1 = stemp1.split(BusinessRules.COMMA);
	                   String[] stempTokens2 = stemp2.split(BusinessRules.COMMA);
	                   String ec1 = stempTokens1[1];
	                   String ec2 = stempTokens2[1];

	                   EquivalenceClass equivalenceClass1 = DependencyManager.INSTANCE.getEquivalenceClass(dependency, stemp1);
	                   EquivalenceClass equivalenceClass2 = DependencyManager.INSTANCE.getEquivalenceClass(dependency, stemp2);

	                   int index1 = element.getEquivalenceClasses().indexOf(equivalenceClass1);
	                   Logger.getLogger(this.getClass()).info(index1);
	                   int index2 = element.getEquivalenceClasses().indexOf(equivalenceClass2);
	                   Logger.getLogger(this.getClass()).info(index2);
	                   Logger.getLogger(this.getClass()).info("***");
	                   if (index1 > index2) {
	                       return 1;
	                   } else if (index1 < index2) {
	                       return -1;
	                   } else {
	                       return 0;
	                   }
	               }
	           });

	       }
	   }
	private Vector convertToCombinationsVector(ArrayList p_AllPairsCombinations, Dependency p_Dependency,CMCompoundEdit p_ce) {
	       ArrayList combinedEquivalenceClasses = null;
	       String equivalenceClassIdString = null;
	       EquivalenceClass equivalenceClass = null;
	       Combination newCombination = null;
	       Vector<Combination> combinationsVector = new Vector<Combination>(0);

	       for (int i = 0; i < p_AllPairsCombinations.size(); i++) {
	           newCombination = new Combination();
	           p_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeOriginTypeModelEdit(newCombination, Combination.Origin.ALLPAIRS));
	           newCombination.setOriginType(Combination.Origin.ALLPAIRS);

	         //  p_ce.addEdit(CMModelEditFactory.INSTANCE.createCombinationModelEdit(combinationsVector, newCombination));
	           combinationsVector.addElement(newCombination);
	           combinedEquivalenceClasses = (ArrayList) p_AllPairsCombinations.get(i);
	           int numOfCombinedEquivalenceClasses = combinedEquivalenceClasses.size();
	           for (int j = 0; j < numOfCombinedEquivalenceClasses; j++) {
	               equivalenceClassIdString = (String) combinedEquivalenceClasses.get(j);
	               equivalenceClass = DependencyManager.INSTANCE.getEquivalenceClass(p_Dependency, equivalenceClassIdString);
	               p_ce.addEdit(CombinationManager.INSTANCE.addEquivalenceClassToCombination(equivalenceClass, newCombination));
	           }
	       }
	       return combinationsVector;
	   }


}
