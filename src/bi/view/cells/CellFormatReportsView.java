package bi.view.cells;


import java.util.Vector;

import model.TestCaseExternalReports;


public class CellFormatReportsView extends Vector {
    public CellFormatReportsView(TestCaseExternalReports p_TestDataSetReport) {
      super(0);      
      m_TestDataSetReport=p_TestDataSetReport;
     /* this.m_name = m_TestDataSetReport.getName();
      this.m_path = m_TestDataSetReport.getFilePath();
      this.m_Ext= m_TestDataSetReport.getExtension();*/
    }

   /* public String getM_Name(){
            return m_name;
        }

    public void setM_Name(String m_name){
            this.m_name = m_name;
        }

    public String getM_Ext(){ return m_Ext; }

    public void setM_Ext(String m_Ext){ this.m_Ext = m_Ext; }

    public String getM_Path(){
            return m_path;
        }

    public void setM_Path(String m_path){
            this.m_path = m_path;
        }*///ccastedo 07.11.06

    public CellFormatReportsNameView getM_CellFormatReportsNameView(){
            return m_CellFormatReportsNameView;
        }

    public void setM_CellFormatReportsNameView(CellFormatReportsNameView m_CellFormatReportsNameView){
            this.m_CellFormatReportsNameView = m_CellFormatReportsNameView;
        }

    public CellFormatReportsPathView getM_CellFormatReportsPathView(){
            return m_CellFormatReportsPathView;
        }

    public void setM_CellFormatReportsPathView(CellFormatReportsPathView m_CellFormatReportsPathView){
            this.m_CellFormatReportsPathView = m_CellFormatReportsPathView;
        }

    public CellFormatReportsExtensionView getM_CellFormatReportsExtensionView(){ return m_CellFormatReportsExtensionView; }

    public void setM_CellFormatReportsExtensionView(CellFormatReportsExtensionView m_CellFormatReportsExtensionView){ this.m_CellFormatReportsExtensionView = m_CellFormatReportsExtensionView; }

    //ccastedo begins 07.11.06
    public TestCaseExternalReports getTestDataSetReport(){
    	return m_TestDataSetReport;
    }
    
    public void setTestDataSetReport(TestCaseExternalReports p_TestDataSetReport){
    	m_TestDataSetReport=p_TestDataSetReport;
    }
    private TestCaseExternalReports m_TestDataSetReport;
    //ccastedo ends 07.11.06
    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @link aggregationByValue
     * @directed 
     */
    private CellFormatReportsNameView m_CellFormatReportsNameView;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed
     * @link aggregationByValue 
     */
    private CellFormatReportsPathView m_CellFormatReportsPathView;

    /**
     * @clientCardinality 1
     * @supplierCardinality 1
     * @directed 
     */
  /*  private String m_name;
    private String m_path;*/

    /**
     * @link aggregationByValue
     * @clientCardinality 1
     * @supplierCardinality 1 
     */
    private CellFormatReportsExtensionView m_CellFormatReportsExtensionView;
   // private String m_Ext;
    
}

