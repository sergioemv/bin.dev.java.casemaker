/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.tdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import model.TDStructure;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeIdTestDataSetInTDStructureModelEdit extends AbstractUndoableEdit {

	private TDStructure m_TDStructure;
	private int oldID;
	private int newID;

	public CMChangeIdTestDataSetInTDStructureModelEdit(TDStructure p_TDStructure, int id){
		m_TDStructure = p_TDStructure;
		oldID = p_TDStructure.getIdsTestDataSet();
		newID = id;
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
		this.m_TDStructure.setIdsTestDataSet(newID);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_TDStructure.setIdsTestDataSet(oldID);
	}


}

