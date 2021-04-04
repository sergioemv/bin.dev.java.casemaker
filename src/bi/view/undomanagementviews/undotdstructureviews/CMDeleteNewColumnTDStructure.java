package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMDeleteNewColumnTDStructure extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;    
    private String m_NameNewColumn;
    
	private int m_IndexStructure;
	private int indexTDS;
	private boolean swRedo = false;
    private boolean swUndo = false;
	
    public CMDeleteNewColumnTDStructure(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure, int p_IndexStructure, int indexTDS, String p_nameNewColumn, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_NameNewColumn = p_nameNewColumn;
        this.m_IndexStructure=p_IndexStructure;
        this.indexTDS = indexTDS;
    }

    public void redo() throws CannotUndoException {
        super.redo();      
        swRedo = true;
        m_CMGridTDStructure.deleteNewColumnTDStructure(swRedo,m_IndexStructure, indexTDS);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(indexTDS);
		 m_CMGridTDStructure.update(indexTDS);
        setUndoState();
        setRedoState();
        swRedo = false;
    }

    public void undo() throws CannotRedoException {
        super.undo();
        swUndo = true;
        m_CMGridTDStructure.insertNewColumnforRedo(swUndo,m_NameNewColumn,m_IndexStructure,indexTDS);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(indexTDS);
		 m_CMGridTDStructure.update(indexTDS);
        setUndoState();
        setRedoState();
        swUndo = false;
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */

