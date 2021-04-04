/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testdataformat;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestDataFormat;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeValueInTestDataFormatModelEdit extends
		AbstractUndoableEdit {
	
	private TestDataFormat m_tdformat;
	private String newValue;
	private String oldValue;
	
	public CMChangeValueInTestDataFormatModelEdit(TestDataFormat p_tdformat,String p_value){
		this.m_tdformat = p_tdformat;
		this.oldValue = p_tdformat.getValue();
		this.newValue = p_value;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_tdformat!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_tdformat!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_tdformat.setValue(this.newValue);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_tdformat.setValue(this.oldValue);
	}

}
