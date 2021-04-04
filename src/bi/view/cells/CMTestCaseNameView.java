/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.TestCase;

import com.eliad.swing.JSmartGrid;

public class CMTestCaseNameView extends CMBaseCell {
    

    public CMTestCaseNameView(JSmartGrid p_grid, Object p_model) {
		super(p_grid, p_model);
	}

	public CMTestCaseDescriptionView getM_CMTestCaseDescriptionView(){
            return m_CMTestCaseDescriptionView;
        }

    public void setM_CMTestCaseDescriptionView(CMTestCaseDescriptionView m_CMTestCaseDescriptionView){
            this.m_CMTestCaseDescriptionView = m_CMTestCaseDescriptionView;
        }

    /**
     * @directed 
     */
    private CMTestCaseDescriptionView m_CMTestCaseDescriptionView;
    
    @Override
    public String toString() {
    	
    	return ((TestCase)getModel()).getName()+((TestCase)getModel()).getStateName();
    }
}
