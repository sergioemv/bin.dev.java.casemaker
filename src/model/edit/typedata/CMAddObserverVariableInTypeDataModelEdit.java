/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ITypeData;
import model.Variable;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddObserverVariableInTypeDataModelEdit extends
		AbstractUndoableEdit {

	private Variable m_variable;
	private ITypeData m_typeData;

	public CMAddObserverVariableInTypeDataModelEdit(Variable p_variable, ITypeData p_typeData){
		m_typeData = p_typeData;
		m_variable = p_variable;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((m_typeData!= null)&&(m_variable!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((m_typeData!= null)&&(m_variable!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_variable.getM_Observers().addObserver(m_typeData);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_variable.getM_Observers().deleteObserver(m_typeData);
	}



}
