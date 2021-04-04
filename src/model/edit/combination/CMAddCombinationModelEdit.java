/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.combination;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.Combination;
import model.edit.CMModelUndoableEdit;
import model.util.CMCombinationsBean;

/**
 * @author smoreno
 *
 */
public class CMAddCombinationModelEdit extends AbstractUndoableEdit implements CMModelUndoableEdit {

	private CMCombinationsBean combinationBean;
	private Combination combination;

	/**
	 * @param p_bean
	 * @param p_combination
	 */
	public CMAddCombinationModelEdit(CMCombinationsBean p_bean, Combination p_combination) {

		this.combinationBean = p_bean;
		this.combination = p_combination;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoableEdit#undo()
	 */
	public void undo() throws CannotUndoException {
		super.undo();
		combinationBean.removeCombination(combination);
	}


	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoableEdit#redo()
	 */
	public void redo() throws CannotRedoException {
		super.redo();
		combinationBean.addCombination(combination);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.UndoableEdit#canRedo()
	 */
	public boolean canRedo() {
		return !combinationBean.getCombinations().contains(combination);
	}

	public Object getModifiedObject() {
		return combinationBean;
	}


}
