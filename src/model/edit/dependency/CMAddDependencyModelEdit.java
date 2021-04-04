/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.dependency;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Dependency;
import model.edit.CMModelUndoableEdit;
import model.util.CMDependencyBean;

/**
 * @author smoreno
 *
 */
public class CMAddDependencyModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMDependencyBean bean;
	private Dependency dependency;

	/**
	 * @param p_bean
	 * @param p_dependency
	 */
	public CMAddDependencyModelEdit(CMDependencyBean p_bean, Dependency p_dependency) {

		this.bean = p_bean;
		this.dependency = p_dependency;

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.bean.removeDependency(dependency);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.bean.addDependency(dependency);
	}

	public Object getModifiedObject() {
		return bean;
	}
}
