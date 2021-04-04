/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;
import model.TDStructure;
import model.util.IdSet;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMGenerateTDStructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private TDStructure m_TDStructure;
    private StructureTestData m_StructureTestData;
    private boolean m_WithConfirmation;
    private IdSet m_clonIdSetStructure;
    private IdSet m_clonIdSetTestData;
    private IdSet m_clonIdSetTestDataSet;

    public CMGenerateTDStructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure, int p_Index,TDStructure p_TDStructure,StructureTestData p_StructureTestData,IdSet clonIdSetStructure,IdSet clonIdSetTestData,IdSet clonIdSetTestDataSet, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_TDStructure=p_TDStructure;
        this.m_StructureTestData  = p_StructureTestData;
        this.m_Index = p_Index;
        this.m_clonIdSetStructure=clonIdSetStructure;
        this.m_clonIdSetTestData=clonIdSetTestData;
        this.m_clonIdSetTestDataSet=clonIdSetTestDataSet;

    }

    public void redo() throws CannotUndoException {
        super.redo();
  /*      m_CMFrameView.getTreeWorkspaceView().setSelectedTDStructure(m_TDStructure);
         m_CMFrameView.getTreeWorkspaceView().setSelectedResultComparation(m_TDStructure);
        m_CMFrameView.getPanelTDStructureView().setTDStructure(m_TDStructure);
        m_CMGridTDStructure.setTDStructure(m_TDStructure);
        m_CMFrameView.getPanelTestDataView().setM_TestDataCombinations(m_TDStructure.getTestDataCombination());
        m_CMFrameView.getPanelTestDataSetView().setTDStructure(m_TDStructure);
        m_CMFrameView.getPanelResultComparation().setM_TDStructure(m_TDStructure);
*/
     //   m_CMGridTDStructure.redoGenerateTDStructure(m_TDStructure,(IdSet)m_clonIdSetStructure.clone(),(IdSet)m_clonIdSetTestData.clone(),(IdSet)m_clonIdSetTestDataSet.clone());
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();
    }

    public void undo() throws CannotRedoException {
        super.undo();
        TDStructure aux=m_TDStructure.cloneTDStructure();
        m_CMGridTDStructure.deleteStructureTestData(m_Index);
        m_TDStructure=aux;
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
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
