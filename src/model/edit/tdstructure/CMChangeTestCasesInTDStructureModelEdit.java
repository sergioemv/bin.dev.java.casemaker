/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.tdstructure;

import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TDStructure;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeTestCasesInTDStructureModelEdit extends
		AbstractUndoableEdit {
	
	
	
	private TDStructure m_tdstructure;
	private Vector newTestCases;
	private Vector oldTestCases;

	public CMChangeTestCasesInTDStructureModelEdit(TDStructure p_tdstructure, Vector p_testCases){
		m_tdstructure = p_tdstructure;
		newTestCases = p_testCases;
		oldTestCases = p_tdstructure.getM_TestCaseInTDStructure();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_tdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_tdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_tdstructure.setM_TestCaseInTDStructure(newTestCases);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_tdstructure.setM_TestCaseInTDStructure(oldTestCases);
	}

}
