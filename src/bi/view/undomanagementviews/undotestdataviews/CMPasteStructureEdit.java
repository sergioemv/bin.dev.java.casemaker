/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotestdataviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMPasteStructureEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMPanelTestDataView;
    private int m_IndexTestData;
	private StructureTestData m_StructureTestData;

    public CMPasteStructureEdit(CMFrameView p_CMFrameView,CMPanelTestDataView p_CMPanelTestDataView,int p_IndexTestData,StructureTestData p_StructureTestData,CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMPanelTestDataView=p_CMPanelTestDataView;
        this.m_IndexTestData = p_IndexTestData;
		this.m_StructureTestData=p_StructureTestData;
    }

    public void undo() throws CannotUndoException {
        super.undo();
		m_CMPanelTestDataView.undoAssignStructure(m_IndexTestData,m_StructureTestData);
     	int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()-1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
        
    }

    public void redo() throws CannotRedoException {
        super.redo();
		m_CMPanelTestDataView.redoAssignStructure(m_IndexTestData,m_StructureTestData);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
       setUndoState();
        setRedoState();
    }

}