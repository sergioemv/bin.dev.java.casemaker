package model;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import bi.view.lang.CMMessages;
import bi.view.report.data.EReportDataSource;

/**
 ******************************************************************************
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 *************************************************************************************/
public class ApplicationSetting {

    public ApplicationSetting() {
        ExternalApplication explorer = new ExternalApplication();
        explorer.setM_FilePath(BusinessRules.PATH_INTERNET_EXPLORER);
		explorer.setM_Name(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE);
        ExternalApplication excel = new ExternalApplication();
        excel.setM_FilePath(BusinessRules.PATH_EXCEL_APPLICATION);
        excel.setM_Name(BusinessRules.CHART_APPLICATION_DENOMINATIVE);
        explorer.setM_Param("");
        excel.setM_Param("");
        ToolVendor compuware = new ToolVendor();
   	 compuware.setM_Name(BusinessRules.COMPUWARE);
   	compuware.setM_FilePath(BusinessRules.PATH_TOOLVENDORS);
        this.m_ExternalApplications =new Vector<ExternalApplication>();
        m_ExternalApplications.addElement(explorer);
        m_ExternalApplications.addElement(excel);
        this.m_ToolVendors = new Vector<ToolVendor>(0);
        m_ToolVendors.addElement(compuware);
		createDefaultReports();
    }

    @Deprecated
    //used only for backward compatibility
    public String getM_Language(){
            return m_Language;
        }


    public String getM_UILookAndFeel(){
            return m_UILookAndFeel;
        }

    public void setM_UILookAndFeel(String m_UILookAndFeel){
            this.m_UILookAndFeel = m_UILookAndFeel;
        }

    public String getM_Version(){ return m_Version; }

    public void setM_Version(String m_Version){ this.m_Version = m_Version; }

    public Date getM_TimeStamp(){ return m_TimeStamp; }

    public void setM_TimeStamp(Date m_TimeStamp){ this.m_TimeStamp = m_TimeStamp; }
//fcastro_24062004_begin
    public Vector<ExternalApplication> getM_ExternalApplications(){
    	if (m_ExternalApplications==null || m_ExternalApplications.size()==0){
    	      ExternalApplication explorer = new ExternalApplication(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE,BusinessRules.PATH_INTERNET_EXPLORER,"");//linea modificada
    	      ExternalApplication excel = new ExternalApplication(BusinessRules.CHART_APPLICATION_DENOMINATIVE,BusinessRules.PATH_EXCEL_APPLICATION,"");//linea Modificada
    	      m_ExternalApplications =new Vector();
    	      m_ExternalApplications.addElement(explorer);
    	      m_ExternalApplications.addElement(excel);
    	}
            return m_ExternalApplications;
        }

    public void setM_ExternalApplications(Vector<ExternalApplication> m_ExternalApplications){
            this.m_ExternalApplications = m_ExternalApplications;
        }


    //My add..
    public Vector getM_ToolVendors(){
        return m_ToolVendors;
    }

    public void setM_ToolVendors(Vector<ToolVendor> m_toolVendors){
        this.m_ToolVendors = m_toolVendors;
     //   toolVendorManager.setToolVendor(m_ToolVendors);
    }



    //My adds end

    public int getM_MaxNumOfCombinations(){
            return m_MaxNumOfCombinations;
        }

    public void setM_MaxNumOfCombinations(int m_MaxNumOfCombinations){
            this.m_MaxNumOfCombinations = m_MaxNumOfCombinations;
        }

    public int getM_MaxNumOfPositiveTestCases(){
            return m_MaxNumOfPositiveTestCases;
        }

    public void setM_MaxNumOfPositiveTestCases(int m_MaxNumOfPositiveTestCases){
            this.m_MaxNumOfPositiveTestCases = m_MaxNumOfPositiveTestCases;
        }

    public int getM_MaxNumOfNegativeTestCases(){
            return m_MaxNumOfNegativeTestCases;
        }

    public void setM_MaxNumOfNegativeTestCases(int m_MaxNumOfNegativeTestCases){
            this.m_MaxNumOfNegativeTestCases = m_MaxNumOfNegativeTestCases;
        }

    public int getM_MaxNumOfFaultyTestCases(){
            return m_MaxNumOfFaultyTestCases;
        }

    public void setM_MaxNumOfFaultyTestCases(int m_MaxNumOfFaultyTestCases){
            this.m_MaxNumOfFaultyTestCases = m_MaxNumOfFaultyTestCases;
        }

//fcastro_24062004_end
//  hcanedo 26_07_2004_Begin
  //  public ExternalXSLTReportFormat getM_ExternalXSLTReportFormatOld(){ return m_ExternalXSLTReportFormat; }

    //public void setM_ExternalXSLTReportFormatOld(ExternalXSLTReportFormat p_ExternalXSLTReportFormat){ this.m_ExternalXSLTReportFormat = p_ExternalXSLTReportFormat; }


    public Vector<TestCaseExternalReports> getM_ExternalXSLTReportFormat(){

    	Vector externalXSLTReportFormatnew= new Vector<TestCaseExternalReports>(0);

    		if (m_ExternalXSLTReportFormat == null){

    			externalXSLTReportFormatnew = new Vector<TestCaseExternalReports>(0);
    		}
    		else{
    			m_ExternalXSLTReportFormat.correctFilePathDefaultXSLTReportFormat();
        		int numberOfExternalReports = m_ExternalXSLTReportFormat.getM_Name().size();

        		for (int i=0; i<numberOfExternalReports;i++){
        			if (!m_ExternalXSLTReportFormat.getM_Name().elementAt(i).equals("CSV")){
        				TestCaseExternalReports testdataSetReportsFormat = new TestCaseExternalReports(m_ExternalXSLTReportFormat.getM_Name().elementAt(i).toString(),m_ExternalXSLTReportFormat.getM_FilePath().elementAt(i).toString(),
        						m_ExternalXSLTReportFormat.getM_Extension().elementAt(i).toString(),EReportDataSource.REPORTDS_OLD_TESTDATASET.name(),BusinessRules.CHART_APPLICATION_DENOMINATIVE);
        				if (existTestDataSetReport(testdataSetReportsFormat)){
        					m_ExternalXSLTReportFormat = null;
        					return null;
        				}

        				externalXSLTReportFormatnew.addElement(testdataSetReportsFormat);
        			}

        		}
    		}

    	m_ExternalXSLTReportFormat = null;

    	return externalXSLTReportFormatnew;

    }




    public void setTestCaseReports(Vector<TestCaseExternalReports> p_TestCaseReport){
    	m_TestCaseReports=p_TestCaseReport;
    }
    public Vector<TestCaseExternalReports> getTestCaseReports(){
    	return m_TestCaseReports;
    }


    //Ccastedo begin
    public ToolVendor getM_ToolVendor(){ return m_ToolVendor; }

    public void setM_ToolVendor(ToolVendor m_ToolVendor){ this.m_ToolVendor = m_ToolVendor; }
    //Ccastedo end

    @Deprecated
    //only for backward compatibility
    private transient String m_Language;
    private String m_UILookAndFeel = BusinessRules.UILOOKANDFEEL_WINDOWS;
    private String m_Version = BusinessRules.SESSION_FILE_VERSION;
    private Date m_TimeStamp = new Date();
    //hcanedo 26_07_2004_Begin
    /**
     *@link aggregationByValue
     *@associates <{ExternalXSLTReportFormat}>
     * @clientCardinality 1
     * @supplierCardinality 0..*
     */
    private ToolVendor m_ToolVendor= new ToolVendor();

    /**
    *@deprecated
    *m_TestCaseReports have all the reports now, it has to be used instead m_ExternalXSLTReportFormat
    */
    private ExternalXSLTReportFormat m_ExternalXSLTReportFormat = new ExternalXSLTReportFormat();
	//hcanedo 26_07_2004_End
    private Vector<TestCaseExternalReports> m_TestCaseReports= new Vector<TestCaseExternalReports>(0);

  //  private Vector<TestCaseExternalReports> m_ExternalXSLTReportFormatnew= new Vector<TestCaseExternalReports>(0);

    private Vector<ExternalApplication> m_ExternalApplications = new Vector<ExternalApplication>(0);

    private Vector<ToolVendor> m_ToolVendors = new Vector<ToolVendor>(0);


    private int m_MaxNumOfCombinations = BusinessRules.MIN_NUMBER_OF_COMBINATIONS;
    private int m_MaxNumOfPositiveTestCases =  BusinessRules.MIN_NUMBER_OF_POSITIVE_TEST_CASES;
    private int m_MaxNumOfNegativeTestCases =  BusinessRules.MIN_NUMBER_OF_NEGATIVE_TEST_CASES;
    private int m_MaxNumOfFaultyTestCases = BusinessRules.MIN_NUMBER_OF_FAULTY_TEST_CASES;

