/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.util;

import java.util.Comparator;

import model.Dependency;
import model.Element;
import model.TestCase;

/**
 * @author smoreno
 *
 */
public class CMElementWorkFlowComparator implements Comparator{

	private TestCase testCase;

	/**
	 * @param p_testCase
	 */
	public CMElementWorkFlowComparator(TestCase p_testCase) {
		this.testCase = p_testCase;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(Object p_o1, Object p_o2) {
		//try to compare the element taking the dependency of the first combination of the testCase

		//both objects must be Element
		if ((p_o1 instanceof Element) && (p_o2 instanceof Element)&&(testCase!=null))
			if (testCase.getCombinations().size()>0)
			{
				Dependency dep = testCase.getCombinations().get(0).getDependency();
				//if theres no asociation of the test case with any dependency return the user Order
				if (dep==null)
					return CMUserOrderBean.COMPARATOR.compare(p_o1,p_o2);
				int indexEl1 = dep.getElements().indexOf(p_o1);
				int indexEl2 = dep.getElements().indexOf(p_o2);
				//if none of the elements are present  in the dependency the User Order will be taked
				if ((indexEl1==-1)&&(indexEl2==-1))
					return CMUserOrderBean.COMPARATOR.compare(p_o1,p_o2);
				//if one of the elements is not present in the dependency the element that is present will be first
				if (indexEl1==-1&&indexEl2!=-1)
					return 1;
				if (indexEl1!=-1&&indexEl2==-1)
					return -1;
				//if both elements are in the dependency take the order of the dependency
				if (indexEl1>indexEl2)
					return 1;
				if (indexEl2>indexEl1)
					return -1;
			}
			else
				return 0;


		return 0;
	}

}
