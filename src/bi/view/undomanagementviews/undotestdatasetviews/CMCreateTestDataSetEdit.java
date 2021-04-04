/* ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
/*******************************************************************************
******************************************************************************  Developed by BUSINESS SOFTWARE INNOVATIONS. .
******************************************************************************  Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/
 

package bi.view.undomanagementviews.undotestdatasetviews;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestDataSet;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

public class CMCreateTestDataSetEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
    private int m_Index;
    private TestDataSet m_TestDataSet;

    public CMCreateTestDataSetEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure, TestDataSet p_TestDataSet,int p_index, CMUndoMediator.FocusInfo p_FocusInfo) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_TestDataSet = p_TestDataSet;
        this.m_Index=p_index;
    }

    public void undo() throws CannotUndoException {
        super.undo();
        boolean withConfirmation = false;
//hcanedo_21_09_2004_begin
        m_CMGridTDStructure.undoCreateTestDataSet(m_Index,m_TestDataSet.getId());
//hcanedo_21_09_2004_end
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMGridTDStructure.redoCreateTestDataSet(m_TestDataSet);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);

        setUndoState();
        setRedoState();
    }
}
