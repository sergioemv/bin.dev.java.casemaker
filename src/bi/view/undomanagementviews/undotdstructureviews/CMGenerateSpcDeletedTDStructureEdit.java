/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotdstructureviews;

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TDStructure;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMGenerateSpcDeletedTDStructureEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private TDStructure m_TDStructure;
//hcanedo_21102004_begin
	private Vector m_testcaseintestdata;




    public CMGenerateSpcDeletedTDStructureEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure,TDStructure p_TDstructure, int p_Index,Vector p_testcaseintestdata, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_Index = p_Index;
        this.m_TDStructure=p_TDstructure;
   		this.m_testcaseintestdata = p_testcaseintestdata;
    }

    public void redo() throws CannotUndoException {
        super.redo();
        m_CMGridTDStructure.initTDStructureForRedo();
		m_CMGridTDStructure.generateSpcTestDataEdit(m_testcaseintestdata);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        if(m_Index>0)
        {
        m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
        m_CMGridTDStructure.update(m_Index);
        }
        else
        {
             m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(0);
        m_CMGridTDStructure.update(0);
        }
  //       m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_Index);
//		 m_CMGridTDStructure.update(m_Index);
        setUndoState();
        setRedoState();
    }
//hcanedo_21102004_end
    public void undo() throws CannotRedoException {
        super.undo();
        if(m_Index>=0)
        	m_CMGridTDStructure.deleteStructureTestData(m_Index);
        m_CMGridTDStructure.chargeOldValuesInTDstructure(m_TDStructure);
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
