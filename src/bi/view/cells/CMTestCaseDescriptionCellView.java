/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

public class CMTestCaseDescriptionCellView {
    public CMTestCaseDescriptionCellView() {
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
}
