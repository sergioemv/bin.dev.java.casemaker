/* Generated by Together */

package  bi.view.cells;

import java.util.Vector;
public class CMReportOutputView extends Vector {
    public CMReportOutputView() {
        	super(0);
    }

    public CMOpenWithCell getM_CMOpenWithCell(){ return m_CMOpenWithCell; }

    public void setM_CMOpenWithCell(CMOpenWithCell m_CMOpenWithCell){ this.m_CMOpenWithCell = m_CMOpenWithCell; }

    public CMReportNameCell getM_CMReportNameCell(){ return m_CMReportNameCell; }

    public void setM_CMReportNameCell(CMReportNameCell m_CMReportNameCell){ this.m_CMReportNameCell = m_CMReportNameCell; }

    public CMDateCell getM_CMDateCell(){ return m_CMDateCell; }

    public void setM_CMDateCell(CMDateCell m_CMDateCell){ this.m_CMDateCell = m_CMDateCell; }

    public CMFilePathCell getM_CMFilePathCell(){ return m_CMFilePathCell; }

    public void setM_CMFilePathCell(CMFilePathCell m_CMFilePathCell){ this.m_CMFilePathCell = m_CMFilePathCell; }
//hcanedo_21_09_2004_begin
    public CMReportParameterCell getM_CMReportParameterCell(){ return m_CMReportParameterCell; }

    public void setM_CMReportParameterCell(CMReportParameterCell m_CMReportParameterCell){ this.m_CMReportParameterCell = m_CMReportParameterCell; }
//hcanedo_21_09_2004_end
    /**
     * @link aggregationByValue 
     */
    private CMOpenWithCell m_CMOpenWithCell;

    /**
     * @link aggregationByValue 
     */
    private CMReportNameCell m_CMReportNameCell;

    /**
     * @link aggregationByValue 
     */
    private CMDateCell m_CMDateCell;

    /**
     * @link aggregationByValue 
     */
    private CMFilePathCell m_CMFilePathCell;
//hcanedo_21_09_2004_begin

    /**
     * @link aggregationByValue 
     */
    private CMReportParameterCell m_CMReportParameterCell;
//hcanedo_21_09_2004_end
}
