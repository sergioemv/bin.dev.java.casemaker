/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 D�az und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.tdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TDStructure;
import model.TestDataSet;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMRemoveTestDataSetElementAtFromTDStructureModelEdit extends
		AbstractUndoableEdit {


	private TDStructure m_tdstructure;
	private TestDataSet m_testdataset;
	private int m_index;

	public CMRemoveTestDataSetElementAtFromTDStructureModelEdit(TDStructure p_tdstructure, int p_index){
		m_tdstructure = p_tdstructure;
		m_testdataset = (TestDataSet) p_tdstructure.getM_TestDataSet().elementAt(p_index);
		m_index = p_index;
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
		m_tdstructure.getM_TestDataSet().removeElementAt(m_index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_tdstructure.getM_TestDataSet().add(m_index,m_testdataset);
	}

}
