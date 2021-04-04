/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package bi.view.indextdstructureupdate;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import bi.view.testdataviews.CMIndexTDStructureUpdate;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMUpdateIndexTestDataSetInCMIndexTDStructureUpdateVIewEdit extends
		AbstractUndoableEdit {

	
	private CMIndexTDStructureUpdate m_indextdstructure;
	private int newIndex;
	private int oldIndex;

	public CMUpdateIndexTestDataSetInCMIndexTDStructureUpdateVIewEdit(CMIndexTDStructureUpdate p_indextdstructure, int p_indextestdata){
		m_indextdstructure = p_indextdstructure;
		newIndex = p_indextestdata;
		oldIndex = p_indextdstructure.getindexTestDataSet();
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_indextdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_indextdstructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_indextdstructure.setindexTestDataSet(newIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_indextdstructure.setindexTestDataSet(oldIndex);
	}
	
}
