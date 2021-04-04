/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
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
public class CMAddTestDataSetToTDStructureModelEdit extends
		AbstractUndoableEdit {


	private TDStructure m_tds;
	private TestDataSet newTDS;

	public CMAddTestDataSetToTDStructureModelEdit(TDStructure p_tds, TestDataSet p_testdataset){
		m_tds = p_tds;
		newTDS = p_testdataset;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_tds!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_tds!=null)
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
		m_tds.getM_TestDataSet().addElement(newTDS);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_tds.getM_TestDataSet().removeElement(newTDS);
	}

}
