package model.edit;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

import model.util.CMValueBean;

public class CMChangeValueModelEdit extends AbstractUndoableEdit implements
		CMModelUndoableEdit {

	private CMValueBean model;
	private String value;
	private String oldValue;

	/**
	 * @param p_model
	 * @param p_description
	 */
	public CMChangeValueModelEdit(CMValueBean p_model, String p_value) {
		model = p_model;
		oldValue = p_model.getValue();
		//Logger.getLogger(this.getClass()).debug("Old description input "+oldValue);

		value = p_value;
	}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#undo()
 */
@Override
public void undo() throws CannotUndoException {
	// TODO Auto-generated method stub
	super.undo();
	//Logger.getLogger(this.getClass()).debug("Old description "+oldDescription);
	model.setValue(oldValue);
}
/* (non-Javadoc)
 * @see javax.swing.undo.AbstractUndoableEdit#redo()
 */
@Override
public void redo() throws CannotRedoException {
	// TODO Auto-generated method stub
	super.redo();
	model.setValue(value);
}
public Object getModifiedObject() {
	return model;
}
}
