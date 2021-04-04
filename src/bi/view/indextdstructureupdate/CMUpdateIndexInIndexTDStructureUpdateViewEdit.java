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
public class CMUpdateIndexInIndexTDStructureUpdateViewEdit extends
		AbstractUndoableEdit {

	private CMIndexTDStructureUpdate m_indexTDStructure;
	private int oldIndex;
	private int newIndex;
	
	public CMUpdateIndexInIndexTDStructureUpdateViewEdit(CMIndexTDStructureUpdate p_indexTDStructure, int p_index){
		this.m_indexTDStructure = p_indexTDStructure;
		this.oldIndex = p_indexTDStructure.getIndex();
		this.newIndex = p_index;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_indexTDStructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_indexTDStructure!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_indexTDStructure.setindex(newIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		m_indexTDStructure.setindex(oldIndex);
	}

}
