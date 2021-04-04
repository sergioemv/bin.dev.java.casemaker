/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset;

import java.awt.Event;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.TestDataSet;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTestDataSetView;
import bi.view.testdataviews.grid.DialogCreateEditTestDataSet;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMNewTestDataSetAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataSetView m_panelTestDataSetView;

	public CMNewTestDataSetAction(){
		super(CMMessages.getString("TESTDATASET_CONTEXMENU_NEW"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATASET_CREATE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASET_NEW_TESTDATASET.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("CREATE_TEST_DATA_SET_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		CMCompoundEdit ce = new CMCompoundEdit();

		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_panelTestDataSetView.update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        TestDataSet testDataSet = new TestDataSet(m_gridTDStructure.getTDStructure());
        DialogCreateEditTestDataSet cmd = new DialogCreateEditTestDataSet(this.m_frame, testDataSet,
        		CMMessages.getString("TESTDATA_CREATE_TESTDATASET"),ce);
        cmd.setVisible(true);
        if (cmd.selectedOk) {
            if(testDataSet.getM_TestDataCombinations().getM_TestDatas().size()>0)
            {
            	this.m_frame.setWaitCursor(true);
            	ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestDataSetSingleToTDStructure(m_gridTDStructure.getTDStructure(),testDataSet));
            	m_gridTDStructure.getTDStructure().getM_TestDataSet().addElement(testDataSet);
            	ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexTestDataSetInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),m_gridTDStructure.getTDStructure().getM_TestDataSet().size()-1));
            	CMIndexTDStructureUpdate.getInstance().setindexTestDataSet(m_gridTDStructure.getTDStructure().
            			getM_TestDataSet().size()-1);


            	ce.addDelegateEdit(new CMDelegate(){

        			public void execute() {
        				m_panelTestDataSetView.update();
        			}

        		});

            	m_panelTestDataSetView.update();

            	if(ce.hasEdits())
    	        	CMUndoMediator.getInstance().doEdit(ce);

            	/*this.m_frame.getM_CMUndoMediator().createTestDataSetEdit(m_gridTDStructure,testDataSet,
            			CMIndexTDStructureUpdate.getInstance().getindexTestDataSet(),
            			this.m_frame.getm_TabbedPaneView(),CMMessages.getString("TESTDATA_TESTDATASET"));*/
            	this.m_frame.setWaitCursor(false);
            }
            else{
                m_gridTDStructure.getTDStructure().degenerateIDTestDataSet();
                JOptionPane.showMessageDialog(this.m_frame,
                		CMMessages.getString("TESTDATASET_MESSAGE_CREATE_WITHOUT_TESTDATA"),
                		CMMessages.getString("TESTDATA_TITLE_MESSAGE_ERROR"),JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            m_gridTDStructure.getTDStructure().degenerateIDTestDataSet();
  /*          ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				m_panelTestDataSetView.update();
    			}

    		});

        	m_panelTestDataSetView.update();

        	if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);*/
        }

	}

}
