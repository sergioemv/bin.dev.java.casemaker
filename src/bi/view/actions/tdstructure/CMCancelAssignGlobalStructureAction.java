/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
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
import bi.view.testdataviews.CMPanelTestDataSetView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMCancelAssignGlobalStructureAction extends AbstractAction
		implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;
	private TDStructure testData;

	public CMCancelAssignGlobalStructureAction(){
		super(CMMessages.getString("TESTDATA_CANCEL_ASSIGNEMENT_TDSTRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString
				("BUTTON_TOOLTIP_CANCEL_ASSIGNEMENT_GLOBAL_STRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_CANCEL_GLOBAL_STRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString
	    		("CANCEL_GLOBAL_STRUCTURE_ASSIGNEMENT_MNEMONIC").charAt(0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		this.testData = m_gridTDStructure.getTDStructure();
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
                m_panelTestDataView.update();
                m_panelTestDataSetView.update();
                if(m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size()!=0)
                {
                	m_frame.stateMenusAssignTdStructure(true);
                }
			}

		});
		ce.addEdit(new CMComponentAwareEdit());


        TestData tds = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().
        elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
        if (tds.getM_TDStructure().getM_StructureTestData().size() > 0) {
            int confirmation = JOptionPane.YES_OPTION;
            confirmation = JOptionPane.showConfirmDialog(this.m_frame,
            		CMMessages.getString("TESTDATA_MESSAGE_DELETE_TDSTRUCTURE"),
                CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_TDSTRUCTURE"),
                JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
				StructureTestData auxSTD=(StructureTestData)tds.getM_TDStructure().getM_StructureTestData().
				elementAt(CMIndexTDStructureUpdate.getInstance().getIndex());
				ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveStructureTestDataFromTDStructure(tds.getM_TDStructure(),CMIndexTDStructureUpdate.getInstance().getIndex()));
                tds.getM_TDStructure().getM_StructureTestData().removeElementAt
                (CMIndexTDStructureUpdate.getInstance().getIndex());
                ce.addEdit(deleteTDStructurefromTestData2(auxSTD));
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
	                    m_panelTestDataView.update();
	                    m_panelTestDataSetView.update();
	                    if(m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size()==0)
	                    {
	                    	m_frame.stateMenusAssignTdStructure(false);
	                    }
	    			}

	    		});


                if (testData.getM_StructureTestData().size() == 0) {
                	this.m_frame.statesMenusTDStructureEditDelete(false);
                	this.m_frame.stateInsertColumnOptionsinStructure(false);
                }
                if (testData.getM_TestDataSet().size() == 0) {
                	this.m_frame.statesMenusTestDataSetEditDeleteReport(false);
                }
                if (testData.getTestDataCombination().getM_TestDatas().size() == 0) {
                	this.m_frame.statesMenusTestDataEditDeleteAssign(false);
                }

                m_gridTDStructure.update(0);
                m_panelTestDataView.update();
                m_panelTestDataSetView.update();
                if(m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size()==0)
                {
                	this.m_frame.stateMenusAssignTdStructure(false);
                }
                if(ce.hasEdits())
		        	CMUndoMediator.getInstance().doEdit(ce);
            }
        }
	}

    @SuppressWarnings("unchecked")
	public UndoableEdit deleteTDStructurefromTestData2(StructureTestData p_StructureTestData) {
    	CMCompoundEdit ce = new CMCompoundEdit();
        //int index = CMIndexTDStructureUpdate.getInstance().getIndex();
        //Vector p_ResultCompariosnActual = (Vector)this.m_gridTDStructure.getTDStructure().getM_ResultComparation().
        //getTestDataSetActual().clone();
        //Vector p_ResultComparisonTarget = (Vector)this.m_gridTDStructure.getTDStructure().getM_ResultComparation().
        //getTestDataSetTarget().clone();
        Vector p_TestDataSetDeleted = new Vector();
        Vector p_TestDataSetIndex = new Vector();
        Vector p_TDDeletedinTDS = new Vector();
        //TestData tdDeleted = (TestData)this.testData.getTestDataCombination().getM_TestDatas().
        //elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
        //int indexTD = CMIndexTDStructureUpdate.getInstance().getindexTestData();
        //boolean isDeletedTestData = false;
        int sizeCombinations = this.testData.getTestDataCombination().getM_TestDatas().size();
        for (int i = sizeCombinations - 1; i >= 0; i--)
        {
            TestData td = (TestData)this.testData.getTestDataCombination().getM_TestDatas().elementAt(i);
            if (td.getM_TDStructure().getM_StructureTestData().size() == 0) {
                int idTD = td.getId();
                ce.addEdit(this.testData.deleteIdTestData(idTD));
                //isDeletedTestData = true;
                ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveTestDataFromTestDataCombination(testData.getTestDataCombination(),i));
                this.testData.getTestDataCombination().getM_TestDatas().removeElementAt(i);
                for (int k = this.m_gridTDStructure.getTDStructure().getM_TestDataSet().size() - 1; k >= 0; k--) {
                    TestDataSet tds = (TestDataSet)this.m_gridTDStructure.getTDStructure().getM_TestDataSet().
                    elementAt(k);
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
                        ce.addEdit(this.m_gridTDStructure.getTDStructure().deleteIdTestDataSet(idTDS));
                        String name = tds.getName();
                        //AQUI ESTAMOS, DE AQUI PA ABAJO MODIFICAR LAS ACCIONES
                        ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetFromResultComparison(this.m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
                        this.m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetActual().
                        remove(name);
                        ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetTargetFromResultComparison(this.m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
                        this.m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetTarget().
                        remove(name);
                        ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementTestDataSetAtFromTestDataSet(m_gridTDStructure.getTDStructure(),k));
                        this.m_gridTDStructure.getTDStructure().getM_TestDataSet().removeElementAt(k);
                    }
                }
            }
        }

        ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),CMIndexTDStructureUpdate.getInstance().getIndex() - 1));
        CMIndexTDStructureUpdate.getInstance().setindex(CMIndexTDStructureUpdate.getInstance().getIndex() - 1);
        /*this.m_frame.getM_CMUndoMediator().cancelAssignTDStructureInTestDataAt(this.m_gridTDStructure,
        		p_ResultCompariosnActual,p_ResultComparisonTarget, p_TestDataSetDeleted, p_TestDataSetIndex,
        		tdDeleted, indexTD, p_StructureTestData, index,isDeletedTestData, p_TDDeletedinTDS,
        		this.m_frame.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TESTDATA"));*/
        return ce;
    }

}
