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
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.report.CMReportRunner;
import bi.view.report.data.EReportDataSource;

/**
 * @author smoreno
 *
 */
public class CMErrorReportListAction extends AbstractAction implements Action {
	public CMErrorReportListAction() {
		super(CMMessages.getString("POPUPMENU_ITEM_GENERATE_ERRORS_REPORT"));
//		 Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_GENERATE_ERRORS_REPORT"));
	    putValue(Action.SMALL_ICON, CMAction.ERROR_REPORT_LIST.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_GENERATE_MNEMONIC").charAt(0));

	    
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		  CMReportRunner reportManager = CMReportRunner.getInstance();
		  EReportDataSource.REPORTDS_ERROR_LIST_1.getReportData().setParameter("STRUCTURE", StructureManager.getSelectedStructure());
		  EReportDataSource.REPORTDS_ERROR_LIST_1.getReportData().setParameter("PROJECT", ProjectManager.getSelectedProject());
		  TestCaseExternalReports format = SessionManager.INSTANCE.getApplicationSettingManager().
				getApplicationSetting().getTestCaseReportByName(CMMessages.getString("REPORT_ERROR_LIST"));
		//  reportManager.generateReport(format,CMTestCaseReportList1Action.getDefaultTCReportFileName());
		  reportManager.generateReport(EReportDataSource.REPORTDS_ERROR_LIST_1,CMTestCaseReportList1Action.getDefaultTCReportFileName());
	}

}
