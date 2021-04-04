/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import com.eliad.swing.JSmartGrid;

import model.Combination;
import model.EquivalenceClass;
import model.StdCombination;
import model.TestCase;
import bi.view.cells.renderers.CMImageEquivalenceClassinTestCaseRenderer;

public class CMCellEquivalenceClassInTestCase extends CMBaseCell{
    public CMCellEquivalenceClassInTestCase(EquivalenceClass equivalenceClass, TestCase testCase) {
      super(testCase);
      this.equivalenceClass = equivalenceClass;
    }
 
    public static final CMImageEquivalenceClassinTestCaseRenderer defaultRenderer = new CMImageEquivalenceClassinTestCaseRenderer();
    
    public EquivalenceClass getEquivalenceClass(){ return equivalenceClass; }

    public String toString() { 
    	if (getModel()==null || equivalenceClass==null)
    		return "";
    	TestCase testCase = (TestCase) getModel();
    	StringBuilder builder = new StringBuilder();
    	for (Combination combination : testCase.getCombinations(equivalenceClass)){
    		if (builder.length()>0)
    			builder.append(",");
    		if (combination.getDependencyRoot()!=null)
    			builder.append(combination.getDependency().getName()+".");
    		builder.append(combination.getName());
    		builder.append(combination.getStateName());
    	}
  if (testCase.getStdCombination(equivalenceClass)!=null)
    		builder.append(testCase.getStdCombination(equivalenceClass).getName());

    	
    	if (builder.length()>0)
    		return builder.toString();
    	if (testCase.getEquivalenceClasses().contains(equivalenceClass))
    		return getEquivalenceClass().getName() ;
    	return "";
    }
    private EquivalenceClass equivalenceClass;
}
