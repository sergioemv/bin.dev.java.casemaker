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
public class CMAddExpectedResult extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private Effect effect;
	private ExpectedResult expectedResult;

	/**
	 * @param p_effect
	 * @param p_expRes
	 */
	public CMAddExpectedResult(Effect p_effect, ExpectedResult p_expRes) {
		this.effect = p_effect;
		this.expectedResult = p_expRes;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		// TODO Auto-generated method stub
		super.undo();
		this.effect.removeExpectedResult(expectedResult);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.effect.addExpectedResult(expectedResult);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		return !this.effect.getLnkExpectedResults().contains(expectedResult);
	}

	public Object getModifiedObject() {
		return effect;
	}
}
