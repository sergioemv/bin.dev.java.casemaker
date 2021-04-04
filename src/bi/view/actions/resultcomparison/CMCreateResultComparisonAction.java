/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.resultcomparison;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.BusinessRules;
import model.ProjectReference;
import model.Session2;
import model.TestObject;
import model.TestObjectReference;
import bi.controller.ProjectManager;
import bi.controller.StructureManager;
import bi.controller.WorkspaceManager;
import bi.view.actions.CMAction;
import bi.view.icons.CMIcon;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.report.CMReportRunner;
import bi.view.report.data.EReportDataSource;
import bi.view.resultscomparationviews.CMDialogCreateEditResultComparation;

/**
 * @author smoreno
 *
 */
public class CMCreateResultComparisonAction extends AbstractAction implements
		Action {
/**
 *
 */
public CMCreateResultComparisonAction() {
	  super(CMMessages.getString("LABEL_RESULT_COMPARISON_CREATE_BUTTON"));
	    // Set tool tip text
	     putValue(Action.SMALL_ICON, CMIcon.RESULT_CREATE.getImageIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("LABEL_RESULT_COMPARISON_CREATE_BUTTON_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent p_e) {
		CMDialogCreateEditResultComparation cmd=new CMDialogCreateEditResultComparation(CMApplication.frame,CMApplication.frame.getTreeWorkspaceView().getSelectedResultComparation());
	    cmd.setVisible(true);
	    CMApplication.frame.getPanelResultComparation().setM_TDStructure(CMApplication.frame.getTreeWorkspaceView().getSelectedResultComparation());

	}


}
