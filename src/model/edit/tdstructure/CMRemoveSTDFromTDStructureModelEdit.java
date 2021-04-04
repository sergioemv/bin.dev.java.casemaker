/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.tdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.StructureTestData;
import model.TDStructure;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMRemoveSTDFromTDStructureModelEdit extends AbstractUndoableEdit {

	
	private TDStructure m_tdstructure;
	private int m_index;
	private StructureTestData m_std;

	public CMRemoveSTDFromTDStructureModelEdit(TDStructure p_Tdstructure,int index){
		m_tdstructure = p_Tdstructure;
		m_index = index;
		m_std = (StructureTestData) p_Tdstructure.getM_StructureTestData().elementAt(index);
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
		m_tdstructure.getM_StructureTestData().removeElementAt(m_index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_tdstructure.getM_StructureTestData().addElement(m_std);
	}

}
