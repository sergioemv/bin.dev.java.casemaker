/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.typedata;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import model.ITypeData;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDeleteObserverInTypeDataModelEdit extends AbstractUndoableEdit {

	private ITypeData m_observerTypeData;
	private ITypeData m_observableTypeData;
	private String m_key;

	public CMDeleteObserverInTypeDataModelEdit(ITypeData p_observerTypeData, ITypeData p_observableTypeData, String p_key){

		m_key = p_key;
		this.m_observerTypeData=p_observerTypeData;
		this.m_observableTypeData = p_observableTypeData;

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((this.m_observableTypeData!=null)&&(this.m_observerTypeData!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((this.m_observableTypeData!=null)&&(this.m_observerTypeData!=null))
			return true;
		return false;
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_observableTypeData.deleteObserver(this.m_observerTypeData);
		this.m_observerTypeData.removeSubject(m_key);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_observableTypeData.addObserver(this.m_observerTypeData);
		m_observerTypeData.addSubject(m_key,m_observableTypeData);
	}

}
