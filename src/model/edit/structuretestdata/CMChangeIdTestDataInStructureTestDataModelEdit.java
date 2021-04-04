/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.structuretestdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeIdTestDataInStructureTestDataModelEdit extends
		AbstractUndoableEdit {



	private StructureTestData m_std;
	private int newID;
	private int oldID;

	public CMChangeIdTestDataInStructureTestDataModelEdit(StructureTestData p_std, int p_idTestData){
		m_std = p_std;
		newID = p_idTestData;
		oldID = p_std.getIdTestData();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_std!= null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_std!= null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_std.setIdTestData(newID);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_std.setIdTestData(oldID);
	}

}
