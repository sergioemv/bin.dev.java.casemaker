/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdata;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import model.TestData;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogEditTestData;
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
public class CMEditTestDataAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;


	public CMEditTestDataAction(){
		super(CMMessages.getString("TESTDATA_EDIT_TESTDATA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_EDIT"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_EDIT_TESTDATA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("EDIT_TEST_DATA_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		CMCompoundEdit ce = new CMCompoundEdit();
		
		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_frame.getPanelTestDataView().update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        TestData td = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().
        elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
        CMDialogEditTestData cmd = new CMDialogEditTestData(this.m_frame, td);
        cmd.setVisible(true);

        if(!td.getDescription().equalsIgnoreCase(CMDialogEditTestData.m_testdataDescription)){
        	/*this.m_frame.getM_CMUndoMediator().editTestDataEdit(this.m_frame.getPanelTestDataView(),
        			CMIndexTDStructureUpdate.getInstance().getindexTestData(),
        			CMDialogEditTestData.m_testdataDescription,
        			td.getDescription(),this.m_frame.getm_TabbedPaneView(),
        			CMMessages.getString("TESTDATA_TESTDATA"));*/
        	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeDescriptionTestDataModelEdit(td,CMDialogEditTestData.m_testdataDescription));
        	td.setDescription(CMDialogEditTestData.m_testdataDescription);
        	
    		ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				m_frame.getPanelTestDataView().update();
    			}

    		});
    		
			if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);
		
        }
		this.m_frame.getPanelTestDataView().update();
	}

}
