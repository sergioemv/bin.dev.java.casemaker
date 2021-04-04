/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset.reports;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMScrollPaneOutput;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMEditReportAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMScrollPaneOutput m_panelTestDataSetReportsView;
	
	public CMEditReportAction(){
		super(CMMessages.getString("TESTDATASET_REPORTS_EDIT"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATASET_REPORTS_EDIT"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASETREPORT_EDIT_REPORT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("EDIT_REPORT_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		this.m_frame = CMApplication.frame;
		this.m_panelTestDataSetReportsView = m_frame.getPanelTestDataSetReportsView();
		
		m_panelTestDataSetReportsView.renameReport();

	}

}
