/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMEditTDStructureDescriptionEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private StructureTestData m_StructureTestData;
    private String m_Description;
    private String m_OldDescription;
    private String m_Type;
    private String m_OldType;

    public CMEditTDStructureDescriptionEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure, int p_Index, String p_Description, String p_OldDescription,String p_Type, String p_OldType, StructureTestData p_StructureTestData, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Description  = p_Description;
        this.m_OldDescription = p_OldDescription;
        this.m_Type  = p_Type;
        this.m_OldType = p_OldType;
        this.m_Index = p_Index;
        this.m_StructureTestData = p_StructureTestData;

    }

    public void undo() throws CannotUndoException {
        super.undo();
        m_CMGridTDStructure.changeStructureTestDataDescription(m_Index, m_OldDescription,m_OldType, m_StructureTestData);
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
        m_CMGridTDStructure.changeStructureTestDataDescription(m_Index, m_Description,m_Type, m_StructureTestData);
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
