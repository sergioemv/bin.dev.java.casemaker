/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestData;
import model.TestDataCombinations;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMRemoveTestDataFromTestDataCombination extends
		AbstractUndoableEdit {

	
	private TestDataCombinations m_tdc;
	private TestData m_testdata;

	public CMRemoveTestDataFromTestDataCombination(TestDataCombinations p_tdc, int p_index){
		m_tdc = p_tdc;
		m_testdata = (TestData) p_tdc.getM_TestDatas().elementAt(p_index);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_tdc!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_tdc!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_tdc.getM_TestDatas().removeElement(m_testdata);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_tdc.getM_TestDatas().addElement(m_testdata);
	}

}
