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
public class CMTestDataEditTestCaseInTestDataModelEdit extends
		AbstractUndoableEdit {

	private TestData m_testdata;
	private String m_newText;
	private String m_oldText;
	
	public CMTestDataEditTestCaseInTestDataModelEdit(TestData p_testdata, String p_testcaseintestdata){
		this.m_testdata = p_testdata;
		this.m_newText = p_testcaseintestdata;
		this.m_oldText = p_testdata.getM_TestCaseinTestData();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
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
		this.m_testdata.setM_TestCaseinTestData(this.m_newText);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_testdata.setM_TestCaseinTestData(this.m_oldText);
	}

}
