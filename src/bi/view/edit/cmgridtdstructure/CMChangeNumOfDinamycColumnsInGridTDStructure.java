/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.edit.cmgridtdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.testdataviews.CMGridTDStructure;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeNumOfDinamycColumnsInGridTDStructure extends
		AbstractUndoableEdit {

	
	private CMGridTDStructure m_gridtdstructure;
	private int newColumns;
	private int oldColumns;

	public CMChangeNumOfDinamycColumnsInGridTDStructure(CMGridTDStructure p_gridtdstructure,int columns){
		m_gridtdstructure = p_gridtdstructure;
		newColumns = columns;
		oldColumns = p_gridtdstructure.getNumofcolumnsDinamic();
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_gridtdstructure != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_gridtdstructure != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_gridtdstructure.setNumofcolumnsDinamic(newColumns);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_gridtdstructure.setNumofcolumnsDinamic(oldColumns);
	}

}
