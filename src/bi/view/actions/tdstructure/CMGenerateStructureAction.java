/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import model.StructureTestData;
import model.TDStructure;
import model.util.CMDelegate;

import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTDStructureView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMGenerateStructureAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTDStructureView m_panelTDStructure;

	public CMGenerateStructureAction(){

		super(CMMessages.getString("TESTDATA_GENERATE_TDSTRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_STRUCTURE_GENERATE_STRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_GENERATE_STRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("GENERATE_STRUCTURE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTDStructure = m_frame.getPanelTDStructureView();
		CMCompoundEdit ce = new CMCompoundEdit();

		this.m_frame.setWaitCursor(true);
        TDStructure testData = this.m_frame.getTreeWorkspaceView().getSelectedTDStructure();
        
        ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				//CMIndexTDStructureUpdate.getInstance().setindex(0);
	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
	            m_panelTDStructure.update();
	            //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
	            //m_panelTestDataView.update();
	            //m_panelTestDataSetView.update();
	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}

		});
		ce.addEdit(new CMComponentAwareEdit());
        
        this.m_gridTDStructure.setTDStructure(testData);
        StructureTestData s_TestData= this.m_gridTDStructure.insertDataTDStructureView(ce);
		if(s_TestData != null){
			ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				//CMIndexTDStructureUpdate.getInstance().setindex(0);
    	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
    	            m_panelTDStructure.update();
    	            //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
    	            //m_panelTestDataView.update();
    	            //m_panelTestDataSetView.update();
    	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}

    		});
			
			if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);
			
		}
			/*this.m_frame.getM_CMUndoMediator().addTDStructureAt(this.m_gridTDStructure,
					testData.getM_StructureTestData().size()-1,s_TestData,this.m_frame.getm_TabbedPaneView(),
					CMMessages.getString("TESTDATA_TDSTRUCTURE"));*/
		this.m_frame.setWaitCursor(false);
		this.m_panelTDStructure.update();

	}

}
