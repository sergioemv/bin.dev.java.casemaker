/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset.reports;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
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
public class CMDeleteReportAction extends AbstractAction implements Action {
	
	private CMFrameView m_frame;
	private CMScrollPaneOutput m_panelTestDataSetReportsView;
	
	public CMDeleteReportAction(){
		super(CMMessages.getString("TESTDATASET_REPORTS_DELETE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATASET_REPORTS_DELETE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASETREPORT_DELETE_REPORT.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_REPORT_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		
		this.m_frame = CMApplication.frame;
		this.m_panelTestDataSetReportsView = m_frame.getPanelTestDataSetReportsView();
		
        int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(this.m_frame, 
        		CMMessages.getString("TESTDATA_MESSAGE_DELETE_TESTDATASET_REPORTS"),
        		CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
        	m_panelTestDataSetReportsView.deleteReports();
        }
	}
}
