/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.testdataviews.grid;

import java.util.Comparator;

import model.TestData;

/**
 * @author svonborries
 *
 */
public class TestDataTestCaseComparator implements Comparator {

	private boolean order = false;

	public TestDataTestCaseComparator(boolean order){
		this.order = order;
	}

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(T, T)
	 */
	public int compare(Object testData0, Object testData1) {
		String name0 = ((TestData)testData0).getM_TestCaseinTestData();
		String name1 = ((TestData)testData1).getM_TestCaseinTestData();

		if(order){
			return name1.compareTo(name0);
		}
		else{
			return name0.compareTo(name1);
			}
	}

}
