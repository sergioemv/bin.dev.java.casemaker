/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotdstructureviews;

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMAssigGlobalReferenceInTDSructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private int m_row;
    private int m_column;
    private String valueRedo="";
    private String valueUndo="G";
    private Vector m_OldReferenceInTestData;
    private CMCompoundEdit m_ce;//svonborries_09032006

    public CMAssigGlobalReferenceInTDSructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,
        int p_Index, int p_row, int p_column, Vector p_OldReferenceInTestData,CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_edit) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Index = p_Index;
        this.m_row=p_row;
        this.m_column=p_column;
        this.m_OldReferenceInTestData=p_OldReferenceInTestData;
        this.m_ce = (CMCompoundEdit) p_edit;//svonborries_09032006
    }

    public void redo() throws CannotUndoException {
        super.redo();
//      svonborries_09032006_begin
        if(m_ce.isInProgress())
        	m_ce.endAll();
        m_ce.redo();
        //svonborries_09032006_end

        m_CMGridTDStructure.undoRedoReferenceGlobalInTDStructure(m_row,m_column,m_Index,valueUndo, m_OldReferenceInTestData);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();

    }

    public void undo() throws CannotRedoException {
        super.undo();
//      svonborries_09032006_begin
        if(m_ce.isInProgress())
        	m_ce.endAll();
        m_ce.undo();
        //svonborries_09032006_end
        m_CMGridTDStructure.undoRedoReferenceGlobalInTDStructure(m_row,m_column,m_Index,valueRedo, m_OldReferenceInTestData);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */