/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.resultcomparison;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.ResultComparation;
import model.edit.CMModelUndoableEdit;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteTestDataSetFromResultComparisionModelEdit extends
		AbstractUndoableEdit implements CMModelUndoableEdit {


	private int oldNameIndex;
	private ResultComparation m_resComparation;
	private String newName;
	private String oldName;

	public CMDeleteTestDataSetFromResultComparisionModelEdit(ResultComparation p_resComparation, String p_name){
		oldNameIndex = p_resComparation.getTestDataSetActual().indexOf(p_name);
		if(oldNameIndex!=-1){
			oldName = (String) p_resComparation.getTestDataSetActual().elementAt(oldNameIndex);
		}
		else{
			oldNameIndex = 0;
			oldName = "";
		}
		m_resComparation = p_resComparation;
		newName = p_name;
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_resComparation!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_resComparation!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		m_resComparation.getTestDataSetActual().remove(newName);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		if(oldName=="")
			return;
		m_resComparation.getTestDataSetActual().add(oldNameIndex,oldName);
	}
	public Object getModifiedObject() {
		// TODO Auto-generated method stub
		return m_resComparation;
	}

}
