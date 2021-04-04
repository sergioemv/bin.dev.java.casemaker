/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdataset;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.TestData;
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
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteTestDataSetAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataSetView m_panelTestDataSetView;

	public CMDeleteTestDataSetAction(){
		super(CMMessages.getString("TESTDATASET_CONTEXMENU_DELETE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATASET_DELETE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATASET_DELETE_TESTDATASET.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_TEST_DATA_SET_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
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
	            if(m_gridTDStructure.getTDStructure().getM_TestDataSet().size()!=0)
	            {
	            	m_frame.statesMenusTestDataSetEditDeleteReport(true);
	            }
	            m_panelTestDataSetView.update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(this.m_frame,
        		CMMessages.getString("TESTDATA_MESSAGE_DELETE_TESTDATASET"),
            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_TESTDATASET"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {

        	//Vector p_ResultCompariosnActual= (Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().
        	//getTestDataSetActual().clone();
        	//Vector p_ResultComparisonTarget=(Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().
        	//getTestDataSetTarget().clone();
        	//int indexTDS=CMIndexTDStructureUpdate.getInstance().getindexTestDataSet();
            TestDataSet tds = (TestDataSet)m_gridTDStructure.getTDStructure().getM_TestDataSet().
            elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
            for (int i = 0; i < tds.getM_TestDataCombinations().getM_TestDatas().size(); i++) {
                TestData td = (TestData)tds.getM_TestDataCombinations().getM_TestDatas().elementAt(i);
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeContTestDataSetInTestData(td,td.getContTestDataSet() - 1));
                td.setContTestDataSet(td.getContTestDataSet() - 1);
                if (td.getContTestDataSet() == 0)
                	ce.addEdit(CMModelEditFactory.INSTANCE.createChangeIsSetTestDataModelEdit(td,false));
                    td.setSet(false);
            }
            int idTDS=tds.getId();
            ce.addEdit(m_gridTDStructure.getTDStructure().deleteIdTestDataSet(idTDS));
            String name= tds.getName();
            ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetFromResultComparison(m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
			m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetActual().remove(name);
			ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetTargetFromResultComparison(m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
            m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetTarget().remove(name);
            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementTestDataSetAtFromTestDataSet(m_gridTDStructure.getTDStructure(),CMIndexTDStructureUpdate.getInstance().getindexTestDataSet()));
            m_gridTDStructure.getTDStructure().getM_TestDataSet().removeElementAt
            (CMIndexTDStructureUpdate.getInstance().getindexTestDataSet());
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexTestDataSetInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),CMIndexTDStructureUpdate.getInstance().getindexTestDataSet()-1));
            CMIndexTDStructureUpdate.getInstance().setindexTestDataSet
            (CMIndexTDStructureUpdate.getInstance().getindexTestDataSet()-1);
            if(m_gridTDStructure.getTDStructure().getM_TestDataSet().size()>0)
            {
            	if(CMIndexTDStructureUpdate.getInstance().getindexTestDataSet()==-1)
            		ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexTestDataSetInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),0));
					CMIndexTDStructureUpdate.getInstance().setindexTestDataSet(0);
            }

    		ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    	            if(m_gridTDStructure.getTDStructure().getM_TestDataSet().size()==0)
    	            {
    	            	m_frame.statesMenusTestDataSetEditDeleteReport(false);
    	            }
    	            m_panelTestDataSetView.update();
    			}

    		});

            if(m_gridTDStructure.getTDStructure().getM_TestDataSet().size()==0)
            {
            	this.m_frame.statesMenusTestDataSetEditDeleteReport(false);
            }
            m_panelTestDataSetView.update();

			if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);

            /*this.m_frame.getM_CMUndoMediator().deleteTestDataSetAt(m_gridTDStructure, p_ResultCompariosnActual,
            		p_ResultComparisonTarget, tds, indexTDS, this.m_frame.getm_TabbedPaneView(),
            		CMMessages.getString("TESTDATA_TESTDATASET"));*/
        }
	}
}
