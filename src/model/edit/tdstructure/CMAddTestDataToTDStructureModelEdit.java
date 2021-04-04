/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.tdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import model.TDStructure;
import model.TestData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddTestDataToTDStructureModelEdit extends AbstractUndoableEdit {

	private TDStructure m_structure;
	private TestData m_testdata;
	
	public CMAddTestDataToTDStructureModelEdit(TDStructure p_structure, TestData p_testdata){
		this.m_structure = p_structure;
		this.m_testdata = p_testdata;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_testdata!=null &&m_structure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_testdata!=null &&m_structure!=null)
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
		this.m_structure.getTestDataCombination().getM_TestDatas().addElement(this.m_testdata);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_structure.getTestDataCombination().getM_TestDatas().removeElement(this.m_testdata);
	}

}
