/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testdata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeCOntTestDataSetInTestDataModelEdit extends
		AbstractUndoableEdit {

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	
	
	private TestData m_testdata;
	private int oldI;
	private int newI;

	public CMChangeCOntTestDataSetInTestDataModelEdit(TestData p_Testdata, int i){
		m_testdata = p_Testdata;
		oldI = p_Testdata.getContTestDataSet();
		newI = i;
	}
	
	
	@Override
	public boolean canRedo() {
		if(m_testdata!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_testdata!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_testdata.setContTestDataSet(newI);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_testdata.setContTestDataSet(oldI);
	}

}
