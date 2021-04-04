/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit.workspace;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import bi.controller.SessionManager;

import model.ProjectReference;
import model.Workspace2;

/**
 * @author smoreno
 *
 */
public class CMAddProjectReferenceModelEdit extends AbstractUndoableEdit
		implements UndoableEdit {

	private Workspace2 workspace;
	private ProjectReference reference;
	private Workspace2 oldWorkspace;

	/**
	 * @param p_reference 
	 * @param p_p_workspace2 
	 * 
	 */
	public CMAddProjectReferenceModelEdit(Workspace2 p_workspace2, ProjectReference p_reference) {
		super();
		this.workspace = p_workspace2;
		this.reference = p_reference;
		this.oldWorkspace = p_reference.getM_Workspace();
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		reference.setM_Workspace(workspace);
		workspace.removeProjectReference(reference);
		//write the changes to the session
		SessionManager.INSTANCE.writeSession2ToFile(SessionManager.getCurrentSession());
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		// TODO Auto-generated method stub
		super.redo();
		reference.setM_Workspace(oldWorkspace);
		workspace.addProjectReference(reference);
		//write the changes to the session (no need to write the project, its already there)
		SessionManager.INSTANCE.writeSession2ToFile(SessionManager.getCurrentSession());
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#canRedo()
 */
@Override
public boolean canRedo() {
	return !this.workspace.getM_ProjectReferences().contains(reference);
}
}
