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
public class CMSessionMoveWorkspace extends AbstractUndoableEdit implements
		UndoableEdit {

	private Workspace2 workspace;
	private Session2 session;
	private int index;
	private int oldIndex;

	/**
	 * @param p_session
	 * @param p_workspace
	 * @param p_index
	 */
	public CMSessionMoveWorkspace(Session2 p_session, Workspace2 p_workspace, int p_index) {
		// TODO Auto-generated constructor stub
		this.workspace = p_workspace;
		this.session = p_session;
		this.index = p_index;
		oldIndex = session.getM_Workspaces().indexOf(workspace);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
	 */
	@Override
	public boolean canRedo() {
		return session.getM_Workspaces().contains(workspace)&&(index < session.getM_Workspaces().size());
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return session.getM_Workspaces().contains(workspace);
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		session.removeWorkspace(workspace);
		session.addWorkspace(workspace,oldIndex);
}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		session.removeWorkspace(workspace);
		session.addWorkspace(workspace,index);
	}
}
