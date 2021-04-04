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
 * This comparator class, compare two <code>TestData</code> according to <code>TestData</code> risk level.
 * @author Franz Nava
 * @version Revision: Date: 04-03-2005 01:08:54 PM
 */
public class TestDataRiskComparator implements Comparator {
    
    /**
     * Holds the sorter order, i. e., if it is ascendent or descendent.
     */
    private boolean order = false;

    /**
     * @param order
     */
    public TestDataRiskComparator(boolean order) {
        this.order = order;
    }

    /* (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    public int compare(Object testData0, Object testData1) {
        int risk0 = ((TestData) testData0).getM_RiskLevel();
        int risk1 = ((TestData) testData1).getM_RiskLevel();
        
        if (order) {
            return risk1 - risk0;
        } else {
            return risk0 - risk1;
        }
        
    }

}
