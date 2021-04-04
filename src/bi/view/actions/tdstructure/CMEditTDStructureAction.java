/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import model.StructureTestData;
import model.TestData;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogEditTDStructure;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMEditTDStructureAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;

	public CMEditTDStructureAction(){
		super(CMMessages.getString("TESTDATA_EDIT_TDSTRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_EDIT_TDSTRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_EDIT_TDSTRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("EDIT_STRUCTURE_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		m_frame = CMApplication.frame;
		StructureTestData std = new StructureTestData();
		CMCompoundEdit ce = new CMCompoundEdit();
		
		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_frame.getPanelTestDataView().update();
				m_frame.getPanelTDStructureView().update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

		if(m_frame.isIsPanelTestDataSelected()){
			m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
			TestData td = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
	        std = (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
	        CMDialogEditTDStructure cmd = new CMDialogEditTDStructure(m_frame, std,ce);
	        cmd.setVisible(true);
		}
		else{
			m_gridTDStructure = m_frame.getGridTDStructure();
			if (m_gridTDStructure.getTDStructure().getM_StructureTestData().size() != 0) {
	            std = (StructureTestData)m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
	            CMDialogEditTDStructure cmd = new CMDialogEditTDStructure(m_frame, std,ce);
	            cmd.setVisible(true);
	        }
		}

		if(CMDialogEditTDStructure.isOk){
		       
	 		ce.addDelegateEdit(new CMDelegate(){

				public void execute() {
					m_frame.getPanelTestDataView().update();
					m_frame.getPanelTDStructureView().update();
				}

			});
	         
	        if(m_frame.isIsPanelTestDataSelected())
	        {
	        	m_frame.getPanelTestDataView().update();
	        }
	        else
	        {
	        	m_frame.getPanelTDStructureView().update();
	        }
            if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);
		}


	}
}
