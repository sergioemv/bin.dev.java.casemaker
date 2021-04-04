package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;import bi.view.undomanagementviews.undoviews.CMUndoMediator;
;

/**
 * @author svonborries
 * Modofy to be adapted to the new model of undo/redo
 *
 */
@SuppressWarnings("serial")
public class CMCancelAssignFormatinTDStructureEdit extends CMComponentAwareEdit {
	 private CMGridTDStructure m_CMGridTDStructure;
	    private int m_Index;
	    private int m_row;
	    private int m_column;

	    private CMCompoundEdit m_ce;

	    public CMCancelAssignFormatinTDStructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,
	        int p_Index, int p_row, int p_column,CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_ce) {
	        super(p_FocusInfo, p_CMFrameView);
	        this.m_CMGridTDStructure =  p_CMGridTDStructure;
	        this.m_Index = p_Index;
	        this.m_row=p_row;
	        this.m_column=p_column;
	        this.m_ce = (CMCompoundEdit) p_ce;

	    }

	    public void undo() throws CannotUndoException {
	        super.undo();
	        if(m_ce.isInProgress())
	        	m_ce.endAll();
	        m_ce.undo();
	        m_CMGridTDStructure.undosetFormula(m_row,m_column,m_Index);
			int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()- 1;
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
	        if(m_ce.isInProgress())
	        	m_ce.endAll();
	        m_ce.redo();
	        m_CMGridTDStructure.undosetFormula(m_row,m_column,m_Index);
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
