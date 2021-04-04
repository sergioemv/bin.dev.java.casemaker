/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.structuretestdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;
import model.StructureTestData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMRemoveElementInStructureTestData extends AbstractUndoableEdit {

	private ITypeData m_typeData;
	private StructureTestData m_structure;

	public CMRemoveElementInStructureTestData(ITypeData p_typeData, StructureTestData p_structureTestData){
		m_structure = p_structureTestData;
		m_typeData = p_typeData;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((m_structure!= null) && (m_typeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((m_structure!= null) && (m_typeData!=null))
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
		m_structure.getTypeData().removeElement(m_typeData);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_structure.getTypeData().addElement(m_typeData);
	}

}
