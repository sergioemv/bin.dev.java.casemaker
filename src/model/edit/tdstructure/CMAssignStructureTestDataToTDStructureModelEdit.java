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
public class CMAssignStructureTestDataToTDStructureModelEdit extends
		AbstractUndoableEdit {

	private TDStructure m_tdstructure;
	private StructureTestData m_std;
	
	public CMAssignStructureTestDataToTDStructureModelEdit(TDStructure p_tdstructure, StructureTestData p_std){
		this.m_tdstructure = p_tdstructure;
		this.m_std = p_std;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(this.m_std!=null && this.m_tdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(this.m_std!=null && this.m_tdstructure!=null)
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
		this.m_tdstructure.getM_StructureTestData().addElement(this.m_std);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_tdstructure.getM_StructureTestData().removeElement(this.m_std);
	}

}
