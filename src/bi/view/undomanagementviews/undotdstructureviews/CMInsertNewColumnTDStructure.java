package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMInsertNewColumnTDStructure extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;   
	private int m_IndexStructure;
	private int indexSTD;
	private String m_columnName;
	private boolean swUndo = false;
	private boolean swRedo = false;
    public CMInsertNewColumnTDStructure(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure, int p_IndexStructure, int indexSTD, String columnName, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.indexSTD = indexSTD;
        this.m_CMGridTDStructure =  p_CMGridTDStructure;      
        this.m_IndexStructure=p_IndexStructure;
        this.m_columnName=columnName;
    }

    public void undo() throws CannotUndoException {
        super.undo();
        boolean withConfirmation = false;
        swUndo = true;
        m_CMGridTDStructure.deleteNewColumnTDStructure(swUndo,m_IndexStructure, indexSTD);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()- 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(indexSTD);
		 m_CMGridTDStructure.update(indexSTD);
        setUndoState();
        setRedoState();
        swUndo = false;

    }

    public void redo() throws CannotRedoException {
        super.redo();
        swRedo = true;
        
        m_CMGridTDStructure.insertNewColumnforRedo(swRedo, m_columnName, m_IndexStructure,indexSTD);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(indexSTD);
		 m_CMGridTDStructure.update(indexSTD);
        setUndoState();
        setRedoState();
        swRedo = false;
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
