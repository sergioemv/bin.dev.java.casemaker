/*******************************************************************************
****************************************************************************** Developed by BUSINESS SOFTWARE INNOVATIONS. .
****************************************************************************** Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
*******************************************************************************/

package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMGridTDStructure;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 * modified to undo/redo
 *
 */
@SuppressWarnings("serial")
public class CMDeleteTDStructureFieldsEdit extends CMComponentAwareEdit {
    private CMGridTDStructure m_CMGridTDStructure;
	private int m_IndexStructure;
	private CMCompoundEdit m_ce;

    public CMDeleteTDStructureFieldsEdit(CMFrameView p_CMFrameView,CMGridTDStructure p_CMGridTDStructure, int p_IndexStructure, CMUndoMediator.FocusInfo p_FocusInfo, UndoableEdit p_ce) {
        super(p_FocusInfo, p_CMFrameView);
        this.m_CMGridTDStructure =  p_CMGridTDStructure;
        this.m_IndexStructure=p_IndexStructure;
        this.m_ce = (CMCompoundEdit) p_ce;
    }

    public void redo() throws CannotUndoException {
        super.redo();
        if(m_ce.isInProgress())
        	m_ce.endAll();
        m_ce.redo();
        //m_CMGridTDStructure.deleteFieldTDStructure(m_Index,m_IndexStructure,m_TypeData);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
         m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_IndexStructure);
		 m_CMGridTDStructure.update(m_IndexStructure);
        setUndoState();
        setRedoState();

    }

    public void undo() throws CannotRedoException {
        super.undo();
        if(m_ce.isInProgress())
        	m_ce.endAll();
        m_ce.undo();
        //m_CMGridTDStructure.insertFieldTDStructure(m_Index,m_IndexStructure, m_TypeData);
		int n = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() - 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);


        int m = m_CMFrameView.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
		 m_CMFrameView.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(m_IndexStructure);
		 m_CMGridTDStructure.update(m_IndexStructure);
        setUndoState();
        setRedoState();
    }

}

/* This Software has been developed by Business Software Innovations  .
Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. */
