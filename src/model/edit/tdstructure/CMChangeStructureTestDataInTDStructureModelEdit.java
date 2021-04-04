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
public class CMChangeStructureTestDataInTDStructureModelEdit extends AbstractUndoableEdit{

	private TDStructure m_tdstructure;
	private Vector newSTD;
	private Vector oldSTD;
	
	public CMChangeStructureTestDataInTDStructureModelEdit(TDStructure p_tdstructure, Vector p_std){
		m_tdstructure = p_tdstructure;
		oldSTD = p_tdstructure.getM_StructureTestData();
		newSTD = p_std;
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_tdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_tdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_tdstructure.setM_StructureTestData(newSTD);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_tdstructure.setM_StructureTestData(oldSTD);
	}

}

