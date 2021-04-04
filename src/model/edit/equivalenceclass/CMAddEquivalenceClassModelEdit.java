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
import model.util.CMEquivalenceClassesBean;

/**
 * @author smoreno
 *
 */
public class CMAddEquivalenceClassModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private CMEquivalenceClassesBean element;
	private EquivalenceClass equivalenceClass;


	/**
	 * @param p_bean
	 * @param p_equivalenceClass
	 */
	public CMAddEquivalenceClassModelEdit(CMEquivalenceClassesBean p_bean, EquivalenceClass p_equivalenceClass) {
		element = p_bean;
		equivalenceClass = p_equivalenceClass;

	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		// TODO Auto-generated method stub
		super.undo();
		element.removeEquivalenceClass(equivalenceClass);

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		super.redo();
		element.addEquivalenceClass(equivalenceClass);

	}
	public Object getModifiedObject() {
		return element;
	}

}
