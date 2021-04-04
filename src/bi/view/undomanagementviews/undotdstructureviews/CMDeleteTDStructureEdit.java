/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotdstructureviews;

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMDeleteTDStructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
	Vector m_ResultCompariosnActual;
        Vector m_ResultComparisonTarget;
        Vector m_TestDataSetDeleted;
        Vector m_TestDataSetIndex;
        Vector m_TestDataDeleted;
        Vector m_TestDataIndex;
        Vector m_TDDeletedinTDS;
        Vector m_StDeletedinTD;
        StructureTestData m_StructureTestData;

    public CMDeleteTDStructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,Vector p_ResultCompariosnActual, Vector p_ResultComparisonTarget, Vector p_TestDataSetDeleted, Vector  p_TestDataSetIndex, Vector p_TestDataDeleted, Vector p_TestDataIndex, StructureTestData structTD,int index, Vector p_TDDeletedinTDS, Vector p_StDeletedinTD,CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
		     m_Index=index;
 			m_ResultCompariosnActual=p_ResultCompariosnActual;
         m_ResultComparisonTarget=p_ResultComparisonTarget;
         m_TestDataSetDeleted=p_TestDataSetDeleted;
         m_TestDataSetIndex=p_TestDataSetIndex;
         m_TestDataDeleted=p_TestDataDeleted;
         m_TestDataIndex=p_TestDataIndex;
         m_TDDeletedinTDS=p_TDDeletedinTDS;
         m_StDeletedinTD=p_StDeletedinTD;
        m_StructureTestData=structTD;

    }

    public void undo() throws CannotUndoException {
        super.undo();
        m_CMGridTDStructure.redoGenerateTDStructure(m_ResultCompariosnActual,m_ResultComparisonTarget,m_TestDataSetDeleted,m_TestDataSetIndex,m_TestDataDeleted,m_TestDataIndex,m_StructureTestData,m_Index,m_TDDeletedinTDS,m_StDeletedinTD);
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
        m_CMGridTDStructure.deleteStructureTestData(m_Index);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        if(m_Index>0)
        {
        m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index-1);
        m_CMGridTDStructure.update(m_Index-1);
        }
        else
        {
             m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(0);
        m_CMGridTDStructure.update(0);
        }

        setUndoState();
        setRedoState();
    }
}
