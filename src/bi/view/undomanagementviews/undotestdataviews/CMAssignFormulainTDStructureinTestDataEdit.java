/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotestdataviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMPanelTestDataView;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

@SuppressWarnings("serial")
public class CMAssignFormulainTDStructureinTestDataEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMPanelTestDataView;
    private int m_IndexTestData;
    private int m_indexStructure;
    private int m_row;
    private int m_column;
    private CMCompoundEdit m_ce;

    /**
     * @param p_CMFrameView
     * @param p_IndexTestData
     * @param p_indexStructure
     * @param p_row
     * @param p_column
     * @param p_ce
     * @param p_FocusInfo
     * @author svonborries
     * @version: Modify to be adapted to the new model of Undo/Redo
     */
    public CMAssignFormulainTDStructureinTestDataEdit(CMFrameView p_CMFrameView,int p_IndexTestData,int p_indexStructure, int p_row, int p_column,UndoableEdit p_ce,CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMPanelTestDataView=p_CMFrameView.getPanelTestDataView();
        this.m_IndexTestData = p_IndexTestData;
        this.m_indexStructure=p_indexStructure;
        this.m_row=p_row;
        this.m_column=p_column;
        this.m_ce = (CMCompoundEdit) p_ce;
    }

    public void undo() throws CannotUndoException {
        super.undo();
        if(m_ce.isInProgress())
        	m_ce.endAll();
        m_ce.undo();
		m_CMPanelTestDataView.undoredoSetFormula(m_IndexTestData,m_indexStructure,m_row,m_column);
     	int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        if(m_ce.isInProgress())
        	m_ce.endAll();
        m_ce.redo();
		m_CMPanelTestDataView.undoredoSetFormula(m_IndexTestData,m_indexStructure,m_row,m_column);
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
