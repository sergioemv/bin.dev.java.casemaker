/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.typedata;

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
public class CMChangeStructureTestDataInTypeDataModelEdit extends
		AbstractUndoableEdit {

	private ITypeData m_typeData;
	private StructureTestData m_structure;
	private StructureTestData m_oldstructure;

	public CMChangeStructureTestDataInTypeDataModelEdit(ITypeData p_typeData, StructureTestData p_structureTestData){
		m_typeData = p_typeData;
		m_structure = p_structureTestData;
		m_oldstructure = p_typeData.getStructureTestData();
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
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_typeData.setStructureTestData(m_structure);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_typeData.setStructureTestData(m_oldstructure);
	}



}
