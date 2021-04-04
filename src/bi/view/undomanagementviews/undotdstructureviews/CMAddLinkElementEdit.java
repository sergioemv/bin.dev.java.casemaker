/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.undomanagementviews.undotdstructureviews;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import bi.view.mainframeviews.CMFrameView;
import bi.view.testdataviews.CMIndexTDStructureUpdate;
import bi.view.undomanagementviews.CMCompoundEdit;
import bi.view.undomanagementviews.undoviews.CMComponentAwareEdit;
import bi.view.undomanagementviews.undoviews.CMUndoMediator;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddLinkElementEdit extends CMComponentAwareEdit{

	private CMCompoundEdit m_ce;
	private CMFrameView m_frame;

	public CMAddLinkElementEdit(CMUndoMediator.FocusInfo p_FocusInfo, CMFrameView p_CMFrameView, UndoableEdit p_ce) {
		super(p_FocusInfo, p_CMFrameView);
		this.m_ce = (CMCompoundEdit) p_ce;
		this.m_frame = p_CMFrameView;
	}

	/* (non-Javadoc)
	 * @see bi.view.undomanagementviews.undoviews.CMComponentAwareEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		if(m_ce.isInProgress())
			m_ce.endAll();
		m_ce.redo();
		//m_frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		int n = m_frame.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects() + 1;
		m_CMFrameView.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_frame.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()-1;
        m_frame.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        m_frame.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(CMIndexTDStructureUpdate.getInstance().getIndex());
		m_frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
        setUndoState();
        setRedoState();

	}

	/* (non-Javadoc)
	 * @see bi.view.undomanagementviews.undoviews.CMComponentAwareEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		if(m_ce.isInProgress())
			m_ce.endAll();
		m_ce.undo();
		//m_frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
		int n = m_frame.getM_CMUndoMediator().getM_NumberOfUndoableEditObjects()- 1;
		m_frame.getM_CMUndoMediator().setM_NumberOfUndoableEditObjects(n);

        int m = m_frame.getM_CMUndoMediator().getM_NumberOfRedoableEditObjects()+1;
   		m_frame.getM_CMUndoMediator().setM_NumberOfRedoableEditObjects(m);
        m_frame.getPanelTDStructureView().ScrollPaneStructureDescriptionView.update(CMIndexTDStructureUpdate.getInstance().getIndex());
		m_frame.getGridTDStructure().update(CMIndexTDStructureUpdate.getInstance().getIndex());
        setUndoState();
        setRedoState();

	}




}
