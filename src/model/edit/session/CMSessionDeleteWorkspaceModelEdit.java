/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit.session;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.Session2;
import model.Workspace2;

/**
 * @author smoreno
 *
 */
public class CMSessionDeleteWorkspaceModelEdit extends AbstractUndoableEdit
		implements UndoableEdit {
	private int index = -1;
	private Session2 session;
	private Workspace2 workspace2;
	public CMSessionDeleteWorkspaceModelEdit(Workspace2 p_workspace2) {
		super();
		// TODO Auto-generated constructor stub
		this.session = p_workspace2.getM_Session();
		this.workspace2 = p_workspace2;
		index = this.session.getM_Workspaces().indexOf(p_workspace2);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return !session.getM_Workspaces().contains(workspace2);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		session.addWorkspace(workspace2,index);
		workspace2.setM_Session(session);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		session.removeWorkspace(workspace2);
		workspace2.setM_Session(null);
	}
}
