package bi.view.undomanagementviews.undoerrorviews;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.CMError;
import bi.view.errorviews.CMErrorGridView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;

public class CMAssignTestCasesToErrorEdit extends CMComponentAwareEdit{
    CMErrorGridView m_CMErrorGridView;
    int m_Index;
    CMError m_CMError;
    Vector m_OldTestCases;
    Vector m_NewTestCases;


    public CMAssignTestCasesToErrorEdit(int index,CMError error,Vector oldTestCases,Vector newTestCases){
		super();
        m_CMErrorGridView = m_CMFrameView.getCMErrorGridView();
        m_Index =index;
        m_CMError = error;
        m_OldTestCases = oldTestCases;
        m_NewTestCases =newTestCases;
    }

    
     public void undo() throws CannotUndoException {
        super.undo();
        m_CMErrorGridView.changeAssignedTestCasesToError(m_CMError,(Vector)m_OldTestCases.clone(),m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMErrorGridView.changeAssignedTestCasesToError(m_CMError,(Vector)m_NewTestCases.clone(),m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);

        setUndoState();
        setRedoState();
    }
}