	public Vector<TestCaseExternalReports> getTestCaseReports(int test_case_list_type) {
		Vector<TestCaseExternalReports> reports = new Vector<TestCaseExternalReports>(0);
		reports.addAll(this.getTestCaseReports());
		for (Iterator i = this.getTestCaseReports().iterator();i.hasNext();)
		{
			TestCaseExternalReports report = (TestCaseExternalReports) i.next();
			String s1 = report.getReportDataSourceName();
			String s2 = Arrays.asList(EReportDataSource.values()).get(test_case_list_type).name();
			if (!(report.getReportDataSourceName().equals(Arrays.asList(EReportDataSource.values()).get(test_case_list_type).name())))
				reports.remove(report);
		}
		return reports;
	}

	/**
	 *  Create the default "packaged" reports for casemaker
	 */
	public  void createDefaultReports()
	{

	//save the user report formats
	Vector<TestCaseExternalReports> userReports = new Vector<TestCaseExternalReports>();
	if (m_TestCaseReports!=null)
		for (TestCaseExternalReports report : m_TestCaseReports)
			if (!report.isProtectedReport() && !report.isDefaultReport()){
				userReports.add(report);
			}

	m_TestCaseReports= new Vector<TestCaseExternalReports>(0);
	m_TestCaseReports.addElement( new TestCaseExternalReports("REPORT_TESTCASE_LIST_ORDERED_BY_NUMBER",BusinessRules.TESTCASELIST1_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_HTML,EReportDataSource.REPORTDS_TESTCASE_LIST_1.name(),
			BusinessRules.INTERNET_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement( new TestCaseExternalReports("REPORT_TESTCASE_LIST_ORDERED_BY_RISK_LEVELS",BusinessRules.TESTCASELIST2_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_HTML,EReportDataSource.REPORTDS_TESTCASE_LIST_1.name(),
			BusinessRules.INTERNET_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement( new TestCaseExternalReports("REPORT_ERROR_LIST",BusinessRules.ERROR_LIST_DEFAULT_FILEPATH, BusinessRules.FORMAT_HTML,EReportDataSource.REPORTDS_ERROR_LIST_1.name(),
			BusinessRules.INTERNET_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement(new TestCaseExternalReports("REPORT_TESTCASE_LIST_EXCEL_1",BusinessRules.TESTCASELIST1EXCEL_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_EXCEL,EReportDataSource.REPORTDS_TESTCASE_LIST_2.name(),
			BusinessRules.CHART_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement(new TestCaseExternalReports("REPORT_TESTCASE_LIST_WORFLOW",BusinessRules.TESTCASELISTWORKFLOW_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_HTML,EReportDataSource.REPORTDS_TESTCASE_LIST_2.name(),
			BusinessRules.INTERNET_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement(new TestCaseExternalReports("REPORT_TESTCASE_LIST_EXCEL_2",BusinessRules.TESTCASELIST2EXCEL_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_EXCEL,EReportDataSource.REPORTDS_TESTCASE_LIST_2.name(),
			BusinessRules.CHART_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement(new TestCaseExternalReports("REPORT_TESTCASE_LIST_EXCEL_2_MOD1",BusinessRules.TESTCASELIST2MOD1EXCEL_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_EXCEL,EReportDataSource.REPORTDS_TESTCASE_LIST_2.name(),
			BusinessRules.CHART_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement( new TestCaseExternalReports("REPORT_TESTCASE_EXPORT_TO_CSV",BusinessRules.TESTCASEEXPORTCSV_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_CSV,EReportDataSource.REPORTDS_TESTCASE_LIST_2.name(),
			BusinessRules.INTERNET_APPLICATION_DENOMINATIVE));
	m_TestCaseReports.addElement(new TestCaseExternalReports("REPORT_DEPENDENCY_COMBINATION_LIST",BusinessRules.DEPENDENCYCOMB_XSLT_DEFAULT_FILEPATH, BusinessRules.FORMAT_EXCEL,EReportDataSource.REPORTDS_DEPENDENCY_COMBINATION.name(),
			BusinessRules.CHART_APPLICATION_DENOMINATIVE));
	String filepath = (BusinessRules.TESTDATASET_XSLT_DEFAULT_FILEPATH).replace('\\','/');
	m_TestCaseReports.addElement( new TestCaseExternalReports("CSV2",filepath, BusinessRules.FORMAT_CSV,EReportDataSource.REPORTDS_TESTDATASET.name(),
			BusinessRules.CHART_APPLICATION_DENOMINATIVE));
	filepath = (BusinessRules.TESTDATASET_XSLT_DEFAULT_DORMA_FILEPATH).replace('\\','/');
	m_TestCaseReports.addElement( new TestCaseExternalReports("REPORT_TESTDATA_DORMA_CSV",filepath, BusinessRules.FORMAT_CSV,EReportDataSource.REPORTDS_TESTDATA_DORMA.name(),
			BusinessRules.CHART_APPLICATION_DENOMINATIVE));

	for (TestCaseExternalReports report : m_TestCaseReports)
		report.setProtectedReport(true);
	//here the unprotected default reports
	TestCaseExternalReports testcaseExternalReport= new TestCaseExternalReports(CMMessages.getString("REPORT_TESTCASE_EXPORT_TO_QADIRECTORXML"),BusinessRules.TESTCASEEXPORTQADIRECTORXML_XSLT_DEFAULT_FILEPATH.replace('\\', '/'), BusinessRules.FORMAT_XML,EReportDataSource.REPORTDS_TESTCASE_LIST_2.name(),
			BusinessRules.INTERNET_APPLICATION_DENOMINATIVE);
	testcaseExternalReport.setDefaultReport(true);
	m_TestCaseReports.addElement(testcaseExternalReport );

	for (int i=0;i<userReports.size();i++){
		TestCaseExternalReports tcExternalReport = (TestCaseExternalReports)userReports.elementAt(i);
		if (tcExternalReport.getName().equals(testcaseExternalReport.getName()))
			userReports.removeElementAt(i);
	}
	m_TestCaseReports.addAll(userReports);
	Vector<TestCaseExternalReports> userTestDataSetReports = new Vector<TestCaseExternalReports>();
	Vector<TestCaseExternalReports> externalXSLTReportFormat = getM_ExternalXSLTReportFormat();
	if (externalXSLTReportFormat!=null){
		for (TestCaseExternalReports report : externalXSLTReportFormat)
			if (!report.isProtectedReport()){

					/*if (report.getReportDataSourceId() == -1){
						report.setReportDataSourceId(4);
					}*/
					userTestDataSetReports.add(report);


			}
	}
	if (userTestDataSetReports.size()>0)
		m_TestCaseReports.addAll(userTestDataSetReports);



	}

	public TestCaseExternalReports getTestCaseReportByName(String string) {
		for (TestCaseExternalReports report :  getTestCaseReports())
			if (report.getName().equalsIgnoreCase(string))
				return report;

		return null;
	}

	private boolean existTestDataSetReport(TestCaseExternalReports p_report){
		for (TestCaseExternalReports report : m_TestCaseReports)
			if (report.getName().equals(p_report.getName()))
				return true;
		return false;
	}

	public Vector<EReportDataSource> getM_ReportDataSources() {
			Vector<EReportDataSource> reportDataSources = new Vector<EReportDataSource>(0);
			reportDataSources.add(EReportDataSource.REPORTDS_TESTCASE_LIST_1);
			reportDataSources.add(EReportDataSource.REPORTDS_TESTCASE_LIST_2);
			reportDataSources.add(EReportDataSource.REPORTDS_ERROR_LIST_1);
			reportDataSources.add(EReportDataSource.REPORTDS_DEPENDENCY_COMBINATION);
			reportDataSources.add(EReportDataSource.REPORTDS_TESTDATASET);
			reportDataSources.add(EReportDataSource.REPORTDS_OLD_TESTDATASET);
			reportDataSources.add(EReportDataSource.REPORTDS_TESTDATA_DORMA);
		return reportDataSources;
	}

	public ExternalApplication getM_ExternalApplicationByName(String defaultApp) {
		for (ExternalApplication app :   getM_ExternalApplications())
			if (app.getM_Name().equalsIgnoreCase(defaultApp))
				return app;

		return null;
	}

	//Ccastedo begins 16-02-06
	private float m_MaxSizeofXMLFiles =  (BusinessRules.MIN_SIZE_OF_XML_FILE  + 18);

	 public float getM_MaxSizeofXMLFiles(){
		 if (m_MaxSizeofXMLFiles<2){
			 return (BusinessRules.MIN_SIZE_OF_XML_FILE + 18);
		 }
		 else
			 return m_MaxSizeofXMLFiles;
     }

	 public void setM_MaxSizeofXMLFiles (float p_MaxSizeofXMLFiles){
         this.m_MaxSizeofXMLFiles = p_MaxSizeofXMLFiles;
     }

}
