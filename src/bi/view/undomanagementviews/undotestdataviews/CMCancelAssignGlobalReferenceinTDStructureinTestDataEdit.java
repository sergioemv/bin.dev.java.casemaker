/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotestdataviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMCancelAssignGlobalReferenceinTDStructureinTestDataEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMPanelTestDataView;
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_IndexTestData;
    private int m_indexStructure;

    private ITypeData m_OldtypeData;
    private int m_numoftable;
    private int m_numofrow;
    private int m_row;
    private int m_column;

    public CMCancelAssignGlobalReferenceinTDStructureinTestDataEdit(CMFrameView p_CMFrameView,CMPanelTestDataView p_CMPanelTestDataView,CMGridTDStructure p_CMGridTDStructure,
        int p_IndexTestData,int p_indexStructure,  ITypeData p_OldTypeData,  int p_numoftable,int p_numofrow,
        int p_row, int p_column, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_CMPanelTestDataView=p_CMPanelTestDataView;
        this.m_IndexTestData = p_IndexTestData;
        this.m_indexStructure=p_indexStructure;
        this.m_OldtypeData=p_OldTypeData;
        this.m_numoftable=p_numoftable;
        this.m_numofrow=p_numofrow;
        this.m_row=p_row;
        this.m_column=p_column;
    }

    public void redo() throws CannotUndoException {
        super.redo();
        ITypeData aux=(ITypeData) m_OldtypeData.clone();
        aux.setGlobal("");
        int globalindex =m_CMPanelTestDataView.undoGlobalReferenceinTestData(m_IndexTestData,m_indexStructure,m_row, m_column,aux);
   	 	m_CMGridTDStructure.ifcancelReferenceInTestDataCancelinStructure(globalindex, m_row-1);
         m_CMPanelTestDataView.update(m_IndexTestData,m_indexStructure);
     	int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void undo() throws CannotRedoException {
        super.undo();
         ITypeData m_typ=m_CMGridTDStructure.setGlobalReferenceStructure(m_numoftable, m_numofrow,null);
        m_CMPanelTestDataView.redoGLobalReference(m_IndexTestData,m_indexStructure,m_row, m_column,m_indexStructure,(ITypeData)m_OldtypeData.clone());
        m_CMPanelTestDataView.update(m_IndexTestData,m_indexStructure);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
       setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
