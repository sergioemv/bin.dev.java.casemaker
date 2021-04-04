/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.idset;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.IdSet;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteElementStructureToIdSetModelEdit extends
		AbstractUndoableEdit {

	private IdSet m_idset;
	private int i;

	public CMDeleteElementStructureToIdSetModelEdit(IdSet p_idset, int i){
		m_idset = p_idset;
		this.i = i;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_idset!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_idset!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_idset.deleteId(i);

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_idset.registerId(i);
	}




}

