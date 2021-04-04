/**
 * This Software has been developed by Business Software Innovations  .
 * Copyright (c)2003 Díaz und Hilterscheid Unternehmensberatung. All rights reserved.
 */
package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMDescriptionBean;

/**
 * @author smoreno
 *
 */
public class CMChangeDescriptionModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private CMDescriptionBean model;
	private String description;
	private String oldDescription;

	/**
	 * @param p_model
	 * @param p_description
	 */
	public CMChangeDescriptionModelEdit(CMDescriptionBean p_model, String p_description) {
		model = p_model;
		oldDescription = p_model.getDescription();

		description = p_description;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	model.setDescription(oldDescription);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	model.setDescription(description);
}
public Object getModifiedObject() {

	return model;
}
}
