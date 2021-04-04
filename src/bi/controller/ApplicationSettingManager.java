package bi.controller;

import java.util.Vector;

import bi.view.report.data.EReportDataSource;


import model.ApplicationSetting;
import model.TestCaseExternalReports;

public class ApplicationSettingManager {

	//static instance
	public static final ApplicationSettingManager INSTANCE = new ApplicationSettingManager();

	public boolean existTestCaseNameReport(String p_Name){
		Vector testCaseReports= m_ApplicationSetting.getTestCaseReports();
		for(int i =0; i<testCaseReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testCaseReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(p_Name))
				return true;
		}
		return false;
	}

	public TestCaseExternalReports registerNewTestCaseReport(String p_Name, String p_FilePath, String p_Extension,String p_type,String defaultApp){
		TestCaseExternalReports newReport = null;
		if (p_type == EReportDataSource.REPORTDS_TESTDATASET.name())
			newReport= new TestCaseExternalReports(p_Name,p_FilePath,p_Extension,p_type,defaultApp);
		else
			newReport= new TestCaseExternalReports(p_Name,p_FilePath,p_Extension,p_type,defaultApp);
		m_ApplicationSetting.getTestCaseReports().addElement(newReport);
		return newReport;
	}

	//ccastedo begins 07.11.06
	/*public void registerNewTestDataSetReport(String p_Name, String p_FilePath, String p_Extension,String defaultApp){
		TestCaseExternalReports newReport= new TestCaseExternalReports("\\res\\ExternalXSLT\\",p_Name,p_FilePath,p_Extension,-1,defaultApp);
		m_ApplicationSetting.getM_ExternalXSLTReportFormat().addElement(newReport);
	}
	public boolean existTestDataSetNameReport(String p_Name){
		Vector testDataSetReports= m_ApplicationSetting.getM_ExternalXSLTReportFormat();
		for(int i =0; i<testDataSetReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testDataSetReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(p_Name))
				return true;
		}
		return false;
	}
	public void changeNameTestDataSetReport(TestCaseExternalReports testdataSetReport, String newName){
		testdataSetReport.setName(newName);
	}

	public void changeExtensionTestDataSetReport(TestCaseExternalReports testdataSetReport, String newExtension){
		testdataSetReport.setExtension(newExtension);
	}
	public TestCaseExternalReports getTestDataSetReportByName(String p_Name){
		Vector testDataSetReports= m_ApplicationSetting.getM_ExternalXSLTReportFormat();
		for(int i =0; i<testDataSetReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testDataSetReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(p_Name))
				return report;
		}
		return null;
	}
	public String getTestDataSetReportFilePathByName(String p_Name){
		Vector testDataSetReports= m_ApplicationSetting.getM_ExternalXSLTReportFormat();
		for(int i =0; i<testDataSetReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testDataSetReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(p_Name))
				return report.getFilePath();
		}
		return null;
	}*/
	//ccastedo ends 07.11.06

	public void changeNameTestCaseReport(TestCaseExternalReports testcaseReport, String newName){
		testcaseReport.setName(newName);
	}

	public void changeExtensionTestCaseReport(TestCaseExternalReports testcaseReport, String newExtension){
		testcaseReport.setExtension(newExtension);
	}

	public void setApplicationSetting(ApplicationSetting p_ApplicationSetting){
		m_ApplicationSetting=p_ApplicationSetting;
	}

	public ApplicationSetting getApplicationSetting(){
		return m_ApplicationSetting;
	}

	public TestCaseExternalReports getTestCaseReportByName(String p_Name){
		Vector testCaseReports= m_ApplicationSetting.getTestCaseReports();
		for(int i =0; i<testCaseReports.size();i++){
			TestCaseExternalReports report= (TestCaseExternalReports)testCaseReports.elementAt(i);
			if(report.getName().equalsIgnoreCase(p_Name))
				return report;
		}
		return null;
	}
	private ApplicationSetting m_ApplicationSetting;





}
