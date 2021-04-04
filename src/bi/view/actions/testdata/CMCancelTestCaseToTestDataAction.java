/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdata;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMCancelTestCaseToTestDataAction extends AbstractAction implements
		Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataView m_panelTestDataView;

	public CMCancelTestCaseToTestDataAction(){
		super(CMMessages.getString("TESTDATA_CANCEL_ASSIGNEMENT_TESTCASE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_CANCEL_ASSIGNMENT_TESTCASE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_CANCEL_TESTCASE_TO_TESTDATA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("CANCEL_TEST_CASE_ASSIGNMENT_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		CMCompoundEdit ce = new CMCompoundEdit();

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_panelTestDataView.update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(this.m_frame,
        		CMMessages.getString("TESTDATA_MESSAGE_DELETE_TESTCASE_REFERENCE"),
        		CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {

        	try {

        		TestData testData = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().
        		getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());

        		//String oldTestCase=testData.getM_TestCaseinTestData();
        		/*this.m_frame.getM_CMUndoMediator().assignTestCaseToTestDataEdit(m_panelTestDataView,
        				CMIndexTDStructureUpdate.getInstance().getindexTestData(),"",oldTestCase,
        				this.m_frame.getm_TabbedPaneView(),CMMessages.getString("TESTDATA_TESTDATA"));*/
        		ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestCaseInTestDataModelEdit(testData,""));
        		testData.setM_TestCaseinTestData("");
        		ce.addEdit(CMModelEditFactory.INSTANCE.createChangeRiskLevelInTestDataModelEdit(testData,0));
        		testData.setM_RiskLevel(0);
        		
        		ce.addDelegateEdit(new CMDelegate(){

        			public void execute() {
        				m_panelTestDataView.update();
        			}

        		});
        		
        		m_panelTestDataView.update();
        		
				if(ce.hasEdits())
		        	CMUndoMediator.getInstance().doEdit(ce);
        	}
        	catch (Exception ex) {
        		JOptionPane.showMessageDialog(this.m_frame, CMMessages.getString("TESTDATA_ERROR_EXIST_TESTDATA"),
        				CMMessages.getString("TESTDATA_TITLE_ERROR"),JOptionPane.ERROR_MESSAGE);
        	}
        }
	}
}
