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
public class CMTestDataEditDescriptionModelEdit extends AbstractUndoableEdit {

	private TestData m_testdata;
	private String m_oldDescription;
	private String m_newDescription;
	
	public CMTestDataEditDescriptionModelEdit(TestData p_testdata, String p_description){
		this.m_testdata = p_testdata;
		this.m_newDescription = p_description;
		this.m_oldDescription = p_testdata.getDescription();
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
		this.m_testdata.setDescription(this.m_newDescription);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_testdata.setDescription(this.m_oldDescription);
	}

}
