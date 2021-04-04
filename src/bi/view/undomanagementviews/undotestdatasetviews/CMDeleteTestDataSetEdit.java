/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotestdatasetviews;

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestDataSet;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMDeleteTestDataSetEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
	Vector m_ResultCompariosnActual;
        Vector m_ResultComparisonTarget;

        TestDataSet m_StructureTestData;

    public CMDeleteTestDataSetEdit(CMFrameView p_CMFrameView,CMGridTDStructure gridTDStructure, Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget,TestDataSet td,int indexTD,CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  gridTDStructure;
		     m_Index=indexTD;
 			m_ResultCompariosnActual=p_ResultCompariosnActual;
         m_ResultComparisonTarget=p_ResultComparisonTarget;
        m_StructureTestData=td;

    }

    public void undo() throws CannotUndoException {
        super.undo();
        m_CMGridTDStructure.undoDeleteTestDataSet(m_ResultCompariosnActual,  m_ResultComparisonTarget,m_StructureTestData,m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		m_CMFrameView.getPanelTestDataSetView().update();
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMGridTDStructure.redoDeleteTestDataSet(m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);


        setUndoState();
        setRedoState();
    }
}
