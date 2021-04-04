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
public class CMStructureEditNameModelEdit extends AbstractUndoableEdit {

	private StructureTestData m_structure;
	private String m_newName;
	private String m_oldName;
	
	public CMStructureEditNameModelEdit(StructureTestData p_Structure, String p_name){
		this.m_structure = p_Structure;
		this.m_newName = p_name;
		this.m_oldName = p_Structure.getName();
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_structure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_structure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_structure.setName(this.m_newName);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_structure.setName(this.m_oldName);
	}

}
