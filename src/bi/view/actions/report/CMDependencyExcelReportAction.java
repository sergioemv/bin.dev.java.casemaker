/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.report;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.TestCaseExternalReports;
import bi.controller.DependencyManager;
import bi.controller.ProjectManager;
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.CMReportRunner;
import bi.view.report.data.EReportDataSource;

/**
 * @author smoreno
 *
 */
public class CMDependencyExcelReportAction extends AbstractAction implements
		Action {

	/**
	 * 
	 */
	public CMDependencyExcelReportAction() {
		 super(CMMessages.getString("LABEL_DEPENDENCY_TO_EXCEL"));    
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("LABEL_DEPENDENCY_TO_EXCEL"));
	    putValue(Action.LONG_DESCRIPTION, "Generate the test case list reports");
	    // Set an icon
	    putValue(Action.SMALL_ICON, CMAction.DEPENDENCY_REPORT_EXCEL.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_DEPENDENCY_TO_EXCEL_MNEMONIC").charAt(0));

	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
	  CMApplication.frame.setWaitCursor(true);
	  Component comp = CMApplication.frame.getFocusOwner(); 
  	  CMReportRunner reportManager = CMReportRunner.getInstance();
	  EReportDataSource.REPORTDS_DEPENDENCY_COMBINATION.getReportData().setParameter("STRUCTURE", StructureManager.getSelectedStructure());
	  EReportDataSource.REPORTDS_DEPENDENCY_COMBINATION.getReportData().setParameter("PROJECT", ProjectManager.getSelectedProject());
	  EReportDataSource.REPORTDS_DEPENDENCY_COMBINATION.getReportData().setParameter("DEPENDENCY", DependencyManager.getSelectedDependency());

  	  TestCaseExternalReports format = CMApplication.frame.getCmApplication().getSessionManager().getApplicationSettingManager().
  			getApplicationSetting().getTestCaseReportByName(CMMessages.getString("REPORT_DEPENDENCY_COMBINATION_LIST"));
  	//  reportManager.generateReport(format,CMTestCaseReportList1Action.getDefaultTCReportFileName());
  	  reportManager.generateReport(EReportDataSource.REPORTDS_DEPENDENCY_COMBINATION,CMTestCaseReportList1Action.getDefaultTCReportFileName());
      CMApplication.frame.setWaitCursor(false);
      if (comp!=null)
		  comp.requestFocus();
      

	}

}
