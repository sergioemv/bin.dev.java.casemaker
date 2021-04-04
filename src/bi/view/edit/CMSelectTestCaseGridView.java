/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package bi.view.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.controller.TestCaseManager;
import bi.view.causeeffectviews.CMCauseEffectStructureGridView;
import bi.view.mainframeviews.CMApplication;
import bi.view.testcaseviews.CMDescriptionTestCaseViews;

import model.Effect;
import model.TestCase;

/**
 * @author smoreno
 *
 */
public class CMSelectTestCaseGridView extends AbstractUndoableEdit implements
		UndoableEdit {

	private TestCase selectedTestCase;
	private TestCase oldSelectedTestCase;
	private CMDescriptionTestCaseViews grid;
	public CMSelectTestCaseGridView(TestCase p_TestCase) {
		super();
		this.selectedTestCase = p_TestCase;
		grid = CMApplication.frame.getJSPlitPaneTestCaseStructureView().getM_CMDescriptionTestCaseViews();
		oldSelectedTestCase = grid.getSelectedTestCase();
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
	//	selectedEffect = grid.getSelectedEffect();
		if (oldSelectedTestCase!=null)
			grid.selectCell(oldSelectedTestCase, null);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		//oldSelectedEffect = grid.getSelectedEffect();
		grid.selectCell(selectedTestCase,null);
	}
}
