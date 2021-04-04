/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoableEdit;

import bi.view.elementviews.CMGridElements;
import bi.view.mainframeviews.CMApplication;

import model.EquivalenceClass;

/**
 * @author smoreno
 *
 */
public class CMSelectEquivalenceClassEffectsGridView extends
		AbstractUndoableEdit implements UndoableEdit {

	private EquivalenceClass equivalenceClass;
	private CMGridElements grid;

	/**
	 * @param p_equivalenceClass
	 */
	public CMSelectEquivalenceClassEffectsGridView(EquivalenceClass p_equivalenceClass) {
		this.equivalenceClass = p_equivalenceClass;
		grid = CMApplication.frame.getElementsGrid();
		
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	grid.selectEquivalenceClassEffects(equivalenceClass);
}
}
