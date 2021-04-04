/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotestdataviews;

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMDeleteTestDataEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
	Vector m_ResultCompariosnActual;
        Vector m_ResultComparisonTarget;
        Vector m_TestDataSetDeleted;
        Vector m_TestDataSetIndex;
        Vector m_TDDeletedinTDS;
        TestData m_StructureTestData;

    public CMDeleteTestDataEdit(CMFrameView p_CMFrameView,CMGridTDStructure gridTDStructure, Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget,Vector p_TestDataSetDeleted,Vector  p_TestDataSetIndex,TestData td,int indexTD,Vector  p_TDDeletedinTDS,CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  gridTDStructure;
		     m_Index=indexTD;
 			m_ResultCompariosnActual=p_ResultCompariosnActual;
         m_ResultComparisonTarget=p_ResultComparisonTarget;
         m_TestDataSetDeleted=p_TestDataSetDeleted;
         m_TestDataSetIndex=p_TestDataSetIndex;
         m_TDDeletedinTDS=p_TDDeletedinTDS;
        m_StructureTestData=td;

    }

    public void undo() throws CannotUndoException {
        super.undo();
        m_CMGridTDStructure.undoDeleteTestData(m_ResultCompariosnActual,  m_ResultComparisonTarget, m_TestDataSetDeleted,   m_TestDataSetIndex, m_StructureTestData,m_Index,m_TDDeletedinTDS);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		m_CMFrameView.getPanelTestDataView().update(m_Index,0);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMGridTDStructure.redoDeleteTestData(m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
     //   m_CMFrameView.getPanelTestDataView().update(m_Index,0);

        setUndoState();
        setRedoState();
    }
}
