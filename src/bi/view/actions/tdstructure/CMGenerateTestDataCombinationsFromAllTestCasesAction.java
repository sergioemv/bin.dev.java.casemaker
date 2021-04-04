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
import bi.controller.StructureManager;
import bi.view.actions.CMAction;
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
public class CMGenerateTestDataCombinationsFromAllTestCasesAction extends
		AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private CMPanelTDStructureView m_panelTDStructureView;
	private CMPanelTestDataView m_panelTestDataView;
	private CMPanelTestDataSetView m_panelTestDataSetView;
	private StructureTestData createStructure;

	public CMGenerateTestDataCombinationsFromAllTestCasesAction(){
		super(CMMessages.getString("TDSTRUCTURE_CONTEXMENU_ALL"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("BUTTON_TOOLTIP_STRUCTURE_GENERATE_ALL"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_GENERATE_TESTDATA_FROM_ALL_TESTCASES.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("GENERATE_TEST_DATA_COMBINATIONS_FROM_ALL_THE_EXISTING_TEST_CASES_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, Event.CTRL_MASK));
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
		CMCompoundEdit m_ce = new CMCompoundEdit();
		final TDStructure tdStructure = m_frame.getTreeWorkspaceView().getSelectedTDStructure();
		m_ce.addUndoDelegateEdit(new CMDelegate(){

			public void execute() {
				CMIndexTDStructureUpdate.getInstance().setindex(0);
	            CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
	            m_panelTDStructureView.update();
	            m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
	            m_panelTestDataView.update();
	            m_panelTestDataSetView.update();
	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
			}

		});
		m_ce.addEdit(new CMComponentAwareEdit());


        m_gridTDStructure.setTDStructure(tdStructure);
        //1. create the structure 
        createStructure= new StructureTestData();
        //int index=0;

        int confirmation = JOptionPane.YES_OPTION;
        if(m_gridTDStructure.existTestCase())
        {
        if (tdStructure.getTestDataCombination().getM_TestDatas().size() > 0 || tdStructure.getM_StructureTestData().size()>0) {
            confirmation = JOptionPane.showConfirmDialog(m_frame, CMMessages.getString("TESTDATA_MESSAGE_DELETE_ALL_TESTDATA"),
                CMMessages.getString("TESTDATA_TITLE_MESSAGE_DELETE_ALL_TESTDATA"), JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$ //$NON-NLS-2$
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
        	//TDStructure clonteStructure =tdStructure.cloneTDStructure();
            m_ce.addEdit(tdStructure.deleteIds());
            //aqui
            Vector std = new Vector();
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createChangeStructureTestDataInTDStructure(tdStructure,std));
            tdStructure.setM_StructureTestData(std);
            TestDataCombinations tdc = new TestDataCombinations();
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestdataCOmbinationToTDStructureModelEdit(tdStructure,tdc));
            tdStructure.setTestDataCombination(tdc);
            Vector testdataset = new Vector();
            m_ce.addEdit(CMModelEditFactory.INSTANCE.createAddTestDataSetToTDStructureModelEdit(tdStructure,testdataset));
            tdStructure.setM_TestDataSet(testdataset);
			createStructure=m_gridTDStructure.insertDataTDStructureView(m_ce);
            //index =m_gridTDStructure.getTDStructure().getM_StructureTestData().size()-1;
//			int sizeCompound = m_ce.getSize();
        	m_gridTDStructure.GenerateAllTDStructure(createStructure,m_ce,intervalAmount);
//        	if(sizeCompound == m_ce.getSize()){
//        		m_ce.undo();
//        		return;
//        	}
        		


    		m_ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				//CMIndexTDStructureUpdate.getInstance().setindex(0);
    	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
    	            m_panelTDStructureView.update();
    	            m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
    	            m_panelTestDataView.update();
    	            m_panelTestDataSetView.update();
    	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}

    		});

	        CMIndexTDStructureUpdate.getInstance().setindex(0);
	        CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
	        m_panelTDStructureView.update();
	        m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
	        m_panelTestDataView.update();
	        m_panelTestDataSetView.update();
			CMGenerateResultStructureAction.IS_CALLED_FROM_OUTSIDE = true;


			if(m_ce.hasEdits())
	        	CMUndoMediator.getInstance().doEdit(m_ce);

			if(StructureManager.getSelectedStructure().hasExpectedResults()){
				CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getAction().actionPerformed(null);
				CMUndoMediator.getInstance().mergeLastEdits(2);
			}
        }
        else if(confirmation==JOptionPane.NO_OPTION)
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
			createStructure=m_gridTDStructure.insertDataTDStructureView(m_ce);
           // index =m_gridTDStructure.getTDStructure().getM_StructureTestData().size()-1;
//			int sizeCompound = m_ce.getSize();
			m_gridTDStructure.GenerateAllTDStructure(createStructure,m_ce, intervalAmount);
//			if(sizeCompound == m_ce.getSize()){
//        		m_ce.undo();
//        		return;
//        	}

    		m_ce.addDelegateEdit(new CMDelegate(){

    			public void execute() {
    				//CMIndexTDStructureUpdate.getInstance().setindex(0);
    	            //CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
    	            m_panelTDStructureView.update();
    	            m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
    	            m_panelTestDataView.update();
    	            m_panelTestDataSetView.update();
    	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
    			}

    		});

            CMIndexTDStructureUpdate.getInstance().setindex(0);
            CMIndexTDStructureUpdate.getInstance().setindexTestData(0);
            m_panelTDStructureView.update();
            m_panelTestDataView.setM_TestDataCombinations(tdStructure.getTestDataCombination());
            m_panelTestDataView.update();
            m_panelTestDataSetView.update();
            CMGenerateResultStructureAction.IS_CALLED_FROM_OUTSIDE = true;


            if(m_ce.hasEdits())
            	CMUndoMediator.getInstance().doEdit(m_ce);


            if(StructureManager.getSelectedStructure().hasExpectedResults()){
            	CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getAction().actionPerformed(null);
            	CMUndoMediator.getInstance().mergeLastEdits(2);
            }
        }


        this.createStructure = null;
        CMGenerateResultStructureAction.IS_CALLED_FROM_OUTSIDE = false;
        this.m_frame.setWaitCursor(false);
        }



	}

	/**
	 * @return Returns the createStructure.
	 * svonborries
	 */
	public StructureTestData getCreateStructure() {
		return createStructure;
	}

	/**
	 * @param createStructure The createStructure to set.
	 * svonborries
	 */
	public void setCreateStructure(StructureTestData createStructure) {
		this.createStructure = createStructure;
	}

}
