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
import javax.swing.undo.UndoableEdit;

import model.BusinessRules;
import model.ITypeData;
import model.StructureTestData;
import model.TDStructure;
import model.TestDataFormat;
import model.TypeDataGlobal;
import model.edit.CMModelEditFactory;
import model.util.CMDelegate;

import bi.view.actions.CMAction;
import bi.view.edit.CMViewEditFactory;
import bi.view.lang.CMMessages;
import bi.view.mainframeviews.CMApplication;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMDialogCreateStructure;
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
public class CMCreateTDStructureAction extends AbstractAction implements Action {

	private CMFrameView m_frame;
	private CMGridTDStructure m_gridTDStructure;
	private int m_cantRows;

	public CMCreateTDStructureAction(){
		super(CMMessages.getString("TESTDATA_CREATE_TDSTRUCTURE"));
		putValue(Action.SHORT_DESCRIPTION, CMMessages.getString("TESTDATA_CREATE_TDSTRUCTURE"));
	    putValue(Action.SMALL_ICON, CMAction.TESTDATA_CREATE_TDSTRUCTURE.getIcon());
	    putValue(Action.MNEMONIC_KEY,(int)CMMessages.getString("CREATE_STRUCTURE_MNEMONIC").charAt(0));
	    putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, Event.CTRL_MASK));
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		this.m_frame = CMApplication.frame;
		this.m_gridTDStructure = m_frame.getGridTDStructure();
		CMCompoundEdit ce = new CMCompoundEdit();
		m_cantRows = 0;

        TDStructure testData = m_frame.getTreeWorkspaceView().getSelectedTDStructure();

        ce.addDelegateEdit(new CMDelegate(){

			public void execute() {

	            m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
	            m_frame.getPanelTDStructureView().update();
			}

		});
		ce.addEdit(new CMComponentAwareEdit());

        m_gridTDStructure.setTDStructure(testData);
        initmanualStructureCreate(ce);

	}


    public void initmanualStructureCreate(UndoableEdit p_ce) {
    	CMCompoundEdit ce;
    	if(p_ce == null)
    		ce = new CMCompoundEdit();
    	else
    		ce = (CMCompoundEdit) p_ce;
        StructureTestData newStructureTestData = new StructureTestData();
        ce.addEdit(m_gridTDStructure.setNameDescription(newStructureTestData));
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeTypeInStructureTestData(newStructureTestData,BusinessRules.TYPETDSTRUCTURE_PRIMARY));
        newStructureTestData.setType(BusinessRules.TYPETDSTRUCTURE_PRIMARY);
        int globalIndex = m_gridTDStructure.getTDStructure().getM_StructureTestData().size();
        ce.addEdit(CMModelEditFactory.INSTANCE.createChangeGlobalIndexInStructureTestData(newStructureTestData,globalIndex));
        newStructureTestData.setGlobalIndex(globalIndex);
        CMDialogCreateStructure cmd = new CMDialogCreateStructure(m_frame, newStructureTestData, m_gridTDStructure);
        cmd.setVisible(true);
        m_cantRows = CMDialogCreateStructure.m_cantRows;
        manualStructureCreate(m_cantRows,newStructureTestData,ce);

        ce.addDelegateEdit(new CMDelegate(){

			public void execute() {
				m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
		        m_frame.getPanelTDStructureView().update();
			}

		});

        m_gridTDStructure.update(CMIndexTDStructureUpdate.getInstance().getIndex());
        m_frame.getPanelTDStructureView().update();


        if(ce.hasEdits())
        	CMUndoMediator.getInstance().doEdit(ce);

    }

    @SuppressWarnings("unchecked")
	public void manualStructureCreate(int rows, StructureTestData p_StructureTestData,UndoableEdit p_ce) {
    	CMCompoundEdit ce;
    	if(p_ce == null)
    		ce = new CMCompoundEdit();
    	else
    		ce = (CMCompoundEdit) p_ce;
        if (rows > 0) {
            for (int i = 1; i <= rows; i++) {
                ITypeData newTypeData = new TypeDataGlobal();
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatInTypeDataModelEdit(newTypeData,BusinessRules.FORMULAS_FORMAT_STRING));
                newTypeData.setFormat(BusinessRules.FORMULAS_FORMAT_STRING);
                TestDataFormat tdFormat = new TestDataFormat();
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFormatterInTypeDataModelEdit(newTypeData,tdFormat));
                newTypeData.setFormatter(tdFormat);
                String typedataName = CMMessages.getString("TESTDATA_DEFAULT_NAME") + " " + Integer.toString(i) + ">";
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeNameInTypeDataModelEdit(newTypeData,typedataName));
                newTypeData.setName(typedataName);
                String typedataField = CMMessages.getString("TESTDATA_DEFAULT_FIELD") + " " + Integer.toString(i) + ">";
                ce.addEdit(CMModelEditFactory.INSTANCE.createChangeFieldInTypeDataModelEdit(newTypeData,typedataField));
                newTypeData.setField(typedataField);
                ce.addEdit(CMModelEditFactory.INSTANCE.createAddTypeDataToStructureTestDataModelEdit(p_StructureTestData,newTypeData));
                p_StructureTestData.getTypeData().addElement(newTypeData);
                newTypeData.setStructureTestData(p_StructureTestData);
            }
            ce.addEdit(CMModelEditFactory.INSTANCE.createAddStructureTestDataToTDStructureModelEdit(m_gridTDStructure.getTDStructure(),p_StructureTestData));
            m_gridTDStructure.getTDStructure().getM_StructureTestData().addElement(p_StructureTestData);
            /*m_frame.getM_CMUndoMediator().addTDStructureAt(m_gridTDStructure, m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1,
                p_StructureTestData, m_frame.getm_TabbedPaneView(), CMMessages.getString("TESTDATA_TDSTRUCTURE"));*/
            ce.addEdit(CMViewEditFactory.INSTANCE.createChangeIndexInCMIndexTDStructureUpdate
            		(CMIndexTDStructureUpdate.getInstance(),m_gridTDStructure.getTDStructure()
            				.getM_StructureTestData().size() - 1));
            CMIndexTDStructureUpdate.getInstance().setindex(m_gridTDStructure.getTDStructure().getM_StructureTestData().size() - 1);

        }
    }

}
