package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import model.CMError;
import model.util.CMErrorsBean;

public class CMAddErrorModelEdit extends AbstractUndoableEdit implements
		UndoableEdit {

	CMErrorsBean parent;
	CMError error;
	public CMAddErrorModelEdit(CMErrorsBean parent, CMError error) {
		this.parent = parent;
		this.error = error;
	}
	@Override
	public void redo() throws CannotRedoException {
		this.parent.addError(error);
		super.redo();
	}
	
	@Override
	public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	this.parent.removeError(error);
	super.undo();
	}

}
