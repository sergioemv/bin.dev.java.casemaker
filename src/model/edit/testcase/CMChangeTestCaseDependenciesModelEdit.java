/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testcase;

import java.util.Vector;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestCase;
import model.edit.CMModelUndoableEdit;

/**
 * @author smoreno
 *
 */
public class CMChangeTestCaseDependenciesModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private TestCase testCase;
	private Vector dependencies;
	private Vector oldDependencies;

	/**
	 * @param p_testCase
	 * @param p_deps
	 */
	public CMChangeTestCaseDependenciesModelEdit(TestCase p_testCase, Vector p_deps) {
		this.testCase = p_testCase;
		this.dependencies = p_deps;
		this.oldDependencies = this.testCase.getDependencies();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.testCase.setDependencies(this.oldDependencies);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.testCase.setDependencies(this.dependencies);
	}

	public Object getModifiedObject() {
		return testCase;
	}

}
