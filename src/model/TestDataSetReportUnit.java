package model;
import java.util.Vector;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
public class TestDataSetReportUnit {
   /* public TestDataSetReportUnit() {
         applicationName= new Vector();
    	 reportName= new Vector();
//hcanedo_21_09_2004_begin
		nameReportsImport= new Vector();
    	pathReportsImport= new Vector();
        parameterReport= new Vector();
//hcanedo_21_09_2004_end
    }
*/
    public Vector getApplicationName(){ return applicationName; }

    public void setApplicationName(Vector applicationName){ this.applicationName = applicationName; }


    public Vector getReportName(){ return reportName; }

    public void setReportName(Vector reportName){ this.reportName = reportName; }
//hcanedo_21_09_2004_begin
    public Vector getParameterReport(){ return parameterReport; }

    public void setParameterReport(Vector parameterReport){ this.parameterReport = parameterReport; }

    public Vector getNameReportsImport(){ return nameReportsImport; }

    public void setNameReportsImport(Vector nameReportsImport){ this.nameReportsImport = nameReportsImport; }

    public Vector getPathReportsImport(){ return pathReportsImport; }

    public void setPathReportsImport(Vector pathReportsImport){ this.pathReportsImport = pathReportsImport; }

	private Vector nameReportsImport= new Vector();
    private Vector pathReportsImport= new Vector();
    private Vector parameterReport;
//hcanedo_21_09_2004_end
    private Vector applicationName;

    private Vector reportName;
	public TestDataSetReportUnit() {
		m_ReportRecords= new Vector();
		m_ImportReportRecords= new Vector();
	}
	
	private Vector m_ReportRecords= new Vector();
	private Vector m_ImportReportRecords= new Vector();
	/**
	 * @return Returns the m_ReportRecords.
	 */
	public Vector getM_ReportRecords() {
		return m_ReportRecords;
	}

	/**
	 * @param reportRecords The m_ReportRecords to set.
	 */
	public void setM_ReportRecords(Vector reportRecords) {
		m_ReportRecords = reportRecords;
	}

	/**
	 * @return Returns the m_ImportReportRecords.
	 */
	public Vector getM_ImportReportRecords() {
		return m_ImportReportRecords;
	}

	/**
	 * @param importReportRecords The m_ImportReportRecords to set.
	 */
	public void setM_ImportReportRecords(Vector importReportRecords) {
		m_ImportReportRecords = importReportRecords;
	}
}
