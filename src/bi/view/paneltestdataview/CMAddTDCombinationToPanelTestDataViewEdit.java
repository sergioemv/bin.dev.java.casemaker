/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.paneltestdataview;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.TestDataCombinations;

import bi.view.testdataviews.CMPanelTestDataView;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMAddTDCombinationToPanelTestDataViewEdit extends
		AbstractUndoableEdit {
	
	private CMPanelTestDataView m_panelTDView;
	private TestDataCombinations newTDCombination;
	private TestDataCombinations oldTDCombination;
	
	public CMAddTDCombinationToPanelTestDataViewEdit(CMPanelTestDataView p_panelTDView, TestDataCombinations p_TDCombination){
		this.m_panelTDView = p_panelTDView;
		this.oldTDCombination = p_panelTDView.getM_TestDataCombinations();
		this.newTDCombination = p_TDCombination;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_panelTDView!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_panelTDView!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_panelTDView.setM_TestDataCombinations(newTDCombination);
		this.m_panelTDView.update();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_panelTDView.setM_TestDataCombinations(oldTDCombination);
		this.m_panelTDView.update();
	}

}
