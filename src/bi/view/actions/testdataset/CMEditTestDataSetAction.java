/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import model.TestDataSet;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
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
public class CMEditTestDataSetAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataSetView m_panelTestDataSetView;

	public CMEditTestDataSetAction(){
		super(CMMessages.getString("TESTDATASET_CONTEXMENU_EDIT"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATASET_EDIT"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASET_EDIT_TESTDATASET.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("EDIT_TEST_DATA_SET_MNEMONIC").charAt(0));
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
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

        if (m_gridTDStructure.getTDStructure().getM_TestDataSet().size() != 0) {

            TestDataSet testDataSet = (TestDataSet)m_gridTDStructure.getTDStructure().getM_TestDataSet().
            elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
            //TestDataSet tds = testDataSet.cloneTestDataSet();
            DialogCreateEditTestDataSet cmd = new DialogCreateEditTestDataSet(this.m_frame, testDataSet,
            		CMMessages.getString("TESTDATA_EDIT_TESTDATASET"),ce);
            cmd.setVisible(true);

         if (cmd.selectedOk) {
            if(testDataSet.getM_TestDataCombinations().getM_TestDatas().size()<=0)
            {

            	//Vector p_ResultCompariosnActual= (Vector)m_gridTDStructure.getTDStructure().
            	//getM_ResultComparation().getTestDataSetActual().clone();
            	//Vector p_ResultComparisonTarget=(Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().
            	//getTestDataSetTarget().clone();
            	//int indexTDS=CMIndexTDStructureUpdate.getInstance().getindexTestDataSet();
                ce.addEdit(m_gridTDStructure.getTDStructure().degenerateIDTestDataSet());
                ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetFromTDStructure(m_gridTDStructure.getTDStructure(),testDataSet));
				m_gridTDStructure.getTDStructure().getM_TestDataSet().remove(testDataSet);
				/*this.m_frame.getM_CMUndoMediator().deleteTestDataSetAt(m_gridTDStructure, p_ResultCompariosnActual,
						p_ResultComparisonTarget,tds, indexTDS, this.m_frame.getm_TabbedPaneView(),
						CMMessages.getString("TESTDATA_TESTDATASET"));*/
				ce.addDelegateEdit(new CMDelegate(){

					public void execute() {
						m_panelTestDataSetView.update();
					}

				});

				if(ce.hasEdits())
		        	CMUndoMediator.getInstance().doEdit(ce);

                JOptionPane.showMessageDialog(this.m_frame,
                		CMMessages.getString("TESTDATASET_MESSAGE_CREATE_WITHOUT_TESTDATA"),
                		CMMessages.getString("TESTDATA_TITLE_MESSAGE_ERROR"),JOptionPane.ERROR_MESSAGE);
            }
        }
			ce.addDelegateEdit(new CMDelegate(){

				public void execute() {
					m_panelTestDataSetView.update();
				}

			});

			if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);

            m_panelTestDataSetView.update();
        }
	}
}
