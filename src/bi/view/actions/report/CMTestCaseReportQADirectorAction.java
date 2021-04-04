/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.report;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.TestCaseExternalReports;
import bi.controller.ProjectManager;
import bi.controller.SessionManager;
import bi.controller.StructureManager;
import bi.controller.WorkspaceManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.report.CMReportRunner;
import bi.view.report.data.EReportDataSource;

/**
 * @author smoreno
 *
 */
public class CMTestCaseReportQADirectorAction extends AbstractAction implements
		Action {

	public CMTestCaseReportQADirectorAction() {
		super(CMMessages.getString("TESTCASE_REPORTS_QADIRECTOR"));
//		 Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTCASE_REPORTS_QADIRECTOR"));
	    putValue(Action.LONG_DESCRIPTION, "Creates a new element in the test object");
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.TESTCASE_REPORT_QADIRECTOR.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("TESTCASE_REPORTS_QADIRECTOR_MNEMONICS").charAt(0));

	    
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		  CMReportRunner reportManager = CMReportRunner.getInstance();
		  EReportDataSource.REPORTDS_TESTCASE_LIST_2.getReportData().setParameter("STRUCTURE", StructureManager.getSelectedStructure());
		  EReportDataSource.REPORTDS_TESTCASE_LIST_2.getReportData().setParameter("PROJECT", ProjectManager.getSelectedProject());
		  EReportDataSource.REPORTDS_TESTCASE_LIST_2.getReportData().setParameter("WORKSPACE", WorkspaceManager.getSelectedWorkspace());

		  TestCaseExternalReports format = SessionManager.INSTANCE.getApplicationSettingManager().
				getApplicationSetting().getTestCaseReportByName(CMMessages.getString("REPORT_TESTCASE_LIST_QADIRECTOR"));
		  reportManager.generateReport(format,CMTestCaseReportList1Action.getDefaultTCReportFileName());
		  
	}

}
