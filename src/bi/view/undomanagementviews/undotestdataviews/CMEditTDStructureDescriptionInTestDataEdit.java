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

public class CMEditTDStructureDescriptionInTestDataEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMGridTDStructure;
    private int m_IndexTestData;
    private int m_indexStructure;
    private StructureTestData m_StructureTestData;
    private String m_Description;
    private String m_OldDescription;
    private String m_Type;
    private String m_OldType;

    public CMEditTDStructureDescriptionInTestDataEdit(CMFrameView p_CMFrameView,CMPanelTestDataView p_CMGridTDStructure, int p_IndexTestData, int p_indexStructure, String p_Description, String p_OldDescription,String p_Type, String p_OldType, StructureTestData p_StructureTestData, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Description  = p_Description;
        this.m_OldDescription = p_OldDescription;
        this.m_Type  = p_Type;
        this.m_OldType = p_OldType;
        this.m_IndexTestData = p_IndexTestData;
        this.m_indexStructure=p_indexStructure;
        this.m_StructureTestData = p_StructureTestData;

    }

    public void undo() throws CannotUndoException {
        super.undo();
        m_CMGridTDStructure.changeStructureDescriptionInTestData(m_IndexTestData,m_indexStructure, m_OldDescription,m_OldType, m_StructureTestData);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();

    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMGridTDStructure.changeStructureDescriptionInTestData(m_IndexTestData,m_indexStructure, m_Description,m_Type, m_StructureTestData);
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
