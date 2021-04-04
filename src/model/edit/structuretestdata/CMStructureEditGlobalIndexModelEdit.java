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
public class CMStructureEditGlobalIndexModelEdit extends AbstractUndoableEdit {

	private StructureTestData m_structure;
	private int newIndex;
	private int oldIndex;
	
	public CMStructureEditGlobalIndexModelEdit(StructureTestData p_structure, int p_globalIndex){
		this.m_structure = p_structure;
		this.oldIndex = p_structure.getGlobalIndex();
		this.newIndex = p_globalIndex;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(this.m_structure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(this.m_structure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_structure.setGlobalIndex(this.newIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_structure.setGlobalIndex(this.oldIndex);
	}

}
