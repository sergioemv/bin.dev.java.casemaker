/*
 * Revision:
 * Created: 16-02-2005
 * 
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *
 */
package bi.view.testdataviews.grid;

import java.util.ArrayList;
import java.util.List;

import model.TestData;

import com.eliad.model.AbstractGridModel;

/**
 * @author Franz Nava
 * @version Revision: Date: 16-02-2005 09:43:01 AM
 */
public class GridModelTestData extends AbstractGridModel {
    
    private List dataModel = null;
    
    /**
     * Construct a model for a grid
     * @param dataModel, a list with the data
     */
    public GridModelTestData(List dataModel) {
        this.dataModel = dataModel;
    }
    /* (non-Javadoc)
     * @see com.eliad.model.GridModel#isCellEditable(int, int)
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    /* (non-Javadoc)
     * @see com.eliad.model.GridModel#getValueAt(int, int)
     */
    public Object getValueAt(int row, int column) {
  		if ( (row < 0) || (column < 0) ) {
    		return null;
  		}
  		
  		TestData testData = (TestData) dataModel.get(row);
  		if (column == ColumnHeaderModel.TESTDATA_NAME) {
            return testData.getName();
        } else if (column == ColumnHeaderModel.TESTDATA_LEVEL) {
            return testData.getM_RiskLevel() + "";
        } else if (column == ColumnHeaderModel.TESTDATA_SET) {
            return testData.isSet()?" *":"";
        } 
  		//SVONBORRIES_29122005_BEGIN
        else if (column == ColumnHeaderModel.TESTDATA_TESTCASE){
        	return testData.getM_TestCaseinTestData();
        }
  		//SVONBORRIES_29122005_END
        
        return null;
        
    }
    /* (non-Javadoc)
     * @see com.eliad.model.AbstractGridModel#getColumnCount()
     */
    public int getColumnCount() {
        return ColumnHeaderModel.COLUMNS;
    }
    /* (non-Javadoc)
     * @see com.eliad.model.AbstractGridModel#getRowCount()
     */
    public int getRowCount() {
        return dataModel.size();
    }
    /**
     * @return Returns the dataModel.
     */
    public List getDataModel() {
        return dataModel;
    }
    /**
     * @param dataModel The dataModel to set.
     */
    public void setDataModel(List dataModel) {
        this.dataModel = dataModel;
    }
    /**
     * It returns a list of elements.
     * @param selected
     * @return
     */
    public List getElements(int[] indexes) {
        List elements = new ArrayList();
        for (int i = 0; i < indexes.length; i++) {
            elements.add(dataModel.get(indexes[i]));
        }
        
        return elements;
    }
    
    /**
     * Returns the index in this model of the first occurrence of the specified element, 
     * or -1 if this list does not contain this element.
     * @param element, supposedly an element belong to the data model.
     * @return the index of this element in the data model.
     */
    public int getIndex(Object element) {
        return dataModel.indexOf(element);
    }

    
}
