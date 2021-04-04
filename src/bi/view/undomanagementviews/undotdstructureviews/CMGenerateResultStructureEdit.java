/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;

import bi.view.actions.CMAction;
import bi.view.mainframeviews.CMApplication;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
public class CMGenerateResultStructureEdit extends CMComponentAwareEdit {

	private CMGridTDStructure m_gridTDStructure;
	private int index;
	
	public CMGenerateResultStructureEdit(CMGridTDStructure p_CMGridTDStructure, int p_Index, 
			CMUndoMediator.FocusInfo p_FocusInfo){
		super(p_FocusInfo,CMApplication.frame);
		this.m_gridTDStructure = p_CMGridTDStructure;
		this.index = p_Index;
	}
	
	/* (non-Javadoc)
	 * @see bi.view.undomanagementviews.undoviews.CMComponentAwareEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		CMAction.TESTDATA_GENERATE_RESULT_STRUCTURE.getAction().actionPerformed(null);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(index);
		m_gridTDStructure.update(index);
        setUndoState();
        setRedoState();
	}

	/* (non-Javadoc)
	 * @see bi.view.undomanagementviews.undoviews.CMComponentAwareEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		StructureTestData std = (StructureTestData) m_gridTDStructure.getTDStructure().getM_StructureTestData().elementAt(index);
		//std.get
		m_gridTDStructure.getTDStructure().getM_StructureTestData().remove(index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        if(index>0)
        {
        	m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(index-1);
        	m_gridTDStructure.update(index-1);
        }
        else
        {
             m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(0);
             m_gridTDStructure.update(0);
        }

        setUndoState();
        setRedoState();
	}

}
