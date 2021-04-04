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
public class CMDeleteProjectReferenceModelEdit extends AbstractUndoableEdit
		implements UndoableEdit {

	private ProjectReference projectRefence;
	private Workspace2 worskpace;
	private int index;

	/**
	 * @param p_projectReference 
	 * 
	 */
	public CMDeleteProjectReferenceModelEdit(ProjectReference p_projectReference) {
		super();
		this.projectRefence = p_projectReference;
		this.worskpace = p_projectReference.getM_Workspace();
		this.index = this.worskpace.getM_ProjectReferences().indexOf(projectRefence);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#undo()
	 */
	@Override
	public void undo() throws CannotUndoException {
		super.undo();
		this.projectRefence.setM_Workspace(this.worskpace);
		this.worskpace.addProjectReference(projectRefence,index);
		//write the changes to the session
		SessionManager.INSTANCE.writeSession2ToFile(SessionManager.getCurrentSession());
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#canUndo()
 */
	@Override
	public boolean canUndo() {

		return !this.worskpace.getM_ProjectReferences().contains(projectRefence);
	}
	/* (non-Javadoc)
	 * @see javax.swing.undo.AbstractUndoableEdit#redo()
	 */
	@Override
	public void redo() throws CannotRedoException {
		super.redo();
		this.projectRefence.setM_Workspace(null);
		this.worskpace.removeProjectReference(projectRefence);
		//write the changes to the session
		SessionManager.INSTANCE.writeSession2ToFile(SessionManager.getCurrentSession());
	}	
}
