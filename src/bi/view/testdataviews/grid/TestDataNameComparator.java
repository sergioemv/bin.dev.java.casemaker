/*
 * Revision:
 * Created: 04-03-2005
 * 
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.testdataviews.grid;

import java.util.Comparator;

import model.TestData;

/**
 * This comparator class, compare two <code>TestData</code> according to <code>TestData</code> names. 
 * @author Franz Nava
 * @version Revision: Date: 04-03-2005 12:50:59 PM
 */
public class TestDataNameComparator implements Comparator {

    /**
     * Holds the sorter order, i. e., if it is ascendent or descendent.
     */
    private boolean order = false; 
    /**
     * @param order
     */
    public TestDataNameComparator(boolean order) {
        this.order = order;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object testData0, Object testData1) {
        String name0 = ((TestData) testData0).getName();
        String name1 = ((TestData) testData1).getName();
        
        if (order) {
            return name1.compareTo(name0);
        } else {
            return name0.compareTo(name1);
        }                
    }

}
