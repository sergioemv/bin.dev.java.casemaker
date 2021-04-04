/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.testcase;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestCase;
import model.TestCaseGroup;
import model.edit.CMModelUndoableEdit;

/**
 * @author smoreno
 *
 */
public class CMChangeTestCaseGroupModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private TestCase testcase;
	private TestCaseGroup group;
	private TestCaseGroup oldGroup;

	/**
	 * @param p_testCase
	 * @param p_group
	 */
	public CMChangeTestCaseGroupModelEdit(TestCase p_testCase, TestCaseGroup p_group) {

		this.testcase = p_testCase;
		this.group = p_group;
		this.oldGroup = testcase.getTestCaseGroup();
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	super.undo();
	this.testcase.setTestCaseGroup(this.oldGroup);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	this.testcase.setTestCaseGroup(this.group);

}
public Object getModifiedObject() {
	// TODO Auto-generated method stub
	return testcase;
}
}
