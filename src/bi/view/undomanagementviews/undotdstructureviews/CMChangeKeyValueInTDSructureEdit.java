/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMChangeKeyValueInTDSructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private CMCompoundEdit m_ce;//svonborries_07032006

    public CMChangeKeyValueInTDSructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,
        int p_Index, CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_ce) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Index = p_Index;
        this.m_ce = (CMCompoundEdit) p_ce;//svonborries_07032006
    }

    public void undo() throws CannotUndoException {
        super.undo();
//      svonborries_07032006_begin
        if(this.m_ce.isInProgress())
        	this.m_ce.endAll();
        this.m_ce.undo();
//      svonborries_07032006_end
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();

    }

    public void redo() throws CannotRedoException {
        super.redo();
//      svonborries_07032006_begin
        if(this.m_ce.isInProgress())
        	this.m_ce.endAll();
        this.m_ce.redo();
//      svonborries_07032006_end
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
