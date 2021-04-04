/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved. 
 */
package model.edit.project;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.Project2;
import model.ProjectReference;

/**
 * @author smoreno
 *
 */
public class CMBindProjectAndReferenceModelEdit extends AbstractUndoableEdit
		implements UndoableEdit {

	private Project2 project;
	private ProjectReference projectReference;

	/**
	 * @param p_projectReference 
	 * @param p_project 
	 * 
	 */
	public CMBindProjectAndReferenceModelEdit(Project2 p_project, ProjectReference p_projectReference) {
		super();
		this.project = p_project;
		this.projectReference = p_projectReference;
		// TODO Auto-generated constructor stub
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	this.project.setProjectReference(null);
	this.projectReference.setProject(null);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	this.project.setProjectReference(projectReference);
	this.projectReference.setProject(project);
}
}
