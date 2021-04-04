/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.effect;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Effect;
import model.ExpectedResult;
import model.edit.CMModelUndoableEdit;

/**
 * @author smoreno
 *
 */
public class CMRemoveExpectedResultModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private Effect effect;
	private ExpectedResult expectedResult;

	/**
	 * @param p_effect
	 * @param p_exp
	 */
	public CMRemoveExpectedResultModelEdit(Effect p_effect, ExpectedResult p_exp) {
		this.effect = p_effect;
		this.expectedResult = p_exp;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
 */
@Override
public boolean canUndo() {
	// TODO Auto-generated method stub
	return !this.effect.getLnkExpectedResults().contains(this.expectedResult);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {

	super.undo();
	this.effect.addExpectedResult(this.expectedResult);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {

	super.redo();
	this.effect.removeExpectedResult(this.expectedResult);
}
public Object getModifiedObject() {
	return effect;
}
}
