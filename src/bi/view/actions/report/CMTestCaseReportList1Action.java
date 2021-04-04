/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.actions.report;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;

import model.BusinessRules;
import model.ProjectReference;
import model.Session2;
import model.TestObject;
import model.TestObjectReference;
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
public class CMTestCaseReportList1Action extends AbstractAction implements
		Action {
/**
 * 
 */
public CMTestCaseReportList1Action() {
	  super(CMMessages.getString("MENU_ITEM_GENERATE_TEST_CASE_LIST"));    
	    // Set tool tip text
	    putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_GENERATE_TEST_CASE_LIST"));
	     putValue(Action.SMALL_ICON, CMAction.TESTCASE_REPORT_LIST1.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("MENU_ITEM_GENERATE_TEST_CASE_LIST_MNEMONIC").charAt(0));
	 
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
		  EReportDataSource.REPORTDS_TESTCASE_LIST_1.getReportData().setParameter("STRUCTURE", StructureManager.getSelectedStructure());
		  EReportDataSource.REPORTDS_TESTCASE_LIST_1.getReportData().setParameter("PROJECT", ProjectManager.getSelectedProject());


		  reportManager.generateReport(Arrays.asList(EReportDataSource.REPORTDS_TESTCASE_LIST_1,EReportDataSource.REPORTDS_TESTCASE_LIST_2),getDefaultTCReportFileName());
		  if (comp!=null)
			  comp.requestFocus();
		
	}
	public static String getDefaultTCReportFileName() {
		ProjectReference projectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentProjectReference();
	    TestObjectReference testObjectReference = CMApplication.frame.getTreeWorkspaceView().getCurrentTestObjectReference();
	    TestObject testObject =CMApplication.frame.getTreeWorkspaceView().getCurrentTestObject();
	    Session2 session = CMApplication.frame.getTreeWorkspaceView().getM_Session2();
		if( CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().isTestObjectCheckedOutLocalByTheSameUser(testObject, session) ) {
		  projectReference = projectReference.getM_LocalProjectReference();
		}
	    String absoluteReportsPath = CMApplication.frame.getCmApplication().getSessionManager().getM_WorkspaceManager().buildAbsoluteReportsPath(projectReference, testObjectReference);
	    absoluteReportsPath = absoluteReportsPath + BusinessRules.URL_SEPARATOR+testObject.getName() ;
	    return absoluteReportsPath;
	}

}
