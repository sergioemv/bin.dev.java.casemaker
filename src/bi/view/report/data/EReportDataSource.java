/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.report.data;

import model.BusinessRules;
import bi.view.lang.CMMessages;

/**
 *  Enumerates all the sources for the reports
 * @author ccastedo
 *
 */
public enum EReportDataSource {

	REPORTDS_TESTCASE_LIST_1, 
	REPORTDS_TESTCASE_LIST_2, 
	REPORTDS_ERROR_LIST_1,
	REPORTDS_DEPENDENCY_COMBINATION,
	REPORTDS_TESTDATASET,
	REPORTDS_OLD_TESTDATASET,
	REPORTDS_TESTDATA_DORMA;
	
	
	private CMReportData reportData;
	
	public String getSchemaName(){
		switch (this) {
		case REPORTDS_TESTCASE_LIST_1:
			return "ReportTCList1.xsd";
		case REPORTDS_TESTCASE_LIST_2:
			return "ReportTCList2.xsd";
		case REPORTDS_ERROR_LIST_1:
			return "ReportERList1.xsd";
		case REPORTDS_DEPENDENCY_COMBINATION:
			return "ReportDCList1.xsd";
		case REPORTDS_TESTDATASET:
			return "ReportTDList1.xsd";
		case REPORTDS_OLD_TESTDATASET:
			return "ReportOldTDList1.xsd";
		case REPORTDS_TESTDATA_DORMA:
			return "ReportTCList1.xsd";
		default:
			return null;
		} 
	}
	
	public String getAbsoluteSchema()
	 {
		 switch (this) {
		case REPORTDS_TESTCASE_LIST_1:
			return BusinessRules.TESTCASE_XSLTREPORTS_CARPET + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		case REPORTDS_TESTCASE_LIST_2:
			return BusinessRules.TESTCASE_XSLTREPORTS_CARPET + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		case REPORTDS_ERROR_LIST_1:
			return BusinessRules.TESTCASE_XSLTREPORTS_CARPET + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		case REPORTDS_DEPENDENCY_COMBINATION:
			return BusinessRules.TESTCASE_XSLTREPORTS_CARPET + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		case REPORTDS_TESTDATASET:
			return BusinessRules.RESOURCE_FOLDER + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		case REPORTDS_OLD_TESTDATASET:
			return BusinessRules.RESOURCE_FOLDER + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		case REPORTDS_TESTDATA_DORMA:
			return BusinessRules.RESOURCE_FOLDER + BusinessRules.URL_SEPARATOR + this.getSchemaName();
		default:
			return  null;
		}
	 }
	
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
			switch (this) {
			case REPORTDS_TESTCASE_LIST_1:
				return CMMessages.getString("REPORT_DATA_TEST_CASE_LIST_1");
			case REPORTDS_TESTCASE_LIST_2:
				return CMMessages.getString("REPORT_DATA_TEST_CASE_LIST_2");
			case REPORTDS_ERROR_LIST_1:
				return CMMessages.getString("REPORT_DATA_ERROR_LIST");
			case REPORTDS_DEPENDENCY_COMBINATION:
				return CMMessages.getString("REPORT_DATA_DEPENDENCY_COMBINATION");
			case REPORTDS_TESTDATASET:
				return CMMessages.getString("REPORT_DATA_TEST_DATA");
			case REPORTDS_OLD_TESTDATASET:
				return CMMessages.getString("REPORT_DATA_OLD_TEST_DATA");
			case REPORTDS_TESTDATA_DORMA:
				return CMMessages.getString("REPORT_DATA_TEST_DATA");
			default:
				return "";
			}
				
			
	}
	public CMReportData getReportData() {
		if (reportData == null)
			reportData = createReportData();
		return reportData;
	}
	
	public CMReportData createReportData() {
		switch (this) {
		case REPORTDS_ERROR_LIST_1:
			return new CMReportDataXMLErrorList();
		case REPORTDS_TESTCASE_LIST_1:
			return new CMReportDataXMLTestCaseList1();
		case REPORTDS_TESTCASE_LIST_2:
			return new CMReportDataXMLTestCaseList2();
		case REPORTDS_DEPENDENCY_COMBINATION:
			return new CMReportDataXMLDepCombList1();	
		case REPORTDS_TESTDATASET:
			return new CMReportDataXMLTestDataList();
		case REPORTDS_OLD_TESTDATASET:
			return new CMReportDataXMLOldTestDataList();
		case REPORTDS_TESTDATA_DORMA:
			return new CMReportDataXMLTestDataDormaList();
		default:
			return null;
		}
	
	}
}
