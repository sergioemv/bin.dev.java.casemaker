/*
 * Revision:
 * Created: 15-02-2005
 * 
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.testdataviews.grid;

import bi.view.lang.CMMessages;

import com.eliad.model.AbstractGridModel;

/**
 * A especific column header to a grid.
 * @author Franz Nava
 * @version Revision: Date: 15-02-2005 06:19:09 PM
 */
public final class ColumnHeaderModel extends AbstractGridModel {
    
    public static final int TESTDATA_NAME = 0;
    public static final int TESTDATA_LEVEL = 1;
    public static final int TESTDATA_SET = 2;
    //svonborries_29122005_begin
    public static final int TESTDATA_TESTCASE = 3;
    public static final int COLUMNS = 4;
    //svonborries_29122005_end
    /**
     * @param grid
     */
    public ColumnHeaderModel() {
    }

    final static String[] names = {CMMessages.getString("TESTDATA_TESTDATA"), CMMessages.getString("TESTDATA_RISKLEVEL"), CMMessages.getString("TESTDATA_SET"),CMMessages.getString("TESTDATA_TESTCASE")}; //svonborries_29122005   

    /* (non-Javadoc)
     * @see com.eliad.model.GridModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int column) {
        return names[column];
    }

    /* (non-Javadoc)
     * @see com.eliad.model.GridModel#getColumnCount()
     */
    public int getColumnCount() {
        return COLUMNS;
    }

    /* (non-Javadoc)
     * @see com.eliad.model.GridModel#getRowCount()
     */
    public int getRowCount() {
        return 1;
    }

}
