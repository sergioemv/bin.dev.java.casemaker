/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undoerrorviews;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.CMError;
import bi.view.errorviews.CMErrorGridView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;

public class CMCreateErrorEdit extends CMComponentAwareEdit {
    private CMErrorGridView m_CMErrorGridView;
    private int m_Index;
    private CMError m_CMError;

    public CMCreateErrorEdit(int p_Index, CMError p_CMError) {
        super();
        this.m_CMErrorGridView =  m_CMFrameView.getCMErrorGridView();
        this.m_CMError  = p_CMError;
        this.m_Index = p_Index;
    }

    public void undo() throws CannotUndoException {
        super.undo();
        boolean withConfirmation = false;
        m_CMErrorGridView.deleteErrorAt(m_Index, withConfirmation);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMErrorGridView.addErrorAt(m_Index, m_CMError);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);

        setUndoState();
        setRedoState();
    }
}
