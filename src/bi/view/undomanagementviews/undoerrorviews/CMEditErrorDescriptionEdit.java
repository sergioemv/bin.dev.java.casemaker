/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undoerrorviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.CMError;
import bi.view.errorviews.CMErrorGridView;
import bi.view.mainframeviews.CMFrameView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMEditErrorDescriptionEdit extends CMComponentAwareEdit {
    private CMErrorGridView m_CMErrorGridView;
    private int m_Index;
    private CMError m_CMError;
    private String m_Description;
    private String m_OldDescription;

    public CMEditErrorDescriptionEdit(CMFrameView p_CMFrameView,CMErrorGridView p_CMErrorGridView, int p_Index, String p_Description, String p_OldDescription, CMError p_CMError, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMErrorGridView =  p_CMErrorGridView;
        this.m_Description  = p_Description;
        this.m_OldDescription = p_OldDescription;
        this.m_Index = p_Index;
        this.m_CMError = p_CMError;
    }

    public void undo() throws CannotUndoException {
        super.undo();
        boolean withConfirmation = false;
        m_CMErrorGridView.changeErrorDescription(m_Index, m_OldDescription, m_CMError);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();

    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMErrorGridView.changeErrorDescription(m_Index, m_Description, m_CMError);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);

        setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
