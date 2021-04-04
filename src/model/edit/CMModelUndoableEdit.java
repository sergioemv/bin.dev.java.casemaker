package model.edit;

import javax.swing.undo.UndoableEdit;

public interface CMModelUndoableEdit extends UndoableEdit {

	public Object getModifiedObject();
}
