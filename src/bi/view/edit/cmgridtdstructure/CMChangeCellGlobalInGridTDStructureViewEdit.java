/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.edit.cmgridtdstructure;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.cells.CMCellTDStructureGlobal;
import bi.view.testdataviews.CMGridTDStructure.CMGridModel;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMChangeCellGlobalInGridTDStructureViewEdit extends
		AbstractUndoableEdit {



	private CMGridModel m_gridmodel;
	private Object cellGlobalNew;
	private Object cellGlobalOld;
	private int row;
	private int column;

	public CMChangeCellGlobalInGridTDStructureViewEdit(CMGridModel cmGridModel, CMCellTDStructureGlobal cellGlobal, int p_row, int p_column){
		m_gridmodel = cmGridModel;
		cellGlobalNew = cellGlobal;
		//cellGlobalOld = getCellObjectAt(p_row,p_column);
		row = p_row;
		column = p_column;

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_gridmodel != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_gridmodel != null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_gridmodel.setValueAt(cellGlobalNew,row,column);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_gridmodel.setValueAt(cellGlobalOld,row,column);
	}

}
