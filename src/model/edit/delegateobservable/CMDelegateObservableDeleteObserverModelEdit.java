/**
 * Developed by BUSINESS SOFTWARE INNOVATIONS.
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit.delegateobservable;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.DelegateObservable;
import model.IObserver;

/**
 * @author svonborries
 *
 */
@SuppressWarnings("serial")
public class CMDelegateObservableDeleteObserverModelEdit extends
		AbstractUndoableEdit {

	private DelegateObservable m_observable;
	private IObserver m_observer;
	
	public CMDelegateObservableDeleteObserverModelEdit(DelegateObservable p_observable, IObserver p_observer){
		this.m_observable = p_observable;
		this.m_observer = p_observer;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		if(m_observable!=null&&m_observer!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		if(m_observable!=null&&m_observer!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.m_observable.deleteObserver(m_observer);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.m_observable.addObserver(m_observer);
	}

}
