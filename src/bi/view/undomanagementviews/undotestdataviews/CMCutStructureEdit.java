/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotestdataviews;

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;
import model.TestData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMCutStructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_IndexTestData;
	Vector m_ResultCompariosnActual;
        Vector m_ResultComparisonTarget;
        Vector m_TestDataSetDeleted;
        Vector m_TestDataSetIndex;
        TestData m_TestData;
        StructureTestData m_StructureTestData;
        int m_indexStructure;
        boolean m_isdeletedTD;
        Vector m_TDDeletedinTDS;

    public CMCutStructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure gridTDStructure, Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget,Vector p_TestDataSetDeleted,Vector  p_TestDataSetIndex,TestData td,int indexTD,StructureTestData p_StructureTestdata, int indexStructure,boolean isdeletedTD,Vector p_TDDeletedinTDS,CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  gridTDStructure;
		     m_IndexTestData=indexTD;
 			m_ResultCompariosnActual=p_ResultCompariosnActual;
         m_ResultComparisonTarget=p_ResultComparisonTarget;
         m_TestDataSetDeleted=p_TestDataSetDeleted;
         m_TestDataSetIndex=p_TestDataSetIndex;
        m_TestData=td;
        m_StructureTestData=p_StructureTestdata;
        m_indexStructure=indexStructure;
        m_isdeletedTD=isdeletedTD;
        m_TDDeletedinTDS=p_TDDeletedinTDS;


    }

    public void undo() throws CannotUndoException {
        super.undo();
        m_CMGridTDStructure.undoCancelAssignTDStructureInTestData(m_ResultCompariosnActual,  m_ResultComparisonTarget, m_TestDataSetDeleted,   m_TestDataSetIndex, m_TestData,m_IndexTestData,m_StructureTestData,m_indexStructure,m_isdeletedTD,m_TDDeletedinTDS);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		m_CMFrameView.getPanelTestDataView().update(m_IndexTestData,m_indexStructure);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMGridTDStructure.redoCancelAssignTDStructureInTestData(m_IndexTestData,m_indexStructure);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
 

        setUndoState();
        setRedoState();
    }
}/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
