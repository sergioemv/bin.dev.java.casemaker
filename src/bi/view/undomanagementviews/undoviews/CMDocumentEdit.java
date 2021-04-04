/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */

package bi.view.undomanagementviews.undoviews;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMApplication;

/**
 * A wrapper edit object that encapsulates another edit.  The idea is
 * that whenever this edit is asked to be undone or redone, it first
 * sets focus as directed by a FocusInfo object then undoes or redos by
 * delegating to the internal edit object.
 */
class CMDocumentEdit extends CMComponentAwareEdit {
    private UndoableEdit edit;

    public CMDocumentEdit(UndoableEdit edit,
                        CMUndoMediator.FocusInfo focusInfo) {
        super(focusInfo, CMApplication.frame);
        this.edit = edit;
        if (!edit.isSignificant()) return;
        m_CMFrameView.getBusinessRulesPanelView().getCMBusinessRuleEditor().updateLinesNumbers();
    }

    public void undo() throws CannotUndoException {
        super.undo();
        edit.undo();
        m_CMFrameView.getBusinessRulesPanelView().getCMBusinessRuleEditor().updateLinesNumbers();

        int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()-1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        edit.redo();
        m_CMFrameView.getBusinessRulesPanelView().getCMBusinessRuleEditor().updateLinesNumbers();

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
