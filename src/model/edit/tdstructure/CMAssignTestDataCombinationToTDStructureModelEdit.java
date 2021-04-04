/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.tdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TDStructure;
import model.TestDataCombinations;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAssignTestDataCombinationToTDStructureModelEdit extends
		AbstractUndoableEdit {

	private TDStructure m_structure;
	private TestDataCombinations newCombination;
	private TestDataCombinations oldCombination;
	
	public CMAssignTestDataCombinationToTDStructureModelEdit(TDStructure p_tdstructure, TestDataCombinations p_testDataComb){
		this.m_structure = p_tdstructure;
		this.oldCombination = p_tdstructure.getTestDataCombination();
		this.newCombination = p_testDataComb;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(this.m_structure!=null && this.newCombination!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(this.m_structure!=null && this.newCombination!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_structure.setTestDataCombination(this.newCombination);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_structure.setTestDataCombination(this.oldCombination);
	}

}
