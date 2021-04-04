/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdata;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.TDStructure;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogImportTestData;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataSetView;
import bi.view.testdataviews.CMPanelTestDataView;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMImportTestDataAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;

	public CMImportTestDataAction(){
		super(CMMessages.getString("IMPORT_TEST_DATA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_IMPORT_TEST_DATA"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_IMPORT_TESTDATA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("IMPORT_TEST_DATA_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();

		CMDialogImportTestData cmd = new CMDialogImportTestData(this.m_frame, m_gridTDStructure);
        cmd.setVisible(true);
        if(cmd.isOkSelected){
			TDStructure tdStructure = this.m_frame.getTreeWorkspaceView().getSelectedTDStructure();
			CMIndexTDStructureUpdate.getInstance().setindexTestData
			(tdStructure.getTestDataCombination().getM_TestDatas().size()-1);
        	m_panelTestDataView.update();
        	m_panelTestDataSetView.update();
        }
	}
}
