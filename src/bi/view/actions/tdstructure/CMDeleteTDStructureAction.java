/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoableEdit;

import model.StructureTestData;
import model.TDStructure;
import model.TestData;
import model.TestDataSet;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMComplexObjectToDeleted;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTDStructureView;
import bi.view.testdataviews.CMPanelTestDataSetView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteTDStructureAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTDStructureView m_panelTDStructureView;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;
	private TDStructure testData;

	public CMDeleteTDStructureAction(){

		super(CMMessages.getString("TESTDATA_DELETE_TDSTRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_DELETE_TDSTRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_DELETE_TDSTRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_STRUCTURE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		m_frame = CMApplication.frame;
		m_gridTDStructure = m_frame.getGridTDStructure();
		m_panelTDStructureView = m_frame.getPanelTDStructureView();
		m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		m_panelTestDataView = m_frame.getPanelTestDataView();
		testData = m_gridTDStructure.getTDStructure();
		CMCompoundEdit ce = new CMCompoundEdit();

    	ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
	            if (testData.getM_StructureTestData().size() != 0) {
	                m_frame.statesMenusTDStructureEditDelete(true);
	                m_frame.stateInsertColumnOptionsinStructure(true);
	            }
	            if (testData.getM_TestDataSet().size() != 0) {
	            	m_frame.statesMenusTestDataSetEditDeleteReport(true);
	            }
	            if (testData.getTestDataCombination().getM_TestDatas().size() != 0) {
	            	m_frame.statesMenusTestDataEditDeleteAssign(true);
	            }
	            m_gridTDStructure.update(0);
	        	m_panelTDStructureView.update();
	        	m_panelTestDataView.update();
	        	m_panelTestDataSetView.update();
	        	//m_panelTDStructureView.getScrollPaneStructureDescriptionView().getM_cmStructureView().gainFocusWhenAintRowLeft();
			}

		});

			int confirmation = JOptionPane.YES_OPTION;
	        confirmation = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_STRUCTURE"),
	            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_STRUCTURE"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
	        if (confirmation == JOptionPane.YES_OPTION) {
	        	ce.addEdit(deleteTDStructure());
	        	if(ce.hasEdits())
		        	CMUndoMediator.getInstance().doEdit(ce);

	        }
	}

    @SuppressWarnings("unchecked")
	public UndoableEdit deleteTDStructure() {
    	CMCompoundEdit ce = new CMCompoundEdit();
        //Vector p_ResultCompariosnActual = (Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetActual().clone();
        //Vector p_ResultComparisonTarget = (Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetTarget().clone();
        Vector p_TestDataSetDeleted = new Vector();
        Vector p_TestDataSetIndex = new Vector();
        Vector p_TestDataDeleted = new Vector();
        Vector p_TestDataIndex = new Vector();
        Vector p_TDDeletedinTDS = new Vector();
        Vector p_StDeletedinTD = new Vector();
        int index = CMIndexTDStructureUpdate.getInstance().getIndex();

        if (testData.getM_StructureTestData().size() > 0 && index < testData.getM_StructureTestData().size()) {
            int globalindex = ((StructureTestData)this.testData.getM_StructureTestData().elementAt(index)).getGlobalIndex();

            StructureTestData structTD = (StructureTestData)this.testData.getM_StructureTestData().elementAt(index);
            int idstructTD = structTD.getId();
            ce.addEdit(this.testData.deleteIdStructure(idstructTD));

            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveStructureTestDataFromTDStructure(testData,index));
            this.testData.getM_StructureTestData().removeElementAt(index);
            int sizeCombinations = this.testData.getTestDataCombination().getM_TestDatas().size();
            for (int i = sizeCombinations - 1; i >= 0; i--)
            {
                TestData td = (TestData)this.testData.getTestDataCombination().getM_TestDatas().elementAt(i);
                for (int j = td.getM_TDStructure().getM_StructureTestData().size() - 1; j >= 0; j--) {
                    int globalindex2 = ((StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j)).getGlobalIndex();
                    if (globalindex == globalindex2) {
                        CMComplexObjectToDeleted obj = new CMComplexObjectToDeleted(i, j,
                            (StructureTestData)td.getM_TDStructure().getM_StructureTestData().elementAt(j));

                        p_StDeletedinTD.addElement(obj);
                        ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveStructureTestDataFromTDStructure(td.getM_TDStructure(),j));
                        td.getM_TDStructure().getM_StructureTestData().removeElementAt(j);

                    }
                }
                if (td.getM_TDStructure().getM_StructureTestData().size() == 0) {
                    int idTD = td.getId();
                    this.testData.deleteIdTestData(idTD);
                    p_TestDataDeleted.addElement(td);
                    p_TestDataIndex.addElement(new Integer(i));

                    /**  */
                    ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveTestDataFromTestDataCombination(this.testData.getTestDataCombination(),i));
                    this.testData.getTestDataCombination().getM_TestDatas().removeElementAt(i);
                    for (int k = m_gridTDStructure.getTDStructure().getM_TestDataSet().size() - 1; k >= 0; k--) {
                        TestDataSet tds = (TestDataSet)m_gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(k);
                        int indextds = -1;
                        boolean b = false;
                        String nameTestData = td.getName();
                        for (int h = 0; h < tds.getM_TestDataCombinations().getM_TestDatas().size(); h++) {
                            TestData tdaux = (TestData)tds.getM_TestDataCombinations().getM_TestDatas().elementAt(h);
                            if (nameTestData.equals(tdaux.getName())) {
                                indextds = h;
                            }
                        }
                        if (indextds >= 0) {
                        	ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveTestDataFromTestDataCombination(tds.getM_TestDataCombinations(),indextds));
                            tds.getM_TestDataCombinations().getM_TestDatas().remove(indextds);
                            b = true;
                        }
                        if (b) {
                            CMComplexObjectToDeleted obj = new CMComplexObjectToDeleted(k, indextds, td);
                            p_TDDeletedinTDS.addElement(obj);
                        }
                        if (tds.getM_TestDataCombinations().getM_TestDatas().size() == 0) {
                            int idTDS = tds.getId();
                            p_TestDataSetDeleted.addElement(tds);
                            p_TestDataSetIndex.addElement(new Integer(k));
                            ce.addEdit(m_gridTDStructure.getTDStructure().deleteIdTestDataSet(idTDS));
                            String name = tds.getName();
                            ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetFromResultComparison(this.m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
                            m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetActual().remove(name);
                            ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetTargetFromResultComparison(this.m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
                            m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetTarget().remove(name);
                            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementTestDataSetAtFromTestDataSet(m_gridTDStructure.getTDStructure(),k));
                            m_gridTDStructure.getTDStructure().getM_TestDataSet().removeElementAt(k);
                        }
                    }
                }
            }

            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),CMIndexTDStructureUpdate.getInstance().getIndex() - 1));
            CMIndexTDStructureUpdate.getInstance().setindex(CMIndexTDStructureUpdate.getInstance().getIndex() - 1);
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeNumOfColumnsDinamicInGridTDStructure(m_gridTDStructure,12));
            m_gridTDStructure.setNumofcolumnsDinamic(12);

        	ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    	            if (testData.getM_StructureTestData().size() == 0) {
    	                m_frame.statesMenusTDStructureEditDelete(false);
    	                m_frame.stateInsertColumnOptionsinStructure(false);
    	            }
    	            if (testData.getM_TestDataSet().size() == 0) {
    	            	m_frame.statesMenusTestDataSetEditDeleteReport(false);
    	            }
    	            if (testData.getTestDataCombination().getM_TestDatas().size() == 0) {
    	            	m_frame.statesMenusTestDataEditDeleteAssign(false);
    	            }
    	            m_gridTDStructure.update(0);
    	        	m_panelTDStructureView.update();
    	        	m_panelTestDataView.update();
    	        	m_panelTestDataSetView.update();
    	        	//m_panelTDStructureView.getScrollPaneStructureDescriptionView().getM_cmStructureView().gainFocusWhenAintRowLeft();
    			}

    		});

            if (testData.getM_StructureTestData().size() == 0) {
                m_frame.statesMenusTDStructureEditDelete(false);
                m_frame.stateInsertColumnOptionsinStructure(false);
            }
            if (testData.getM_TestDataSet().size() == 0) {
            	m_frame.statesMenusTestDataSetEditDeleteReport(false);
            }
            if (testData.getTestDataCombination().getM_TestDatas().size() == 0) {
            	m_frame.statesMenusTestDataEditDeleteAssign(false);
            }
            m_gridTDStructure.update(0);
        	m_panelTDStructureView.update();
        	m_panelTestDataView.update();
        	m_panelTestDataSetView.update();
            /*m_frame.getM_CMUndoMediator().deleteStructureAt(m_gridTDStructure, p_ResultCompariosnActual, p_ResultComparisonTarget,
                p_TestDataSetDeleted, p_TestDataSetIndex, p_TestDataDeleted, p_TestDataIndex, structTD, index,
                p_TDDeletedinTDS, p_StDeletedinTD, m_frame.getm_TabbedPaneView(),
                CMMessages.getString("TESTDATA_TDSTRUCTURE"));*/
        }
        return ce;
    }

}
