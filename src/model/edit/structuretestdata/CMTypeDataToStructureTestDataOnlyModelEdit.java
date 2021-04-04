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
public class CMTypeDataToStructureTestDataOnlyModelEdit extends
		AbstractUndoableEdit {

	private StructureTestData m_structureTestData;
	private ITypeData m_typeData;
	private StructureTestData oldStructureTD; 
	
	public CMTypeDataToStructureTestDataOnlyModelEdit(StructureTestData p_structure, ITypeData p_typeData){
		this.m_structureTestData = p_structure;
		this.m_typeData = p_typeData;
		this.oldStructureTD = p_typeData.getStructureTestData();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(this.m_structureTestData!=null && this.m_typeData!=null)
			return true;
		return false;
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(this.m_structureTestData!=null && this.m_typeData!=null)
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
		this.m_typeData.setStructureTestData(this.m_structureTestData);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_typeData.setStructureTestData(this.oldStructureTD);
	}

}
