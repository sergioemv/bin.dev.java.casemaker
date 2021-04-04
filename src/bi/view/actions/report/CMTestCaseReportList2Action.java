/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.report;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import bi.controller.ProjectManager;
import bi.controller.StructureManager;
import bi.controller.WorkspaceManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.CMReportRunner;
import bi.view.report.data.EReportDataSource;

/**
 * @author smoreno
 *
 */
public class CMTestCaseReportList2Action extends AbstractAction implements
		Action {
	public CMTestCaseReportList2Action() {
		  super(CMMessages.getString("MENU_ITEM_EXPORT_TEST_CASES_TO_EXCEL"));    
		    // Set tool tip text
		    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_EXPORT_TEST_CASES_TO_EXCEL"));
		    // Set an icon
		    putValue(Action.SMALL_ICON, CMAction.TESTCASE_REPORT_LIST2.getIcon());
		    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_EXPORT_TEST_CASES_TO_EXCEL_MNEMONIC").charAt(0));
		 
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
			  Component comp = CMApplication.frame.getFocusOwner();	
			  CMReportRunner reportManager = CMReportRunner.getInstance();
			  EReportDataSource.REPORTDS_TESTCASE_LIST_2.getReportData().setParameter("STRUCTURE", StructureManager.getSelectedStructure());
			  EReportDataSource.REPORTDS_TESTCASE_LIST_2.getReportData().setParameter("PROJECT", ProjectManager.getSelectedProject());
			  EReportDataSource.REPORTDS_TESTCASE_LIST_2.getReportData().setParameter("WORKSPACE", WorkspaceManager.getSelectedWorkspace());
			  reportManager.generateReport(EReportDataSource.REPORTDS_TESTCASE_LIST_2,CMTestCaseReportList1Action.getDefaultTCReportFileName());
			  if (comp!=null)
				  comp.requestFocus();
	}

}
