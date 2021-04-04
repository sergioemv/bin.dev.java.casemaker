/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.equivalenceclass;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.EquivalenceClass;
import model.edit.CMModelUndoableEdit;

/**
 * @author smoreno
 *
 */
public class CMEquivalenceClassChangeValueModelEdit extends
		AbstractUndoableEdit implements CMModelUndoableEdit {

	private EquivalenceClass equivalenceClass;
	private String value;
	private String oldValue;

	/**
	 * @param p_equivalenceClass
	 * @param p_stringValue
	 */
	public CMEquivalenceClassChangeValueModelEdit(EquivalenceClass p_equivalenceClass, String p_stringValue) {

		equivalenceClass  = p_equivalenceClass;
		oldValue = equivalenceClass.getValue();
		value = p_stringValue;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		// TODO Auto-generated method stub
		super.undo();
		equivalenceClass.setValue(oldValue);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		super.redo();
		equivalenceClass.setValue(value);
	}

	public Object getModifiedObject() {
		return equivalenceClass;
	}
}
