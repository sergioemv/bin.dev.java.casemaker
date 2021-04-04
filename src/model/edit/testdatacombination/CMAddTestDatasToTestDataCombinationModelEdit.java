/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testdatacombination;

import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestDataCombinations;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddTestDatasToTestDataCombinationModelEdit extends
		AbstractUndoableEdit {
	
	
	private TestDataCombinations m_tdc;
	private Vector newTestDatas;
	private Vector oldTestDatas;

	public CMAddTestDatasToTestDataCombinationModelEdit(TestDataCombinations p_tdc,Vector p_testdatas){
		m_tdc = p_tdc;
		newTestDatas = p_testdatas;
		oldTestDatas = p_tdc.getM_TestDatas();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_tdc != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_tdc != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_tdc.setM_TestDatas(newTestDatas);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_tdc.setM_TestDatas(oldTestDatas);
	}

}
