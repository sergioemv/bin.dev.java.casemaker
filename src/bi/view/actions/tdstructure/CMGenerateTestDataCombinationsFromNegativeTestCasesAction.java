/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.actions.tdstructure;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.StructureTestData;
import model.TDStructure;
import model.TestDataCombinations;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogIntervalValuesChooser;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.testdataviews.CMPanelTDStructureView;
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
public class CMGenerateTestDataCombinationsFromNegativeTestCasesAction extends
		AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTDStructureView m_panelTDStructureView;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;


	public CMGenerateTestDataCombinationsFromNegativeTestCasesAction(){
		super(CMMessages.getString("TESTDATA_GENERATE_NEGATIVE_TESTDATA"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_GENERATE_NEGATIVE_TESTDATA"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_NEGATIVE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("GENERATE_TEST_DATA_COMBINATIONS_FROM_THE_NEGATIVE_TEST_CASES_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		this.m_panelTDStructureView = m_frame.getPanelTDStructureView();
		this.m_panelTestDataSetView = m_frame.getPanelTestDataSetView();
		this.m_panelTestDataView = m_frame.getPanelTestDataView();
		CMCompoundEdit ce = new CMCompoundEdit();

        TDStructure tdStructure = this.m_frame.getTreeWorkspaceView().getSelectedTDStructure();
        
        ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				//CMIndexTDStructureUpdate.getInstance().setindex(0);
	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
	            m_panelTDStructureView.update();
	            //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
	            m_panelTestDataView.update();
	            m_panelTestDataSetView.update();
	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}

		});
		ce.addEdit(new CMComponentAwareEdit());
        
        
        this.m_gridTDStructure.setTDStructure(tdStructure);
        StructureTestData createStructure= new StructureTestData();
        //int index=0;
        int confirmation = JOptionPane.YES_OPTION;
        if(this.m_gridTDStructure.existTestCase())
        {
        if (tdStructure.getTestDataCombination().getM_TestDatas().size() > 0
        		|| tdStructure.getM_StructureTestData().size()>0) {
            confirmation = JOptionPane.showConfirmDialog(this.m_frame,
            		CMMessages.getString("TESTDATA_MESSAGE_DELETE_ALL_TESTDATA"),
                CMMessages.getString("TESTDATA_TITLE_MESSAGE_DELETE_NEGATIVE_TESTDATA"), JOptionPane.YES_NO_CANCEL_OPTION);
        }
        this.m_frame.setWaitCursor(true);
        if (confirmation == JOptionPane.YES_OPTION) {

        	int intervalAmount=2;
            CMDialogIntervalValuesChooser cmd= new CMDialogIntervalValuesChooser(CMApplication.frame);
            cmd.setVisible(true);
            if(cmd.isOkSelected()){
            	intervalAmount=cmd.getAmountOfIntervalValues();
            }
            else{
            	this.m_frame.setWaitCursor(false);
            	return;
            }
        	//TDStructure clontdStructure =tdStructure.cloneTDStructure();
            ce.addEdit(tdStructure.deleteIds());
            Vector std = new Vector();
            ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStructureTestDataInTDStructure(tdStructure,std));
            tdStructure.setM_StructureTestData(std);
            TestDataCombinations tdc = new TestDataCombinations();
            ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestdataCOmbinationToTDStructureModelEdit(tdStructure,tdc));
            tdStructure.setTestDataCombination(tdc);
            Vector testdataset = new Vector();
            ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestDataSetToTDStructureModelEdit(tdStructure,testdataset));
            tdStructure.setM_TestDataSet(testdataset);

			createStructure=this.m_gridTDStructure.insertDataTDStructureView(ce);
            //index =this.m_gridTDStructure.getTDStructure().getM_StructureTestData().size()-1;

            this.m_gridTDStructure.GenerateTDStructureForNegativeTestCase(createStructure,ce, intervalAmount);
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),0));
            CMIndexTDStructureUpdate.getInstance().setindex(0);
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexTestDataInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),0));
            CMIndexTDStructureUpdate.getInstance().setindexTestData(0);

            ce.addEdit(CMViewEditFactory.INSTANCE.createAddTDCombinationToPanelTDViewViewEdit(m_panelTestDataView,tdStructure.getTestDataCombination()));
            m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
            
            ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				//CMIndexTDStructureUpdate.getInstance().setindex(0);
    	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
    	            m_panelTDStructureView.update();
    	            //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
    	            m_panelTestDataView.update();
    	            m_panelTestDataSetView.update();
    	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}

    		});
            
            
            m_panelTDStructureView.update();
            m_panelTestDataView.update();
            m_panelTestDataSetView.update();
            
            if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);

            /*this.m_frame.getM_CMUndoMediator().generateNegDeletedTDStructure(this.m_gridTDStructure,clontdStructure,
            		index,this.m_frame.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TDSTRUCTURE"));*/

        }
        else if(confirmation ==JOptionPane.NO_OPTION)
        {
        	int intervalAmount=2;
            CMDialogIntervalValuesChooser cmd= new CMDialogIntervalValuesChooser(CMApplication.frame);
            cmd.setVisible(true);
            if(cmd.isOkSelected()){
            	intervalAmount=cmd.getAmountOfIntervalValues();
            }
            else{
            	this.m_frame.setWaitCursor(false);
            	return;
            }
			createStructure=this.m_gridTDStructure.insertDataTDStructureView(ce);
            //index =this.m_gridTDStructure.getTDStructure().getM_StructureTestData().size()-1;
            this.m_gridTDStructure.GenerateTDStructureForNegativeTestCase(createStructure,ce, intervalAmount);
            
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),0));
            CMIndexTDStructureUpdate.getInstance().setindex(0);
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexTestDataInCMIndexTDStructureUpdate(CMIndexTDStructureUpdate.getInstance(),0));
            CMIndexTDStructureUpdate.getInstance().setindexTestData(0);

            ce.addEdit(CMViewEditFactory.INSTANCE.createAddTDCombinationToPanelTDViewViewEdit(m_panelTestDataView,tdStructure.getTestDataCombination()));
            m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
            
            ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				//CMIndexTDStructureUpdate.getInstance().setindex(0);
    	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
    	            m_panelTDStructureView.update();
    	            //m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
    	            m_panelTestDataView.update();
    	            m_panelTestDataSetView.update();
    	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}

    		});
            
            
            m_panelTDStructureView.update();
            m_panelTestDataView.update();
            m_panelTestDataSetView.update();
            
            if(ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(ce);

            /*this.m_frame.getM_CMUndoMediator().generateNegTDStructure(this.m_gridTDStructure,index,
            		this.m_frame.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TDSTRUCTURE"));*/
        }
        this.m_frame.setWaitCursor(false);
	}
	}
}
