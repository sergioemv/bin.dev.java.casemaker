package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMDescriptionEditableBean;

public class CMChangeDescriptionEditableModelEdit extends AbstractUndoableEdit
		implements CMModelUndoableEdit {

	private CMDescriptionEditableBean model;
	private String description;
	private String oldDescription;

	/**
	 * @param p_model
	 * @param p_description
	 */
	public CMChangeDescriptionEditableModelEdit(CMDescriptionEditableBean p_model, String p_description) {
		model = p_model;
		oldDescription = p_model.getDescriptionEditable();

		description = p_description;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	model.setDescriptionEditable(oldDescription);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	model.setDescriptionEditable(description);
}
public Object getModifiedObject() {

	return model;
}
}


