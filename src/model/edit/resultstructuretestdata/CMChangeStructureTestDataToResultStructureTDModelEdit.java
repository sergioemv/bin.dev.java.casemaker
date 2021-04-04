/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.resultstructuretestdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ResultStructureTestData;
import model.StructureTestData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeStructureTestDataToResultStructureTDModelEdit extends
		AbstractUndoableEdit {

	private ResultStructureTestData m_resultStrucTD;
	private StructureTestData newSTD;
	private StructureTestData oldSTD;
	
	public CMChangeStructureTestDataToResultStructureTDModelEdit(ResultStructureTestData p_structure, StructureTestData p_srtucture2){
		m_resultStrucTD = p_structure;
		newSTD = p_srtucture2;
		if(p_structure.getStructureTestDataLinked()!=null)
			oldSTD = p_structure.getStructureTestDataLinked();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_resultStrucTD!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_resultStrucTD!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_resultStrucTD.setStructureTestDataLinked(newSTD);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_resultStrucTD.setStructureTestDataLinked(oldSTD);
	}

}
