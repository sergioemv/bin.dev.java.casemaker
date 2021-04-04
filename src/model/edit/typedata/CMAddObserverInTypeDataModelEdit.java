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
public class CMAddObserverInTypeDataModelEdit extends AbstractUndoableEdit {

	private ITypeData m_observer;
	private ITypeData m_observable;
	private String m_key;

	public CMAddObserverInTypeDataModelEdit(ITypeData p_obsrever, ITypeData p_observable, String p_key){

		m_key = p_key;
		m_observable = p_observable;
		m_observer = p_obsrever;

	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if((this.m_observable!=null)&&(this.m_observer!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if((this.m_observable!=null)&&(this.m_observer!=null))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_observable.addObserver(this.m_observer);
		m_observer.addSubject(m_key,m_observable);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_observable.deleteObserver(this.m_observer);
		this.m_observer.removeSubject(m_key);
	}

}
