/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotestdataviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMAssignGlobalValueinTestDataEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMPanelTestDataView;
    private int m_IndexTestData;
    private int m_indexStructure;
    private ITypeData m_TypeData;
    private ITypeData m_OldtypeData;
    private int m_numoftable;
    private int m_row;
    private int m_column;

    public CMAssignGlobalValueinTestDataEdit(CMFrameView p_CMFrameView,CMPanelTestDataView p_CMPanelTestDataView, int p_IndexTestData,int p_indexStructure,  ITypeData p_OldTypeData,ITypeData p_TypeData,int p_numoftable,int p_row, int p_column, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMPanelTestDataView=p_CMPanelTestDataView;
        this.m_IndexTestData = p_IndexTestData;
        this.m_indexStructure=p_indexStructure;
        this.m_OldtypeData=p_OldTypeData;
        this.m_TypeData=p_TypeData;
        this.m_numoftable=p_numoftable;
        this.m_row=p_row;
        this.m_column=p_column;
    }

    public void undo() throws CannotUndoException {
        super.undo();
		m_CMPanelTestDataView.setTypeData(m_numoftable, m_row,m_column, m_OldtypeData,m_IndexTestData,m_indexStructure);
       	int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMPanelTestDataView.setTypeData(m_numoftable, m_row,m_column, m_TypeData,m_IndexTestData,m_indexStructure);
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
