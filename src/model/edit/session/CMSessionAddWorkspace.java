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
public class CMSessionAddWorkspace extends AbstractUndoableEdit implements
		UndoableEdit {

	private Workspace2 workspace;
	private Session2 session;
	private Session2 oldsession;
	private int index = -1; 
	/**
	 * @param p_currentSession
	 * @param p_workspace
	 */
	public CMSessionAddWorkspace(Session2 p_currentSession, Workspace2 p_workspace) {
		this.workspace = p_workspace;
		this.session = p_currentSession;
		this.oldsession = p_workspace.getM_Session();
	}
/**
	 * @param p_session
	 * @param p_workspace
	 * @param p_index
	 */
	public CMSessionAddWorkspace(Session2 p_session, Workspace2 p_workspace, int p_index) {
		this.workspace = p_workspace;
		this.session = p_session;
		this.oldsession = p_workspace.getM_Session();
		this.index = p_index;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	super.undo();
	workspace.setM_Session(oldsession);
	session.removeWorkspace(workspace);
	
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	super.redo();
	if (index == -1)
		session.addWorkspace(workspace);
	else
		session.addWorkspace(workspace,index);
	workspace.setM_Session(session);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
 */
@Override
public boolean canRedo() {
	return !session.getM_Workspaces().contains(workspace);
}
}
