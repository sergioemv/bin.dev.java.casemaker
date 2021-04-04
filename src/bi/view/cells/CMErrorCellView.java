/* Developed by BUSINESS SOFTWARE INNOVATIONS.  .. Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved..*/

package  bi.view.cells;



import model.CMError;
import java.util.Vector;

public class CMErrorCellView extends Vector {
    public CMErrorCellView(CMError p_CMError) {
      super(0);
      this.m_CMError = p_CMError;
    }

    public CMError getM_CMError(){
            return m_CMError;
        }

    public void setM_CMError(CMError m_CMError){
            this.m_CMError = m_CMError;
        }

    public CMErrorNameCellView getM_CMErrorNameCellView(){
            return m_CMErrorNameCellView;
        }

    public void setM_CMErrorNameCellView(CMErrorNameCellView m_CMErrorNameCellView){
            this.m_CMErrorNameCellView = m_CMErrorNameCellView;
        }

    public CMErrorDescriptionCellView getM_CMErrorDescriptionCellView(){
            return m_CMErrorDescriptionCellView;
        }

    public void setM_CMErrorDescriptionCellView(CMErrorDescriptionCellView m_CMErrorDescriptionCellView){
            this.m_CMErrorDescriptionCellView = m_CMErrorDescriptionCellView;
        }

    public CMErrorStateCellView getM_CMErrorStateCellView(){
            return m_CMErrorStateCellView;
        }

    public void setM_CMErrorStateCellView(CMErrorStateCellView m_CMErrorStateCellView){
            this.m_CMErrorStateCellView = m_CMErrorStateCellView;
        }

    public CMErrorErrorClassCellView getM_CMErrorErrorClassCellView(){
            return m_CMErrorErrorClassCellView;
        }

    public void setM_CMErrorErrorClassCellView(CMErrorErrorClassCellView m_CMErrorErrorClassCellView){
            this.m_CMErrorErrorClassCellView = m_CMErrorErrorClassCellView;
        }


    public CMErrorPriorityCellView getM_CMErrorPriorityCellView(){
            return m_CMErrorPriorityCellView;
        }

    public void setM_CMErrorPriorityCellView(CMErrorPriorityCellView m_CMErrorPriorityCellView){
            this.m_CMErrorPriorityCellView = m_CMErrorPriorityCellView;
        }

    public CMErrorIssueDateCellView getM_CMErrorIssueDateCellView(){
            return m_CMErrorIssueDateCellView;
        }

    public void setM_CMErrorIssueDateCellView(CMErrorIssueDateCellView m_CMErrorIssueDateCellView){
            this.m_CMErrorIssueDateCellView = m_CMErrorIssueDateCellView;
        }

    public CMErrorIssuedByCellView getM_CMErrorIssuedByCellView(){
            return m_CMErrorIssuedByCellView;
        }

    public void setM_CMErrorIssuedByCellView(CMErrorIssuedByCellView m_CMErrorIssuedByCellView){
            this.m_CMErrorIssuedByCellView = m_CMErrorIssuedByCellView;
        }

    public CMErrorClosingDateCellView getM_CMErrorClosingDateCellView(){
            return m_CMErrorClosingDateCellView;
        }

    public void setM_CMErrorClosingDateCellView(CMErrorClosingDateCellView m_CMErrorClosingDateCellView){
            this.m_CMErrorClosingDateCellView = m_CMErrorClosingDateCellView;
        }

    public CMErrorClosedByCellView getM_CMErrorClosedByCellView(){
            return m_CMErrorClosedByCellView;
        }

    public void setM_CMErrorClosedByCellView(CMErrorClosedByCellView m_CMErrorClosedByCellView){
            this.m_CMErrorClosedByCellView = m_CMErrorClosedByCellView;
        }
//HCanedo_30112004_Begin
    public CMErrorRiskLevelCellView getM_CMErrorRiskLevelCellView(){ return m_CMErrorRiskLevelCellView; }

    public void setM_CMErrorRiskLevelCellView(CMErrorRiskLevelCellView m_CMErrorRiskLevelCellView){ this.m_CMErrorRiskLevelCellView = m_CMErrorRiskLevelCellView; }

    public CMErrorAssignDateCellView getM_CMErrorAssignDateCellView(){ return m_CMErrorAssignDateCellView; }

    public void setM_CMErrorAssignDateCellView(CMErrorAssignDateCellView m_CMErrorAssignDateCellView){ this.m_CMErrorAssignDateCellView = m_CMErrorAssignDateCellView; }

    public CMErrorAssignToCellView getM_CMErrorAssignToCellView(){ return m_CMErrorAssignToCellView; }

    public void setM_CMErrorAssignToCellView(CMErrorAssignToCellView m_CMErrorAssignToCellView){ this.m_CMErrorAssignToCellView = m_CMErrorAssignToCellView; }
//HCanedo_30112004_End
   public CMErrorTestCasesCellView getM_CMErrorTestCasesCellView(){
            return m_CMErrorTestCasesCellView;
        }

    public void setM_CMErrorTestCasesCellView(CMErrorTestCasesCellView m_CMErrorTestCasesCellView){
            this.m_CMErrorTestCasesCellView = m_CMErrorTestCasesCellView;
        }

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregationByValue
     * @directed 
     */
    private CMErrorNameCellView m_CMErrorNameCellView;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     * @link aggregationByValue 
     */
    private CMErrorDescriptionCellView m_CMErrorDescriptionCellView;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed 
     */
    private CMError m_CMError;

    /**
     * @link aggregationByValue 
     */
    private CMErrorStateCellView m_CMErrorStateCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorErrorClassCellView m_CMErrorErrorClassCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorPriorityCellView m_CMErrorPriorityCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorIssueDateCellView m_CMErrorIssueDateCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorIssuedByCellView m_CMErrorIssuedByCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorClosingDateCellView m_CMErrorClosingDateCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorClosedByCellView m_CMErrorClosedByCellView;
//HCanedo_30112004_Begin
    /**
     * @link aggregationByValue 
     */
    private CMErrorRiskLevelCellView m_CMErrorRiskLevelCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorAssignDateCellView m_CMErrorAssignDateCellView;

    /**
     * @link aggregationByValue 
     */
    private CMErrorAssignToCellView m_CMErrorAssignToCellView;

//HCanedo_30112004_End

    /**
     * @link aggregationByValue 
     */
    private CMErrorTestCasesCellView m_CMErrorTestCasesCellView;
}
