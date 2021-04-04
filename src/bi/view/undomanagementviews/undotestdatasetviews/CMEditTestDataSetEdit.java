/* ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
/*******************************************************************************
******************************************************************************  Developed by BUSINESS SOFTWARE INNOVATIONS. .
******************************************************************************  Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotestdatasetviews;
import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMEditTestDataSetEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private Vector m_selected;
    Vector m_oldSelected;
    String m_description;
    String m_oldDescription;

    public CMEditTestDataSetEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,Vector p_Selecteds,String description,Vector cancelSelected,String oldDescription,int index, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Index=index;
        m_selected=p_Selecteds;
        m_description=description;
        m_oldSelected=cancelSelected;
        m_oldDescription=oldDescription;
    }

    public void undo() throws CannotUndoException {
        super.undo();
        boolean withConfirmation = false;
        m_CMGridTDStructure.undoRedoEditTestDataSetAt((Vector)m_oldSelected.clone(),m_oldDescription,m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMGridTDStructure.undoRedoEditTestDataSetAt((Vector)m_selected.clone(),m_description,m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);

        setUndoState();
        setRedoState();
    }
}
