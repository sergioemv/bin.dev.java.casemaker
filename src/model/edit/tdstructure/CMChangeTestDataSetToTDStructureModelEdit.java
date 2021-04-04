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
public class CMChangeTestDataSetToTDStructureModelEdit extends
		AbstractUndoableEdit {

	private TDStructure m_TDStructure;
	private Vector newTestDataSet;
	private Vector oldTestDataSet;

	public CMChangeTestDataSetToTDStructureModelEdit(TDStructure p_tdstructure,Vector p_testdataset){
		this.m_TDStructure = p_tdstructure;
		this.oldTestDataSet = p_tdstructure.getM_TestDataSet();
		this.newTestDataSet = p_testdataset;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_TDStructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_TDStructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_TDStructure.setM_TestDataSet(newTestDataSet);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_TDStructure.setM_TestDataSet(oldTestDataSet);
	}

}

