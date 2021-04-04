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
 * This comparator class, compare two <code>TestData</code> according to <code>TestData</code> set.
 * @author Franz Nava
 * @version Revision: Date: 04-03-2005 01:29:33 PM
 */
public class TestDataSetComparator implements Comparator {
    
    /**
     * Holds the sorter order, i. e., if it is ascendent or descendent.
     */
    private boolean order = false;

    /**
     * @param order
     */
    public TestDataSetComparator(boolean order) {
        this.order = order;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object testData0, Object testData1) {
        boolean set0 = ((TestData) testData0).isSet();
        boolean set1 = ((TestData) testData1).isSet();
        
        if (order) {
            return (set0 == set1)? 0: (!set1?-1:1);
        } else {
            return (set0 == set1)? 0: (!set0?-1:1);
        }
    }

}
