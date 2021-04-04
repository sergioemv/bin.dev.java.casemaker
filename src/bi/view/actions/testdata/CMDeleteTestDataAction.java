/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.testdata;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import model.TestData;
import model.TestDataSet;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;
import bi.view.actions.CMAction;
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
public class CMDeleteTestDataAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;

	public CMDeleteTestDataAction(){
		super(CMMessages.getString("TESTDATA_DELETE_TESTDATA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_TESTDATA_DELETE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_DELETE_TESTDATA.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("DELETE_TEST_DATA_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		//this.m_gridTDStructure = m_frame.getGridTDStructure();
		m_gridTDStructure = m_frame.getPanelTestDataView().getM_CMGridTDStructure();
		this.m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		CMCompoundEdit ce = new CMCompoundEdit();
		
		ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
	            if(m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size()!=0)
	            {
	            	m_frame.statesMenusTestDataEditDeleteAssign(true);
	            }
	            m_panelTestDataView.update();
	            m_panelTestDataSetView.update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());
		

        int confirmation = JOptionPane.YES_OPTION;
        confirmation = JOptionPane.showConfirmDialog(this.m_frame,
        		CMMessages.getString("TESTDATA_MESSAGE_DELETE_TESTDATA"),
            CMMessages.getString("TESTDATA_TITLE_MENSSAGE_DELETE_TESTDATA"), JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            TestData td = (TestData)m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().
            elementAt(CMIndexTDStructureUpdate.getInstance().getindexTestData());
            //int indexTD=CMIndexTDStructureUpdate.getInstance().getindexTestData();
            //Vector p_ResultCompariosnActual= (Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().
            //getTestDataSetActual().clone();
            //Vector p_ResultComparisonTarget=(Vector)m_gridTDStructure.getTDStructure().getM_ResultComparation().
            //getTestDataSetTarget().clone();
            Vector p_TestDataSetDeleted= new Vector();
            Vector p_TestDataSetIndex= new Vector();
		    Vector p_TDDeletedinTDS= new Vector();
            for (int i =  m_gridTDStructure.getTDStructure().getM_TestDataSet().size()-1;i>=0; i--) {
                TestDataSet tds = (TestDataSet)m_gridTDStructure.getTDStructure().getM_TestDataSet().elementAt(i);
                int indextds=-1;
				boolean b=false;
				String nameTestData = td.getName();
                for(int h=0; h<tds.getM_TestDataCombinations().getM_TestDatas().size();h++)
                {
                    TestData tdaux= (TestData)tds.getM_TestDataCombinations().getM_TestDatas().elementAt(h);
                    if(nameTestData.equals(tdaux.getName()))
                    {
						indextds=h;
                    }

                }
                if(indextds>=0)
                {
                	ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveTestDataFromTestDataCombination(tds.getM_TestDataCombinations(),indextds));
                	tds.getM_TestDataCombinations().getM_TestDatas().remove(indextds);
                	b=true;
                }
                if(b)
                {
                    CMComplexObjectToDeleted obj=new CMComplexObjectToDeleted(i,indextds,td);
                    p_TDDeletedinTDS.addElement(obj);
                }
                if(tds.getM_TestDataCombinations().getM_TestDatas().size()==0)
                {
                    int idTDS=tds.getId();
                    p_TestDataSetDeleted.addElement(tds);
                    p_TestDataSetIndex.addElement(new Integer(i));
                    ce.addEdit(m_gridTDStructure.getTDStructure().deleteIdTestDataSet(idTDS));
                    String name= tds.getName();
                    ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetFromResultComparison(m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
					m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetActual().remove(name);
					ce.addEdit(CMModelEditFactory.INSTANCE.createDeleteTestDataSetTargetFromResultComparison(m_gridTDStructure.getTDStructure().getM_ResultComparation(),name));
                    m_gridTDStructure.getTDStructure().getM_ResultComparation().getTestDataSetTarget().remove(name);
                    ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveElementTestDataSetAtFromTestDataSet(m_gridTDStructure.getTDStructure(),i));
                    m_gridTDStructure.getTDStructure().getM_TestDataSet().removeElementAt(i);
                }
            }
            int idTD = td.getId();
            ce.addEdit(m_gridTDStructure.getTDStructure().deleteIdTestData(idTD));
            ce.addEdit(CMModelEditFactory.INSTANCE.createRemoveTestDataFromTestDataCombination
            		(m_gridTDStructure.getTDStructure().getTestDataCombination(),
            				CMIndexTDStructureUpdate.getInstance().getindexTestData()));
            m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().removeElementAt
            (CMIndexTDStructureUpdate.getInstance().getindexTestData());

            ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    	            if(m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size()==0)
    	            {
    	            	m_frame.statesMenusTestDataEditDeleteAssign(false);
    	            }
    	            m_panelTestDataView.update();
    	            m_panelTestDataSetView.update();
    			}

    		});

            if(m_gridTDStructure.getTDStructure().getTestDataCombination().getM_TestDatas().size()==0)
            {
            	this.m_frame.statesMenusTestDataEditDeleteAssign(false);
            }
            m_panelTestDataView.update();
            m_panelTestDataSetView.update();
            
			if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);
            
            
            /*this.m_frame.getM_CMUndoMediator().deleteTestDataAt(m_gridTDStructure, p_ResultCompariosnActual,
            		p_ResultComparisonTarget, p_TestDataSetDeleted,  p_TestDataSetIndex, td, indexTD,
            		p_TDDeletedinTDS, this.m_frame.getm_TabbedPaneView(),CMMessages.getString("TESTDATA_TESTDATA"));*/
        }
	}
}
