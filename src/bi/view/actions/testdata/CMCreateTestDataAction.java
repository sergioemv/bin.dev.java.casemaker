/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdata;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.TDStructure;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
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
public class CMCreateTestDataAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMPanelTestDataView m_panelTestDataView;

	public CMCreateTestDataAction(){
		super(CMMessages.getString("TESTDATA_CREATE_TESTDATA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_CREATE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_CREATE_TESTDATA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("CREATE_A_NEW_TEST_DATA_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		CMCompoundEdit ce = new CMCompoundEdit();
		
		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_panelTestDataView.update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

		this.m_frame.setWaitCursor(true);
        TDStructure tdStructure = this.m_frame.getTreeWorkspaceView().getSelectedTDStructure();
        TestData testData = new TestData((TDStructure)tdStructure.clone());
        if(testData.getM_TDStructure().getM_StructureTestData().size()!=0)
        {
        	String tdName = testData.generateName(tdStructure,ce);
        	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameTestDataModelEdit(testData,tdName));
        	testData.setName(tdName);
        	ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestDataToTDStructureModelEdit(tdStructure,testData));
        	tdStructure.getTestDataCombination().getM_TestDatas().addElement(testData);

        	/*this.m_frame.getM_CMUndoMediator().createTestDataEdit(m_panelTestDataView,
        			tdStructure.getTestDataCombination().getM_TestDatas().size()-1,testData.cloneTestData(),
        			this.m_frame.getm_TabbedPaneView(),CMMessages.getString("TESTDATA_TESTDATA"));*/

        	ce.addEdit(CMViewEditFactory.INSTANCE.createAddTDCombinationToPanelTDViewViewEdit(m_panelTestDataView,tdStructure.getTestDataCombination()));
        	m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
        	ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexTestDataInCMIndexTDStructureUpdate
        			(CMIndexTDStructureUpdate.getInstance(),tdStructure.getTestDataCombination()
        					.getM_TestDatas().size()-1));
			CMIndexTDStructureUpdate.getInstance().setindexTestData
			(tdStructure.getTestDataCombination().getM_TestDatas().size()-1);
			
			ce.addDelegateEdit(new CMDelegate(){

				public void execute() {
					m_panelTestDataView.update();
				}

			});
			
			m_panelTestDataView.update();
			
			if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);
        }
        else{
            JOptionPane.showMessageDialog(this.m_frame,
            		CMMessages.getString("TESTDATA_MESSAGE_CREATE_WITHOUT_STRUCTURE"),
            		CMMessages.getString("TESTDATA_TITLE_MESSAGE_ERROR"),JOptionPane.ERROR_MESSAGE);
        }
        this.m_frame.setWaitCursor(false);
	}

}
