package model;

import java.util.Arrays;

import bi.view.lang.CMMessages;
import bi.view.report.data.EReportDataSource;

public class TestCaseExternalReports {



	private String name;
	private String filePath;	
	private String extension;
	/**
	    *@deprecated	    
	*/
	private int reportDataSourceId  = 0;
	private String defaultApp = BusinessRules.INTERNET_APPLICATION_DENOMINATIVE;
	private boolean protectedReport;
	private boolean defaultReport;
	private String reportDataSourceName = "";
	
	public TestCaseExternalReports(String p_name, String p_FilePath, String p_Extension, String type, String app){
	//	contentFolder = p_contentFolder;
		name=p_name;
		filePath=p_FilePath;
		extension=p_Extension;
		protectedReport= false;
		defaultReport = false;
		//this.reportDataSourceId = type;
		reportDataSourceName = type;
		this.defaultApp = app;
	}
	public void setName(String p_Name){
		name=p_Name;
	}
	public String getName(){
		if (protectedReport)
			return CMMessages.getString(name);
		else
			return name;
	}

	public void setFilePath(String p_FilePath){
		filePath=p_FilePath;
	}
	public String getFilePath(){
		//if is a protected report then always is on the installation directory under /res
		/*if (isProtectedReport()){
			if (contentFolder == null)
				contentFolder = "\\res\\TestCaseReports\\";
			return (CMApplication.getInstallationDirectory().replace('\\', '/')+contentFolder+filePath.substring(filePath.lastIndexOf('/')+1,filePath.length())).replace('\\','/');
		}			
		else*/
			return filePath.replace('\\', '/');
	}

	public void setExtension(String p_Extension){
		extension=p_Extension;
	}
	public String getExtension(){
		return extension;
	}

	public void setProtectedReport(boolean protect){
		protectedReport=protect;
	}
	public boolean isProtectedReport(){
		return protectedReport;
	}
	public String toString(){
		return getName();
	}
	private int getReportDataSourceId() {
		return reportDataSourceId;
	}
	/*public void setReportDataSourceId(int type) {
		this.reportDataSourceId = type;
	}*/
	public String getDefaultApp() {
		//if no default application is assigned
		if (defaultApp==null)
			defaultApp = new String(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE);
		if (defaultApp.equalsIgnoreCase(""))
			defaultApp = BusinessRules.INTERNET_APPLICATION_DENOMINATIVE;
//		if ((!defaultApp.equals(BusinessRules.INTERNET_APPLICATION_DENOMINATIVE)) && (!defaultApp.equals(BusinessRules.CHART_APPLICATION_DENOMINATIVE)))
//				defaultApp = BusinessRules.INTERNET_APPLICATION_DENOMINATIVE;
		return defaultApp;
	}
	public void setDefaultApp(String defaultApp) {
		this.defaultApp = defaultApp;
	}

	
	
	public EReportDataSource getReporDataSourceType(){
		/*if (reportDataSourceName == null)
			return Arrays.asList(EReportDataSource.values()).get(reportDataSourceId);
		else*/
			return EReportDataSource.valueOf(getReportDataSourceName());
		
	}

	public boolean isDefaultReport() {
		return defaultReport;
	}
	public void setDefaultReport(boolean defaultReport) {
		this.defaultReport = defaultReport;
	}
	public String getReportDataSourceName() {
		if (reportDataSourceName==null)
			reportDataSourceName = (Arrays.asList(EReportDataSource.values()).get(reportDataSourceId)).name();

		return reportDataSourceName;
	}
	public void setReportDataSourceName(String reportDataSourceName) {
		this.reportDataSourceName = reportDataSourceName;
	}

}
