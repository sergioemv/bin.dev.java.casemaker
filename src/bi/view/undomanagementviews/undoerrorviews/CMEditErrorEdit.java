package bi.view.undomanagementviews.undoerrorviews;

/**
 ****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */

/**
* integration_fcastro_17082004 whole new class
* */

import java.util.Vector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.CMError;
import bi.view.errorviews.CMErrorGridView;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;

public class CMEditErrorEdit extends CMComponentAwareEdit{
    CMErrorGridView m_CMErrorGridView;
    int m_Index;
    Vector m_OldData;
    Vector m_NewData;
    Vector m_OldErrorsOfTestCases;
    Vector m_TestCasesToAssign;
    Vector m_TestCasesToDelete;
    Vector m_OldTestCasesOfError;
    CMError m_CMError;

    public CMEditErrorEdit(int index,Vector oldData,Vector newData,CMError error,Vector errorsOfTestCases,Vector rightList,Vector leftList){
		super();
        
        m_CMErrorGridView = m_CMFrameView.getCMErrorGridView();
        m_Index =index;
        m_OldData = oldData;
        m_NewData = newData;
        m_CMError = error;
        m_OldErrorsOfTestCases = errorsOfTestCases;
        m_TestCasesToAssign = rightList;
        m_TestCasesToDelete =leftList;
        m_OldTestCasesOfError = (Vector)error.getM_TestCases().clone();
    }

    
     public void undo() throws CannotUndoException {
        super.undo();
//HCanedo_30112004_Begin
        m_CMErrorGridView.changeErrorData(m_CMError,m_Index,m_OldData,null,null,m_OldTestCasesOfError, null);//,m_OldErrorsOfTestCases,m_OldTestCasesOfError,m_TestCasesToAssign,m_TestCasesToDelete);
//HCanedo_30112004_End
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        setUndoState();
        setRedoState();
    }

    public void redo() throws CannotRedoException {
        super.redo();
        m_CMErrorGridView.changeErrorData(m_CMError,m_Index,m_NewData,null,null,m_TestCasesToAssign,m_TestCasesToDelete);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);

        setUndoState();
        setRedoState();
    }
}

