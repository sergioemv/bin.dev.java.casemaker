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
public class CMRemoveEquivalenceClassModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMEquivalenceClassesBean bean;
	private EquivalenceClass equivalenceClass;

	/**
	 * @param p_bean
	 * @param p_class
	 */
	public CMRemoveEquivalenceClassModelEdit(CMEquivalenceClassesBean p_bean, EquivalenceClass p_class) {

		this.bean = p_bean;
		this.equivalenceClass = p_class;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.bean.addEquivalenceClass(equivalenceClass);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.bean.removeEquivalenceClass(equivalenceClass);
	}

	public Object getModifiedObject() {
		return bean;
	}
}
