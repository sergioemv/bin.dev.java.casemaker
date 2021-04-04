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
public class CMChangeValuesInTDSructureInTestDataEdit extends CMComponentAwareEdit {
    private CMPanelTestDataView m_CMGridTDStructure;
    private int m_IndexTestData;
    private int m_indexStructure;
    private Object m_editingObject;
    private String m_value;
    private String m_Oldvalue;
    private int m_row;
    private int m_column;
    private CMCompoundEdit m_ce;

    public CMChangeValuesInTDSructureInTestDataEdit(CMFrameView p_CMFrameView,CMPanelTestDataView p_CMGridTDStructure,
        int p_IndexTestData,int p_indexStructure, Object p_editingObject,  String p_value,String p_OldValue,
        int p_row, int p_column, CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_ce) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_IndexTestData = p_IndexTestData;
        this.m_indexStructure=p_indexStructure;
        this.m_editingObject=p_editingObject;
        this.m_value=p_value;
        this.m_Oldvalue=p_OldValue;
        this.m_row=p_row;
        this.m_column=p_column;
        this.m_ce = (CMCompoundEdit) p_ce;
    }

    public void undo() throws CannotUndoException {
        super.undo();
    	if(this.m_ce.isInProgress()){
    		m_ce.endAll();
    	}
    	this.m_ce.undo();
        m_CMGridTDStructure.changeValuesInGridStructureInTestData(m_IndexTestData,m_indexStructure,m_editingObject,m_Oldvalue,m_row,m_column);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 this.m_CMGridTDStructure.getM_CMGridTDStructure().setRowSelected(m_row);
		 this.m_CMGridTDStructure.getM_CMGridTDStructure().setColumnSelected(m_column);
		 m_CMGridTDStructure.getM_CMGridTDStructure().changeSelection(m_row,m_column,false,false);
		 m_CMGridTDStructure.getM_CMGridTDStructure().grabFocus();
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
    	if(this.m_ce.isInProgress()){
    		m_ce.endAll();
    	}
    	this.m_ce.redo();
        m_CMGridTDStructure.changeValuesInGridStructureInTestData(m_IndexTestData,m_indexStructure,m_editingObject,m_value,m_row,m_column);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 this.m_CMGridTDStructure.getM_CMGridTDStructure().setRowSelected(m_row);
		 this.m_CMGridTDStructure.getM_CMGridTDStructure().setColumnSelected(m_column);
		 m_CMGridTDStructure.getM_CMGridTDStructure().changeSelection(m_row,m_column,false,false);
		 m_CMGridTDStructure.getM_CMGridTDStructure().grabFocus();
       setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
