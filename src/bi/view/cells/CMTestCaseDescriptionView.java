/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;

import model.TestCase;
import java.util.Vector;

public class CMTestCaseDescriptionView extends Vector {
    public CMTestCaseDescriptionView(TestCase p_TestCase) {
      super(0);
      this.m_TestCase = p_TestCase;
    }

    public CMTestCaseDescriptionCellView getM_CMTestCaseDescriptionCellView(){
            return m_CMTestCaseDescriptionCellView;
        }

    public void setM_CMTestCaseDescriptionCellView(CMTestCaseDescriptionCellView m_CMTestCaseDescriptionCellView){
            this.m_CMTestCaseDescriptionCellView = m_CMTestCaseDescriptionCellView;
        }

    public CMTestCaseNameView getM_CMTestCaseNameView(){
            return m_CMTestCaseNameView;
        }

    public void setM_CMTestCaseNameView(CMTestCaseNameView m_CMTestCaseNameView){
            this.m_CMTestCaseNameView = m_CMTestCaseNameView;
        }

    public TestCase getM_TestCase(){
            return m_TestCase;
        }

    public void setM_TestCase(TestCase m_TestCase){
            this.m_TestCase = m_TestCase;
        }

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMTestCaseNameView m_CMTestCaseNameView;

    /**
     * @link aggregationByValue
     * @directed 
     */
    private CMTestCaseDescriptionCellView m_CMTestCaseDescriptionCellView;

    /**
     * @directed 
     */
    private TestCase m_TestCase;
}
